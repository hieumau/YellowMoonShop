package com.mh.controller;

import com.mh.cake.CakeBLO;
import com.mh.cake.CategoryBLO;
import com.mh.entity.Cake;
import com.mh.entity.Category;
import com.mh.entity.Users;
import com.mh.jpa_controller.CategoryJpaController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

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

    public static List<Category> getCategoryList(){
        CategoryBLO categoryBLO = new CategoryBLO();
        return categoryBLO.getCategoryList();
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

            return new CakeBLO().getCakeListSortByCreateTimeFilterByKeywordAndCategoryAndPriceRange(
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



}
