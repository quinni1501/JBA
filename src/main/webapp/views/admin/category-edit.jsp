<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
    }
    .form-container {
        width: 50%;
        margin: 0 auto;
        background-color: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    label {
        font-weight: bold;
        margin-bottom: 5px;
        display: block;
        color: #333;
    }
    input[type="text"], input[type="file"] {
        width: 100%;
        padding: 10px;
        margin: 10px 0 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
    }
    input[type="radio"] {
        margin-right: 10px;
    }
    img {
        display: block;
        margin: 20px 0;
    }
    .form-group {
        margin-bottom: 20px;
    }
    .btn {
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
    }
    .btn-update {
        background-color: #f4511e;
        color: white;
    }
    .btn-update:hover {
        background-color: #e64514;
    }
</style>
<div class="form-container">
    <form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data">
        <input type="text" id="categoryid" name="categoryid" value="${cate.categoryid}" hidden>
        <div class="form-group">
            <label for="categoryname">Category Name:</label>
            <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}">
        </div>
        <div class="form-group">
            <label for="images">Image:</label>
            <input type="text" id="images" name="images" value="${cate.images}">
        </div>
        <c:if test="${cate.images.substring(0,5) != 'https'}">
            <c:url value="/image?fname=${cate.images}" var="imgUrl"></c:url>
        </c:if>
        <c:if test="${cate.images.substring(0,5) == 'https'}">
            <c:url value="${cate.images}" var="imgUrl"></c:url>
        </c:if>
        <img id="imagess" height="150" width="200" src="${imgUrl}">
        <div class="form-group">
            <label for="images1">Upload New File:</label>
            <input type="file" id="images1" name="images1" value="${cate.images}">
        </div>
        <div class="form-group">
            <label>Status:</label>
            <input type="radio" id="ston" name="status" value="1" ${cate.status == 1 ? 'checked' : ''}>
            <label for="ston">Hoạt động</label>
            <input type="radio" id="stoff" name="status" value="0" ${cate.status == 0 ? 'checked' : ''}>
            <label for="stoff">Khóa</label>
        </div>
        <input type="submit" value="Update" class="btn btn-update">
    </form>
</div>
