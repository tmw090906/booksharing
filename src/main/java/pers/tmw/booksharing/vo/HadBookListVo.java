package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/26.
 */
public class HadBookListVo {

    private Long bookId;
    private String bookName;
    private String bookAuthor;

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
