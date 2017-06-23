package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/19.
 */
public class ExchangeListVo {

    private Long userId;
    private String userName;
    private Long exchangeUserId;
    private Long bookId;
    private String bookName;
    private String bookAuthor;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getExchangeUserId() {
        return exchangeUserId;
    }

    public void setExchangeUserId(Long exchangeUserId) {
        this.exchangeUserId = exchangeUserId;
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

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
