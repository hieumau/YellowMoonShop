package com.mh.controller;

import com.mh.cake.CakeBLO;
import com.mh.cake.CategoryBLO;
import com.mh.cart.Cart;
import com.mh.entity.Cake;
import com.mh.entity.Category;
import com.mh.entity.Users;
import com.mh.jpa_controller.CategoryJpaController;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.mh.controller.Constants.*;

public class ProcessLib {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

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

    public static List<Category> getCategoryList(){
        CategoryBLO categoryBLO = new CategoryBLO();
        return categoryBLO.getCategoryList();
    }

    public static Date convertStringToDate(String s, String pattern){
        try {
            return new SimpleDateFormat(pattern).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String convertDateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static List<Cake> getCakeList(HttpServletRequest request){
        try {
            int page = 1;
            float minPrice = 0;
            float maxPrice = 10000;
            String keyword = "";
            String categoryId = "";
            Category category = null;

            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            if (request.getParameter("sellectedCategory") != null)
                categoryId = request.getParameter("sellectedCategory");
            if (!categoryId.isEmpty() && !categoryId.contains("all")){
                category = new Category(Integer.parseInt(categoryId));
            }

            if (request.getParameter("keyword") != null)
                keyword = request.getParameter("keyword");

            if (request.getParameter("minPrice") != null)
                minPrice = Float.parseFloat(request.getParameter("minPrice"));

            if (request.getParameter("maxPrice") != null)
                maxPrice = Float.parseFloat(request.getParameter("maxPrice"));

            if (getAuthUser(request).getRoleId().getId() == ADMIN)
                return new CakeBLO().getCakeListSortByCreateTimeFilterByKeywordAndCategoryAndPriceRangeForAdmin(
                        page,
                        keyword,
                        category,
                        minPrice,
                        maxPrice);
            else return new CakeBLO().getCakeListSortByCreateTimeFilterByKeywordAndCategoryAndPriceRange(
                    page,
                    keyword,
                    category,
                    minPrice,
                    maxPrice);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static HttpSession getSession(HttpServletRequest request){
        return request.getSession();
    }

    public static Cart getCartFromSession(HttpServletRequest request) {
        Cart cart;

        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("CART");
        if (cart != null){
            return cart;
        } else {
            cart = new Cart();
            session.setAttribute("CART", cart);
        }
        return cart;
    }

    public static Hashtable<String, FileItem> getParamFromEncryptRequest(HttpServletRequest request){
        Hashtable<String, FileItem> params = null;

        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        if (isMultiPart){
            params = new Hashtable<>();

            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

            //get list upload item
            List<FileItem> fileItemList = null;
            try {
                fileItemList = upload.parseRequest(request);
            } catch (Exception e){
                e.printStackTrace();
            }

            //descrypt item list and put to Hashtable
            try {
                for (FileItem fileItem: fileItemList){
                    params.put(fileItem.getFieldName(), fileItem);
                }
            } catch (Exception exception){
                exception.printStackTrace();
            }
        }

        return params;
    }


}
