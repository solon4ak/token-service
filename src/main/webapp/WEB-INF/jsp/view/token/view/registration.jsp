<%--@elvariable id="registrationFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="tokenRegistrationForm" type="ru.tokens.site.controller.TokenRegistrationController.TokenRegistrationForm"--%>
<template:basic_bs_two_col htmlTitle="Token registration" bodyTitle="Регистрация жетона">

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
            </a>
            <a class="nav-link" href="<c:url value="/token/user/postaddress/view" />">
                Почтовый адрес
            </a>
            <a class="nav-link disabled" href="<c:url value="/token/user/view" />">
                Жетон
            </a> 
        </nav>
    </jsp:attribute>
    

    <jsp:body>
        <c:if test="${registrationFailed}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                ID жетона или код активации введены неверно. Повторите ввод.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${message ne null}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong>
                    <c:out value="${message}" />
                </strong>                    
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <form:form method="post" modelAttribute="tokenRegistrationForm">
            <div class="form-group">
                <div class="form-group row">
                    <form:label path="uuid" class="col-sm-3 col-form-label">
                        ID жетона
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="uuid" type="text" class="form-control" 
                                    aria-describedby="tknHelp" 
                                    placeholder="57Kkq2mjBVU1ql3tVH2u75Iuq3QQL10y4JoT" required="true" size="36" />
                        <small id="tknHelp" class="form-text text-muted">
                            36 символов!
                        </small>
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="activationCode" class="col-sm-3 col-form-label">
                        Строка активации
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="activationCode" type="text" class="form-control" 
                                    aria-describedby="acHelp" 
                                    placeholder="Ztg50L@M@x8@" required="true" size="12" />
                        <small id="acHelp" class="form-text text-muted">
                            12 символов!
                        </small>
                    </div>                
                </div>
                <!-- Submit -->
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">
                        &nbsp;
                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Зарегистрировать</button>
                    </div>                
                </div>
            </div>            
        </form:form>
    </jsp:body>

</template:basic_bs_two_col>
