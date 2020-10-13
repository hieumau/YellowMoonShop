<%-- 
    Document   : edit_cake_page
    Created on : Oct 12, 2020, 9:12:49 PM
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
                    <h1>Product Details Page</h1>
                    <nav class="d-flex align-items-center">
                    </nav>
                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->
    <!--================Single Product Area =================-->
    <c:set var="cake" value="${requestScope.CAKE}"></c:set>
    <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}"></c:set>
    <div class="product_image_area">
        <div class="container">
            <form method="post" action="MainController?btnAction=Update cake&cakeId=${cake.id}"
                  enctype="multipart/form-data" class="row s_product_inner">
                <div class="col-lg-6">
                    <div class="">

                        <div class="single-prd-item">
                            <img id="imageUpload" class="img-fluid" src="images/${cake.imagePath}" alt="" -webkit-filter: drop-shadow(5px 5px 5px #222); filter: drop-shadow(5px 5px 5px #222);>
                        </div>

                    </div>


                    <div class="attachments">
                        <ul>
                            <li>
                                <label class="fileContainer">
                                    <script>
                                        function readImage(input) {
                                            if (input.files && input.files[0]) {
                                                var reader = new FileReader();
                                                reader.onload = function (e) {
                                                    $('#imageUpload')
                                                        .attr('src', e.target.result)
                                                        .width(innerWidth)
                                                        .height(init_autosize());
                                                };

                                                reader.readAsDataURL(input.files[0]);
                                            }
                                        }</script>
                                    <style>
                                        input[type="file"] {
                                            display: none;
                                        }

                                    </style>
                                    <label for="file-upload" class="genric-btn info" >
                                        <i class="fa fa-cloud-upload"></i> Upload Image
                                    </label>
                                    <input id="file-upload" type="file" name="imageUpload" value="" onchange="readImage(this)" />
                                </label>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-5 offset-lg-1">
                    <div class="">
                        <h4 class="mb-30">Cake detail</h4>
                        <div class="mt-10">
                            <input type="text" name="name" value="${cake.name}" placeholder="Cake name" onfocus="this.placeholder = ''" onblur="this.placeholder = 'First Name'"
                                   required class="single-input">
                        </div>

                        <div class="input-group-icon mt-10">
                            <div class="icon"><i class="fa fa-thumb-tack" aria-hidden="true"></i></div>
                            <div class="form-select" id="default-select" >
                                <select name="category" data-placeholder="Category">
                                    <c:forEach var="category" items="${categoryList}">
                                        <c:choose>
                                            <c:when test="${category.id == cake.categoryId.id}">
                                                <option selected value="${category.id}">${category.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${category.id}">${category.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="mt-10">
								<textarea maxlength="100" rows="5" class="single-textarea" name="description" placeholder="Description" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Message'"
                                          required>${cake.description}</textarea>
                        </div>

                        <div class="">
                            <br><h4 class="mb-30">Available</h4>
                            <div class="primary-switch">
                                <c:if test="${cake.status}">
                                    <input type="checkbox" name="status" id="primary-switch" value="true" checked>
                                    <label for="primary-switch"></label>
                                </c:if>
                                <c:if test="${!cake.status}">
                                    <input type="checkbox" name="status" id="primary-switch" value="true">
                                    <label for="primary-switch"></label>
                                </c:if>
                            </div>
                        </div>

                        <div class="">
                            <br><h4 class="mb-30">Create date</h4>
                            <input id="datefield1" type="date" name="createDate" min="1975-01-01"
                                   value="${cake.converCreateDayToString('YYYY-MM-dd')}" placeholder="Expiry date" required>
                            <script>
                                var today = new Date();
                                var dd = today.getDate();
                                var mm = today.getMonth() + 1; //January is 0!
                                var yyyy = today.getFullYear();
                                if (dd < 10) {
                                    dd = '0' + dd
                                }
                                if (mm < 10) {
                                    mm = '0' + mm
                                }
                                today = yyyy + '-' + mm + '-' + dd;
                                document.getElementById("datefield1").setAttribute("max", today);
                            </script>
                        </div>

                        <div class="">
                            <br><h4 class="mb-30">Expiry date</h4>
                            <input id="datefield" type="date" name="expiryDate" min="1975-01-01"
                                   value="${cake.converExpDayToString('YYYY-MM-dd')}" placeholder="Expiry date" required>
                            <script>
                                var today = new Date();
                                var dd = today.getDate();
                                var mm = today.getMonth() + 1; //January is 0!
                                var yyyy = today.getFullYear();
                                if (dd < 10) {
                                    dd = '0' + dd
                                }
                                if (mm < 10) {
                                    mm = '0' + mm
                                }
                                today = yyyy + '-' + mm + '-' + dd;
                                document.getElementById("datefield").setAttribute("min", today);
                            </script>
                        </div>

                        <br><h4 class="mb-30">Price</h4>
                        <div class="product_count">
                            <input type="number" name="price" id="price" max="2000" value="${cake.price}" title="Quantity:" class="input-text qty">
                            <button onclick="var result = document.getElementById('price'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;"
                                    class="increase items-count" type="button"><i class="lnr lnr-chevron-up"></i></button>
                            <button onclick="var result = document.getElementById('price'); var sst = result.value; if( !isNaN( sst ) &amp;&amp; sst > 0 ) result.value--;return false;"
                                    class="reduced items-count" type="button"><i class="lnr lnr-chevron-down"></i></button>
                        </div><span> $</span>

                        <br><h4 class="mb-30">Quantity</h4>
                        <div class="product_count">
                            <input type="number" name="quantity" id="sst" max="10000" value="${cake.quantity}" title="Quantity:" class="input-text qty">
                            <button onclick="var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;"
                                    class="increase items-count" type="button"><i class="lnr lnr-chevron-up"></i></button>
                            <button onclick="var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst ) &amp;&amp; sst > 0 ) result.value--;return false;"
                                    class="reduced items-count" type="button"><i class="lnr lnr-chevron-down"></i></button>
                        </div><span> Unit</span>
                        <div class="card_area d-flex align-items-center">
                            <button type="submit" class="genric-btn  primary-btn">Update</button>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
    <!-- Start related-product Area -->
    <section class="related-product-area section_gap_bottom">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6 text-center">
                    <div class="section-title">
                        <br><br><h2>Last modify</h2>
                        <h3>${cake.modifyUserId.id}</h3>
                        <h4>${cake.modifyDate}</h4>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End related-product Area -->
    <!--================End Single Product Area =================-->
    </body>
    <%@include file="user_footer.jsp"%>

</html>
