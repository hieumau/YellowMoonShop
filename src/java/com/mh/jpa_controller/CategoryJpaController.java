/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.jpa_controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mh.entity.Cake;
import com.mh.entity.Category;
import com.mh.jpa_controller.exceptions.IllegalOrphanException;
import com.mh.jpa_controller.exceptions.NonexistentEntityException;
import com.mh.jpa_controller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author saost
 */
public class CategoryJpaController implements Serializable {

    public CategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Category category) throws PreexistingEntityException, Exception {
        if (category.getCakeCollection() == null) {
            category.setCakeCollection(new ArrayList<Cake>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cake> attachedCakeCollection = new ArrayList<Cake>();
            for (Cake cakeCollectionCakeToAttach : category.getCakeCollection()) {
                cakeCollectionCakeToAttach = em.getReference(cakeCollectionCakeToAttach.getClass(), cakeCollectionCakeToAttach.getId());
                attachedCakeCollection.add(cakeCollectionCakeToAttach);
            }
            category.setCakeCollection(attachedCakeCollection);
            em.persist(category);
            for (Cake cakeCollectionCake : category.getCakeCollection()) {
                Category oldCategoryIdOfCakeCollectionCake = cakeCollectionCake.getCategoryId();
                cakeCollectionCake.setCategoryId(category);
                cakeCollectionCake = em.merge(cakeCollectionCake);
                if (oldCategoryIdOfCakeCollectionCake != null) {
                    oldCategoryIdOfCakeCollectionCake.getCakeCollection().remove(cakeCollectionCake);
                    oldCategoryIdOfCakeCollectionCake = em.merge(oldCategoryIdOfCakeCollectionCake);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategory(category.getId()) != null) {
                throw new PreexistingEntityException("Category " + category + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Category category) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category persistentCategory = em.find(Category.class, category.getId());
            Collection<Cake> cakeCollectionOld = persistentCategory.getCakeCollection();
            Collection<Cake> cakeCollectionNew = category.getCakeCollection();
            List<String> illegalOrphanMessages = null;
            for (Cake cakeCollectionOldCake : cakeCollectionOld) {
                if (!cakeCollectionNew.contains(cakeCollectionOldCake)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cake " + cakeCollectionOldCake + " since its categoryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cake> attachedCakeCollectionNew = new ArrayList<Cake>();
            for (Cake cakeCollectionNewCakeToAttach : cakeCollectionNew) {
                cakeCollectionNewCakeToAttach = em.getReference(cakeCollectionNewCakeToAttach.getClass(), cakeCollectionNewCakeToAttach.getId());
                attachedCakeCollectionNew.add(cakeCollectionNewCakeToAttach);
            }
            cakeCollectionNew = attachedCakeCollectionNew;
            category.setCakeCollection(cakeCollectionNew);
            category = em.merge(category);
            for (Cake cakeCollectionNewCake : cakeCollectionNew) {
                if (!cakeCollectionOld.contains(cakeCollectionNewCake)) {
                    Category oldCategoryIdOfCakeCollectionNewCake = cakeCollectionNewCake.getCategoryId();
                    cakeCollectionNewCake.setCategoryId(category);
                    cakeCollectionNewCake = em.merge(cakeCollectionNewCake);
                    if (oldCategoryIdOfCakeCollectionNewCake != null && !oldCategoryIdOfCakeCollectionNewCake.equals(category)) {
                        oldCategoryIdOfCakeCollectionNewCake.getCakeCollection().remove(cakeCollectionNewCake);
                        oldCategoryIdOfCakeCollectionNewCake = em.merge(oldCategoryIdOfCakeCollectionNewCake);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = category.getId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cake> cakeCollectionOrphanCheck = category.getCakeCollection();
            for (Cake cakeCollectionOrphanCheckCake : cakeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the Cake " + cakeCollectionOrphanCheckCake + " in its cakeCollection field has a non-nullable categoryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(category);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> findCategoryEntities() {
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Category findCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
