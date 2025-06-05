<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Access Denied</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center" style="min-height: 100vh;">

<div class="container text-center">
    <div class="card shadow-lg p-4">
        <div class="card-body">
            <h1 class="display-4 text-danger"><i class="bi bi-shield-exclamation"></i> 403</h1>
            <p class="lead">Bạn không có quyền truy cập trang này.</p>
            <p>Vui lòng đăng nhập bằng tài khoản có quyền hoặc quay lại trang chủ.</p>
            <a href="/" class="btn btn-primary">Về trang chủ</a>
        </div>
    </div>
</div>

<!-- Bootstrap Icons (optional) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</body>
</html>
