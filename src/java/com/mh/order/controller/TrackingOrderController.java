/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.order.controller;

import com.mh.cart.Cart;
import com.mh.entity.Users;
import com.mh.order.OrderBLO;

import java.io.IOException;
import java.io.PrintWriter;
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
public class TrackingOrderController extends HttpServlet {
    private static final String ERROR = TRACKING_ORDER_PAGE;
    private static final String SUCCESS = TRACKING_ORDER_RESULT_PAGE;
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
            //get orderId
            int orderId;
            orderId = Integer.parseInt(request.getParameter("orderId"));

            //check User
            Users user = getAuthUser(request);
            if (user.getRoleId().getId() != MEMBER) throw new Exception("Access denied");

            //check order
            OrderBLO orderBLO = new OrderBLO();
            Cart cart = orderBLO.getOrderAndOrderDetailsByUserAndOrderId(user.getId(), orderId);

            if (cart != null) {
                url = SUCCESS;
                request.setAttribute("CART", cart);
            } else {
                url = TRACKING_ORDER_PAGE;
                request.setAttribute("MESSAGE", "Can't find this order!");
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
