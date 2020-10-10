package com.mh.controller;

import com.mh.entity.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.mh.controller.Constants.*;

public class ProcessLib {


    public static Users getAuthUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("AUTH_USER");

        if (user == null)
        return null;
        else return user;
    }

    public static int getUserRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("AUTH_USER");

        if (user == null)
            return GUEST;
        else if (user.getRoleId().getId() == MEMBER) return MEMBER;
        else return ADMIN;
    }

    public static HttpSession getSession(HttpServletRequest request){
        return request.getSession();
    }


}
