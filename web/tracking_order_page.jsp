<%-- 
    Document   : tracking_order_page
    Created on : Oct 12, 2020, 9:15:39 PM
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
                    <h1>Order Tracking</h1>
                    <nav class="d-flex align-items-center">
                    </nav>
                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->

    <!--================Tracking Box Area =================-->
    <section class="tracking_box_area section_gap">
        <div class="container">
            <div class="tracking_box_inner">
                <p>To track your order please enter your Order ID in the box below and press the "Track" button.</p>
                <form class="row tracking_form" action="MainController" method="post" novalidate="novalidate">
                    <div class="col-md-12 form-group">
                        <input type="number" class="form-control" id="order" value="${param.get("orderId")}" name="orderId" placeholder="Order ID" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Order ID'">
                        <span>${requestScope.MESSAGE}</span>
                    </div>
                    <div class="col-md-12 form-group">
                        <button type="submit" name="btnAction" value="Track order" class="primary-btn">Track Order</button>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <!--================End Tracking Box Area =================-->
    </body>
    <%@include file="user_footer.jsp"%>

</html>
