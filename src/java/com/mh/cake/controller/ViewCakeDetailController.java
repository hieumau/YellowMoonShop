/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cake.controller;

import com.mh.cake.CakeBLO;
import com.mh.controller.ProcessLib;
import com.mh.entity.Cake;
import com.mh.entity.Category;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mh.controller.Constants.*;

/**
 *
 * @author saost
 */
public class ViewCakeDetailController extends HttpServlet {
    private static final String ERROR = UPDATE_CAKE_PAGE;
    private static final String SUCCESS = UPDATE_CAKE_PAGE;
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
            //get category list
            List<Category> categoryList = ProcessLib.getCategoryList();
            request.setAttribute("CATEGORY_LIST", categoryList);

            Integer cakeId = null;
            if (request.getParameter("cakeId") != null){
                cakeId = Integer.parseInt(request.getParameter("cakeId"));
            }

            if (cakeId != null){
                CakeBLO cakeBLO = new CakeBLO();
                Cake cake = cakeBLO.get(cakeId);
                if (cake != null){
                    url = SUCCESS;
                    request.setAttribute("CAKE", cake);
                }
            } else {
                url = CREATE_CAKE_PAGE;
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
