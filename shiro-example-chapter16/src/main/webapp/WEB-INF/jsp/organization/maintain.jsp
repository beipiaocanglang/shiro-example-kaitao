<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>组织结构 最右侧 节点详情页</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    </head>
    <body>
        <form:form id="form" method="post" commandName="organization">
            <form:hidden path="id"/>
            <form:hidden path="available"/>
            <form:hidden path="parentId"/>
            <form:hidden path="parentIds"/>

            <%--显示组织名称--%>
            <div class="form-group">
                <form:label path="name">名称：</form:label>
                <form:input path="name"/>
            </div>
            <%--拥有相对应权限的可以修改--%>
            <shiro:hasPermission name="organization:update">
                <form:button id="updateBtn">修改</form:button>
            </shiro:hasPermission>
            <%--拥有相对应权限的可以删除--%>
            <shiro:hasPermission name="organization:delete">
                <c:if test="${not organization.rootNode}">
                    <form:button id="deleteBtn">删除</form:button>
                </c:if>
            </shiro:hasPermission>
            <%--拥有相对应权限的可以添加子节点--%>
            <shiro:hasPermission name="organization:create">
                <form:button id="appendChildBtn">添加子节点</form:button>
            </shiro:hasPermission>
            <%--拥有相对应权限的可以移动子节点--%>
            <shiro:hasPermission name="organization:update">
                <%--如果不是父节点就可以移动--%>
                <c:if test="${not organization.rootNode}">
                    <form:button id="moveBtn">移动节点</form:button>
                </c:if>
            </shiro:hasPermission>
        </form:form>

        <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script>
            $(function() {
                /*修改的点击事件*/
                $("#updateBtn").click(function() {
                    $("#form").attr("action", "${pageContext.request.contextPath}/organization/${organization.id}/update").submit();
                    return false;
                });
                /*删除的点击事件*/
                $("#deleteBtn").click(function() {
                    if(confirm("确认删除吗？")) {
                        $("#form").attr("action", "${pageContext.request.contextPath}/organization/${organization.id}/delete").submit();
                    }
                    return false;
                });
                /*添加子节点的点击事件*/
                $("#appendChildBtn").click(function() {
                    location.href="${pageContext.request.contextPath}/organization/${organization.id}/appendChild";
                    return false;
                });
                /*移动节点的点击事件*/
                $("#moveBtn").click(function() {
                    location.href="${pageContext.request.contextPath}/organization/${organization.id}/move";
                    return false;
                });
            });
        </script>
    </body>
</html>