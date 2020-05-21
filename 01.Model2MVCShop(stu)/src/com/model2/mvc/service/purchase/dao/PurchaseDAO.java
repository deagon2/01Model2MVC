package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {


	public void insertPurchase(PurchaseVO purchaseVO) {
		String SQL = "INSERT INTO transaction VALUES(seq_transaction_tran_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 1, sysdate, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SQL);){
			pstmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
			pstmt.setString(2, purchaseVO.getBuyer().getUserId());
			pstmt.setString(3, purchaseVO.getPaymentOption());
			pstmt.setString(4, purchaseVO.getReceiverName());
			pstmt.setString(5, purchaseVO.getReceiverPhone());
			pstmt.setString(6, purchaseVO.getDivyAddr());
			pstmt.setString(7, purchaseVO.getDivyRequest());
			pstmt.setString(8, purchaseVO.getDivyDate());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		System.out.println("여기는 파인드펄체스의 traNO"+tranNo);
		String sql = "SELECT * FROM transaction WHERE tran_no=?";
		
		Connection con = DBUtil.getConnection();
				
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		PurchaseVO purchaseVO = null;
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			purchaseVO.setBuyer(userService.getUser(rs.getString("buyer_id")));
			purchaseVO.setPurchaseProd(productService.getProduct(rs.getInt("prod_no")));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setDivyDate(rs.getString("order_data"));
			purchaseVO.setOrderDate(rs.getDate("dlvy_date"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			
		}
		rs.close();
		stmt.close();
		con.close();
		
		return purchaseVO;
	}
	
	
	
	public Map<String,Object> getPurchaseList(SearchVO searchVO,String buyerId) throws Exception {
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
		
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM transaction WHERE buyer_id='" + buyerId + "'";
		
		PreparedStatement stmt = con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);


		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setBuyer(userService.getUser(buyerId));
				purchaseVO.setPurchaseProd(productService.getProduct(rs.getInt("prod_no")));
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setTranNo(rs.getInt("tran_no"));
				purchaseVO.setTranCode(rs.getString("tran_status_code"));
				

				list.add(purchaseVO);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		map.put("count", new Integer(total));
		System.out.println("map().size() : "+ map.size());
		
		con.close();
		
		return map;
	}
}
