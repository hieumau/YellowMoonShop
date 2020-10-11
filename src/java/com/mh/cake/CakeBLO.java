/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cake;

import com.mh.entity.Cake;
import com.mh.entity.Category;
import com.mh.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author saost
 */
public class CakeBLO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("YellowMoonShopPU");

    public static final int MAX_CAKE_PER_PAGE = 10;

    public List<Cake> getCakeListSortByCreateTimeFilterByKeywordAndCategoryAndPriceRange(int page, String keyword, Category category, float minPrice, float maxPrice){
        List<Cake> cakeList = new ArrayList<>();
        EntityManager entityManager = emf.createEntityManager();
        String categorySqlPatch = " ";
        if (category != null){
            categorySqlPatch = "AND cake.categoryId = :category ";
        }

        String sql = "SELECT cake " +
                "FROM Cake cake " +
                "WHERE cake.status = true " +
                "AND cake.quantity > 0 " +
                "AND cake.name LIKE :keyword " +
                "AND cake.price >= :minPrice AND cake.price <= :maxPrice " +
                categorySqlPatch +
                "ORDER BY cake.createDate DESC";
        Query query = entityManager.createQuery(sql, Cake.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        if (category != null)
            query.setParameter("category", category);
        query.setFirstResult((page-1) * MAX_CAKE_PER_PAGE);
        query.setMaxResults(MAX_CAKE_PER_PAGE);
        cakeList = query.getResultList();

        return cakeList;
    }
}
