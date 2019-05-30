<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="${token.uuidString}"
                           bodyTitle="Token: ${token.uuidString}">

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

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/token/user/birthcert/view" />">
                Birth cert
            </a>
            <a class="nav-link" href="<c:url value="/token/user/passport/view" />">
                Passport
            </a>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Address
            </a>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Contacts
            </a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Medical
            </a>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Events
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <h5>Token ID: <c:out value="${token.uuidString}"/></h5>
        <h5>User E-mail: <c:out value="${user.userEmailAddress}"/></h5>
        <p class="font-weight-lighter">
            Activated: <wrox:formatDate value="${token.activatedDate}" />
        </p>
        <hr />        
        View my token page: 
        <a class="btn btn-link" href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
               <c:param name="showMH" value="false" />              
           </c:url>" target="_blank">
            Address
        </a> / 
        <a class="btn btn-link" href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
               <c:param name="showMH" value="true" />              
           </c:url>" target="_blank">
            Full
        </a>
        <hr />
        <p class="font-weight-normal">
            <b>Token owner</b> - <c:out value="${user.lastName}, 
                   ${user.firstName} ${user.middleName} " />
        </p>
        <p class="font-weight-normal">
            Birth date: <tags:localDate date="${user.birthDate}" />
        </p>
        <c:choose>
            <c:when test="${user.image == null}">
                <br /><br />
                <a class="btn btn-link" href="<c:url value="/token/user/image/upload" />">
                    Add photo
                </a>
            </c:when>
            <c:otherwise>
                <hr />
                <img src="<c:url value="/token/user/image/thumbnail" />" 
                     alt="${user.lastName}, ${user.firstName}"/><br /><br />
                <a class="btn btn-link" href="<c:url value="/token/user/image/delete" />">
                    Delete
                </a> / 
                <a class="btn btn-link" href="<c:url value="/token/user/image/upload" />">
                    Change
                </a>
            </c:otherwise>
        </c:choose>  
          
        <hr />
        <h4>Address:</h4>
        <c:choose>
            <c:when test="${user.tokenAddress == null && user.postAddress == null}">
                There is no address binded to the token!<br /><br />
                <a href="<c:url value="/token/user/address/add" />">Add address</a>                 
            </c:when>
            <c:when test="${user.tokenAddress == null && user.postAddress != null}">
                There is no address binded to the token!<br /><br />
                <a href="<c:url value="/token/user/address/add" />">Add address</a> /
                <a href="<c:url value="/token/user/address/addpostaddr" />">Use Post Address</a>  
            </c:when>
            <c:otherwise>
                <table class="data_table">
                    <thead>
                        <tr>
                            <th>Property</th>
                            <th>Data</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Country:</td>
                            <td><c:out value="${user.tokenAddress.country}" /></td>
                        </tr>
                        <tr>
                            <td>Region:</td>
                            <td><c:out value="${user.tokenAddress.region}" /></td>
                        </tr>
                        <tr>
                            <td>City:</td>
                            <td><c:out value="${user.tokenAddress.city}" /></td>
                        </tr>
                        <tr>
                            <td>Street:</td>
                            <td><c:out value="${user.tokenAddress.street}" /></td>
                        </tr>
                        <tr>
                            <td>Building:</td>
                            <td><c:out value="${user.tokenAddress.building}" /></td>
                        </tr>
                        <tr>
                            <td>Apartment:</td>
                            <td><c:out value="${user.tokenAddress.apartment}" /></td>
                        </tr>
                    </tbody>
                </table>
                <br />
                <a href="<c:url value="/token/user/address/edit" />">Edit address</a>
            </c:otherwise>
        </c:choose>
        <hr />

        <h4>Contacts:</h4>
        <c:if test="${user.numberOfContacts == 0}">
            There is no contacts binded to the token!
        </c:if>
        <table class="data_table">
            <c:if test="${user.numberOfContacts > 0}">
                <thead>
                    <!-- here should go some titles... -->
                    <tr>                
                        <th>First name</th>
                        <th>Last Name</th>
                        <th>Phone</th>
                        <th>E-mail</th>
                        <th>Action</th>
                    </tr>
                </thead>
            </c:if>
            <tbody>
                <c:forEach items="${user.contacts}" var="contact">
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
                        <td>
                            <c:out value="${contact.email}" />
                        </td>
                        <td>
                            <a href="<c:url value="/token/user/contact/edit/${contact.contactId}" />">Edit</a>&nbsp;/
                            <a href="<c:url value="/token/user/contact/delete/${contact.contactId}" />">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        <a href="<c:url value="/token/user/contact/add" />">Add contact</a>   
        <hr />
        <c:choose>
            <c:when test="${user.medicalHistory == null}">
                <a href="<c:url value="/token/user/med/add" />">Add Medical Data</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/token/user/med/view" />">Medical Data</a>
            </c:otherwise>
        </c:choose>
        <hr />
        <a href="<c:url value="/token/user/csdevent/add" />">Add Caused Message</a> 
        / <a href="<c:url value="/token/user/csdevent/list" />">
            List entries
        </a>
    </jsp:body>
</template:basic_bs_two_col>
