<%-- 
    Document   : order_detail_page
    Created on : Oct 12, 2020, 9:16:36 PM
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
                    <h1>Confirmation</h1>

                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->

    <!--================Order Details Area =================-->
    <c:set var="cart" value="${requestScope.CART}"></c:set>
    <c:set var="order" value="${requestScope.CART.order}"></c:set>

    <section class="order_details section_gap">
        <div class="container">
            <h3 class="title_confirmation">Your Order</h3>
            <div class="row order_d_inner">
                <div class="col-lg-6">
                    <div class="details_item">
                        <h4>Order Info</h4>
                        <ul class="list">
                            <li><a href="#"><span>Order number</span> : ${order.id}</a></li>
                            <li><a href="#"><span>Date</span> : ${order.time.toString()}</a></li>
                            <li><a href="#"><span>Total</span> : ${cart.total}</a></li>
                            <li><a href="#"><span>Payment method</span> : Check and cash payments</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-lg-6">
                    <div class="details_item">
                        <h4>Shipping Address</h4>
                        <ul class="list">
                            <li><a href="#"><span>Reciever Name</span> : ${order.fullName}</a></li>
                            <li><a href="#"><span>Phone number</span> : ${order.phone}</a></li>
                            <li><a href="#"><span>Address </span> : ${order.address}</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="order_details_table">
                <h2>Order Details</h2>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Product</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Total</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="item" items="${cart.cart}">
                            <tr>
                                <td>
                                    <p>${item.key.name}</p>
                                </td>
                                <td>
                                    <h5>x ${item.value}</h5>
                                </td>
                                <td>
                                    <p>$${item.key.price * item.value}</p>
                                </td>
                            </tr>
                        </c:forEach>

                        <tr>
                            <td>
                                <h4>Subtotal</h4>
                            </td>
                            <td>
                                <h5></h5>
                            </td>
                            <td>
                                <p>$${cart.total}</p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h4>Shipping</h4>
                            </td>
                            <td>
                                <h5></h5>
                            </td>
                            <td>
                                <p>Free ship(COD)</p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h4>Total</h4>
                            </td>
                            <td>
                                <h5></h5>
                            </td>
                            <td>
                                <p>$${cart.total}</p>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
    <!--================End Order Details Area =================-->
    </body>
<%@include file="user_footer.jsp"%>
</html>
