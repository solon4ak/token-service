<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="address" type="ru.tokens.site.entities.Address"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: User reside address"
                                 bodyTitle="User reside address">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <c:choose>
                <c:when test="${user.tokenAddress == null && user.postAddress == null}">
                    <a class="nav-link" href="<c:url value="/token/user/address/add" />">Add address</a>                 
                </c:when>
                <c:when test="${user.tokenAddress == null && user.postAddress != null}">
                    <a class="nav-link" href="<c:url value="/token/user/address/add" />">Add address</a>
                    <a class="nav-link" href="<c:url value="/token/user/address/addpostaddr" />">Use Post Address</a>  
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/address/edit" />">
                        Edit
                    </a>                    
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
            <h4>Reside Address</h4>
            <c:choose>
                <c:when test="${user.tokenAddress == null}">
                    There is no address binded to the token!<br /><br />                 
                </c:when>            
                <c:otherwise>
                    <table class="table">                    
                        <tbody>
                            <tr>
                                <td>Country</td>
                                <td><c:out value="${address.country}" /></td>
                            </tr>
                            <tr>
                                <td>Region</td>
                                <td><c:out value="${address.region}" /></td>
                            </tr>
                            <tr>
                                <td>City</td>
                                <td><c:out value="${address.city}" /></td>
                            </tr>
                            <tr>
                                <td>Street</td>
                                <td><c:out value="${address.street}" /></td>
                            </tr>
                            <tr>
                                <td>Building</td>
                                <td><c:out value="${address.building}" /></td>
                            </tr>
                            <tr>
                                <td>Apartment</td>
                                <td><c:out value="${address.apartment}" /></td>
                            </tr>
                        </tbody>
                    </table>                
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
