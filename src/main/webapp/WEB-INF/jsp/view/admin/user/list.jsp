<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="List users" bodyTitle="Список пользователей">
    <hr />    
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <table class="data_table">
        <thead>
            <tr>
                <th>id</th>
                <th>registered</th>
                <th>name</th>
                <th>e-mail</th>
                <th>activated</th>                
                <th>token</th>
                <th>balance</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>
                        <c:out value="${user.userId}" />
                    </td>
                    <td>
                        <wrox:formatDate value="${user.registered}" dateStyle="full" />
                    </td>
                    <td>
                        <c:out value="${user}" />
                    </td>
                    <td>
                        <c:out value="${user.userEmailAddress}" />
                    </td>
                    <td>
                        <c:out value="${user.emailActivated}" />
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${user.token ne null}">
                                <c:out value="${user.token.uuidString}" />
                            </c:when>
                            <c:otherwise>
                                n/a
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${user.userBalance.ammount > 0 
                                            and user.userBalance.payedTill.payedTillMillies > instant}">
                                positive
                            </c:when>
                            <c:otherwise>
                                negative
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>                            
        </tbody>
    </table>
</template:admin>