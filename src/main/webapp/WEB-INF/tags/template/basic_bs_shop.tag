<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ attribute name="leftColumnContent" fragment="true" required="false" %>
<%@ attribute name="authContent" fragment="true" required="true" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<template:main_bs htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>

    <jsp:attribute name="navigationContent">
        <nav class="my-2 my-md-0 mr-md-3">
            <a class="p-2 text-dark" href="/tkn">Features</a>
            <a class="p-2 text-dark" href="#">Shop</a>
            <a class="p-2 text-dark" href="#">Support</a>
            <a class="p-2 text-dark" href="#">Pricing</a>
            <jsp:invoke fragment="extraNavigationContent" />
        </nav>
        <jsp:invoke fragment="authContent" />
    </jsp:attribute>
    <jsp:body>
        <div class="row justify-content-lg-center">
            <div class="col-2 border-right">
                <jsp:invoke fragment="leftColumnContent" />
            </div>
            <div class="col-10">
                <jsp:doBody />
            </div>
        </div>
    </jsp:body>
</template:main_bs>
