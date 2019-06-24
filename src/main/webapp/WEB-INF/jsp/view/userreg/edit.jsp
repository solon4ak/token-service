<%--@elvariable id="userRegistrationForm" type="ru.tokens.site.controller.UserRegistrationController.UserRegistrationForm"--%>
<template:basic_bs_two_col htmlTitle="Edit user" bodyTitle="Редактирование данных пользователя">

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link disabled" href="<c:url value="/user/view" />">
                Пользователь
            </a>
            <a class="nav-link" href="<c:url value="/token/user/postaddress/view" />">
                Почтовый адрес
            </a>
            <a class="nav-link" href="<c:url value="/user/order/list" />">
                Заказы
            </a>
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Жетон
            </a> 
        </nav>
    </jsp:attribute>


    <jsp:body>
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
        <h5>Пользователь: <c:out value="${user.lastName}, ${user.firstName}" /></h5>
        <h5>E-mail: <c:out value="${user.userEmailAddress}" /></h5>
        <hr />
        <form:form method="post" modelAttribute="userRegistrationForm">
            <div class="form-group">
                <div class="form-group row">
                    <form:label path="lastName" class="col-sm-3 col-form-label">
                        Фамилия
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="lastName" type="text" class="form-control" 
                                    placeholder="Last name" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="firstName" class="col-sm-3 col-form-label">
                        Имя
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="firstName" type="text" class="form-control" 
                                    placeholder="First name" required="true" />
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="middleName" class="col-sm-3 col-form-label">
                        Отчество
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="middleName" type="text" class="form-control" 
                                    placeholder="Middle name" />
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="birthDate" class="col-sm-3 col-form-label">
                        Дата рождения
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="birthDate" type="text" class="form-control" 
                                    id="birthDate" aria-describedby="bdHelp" 
                                    placeholder="23.11.1997" required="true" />
                        <small id="bdHelp" class="form-text text-muted">
                            Формат: dd.mm.yyyy (29.01.2001).
                        </small>
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="phoneNumber" class="col-sm-3 col-form-label">
                        Телефон
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="phoneNumber" type="text" class="form-control" 
                                    id="phoneNumber" aria-describedby="pnHelp" 
                                    placeholder="+7 XXX XXX XX XX" required="true" />
                        <small id="pnHelp" class="form-text text-muted">
                            Формат: +7 499 999 99 99
                        </small>
                    </div>
                </div>                
                <div class="form-group row">
                    <form:label path="password" class="col-sm-3 col-form-label">
                        Пароль
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="password" type="text" class="form-control" 
                                    id="password" aria-describedby="passHelp"
                                    readonly="true" />
                        <small id="passHelp" class="form-text text-muted">
                            Создается системой.
                        </small>
                    </div>                
                </div>
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">

                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Подтвердить</button>
                    </div>                
                </div>

            </form:form>
        </jsp:body>
    </template:basic_bs_two_col>
