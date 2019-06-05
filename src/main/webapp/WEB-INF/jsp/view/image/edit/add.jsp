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
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />

            <form:form method="post" enctype="multipart/form-data" modelAttribute="imgForm"> 
                <div class="my-2 py-4">
                    <div class="form-group row">
                        <label for="userImg" class="col-sm-3 col-form-label">
                            User picture
                        </label>
                        <div class="col-sm-9">
                            <input type="file" class="form-control-file" name="file"
                                   id="userImg" aria-describedby="imgHelp">
                            <small id="imgHelp" class="form-text text-muted">
                                Up to 5 Mb
                            </small>
                        </div>                
                    </div>
                    <div class="form-group row">
                        <span class="col-sm-3 col-form-label">
                            &nbsp;
                        </span>
                        <div class="col-sm-9">
                            <button class="btn btn-primary" type="submit">Submit</button>
                        </div>                
                    </div>
                </div>
            </form:form>    
        </div>
    </jsp:body>

</template:basic_bs_three_col_tkn>