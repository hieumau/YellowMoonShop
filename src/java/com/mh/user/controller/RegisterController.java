/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.user.controller;

import com.mh.controller.Constants;
import com.mh.entity.Users;
import com.mh.user.UserBLO;
import com.mh.user.UserError;

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
public class RegisterController extends HttpServlet {
    private static final String ERROR = Constants.LOGIN_PAGE;
    private static final String SUCCESS = Constants.LOGIN_PAGE;
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
        UserError userError = new UserError();

        try {
            HttpSession session = request.getSession();
            session.setAttribute("ERROR_MESSAGE", "");

            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            String passwordRepeat = request.getParameter("passwordRepeat");
            String fullName = request.getParameter("fullName");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phoneNumber");
            boolean check = true;

            UserBLO userBLO = new UserBLO();

            if (userBLO.isExitsUserId(userId)){
                userError.setUserIdError("This username is used");
                check = false;
            }

            if (!passwordRepeat.equals(password)){
                userError.setPasswordRepeatError("Password is not match");
                check = false;
            }

            if (check) {
                Users user = userBLO.create(userId, password, fullName, address, phoneNumber);
                if (user != null){
                    url = SUCCESS;
                    request.setAttribute("SUCCESS_MESSAGE", "Register successful");
                }
            } else {
                request.setAttribute("USER_ERROR",userError);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
