<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>登录</title>
        <style>.error{color:red;}</style>
    </head>
    <body>
        <div class="error">${error}</div>
        <form action="" method="post">
            用户名：<input type="text" name="username" value="<shiro:principal/>"><br/><br/>
            密码：<input type="password" name="password"><br/><br/>

            <%--
                根据jcaptchaEbabled来显示验证码图片
                jcaptchaEbabled 在JCaptchaValidateFilter设置
            --%>
            <c:if test="${jcaptchaEbabled}">
                验证码：
                <input type="text" name="jcaptchaCode">
                <img class="jcaptcha-btn jcaptcha-img" src="${pageContext.request.contextPath}/jcaptcha.jpg" title="点击更换验证码">
                <a class="jcaptcha-btn" href="javascript:;">换一张</a>
                <br/><br/>
            </c:if>

            自动登录：<input type="checkbox" name="rememberMe" value="true"><br/>
            <hr>
            <input type="submit" value="登录">
        </form>
        <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script>
            $(function() {
                $(".jcaptcha-btn").click(function() {
                    $(".jcaptcha-img").attr("src", '${pageContext.request.contextPath}/jcaptcha.jpg?'+new Date().getTime());
                });
            });
        </script>
    </body>
</html>