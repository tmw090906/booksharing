package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/11.
 */
public class SelfLibraryVo {

    private Long selfLibraryId;
    private Long bookId;
    private String bookName;
    private String updateTime;
    private String publishTrim;
    private String bookAuthor;
    private Short Status;


    public Long getSelfLibraryId() {
        return selfLibraryId;
    }

    public void setSelfLibraryId(Long selfLibraryId) {
        this.selfLibraryId = selfLibraryId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPublishTrim() {
        return publishTrim;
    }

    public void setPublishTrim(String publishTrim) {
        this.publishTrim = publishTrim;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Short getStatus() {
        return Status;
    }

    public void setStatus(Short status) {
        Status = status;
    }
}
