package pers.tmw.booksharing.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Illegal {
    private Long illegalId;

    private Long userId;

    private Long replaceId;

    private Long victimId;

    private BigDecimal deposit;

    private Long manager;

    private Date updateTime;

    private Date createTime;

    public Illegal(Long illegalId, Long userId, Long replaceId, Long victimId, BigDecimal deposit, Long manager, Date updateTime, Date createTime) {
        this.illegalId = illegalId;
        this.userId = userId;
        this.replaceId = replaceId;
        this.victimId = victimId;
        this.deposit = deposit;
        this.manager = manager;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Illegal() {
        super();
    }

    public Long getIllegalId() {
        return illegalId;
    }

    public void setIllegalId(Long illegalId) {
        this.illegalId = illegalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Long replaceId) {
        this.replaceId = replaceId;
    }

    public Long getVictimId() {
        return victimId;
    }

    public void setVictimId(Long victimId) {
        this.victimId = victimId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
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