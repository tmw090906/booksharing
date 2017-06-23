package pers.tmw.booksharing.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PayInfo {
    private Long payId;

    private Long userId;

    private BigDecimal orderNo;

    private Short platForm;

    private String tradeNo;

    private String tradeStatus;

    private Date updateTime;

    private Date createTime;

    public PayInfo(Long payId, Long userId, BigDecimal orderNo, Short platForm, String tradeNo, String tradeStatus, Date updateTime, Date createTime) {
        this.payId = payId;
        this.userId = userId;
        this.orderNo = orderNo;
        this.platForm = platForm;
        this.tradeNo = tradeNo;
        this.tradeStatus = tradeStatus;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public PayInfo() {
        super();
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
    }

    public Short getPlatForm() {
        return platForm;
    }

    public void setPlatForm(Short platForm) {
        this.platForm = platForm;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
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