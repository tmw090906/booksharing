package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/19.
 */
public class ApplyListVo {

    private Long applyId;
    private Long applyUserId;
    private String applyUserName;
    private Long appliedUserId;
    private String appliedUserName;
    private Long applyBookId;
    private String applyBookName;
    private Long appliedBookId;
    private String appliedBookName;
    private String updateTime;
    private String statusStr;
    private Short status;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getAppliedUserName() {
        return appliedUserName;
    }

    public void setAppliedUserName(String appliedUserName) {
        this.appliedUserName = appliedUserName;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public Long getAppliedUserId() {
        return appliedUserId;
    }

    public void setAppliedUserId(Long appliedUserId) {
        this.appliedUserId = appliedUserId;
    }

    public Long getApplyBookId() {
        return applyBookId;
    }

    public void setApplyBookId(Long applyBookId) {
        this.applyBookId = applyBookId;
    }

    public String getApplyBookName() {
        return applyBookName;
    }

    public void setApplyBookName(String applyBookName) {
        this.applyBookName = applyBookName;
    }

    public Long getAppliedBookId() {
        return appliedBookId;
    }

    public void setAppliedBookId(Long appliedBookId) {
        this.appliedBookId = appliedBookId;
    }

    public String getAppliedBookName() {
        return appliedBookName;
    }

    public void setAppliedBookName(String appliedBookName) {
        this.appliedBookName = appliedBookName;
    }
}
