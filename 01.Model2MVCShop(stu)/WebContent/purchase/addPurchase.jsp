<%@ page contentType="text/html; charset=EUC-KR"%>

<%@ page import="com.model2.mvc.service.product.vo.ProductVO" %>
<%@ page import="com.model2.mvc.service.user.vo.UserVO" %>
<%@ page import="com.model2.mvc.service.purchase.vo.PurchaseVO" %>

	<%
	UserVO user=(UserVO)request.getAttribute("user");
	ProductVO product=(ProductVO)request.getAttribute("product");
	PurchaseVO purchase = (PurchaseVO)request.getAttribute("purchase");
	%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%=product.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td><%=user.getUserId()%></td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		
	 	<%if(purchase.getPaymentOption().equals("1")){ %>
		���ݱ���
		<%}else if(purchase.getPaymentOption().equals("2")){ %>
		�ſ뱸��
		<%} %>
				
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%=user.getUserName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%=user.getPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%=user.getAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%=purchase.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%=purchase.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html> 