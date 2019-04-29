<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="ru.tokens.site.AuthenticationController.Form"--%>
<template:basic htmlTitle="Log In" bodyTitle="Log In">
    You must log in to access to your personal page.<br /><br />
    <c:if test="${loginFailed}">
        <b>The username and password you entered are not correct. Please try
            again.</b><br /><br />
    </c:if>
    <form:form method="post" modelAttribute="loginForm">
        <form:label path="email">E-mail:</form:label>
        <form:input path="email" /><br />
        <form:label path="password">Password</form:label>
        <form:password path="password" /><br /><br />
        <input type="submit" value="Log In" />
    </form:form>
</template:basic>
