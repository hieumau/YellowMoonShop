/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.order;

import com.mh.cart.Cart;
import com.mh.entity.*;
import com.mh.jpa_controller.CakeJpaController;
import com.mh.jpa_controller.OrderDetailJpaController;
import com.mh.jpa_controller.OrdersJpaController;
import com.mh.user.UserBLO;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author saost
 */
public class OrderBLO {
    public static final int SHIPPING = 1;
    public static final int RECIEVED = 2;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("YellowMoonShopPU");

    public synchronized Orders create(Cart cart, String userId, String fullName, String address, String phoneNumber){
        try {
            if (!cart.isEnough()) return null;
            if (cart.getCart().size() == 0) return null;
            //create order
            Orders order = new Orders(0, new Date(), SHIPPING, address, phoneNumber, fullName);
            order.setUserId(new Users(userId));

            OrdersJpaController ordersJpaController = new OrdersJpaController(emf);
            ordersJpaController.create(order);
            Integer orderId = order.getId();

            //create order details
            OrderDetailJpaController orderDetailJpaController = new OrderDetailJpaController(emf);
            for (Map.Entry<Cake, Integer> item : cart.getCart().entrySet()) {
                Cake cake = item.getKey();
                OrderDetail orderDetail = new OrderDetail(new OrderDetailPK(orderId, cake.getId()));
                orderDetail.setQuantity(item.getValue());
                orderDetail.setOrders(order);
                orderDetail.setCake(cake);
                orderDetailJpaController.create(orderDetail);
                
                //decrease cake quantity
                CakeJpaController cakeJpaController = new CakeJpaController(emf);
                cake = cakeJpaController.findCake(cake.getId());
                cake.setQuantity(cake.getQuantity() - item.getValue());
                cakeJpaController.edit(cake);
            }
            return ordersJpaController.findOrders(orderId);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Cart getOrderAndOrderDetailsByUserAndOrderId(String userId, int orderId){
        Cart cart = null;

        try {
            //get and check User
            UserBLO userBLO = new UserBLO();
            Users user =  userBLO.get(userId);
            if (user == null) return null;

            //get and check Order
            OrdersJpaController ordersJpaController = new OrdersJpaController(emf);
            Orders order = get(orderId);
            if (order == null) return null;
            if (!order.getUserId().getId().equals(userId)) return null;

            //get Order detail
            List<OrderDetail> orderDetailList = new ArrayList<>();
            OrderDetailJpaController orderDetailJpaController = new OrderDetailJpaController(emf);
            EntityManager entityManager = emf.createEntityManager();

            String sql = "SELECT orderDetail " +
                    "FROM OrderDetail orderDetail " +
                    "WHERE orderDetail.orders = :orderId ";
            Query query = entityManager.createQuery(sql, OrderDetail.class);
            query.setParameter("orderId", new Orders(orderId));
            orderDetailList = query.getResultList();

            // put all to cart
            cart = new Cart();
            cart.setOrder(order);
            for (OrderDetail orderDetail : orderDetailList) {
                cart.update(orderDetail.getCake().getId(), orderDetail.getQuantity());
            }

            return cart;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cart;
    }


    public Orders get(int orderId){
        OrdersJpaController ordersJpaController = new OrdersJpaController(emf);
        return ordersJpaController.findOrders(orderId);
    }
}
