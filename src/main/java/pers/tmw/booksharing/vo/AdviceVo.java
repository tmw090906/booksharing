package pers.tmw.booksharing.vo;

import pers.tmw.booksharing.pojo.Advice;
import pers.tmw.booksharing.util.DateTimeUtil;

import java.util.Date;

/**
 * Created by tmw090906 on 2017/6/10.
 */
public class AdviceVo {

    private Long adviceId;

    private String isbn;

    private String bookName;

    private String bookauthor;

    private Short status;

    private Long userId;

    private Long managerId;

    private String updateTime;

    private String stautsStr;

    public String getStautsStr() {
        return stautsStr;
    }

    public void setStautsStr(String stautsStr) {
        this.stautsStr = stautsStr;
    }

    public AdviceVo() {
    }

    public AdviceVo(Advice advice){

        this.adviceId = advice.getAdviceId();
        this.isbn = advice.getIsbn();
        this.bookName = advice.getBookName();
        this.bookauthor = advice.getBookauthor();
        this.status = advice.getStatus();
        this.userId = advice.getUserId();
        this.managerId = advice.getManagerId();
        this.updateTime = DateTimeUtil.dateToStr(advice.getUpdateTime());
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
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
