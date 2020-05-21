package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class AddPurchaseAction extends Action{

			public String execute(	HttpServletRequest request,
														HttpServletResponse response) throws Exception {
				
				
				
				//구매자정보 가져오기
				UserVO buyer = new UserVO();
				buyer.setUserId(request.getParameter("buyerId"));
				//상품정보 가져오기
				ProductVO purchaseProd = new ProductVO();
				purchaseProd.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
				
				//거래자정보에 구매자 상품정보등록
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setBuyer(buyer);
				purchaseVO.setPurchaseProd(purchaseProd);
				
				// 결제방법
				purchaseVO.setPaymentOption(request.getParameter("paymentOption"));	
				// 수령자 이름
				purchaseVO.setReceiverName(request.getParameter("receiverName"));	
				// 수령자 전화번호
				purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
				// 배송 주소
				purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));	
				// 배송요청사항
				purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
				// 배송희망일
				purchaseVO.setDivyDate(request.getParameter("receiverDate"));
				
				PurchaseService service = new PurchaseServiceImpl();
				service.addPurchase(purchaseVO);
				
				
				request.setAttribute("purchaseVO", purchaseVO);
				
				return "forward:/purchase/addPurchase.jsp";
			}
		}
