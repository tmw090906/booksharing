package pers.tmw.booksharing.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tmw090906 on 2017/6/28.
 */
public class OrderVo {

    private Long orderId;

    private String orderNumber;

    private BigDecimal orderMoney;

    private BigDecimal oldMoney;

    private BigDecimal newMoney;

    private String status;

    private String paymentTime;

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
        this.orderNumber = orderNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
}
