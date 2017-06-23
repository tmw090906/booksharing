package pers.tmw.booksharing.pojo;

import java.util.Date;

public class Apply {
    private Long applyId;

    private Long applyUser;

    private Long applyBook;

    private Long appliedUser;

    private Long appliedBook;

    private Short status;

    private String reason;

    private Date updateTime;

    private Date createTime;

    public Apply(Long applyId, Long applyUser, Long applyBook, Long appliedUser, Long appliedBook, Short status, String reason, Date updateTime, Date createTime) {
        this.applyId = applyId;
        this.applyUser = applyUser;
        this.applyBook = applyBook;
        this.appliedUser = appliedUser;
        this.appliedBook = appliedBook;
        this.status = status;
        this.reason = reason;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Apply() {
        super();
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

    public Long getApplyBook() {
        return applyBook;
    }

    public void setApplyBook(Long applyBook) {
        this.applyBook = applyBook;
    }

    public Long getAppliedUser() {
        return appliedUser;
    }

    public void setAppliedUser(Long appliedUser) {
        this.appliedUser = appliedUser;
    }

    public Long getAppliedBook() {
        return appliedBook;
    }

    public void setAppliedBook(Long appliedBook) {
        this.appliedBook = appliedBook;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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