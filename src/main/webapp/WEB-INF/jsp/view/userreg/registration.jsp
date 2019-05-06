<%--@elvariable id="userRegistrationFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="userRegistrationForm" type="ru.tokens.site.controller.UserRegistrationController.UserRegistrationForm"--%>
<template:basic htmlTitle="User registration" bodyTitle="New user registration">
    <c:if test="${userRegistrationFailed}">
        <b><c:out value="${message}" /></b>
        <br /><br />
    </c:if>
    <form:form method="post" modelAttribute="userRegistrationForm">
        <fieldset>
            <label>User data</label>
            <form:label path="lastName">Last name:</form:label>
            <form:input path="lastName" />
            <form:label path="firstName">First name:</form:label>
            <form:input path="firstName" />
            <form:label path="middleName">Middle name:</form:label>
            <form:input path="middleName" /> 
            <form:label path="birthDate">Birth date:</form:label>
            <form:input path="birthDate" />
            <form:label path="email">Email:</form:label>
            <form:input path="email" />     
            <form:label path="password">Password:</form:label>
            <form:input path="password" readonly="true" /> 
            <form:label path="phoneNumber">Phone number:</form:label>
            <form:input path="phoneNumber" /> 
        </fieldset>        
        <input type="submit" value="Register" />
    </form:form>
</template:basic>
