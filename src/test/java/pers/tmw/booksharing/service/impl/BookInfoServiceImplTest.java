package pers.tmw.booksharing.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IBookInfoService;
import pers.tmw.booksharing.vo.ExchangeOneListVo;
import pers.tmw.booksharing.vo.SelfLibraryVo;

import java.awt.print.Book;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tmw090906 on 2017/6/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class BookInfoServiceImplTest {

    @Autowired
    private IBookInfoService iBookInfoService;


    @Test
    public void getExchangeInfoOne() throws Exception {

        User user = new User();
        user.setUserId(23L);
        ServerResponse serverResponse = iBookInfoService.getExchangeInfoOne(user.getUserId(),90L);
        List<ExchangeOneListVo> selfLibraryVoList = (List)serverResponse.getDate();
        for(ExchangeOneListVo exchangeOneListVo : selfLibraryVoList){
            System.out.println(exchangeOneListVo.getBookName());
            System.out.println(exchangeOneListVo.getUserName());
        }

    }

}