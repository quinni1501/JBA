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
    .add-video {
        display: block;
        text-align: center;
        margin: 20px 0;
        font-weight: bold;
        text-decoration: none;
        color: #f4511e;
    }
    .add-video:hover {
        text-decoration: underline;
    }
</style>

<a href="<c:url value='/admin/video/add'/>" class="add-video">Add Video</a>

<table>
    <tr>
        <th>STT</th>
        <th>Poster</th>
        <th>Video Title</th>
        <th>Description</th>
        <th>Category</th>
        <th>Views</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${listvid}" var="video" varStatus="STT">
        <tr>
            <td>${STT.index + 1}</td>

           
            <c:if test="${video.poster.substring(0,5) == 'https'}">
                <c:url value="${video.poster}" var="imgUrl"></c:url>
            </c:if>

            <c:if test="${video.poster.substring(0,5) != 'https'}">
                <c:url value="/image?fname=${video.poster}" var="imgUrl"></c:url>
            </c:if>

            <td><img src="${imgUrl}" alt="Video Poster"></td>

            <td>${video.title}</td>
            <td>${video.description}</td>
            <td>${video.category.categoryname}</td>
            <td>${video.views}</td>
            <td>${video.active == 1 ? 'Active' : 'Inactive'}</td>
            <td>
                <a href="<c:url value='/admin/video/edit?id=${video.videoId}'/>">Edit</a> | 
                <a href="<c:url value='/admin/video/delete?id=${video.videoId}'/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
