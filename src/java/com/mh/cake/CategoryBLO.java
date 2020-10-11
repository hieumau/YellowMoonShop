package com.mh.cake;

import com.mh.entity.Category;
import com.mh.jpa_controller.CategoryJpaController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class CategoryBLO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("YellowMoonShopPU");

    public List<Category> getCategoryList(){
        List<Category> categoryList = new ArrayList<>();
        CategoryJpaController categoryJpaController = new CategoryJpaController(emf);

        int total = categoryJpaController.getCategoryCount();
        categoryList = categoryJpaController.findCategoryEntities(total, 0);
        return categoryList;
    }


}
