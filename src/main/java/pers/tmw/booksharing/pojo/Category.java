package pers.tmw.booksharing.pojo;

import java.util.Date;
import java.util.List;

public class Category {
    private Long categoryId;

    private String categoryName;

    private Long parentId;

    private Long orders;

    private Short status;

    private String createdby;

    private Date updateTime;

    private Date createTime;

    private List<Category> categoryList;


    public Category(Long categoryId, String categoryName, Long parentId, Long orders, Short status, String createdby, Date updateTime, Date createTime, List<Category> categoryList) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.orders = orders;
        this.status = status;
        this.createdby = createdby;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.categoryList = categoryList;
    }

    public Category(Long categoryId, String categoryName, Long parentId, Long orders, Short status, String createdby, Date updateTime, Date createTime) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.orders = orders;
        this.status = status;
        this.createdby = createdby;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Category() {
        super();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}