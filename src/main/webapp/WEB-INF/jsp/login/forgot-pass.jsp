<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
 
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
    <title>WD: Сброс пароля</title>
</head>

<body>
    <div class="splash-container">
        <div class="card">
            <div class="card-header text-center"><img class="logo-img" src="${pageContext.request.contextPath}/assets/images/logo.png" alt="logo"><span class="splash-description">Пожалуйста, введите информацию.</span></div>
            <div class="card-body">
                <form>
                    <p>Не переживайте, мы вышлем вам email, чтобы сбросить пароль</p>
                    <div class="form-group">
                        <input class="form-control form-control-lg" type="email" name="email" required="" placeholder="Ваш E-mail" autocomplete="off">
                    </div>
                    <div class="form-group pt-1"><a class="btn btn-block btn-primary btn-xl" href="">Сбросить пароль</a></div>
                </form>
            </div>
            <div class="card-footer text-center">
                <span>Нет аккаунта?<a href="${pageContext.request.contextPath}/login/sign-up"> Зарегистрируйтесь</a></span>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
</body>

 
</html>
