/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author saost
 */
@Entity
@Table(name = "Cake")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cake.findAll", query = "SELECT c FROM Cake c")
    , @NamedQuery(name = "Cake.findById", query = "SELECT c FROM Cake c WHERE c.id = :id")
    , @NamedQuery(name = "Cake.findByName", query = "SELECT c FROM Cake c WHERE c.name = :name")
    , @NamedQuery(name = "Cake.findByImagePath", query = "SELECT c FROM Cake c WHERE c.imagePath = :imagePath")
    , @NamedQuery(name = "Cake.findByDescription", query = "SELECT c FROM Cake c WHERE c.description = :description")
    , @NamedQuery(name = "Cake.findByPrice", query = "SELECT c FROM Cake c WHERE c.price = :price")
    , @NamedQuery(name = "Cake.findByCreateDate", query = "SELECT c FROM Cake c WHERE c.createDate = :createDate")
    , @NamedQuery(name = "Cake.findByExpirationDate", query = "SELECT c FROM Cake c WHERE c.expirationDate = :expirationDate")
    , @NamedQuery(name = "Cake.findByQuantity", query = "SELECT c FROM Cake c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "Cake.findByStatus", query = "SELECT c FROM Cake c WHERE c.status = :status")
    , @NamedQuery(name = "Cake.findByModifyDate", query = "SELECT c FROM Cake c WHERE c.modifyDate = :modifyDate")})
public class Cake implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "imagePath")
    private String imagePath;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @Column(name = "expirationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    @Column(name = "modifyDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "modifyUserId", referencedColumnName = "id")
    @ManyToOne
    private Users modifyUserId;

    public Cake() {
    }

    public Cake(Integer id) {
        this.id = id;
    }

    public Cake(Integer id, String name, String imagePath, String description, BigDecimal price, Date createDate, Date expirationDate, int quantity, boolean status) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Users getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Users modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getExpDateFormated(){
        return DateFormat.getDateInstance().format(expirationDate);
    }

    public String getCreateDateFormated(){
        return DateFormat.getDateInstance().format(createDate);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cake)) {
            return false;
        }
        Cake other = (Cake) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mh.entity.Cake[ id=" + id + " ]";
    }

    public Boolean isEnough(int quantity){
        if (this.quantity < quantity) return false;
        return true;
    }
}
