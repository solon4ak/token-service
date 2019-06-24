<%--@elvariable id="addressForm" type="ru.tokens.site.controller.PostAddressController.PostAddressForm"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="Add Post Address" bodyTitle="Добавление почтового адреса">

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
            </a>
            <a class="nav-link disabled" href="<c:url value="/token/user/postaddress/view" />">
                Почтовый адрес
            </a>
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Жетон
            </a> 
        </nav>
    </jsp:attribute>
    
    <jsp:body>
        <h5><c:out value="E-mailS: ${user.userEmailAddress}"/></h5>
        <hr />
        <form:form method="post" modelAttribute="addressForm">
            <div class="form-group">
                <div class="form-group row">
                    <form:label path="zipCode" class="col-sm-3 col-form-label">
                        Почтовый индекс
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="zipCode" type="text" class="form-control" 
                                    placeholder="352800" required="true" size="6" />
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
                        Регион (область, край)
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

                <!-- Submit -->
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">

                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Подтвердить</button>
                    </div>                
                </div>
            </div>
                           
        </form:form>    
    </jsp:body>

</template:basic_bs_two_col>