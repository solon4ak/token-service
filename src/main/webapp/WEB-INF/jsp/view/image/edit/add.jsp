<%--@elvariable id="imgForm" type="ru.tokens.site.controller.ImageController.ImageForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Add User Photo" bodyTitle="Token owner Photo">    
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    
    <form:form method="post" enctype="multipart/form-data" modelAttribute="imgForm">
        <fieldset>
            <legend>User picture</legend>  
            <label>Up to 5 Mb</label>
            <input type="file" name="file" />            
        </fieldset>
        <hr />
        <input type="submit" value="Submit" />
    </form:form>    
</template:basic>