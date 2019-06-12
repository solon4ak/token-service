<%--@elvariable id="uuidString" type="java.lang.String"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="Error for token: ${uuidString}"
                bodyTitle="Error for token: '${uuidString}'">
    
    <jsp:body>
        <c:if test="${message != null}">
            <div class="alert alert-warning" role="alert">
                <h4 class="alert-heading">Error</h4>
                <p class="mb-0">
                    <c:out value="${message}"/>
                </p>
            </div>
        </c:if>

    </jsp:body>
    
</template:basic_bs_one_col>