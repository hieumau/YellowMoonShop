/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author saost
 */
@Embeddable
public class OrderDetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "cakeId")
    private int cakeId;

    public OrderDetailPK() {
    }

    public OrderDetailPK(int orderId, int cakeId) {
        this.orderId = orderId;
        this.cakeId = cakeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderId;
        hash += (int) cakeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetailPK)) {
            return false;
        }
        OrderDetailPK other = (OrderDetailPK) object;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (this.cakeId != other.cakeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mh.entity.OrderDetailPK[ orderId=" + orderId + ", cakeId=" + cakeId + " ]";
    }
    
}
