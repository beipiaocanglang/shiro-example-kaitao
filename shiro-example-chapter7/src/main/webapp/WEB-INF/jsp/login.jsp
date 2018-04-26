<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>登录</title>
        <style>.error {
            color: red;
        }</style>
    </head>
    <body>
        <%--获取登录失败时的错误信息--%>
        <div class="error">${error}</div>
        <form action="${pageContext.request.contextPath}/login" method="post">
            用户名：<input type="text" name="username"><br/>
            密码：<input type="password" name="password"><br/>
            <input type="submit" value="登录">
        </form>
    </body>
</html>