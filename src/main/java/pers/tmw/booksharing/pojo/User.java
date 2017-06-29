package pers.tmw.booksharing.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private Long userId;

    private String username;

    private String pwd;

    private String trueName;

    private String idcard;

    private String email;

    private Long phone;

    private Short role;

    private String wechart;

    private Long qq;

    private String question;

    private String anwser;

    private BigDecimal deposit;

    private BigDecimal approveDeposit;

    private Date updateTime;

    private Date createTime;

    public User(Long userId, String username, String pwd, String trueName, String idcard, String email, Long phone, Short role, String wechart, Long qq, String question, String anwser, BigDecimal deposit, BigDecimal approveDeposit, Date updateTime, Date createTime) {
        this.userId = userId;
        this.username = username;
        this.pwd = pwd;
        this.trueName = trueName;
        this.idcard = idcard;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.wechart = wechart;
        this.qq = qq;
        this.question = question;
        this.anwser = anwser;
        this.deposit = deposit;
        this.approveDeposit = approveDeposit;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public User() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public String getWechart() {
        return wechart;
    }

    public void setWechart(String wechart) {
        this.wechart = wechart == null ? null : wechart.trim();
    }

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnwser() {
        return anwser;
    }

    public void setAnwser(String anwser) {
        this.anwser = anwser == null ? null : anwser.trim();
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getApproveDeposit() {
        return approveDeposit;
    }

    public void setApproveDeposit(BigDecimal approveDeposit) {
        this.approveDeposit = approveDeposit;
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