package com.mh.controller;

import com.mh.cake.controller.CreateCakeController;
import com.mh.cake.controller.ViewCakeShopController;
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


    public static final int ADMIN = 2;
    public static final int MEMBER = 1;
    public static final int GUEST = 0;
}
