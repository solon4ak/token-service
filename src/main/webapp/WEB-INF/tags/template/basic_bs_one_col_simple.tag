<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<template:main_bs_simple htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>

    <jsp:attribute name="navigationContent">
        <nav class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 
             bg-white border-bottom shadow-sm navbar navbar-expand-md fixed-top">
            <a class="navbar-brand p-2 text-dark" href="/tkn">Tag4Life</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link p-2 text-dark" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link p-2 text-dark" href="#">Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link p-2 text-dark dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
                        <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <a class="dropdown-item p-2 text-dark" href="#">Action</a>
                            <a class="dropdown-item p-2 text-dark" href="#">Another action</a>
                            <a class="dropdown-item p-2 text-dark" href="#">Something else here</a>
                        </div>
                    </li>
                </ul>                
            </div>
        </nav>
    </jsp:attribute>
    <jsp:body>
        <div class="row justify-content-lg-center">
            <div class="col-10">
                <jsp:doBody />
            </div>
        </div>
    </jsp:body>
</template:main_bs_simple>
