<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>
    <jsp:attribute name="authContent">
        <a href="<c:url value="/logout" />">Log Out</a>
    </jsp:attribute>
    <jsp:attribute name="navigationContent">    
        <b>User</b><br /><br />
        <a href="<c:url value="/session/list" />">List Sessions</a><br />
        <hr />
        <b>Shop</b><br /><br />
        <a href="<c:url value="/admin/shop/category/list" />">Categories</a><br /> 
        <a href="<c:url value="/admin/shop/product/list" />">Products</a><br />  
        <hr />
        <b>Log out</b><br /><br />
        <a href="<c:url value="/logout" />">Log Out</a><br />
        <jsp:invoke fragment="extraNavigationContent" />
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main>
