<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="${token.uuidString}"
                bodyTitle="Token: ${token.uuidString}">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    Activated: <wrox:formatDate value="${token.activatedDate}" /><br /><br />
    <a href="<c:url value="/user/view" />">View my user page</a><br /><br />
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
    <b>Token owner</b> - <c:out value="${user.lastName}, 
           ${user.firstName} ${user.middleName} " /><br />  <br />  
    Birth date: <tags:localDate date="${user.birthDate}" /><br /><br />
    <a href="<c:url value="/user/edit" />">Edit user data</a>
    <c:choose>
        <c:when test="${user.image == null}">
            <br /><br />
            <a href="<c:url value="/token/user/image/upload" />">Add photo</a>
        </c:when>
        <c:otherwise>
            <hr />
            <img src="<c:url value="/token/user/image/thumbnail" />" 
                 alt="${user.lastName}, ${user.firstName}"/><br /><br />
            <a href="<c:url value="/token/user/image/delete" />">Delete</a> / 
            <a href="<c:url value="/token/user/image/upload" />">Change</a>
        </c:otherwise>
    </c:choose>  

    <hr />
    <h5>Birth Certificate:</h5>
    <c:choose>
        <c:when test="${user.birthCertificate == null}">
            There is no birth certificate binded to the token.<br /><br />
            <a href="<c:url value="/token/user/birthcert/add" />">Add Birth Certificate</a>                 
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
                        <td><c:out value="${user.birthCertificate.series}" /></td>
                    </tr>
                    <tr>
                        <td>Номер:</td>
                        <td><c:out value="${user.birthCertificate.number}" /></td>
                    </tr>
                    <tr>
                        <td>Кем выдано:</td>
                        <td><c:out value="${user.birthCertificate.issueDepartment}" /></td>
                    </tr>
                    <tr>
                        <td>Дата выдачи:</td>
                        <td><wrox:formatDate value="${user.birthCertificate.issueDate}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
            <a href="<c:url value="/token/user/birthcert/edit" />">Edit</a>&nbsp;/
            <a href="<c:url value="/token/user/birthcert/delete" />">Delete Birth Certificate</a>
        </c:otherwise>
    </c:choose>
    <hr />
    <h5>Passport:</h5>
    <c:choose>
        <c:when test="${user.passport == null}">
            There is no passport binded to the token.<br /><br />
            <a href="<c:url value="/token/user/passport/add" />">Add passport</a>                 
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
                        <td><c:out value="${user.passport.series}" /></td>
                    </tr>
                    <tr>
                        <td>Номер:</td>
                        <td><c:out value="${user.passport.number}" /></td>
                    </tr>
                    <tr>
                        <td>Кем выдан:</td>
                        <td><c:out value="${user.passport.issueDepartment}" /></td>
                    </tr>
                    <tr>
                        <td>Код подразделения:</td>
                        <td><c:out value="${user.passport.issueDepartmentCode}" /></td>
                    </tr>
                    <tr>
                        <td>Дата выдачи:</td>
                        <td><wrox:formatDate value="${user.passport.issueDate}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
            <a href="<c:url value="/token/user/passport/edit" />">Edit</a>&nbsp;/
            <a href="<c:url value="/token/user/passport/delete" />">Delete passport</a>
        </c:otherwise>
    </c:choose>    
    <hr />
    <h5>Address:</h5>
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

    <h5>Contacts:</h5>
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

</template:basic>
