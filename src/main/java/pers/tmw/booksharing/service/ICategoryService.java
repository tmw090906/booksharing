package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;

/**
 * Created by tmw090906 on 2017/6/8.
 */
public interface ICategoryService {

    ServerResponse addNewCategory(String categoryName,Long parentId);

    ServerResponse updateCategoryName(Long categoryId, String categoryName);

    ServerResponse getChildrenParallelCategory(Long categoryId);

    ServerResponse selectCategoryAndDeepChildrenCategory(Long categoryId);

    ServerResponse getTreeCategory();

}
