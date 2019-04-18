<%--@elvariable id="registrationFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="tokenRegistrationForm" type="ru.tokens.site.controller.TokenRegistrationController.TokenRegistrationForm"--%>
<template:basic htmlTitle="Token registration" bodyTitle="Token registration">
    Register your token please.<br /><br />
    <c:if test="${registrationFailed}">
        <b>The User ID or Activation Code you entered are not correct. Please try
            again.</b><br /><br />
        </c:if>
        <form:form method="post" modelAttribute="tokenRegistrationForm">
        <fieldset>
            <form:label path="uuid">User Id:</form:label>
            <form:input path="uuid" /><br />            
            <form:label path="activationCode">Activation Code:</form:label>
            <form:input path="activationCode" /><br /><br />            
        </fieldset>
        <footer>
            <input type="submit" value="Register" />
        </footer>
    </form:form>
</template:basic>
