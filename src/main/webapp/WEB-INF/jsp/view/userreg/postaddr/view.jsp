<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="address" type="ru.tokens.site.entities.Address"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col htmlTitle="User: ${user.lastName}, ${user.firstName} post address"
                             bodyTitle="Почтовый адрес">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <c:choose>
                <c:when test="${user.postAddress == null}">
                    <a class="nav-link" href="<c:url value="/token/user/postaddress/add" />">
                        Добавить
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/postaddress/edit" />">
                        Изменить
                    </a>
                </c:otherwise>
            </c:choose>
        </nav>
    </jsp:attribute>

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
            </a>
            <a class="nav-link disabled" href="<c:url value="/token/user/postaddress/view" />">
                Почтовый адрес
            </a>
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Жетон
            </a> 
        </nav>
    </jsp:attribute>

    <jsp:body>   
        <c:choose>
            <c:when test="${user.postAddress == null}">
                Почтовый адрес не указан.
            </c:when>
            <c:otherwise>
                <h4>Почтовый адрес</h4>
                <table class="table">
                    <tbody>
                        <tr>
                            <td>Индекс</td>
                            <td><c:out value="${address.zipCode}" /></td>
                        </tr>
                        <tr>
                            <td>Страна</td>
                            <td><c:out value="${address.country}" /></td>
                        </tr>
                        <tr>
                            <td>Регион</td>
                            <td><c:out value="${address.region}" /></td>
                        </tr>
                        <tr>
                            <td>Город</td>
                            <td><c:out value="${address.city}" /></td>
                        </tr>
                        <tr>
                            <td>Улица (шоссе, проспект)</td>
                            <td><c:out value="${address.street}" /></td>
                        </tr>
                        <tr>
                            <td>Дом</td>
                            <td><c:out value="${address.building}" /></td>
                        </tr>
                        <tr>
                            <td>Квартира</td>
                            <td><c:out value="${address.apartment}" /></td>
                        </tr>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>        
    </jsp:body>

</template:basic_bs_three_col>
