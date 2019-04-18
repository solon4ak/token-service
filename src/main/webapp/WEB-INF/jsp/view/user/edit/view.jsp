<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="${token.uuidString}"
                bodyTitle="Token: ${token.uuidString}">
    Activated: <wrox:formatDate value="${token.activatedDate}" /><br /><br />
    View my token page: 
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
    <hr />
    <b>Token owner</b> - <c:out value="${token.user.lastName}, 
           ${token.user.firstName} ${token.user.middleName} " /><br />  <br />  
    Birth date: <tags:localDate date="${token.user.birthDate}" /><br /><br />
    <a href="<c:url context="/tkn" value="/token/user/edit" />">Edit user data</a>
    <c:choose>
        <c:when test="${token.user.image == null}">
            <br /><br />
            <a href="<c:url value="/token/user/image/upload" />">Add photo</a>
        </c:when>
        <c:otherwise>
            <hr />
            <img src="<c:url value="/token/user/image/thumbnail" />" 
             alt="${token.user.lastName}, ${token.user.firstName}"/><br /><br />
            <a href="<c:url value="/token/user/image/delete" />">Delete</a> / 
            <a href="<c:url value="/token/user/image/upload" />">Change</a>
        </c:otherwise>
    </c:choose>  
    
    <hr />
    <h5>Birth Certificate:</h5>
    <c:choose>
        <c:when test="${token.user.birthCertificate == null}">
            There is no birth certificate binded to the token.<br /><br />
            <a href="<c:url context="/tkn" value="/token/user/birthcert/add" />">Add Birth Certificate</a>                 
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
                        <td>Серия:</td>
                        <td><c:out value="${token.user.birthCertificate.series}" /></td>
                    </tr>
                    <tr>
                        <td>Номер:</td>
                        <td><c:out value="${token.user.birthCertificate.number}" /></td>
                    </tr>
                    <tr>
                        <td>Кем выдано:</td>
                        <td><c:out value="${token.user.birthCertificate.issueDepartment}" /></td>
                    </tr>
                    <tr>
                        <td>Дата выдачи:</td>
                        <td><wrox:formatDate value="${token.user.birthCertificate.issueDate}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
            <a href="<c:url context="/tkn" value="/token/user/birthcert/edit" />">Edit</a>&nbsp;/
            <a href="<c:url context="/tkn" value="/token/user/birthcert/delete" />">Delete Birth Certificate</a>
        </c:otherwise>
    </c:choose>
    <hr />
    <h5>Passport:</h5>
    <c:choose>
        <c:when test="${token.user.passport == null}">
            There is no passport binded to the token.<br /><br />
            <a href="<c:url context="/tkn" value="/token/user/passport/add" />">Add passport</a>                 
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
                        <td>Серия:</td>
                        <td><c:out value="${token.user.passport.series}" /></td>
                    </tr>
                    <tr>
                        <td>Номер:</td>
                        <td><c:out value="${token.user.passport.number}" /></td>
                    </tr>
                    <tr>
                        <td>Кем выдан:</td>
                        <td><c:out value="${token.user.passport.issueDepartment}" /></td>
                    </tr>
                    <tr>
                        <td>Код подразделения:</td>
                        <td><c:out value="${token.user.passport.issueDepartmentCode}" /></td>
                    </tr>
                    <tr>
                        <td>Дата выдачи:</td>
                        <td><wrox:formatDate value="${token.user.passport.issueDate}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
            <a href="<c:url context="/tkn" value="/token/user/passport/edit" />">Edit</a>&nbsp;/
            <a href="<c:url context="/tkn" value="/token/user/passport/delete" />">Delete passport</a>
        </c:otherwise>
    </c:choose>    
    <hr />
    <h5>Address:</h5>
    <c:choose>
        <c:when test="${token.user.address == null}">
            There is no address binded to the token!<br /><br />
            <a href="<c:url context="/tkn" value="/token/user/address/add" />">Add address</a>                 
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
                        <td><c:out value="${token.user.address.country}" /></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td><c:out value="${token.user.address.city}" /></td>
                    </tr>
                    <tr>
                        <td>Street:</td>
                        <td><c:out value="${token.user.address.street}" /></td>
                    </tr>
                    <tr>
                        <td>Building:</td>
                        <td><c:out value="${token.user.address.building}" /></td>
                    </tr>
                    <tr>
                        <td>Apartment:</td>
                        <td><c:out value="${token.user.address.apartment}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
            <a href="<c:url context="/tkn" value="/token/user/address/edit" />">Edit address</a>
        </c:otherwise>
    </c:choose>
    <hr />

    <h5>Contacts:</h5>
    <c:if test="${fn:length(token.user.contacts) == 0}">
        There is no contacts binded to the token!
    </c:if>
    <table class="data_table">
        <c:if test="${fn:length(token.user.contacts) > 0}">
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
                    <td>
                        <c:out value="${contact.email}" />
                    </td>
                    <td>
                        <a href="<c:url context="/tkn" value="/token/user/contact/edit/${contact.contactId}" />">Edit</a>&nbsp;/
                        <a href="<c:url context="/tkn" value="/token/user/contact/delete/${contact.contactId}" />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br/>
    <a href="<c:url context="/tkn" value="/token/user/contact/add" />">Add contact</a>   
    <hr />
    <c:choose>
        <c:when test="${token.user.medicalHistory == null}">
            <a href="<c:url context="/tkn" value="/token/user/med/add" />">Add Medical Data</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url context="/tkn" value="/token/user/med/view" />">Medical Data</a>
        </c:otherwise>
    </c:choose>

</template:basic>
