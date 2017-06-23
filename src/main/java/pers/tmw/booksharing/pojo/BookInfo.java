package pers.tmw.booksharing.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class BookInfo {
    private Long bookId;

    private String isbn;

    private String bookName;

    private String bookAuthor;

    private Long categoryId;

    private BigDecimal bookPrice;

    private BigDecimal bookDeposit;

    private String bookDetail;

    private String publishTime;

    private Short publishEdition;

    private String publishTrim;

    private String createdby;

    private Date updateTime;

    private Date createTime;

    private String bookImage;

    public BookInfo(Long bookId, String isbn, String bookName, String bookAuthor, Long categoryId, BigDecimal bookPrice, BigDecimal bookDeposit, String bookDetail, String publishTime, Short publishEdition, String publishTrim, String createdby, Date updateTime, Date createTime, String bookImage) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.categoryId = categoryId;
        this.bookPrice = bookPrice;
        this.bookDeposit = bookDeposit;
        this.bookDetail = bookDetail;
        this.publishTime = publishTime;
        this.publishEdition = publishEdition;
        this.publishTrim = publishTrim;
        this.createdby = createdby;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.bookImage = bookImage;
    }

    public BookInfo() {
        super();
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public BigDecimal getBookDeposit() {
        return bookDeposit;
    }

    public void setBookDeposit(BigDecimal bookDeposit) {
        this.bookDeposit = bookDeposit;
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail == null ? null : bookDetail.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public Short getPublishEdition() {
        return publishEdition;
    }

    public void setPublishEdition(Short publishEdition) {
        this.publishEdition = publishEdition;
    }

    public String getPublishTrim() {
        return publishTrim;
    }

    public void setPublishTrim(String publishTrim) {
        this.publishTrim = publishTrim == null ? null : publishTrim.trim();
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
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

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage == null ? null : bookImage.trim();
    }
}