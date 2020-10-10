/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.user.controller;

import com.mh.controller.Constants;
import com.mh.entity.Users;
import com.mh.user.UserBLO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saost
 */
public class LoginController extends HttpServlet {
    private static final String ERROR = Constants.LOGIN_PAGE;
    private static final String ADMIN = Constants.CREATE_CAKE_PAGE;
    private static final String MEMBER = Constants.SHOPPING_PAGE;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;

        try {
            //clear session
            String userID = request.getParameter("userId");
            String password = request.getParameter("password");
            UserBLO usersBLO = new UserBLO();
            Users user = usersBLO.checkLogin(userID, password);
            if (user != null) {
                if (user.getRoleId().getId() == Constants.ADMIN) {
                    url = ADMIN;
                } else if (user.getRoleId().getId() == Constants.MEMBER){
                    url = MEMBER;
                }
                HttpSession session = request.getSession();
                session.setAttribute("AUTH_USER", user);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("ERROR_MESSAGE", "Wrong username or password!");
            }

        } catch (Exception e) {
            log("Error at LoginServlet " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
