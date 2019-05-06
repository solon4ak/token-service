<%--@elvariable id="wrongEmail" type="java.lang.Boolean"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="passwordResetForm" type="ru.tokens.site.controller.PasswordResetController.PasswordResetForm"--%>
<template:basic htmlTitle="Password reset" bodyTitle="Password reset">
    <c:if test="${wrongEmail}">
        <b><c:out value="${message}" /></b>
        <br /><br />
    </c:if>
    <form:form method="post" modelAttribute="passwordResetForm">
        <fieldset>
            <label>Password reset</label>
            <form:label path="email">User email:</form:label>
            <form:input path="email" />            
        </fieldset>        
        <input type="submit" value="Confirm" />
    </form:form>
</template:basic>
