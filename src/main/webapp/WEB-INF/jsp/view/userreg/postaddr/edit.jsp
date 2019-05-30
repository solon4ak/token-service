<%--@elvariable id="addressForm" type="ru.tokens.site.controller.PostAddressController.PostAddressForm"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="Edit Post Address" bodyTitle="Post Address Edit">    
    
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
        <h5><c:out value="User E-mail: ${user.userEmailAddress}"/></h5>
        <hr />
        <form:form method="post" modelAttribute="addressForm">
            <div class="form-group">
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
    </jsp:body>
    
</template:basic_bs_one_col>