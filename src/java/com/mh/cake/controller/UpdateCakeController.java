/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cake.controller;

import com.mh.cake.CakeBLO;
import com.mh.controller.Constants;
import com.mh.controller.ProcessLib;
import com.mh.entity.Cake;
import com.mh.entity.Category;
import com.mh.entity.Users;
import com.mh.user.controller.LogoutController;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mh.controller.Constants.*;
import static com.mh.controller.ProcessLib.*;

/**
 *
 * @author saost
 */
public class UpdateCakeController extends HttpServlet {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());
    private static final String ERROR = VIEW_CAKE_DETAIL_CONTROLLER;
    private static final String SUCCESS = VIEW_CAKE_DETAIL_CONTROLLER;
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
            boolean isValidInfo = true;
            // get admin User
            Users user = getAuthUser(request);

            //init variable
            CakeBLO cakeBLO = new CakeBLO();
            Integer cakeId = null;
            String name;
            String imagePath;
            int categoryId;
            String description;
            boolean status;
            Date createDate;
            Date expriryDate;
            float price;
            int quantity;
            Date modifyDate;


            //decrypt request
            Hashtable<String, FileItem> params = getParamFromEncryptRequest(request);
            if (params != null){
                Cake cake = null;
                if (request.getParameter("cakeId") != null) cakeId = Integer.valueOf(request.getParameter("cakeId"));
                if (cakeId != null){
                    cake = cakeBLO.get(cakeId);
                }
                name = params.get("name").getString("UTF-8");
                categoryId = Integer.parseInt(params.get("category").getString("UTF-8"));
                description = params.get("description").getString();

                if (params.get("status") == null) status = false;
                else if (params.get("status").getString().equals("true")) status = true;
                else status = false;

                createDate = convertStringToDate(params.get("createDate").getString(), "yyyy-MM-dd");
                expriryDate = convertStringToDate(params.get("expiryDate").getString(), "yyyy-MM-dd");
                price = Float.parseFloat(params.get("price").getString());
                quantity = Integer.parseInt(params.get("quantity").getString());
                modifyDate = new Date();

                imagePath = getImage(params.get("imageUpload"));

                //if not upload new image then user the old image
                if (imagePath == null) {
                    if (cake.getImagePath() != null) {
                        imagePath = cake.getImagePath();
                    } else {
                        imagePath = "";
                    }
                }

                //update cake
                cake.setName(name);
                cake.setDescription(description);
                cake.setPrice(BigDecimal.valueOf(price));
                cake.setQuantity(quantity);
                cake.setStatus(status);
                cake.setCategoryId(new Category(categoryId));
                cake.setCreateDate(createDate);
                cake.setExpirationDate(expriryDate);
                cake.setImagePath(imagePath);
                cake.setModifyUserId(user);
                cake.setModifyDate(modifyDate);

                cake  = cakeBLO.update(cake);
                if (cake != null){
                    request.setAttribute("MESSAGE", "Update Successful!");
                    url = "MainController?btnAction=View cake detail&cakeId=" + cake.getId();
                } else {
                    request.setAttribute("MESSAGE", "Some error happen!");
                    url = "ViewCakeDetailController&cakeId=" + cake.getId();
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            response.sendRedirect(url);
        }
    }

    private String getImage(FileItem fileItem) {
        try {
            String itemName = fileItem.getName();
            String fileExt = "";
            if (fileItem.getSize() > 0){
                if (itemName.contains(".")){
                    fileExt = itemName.substring(itemName.lastIndexOf(".") + 1).toLowerCase();
                }
                //check is valid file
                if (fileExt.equals("jpg") || fileExt.equals("png")){
                    String newFileName = counter.getAndIncrement() + "." + fileExt;

                    //save location in server
                    String realPath = getServletContext().getRealPath("/images/") + newFileName;
                    File savedFile = new File(realPath);
                    fileItem.write(savedFile);

                    return newFileName;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
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
