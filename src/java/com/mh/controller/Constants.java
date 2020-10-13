package com.mh.controller;

import com.mh.cake.controller.*;
import com.mh.cake.controller.CreateCakeController;
import com.mh.cake.controller.ViewCakeShopController;
import com.mh.cart.controller.*;
import com.mh.order.controller.TrackingOrderController;
import com.mh.order.controller.ViewOrderDetailController;
import com.mh.user.controller.LoginController;
import com.mh.user.controller.LogoutController;
import com.mh.user.controller.RegisterController;

public class Constants {
    public final static String SHOPPING_PAGE = "shop.jsp";
    public final static String LOGIN_PAGE = "login.jsp";
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
    public final static String CREATE_CAKE_PAGE = "create_cake_page.jsp";
    public final static String UPDATE_CAKE_CONTROLLER = UpdateCakeController.class.getSimpleName();
    public final static String UPDATE_CAKE_PAGE = "edit_cake_page.jsp";
    public final static String ORDER_DETAIL_PAGE = "order_detail_page.jsp";
    public final static String TRACKING_ORDER_PAGE = "tracking_order_page.jsp";
    public final static String TRACKING_ORDER_CONTROLLER = TrackingOrderController.class.getSimpleName();
    public final static String TRACKING_ORDER_RESULT_PAGE = "tracking_order_result_page.jsp";
    public final static String VIEW_ORDER_CONTROLLER = ViewOrderDetailController.class.getSimpleName();
    public final static String VIEW_CAKE_DETAIL_CONTROLLER = ViewCakeDetailController.class.getSimpleName();
    public final static String SHOP_ADMIN_PAGE = "shop_admin.jsp";


    public static final int ADMIN = 2;
    public static final int MEMBER = 1;
    public static final int GUEST = 0;
}
