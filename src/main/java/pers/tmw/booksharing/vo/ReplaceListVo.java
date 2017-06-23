package pers.tmw.booksharing.vo;

/**
 * Created by tmw090906 on 2017/6/20.
 */
public class ReplaceListVo {

    private Long replaceId;
    private Long applyId;

    private Long userId;
    private Long shippingId;
    private Long deliverNo;
    private Long bookId;
    private String bookName;
    private Short status;

    private Long otherUserId;
    private String otherUserName;
    private Long otherShippingId;
    private Long otherDeliverNo;
    private Long otherBookId;
    private String otherBookName;
    private Short otherStatus;

    private Short replaceStatus;

    public Long getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Long replaceId) {
        this.replaceId = replaceId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public Long getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(Long deliverNo) {
        this.deliverNo = deliverNo;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Long otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public Long getOtherShippingId() {
        return otherShippingId;
    }

    public void setOtherShippingId(Long otherShippingId) {
        this.otherShippingId = otherShippingId;
    }

    public Long getOtherDeliverNo() {
        return otherDeliverNo;
    }

    public void setOtherDeliverNo(Long otherDeliverNo) {
        this.otherDeliverNo = otherDeliverNo;
    }

    public Long getOtherBookId() {
        return otherBookId;
    }

    public void setOtherBookId(Long otherBookId) {
        this.otherBookId = otherBookId;
    }

    public String getOtherBookName() {
        return otherBookName;
    }

    public void setOtherBookName(String otherBookName) {
        this.otherBookName = otherBookName;
    }

    public Short getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(Short otherStatus) {
        this.otherStatus = otherStatus;
    }

    public Short getReplaceStatus() {
        return replaceStatus;
    }

    public void setReplaceStatus(Short replaceStatus) {
        this.replaceStatus = replaceStatus;
    }
}
