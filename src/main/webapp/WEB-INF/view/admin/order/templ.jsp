<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Creat User - SB Admin</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Manage Users</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item active">User</li>
                </ol>
                <div class=" mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3 >creat a user</h3>
                            <hr/>

                            <form:form method="post"
                                       action="/admin/user/create"
                                       modelAttribute="newUser">
                                <div class="mb-3">
                                    <label class="form-label">Email: </label>
                                    <form:input type="email" class="form-control" path="email"/>

                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Password: </label>
                                    <form:input type="password" class="form-control" path="password"/>
                                </div>
                                <div class="mb-3 ">
                                    <label class="form-label">Phone number: </label>
                                    <form:input type="text" class="form-control" path="phone"/>
                                </div>
                                <div class="mb-3 ">
                                    <label class="form-label">Full name: </label>
                                    <form:input type="text" class="form-control" path="fullName"/>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Adress: </label>
                                    <form:input type="text" class="form-control" path="address"/>
                                </div>
                                <div class="mb-3">
                                    <label for="formFile" class="form-label">Default file input example</label>
                                    <input class="form-control" type="file" id="formFile">
                                </div>
                                <select class="form-select" aria-label="Default select example">
                                    <option selected>Open this select menu</option>
                                    <option value="1">One</option>
                                    <option value="2">Two</option>
                                    <option value="3">Three</option>
                                </select>

                                <button type="submit" class=" btn btn-primary">Submit</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
</body>
</html>
