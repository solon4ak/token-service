<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="E-mail confirmation"
                           bodyTitle="Подтверждение адреса электронной почты">

    <jsp:body>
        <div class="alert alert-success" role="alert">
            <!--<h4 class="alert-heading">Well done!</h4>-->
            <div class="mb-0 h4 lead text-center">
                <c:out value="${message}" escapeXml="false"/>
            </div>
        </div>
    </jsp:body>    

</template:basic_bs_one_col>