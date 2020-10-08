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
import com.mh.entity.Role;
import com.mh.entity.Orders;
import java.util.ArrayList;
import java.util.Collection;
import com.mh.entity.Cake;
import com.mh.entity.Users;
import com.mh.jpa_controller.exceptions.NonexistentEntityException;
import com.mh.jpa_controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author saost
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, Exception {
        if (users.getOrdersCollection() == null) {
            users.setOrdersCollection(new ArrayList<Orders>());
        }
        if (users.getCakeCollection() == null) {
            users.setCakeCollection(new ArrayList<Cake>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role roleId = users.getRoleId();
            if (roleId != null) {
                roleId = em.getReference(roleId.getClass(), roleId.getId());
                users.setRoleId(roleId);
            }
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : users.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            users.setOrdersCollection(attachedOrdersCollection);
            Collection<Cake> attachedCakeCollection = new ArrayList<Cake>();
            for (Cake cakeCollectionCakeToAttach : users.getCakeCollection()) {
                cakeCollectionCakeToAttach = em.getReference(cakeCollectionCakeToAttach.getClass(), cakeCollectionCakeToAttach.getId());
                attachedCakeCollection.add(cakeCollectionCakeToAttach);
            }
            users.setCakeCollection(attachedCakeCollection);
            em.persist(users);
            if (roleId != null) {
                roleId.getUsersCollection().add(users);
                roleId = em.merge(roleId);
            }
            for (Orders ordersCollectionOrders : users.getOrdersCollection()) {
                Users oldUserIdOfOrdersCollectionOrders = ordersCollectionOrders.getUserId();
                ordersCollectionOrders.setUserId(users);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldUserIdOfOrdersCollectionOrders != null) {
                    oldUserIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldUserIdOfOrdersCollectionOrders = em.merge(oldUserIdOfOrdersCollectionOrders);
                }
            }
            for (Cake cakeCollectionCake : users.getCakeCollection()) {
                Users oldModifyUserIdOfCakeCollectionCake = cakeCollectionCake.getModifyUserId();
                cakeCollectionCake.setModifyUserId(users);
                cakeCollectionCake = em.merge(cakeCollectionCake);
                if (oldModifyUserIdOfCakeCollectionCake != null) {
                    oldModifyUserIdOfCakeCollectionCake.getCakeCollection().remove(cakeCollectionCake);
                    oldModifyUserIdOfCakeCollectionCake = em.merge(oldModifyUserIdOfCakeCollectionCake);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getId()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            Role roleIdOld = persistentUsers.getRoleId();
            Role roleIdNew = users.getRoleId();
            Collection<Orders> ordersCollectionOld = persistentUsers.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = users.getOrdersCollection();
            Collection<Cake> cakeCollectionOld = persistentUsers.getCakeCollection();
            Collection<Cake> cakeCollectionNew = users.getCakeCollection();
            if (roleIdNew != null) {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getId());
                users.setRoleId(roleIdNew);
            }
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            users.setOrdersCollection(ordersCollectionNew);
            Collection<Cake> attachedCakeCollectionNew = new ArrayList<Cake>();
            for (Cake cakeCollectionNewCakeToAttach : cakeCollectionNew) {
                cakeCollectionNewCakeToAttach = em.getReference(cakeCollectionNewCakeToAttach.getClass(), cakeCollectionNewCakeToAttach.getId());
                attachedCakeCollectionNew.add(cakeCollectionNewCakeToAttach);
            }
            cakeCollectionNew = attachedCakeCollectionNew;
            users.setCakeCollection(cakeCollectionNew);
            users = em.merge(users);
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew)) {
                roleIdOld.getUsersCollection().remove(users);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld)) {
                roleIdNew.getUsersCollection().add(users);
                roleIdNew = em.merge(roleIdNew);
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    ordersCollectionOldOrders.setUserId(null);
                    ordersCollectionOldOrders = em.merge(ordersCollectionOldOrders);
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Users oldUserIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getUserId();
                    ordersCollectionNewOrders.setUserId(users);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldUserIdOfOrdersCollectionNewOrders != null && !oldUserIdOfOrdersCollectionNewOrders.equals(users)) {
                        oldUserIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldUserIdOfOrdersCollectionNewOrders = em.merge(oldUserIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            for (Cake cakeCollectionOldCake : cakeCollectionOld) {
                if (!cakeCollectionNew.contains(cakeCollectionOldCake)) {
                    cakeCollectionOldCake.setModifyUserId(null);
                    cakeCollectionOldCake = em.merge(cakeCollectionOldCake);
                }
            }
            for (Cake cakeCollectionNewCake : cakeCollectionNew) {
                if (!cakeCollectionOld.contains(cakeCollectionNewCake)) {
                    Users oldModifyUserIdOfCakeCollectionNewCake = cakeCollectionNewCake.getModifyUserId();
                    cakeCollectionNewCake.setModifyUserId(users);
                    cakeCollectionNewCake = em.merge(cakeCollectionNewCake);
                    if (oldModifyUserIdOfCakeCollectionNewCake != null && !oldModifyUserIdOfCakeCollectionNewCake.equals(users)) {
                        oldModifyUserIdOfCakeCollectionNewCake.getCakeCollection().remove(cakeCollectionNewCake);
                        oldModifyUserIdOfCakeCollectionNewCake = em.merge(oldModifyUserIdOfCakeCollectionNewCake);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            Role roleId = users.getRoleId();
            if (roleId != null) {
                roleId.getUsersCollection().remove(users);
                roleId = em.merge(roleId);
            }
            Collection<Orders> ordersCollection = users.getOrdersCollection();
            for (Orders ordersCollectionOrders : ordersCollection) {
                ordersCollectionOrders.setUserId(null);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
            }
            Collection<Cake> cakeCollection = users.getCakeCollection();
            for (Cake cakeCollectionCake : cakeCollection) {
                cakeCollectionCake.setModifyUserId(null);
                cakeCollectionCake = em.merge(cakeCollectionCake);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
