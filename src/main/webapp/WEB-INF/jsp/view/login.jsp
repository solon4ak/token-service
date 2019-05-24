<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="ru.tokens.site.AuthenticationController.Form"--%>
<template:basic htmlTitle="Log In" bodyTitle="Log In">
    You must log in to access to your personal page.<br /><br />
    <c:if test="${loginFailed}">
        <b>User credentials doesn't correspond or user e-mail doesn't confirmed.</b><br /><br />
    </c:if>
    <form:form method="post" modelAttribute="loginForm">
        <fieldset>
            <legend>User authentication form</legend>
            <form:label path="email">E-mail:</form:label>
            <form:input path="email" /><br />
            <form:label path="password">Password</form:label>
            <form:password path="password" /><br /><br />
        </fieldset>
        <input type="submit" value="Log In" />
    </form:form>
    <hr />
    <a href="<c:url value="/user/passwordreset" />">Reset password</a>
</template:basic>
