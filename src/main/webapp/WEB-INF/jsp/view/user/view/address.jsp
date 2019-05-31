<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="age" type="java.lang.Integer"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col_simple htmlTitle="${token.uuidString}"
                                  bodyTitle="Token: ${token.uuidString}">

    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="ml-4">Owner</h5>
            <hr />
            <div class="container">
                <div class="row justify-content-lg-center py-2">
                    <div class="col-md-6">
                        <c:if test="${user.image != null}">
                            <a href="<c:url value="/token/image">
                                   <c:param name="userId" value="${user.userId}" />
                               </c:url>" data-toggle="lightbox">
                                <img src="<c:url value="/token/thumb">
                                         <c:param name="userId" value="${user.userId}" />
                                     </c:url>" 
                                      class="img-thumbnail" 
                                      alt="${user.lastName}, ${user.firstName}">
                            </a> 
                        </c:if>
                    </div>
                    <div class="col-md-6">
                        <table class="table table-sm table-borderless">
                            <tbody>
                                <tr>
                                    <th>
                                        Token owner
                                    </th>
                                    <td>
                                        <c:out value="${user.lastName}, 
                                               ${user.firstName} ${user.middleName} " />
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Birth date
                                    </th>
                                    <td>
                                        <tags:localDate date="${user.birthDate}" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Age
                                    </th>
                                    <td>
                                        <c:out value="${age}" />
                                    </td>
                                </tr>
                            </tbody>        
                        </table>
                    </div>
                </div>
            </div> 
        </div>
    </div>

    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="pl-4">Address</h5>    
            <c:choose>
                <c:when test="${user.tokenAddress == null}">
                    There is no address binded to the token!                            
                </c:when>
                <c:otherwise>
                    <table class="table">

                        <tbody>
                            <tr>
                                <th>Country</th>
                                <td><c:out value="${user.tokenAddress.country}" /></td>
                            </tr>
                            <tr>
                                <th>Region</th>
                                <td><c:out value="${user.tokenAddress.region}" /></td>
                            </tr>
                            <tr>
                                <th>City</th>
                                <td><c:out value="${user.tokenAddress.city}" /></td>
                            </tr>
                            <tr>
                                <th>Street</th>
                                <td><c:out value="${user.tokenAddress.street}" /></td>
                            </tr>
                            <tr>
                                <th>Building</th>
                                <td><c:out value="${user.tokenAddress.building}" /></td>
                            </tr>
                            <tr>
                                <th>Apartment</th>
                                <td><c:out value="${user.tokenAddress.apartment}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="pl-4">Contacts</h5>
            <c:if test="${fn:length(user.contacts) == 0}">
                There is no contacts binded to the token!
            </c:if>
            <table class="table">
                <c:if test="${fn:length(user.contacts) > 0}">
                    <thead>
                        <!-- here should go some titles... -->
                        <tr>                
                            <th>First name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                        </tr>
                    </thead>
                </c:if>
                <tbody>
                    <c:forEach items="${token.user.contacts}" var="contact">
                        <tr>                
                            <td>
                                <c:out value="${contact.firstName}" />
                            </td>
                            <td>
                                <c:out value="${contact.lastName}" />
                            </td>
                            <td>
                                <c:out value="${contact.phoneNumber}" />
                            </td>                    
                        </tr>
                    </c:forEach>
                </tbody>
            </table>   
        </div>            
    </div>

</template:basic_bs_one_col_simple>
