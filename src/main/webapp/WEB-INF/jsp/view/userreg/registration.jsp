<%--@elvariable id="userRegistrationFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="userRegistrationForm" type="ru.tokens.site.controller.UserRegistrationController.UserRegistrationForm"--%>
<template:basic_bs_one_col htmlTitle="User registration" bodyTitle="User registration">

    <jsp:body>
        <c:if test="${userRegistrationFailed}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <c:out value="${message}" />
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <form:form method="post" modelAttribute="userRegistrationForm">
            <div class="form-group">
                <!--User-->
                <h4>User</h4>
                <hr />
                <div class="form-group row">
                    <form:label path="lastName" class="col-sm-3 col-form-label">
                        Last name
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="lastName" type="text" class="form-control" 
                                    placeholder="Last name" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="firstName" class="col-sm-3 col-form-label">
                        First name
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="firstName" type="text" class="form-control" 
                                    placeholder="First name" required="true" />
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="middleName" class="col-sm-3 col-form-label">
                        Middle name
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="middleName" type="text" class="form-control" 
                                    placeholder="Middle name" />
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="birthDate" class="col-sm-3 col-form-label">
                        Birth date
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="birthDate" type="text" class="form-control" 
                                    id="birthDate" aria-describedby="bdHelp" 
                                    placeholder="23.11.1997" required="true" />
                        <small id="bdHelp" class="form-text text-muted">
                            Please use the following format: dd.mm.yyyy (29.01.2001).
                        </small>
                    </div>
                </div>
                <div class="form-group row">
                    <form:label path="phoneNumber" class="col-sm-3 col-form-label">
                        Phone number
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="phoneNumber" type="text" class="form-control" 
                                    id="phoneNumber" aria-describedby="pnHelp" 
                                    placeholder="+7 XXX XXX XX XX" required="true" />
                        <small id="pnHelp" class="form-text text-muted">
                            We'll never share your phone number with anyone else. Format: +7 499 999 99 99
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
                                    placeholder="Enter email" required="true" />
                        <small id="emailHelp" class="form-text text-muted">
                            We'll never share your email with anyone else. Will be used for authorization.
                        </small>
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="password" class="col-sm-3 col-form-label">
                        Password
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="password" type="text" class="form-control" 
                                    id="password" aria-describedby="passHelp"
                                    readonly="true" />
                        <small id="passHelp" class="form-text text-muted">
                            Automatically generated. Will be send by mail.
                        </small>
                    </div>                
                </div>

                <!--Address-->
                <h4>Address</h4>
                <hr />
                <div class="form-group row">
                    <form:label path="zipCode" class="col-sm-3 col-form-label">
                        Zip code
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="zipCode" type="text" class="form-control" 
                                    placeholder="352800" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="country" class="col-sm-3 col-form-label">
                        Country
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="country" type="text" class="form-control" 
                                    placeholder="Россия" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="region" class="col-sm-3 col-form-label">
                        Region
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="region" type="text" class="form-control" 
                                    placeholder="Краснодарский край" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="city" class="col-sm-3 col-form-label">
                        City
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="city" type="text" class="form-control" 
                                    placeholder="Туапсе" required="true" />
                    </div>                
                </div>                
                <div class="form-group row">
                    <form:label path="street" class="col-sm-3 col-form-label">
                        Street
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="street" type="text" class="form-control" 
                                    placeholder="ул. Ленина" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="building" class="col-sm-3 col-form-label">
                        Building
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="building" type="text" class="form-control" 
                                    placeholder="8" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="apartment" class="col-sm-3 col-form-label">
                        Apartment
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
                        <button class="btn btn-primary" type="submit">Register</button>
                    </div>                
                </div>
            </form:form>
        </jsp:body>
    </template:basic_bs_one_col>
