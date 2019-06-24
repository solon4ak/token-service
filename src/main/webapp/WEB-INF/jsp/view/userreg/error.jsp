<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="User registration error"
                           bodyTitle="Ошибка регистрации">
     
    <jsp:body>
        <div class="alert alert-warning" role="alert">
            <h4 class="alert-heading">Ошибка</h4>
            <p class="mb-0">
                <c:out value="${message}"/>
            </p>
        </div>
    </jsp:body>  

</template:basic_bs_one_col>