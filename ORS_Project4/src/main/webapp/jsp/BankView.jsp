<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.BankCtl"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <jsp:useBean id="bean" class="com.rays.pro4.Bean.BankBean" scope="request"></jsp:useBean>
   <%@ include file="Header.jsp"%>
   
   <center>

    <form action="<%=ORSView.BANK_CTL%>" method="post">
    <%--  <form action="<%=ORSView.Order_CTL%>" method="post"> --%>

        <%
            List l = (List) request.getAttribute("roleList");
        %>

        
    <div align="center">    
            <h1>
 				
           		<% if(bean != null && bean.getId() > 0) { %>
            <tr><th><font size="5px"> Update Order </font>  </th></tr>
            	<%}else{%>
			<tr><th><font size="5px"> Order Add</font>  </th></tr>            
            	<%}%>
            </h1>
   
            <h3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
	       
</div>
            <input type="hidden" name="cid" value="<%=bean.getId()%>">
          

            <table>
                <tr>
                    <th align="left">ShopName<span style="color: red">*</span> :</th>
                    <td><input type="text" name="cName" placeholder="Enter Shop Name" size="25"  value="<%=DataUtility.getStringData(bean.getC_Name())%>"></td>
                    <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("cName", request)%></font></td> 
                    
                </tr>
    
    <tr><th style="padding: 3px"></th></tr>          
              
              <tr>
                    <th align="left">Order<span style="color: red">*</span> :</th>
                    <td><input type="text" name="accu" placeholder="Enter order name" size="25" value="<%=DataUtility.getStringData(bean.getAccount())%>"></td>
                     <td style="position: fixed"><font  color="red"> <%=ServletUtility.getErrorMessage("accu", request)%></font></td>
                </tr>
                 <tr>
                    <th align="left">Price<span style="color: red">*</span> :</th>
                    <td><input type="text" name="price" placeholder="Enter price" size="25" value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>
                     <td style="position: fixed"><font  color="red"> <%=ServletUtility.getErrorMessage("price", request)%></font></td>
                </tr>
   
    <tr><th style="padding: 3px"></th></tr>  
            

              
                <tr ><th></th>
                <%
                if(bean.getId()>0){
                %>
                <td colspan="2" >
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=BankCtl.OP_UPDATE%>">
                      &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=BankCtl.OP_CANCEL%>"></td>
                    
                  
                
                <% }else{%>
                
                <td colspan="2" > 
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=BankCtl.OP_SAVE%>">
                    
                   
                    &nbsp;  &nbsp;
                    <%-- <input type="submit" name="operation" value="<%=BankCtl.OP_RESET%>"> --%></td>
                
                <% } %>
                </tr>
            </table>
    </form>
    </center>

    <%@ include file="Footer.jsp"%>
	
</body>
</html>