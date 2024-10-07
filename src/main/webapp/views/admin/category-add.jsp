<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
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
    .btn-insert {
        background-color: #f4511e;
        color: white;
    }
    .btn-insert:hover {
        background-color: #e64514;
    }
</style>
<div class="form-container">
    <form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="categoryname">Category Name:</label>
            <input type="text" id="categoryname" name="categoryname">
        </div>
        <div class="form-group">
            <label for="images">Link Images:</label>
            <input type="text" id="images" name="images">
        </div>
        <div class="form-group">
            <label for="images1">Upload File:</label>
            <input type="file" id="images1" name="images1">
        </div>
        <div class="form-group">
            <label>Status:</label>
            <input type="radio" id="ston" name="status" value="1">
            <label for="ston">Hoạt động</label>
            <input type="radio" id="stoff" name="status" value="0">
            <label for="stoff">Khóa</label>
        </div>
        <input type="submit" value="Insert" class="btn btn-insert">
    </form>
</div>
