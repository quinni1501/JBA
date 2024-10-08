<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
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
    input[type="text"], input[type="file"], select, textarea {
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
    .btn-update {
        background-color: #f4511e;
        color: white;
    }
    .btn-update:hover {
        background-color: #e64514;
    }
</style>

<div class="form-container">
    <form action="<c:url value='/admin/video/update'/>" method="post" enctype="multipart/form-data">
        <input type="hidden" id="videoId" name="videoId" value="${vid.videoId}">
        
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${vid.title}">
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4">${vid.description}</textarea>
        </div>

        <div class="form-group">
            <label for="categoryId">Category:</label>
            <select id="categoryId" name="categoryId">
                <c:forEach items="${listCategories}" var="category">
                    <option value="${category.categoryid}" ${vid.category.categoryid == category.categoryid ? 'selected' : ''}>
                        ${category.categoryname}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="poster">Poster (Image URL):</label>
            <input type="text" id="poster" name="poster" value="${vid.poster}">
        </div>

        <div class="form-group">
            <label for="images1">Upload New File:</label>
            <input type="file" id="images1" name="images1">
        </div>

        <div class="form-group">
            <label for="views">Views:</label>
            <input type="text" id="views" name="views" value="${vid.views}">
        </div>

        <div class="form-group">
            <label>Status:</label>
            <input type="radio" id="active" name="active" value="1" ${vid.active == 1 ? 'checked' : ''}>
            <label for="active">Active</label>
            <input type="radio" id="inactive" name="active" value="0" ${vid.active == 0 ? 'checked' : ''}>
            <label for="inactive">Inactive</label>
        </div>

        <input type="submit" value="Update" class="btn btn-update">
    </form>
</div>
