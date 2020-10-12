package com.mh.controller;

import com.mh.cake.controller.CreateCakeController;
import com.mh.cake.controller.CreateCakeController;
import com.mh.cake.controller.ViewCakeShopController;
import com.mh.cake.controller.ViewCakeShopController;
import com.mh.cart.controller.*;
import com.mh.user.controller.LoginController;
import com.mh.user.controller.LogoutController;
import com.mh.user.controller.RegisterController;

public class Constants {
    public final static String SHOPPING_PAGE = "shop.jsp";
    public final static String LOGIN_PAGE = "login.jsp";
    public final static String CREATE_CAKE_PAGE = "create_cake.jsp";
    public final static String LOGIN_CONTROLLER = LoginController.class.getSimpleName();
    public final static String LOGOUT_CONTROLLER = LogoutController.class.getSimpleName();
    public final static String CREATE_CAKE_CONTROLLER = CreateCakeController.class.getSimpleName();
    public final static String VIEW_CAKE_SHOP_CONTROLLER = ViewCakeShopController.class.getSimpleName();
    public final static String REGISTER_CONTROLLER = RegisterController.class.getSimpleName();
    public final static String VIEW_CART_CONTROLLER = ViewCartController.class.getSimpleName();
    public final static String CHECKOUT_CONTROLLER = CheckoutController.class.getSimpleName();
    public final static String ADD_TO_CART_CONTROLLER = AddToCartController.class.getSimpleName();
    public final static String UPDATE_CART_CONTROLLER = UpdateCartController.class.getSimpleName();
    public final static String PROCEED_CHECKOUT_CONTROLLER = ProceedCheckoutController.class.getSimpleName();
    public final static String PROCEED_CHECKOUT_PAGE = "proceed_checkout_page.jsp";

    public final static String CART_PAGE = "cart.jsp";


    public static final int ADMIN = 2;
    public static final int MEMBER = 1;
    public static final int GUEST = 0;
}
