<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="address" type="ru.tokens.site.entities.Address"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: User reside address"
                                 bodyTitle="Адрес проживания">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <c:choose>
                <c:when test="${user.tokenAddress == null && user.postAddress == null}">
                    <a class="nav-link" href="<c:url value="/token/user/address/add" />">Добавить</a>                 
                </c:when>
                <c:when test="${user.tokenAddress == null && user.postAddress != null}">
                    <a class="nav-link" href="<c:url value="/token/user/address/add" />">Добавить</a>
                    <a class="nav-link" href="<c:url value="/token/user/address/addpostaddr" />">Использовать почтовый</a>  
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/address/edit" />">
                        Изменить
                    </a>                    
                </c:otherwise>
            </c:choose>   
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />
            <h4>Адрес проживания</h4>
            <c:choose>
                <c:when test="${user.tokenAddress == null}">
                    Не указан!<br /><br />                 
                </c:when>            
                <c:otherwise>
                    <table class="table">                    
                        <tbody>
                            <tr>
                                <td>Страна</td>
                                <td><c:out value="${address.country}" /></td>
                            </tr>
                            <tr>
                                <td>Регион (край, область)</td>
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
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
