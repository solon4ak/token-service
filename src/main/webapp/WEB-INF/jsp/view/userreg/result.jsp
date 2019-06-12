<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="E-mail confirmation"
                bodyTitle="User e-mail confirmation.">
    
    <jsp:body>
        <div class="alert alert-success" role="alert">
        <h4 class="alert-heading">Well done!</h4>
        <p class="mb-0">
            <c:out value="${message}"/>
        </p>
    </div>
    </jsp:body>    
            
</template:basic_bs_one_col>