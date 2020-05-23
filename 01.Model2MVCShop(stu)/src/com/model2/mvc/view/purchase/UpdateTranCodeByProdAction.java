package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeByProdAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		String tranCode=request.getParameter("tranCode");
		
		ProductVO product=new ProductVO();
		product.setProdNo(prodNo);
		product.setProTranCode(tranCode);
		
		PurchaseVO purchase=new PurchaseVO();
		purchase.setPurchaseProd(product);
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
