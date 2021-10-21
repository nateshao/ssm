<%@ page import="com.nateshao.vo.User" %><%--
  Created by IntelliJ IDEA.
  User: 邵桐杰
  Date: 2021/10/21
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>main</title>
</head>
<body>
<h1>main</h1>
<%
    User user = (User) session.getAttribute("u");
%>
Welcome <%= user.getName()%>

</body>
</html>
