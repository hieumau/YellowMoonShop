<%-- 
    Document   : login.jsp
    Created on : Oct 7, 2020, 12:35:14 PM
    Author     : saost
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="user_header.jsp"%>

<!-- Start Banner Area -->
<section class="banner-area organic-breadcrumb">
 <div class="container">
  <div class="breadcrumb-banner d-flex flex-wrap align-items-center justify-content-end">
   <div class="col-first">
    <h1>Login/Register</h1>
   </div>
  </div>
 </div>
</section>
<!-- End Banner Area -->

<!--================Login Box Area =================-->
<section class="login_box_area section_gap">
 <div class="container">
  <div class="row">
   <!--				login area-->
   <div class="col-lg-6">
    <div class="login_form_inner">
     <h3>Log in</h3>
     <form class="row login_form" action="MainController" method="post" id="contactForm" novalidate="novalidate">
      <div class="col-md-12 form-group">
       <span>${sessionScope.ERROR_MESSAGE}</span>
       <input type="text" class="form-control" name="userId" placeholder="Username" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Username'" required>
      </div>
      <div class="col-md-12 form-group">
       <input type="password" class="form-control" name="password" placeholder="Password" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'" required>
      </div>

      <div class="col-md-12 form-group">
       <button type="submit" value="Login" name="btnAction" class="primary-btn">Log In</button>
      </div>
     </form>
    </div>
   </div>

   <!--				register area-->
   <c:set var="error" value="${requestScope.USER_ERROR}"></c:set>
   <div class="col-lg-6">
    <div class="login_form_inner">
     <h3>Register</h3>
     <h6>${requestScope.SUCCESS_MESSAGE}</h6>
     <form class="row login_form" action="MainController" method="post"  novalidate="novalidate">
      <div class="col-md-12 form-group"><span>${error.userIdError}</span>
       <input type="text" class="form-control" name="userId" placeholder="Username" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Username'">
      </div>
      <div class="col-md-12 form-group"><span>${error.passwordError}</span>
       <input type="password" class="form-control"  name="password" placeholder="Password" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'">
      </div>

      <div class="col-md-12 form-group"><span>${error.passwordRepeatError}</span>
       <input type="password" class="form-control"  name="passwordRepeat" placeholder="Re-Password" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Re-Password'">
      </div>

      <div class="col-md-12 form-group"><span>${error.fullNameError}</span>
       <input type="text" class="form-control" name="fullName" placeholder="Full Name" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Full Name'">
      </div>

      <div class="col-md-12 form-group"><span>${error.addressError}</span>
       <input type="text" class="form-control" name="address" placeholder="Address" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Address'">
      </div>

      <div class="col-md-12 form-group"><span>${error.phoneNumberError}</span>
       <input type="number" class="form-control"  name="phoneNumber" placeholder="Phone number" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Phone number'">
      </div>

      <div class="col-md-12 form-group">
       <button type="submit" value="Register" name="btnAction" class="primary-btn">Register</button>
      </div>
     </form>
    </div>
   </div>
  </div>
 </div>
</section>
<!--================End Login Box Area =================-->

<%@include file="user_footer.jsp"%>
</html>
