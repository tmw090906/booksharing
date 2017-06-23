package pers.tmw.booksharing.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long orderId;

    private String orderNumber;

    private Long userId;

    private BigDecimal orderMoney;

    private BigDecimal oldMoney;

    private BigDecimal newMoney;

    private Date updateTime;

    private Date createTime;

    private Short status;

    private Date paymentTime;

    public Order(Long orderId, String orderNumber, Long userId, BigDecimal orderMoney, BigDecimal oldMoney, BigDecimal newMoney, Date updateTime, Date createTime, Short status, Date paymentTime) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.orderMoney = orderMoney;
        this.oldMoney = oldMoney;
        this.newMoney = newMoney;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.status = status;
        this.paymentTime = paymentTime;
    }

    public Order() {
        super();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(BigDecimal oldMoney) {
        this.oldMoney = oldMoney;
    }

    public BigDecimal getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(BigDecimal newMoney) {
        this.newMoney = newMoney;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
}