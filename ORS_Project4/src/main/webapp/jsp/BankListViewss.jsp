<%@page import="com.rays.pro4.controller.BankListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="com.rays.pro4.Bean.BankBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="bean" class="com.rays.pro4.Bean.BankBean"
		scope="request"></jsp:useBean>
       <%@include file = "Header.jsp" %>


	<form action="<%=ORSView.BANK_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Order List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				

				List ulist = (List) request.getAttribute("accu");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<BankBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				 <tr style="background: orange"> </tr>
					<th>Delete</th> 

					<th>S.No.</th>
					<th>ShopName</th>
					<th>Order By</th>
					<th>Price</th>
					
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
							
				%>


				<tr align="center">
					 <td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td> 
						
					<td><%=index++%></td>
					<td><%=bean.getC_Name()%></td>
					<td><%=bean.getAccount()%></td>
					<td><%=bean.getPrice() %>
					<td> <a href="BankCtl?cid=<%=bean.getId()%>" >edit</td>
					
						
				</tr>
				<%
					}
				%>
			</table>

			<table >
			
				<tr>
					<center>

					 <td><input type="submit" name="operation"
						value="<%=BankListCtl.OP_DELETE%>"></td>
					
 					</center>

				</tr>
				
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=BankListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			
				
				
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>
	<%@include file="Footer.jsp"%>

</body>
</html>