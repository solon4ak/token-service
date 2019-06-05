<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ attribute name="extraBodyContent" fragment="true" required="false" %>
<%@ attribute name="rightColumnContent" fragment="true" required="true" %>
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
                <nav class="nav flex-column">
                    <a class="nav-link" href="<c:url value="/token/user/view" />">
                        Token
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="nav-link" href="<c:url value="/token/user/picture/view" />">
                        Picture
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/birthcert/view" />">
                        Birth cert
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/passport/view" />">
                        Passport
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/address/view" />">
                        Address
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/contact/list" />">
                        Contacts
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="nav-link" href="<c:url value="/token/user/med/view" />">
                        Medical data
                    </a>           
                    <a class="nav-link" href="<c:url value="/token/user/csdevent/list" />">
                        Events
                    </a>            
                </nav>
            </div>
            <div class="col-8 border-right">                
                <jsp:doBody />
            </div>
            <div class="col-2">
                <jsp:invoke fragment="rightColumnContent" />
            </div>
        </div>
    </jsp:body>
</template:main_bs>
