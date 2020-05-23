package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//layout에 목록에서 클릭할때 userId를 buyerId로저장해서 태워서 보낸걸 꺼내옴.
		String buyerId=request.getParameter("buyerId");

		SearchVO searchVO = new SearchVO();
		int page=1;
		
		if(request.getParameter("page") !=null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		searchVO.setPage(page);
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getPurchaseList(searchVO, buyerId);
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
				
		return "forward:/purchase/listPurchase.jsp";
	}
}