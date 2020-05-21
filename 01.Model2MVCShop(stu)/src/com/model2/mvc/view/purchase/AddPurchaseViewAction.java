package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.vo.UserVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

//읽어들이는파트
public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.getProduct(Integer.parseInt(request.getParameter("prod_no")));
		
		UserService userService = new UserServiceImpl();
		UserVO userVO = userService.getUser(((UserVO)session.getAttribute("user")).getUserId());
		
	
		request.setAttribute("productVO", productVO);
		request.setAttribute("userVO", userVO);

		System.out.println("2222"+userVO);
		System.out.println("111"+productVO);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
