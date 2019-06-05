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
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />
            <c:choose>
                <c:when test="${user.image == null}">
                    <div class="text-justify">
                        Add your picture.
                    </div>                            
                </c:when>
                <c:otherwise>
                    <div class="justify-content-center">
                        <a href="<c:url value='/token/image'>
                               <c:param name='userId' value='${user.userId}' />
                           </c:url>" data-fancybox="images">
                            <img src="<c:url value="/token/user/image/thumbnail" />" 
                                 class="img-thumbnail" 
                                 alt="${user.lastName}, ${user.firstName}">
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>  
        </div>
    </jsp:body>

</template:basic_bs_three_col_tkn>