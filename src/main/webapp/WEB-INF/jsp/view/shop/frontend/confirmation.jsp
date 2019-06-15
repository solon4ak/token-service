<%--@elvariable id="surcharge" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop_one_col htmlTitle="Shop :: Order confirmation"
                                bodyTitle="Подтверждение заказа">
    <jsp:body>
        <div class="container">  
            <div class="row justify-content-center">
                <div class="col-10">
                    <p class="strong">
                        Ваш заказ принят в обработку и будет отгружен в течение 5 дней.
                    </p>
                    <p>
                        Номер вашего заказа: <strong>${orderRecord.confirmationNumber}</strong><br />
                        Если у вас есть какие-то вопросы по вашему заказу, Вы можете&nbsp;
                        <a href="mailto:tag4life@yandex.ru">связаться с нами</a>
                    </p>
                    <p>
                        Благодарим вас за Ваш заказ!
                    </p>
                </div>
            </div>
            <div class="row justify-content-lg-center">
                <div class="col-6  border-right">
                    <table class="table">
                        <thead>
                            <tr>
                                <th colspan="3" class="lead text-uppercase">заказ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><strong>товар</strong></td>
                                <td><strong>количество</strong></td>
                                <td><strong>сумма</strong></td>
                            </tr>

                            <c:forEach items="${orderedProducts}" var="op">
                                <tr>
                                    <td>
                                        <c:out value="${op.product.productName}" />
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

                            <tr>
                                <td colspan="3"><strong>заказ принят:</strong>
                                    <fmt:formatDate value="${orderRecord.created}"
                                                    type="both"
                                                    dateStyle="short"
                                                    timeStyle="short"/>
                                </td>
                            </tr>
                        </tbody>
                    </table> 
                </div>
                <div class="col-6">
                    <table class="table">
                        <thead>
                            <tr>
                                <th colspan="3" class="lead text-uppercase">
                                    адрес
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="3">
                                    <c:out value="${customer.lastName}" />, <c:out value="${customer.firstName}" />
                                    <br>
                                    <c:out value="${customer.postAddress.zipCode}" />
                                    <br>
                                    <c:out value="${customer.postAddress.country}" />,
                                    <br>
                                    <c:out value="${customer.postAddress.region}" />,
                                    <br>
                                    <c:out value="${customer.postAddress.city}" />,
                                    <br>
                                    <c:out value="${customer.postAddress.street}" />, 
                                    <c:out value="${customer.postAddress.building}" />
                                    /<c:out value="${customer.postAddress.apartment}" />
                                    <hr />
                                    <strong>e-mail:</strong> <c:out value="${customer.userEmailAddress}" />
                                    <br>
                                    <strong>телефон:</strong> <c:out value="${customer.phoneNumber}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</template:basic_bs_shop_one_col>
