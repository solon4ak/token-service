<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="View order #${orderRecord.orderId}" bodyTitle="View order #${orderRecord.orderId}">
       
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    
    <h5>
        ID пользователя #
        <a href="#">
            <c:out value="${customer.userId}" />
        </a>
    </h5>

    <form:form method="post" modelAttribute="statusForm">
        <fieldset>
            <form:label for="orderStatus" path="orderStatus">
                Статус заказа
            </form:label>
            <form:select path="orderStatus" id="orderStatus">
                <form:options items="${ord_stats}" />
            </form:select>            
        </fieldset>
        <input type="submit" value="Изменить" />
    </form:form>

    <h4>
        <strong>заказ принят:</strong>
        <fmt:formatDate value="${orderRecord.created}"
                        type="both"
                        dateStyle="short"
                        timeStyle="short"/>
    </h4>
    <table class="data_table">
        <thead>
            <tr>
                <th>товар</th>
                <th>количество</th>
                <th>сумма</th>
            </tr>
        </thead>
        <tbody>            

            <c:forEach items="${orderedProducts}" var="op">
                <tr>
                    <td>
                        <a href="<c:url value="/admin/shop/product/${op.product.productId}/view" />">
                            <c:out value="${op.product.productName}" />
                        </a>                                
                    </td>
                    <td>
                        <c:url value="${op.quantity}" />
                        &nbsp;(
                        <small>
                            x&nbsp;<fmt:formatNumber type="currency" currencySymbol="&#x20bd; "
                                              value="${op.product.initialPrice}"/>
                        </small>                                        
                        )
                    </td>
                    <td>
                        <fmt:formatNumber type="currency" currencySymbol="&#x20bd; "
                                          value="${op.product.initialPrice * op.quantity}"/>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="3" style="padding: 0 20px"></td>
            </tr>

            <tr>
                <td colspan="2"><strong>доставка:</strong></td>
                <td>
                    <fmt:formatNumber type="currency"
                                      currencySymbol="&#x20bd; "
                                      value="${surcharge}"/></td>
            </tr>

            <tr>
                <td colspan="2"><strong>итог:</strong></td>
                <td>
                    <fmt:formatNumber type="currency"
                                      currencySymbol="&#x20bd; "
                                      value="${orderRecord.amount}"/></td>
            </tr>

            <tr>
                <td colspan="3" style="padding: 0 20px"></td>
            </tr>            
        </tbody>
    </table> 
</template:admin>