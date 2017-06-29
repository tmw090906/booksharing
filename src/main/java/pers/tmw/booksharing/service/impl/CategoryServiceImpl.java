package pers.tmw.booksharing.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.BookInfoMapper;
import pers.tmw.booksharing.dao.CategoryMapper;
import pers.tmw.booksharing.pojo.Category;
import pers.tmw.booksharing.service.ICategoryService;
import pers.tmw.booksharing.vo.CategoryTreeVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tmw090906 on 2017/6/8.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;



    @Override
    public ServerResponse addNewCategory(String categoryName, Long parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setParentId(parentId);
        category.setStatus(Const.Role.ROLE_ADMIN);

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("品类添加成功");
        }
        return ServerResponse.createByErrorMessage("品类添加失败");
    }

    @Override
    public ServerResponse updateCategoryName(Long categoryId, String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryId(categoryId);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("品类名称修改成功");
        }
        return ServerResponse.createByErrorMessage("品类名称修改失败");
    }

    @Override
    public ServerResponse manageDeleteCategory(Long categoryId){
        if(categoryId == null){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(categoryList != null && categoryList.size() >0){
            return ServerResponse.createByErrorMessage("无法删除该分类，该分类下还有子分类");
        }
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        int rowCount = categoryMapper.deleteByPrimaryKey(categoryId);
        if(rowCount > 0){
            Long newCategoryId = category.getParentId();
            bookInfoMapper.updateCategoryForCategoryDelete(categoryId,newCategoryId);
            return ServerResponse.createBySuccessMessage("删除分类成功");
        }
        return ServerResponse.createByErrorMessage("删除分类失败");
    }

    @Override
    public ServerResponse getChildrenParallelCategory(Long categoryId){
        List<Category> childCategoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        Category category = categoryMapper.selectByPrimaryKey(categoryId);

        Map responseMap = Maps.newHashMap();
        responseMap.put("list",childCategoryList);
        if(category == null){
            category = new Category();
            category.setCategoryId(0L);
            responseMap.put("category",category);
            responseMap.put("parentCategory",category);
        }else {
            responseMap.put("category",category);
            Category parentCategory = categoryMapper.selectByPrimaryKey(category.getParentId());
            if(parentCategory == null){
                parentCategory = new Category();
                parentCategory.setCategoryId(0L);
                responseMap.put("parentCategory",parentCategory);
            }else {
                responseMap.put("parentCategory",parentCategory);
            }
        }
        if(CollectionUtils.isEmpty(childCategoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(responseMap);
    }

    /**
     * 调用私有方法findChildCategory方法查询本节点和孩子节点ID
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse selectCategoryAndDeepChildrenCategory(Long categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Long> categoryIds = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryTemp : categorySet){
                categoryIds.add(categoryTemp.getCategoryId());
            }
        }
        return ServerResponse.createBySuccess(categoryIds);
    }

    //递归算法算出子节点
    private void findChildCategory(Set<Category> categorySet,Long categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点，退出递归的条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryTemp:categoryList){
            findChildCategory(categorySet,categoryTemp.getCategoryId());
        }
        return;
    }

    //前台

    /**
     * todo 定义url
     * @return
     */
    public ServerResponse getTreeCategory(){
        Category category = new Category();
        category.setCategoryId(0L);
        this.findTreeCategory(category);
        CategoryTreeVo categoryTreeVo = new CategoryTreeVo();
        this.assembleCategoryTreeVo(categoryTreeVo,category);
        return ServerResponse.createBySuccess(categoryTreeVo.getList());
    }

    private void findTreeCategory(Category category){

        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(category.getCategoryId());

        category.setCategoryList(categoryList);

        for(Category categoryTemp : categoryList){
            findTreeCategory(categoryTemp);
        }
        return;

    }

    /**
     * todo 定义url
     * @return
     */
    private void assembleCategoryTreeVo(CategoryTreeVo categoryTreeVo,Category category){
        categoryTreeVo.setName(category.getCategoryName());

        List<Category> categoryList = category.getCategoryList();
        List<CategoryTreeVo> categoryTreeVoList = Lists.newArrayList();
        for(Category categoryTemp: categoryList){
            CategoryTreeVo categoryTreeVoItem = new CategoryTreeVo();
            categoryTreeVoItem.setCategoryId(categoryTemp.getCategoryId());
            categoryTreeVoItem.setName(categoryTemp.getCategoryName());
            String url = "javascript:";
            categoryTreeVoItem.setUrl(url);
            categoryTreeVoItem.setUserLevel("");
            categoryTreeVoList.add(categoryTreeVoItem);
            assembleCategoryTreeVo(categoryTreeVoItem,categoryTemp);
        }
        categoryTreeVo.setList(categoryTreeVoList);
        return;
    }

    @Override
    public ServerResponse manageGetAllCategory(){
        List<Category> categoryList = categoryMapper.getAllCategory();
        return ServerResponse.createBySuccess(categoryList);
    }


}
