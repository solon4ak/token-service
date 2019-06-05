<%--@elvariable id="categories" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop htmlTitle="Shop"
                        bodyTitle="Shop">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <c:forEach items="${categories}" var="category">
                <a class="nav-link" href="<c:url value="/admin/shop/category/view/${category.categoryId}" />">
                    <c:out value="${category.categoryName}" />
                </a>
            </c:forEach>
            <c:choose>
                <c:when test="${user.passport == null}">
                    <a class="nav-link" href="<c:url value="/token/user/passport/add" />">
                        Add
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/passport/edit" />">
                        Edit
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/passport/delete" />">
                        Delete
                    </a>
                </c:otherwise>
            </c:choose>  
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">

        </div>

    </jsp:body>
</template:basic_bs_shop>
