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
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author saost
 */
public class OrderBLO {
    public static final int SHIPPING = 1;
    public static final int RECIEVED = 2;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("YellowMoonShopPU");

    public Orders create(Cart cart, String userId, String fullName, String address, String phoneNumber){
        try {
            if (!cart.isEnough()) return null;
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

}
