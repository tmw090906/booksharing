package pers.tmw.booksharing.pojo;

import java.util.Date;

public class SelfLibrary {
    private Long id;

    private Long bookId;

    private Long userId;

    private Short status;

    private Date updateTime;

    private Date createTime;

    public SelfLibrary(Long id, Long bookId, Long userId, Short status, Date updateTime, Date createTime) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public SelfLibrary() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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