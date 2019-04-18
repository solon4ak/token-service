<%--@elvariable id="imgForm" type="ru.tokens.site.controller.ImageController.ImageForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Add User Photo" bodyTitle="Token owner Photo">      
    <form:form method="post" enctype="multipart/form-data" modelAttribute="imgForm">
        <fieldset>
            <fieldset>
                <legend>User picture</legend>  
                <label>Up to 5 Mb</label>
                <input type="file" name="file" />            
            </fieldset>
            <hr />
            <input type="submit" value="Submit" />
        </form:form>    
    </template:basic>