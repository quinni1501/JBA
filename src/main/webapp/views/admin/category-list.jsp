<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
    }
    table {
        width: 80%;
        margin: 20px auto;
        border-collapse: collapse;
    }
    table, th, td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: center;
    }
    th {
        background-color: #f2f2f2;
        font-weight: bold;
    }
    td img {
        height: 100px;
        width: 120px;
        object-fit: cover;
    }
    a {
        color: #f4511e;
        text-decoration: none;
        font-weight: bold;
    }
    a:hover {
        text-decoration: underline;
    }
    .add-category {
        display: block;
        text-align: center;
        margin: 20px 0;
        font-weight: bold;
        text-decoration: none;
        color: #f4511e;
    }
    .add-category:hover {
        text-decoration: underline;
    }
</style>

<a href="<c:url value='/admin/category/add'/>" class="add-category">Add Category</a>

<table>
    <tr>
        <th>STT</th>
        <th>Images</th>
        <th>Category Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${listcate}" var="cate" varStatus="STT">
        <tr>
            <td>${STT.index + 1}</td>

            <c:if test="${cate.images.substring(0,5) == 'https'}">
                <c:url value="${cate.images}" var="imgUrl"></c:url>
            </c:if>

            <c:if test="${cate.images.substring(0,5) != 'https'}">
                <c:url value="/image?fname=${cate.images}" var="imgUrl"></c:url>
            </c:if>

            <td><img src="${imgUrl}" alt="Category Image"></td>
            <td>${cate.categoryname}</td>
            <td>${cate.status}</td>
            <td>
                <a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>">Sửa</a> | 
                <a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
