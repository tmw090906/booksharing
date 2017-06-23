package pers.tmw.booksharing.pojo;

import java.util.Date;

public class Advice {
    private Long adviceId;

    private String isbn;

    private String bookName;

    private String bookauthor;

    private Short status;

    private Long userId;

    private Long managerId;

    private Date updateTime;

    private Date createTime;

    public Advice(Long adviceId, String isbn, String bookName, String bookauthor, Short status, Long userId, Long managerId, Date updateTime, Date createTime) {
        this.adviceId = adviceId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.bookauthor = bookauthor;
        this.status = status;
        this.userId = userId;
        this.managerId = managerId;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Advice() {
        super();
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor == null ? null : bookauthor.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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