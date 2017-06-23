package pers.tmw.booksharing.pojo;

import java.util.Date;

public class Complan {
    private Long complanId;

    private Long replaceId;

    private Long userId;

    private String reason;

    private Date updateTime;

    private Date createTime;

    private Short status;

    public Complan(Long complanId, Long replaceId, Long userId, String reason, Date updateTime, Date createTime, Short status) {
        this.complanId = complanId;
        this.replaceId = replaceId;
        this.userId = userId;
        this.reason = reason;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.status = status;
    }

    public Complan() {
        super();
    }

    public Long getComplanId() {
        return complanId;
    }

    public void setComplanId(Long complanId) {
        this.complanId = complanId;
    }

    public Long getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Long replaceId) {
        this.replaceId = replaceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}