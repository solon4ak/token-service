<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ attribute name="extraBodyContent" fragment="true" required="false" %>
<%--<%@ attribute name="leftColumnContent" fragment="true" required="true" %>--%>
<%@ attribute name="rightColumnContent" fragment="true" required="true" %>
<%--<%@ attribute name="authContent" fragment="true" required="true" %>--%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<template:main_bs htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">

    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>

    <jsp:body>
        <div class="row justify-content-lg-center">
            
            <jsp:include page="/WEB-INF/jsp/left_nav.jspf" />
            <div class="col-8 border-right">                
                <jsp:doBody />
            </div>
            <div class="col-2">
                <jsp:invoke fragment="rightColumnContent" />
            </div>
        </div>
    </jsp:body>
</template:main_bs>
