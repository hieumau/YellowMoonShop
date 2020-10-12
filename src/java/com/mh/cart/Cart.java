/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.cart;

import com.mh.cake.CakeBLO;
import com.mh.entity.Cake;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author saost
 */
public class Cart implements Serializable {
    private Map<Cake, Integer> cart = new HashMap<>();

    public void add(int cakeId){
        CakeBLO cakeBLO = new CakeBLO();
        Cake cake = cakeBLO.get(cakeId);

        if (cake != null){
            Integer quantity = cart.getOrDefault(cake, 0);
            cart.put(cake, quantity + 1);
        }

    }

    public void remove(int cakeId){
        CakeBLO cakeBLO = new CakeBLO();
        Cake cake = cakeBLO.get(cakeId);

        if (cake != null){
            if (cart.get(cake) != null) cart.remove(cake);
        }
    }

    public void update(int cakeId, int quantity){
        CakeBLO cakeBLO = new CakeBLO();
        Cake cake = cakeBLO.get(cakeId);

        if(quantity <= 0) remove(cakeId);
        else if (cake != null){
            if (cart.get(cake) != null){
                cart.put(cake, quantity);
            }
        }
    }

    public float getTotal(){
        float total = 0;
        for (Map.Entry<Cake, Integer> item : cart.entrySet()) {
            total += item.getKey().getPrice().floatValue() * item.getValue();
        }
        return total;
    }

    public int size(){
        return cart.size();
    }

    public Map<Cake, Integer> getCart(){
        return cart;
    }

    public void setCart(Map<Cake, Integer> cart) {
        this.cart = cart;
    }

    public Boolean isEnough(){
        for (Map.Entry<Cake, Integer> item : cart.entrySet()) {
            if (item.getKey().getQuantity() < item.getValue()) return false;
        }
        return true;
    }
}
