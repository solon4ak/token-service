<%--@elvariable id="orders" type="java.util.LinkedList"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col htmlTitle="User :: User orders"
                             bodyTitle="Заказы">   

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">  
            <a class="nav-link disabled" href="<c:url value="/user/view" />">
                Пользователь
            </a>
            <a class="nav-link" href="<c:url value="/token/user/postaddress/view" />">
                Почтовый адрес
            </a>
            <a class="nav-link" href="<c:url value="/user/order/list" />">
                Заказы
            </a>
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Жетон
            </a>            
        </nav>
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
<!--            <a class="nav-link" href="<c:url value="/user/edit" />">
                Edit
            </a>-->
        </nav>        
    </jsp:attribute>

    <jsp:body>
        <div class="container">    
            <c:choose>
                <c:when test="${orders != null && fn:length(orders) > 0}">                    
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID заказа</th>
                                <th>Дата размещения</th>
                                <th>Сумма</th>
                                <th>Статус</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="order">
                                <tr>
                                    <td>
                                        <a href="<c:url value='/user/order/view/${order.orderId}' />">
                                            <c:out value="${order.orderId}" />
                                        </a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${order.created}"
                                                        type="both"
                                                        dateStyle="short"
                                                        timeStyle="short"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber type="currency"
                                                          currencySymbol="&#x20bd; "
                                                          value="${order.amount}"/>
                                    </td>
                                    <td><c:out value="${order.orderStatus}" /></td>
                                </tr>
                            </c:forEach>                            
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="m-2">Список заказов пуст</p>
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:body>
</template:basic_bs_three_col>
