<%--@elvariable id="cart" type="ru.tokens.site.entities.shop.ShoppingCart"--%>
<%--@elvariable id="surcharge" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop_one_col htmlTitle="Shop :: Checkout"
                                bodyTitle="Оформление заказа">
    <jsp:body>
        <div class="container">              
            <div class="row justify-content-lg-center">
                <p>Для совершения покупки войдите в систему или зарегистрируйтесь</p>
                <div class="col-6 border-right">
                    <a href="
                       <c:url value="/login">
                           <c:param name="ref" value="checkout" />
                       </c:url>
                       ">
                        Login / Register
                    </a>                    
                </div>
                <div class="col-6">
                    <table class="table table-borderless bg-light">
                        <tr>
                            <td>Товар:</td>
                            <td>
                                <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${cart.subtotal}"/></td>
                        </tr>
                        <tr>
                            <td>Доставка:</td>
                            <td>
                                <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${surcharge}"/></td>
                        </tr>
                        <tr class="border-top">
                            <td class="total">Итог:</td>
                            <td>
                                <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${cart.total}"/></td>
                        </tr>
                    </table>
                    <a class="btn btn-primary btn-sm float-right" href="<c:url value="/shop/purchase" />">Приобрести</a>
                </div>
            </div>
        </div>
    </jsp:body>
</template:basic_bs_shop_one_col>
