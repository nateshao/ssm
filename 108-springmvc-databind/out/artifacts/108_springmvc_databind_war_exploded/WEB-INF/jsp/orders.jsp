<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单查询</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/findOrdersWithUser"
		                                                           method="post">
				<%--  参数是包装类基本属性，则直接用属性名  --%>
		订单编号：<input type="text" name="ordersId" /><br />
				<%-- 参数是包装类中POJO类的子属性，则必须用【对象.属性】--%>
		所属用户：<input type="text" name="user.username" /><br /> 
		          <input type="submit" value="查询" />
	</form>
</body>
</html>
