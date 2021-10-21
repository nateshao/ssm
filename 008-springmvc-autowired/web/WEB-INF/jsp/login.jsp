<%--
  Created by IntelliJ IDEA.
  User: 邵桐杰
  Date: 2021/10/21
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="/008_springmvc_autowired_war_exploded/login" method="post">
    <table border="1">
        <tr>
            <td>用户名</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="pwd"></td>
        </tr>

        <tr>
            <td colspan="2" style="text-align: center"><input type="submit" value="Login"></td>
        </tr>

    </table>

</form>

<div>User Input : name(${inputname},${inputpwd}).</div>
</body>
</html>

















