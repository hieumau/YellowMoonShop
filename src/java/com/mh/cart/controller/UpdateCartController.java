/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cart.controller;

import com.mh.cart.Cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.mh.controller.Constants.CART_PAGE;
import static com.mh.controller.Constants.LOGIN_PAGE;
import static com.mh.controller.ProcessLib.getCartFromSession;

/**
 *
 * @author saost
 */
public class UpdateCartController extends HttpServlet {
    private static final String ERROR = LOGIN_PAGE;
    private static final String SUCCESS = CART_PAGE;
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
        String url = SUCCESS;
        try {
            Cart cart = getCartFromSession(request);

            List<String> cakeIdList = new ArrayList<>(Arrays.asList(request.getParameterValues("cakeId")));
            List<String> quantityList = new ArrayList<>(Arrays.asList(request.getParameterValues("quantity")));

            for (int i = 0; i < cakeIdList.size(); i++){
                if (!cakeIdList.get(i).isEmpty() && !quantityList.get(i).isEmpty()){
                    cart.update(Integer.parseInt(cakeIdList.get(i)), Integer.parseInt(quantityList.get(i)));
                }
            }

            HttpSession session = request.getSession();
            session.setAttribute("CART", cart);

            url = SUCCESS;
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
