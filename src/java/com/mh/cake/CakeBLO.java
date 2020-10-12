/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cake;

import com.mh.entity.Cake;
import com.mh.entity.Category;
import com.mh.entity.Users;
import com.mh.jpa_controller.CakeJpaController;

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

    public static final int MAX_CAKE_PER_PAGE = 9;

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

    public long getNumberOfCakeByKeywordAndCategoryAndPriceRange(String keyword, Category category, float minPrice, float maxPrice){
        EntityManager entityManager = emf.createEntityManager();

        String categorySqlPatch = " ";
        if (category != null){
            categorySqlPatch = "AND cake.categoryId = :category ";
        }

        String sql = "SELECT count (cake.id) " +
                "FROM Cake cake " +
                "WHERE cake.status = true " +
                "AND cake.quantity > 0 " +
                "AND cake.name LIKE :keyword " +
                "AND cake.price >= :minPrice AND cake.price <= :maxPrice " +
                categorySqlPatch;
        Query query = entityManager.createQuery(sql, Long.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        if (category != null)
            query.setParameter("category", category);

        return (long) query.getSingleResult();
    }

    public Cake get(int cakeId){
        CakeJpaController cakeJpaController = new CakeJpaController(emf);
        return cakeJpaController.findCake(cakeId);
    }

    public void delete(int cakeId){
        CakeJpaController cakeJpaController = new CakeJpaController(emf);
        Cake  cake = get(cakeId);
        cake.setStatus(false);

        try {
            cakeJpaController.edit(cake);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Cake create(Cake cake){
        Date date = new Date();
        CakeJpaController cakeJpaController = new CakeJpaController(emf);

        cakeJpaController.create(cake);

        return null;
    }
}
