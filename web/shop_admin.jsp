<%-- 
    Document   : shop
    Created on : Oct 7, 2020, 12:02:40 PM
    Author     : saost
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="user_header.jsp"%>
    <body>

    <!-- Start Banner Area -->
    <section class="banner-area organic-breadcrumb">
        <div class="container">
            <div class="breadcrumb-banner d-flex flex-wrap align-items-center justify-content-end">
                <div class="col-first">
                    <h1>Shop Category page</h1>
                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->

    <!-- start main -->
    <div class="container">
        <div class="row">
            <!-- Start Filter Bar -->
            <div class="col-xl-3 col-lg-4 col-md-5">
                <div class="sidebar-filter mt-50">
                    <a href="#" onclick="createCakePage()" class="genric-btn info radius">Create cake</a>

                    <script>
                        function createCakePage() {
                            window.location.href = 'MainController?btnAction=View cake detail';
                        }
                    </script>

                    <div class="common-filter">
                        <div class="head">Price</div>
                        <div class="price-range-area">
                            <div id="price-range"></div>
                            <div class="value-wrapper d-flex">
                                <div class="price">Price:</div>
                                <span>$</span>
                                <div id="lower-value"></div>
                                <div class="to">to</div>
                                <span>$</span>
                                <div id="upper-value">5000</div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="sidebar-categories">
                    <div class="head">Browse Categories</div>
                    <ul>

                        <c:choose>
                            <c:when test="${param.get('sellectedCategory').contains('all')}">
                                <li class="filter-list"><input class="pixel-radio" onchange="reload()" type="radio" name="category" value="all" checked="checked" onclick="handleClick(this)" ><label for="apple">All<span></span></label></li>
                            </c:when>

                            <c:otherwise>
                                <li class="filter-list"><input class="pixel-radio" type="radio" name="category" value="all" onclick="handleClick(this)" ><label for="apple">All<span></span></label></li>
                            </c:otherwise>
                        </c:choose>
                        <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}"></c:set>
                        <c:forEach var="category" items="${categoryList}">
                            <c:choose>
                                <c:when test="${param.get('sellectedCategory').equals(category.id.toString())}">
                                    <li class="filter-list"><input class="pixel-radio" type="radio" name="category" value="${category.id}" checked="checked" onclick="handleClick(this)"><label for="apple">${category.name}<span></span></label></li>
                                </c:when>
                                <c:when test="${param.get('sellectedCategory').equals(category.id.toString())}">
                                    <li class="filter-list"><input class="pixel-radio" type="radio" name="category" value="${category.id}" checked="checked" onclick="handleClick(this)"><label for="apple">${category.name}<span></span></label></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="filter-list"><input class="pixel-radio" type="radio" name="category" value="${category.id}" onclick="handleClick(this)"><label for="apple">${category.name}<span></span></label></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                    <script>
                        function handleClick(myRadio) {
                            var minPrice = document.getElementById('minPrice').value;
                            var maxPrice = document.getElementById('maxPrice').value;

                            document.getElementById("category").value = myRadio.value;
                            window.location.href = 'MainController?btnAction=View cake shop&keyword=${keyword}&minPrice=' + minPrice +'&maxPrice=' + maxPrice + '&sellectedCategory=' + myRadio.value;
                        }
                    </script>
                </div>

            </div>
            <!-- End Filter Bar -->

            <div class="col-xl-9 col-lg-8 col-md-7">

                <section class="lattest-product-area pb-40 category-list">
                    <div class="row">

                        <c:set var="cakeList" value="${requestScope.CAKE_LIST}"></c:set>
                        <c:forEach var="cake" items="${cakeList}">
                            <!-- single product -->
                            <div class="col-lg-4 col-md-6" >
                                <div class="single-product">
                                    <img class="img-fluid" src="images/${cake.imagePath}" alt="">
                                    <div class="product-details">
                                        <h6>${cake.name}</h6>
                                        <p>${cake.description}</p>
                                        <span>Cre date: ${cake.createDateFormated}</span>
                                        <span>Exp date: ${cake.expDateFormated}</span>

                                        <div class="price">
                                            <h6>$${cake.price}</h6>
                                        </div>
                                        <div class="prd-bottom">
                                            <a onclick="viewCakeDetail(${cake.id})" class="social-info">
                                                <span class="ti-bag"></span>
                                                <p class="hover-text">ADD TO BAG</p>
                                            </a>
                                            <a class="social-info">
                                                <span class="">${cake.quantity}</span>
                                                <p class="hover-text">IN STOCK</p>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <script>
                            function viewCakeDetail(cakeId) {
                                window.location.href = 'MainController?btnAction=View cake detail&cakeId=' + cakeId;
                            }
                        </script>
                    </div>
                </section>
                <!-- End Best Seller -->

                <!-- Start Pageing Bar -->
                <div class="filter-bar d-flex flex-wrap align-items-center">
                    <div class="pagination">
                        <a href="#" class="prev-arrow"><i class="fa fa-long-arrow-left" aria-hidden="true"></i></a>
                        <a href="#" class="active">1</a>
                        <a href="#">2</a>
                        <a href="#">3</a>
                        <a href="#" class="dot-dot"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></a>
                        <a href="#">6</a>
                        <a href="#" class="next-arrow"><i class="fa fa-long-arrow-right" aria-hidden="true"></i></a>
                    </div>
                </div>
                <!-- End Pageing Bar -->
            </div>
        </div>
    </div>
    <!-- end main -->
    </body>
<%@include file="user_footer.jsp"%>
</html>
