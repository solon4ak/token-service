<%--@elvariable id="entryForm" type="ru.tokens.site.controller.EntryController.EntryForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Adding Medical Entry" bodyTitle="Add Medical Entry">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <form:form method="post" enctype="multipart/form-data" modelAttribute="entryForm">
        <fieldset>
            <legend>Add entry</legend>
            <form:label path="subject">Subject</form:label>
            <form:input path="subject"/><br />
            <form:label path="body">Body</form:label>
            <form:textarea path="body" rows="5" cols="30" /><br />
        </fieldset>
        <fieldset>
            <legend>Entry attachments</legend>
            <label>Up to 5 Mb/file</label>
            <input type="file" name="attachments" multiple="multiple"/><br /><br />
        </fieldset>
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>