package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {


	public void insertPurchase(PurchaseVO purchaseVO)throws Exception {
	Connection con = DBUtil.getConnection();
		
		String sql="INSERT INTO transaction"
				+ " VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	
	
	
	
	public Map<String,Object> getPurchaseList(SearchVO searchVO,String buyerId) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE buyer_id='" + buyerId + "'";
		
		PreparedStatement stmt = con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
										//커서이동을 자유롭게하고 업데이트내용을 데이터변경이 가능하도록함.
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		rs.last();
		int total = rs.getRow(); 
		System.out.println("로우의 수:" + total);
		//로우의수를 구하는것임.
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
//						 페이지위치		   *	 페이지글게시수 		    - 			게시글+1
		//absolute 해당쿼리로 바로이동한다.
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if(total>0) {
			for(int i = 0; i < searchVO.getPageUnit(); i++) {
			
			UserVO user = new UserVO();
			UserService userUservice = new UserServiceImpl();
			user = userUservice.getUser(buyerId);
			
			ProductVO product=new ProductVO();
			ProductService productService=new ProductServiceImpl();
			
			product.setProdNo(rs.getInt("PROD_NO"));
			product = productService.getProduct(product.getProdNo());
			
			PurchaseVO purchase=new PurchaseVO();
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			
			list.add(purchase);
			if (!rs.next())
				break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		map.put("count", new Integer(total));  // (게시글 총 갯수를 count로)
		System.out.println("map().size() : "+ map.size());
		
		rs.close();
		stmt.close();
		con.close();
		
		
		return map;
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		//구매번호를 가져온다.
		System.out.println("여기는 파인드펄체스의 traNO"+tranNo);
		String sql = "SELECT * FROM transaction WHERE tran_no=?";
		
		Connection con = DBUtil.getConnection();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		PurchaseVO purchaseVO = null;
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			ProductDAO productDAO = new ProductDAO();
			UserDAO userDAO = new UserDAO();
			
			purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
			purchaseVO.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
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
	
	public void updatePurchase(PurchaseVO purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="UPDATE transaction SET payment_option=?, receiver_name=?, "
				+ "receiver_phone=?, demailaddr=?, dlvy_request=?,"
				+ "dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();

	}

	public void updateTranCode(PurchaseVO purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		if(purchase.getPurchaseProd().getProTranCode().equals("2")) {
			
			String sql="UPDATE transaction SET tran_status_code='2'"
					+ " WHERE prod_no=?";
		
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
			stmt.executeUpdate();
					
		}else if(purchase.getPurchaseProd().getProTranCode().equals("3")) {
			
			String sql="UPDATE transaction SET tran_status_code='3'"
					+ " WHERE tran_no=?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getTranNo());
			stmt.executeUpdate();
		}
		
		con.close();
	}
}
