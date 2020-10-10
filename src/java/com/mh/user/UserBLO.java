/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.user;

import com.mh.controller.Constants;
import com.mh.entity.Role;
import com.mh.entity.Users;
import com.mh.jpa_controller.UsersJpaController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author saost
 */
public class UserBLO {
//    EntityManagerFactory emf = new PersistenceProvider().createEntityManagerFactory("")

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("YellowMoonShopPU");

    public Users create (String userID, String password, String fullName, String address, String phoneNumber){
        UsersJpaController usersJpaController = new UsersJpaController(emf);
        try {
            Users user = new Users(userID, fullName, password, true);
            user.setRoleId(new Role(Constants.MEMBER));
            if (address != null) user.setAddress(address);
            if (phoneNumber != null) user.setPhone(phoneNumber);

            usersJpaController.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExitsUserId(String userID){
        UsersJpaController usersJpaController = new UsersJpaController(emf);
        Users user = usersJpaController.findUsers(userID);

        return (user != null && user.getStatus());
    }

    public Users checkLogin(String userID, String password) {
        UsersJpaController usersJpaController = new UsersJpaController(emf);
        try {
            Users user = usersJpaController.findUsers(userID);
            if (user != null){
                if (user.getPassword().equals(password)){
                    user.setPassword("");
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users get(String userId){
        UsersJpaController usersJpaController = new UsersJpaController(emf);
        return usersJpaController.findUsers(userId);
    }

    public void set(Users user){
        UsersJpaController usersJpaController = new UsersJpaController(emf);

        try {
            usersJpaController.edit(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRole(String userId){
        UsersJpaController usersJpaController = new UsersJpaController(emf);
        return usersJpaController.findUsers(userId).getRoleId().getId();
    }


}
