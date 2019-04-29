<%--@elvariable id="userForm" type="ru.tokens.site.controller.UserDataController.UserForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="tokenId" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Create a Token" bodyTitle="Create a Token">
    <c:out value="Token ID: ${token.uuidString}"/><br /><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <form:form method="post" modelAttribute="userForm">
        <fieldset>
            <legend>User data</legend>
            <form:label path="firstName">First Name:*</form:label>
            <form:input path="firstName"/>
            <form:label path="middleName">Middle Name:</form:label>
            <form:input path="middleName"/>
            <form:label path="lastName">Last Name:*</form:label>
            <form:input path="lastName"/>
            <form:label path="birthDate">Birth Date: (format 27.11.2001)*</form:label>
            <form:input path="birthDate"/>  
        </fieldset>
        <hr />
        <input type="submit" value="Submit" />
    </form:form>    
</template:basic>
