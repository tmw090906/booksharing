package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/29.
 */
public class ComplanVo {

    private Long complanId;

    private Long replaceId;

    private Long userId;

    private String username;

    private String updateTime;

    private String createTime;

    private Short status;

    private String statusStr;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
}
