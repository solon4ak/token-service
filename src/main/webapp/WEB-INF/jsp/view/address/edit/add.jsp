<%--@elvariable id="addressForm" type="ru.tokens.site.controller.AddressController.AddressForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Add Address" bodyTitle="Token owner Address">  
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <form:form method="post" modelAttribute="addressForm">
        <fieldset>
            <legend>Reside Address</legend>
            <form:label path="country">Country:</form:label>
            <form:input path="country"/><br />
            <form:label path="region">Region:</form:label>
            <form:input path="region"/><br />
            <form:label path="city">City:</form:label>
            <form:input path="city"/><br />
            <form:label path="street">Street:</form:label>
            <form:input path="street"/><br />
            <form:label path="building">Building:</form:label>
            <form:input path="building"/><br />
            <form:label path="apartment">Apartment:</form:label>
            <form:input path="apartment"/><br />
            <hr />
            <input type="submit" value="Submit" />
        </fieldset>
                
    </form:form>    
</template:basic>