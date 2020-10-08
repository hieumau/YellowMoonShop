package com.mh.controller;

import com.mh.entity.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProcessLib {
    public static final int ADMIN = 2;
    public static final int MEMBER = 1;
    public static final int GUEST = 0;

    public static Users getAuthUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("AUTH_USER");

        if (user == null || session == null)
        return null;
        else return user;
    }

    public static int getUserRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("AUTH_USER");

        if (user == null || session == null)
            return GUEST;
        else if (user.getRoleId().getId() == MEMBER) return MEMBER;
        else return ADMIN;
    }

    
}
