<%--
        model.put("users", usersCount);
        model.put("users_with_tokens", usersWithToken);
        model.put("orders", orders);        
        model.put("allOrdersTotal", allOrdersTotal);
        model.put("allPayedOrdersTotal", allPayedOrdersTotal);
        model.put("lastWeekOrdersCount", lastWeekOrdersCount);
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="Admin panel" bodyTitle="Admin panel">  

    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
        <table class="table">
            <tbody>
                <tr>
                    <td>Аккаунтов</td>
                    <td><c:out value="${users}" /></td>
                </tr>
                <tr>
                    <td>Зарегистрировано жетонов</td>
                    <td><c:out value="${users_with_tokens}" /></td>
                </tr>
                <tr>
                    <td>Всего заказов</td>
                    <td><c:out value="${orders}" /></td>
                </tr>
                <tr>
                    <td>Заказов за последнюю неделю</td>
                    <td><c:out value="${lastWeekOrdersCount}" /></td>
                </tr>
                <tr>
                    <td>Общая сумма заказов</td>
                    <td><c:out value="${allOrdersTotal}" /></td>
                </tr>
                <tr>
                    <td>Сумма оплаченных заказов</td>
                    <td><c:out value="${allPayedOrdersTotal}" /></td>
                </tr>
            </tbody>
        </table>  
</template:admin>