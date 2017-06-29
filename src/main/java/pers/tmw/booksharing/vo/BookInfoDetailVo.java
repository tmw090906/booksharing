package pers.tmw.booksharing.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tmw090906 on 2017/6/9.
 */
public class BookInfoDetailVo {

    private Long bookId;
    private String isbn;
    private String bookName;
    private String bookAuthor;
    private String categoryName;
    private BigDecimal bookPrice;
    private BigDecimal bookDeposit;
    private String bookDetail;
    private String publishTime;
    private Short publishEdition;
    private String publishTrim;
    private String createTime;
    private String bookImage;
    private String imageHost;
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
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
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
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
        this.bookDetail = bookDetail;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
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
        this.publishTrim = publishTrim;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}
