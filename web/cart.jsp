<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : cart
    Created on : Oct 11, 2020, 5:45:28 PM
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
                    <h1>Shopping Cart</h1>
                    <nav class="d-flex align-items-center">
                        <a href="index.html">Home<span class="lnr lnr-arrow-right"></span></a>
                        <a href="category.html">Cart</a>
                    </nav>
                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->

    <!--================Cart Area =================-->
    <section class="cart_area">
        <div class="container">
            <div class="cart_inner">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Product</th>
                            <th scope="col">Price</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Total</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="cart" value="${sessionScope.CART.cart}"></c:set>

                        <form action="MainController" >
                            <%--Cart Items Start--%>
                            <c:forEach var="item" items="${cart}">
                                <input type="hidden" name="cakeId" value="${item.key.id}">
                                <tr>
                                    <td>
                                        <div class="media">
                                            <div class="d-flex">
                                                <img src="images/${item.key.imagePath}" alt="">
                                            </div>
                                            <div class="media-body">
                                                <p>${item.key.name}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <h5>$${item.key.price}</h5>
                                    </td>
                                    <td>
                                        <div class="product_count">
                                            <input type="number" name="quantity" id="sst${item.key.id}" maxlength="3" value="${item.value}" title="Quantity:"
                                                   class="input-text qty" required>
                                            <button onclick="var result = document.getElementById('sst${item.key.id}'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;"
                                                    class="increase items-count" type="button"><i class="lnr lnr-chevron-up"></i></button>
                                            <button onclick="var result = document.getElementById('sst${item.key.id}'); var sst = result.value; if( !isNaN( sst ) &amp;&amp; sst > 0 ) result.value--;return false;"
                                                    class="reduced items-count" type="button"><i class="lnr lnr-chevron-down"></i></button>
                                        </div>
                                    </td>
                                    <td>
                                        <h5>$${item.key.price * item.value}</h5>
                                    </td>
                                </tr>
                            </c:forEach>
                            <%--Cart Items End--%>

                        <tr class="out_button_area">
                            <td>

                            </td>
                            <td>

                            </td>
                            <td>

                            </td>
                            <td>
                                <div class="checkout_btn_inner d-flex align-items-center">
                                    <c:if test="${cart.size() == 0}">
                                        <button onclick="window.location.href='MainController?btnAction=View cake shop'" class="primary-btn" type="submit">By Some Thing</button>
                                    </c:if>
                                    <c:if test="${cart.size() != 0}">
                                        <button name="btnAction" value="Update cart" class="primary-btn" type="submit">Apply update</button>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>

                            </td>
                            <td>

                            </td>
                            <td>
                                <h5>Subtotal</h5>
                            </td>
                            <td>
                                <h5>$${sessionScope.CART.total}</h5>
                            </td>
                        </tr>
                        </form>
                        <tr class="out_button_area">
                            <td>

                            </td>
                            <td>

                            </td>
                            <td>

                            </td>
                            <td>
                                <div class="checkout_btn_inner d-flex align-items-center">
                                    <a class="gray_btn" href="MainController?btnAction=View cake shop">Continue Shopping</a>
                                    <c:if test="${cart.size() != 0}">
                                        <a class="primary-btn" href="MainController?btnAction=Proceed to checkout">Proceed to checkout</a>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
    <!--================End Cart Area =================-->
    </body>
<%@include file="user_footer.jsp"%>
</html>
