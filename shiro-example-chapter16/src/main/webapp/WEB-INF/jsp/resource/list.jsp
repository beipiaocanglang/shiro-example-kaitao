<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>资源列表页</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/jquery-treetable/stylesheets/jquery.treetable.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/jquery-treetable/stylesheets/jquery.treetable.theme.default.css">
        <style>
            #table th, #table td {
                font-size: 14px;
                padding : 8px;
            }

        </style>
    </head>
    <body>
        <%--如果有错误信息 显示错误信息--%>
        <c:if test="${not empty msg}">
            <div class="message">${msg}</div>
        </c:if>

        <table id="table">
            <thead>
                <tr>
                    <th>名称</th>
                    <th>类型</th>
                    <th>URL路径</th>
                    <th>权限字符串</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <%--循环 资源集合--%>
                <c:forEach items="${resourceList}" var="resource">
                    <%--不是根节点时--%>
                    <tr data-tt-id='${resource.id}' <c:if test="${not resource.rootNode}">data-tt-parent-id='${resource.parentId}'</c:if>>
                        <td>${resource.name}</td>
                        <td>${resource.type.info}</td>
                        <td>${resource.url}</td>
                        <td>${resource.permission}</td>
                        <td>
                            <%--有对应的权限--%>
                            <shiro:hasPermission name="resource:create">
                                <%--当前资源不是按钮--%>
                                <c:if test="${resource.type ne 'button'}">
                                    <a href="${pageContext.request.contextPath}/resource/${resource.id}/appendChild">添加子节点</a>
                                </c:if>
                            </shiro:hasPermission>

                            <%--有对应的权限--%>
                            <shiro:hasPermission name="resource:update">
                                <a href="${pageContext.request.contextPath}/resource/${resource.id}/update">修改</a>
                            </shiro:hasPermission>

                            <%--不是根节点--%>
                            <c:if test="${not resource.rootNode}">
                                <%--有对应的权限--%>
                                <shiro:hasPermission name="resource:delete">
                                    <a class="deleteBtn" href="#" data-id="${resource.id}">删除</a>
                                </shiro:hasPermission>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/jquery-treetable/javascripts/src/jquery.treetable.js"></script>
        <script>
            $(function() {
                $("#table").treetable({ expandable: true }).treetable("expandNode", 1);
                /*删除按钮点击事件*/
                $(".deleteBtn").click(function() {
                    if(confirm("确认删除吗?")) {
                        location.href = "${pageContext.request.contextPath}/resource/"+$(this).data("id")+"/delete";
                    }
                });
            });
        </script>
    </body>
</html>