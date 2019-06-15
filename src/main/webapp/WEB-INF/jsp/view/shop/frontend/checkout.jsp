<%--@elvariable id="cart" type="ru.tokens.site.entities.shop.ShoppingCart"--%>
<%--@elvariable id="surcharge" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop_one_col htmlTitle="Shop :: Checkout"
                                bodyTitle="Оформление заказа">
    <jsp:body>
        <div class="container">              
            <div class="row justify-content-lg-center">
                <c:if test="${loginFailed}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <c:out value="${message}" />
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${authorised}">
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
                    </c:when>
                    <c:otherwise>
                        <p>Для совершения покупки войдите в систему или зарегистрируйтесь</p>
                        <div class="col-6 border-right">
                            <h1 class="h3 mb-3 font-weight-normal">Login</h1>
                            <c:if test="${loginFailed}">
                                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                    User credentials doesn't correspond or user e-mail doesn't confirmed.
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </c:if>
                            <form:form method="post" modelAttribute="loginForm">
                                <div class="form-group">
                                    <form:label path="email">E-mail</form:label>
                                    <form:input path="email" type="email" class="form-control" 
                                                id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" required="true" />
                                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                </div>
                                <div class="form-group">
                                    <form:label path="password">Password</form:label>
                                    <form:input path="password" type="password" class="form-control" placeholder="Password" required="true" />
                                </div>
                                <button class="btn btn-primary" type="submit">Log In</button>
                                <!--<input type="submit" value="Log In" />-->
                            </form:form>
                            <p>
                                <a class="btn btn-link" href="<c:url value="/user/passwordreset" />">Reset password</a>                   
                            </p>
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
                            <!--<a class="btn btn-primary btn-sm float-right" href="<c:url value="/shop/purchase" />">Приобрести</a>-->
                        </div>
                    </c:otherwise>
                </c:choose>  
            </div>
        </div>
    </jsp:body>
</template:basic_bs_shop_one_col>
