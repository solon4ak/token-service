<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="ru.tokens.site.AuthenticationController.Form"--%>
<template:basic_bs_two_col htmlTitle="Log In" bodyTitle="Log In">
    
    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link btn btn-primary" href="<c:url value="/user/register" />">Registration</a>
            <!--<a class="nav-link" href="<c:url value="/user/passwordreset" />">Reset password</a>-->
            <!--<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>-->
        </nav>
    </jsp:attribute>
    <jsp:body>
        <h1 class="h3 mb-3 font-weight-normal">You must log in to access to your personal page</h1>
        <c:if test="${loginFailed}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                User credentials doesn't correspond or user e-mail doesn't confirmed.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <form:form method="post" modelAttribute="loginForm">
            <div class="form-group">
                <form:label path="email">E-mail</form:label>
                <form:input path="email" type="email" class="form-control" 
                            id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" required="true" />
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <form:label path="password">Password</form:label>
                <form:input path="password" type="password" class="form-control" placeholder="Password" required="true" />
            </div>
            <button class="btn btn-primary" type="submit">Log In</button>
            <!--<input type="submit" value="Log In" />-->
        </form:form>
        <hr />
        <a class="btn btn-link" href="<c:url value="/user/passwordreset" />">Reset password</a>

    </jsp:body>
</template:basic_bs_two_col>
