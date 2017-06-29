package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/10.
 */
public class AdviceListVo {

    private Long adviceId;
    private String createTime;
    private Short status;
    private String statusStr;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
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
}
