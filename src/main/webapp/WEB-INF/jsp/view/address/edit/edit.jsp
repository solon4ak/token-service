<%--@elvariable id="addressForm" type="ru.tokens.site.controller.AddressController.AddressForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Edit Address" bodyTitle="Token owner Address">
    <form:form method="post" modelAttribute="addressForm">
        <fieldset>
            <form:label path="country">Country:</form:label>
            <form:input path="country"/><br />
            <form:label path="city">City:</form:label>
            <form:input path="city"/><br />
            <form:label path="street">Street:</form:label>
            <form:input path="street"/><br />
            <form:label path="building">Building:</form:label>
            <form:input path="building"/><br />
            <form:label path="apartment">Apartment:</form:label>
            <form:input path="apartment"/><br />
            <input type="submit" value="Submit" />
        </fieldset>
    </form:form>    
</template:basic>