<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="passport" type="ru.tokens.site.entities.Passport"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="${token.uuidString} :: Passport"
                           bodyTitle="Passport">

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
                <c:when test="${user.passport == null}">
                    <a class="nav-link" href="<c:url value="/token/user/passport/add" />">
                        Add
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/passport/edit" />">
                        Edit
                    </a>
                    <a class="nav-link" href="<c:url value="/token/user/passport/delete" />">
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
        <h4>Passport</h4>
        <c:choose>
            <c:when test="${user.passport == null}">
                There is no passport binded to the token.                 
            </c:when>
            <c:otherwise>
                <table class="table">                    
                    <tbody>
                        <tr>
                            <td>Серия</td>
                            <td><c:out value="${passport.series}" /></td>
                        </tr>
                        <tr>
                            <td>Номер</td>
                            <td><c:out value="${passport.number}" /></td>
                        </tr>
                        <tr>
                            <td>Кем выдан</td>
                            <td><c:out value="${passport.issueDepartment}" /></td>
                        </tr>
                        <tr>
                            <td>Код подразделения</td>
                            <td><c:out value="${passport.issueDepartmentCode}" /></td>
                        </tr>
                        <tr>
                            <td>Дата выдачи</td>
                            <td><wrox:formatDate value="${passport.issueDate}" /></td>
                        </tr>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>        
    </jsp:body>
</template:basic_bs_two_col>
