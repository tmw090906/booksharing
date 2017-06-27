package pers.tmw.booksharing.vo;

import java.util.List;

/**
 * Created by tmw090906 on 2017/6/8.
 */
public class CategoryTreeVo {

    private Long categoryId;

    private String name;

    private String userLevel;

    private String url;

    private List<CategoryTreeVo> list;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<CategoryTreeVo> getList() {
        return list;
    }

    public void setList(List<CategoryTreeVo> list) {
        this.list = list;
    }
}
