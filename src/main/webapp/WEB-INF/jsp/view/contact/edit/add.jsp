<%--@elvariable id="contactForm" type="ru.tokens.site.controller.ContactController.ContactForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: Add Contact" 
                                 bodyTitle="Add contact">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">   
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <c:out value="Token ID: ${token.uuidString}"/><br />
        <c:out value="User E-mail: ${user.userEmailAddress}"/>
        <hr />
        <form:form method="post" modelAttribute="contactForm">
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
                    <form:label path="email" class="col-sm-3 col-form-label">
                        E-mail
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="email" type="email" class="form-control" 
                                    id="exampleInputEmail1" aria-describedby="emailHelp" 
                                    placeholder="Enter email" required="true" />
                        <small id="emailHelp" class="form-text text-muted">
                            Will be used in user events.
                        </small>
                    </div>                
                </div>

                <!--Submit-->
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">
                        &nbsp;
                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Add</button>
                    </div>                
                </div>
            </div>
        </form:form>   
    </jsp:body>

</template:basic_bs_three_col_tkn>