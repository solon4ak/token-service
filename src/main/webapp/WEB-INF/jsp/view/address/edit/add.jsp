<%--@elvariable id="addressForm" type="ru.tokens.site.controller.AddressController.AddressForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Add Reside Address" bodyTitle="Reside Address"> 

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <c:choose>
                <c:when test="${user.postAddress == null}">
                    <a class="nav-link" href="<c:url value="/token/user/address/add" />">Add address</a>                 
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/address/add" />">Add address</a>
                    <a class="nav-link" href="<c:url value="/token/user/address/addpostaddr" />">Use Post Address</a>                   
                </c:otherwise>
            </c:choose>    
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />
            <form:form method="post" modelAttribute="addressForm">
                <div class="form-group">
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

                    <!-- Submit -->
                    <div class="form-group row">
                        <span class="col-sm-3 col-form-label">

                        </span>
                        <div class="col-sm-9">
                            <button class="btn btn-primary" type="submit">Submit</button>
                        </div>                
                    </div>
                </div>            
            </form:form>   
        </div>
    </jsp:body>

</template:basic_bs_three_col_tkn>