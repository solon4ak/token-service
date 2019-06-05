<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="certificate" type="ru.tokens.site.entities.BirthCertificate"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: Birth Certificate"
                                 bodyTitle="Birth Certificate">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
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
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />

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
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
