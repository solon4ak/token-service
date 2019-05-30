<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="certificate" type="ru.tokens.site.entities.BirthCertificate"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="${token.uuidString} :: Birth Certificate"
                           bodyTitle="Birth Certificate">

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
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Token
            </a>
            <div class="dropdown-divider"></div>
            <c:choose>
                <c:when test="${user.birthCertificate == null}">
                    <a class="nav-link" href="<c:url value="/token/user/birthcert/add" />">
                        Add
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/birthcert/edit" />">
                        Edit
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/birthcert/delete" />">
                        Delete
                    </a>
                </c:otherwise>
            </c:choose>            
        </nav>
    </jsp:attribute>

    <jsp:body>
        Token ID: <c:out value="${token.uuidString}"/><br />
        User E-mail: <c:out value="${user.userEmailAddress}"/>
        <hr />
        <h4>Birth Certificate</h4>
        <c:choose>
            <c:when test="${user.birthCertificate == null}">
                There is no birth certificate binded to the token.                 
            </c:when>
            <c:otherwise>
                <table class="table">                    
                    <tbody>
                        <tr>
                            <td>Серия:</td>
                            <td><c:out value="${certificate.series}" /></td>
                        </tr>
                        <tr>
                            <td>Номер:</td>
                            <td><c:out value="${certificate.number}" /></td>
                        </tr>
                        <tr>
                            <td>Кем выдано:</td>
                            <td><c:out value="${certificate.issueDepartment}" /></td>
                        </tr>
                        <tr>
                            <td>Дата выдачи:</td>
                            <td><wrox:formatDate value="${certificate.issueDate}" /></td>
                        </tr>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>        
    </jsp:body>
</template:basic_bs_two_col>
