<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap&subset=cyrillic-ext" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/fonts/fontawesome/css/fontawesome-all.css">
    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: flex;
            -ms-flex-align: center;
            align-items: center;
            padding-top: 40px;
            padding-bottom: 40px;
        }
    </style>
    <title>WD: Авторизация</title>
</head>
<body>
<div class="splash-container">
    <div class="card ">
        <div class="card-header text-center"><a href="${pageContext.request.contextPath}/main"><img class="logo-img" src="${pageContext.request.contextPath}/assets/images/logo.png" alt="logo"></a><span class="splash-description">Пожалуйста, введите свои данные</span>
            <c:forEach var="error" items="${errors}">
                <div class="text-danger">${error}</div> <br>
            </c:forEach>
        </div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/auth">
                <div class="form-group">
                    <input class="form-control form-control-lg" name="username" id="username" type="text" placeholder="Логин" autocomplete="off" required pattern="[a-zA-Z]+" minlength="5" maxlength="20" title="Логин или почта">
                </div>
                <div class="form-group">
                    <input class="form-control form-control-lg" name="password" id="password" type="password" placeholder="Пароль" required>
                </div>
                <div class="form-group">
                    <label class="custom-control custom-checkbox">
                        <input class="custom-control-input" type="checkbox"><span class="custom-control-label">Запомнить меня</span>
                    </label>
                </div>
                <button type="submit" class="btn btn-primary btn-lg btn-block">Войти</button>
            </form>
        </div>
        <div class="card-footer bg-white p-0">
            <div class="card-footer-item card-footer-item-bordered">
                <a href="${pageContext.request.contextPath}/login/sign-up" class="footer-link">Создать аккаунт</a></div>
            <div class="card-footer-item card-footer-item-bordered">
                <a href="${pageContext.request.contextPath}/login/forgot-password" class="footer-link">Забыли пароль?</a>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
