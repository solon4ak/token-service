<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="${token.uuidString}"
                           bodyTitle="Token: ${token.uuidString}">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">            
            <a class="nav-link" href="<c:url value="/token/user/birthcert/view" />">
                Birth cert
            </a>
            <a class="nav-link" href="<c:url value="/token/user/passport/view" />">
                Passport
            </a>
            <a class="nav-link" href="<c:url value="/token/user/address/view" />">
                Address
            </a>
            <a class="nav-link" href="<c:url value="/token/user/contact/list" />">
                Contacts
            </a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/token/user/med/add" />">
                Medical data
            </a>
            <a class="nav-link" href="<c:url value="/token/user/csdevent/list" />">
                Events
            </a>            
        </nav>
    </jsp:attribute>

    <jsp:body>
        <h5>Token ID: <c:out value="${token.uuidString}"/></h5>
        
        <div class="container">
            <div class="row justify-content-lg-center pt-3">
                <div class="col-md-auto">
                    <c:choose>
                        <c:when test="${user.image == null}">
                            <a class="btn btn-primary btn-sm" href="<c:url value="/token/user/image/upload" />">
                                Add photo
                            </a>
                        </c:when>
                        <c:otherwise>
                            <img class="img-thumbnail" src="<c:url value="/token/user/image/thumbnail" />" 
                                 alt="${user.lastName}, ${user.firstName}"/>
                            <div class="p-2">
                                Image&nbsp;
                                <a href="<c:url value="/token/user/image/delete" />">
                                    Delete
                                </a> / 
                                <a href="<c:url value="/token/user/image/upload" />">
                                    Change
                                </a>
                            </div>

                        </c:otherwise>
                    </c:choose>  
                </div>
                <div class="col-md-auto">
                    <table class="table table-sm table-borderless">
                        <tbody>
                            <tr>
                                <td>
                                    Activated 
                                </td>
                                <td>
                                    <wrox:formatDate value="${token.activatedDate}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Token owner 
                                </td>
                                <td>
                                    <c:out value="${user.lastName}, 
                                           ${user.firstName} ${user.middleName} " />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Birth date
                                </td>
                                <td>
                                    <tags:localDate date="${user.birthDate}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Email 
                                </td>
                                <td>
                                    <c:out value="${user.userEmailAddress}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Phone 
                                </td>
                                <td>
                                    <c:out value="${user.phoneNumber}"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>                    
                </div>
            </div>
        </div>


        <hr />        
        <h5>View my token page</h5>
        <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
               <c:param name="showMH" value="false" />              
           </c:url>" target="_blank">
            Address
        </a> / 
        <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
               <c:param name="showMH" value="true" />              
           </c:url>" target="_blank">
            Full
        </a>
    </jsp:body>
</template:basic_bs_two_col>
