package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println("들어온거확인");
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(prodNo);
		System.out.println("prodNo :"+request.getParameter("prodNo"));
		
		productVO.setProdName(request.getParameter("prodName"));
		System.out.println("prodName :"+request.getParameter("prodName"));
		
		productVO.setProdDetail(request.getParameter("prodDetail"));
		System.out.println("prodDetail :"+request.getParameter("prodDetail"));
		
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		System.out.println("price :"+request.getParameter("price"));
		
		productVO.setFileName(request.getParameter("fileName"));
		System.out.println("fileName :"+request.getParameter("fileName"));
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(productVO);
		System.out.println();
		HttpSession session = request.getSession();
		int sessionId = ((ProductVO)session.getAttribute("product")).getProdNo();
		//System.out.println(productVO);
		if( sessionId == prodNo){
			session.setAttribute("product", productVO);
		}
		
		return "forward:/getProduct.do?"+prodNo+"&menu=manage";
	}
}