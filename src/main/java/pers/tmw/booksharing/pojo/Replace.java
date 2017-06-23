package pers.tmw.booksharing.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Replace {
    private Long replaceId;

    private Long applyId;

    private Long applyUser;

    private Long applyShipping;

    private BigDecimal applyDeliverNo;

    private Long appliedUser;

    private Long appliedShipping;

    private Long appliedDeliverNo;

    private Short applyStatus;

    private Short appliedStatus;

    private Short status;

    private Date updateTime;

    private Date createTime;

    public Replace(Long replaceId, Long applyId, Long applyUser, Long applyShipping, BigDecimal applyDeliverNo, Long appliedUser, Long appliedShipping, Long appliedDeliverNo, Short applyStatus, Short appliedStatus, Short status, Date updateTime, Date createTime) {
        this.replaceId = replaceId;
        this.applyId = applyId;
        this.applyUser = applyUser;
        this.applyShipping = applyShipping;
        this.applyDeliverNo = applyDeliverNo;
        this.appliedUser = appliedUser;
        this.appliedShipping = appliedShipping;
        this.appliedDeliverNo = appliedDeliverNo;
        this.applyStatus = applyStatus;
        this.appliedStatus = appliedStatus;
        this.status = status;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Replace() {
        super();
    }

    public Long getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Long replaceId) {
        this.replaceId = replaceId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Long applyUser) {
        this.applyUser = applyUser;
    }

    public Long getApplyShipping() {
        return applyShipping;
    }

    public void setApplyShipping(Long applyShipping) {
        this.applyShipping = applyShipping;
    }

    public BigDecimal getApplyDeliverNo() {
        return applyDeliverNo;
    }

    public void setApplyDeliverNo(BigDecimal applyDeliverNo) {
        this.applyDeliverNo = applyDeliverNo;
    }

    public Long getAppliedUser() {
        return appliedUser;
    }

    public void setAppliedUser(Long appliedUser) {
        this.appliedUser = appliedUser;
    }

    public Long getAppliedShipping() {
        return appliedShipping;
    }

    public void setAppliedShipping(Long appliedShipping) {
        this.appliedShipping = appliedShipping;
    }

    public Long getAppliedDeliverNo() {
        return appliedDeliverNo;
    }

    public void setAppliedDeliverNo(Long appliedDeliverNo) {
        this.appliedDeliverNo = appliedDeliverNo;
    }

    public Short getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Short getAppliedStatus() {
        return appliedStatus;
    }

    public void setAppliedStatus(Short appliedStatus) {
        this.appliedStatus = appliedStatus;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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