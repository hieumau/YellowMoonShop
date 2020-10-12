/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cart.controller;

import com.mh.cart.Cart;
import com.mh.entity.Orders;
import com.mh.entity.Users;
import com.mh.order.OrderBLO;
import com.mh.user.UserError;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.criteria.Order;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.mh.controller.Constants.*;
import static com.mh.controller.ProcessLib.getAuthUser;
import static com.mh.controller.ProcessLib.getCartFromSession;

/**
 *
 * @author saost
 */
public class CheckoutController extends HttpServlet {
    private static final String ERROR = VIEW_CART_CONTROLLER;
    private static final String SUCCESS = VIEW_CART_CONTROLLER;
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
            Users user = getAuthUser(request);
            if (user == null || user.getRoleId().getId() == GUEST){
                user = new Users("guest");
            }
            Cart cart = getCartFromSession(request);

            String fullName;
            String address;
            String phoneNumber;
            Boolean isValidInfo = true;
            UserError userError = new UserError();

            //check valid info start
            fullName = request.getParameter("fullName");
            if (fullName == null){
                isValidInfo = false;
                userError.setFullNameError("You should fill your name!");
            } else if (fullName.isEmpty()){
                isValidInfo = false;
                userError.setFullNameError("You should fill your name!");
            }

            address = request.getParameter("address");
            if (address == null){
                isValidInfo = false;
                userError.setAddressError("You should fill your shipping address!");
            } else if (address.isEmpty()){
                isValidInfo = false;
                userError.setAddressError("You should fill your shipping address!");
            }

            phoneNumber = request.getParameter("phoneNumber");
            if (phoneNumber == null){
                isValidInfo = false;
                userError.setPhoneNumberError("You should fill your phone number!!");
            } else if (phoneNumber.isEmpty()){
                isValidInfo = false;
                userError.setPhoneNumberError("You should fill your phone number!!");
            }
            //check valid info end


            if (isValidInfo){
                OrderBLO orderBLO = new OrderBLO();
                Orders order = orderBLO.create(cart, user.getId(), fullName, address, phoneNumber);
                if (order != null){
                    HttpSession session = request.getSession();
                    session.removeAttribute("CART");
                    url = SUCCESS;
                }
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
