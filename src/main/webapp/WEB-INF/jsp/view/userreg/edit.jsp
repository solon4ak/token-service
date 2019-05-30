<%--@elvariable id="userRegistrationForm" type="ru.tokens.site.controller.UserRegistrationController.UserRegistrationForm"--%>
<template:basic_bs_one_col htmlTitle="Edit user" bodyTitle="Edit user">

    <jsp:attribute name="authContent">
        <c:choose>
            <c:when test="${sessionScope['ru.tkn.user.principal'] != null}">
                <a class="btn btn-light text-dark" href="<c:url value="/logout" />">
                    Logout
                </a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-light text-dark" href="<c:url value="/login" />">
                    Login
                </a>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="extraNavigationContent">
        <c:if test="${sessionScope['ru.tkn.user.principal'] != null}">
            <a class="p-2 text-dark" href="<c:url value="/user/view" />">User</a>
        </c:if>
    </jsp:attribute>    
    
    <jsp:body>
        <h5>User: <c:out value="${user.lastName}, ${user.firstName}" /></h5>
        <h5>E-mail: <c:out value="${user.userEmailAddress}" /></h5>
        <hr />
        <form:form method="post" modelAttribute="userRegistrationForm">
            <div class="form-group">
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
                            Format: dd.mm.yyyy (29.01.2001).
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
                            Format: +7 499 999 99 99
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
                            Automatically generated.
                        </small>
                    </div>                
                </div>
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">

                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Confirm</button>
                    </div>                
                </div>
                
            </form:form>
        </jsp:body>
    </template:basic_bs_one_col>
