<%--@elvariable id="userRegistrationForm" type="ru.tokens.site.controller.UserRegistrationController.UserRegistrationForm"--%>
<template:basic htmlTitle="Edit user" bodyTitle="Edit user">
    User: <c:out value="${user.lastName}, ${user.firstName}" /><br /><br />
    E-mail: <c:out value="${user.userEmailAddress}" />
    <hr />
    <form:form method="post" modelAttribute="userRegistrationForm">
        <fieldset>
            <legend>User data</legend>
            <form:label path="firstName">First name:</form:label>
            <form:input path="firstName" />
            <form:label path="middleName">Middle name:</form:label>
            <form:input path="middleName" /> 
            <form:label path="lastName">Last name:</form:label>
            <form:input path="lastName" />
            <form:label path="birthDate">Birth date:</form:label>
            <form:input path="birthDate" /> 
            <form:label path="phoneNumber">Phone number:</form:label>
            <form:input path="phoneNumber" />
            <form:label path="password">Password:</form:label>
            <form:input path="password" readonly="true" /> 
        </fieldset>        
        <input type="submit" value="Submit" />
    </form:form>
</template:basic>
