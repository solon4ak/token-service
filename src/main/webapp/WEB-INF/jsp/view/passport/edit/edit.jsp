<%--@elvariable id="passportForm" type="ru.tokens.site.controller.PassportController.PassportForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Edit Passport" bodyTitle="Edit token owner Passport">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    Birth Date: <wrox:formatDate value="${token.user.birthDate}" />
    <hr />
    <form:form method="post" modelAttribute="passportForm">
        <fieldset>
            <legend>Passport</legend>
            <form:label path="ser">Серия:</form:label>
            <form:input path="ser"/><br />
            <form:label path="num">Номер паспорта:</form:label>
            <form:input path="num"/><br />
            <form:label path="issueDep">Кем выдан:</form:label>
            <form:input path="issueDep"/><br />
            <form:label path="issueDepCode">Код подразделения:</form:label>
            <form:input path="issueDepCode"/><br />
            <form:label path="dated">Дата выдачи в формате (23.10.1995):</form:label>
            <form:input path="dated"/><br />
            <hr />
            <input type="submit" value="Submit" />
        </fieldset>                
    </form:form>  
</template:basic>