<%--@elvariable id="productForm" type="ru.tokens.site.controller.ProductController.ProductForm"--%>
<%--@elvariable id="product" type="ru.tokens.site.entities.shop.Product"--%>
<%--@elvariable id="categories" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<template:admin htmlTitle="Editing product: ${product.productName}" bodyTitle="Edit product: ${product.productName}"> 

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
                <legend>Edit product</legend>
                <form:label path="productName">Product name</form:label>
                <form:input path="productName"/> 
                <form:label path="price">Product price</form:label>
                <form:input path="price"/> 
                <form:label path="description">Product description</form:label>
                <form:textarea id="addProductDescription" path="description" cols="90" rows="10"/>
            </fieldset>
            <fieldset>
                <legend>Pictures (up to 5)</legend>
                <c:if test="${fn:length(product.pictures) > 0}">
                    <b>Product images:</b><br />
                    <table border="0" cellspacing="5" cellpadding="5">
                        <tbody>
                            <tr>
                                <c:forEach items="${product.pictures}" var="img">
                                    <td>
                                        <a href="<c:url value="/admin/shop/product/image/${img.id}/view" />" target="_blank">
                                            <img src="<c:url value="/admin/shop/product/image/${img.id}/icon" />" 
                                                 alt="${img.name}"/>
                                        </a>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach items="${product.pictures}" var="img">
                                    <td align="center">
                                        <a href="<c:url value="/admin/shop/product
                                               /${product.productId}/image/${img.id}/delete" />">
                                            Delete
                                        </a>
                                    </td>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                    <hr />
                </c:if>
                <label>Up to 5 Mb/file</label>
                <input type="file" name="images" multiple="multiple"/>
            </fieldset>
            <fieldset>
                <legend>Select categories</legend>
                <form:select path="categories" items="${categories}" 
                             itemLabel="categoryName" itemValue="categoryId" 
                             multiple="true" size="5" />
            </fieldset>
            <hr />
            <input type="submit" value="Submit" />
        </form:form> 
    </jsp:body>
</template:admin>