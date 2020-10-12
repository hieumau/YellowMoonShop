<%-- 
    Document   : proceed_checkout_page
    Created on : Oct 12, 2020, 2:18:58 AM
    Author     : saost
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="user_header.jsp"%>
    <body>
    <!-- Start Banner Area -->
    <section class="banner-area organic-breadcrumb">
        <div class="container">
            <div class="breadcrumb-banner d-flex flex-wrap align-items-center justify-content-end">
                <div class="col-first">
                    <h1>Checkout</h1>
                    <nav class="d-flex align-items-center">
                        <a href="index.html">Home<span class="lnr lnr-arrow-right"></span></a>
                        <a href="single-product.html">Checkout</a>
                    </nav>
                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->

    <c:set var="cart" value="${sessionScope.CART.cart}"></c:set>
    <c:set var="user" value="${sessionScope.AUTH_USER}"></c:set>
    <!--================Checkout Area =================-->
    <section class="checkout_area section_gap">
        <div class="container">
            <div class="billing_details">
                <div class="row">
                    <!--                    <form action="MainController">-->
                    <div class="col-lg-12">
                        <h3>Billing Details</h3>
                        <div class="row contact_form">
                            <div class="col-md-12 form-group p_star">
                                <input type="text" required class="form-control" id="fullName" value="${user.fullName}" name="fullName" placeholder="Full name *">
                            </div>

                            <div class="col-md-12 form-group p_star">
                                <input type="text" required class="form-control" id="address" value="${user.address}" name="adress" placeholder="address *">
                            </div>

                            <div class="col-md-12 form-group">
                                <input type="number" required class="form-control" id="phoneNumber" value="${user.phone}" name="phoneNumber" placeholder="Phone numbe *">
                            </div>

                        </div>
                    </div>
                    <div class="col-lg-12">
                        <div class="order_box">
                            <h2>Your Order</h2>
                            <ul class="list">
                                <li><a href="#">Product <span>Total</span></a></li>

                                <c:forEach items="${cart}" var="item">
                                    <li><a href="#">${item.key.name} <span class="middle">x ${item.value}</span> <span class="last">$${item.key.price * item.value}</span></a></li>
                                </c:forEach>

                            </ul>
                            <ul class="list list_2">
                                <li><a href="#">Subtotal <span>$${sessionScope.CART.total}</span></a></li>
                                <li><a href="#">Shipping <span>Free ship (COD)</span></a></li>
                                <li><a href="#">Total <span>$${sessionScope.CART.total}</span></a></li>
                            </ul>

                            <a class="primary-btn" onclick="checkout()">Checkout</a>
                            <script>
                                function checkout() {
                                    var fullName = document.getElementById('fullName').value;
                                    var address = document.getElementById('address').value;
                                    var phoneNumber = document.getElementById('phoneNumber').value;

                                    window.location.href='MainController?btnAction=Checkout&fullName=' + fullName + '&address=' + address + '&phoneNumber=' + phoneNumber;
                                }
                            </script>
                        </div>
                    </div>
                    <!--                    </form>-->
                </div>
            </div>
        </div>
    </section>
    <!--================End Checkout Area =================-->
    </body>
<%@include file="user_footer.jsp"%>
</html>
