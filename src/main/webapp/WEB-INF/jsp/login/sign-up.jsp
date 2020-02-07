<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru">
 
<head>
    <meta charset="utf-8">
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
    <title>WD: Регистрация</title>
</head>
<body>
    <form class="splash-container" method="post" action="${pageContext.request.contextPath}/register">
        <div class="card">
            <div class="card-header">
                <h3 class="mb-1">Форма Регистрации</h3>
                <p>Пожалуйста, введите информацию</p>
            </div>
            <div class="card-body">
                <div class="form-group">
                    <input class="form-control form-control-lg" type="text" name="username" required placeholder="Логин (6-20 символов)" autocomplete="off" pattern="[a-zA-Z]+" minlength="5" maxlength="20" title="Логин может содержать только символы латиницы. Длина логина от 5 до 20 символов">
                </div>
                <div class="form-group">
                    <input class="form-control form-control-lg" type="email" name="email" required placeholder="E-mail" autocomplete="off">
                </div>
                <div class="form-group">
                    <input class="form-control form-control-lg" id="pass1" type="password" required placeholder="Пароль (7-20 символов)" pattern="[0-9a-zA-Z!@#$%^&*]+" minlength="7" title="Пароль может содержать цифры, символы латиницы, спец.символы. Длина пароля от 7 символов">
                </div>
                <div class="form-group">
                    <input class="form-control form-control-lg" id="pass2" type="password" required placeholder="Повторите пароль" title="Пароль должен совпадать">
                </div>
                <div class="form-group pt-2">
                    <button class="btn btn-block btn-primary" id="submit" type="submit">Зарегистрироваться</button>
                </div>
                <div class="form-group">
                    <label class="custom-control custom-checkbox">
                        <input class="custom-control-input" type="checkbox" required><span class="custom-control-label">Регистрируя аккаут вы соглашаетесь с <a href="#">пользовательским соглашением</a></span>
                    </label>
                </div>
            </div>
            <div class="card-footer bg-white">
                <p>Уже зарегистрированы? <a href="${pageContext.request.contextPath}/login" class="text-secondary">Войдите здесь.</a></p>
            </div>
        </div>
    </form>
</body>

<script src="${pageContext.request.contextPath}/assets/js/validate.js"></script>

 
</html>
