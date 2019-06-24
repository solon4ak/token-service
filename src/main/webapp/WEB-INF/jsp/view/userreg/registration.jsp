<%--@elvariable id="userRegistrationFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="userRegistrationForm" type="ru.tokens.site.controller.UserRegistrationController.UserRegistrationForm"--%>
<template:basic_bs_one_col htmlTitle="User registration" bodyTitle="Регистрация пользователя">

    <jsp:body>
        <c:if test="${userRegistrationFailed}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <c:out value="${message}" />
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
        <form:form method="post" modelAttribute="userRegistrationForm">
            <div class="form-group">
                <!--User-->
                <h4>Пользователь</h4>
                <hr />
                <div class="form-group row">
                    <form:label path="lastName" class="col-sm-3 col-form-label">
                        Фамилия
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="lastName" type="text" class="form-control" 
                                    placeholder="Фамилия" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="firstName" class="col-sm-3 col-form-label">
                        Имя
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="firstName" type="text" class="form-control" 
                                    placeholder="Имя" required="true" />
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="middleName" class="col-sm-3 col-form-label">
                        Отчество
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="middleName" type="text" class="form-control" 
                                    placeholder="Отчество" />
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
                            Формат даты: dd.mm.yyyy (29.01.2001)
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
                    <form:label path="email" class="col-sm-3 col-form-label">
                        E-mail
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="email" type="email" class="form-control" 
                                    id="exampleInputEmail1" aria-describedby="emailHelp" 
                                    placeholder="Электронная почта" required="true" />
                        <small id="emailHelp" class="form-text text-muted">
                            Используется в качестве логина.
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
                            Создается системой. Будет отправлен почтой после регистрации.
                        </small>
                    </div>                
                </div>

                <!--Address-->
                <h4>Почтовый адрес</h4>
                <hr />
                <div class="form-group row">
                    <form:label path="zipCode" class="col-sm-3 col-form-label">
                        Индекс
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="zipCode" type="text" class="form-control" 
                                    placeholder="352800" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="country" class="col-sm-3 col-form-label">
                        Страна
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="country" type="text" class="form-control" 
                                    placeholder="Россия" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="region" class="col-sm-3 col-form-label">
                        Регион (край, область)
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="region" type="text" class="form-control" 
                                    placeholder="Краснодарский край" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="city" class="col-sm-3 col-form-label">
                        Город
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="city" type="text" class="form-control" 
                                    placeholder="Туапсе" required="true" />
                    </div>                
                </div>                
                <div class="form-group row">
                    <form:label path="street" class="col-sm-3 col-form-label">
                        Улица (шоссе, проспект)
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="street" type="text" class="form-control" 
                                    placeholder="ул. Ленина" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="building" class="col-sm-3 col-form-label">
                        Дом
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="building" type="text" class="form-control" 
                                    placeholder="8" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="apartment" class="col-sm-3 col-form-label">
                        Квартира
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="apartment" type="text" class="form-control" 
                                    placeholder="3" required="true" />
                    </div>                
                </div>
                <hr />
                <!--Submit-->
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">
                        &nbsp;
                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Зарегистрировать</button>
                    </div>                
                </div>
            </form:form>
        </jsp:body>
    </template:basic_bs_one_col>
