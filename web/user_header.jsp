<%-- 
    Document   : user_header
    Created on : Oct 9, 2020, 9:42:15 AM
    Author     : saost
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <!-- Mobile Specific Meta -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon-->
    <link rel="shortcut icon" href="img/fav.png">
    <!-- Author Meta -->
    <meta name="author" content="CodePixar">
    <!-- Meta Description -->
    <meta name="description" content="">
    <!-- Meta Keyword -->
    <meta name="keywords" content="">
    <!-- meta character set -->
    <meta charset="UTF-8">
    <!-- Site Title -->
    <title>Karma Shop</title>

    <!--
        CSS
        ============================================= -->
    <link rel="stylesheet" href="css/linearicons.css">
    <link rel="stylesheet" href="css/owl.carousel.css">
    <link rel="stylesheet" href="css/themify-icons.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/nice-select.css">
    <link rel="stylesheet" href="css/nouislider.min.css">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>

<!-- Start Header Area -->
<header class="header_area sticky-header">
    <div class="main_menu">
        <nav class="navbar navbar-expand-lg navbar-light main_box">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <a class="navbar-brand logo_h" href="index.html"><img src="img/logo.png" alt=""></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse offset" id="navbarSupportedContent">
                    <ul class="nav navbar-nav menu_nav ml-auto">
                        <li class="nav-item submenu dropdown">
                            <a onclick="window.location.href='MainController?btnAction=View cake shop'" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                               aria-expanded="false">Shop</a>
                        </li>

                        <li class="nav-item submenu dropdown">
                            <a onclick="window.location.href='MainController?btnAction=Track order'" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                               aria-expanded="false">Tracking</a>
                        </li>

                        <li class="nav-item submenu dropdown">
                            <c:if test="${not empty sessionScope.AUTH_USER}">

                                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                                   aria-expanded="false">${sessionScope.AUTH_USER.fullName}</a>
                                <ul class="dropdown-menu">
                                    <li class="nav-item"><a class="nav-link" onclick="window.location.href='MainController?btnAction=Track order'">Tracking</a></li>
                                    <li class="nav-item"><a class="nav-link" onclick="window.location.href='MainController?btnAction=Logout'">Logout</a></li>
                                </ul>

                            </c:if>
                            <c:if test="${empty sessionScope.AUTH_USER}">
                                <a onclick="window.location.href='MainController?btnAction=View login page'" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                                   aria-expanded="false">Login / Register</a>
                            </c:if>

                        </li>

                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="nav-item"><a href="MainController?btnAction=View cart" class="cart"><span class="ti-bag"></span></a></li>
                        <li class="nav-item">
                            <button class="search"><span class="lnr lnr-magnifier" id="search"></span></button>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>


    <c:set var="keyword" value="${param.get('keyword')}"></c:set>
    <c:set var="minPrice" value="${param.get('minPrice')}"></c:set>
    <c:set var="maxPrice" value="${param.get('maxPrice')}"></c:set>
    <c:set var="sellectedCategory" value="${param.get('sellectedCategory')}"></c:set>
    <c:set var="page" value="${param.get('page')}"></c:set>

    <div class="search_input" id="search_input_box">
        <div class="container">
            <form action= "MainController" class="d-flex justify-content-between">
                <input type="text" class="form-control" name="keyword" value="${keyword}" id="search_input" placeholder="Search Here">
                <input type="hidden" id="minPrice" name="minPrice" value="0">
                <input type="hidden" id="maxPrice" name="maxPrice" value="5000">
                <input type="hidden" id="category" name="sellectedCategory" value="${sellectedCategory}">
                <button type="submit" name="btnAction" value="View cake shop" class="btn"></button>
                <span class="lnr lnr-cross" id="close_search" title="Close Search"></span>
            </form>
        </div>
    </div>

    <script>
        function reloadClickCategory(category) {
            var minPrice = document.getElementById('minPrice').value;
            var maxPrice = document.getElementById('maxPrice').value;

            window.location.href = 'MainController?btnAction=View cake shop&keyword=${keyword}&minPrice=' + minPrice+ '&maxPrice=' + maxPrice + '&sellectedCategory=' + category;
        }
    </script>
</header>
<!-- End Header Area -->

</body>
</html>