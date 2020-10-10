/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.jpa_controller;

import com.mh.entity.Cake;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mh.entity.Category;
import com.mh.entity.Users;
import com.mh.jpa_controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author saost
 */
public class CakeJpaController implements Serializable {

    public CakeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cake cake) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryId = cake.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getId());
                cake.setCategoryId(categoryId);
            }
            Users modifyUserId = cake.getModifyUserId();
            if (modifyUserId != null) {
                modifyUserId = em.getReference(modifyUserId.getClass(), modifyUserId.getId());
                cake.setModifyUserId(modifyUserId);
            }
            em.persist(cake);
            if (categoryId != null) {
                categoryId.getCakeCollection().add(cake);
                categoryId = em.merge(categoryId);
            }
            if (modifyUserId != null) {
                modifyUserId.getCakeCollection().add(cake);
                modifyUserId = em.merge(modifyUserId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cake cake) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cake persistentCake = em.find(Cake.class, cake.getId());
            Category categoryIdOld = persistentCake.getCategoryId();
            Category categoryIdNew = cake.getCategoryId();
            Users modifyUserIdOld = persistentCake.getModifyUserId();
            Users modifyUserIdNew = cake.getModifyUserId();
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getId());
                cake.setCategoryId(categoryIdNew);
            }
            if (modifyUserIdNew != null) {
                modifyUserIdNew = em.getReference(modifyUserIdNew.getClass(), modifyUserIdNew.getId());
                cake.setModifyUserId(modifyUserIdNew);
            }
            cake = em.merge(cake);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getCakeCollection().remove(cake);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getCakeCollection().add(cake);
                categoryIdNew = em.merge(categoryIdNew);
            }
            if (modifyUserIdOld != null && !modifyUserIdOld.equals(modifyUserIdNew)) {
                modifyUserIdOld.getCakeCollection().remove(cake);
                modifyUserIdOld = em.merge(modifyUserIdOld);
            }
            if (modifyUserIdNew != null && !modifyUserIdNew.equals(modifyUserIdOld)) {
                modifyUserIdNew.getCakeCollection().add(cake);
                modifyUserIdNew = em.merge(modifyUserIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cake.getId();
                if (findCake(id) == null) {
                    throw new NonexistentEntityException("The cake with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cake cake;
            try {
                cake = em.getReference(Cake.class, id);
                cake.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cake with id " + id + " no longer exists.", enfe);
            }
            Category categoryId = cake.getCategoryId();
            if (categoryId != null) {
                categoryId.getCakeCollection().remove(cake);
                categoryId = em.merge(categoryId);
            }
            Users modifyUserId = cake.getModifyUserId();
            if (modifyUserId != null) {
                modifyUserId.getCakeCollection().remove(cake);
                modifyUserId = em.merge(modifyUserId);
            }
            em.remove(cake);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cake> findCakeEntities() {
        return findCakeEntities(true, -1, -1);
    }

    public List<Cake> findCakeEntities(int maxResults, int firstResult) {
        return findCakeEntities(false, maxResults, firstResult);
    }

    private List<Cake> findCakeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cake.class));
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

    public Cake findCake(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cake.class, id);
        } finally {
            em.close();
        }
    }

    public int getCakeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cake> rt = cq.from(Cake.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
