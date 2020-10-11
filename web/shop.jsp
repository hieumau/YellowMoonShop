<%-- 
    Document   : shop
    Created on : Oct 7, 2020, 12:02:40 PM
    Author     : saost
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <li class="filter-list"><input class="pixel-radio" type="radio" id="apple" name="brand"><label for="apple">Apple<span>(29)</span></label></li>
                        <li class="filter-list"><input class="pixel-radio" type="radio" id="apple" name="brand"><label for="apple">Apple<span>(29)</span></label></li>
                        <li class="filter-list"><input class="pixel-radio" type="radio" id="apple" name="brand"><label for="apple">Apple<span>(29)</span></label></li>
                        <li class="filter-list"><input class="pixel-radio" type="radio" id="apple" name="brand"><label for="apple">Apple<span>(29)</span></label></li>
                        <li class="filter-list"><input class="pixel-radio" type="radio" id="apple" name="brand"><label for="apple">Apple<span>(29)</span></label></li>
                    </ul>
                </div>

            </div>
            <!-- End Filter Bar -->

            <div class="col-xl-9 col-lg-8 col-md-7">
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

                <!-- Start Best Seller -->
                <section class="lattest-product-area pb-40 category-list">
                    <div class="row">
                        <!-- single product -->
                        <div class="col-lg-4 col-md-6" >
                            <div class="single-product">
                                <img class="img-fluid" src="img/product/p1.jpg" alt="">
                                <div class="product-details">
                                    <h6>addidas New Hammer sole
                                        for Sports person</h6>
                                    <div class="price">
                                        <h6>$150.00</h6>
                                    </div>
                                    <div class="prd-bottom">

                                        <a href="" class="social-info">
                                            <span class="ti-bag"></span>
                                            <p class="hover-text">add to bag</p>
                                        </a>

                                    </div>
                                </div>
                            </div>
                        </div>

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
