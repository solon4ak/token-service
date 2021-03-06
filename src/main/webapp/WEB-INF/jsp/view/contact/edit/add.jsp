<%--@elvariable id="contactForm" type="ru.tokens.site.controller.ContactController.ContactForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Add Contact" bodyTitle="Token owner contact">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <form:form method="post" modelAttribute="contactForm">
        <fieldset>
            <legend>New Contact</legend>
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
        </fieldset>
    </form:form>    
</template:basic>