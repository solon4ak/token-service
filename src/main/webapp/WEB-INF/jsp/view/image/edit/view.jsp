<%--@elvariable id="imgForm" type="ru.tokens.site.controller.ImageController.ImageForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Add User Photo" bodyTitle="Token owner Photo">  

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">            
            <c:choose>
                <c:when test="${user.image == null}">
                    <a class="nav-link" href="<c:url value="/token/user/image/upload" />">
                        Add
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/image/upload" />">
                        Change
                    </a> 
                    <a class="nav-link" href="<c:url value="/token/user/image/delete" />">
                        Delete
                    </a>   
                </c:otherwise>
            </c:choose>   
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <c:out value="Token ID: ${token.uuidString}"/><br />
        <c:out value="User E-mail: ${user.userEmailAddress}"/>
        <hr />
        <c:choose>
            <c:when test="${user.image == null}">
                <div class="align-middle">
                    Add your picture.
                </div>                            
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/token/image'>
                       <c:param name='userId' value='${user.userId}' />
                   </c:url>" data-fancybox="images">
                    <img src="<c:url value="/token/user/image/thumbnail" />" 
                         class="img-thumbnail" 
                         alt="${user.lastName}, ${user.firstName}">
                </a>
            </c:otherwise>
        </c:choose>  

    </jsp:body>

</template:basic_bs_three_col_tkn>