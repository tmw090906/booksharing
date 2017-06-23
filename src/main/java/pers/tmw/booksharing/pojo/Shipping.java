package pers.tmw.booksharing.pojo;

import java.util.Date;

public class Shipping {
    private Long shippingId;

    private Long userId;

    private String receiveName;

    private Long receivePhone;

    private String receiveProvince;

    private String receiveCity;

    private String receiveBlock;

    private String receiveAddress;

    private Long receiveZip;

    private Date updateTime;

    private Date createTime;

    public Shipping(Long shippingId, Long userId, String receiveName, Long receivePhone, String receiveProvince, String receiveCity, String receiveBlock, String receiveAddress, Long receiveZip, Date updateTime, Date createTime) {
        this.shippingId = shippingId;
        this.userId = userId;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.receiveProvince = receiveProvince;
        this.receiveCity = receiveCity;
        this.receiveBlock = receiveBlock;
        this.receiveAddress = receiveAddress;
        this.receiveZip = receiveZip;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Shipping() {
        super();
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }

    public Long getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(Long receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince == null ? null : receiveProvince.trim();
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity == null ? null : receiveCity.trim();
    }

    public String getReceiveBlock() {
        return receiveBlock;
    }

    public void setReceiveBlock(String receiveBlock) {
        this.receiveBlock = receiveBlock == null ? null : receiveBlock.trim();
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
    }

    public Long getReceiveZip() {
        return receiveZip;
    }

    public void setReceiveZip(Long receiveZip) {
        this.receiveZip = receiveZip;
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