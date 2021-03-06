<%--@elvariable id="certForm" type="ru.tokens.site.controller.BirthCertificateController.BthCertForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Add Birth Certificate" bodyTitle="Token Birth Certificate">  
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    Birth Date: <wrox:formatDate value="${token.user.birthDate}" />
    <hr />
    <form:form method="post" modelAttribute="certForm">
        <fieldset>
            <legend>Свидетельство о рождении</legend>
            <form:label path="series">Серия:</form:label>
            <form:input path="series"/><br />
            <form:label path="number">Номер свидетельства:</form:label>
            <form:input path="number"/><br />
            <form:label path="issueDepartment">Кем выдан:</form:label>
            <form:input path="issueDepartment"/><br />
            <form:label path="issueDate">Дата выдачи в формате (23.10.1995):</form:label>
            <form:input path="issueDate"/><br />
            <hr />
            <input type="submit" value="Submit" />
        </fieldset>                
    </form:form>    
</template:basic>