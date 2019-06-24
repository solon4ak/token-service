<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="ru.tokens.site.AuthenticationController.Form"--%>
<template:basic_bs_two_col htmlTitle="Log In" bodyTitle="Вход в систему / регистрация">
    
    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link btn btn-primary" href="<c:url value="/user/register" />">Регистрация</a>
            <!--<a class="nav-link" href="<c:url value="/user/passwordreset" />">Reset password</a>-->
            <!--<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>-->
        </nav>
    </jsp:attribute>
    <jsp:body>
        <h1 class="h3 mb-3 font-weight-normal">Авторизуйтесь для входа в систему</h1>
        <c:if test="${loginFailed}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                Вы ввели неверные данные или адрес электронной почты не подтвержден.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <form:form method="post" modelAttribute="loginForm">
            <div class="form-group">
                <form:label path="email">Электронная почта</form:label>
                <form:input path="email" type="email" class="form-control" 
                            id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Ваш адрес эл. почты" required="true" />
                <!--<small id="emailHelp" class="form-text text-muted">(строго между нами)</small>-->
            </div>
            <div class="form-group">
                <form:label path="password">Пароль</form:label>
                <form:input path="password" type="password" class="form-control" placeholder="Ваш пароль" required="true" />
            </div>
            <button class="btn btn-primary" type="submit">Войти</button>
            <!--<input type="submit" value="Log In" />-->
        </form:form>
        <hr />
        <a class="btn btn-link" href="<c:url value="/user/passwordreset" />">Сбросить пароль</a>

    </jsp:body>
</template:basic_bs_two_col>
