<%--@elvariable id="categories" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop_one_col htmlTitle="Shop"
                           bodyTitle="Магазин">

    <jsp:body>
        <div class="pricing-header px-3 py-3 pt-md-3 pb-md-4 mx-auto text-center">
            <!--<h1 class="display-4">Ваш жетон</h1>-->
            <p class="lead">
                Для того, чтобы пользоваться сервисом, вам необходимо приобрести 
                носитель или носители штрих-кода, а также приобрести подписку на сервис.
            </p>
        </div>
        <div class="container">
            <div class="card-deck mb-3 text-center">
                <div class="card mb-4 shadow-sm">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">Носители</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$0 <small class="text-muted">/ mo</small></h1>
                        <ul class="list-unstyled mt-3 mb-4">
                            <li>10 users included</li>
                            <li>2 GB of storage</li>
                            <li>Email support</li>
                            <li>Help center access</li>
                        </ul>
                        <a href="<c:url value="/shop/category/1" />" class="btn btn-lg btn-block btn-outline-primary" >
                            Перейти
                        </a>
                    </div>
                </div>
                <div class="card mb-4 shadow-sm">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">Подписка</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$15 <small class="text-muted">/ mo</small></h1>
                        <ul class="list-unstyled mt-3 mb-4">
                            <li>20 users included</li>
                            <li>10 GB of storage</li>
                            <li>Priority email support</li>
                            <li>Help center access</li>
                        </ul>
                        <button type="button" class="btn btn-lg btn-block btn-primary">Get started</button>
                    </div>
                </div>                
            </div>
        </div>
    </jsp:body>
</template:basic_bs_shop_one_col>
