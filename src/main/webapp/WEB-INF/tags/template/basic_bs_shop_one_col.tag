<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%--<%@ attribute name="extraNavigationContent" fragment="true" required="true" %>--%>
<%--<%@ attribute name="leftColumnContent" fragment="true" required="true" %>--%>
<%--<%@ attribute name="authContent" fragment="true" required="true" %>--%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<template:main_bs htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>
    
    <jsp:attribute name="extraNavigationContent">
        <jsp:include page="/WEB-INF/jsp/cart.jspf" />
    </jsp:attribute>
    
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main_bs>
