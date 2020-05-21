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
				
				
				
				//���������� ��������
				UserVO buyer = new UserVO();
				buyer.setUserId(request.getParameter("buyerId"));
				//��ǰ���� ��������
				ProductVO purchaseProd = new ProductVO();
				purchaseProd.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
				
				//�ŷ��������� ������ ��ǰ�������
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setBuyer(buyer);
				purchaseVO.setPurchaseProd(purchaseProd);
				
				// �������
				purchaseVO.setPaymentOption(request.getParameter("paymentOption"));	
				// ������ �̸�
				purchaseVO.setReceiverName(request.getParameter("receiverName"));	
				// ������ ��ȭ��ȣ
				purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
				// ��� �ּ�
				purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));	
				// ��ۿ�û����
				purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
				// ��������
				purchaseVO.setDivyDate(request.getParameter("receiverDate"));
				
				PurchaseService service = new PurchaseServiceImpl();
				service.addPurchase(purchaseVO);
				
				
				request.setAttribute("purchaseVO", purchaseVO);
				
				return "forward:/purchase/addPurchase.jsp";
			}
		}
