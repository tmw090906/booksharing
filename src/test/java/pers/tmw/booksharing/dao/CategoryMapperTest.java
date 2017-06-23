package pers.tmw.booksharing.dao;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.Category;
import pers.tmw.booksharing.service.IBookInfoService;
import pers.tmw.booksharing.service.ICategoryService;
import pers.tmw.booksharing.vo.CategoryTreeVo;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tmw090906 on 2017/6/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class CategoryMapperTest {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IBookInfoService iBookInfoService;


    @Test
    public void insert() throws Exception {


        ServerResponse serverResponse = iCategoryService.getTreeCategory();
        if(serverResponse.isSuccess()){
            Gson gson = new Gson();
            String json = gson.toJson(serverResponse);
            System.out.println(json);
        }


    }

    @Test
    public void test(){
        ServerResponse serverResponse = iBookInfoService.getBookList("言",3L,1,10,"CREATE_TIME");
        if(serverResponse.isSuccess()){
            Gson gson = new Gson();
            String json = gson.toJson(serverResponse);
            System.out.println(json);
        }
    }

}