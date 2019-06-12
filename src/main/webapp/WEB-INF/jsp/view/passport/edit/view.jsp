<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="passport" type="ru.tokens.site.entities.Passport"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: Passport"
                                 bodyTitle="Passport">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
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
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />

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
        </div>

    </jsp:body>
</template:basic_bs_three_col_tkn>
