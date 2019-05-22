<%--@elvariable id="productForm" type="ru.tokens.site.controller.ProductController.ProductForm"--%>
<%--@elvariable id="categories" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<template:admin htmlTitle="Adding new product" bodyTitle="Add product to category"> 

    <jsp:attribute name="extraHeadContent">
        <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=i6rk63ssg8q0xzbsnyjrkxichdz2rjlup6i7drwsa82i6i1w"></script>        
        <script>
            tinymce.init({
                selector: '#addProductDescription'
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <c:if test="${message != null}">
            <c:out value="${message}" />
            <hr />
        </c:if>
        <form:form method="post" enctype="multipart/form-data" modelAttribute="productForm">
            <fieldset>
                <legend>New product</legend>
                <form:label path="productName">Product name</form:label>
                <form:input path="productName"/> 
                <form:label path="price">Product price</form:label>
                <form:input path="price"/> 
                <form:label path="description">Product description</form:label>
                <form:textarea id="addProductDescription" path="description" cols="90" rows="10"/>
            </fieldset>
            <fieldset>
                <legend>Pictures (1 - 5)</legend>
                <label>Up to 5 Mb/file</label>
                <input type="file" name="images" multiple="multiple"/>
            </fieldset>
            <fieldset>
                <legend>Select categories</legend>
                <form:select path="categories" items="${categories}" multiple="true" size="5" />
            </fieldset>
            <hr />
            <input type="submit" value="Submit" />
        </form:form> 
    </jsp:body>
</template:admin>