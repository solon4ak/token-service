<%--@elvariable id="addressForm" type="ru.tokens.site.controller.PostAddressController.PostAddressForm"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Edit Post Address" bodyTitle="User Post Address">
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <form:form method="post" modelAttribute="addressForm">
        <fieldset>
            <label>Post Address</label>
            <form:label path="zipCode">Zip code:</form:label>
            <form:input path="zipCode"/><br />
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
            <input type="submit" value="Submit" />
        </fieldset>
    </form:form>    
</template:basic>