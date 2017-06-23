package pers.tmw.booksharing.vo;

import java.math.BigDecimal;

/**
 * Created by tmw090906 on 2017/6/22.
 */
public class IllegalVo {

    private Long illegalId;
    private String victimUserName;
    private Long replaceId;
    private BigDecimal deposit;
    private String illegalTime;

    public Long getIllegalId() {
        return illegalId;
    }

    public void setIllegalId(Long illegalId) {
        this.illegalId = illegalId;
    }

    public String getVictimUserName() {
        return victimUserName;
    }

    public void setVictimUserName(String victimUserName) {
        this.victimUserName = victimUserName;
    }

    public Long getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Long replaceId) {
        this.replaceId = replaceId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getIllegalTime() {
        return illegalTime;
    }

    public void setIllegalTime(String illegalTime) {
        this.illegalTime = illegalTime;
    }
}
