<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,
common.auth.*"%><%
String path = request.getContextPath();
Auth model = AuthLogin.getLoginUser(request, response);
if(model == null)
{
	AuthLogin.logout(request);
	response.sendRedirect("login.html");
}
else
{
	String url = "login.html";//-1后台用户
	switch(model.getLogintype().intValue())
	{
		case 0:url = "loginEp.html";break;//0企业管理员
		case 1:url = "loginPerson.html";break;//1个人用户
	}
	AuthLogin.logout(request);
	response.sendRedirect(url);
}
%>