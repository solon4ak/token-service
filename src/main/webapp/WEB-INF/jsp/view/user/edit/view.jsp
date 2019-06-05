<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString}"
                                 bodyTitle="Token: ${token.uuidString}">

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
        <div class="container">
            <h5>Token ID: <c:out value="${token.uuidString}"/></h5>
            <c:choose>
                <c:when test="${user.image == null}">
                    <div class="row pt-3 border-top">                        
                        <div class="col-md-6">
                            <table class="table table-sm table-borderless">
                                <tbody>
                                    <tr>
                                        <th>
                                            Activated 
                                        </th>
                                        <td>
                                            <wrox:formatDate value="${token.activatedDate}" />
                                        </td>
                                    </tr>
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
                                            Email 
                                        </th>
                                        <td>
                                            <c:out value="${user.userEmailAddress}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>
                                            Phone 
                                        </th>
                                        <td>
                                            <c:out value="${user.phoneNumber}"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>                    
                        </div>   
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row pt-3 border-top">
                        <div class="col-md-6 border-right">
                            <img class="img-thumbnail" src="<c:url value="/token/user/image/thumbnail" />" 
                                 alt="${user.lastName}, ${user.firstName}"/>
                        </div>
                        <div class="col-md-6">
                            <table class="table table-sm table-borderless">
                                <tbody>
                                    <tr>
                                        <th>
                                            Activated 
                                        </th>
                                        <td>
                                            <wrox:formatDate value="${token.activatedDate}" />
                                        </td>
                                    </tr>
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
                                            Email 
                                        </th>
                                        <td>
                                            <c:out value="${user.userEmailAddress}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>
                                            Phone 
                                        </th>
                                        <td>
                                            <c:out value="${user.phoneNumber}"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>                    
                        </div>   
                    </div>
                </c:otherwise>
            </c:choose>        

            <hr />        
            <h5>How it looks</h5>
            <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
                   <c:param name="showMH" value="false" />              
               </c:url>" target="_blank">
                Short
            </a> / 
            <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
                   <c:param name="showMH" value="true" />              
               </c:url>" target="_blank">
                Full
            </a>
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
