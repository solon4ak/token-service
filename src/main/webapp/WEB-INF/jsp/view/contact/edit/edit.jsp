<%--@elvariable id="contactForm" type="ru.tokens.site.controller.ContactController.ContactForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Edit Contact" bodyTitle="Edit token owner contact">
    <hr />
    <form:form method="post" modelAttribute="contactForm">
        <form:label path="firstName">First name:</form:label>
        <form:input path="firstName"/>
        <form:label path="lastName">Last name:</form:label>
        <form:input path="lastName"/>
        <form:label path="phoneNumber">Phone number:</form:label>
        <form:input path="phoneNumber"/>
        <form:label path="email">E-mail:</form:label>
        <form:input path="email"/>
        <hr />
        <input type="submit" value="Submit" />
    </form:form>    
</template:basic>