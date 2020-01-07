<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru">

<head>
  <title>WD: Главная</title>
  <%--<meta charset="UTF-8">--%>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css">
  <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap&subset=cyrillic-ext" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/fonts/fontawesome/css/fontawesome-all.css">
</head>
<body>

<div class="dashboard-main-wrapper">
  <div class="dashboard-header">
    <nav class="navbar navbar-expand-lg bg-white fixed-top">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/main">WHITE DREAM</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto navbar-right-top">
          <!--<li class="nav-item">
              <div id="custom-search" class="top-search-bar">
                  <input class="form-control" type="text" placeholder="Search..">
              </div>
          </li>-->
          <li class="nav-item dropdown notification">
            <a class="nav-link nav-icons" href="#" id="navbarDropdownMenuLink1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-fw fa-bell"></i> <span class="indicator"></span></a>
            <ul class="dropdown-menu dropdown-menu-right notification-dropdown">
              <li>
                <div class="notification-title">Уведомления</div>
                <div class="notification-list">
                  <div class="list-group">
                    <a href="#" class="list-group-item list-group-item-action active">
                      <div class="notification-info">
                        <div class="notification-list-user-img"><img src="${pageContext.request.contextPath}/assets/images/avatar-system.png" alt="" class="user-avatar-md rounded-circle"></div>
                        <div class="notification-list-user-block"><span class="notification-list-user-name">Jeremy Rakestraw</span> test test test
                          <div class="notification-date">2 min ago</div>
                        </div>
                      </div>
                    </a>
                    <a href="#" class="list-group-item list-group-item-action">
                      <div class="notification-info">
                        <div class="notification-list-user-img"><img src="${pageContext.request.contextPath}/assets/images/avatar-system.png" alt="" class="user-avatar-md rounded-circle"></div>
                        <div class="notification-list-user-block"><span class="notification-list-user-name">John Abraham</span> test test test
                          <div class="notification-date">2 days ago</div>
                        </div>
                      </div>
                    </a>
                    <a href="#" class="list-group-item list-group-item-action">
                      <div class="notification-info">
                        <div class="notification-list-user-img"><img src="${pageContext.request.contextPath}/assets/images/avatar-system.png" alt="" class="user-avatar-md rounded-circle"></div>
                        <div class="notification-list-user-block"><span class="notification-list-user-name">Monaan Pechi</span> test test test
                          <div class="notification-date">2 min ago</div>
                        </div>
                      </div>
                    </a>
                    <a href="#" class="list-group-item list-group-item-action">
                      <div class="notification-info">
                        <div class="notification-list-user-img"><img src="${pageContext.request.contextPath}/assets/images/avatar-system.png" alt="" class="user-avatar-md rounded-circle"></div>
                        <div class="notification-list-user-block"><span class="notification-list-user-name">Jessica Caruso</span> test test test
                          <div class="notification-date">2 min ago</div>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>
              </li>
              <li>
                <div class="list-footer"> <a href="#">Все уведомления</a></div>
              </li>
            </ul>
          </li>
          <li class="nav-item dropdown nav-user">
            <a class="nav-link nav-user-img" href="#" id="navbarDropdownMenuLink2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${pageContext.request.contextPath}/assets/images/avatar-user.png" alt="" class="user-login rounded-circle"></a>
            <div class="dropdown-menu dropdown-menu-right nav-user-dropdown" aria-labelledby="navbarDropdownMenuLink2">
              <div class="nav-user-info">
                <h5 class="mb-0 text-white nav-user-name">UserLogin</h5>
              </div>
              <a class="dropdown-item" href="#"><i class="fas fa-cog mr-2"></i>Настройки</a>
              <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="fas fa-power-off mr-2"></i>Выйти</a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </div>
  <div class="nav-left-sidebar sidebar-dark">
    <div class="menu-list">
      <nav class="navbar navbar-expand-lg navbar-light">
        <a class="d-xl-none d-lg-none" href="#">Dashboard</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav flex-column">
            <li class="nav-divider">
              Меню
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-toggle="collapse" aria-expanded="false" data-target="#submenu-3" aria-controls="submenu-3"><i class="fas fa-fw fa-chart-pie"></i>Мониторинг</a>
              <div id="submenu-3" class="collapse submenu" style="">
                <ul class="nav flex-column">
                  <li class="nav-item">
                    <a class="nav-link" href="${requestScope.a}">Мониторинг DNS в реестре РКН</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link text-muted " href="#">Настройка мониторинга (скоро)</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link text-muted" href="#">Че-нибудь крутое ещё (скоро)</a>
                  </li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  </div>
  <div class="dashboard-wrapper">
    <div class="container-fluid dashboard-content">
      <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
          <h2 class="text-center">Мониторинг DNS в реестре РКН</h2>
          <h4 class="text-center">Вы можете добавить до 3-х адресов для мониторинга их в реестре РКН, а также выбрать способ уведомления.</h4>
          <div class="container">
            <br>
            <h3>Выберите тип и укажите адрес: </h3>
            <hr/>
            <!-- Nav pills -->
            <ul class="nav nav-pills" role="tablist">
              <li class="nav-item">
                <a class="nav-link active" data-toggle="pill" href="#ip1">IP-адрес</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#dns1">DNS-запись</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#url1">URL</a>
              </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
              <div id="ip1" class="container tab-pane active"><br>
                <input type="text" class="form-control form-control">
              </div>
              <div id="dns1" class="container tab-pane fade"><br>
                <input type="text" class="form-control form-control">
              </div>
              <div id="url1" class="container tab-pane fade"><br>
                <input type="text" class="form-control form-control">
              </div>
            </div>
            <hr/>
            <!-- Nav pills -->
            <ul class="nav nav-pills" role="tablist">
              <li class="nav-item">
                <a class="nav-link active" data-toggle="pill" href="#ip2">IP-адрес</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#dns2">DNS-запись</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#url2">URL</a>
              </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
              <div id="ip2" class="container tab-pane active"><br>
                <input type="text" class="form-control form-control">
              </div>
              <div id="dns2" class="container tab-pane fade"><br>
                <input type="text" class="form-control form-control">
              </div>
              <div id="url2" class="container tab-pane fade"><br>
                <input type="text" class="form-control form-control">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="container-fluid">
        <div class="row">
          <div class="col-xl-6 col-lg-6 col-md-12 col-sm-12 col-12">
            Copyright © 2019 WhiteDream. All rights reserved.
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/slimscroll/jquery.slimscroll.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main-js.js"></script>
</body>

</html>