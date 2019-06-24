<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col htmlTitle="User page for ${user.lastName}, ${user.firstName}"
                             bodyTitle="${user.lastName}, ${user.firstName}">

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
            <a class="nav-link" href="<c:url value="/user/edit" />">
                Изменить
            </a>
        </nav>        
    </jsp:attribute>

    <jsp:body>
        <h4>Пользователь</h4> 
        <table class="table">
            <tbody>  
                <tr>
                    <td>Зарегистрирован</td>
                    <td><wrox:formatDate value="${user.registered}" /></td>
                </tr>
                <tr>
                    <td>Фамилия</td>
                    <td><c:out value="${user.lastName}" /></td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td><c:out value="${user.firstName}" /></td>
                </tr>
                <tr>
                    <td>Отчество</td>
                    <td><c:out value="${user.middleName}" /></td>
                </tr>
                <tr>
                    <td>Дата рождения</td>
                    <td><tags:localDate date="${user.birthDate}" /></td>
                </tr>
                <tr>
                    <td>E-mail</td>
                    <td><c:out value="${user.userEmailAddress}" /></td>
                </tr>
                <tr>
                    <td>Телефон</td>
                    <td><c:out value="${user.phoneNumber}" /></td>
                </tr>
            </tbody>
        </table>            
    </jsp:body>

</template:basic_bs_three_col>
