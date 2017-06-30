package pers.tmw.booksharing.controller.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.Advice;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IBookInfoService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/7.
 * 书籍相关前台接口类
 */
@RequestMapping("/info/")
@Controller
public class BookController {


    @Autowired
    private IBookInfoService iBookInfoService;


    /**
     * 根据关键字，分类，查询图书列表
     * @param textSearch
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("get_book_list.do")
    @ResponseBody
    public ServerResponse getBookList(@RequestParam(value = "textSearch",required = false) String textSearch,
                                      @RequestParam(value = "categoryId",required = false)Long categoryId,
                                      @RequestParam(defaultValue = "1")int pageNum,
                                      @RequestParam(defaultValue = "10")int pageSize,
                                      @RequestParam(value = "orderBy",defaultValue = "CREATE_TIME") String orderBy){
        return iBookInfoService.getBookList(textSearch, categoryId, pageNum, pageSize, orderBy);
    }


    /**
     * 得到图书详情接口
     * @param bookId
     * @return
     */
    @RequestMapping("get_book_detail.do")
    @ResponseBody
    public ServerResponse getBookDetail(Long bookId){
        return iBookInfoService.manageBookDetail(bookId);
    }


    /**
     * 若书库中无想查询的书的信息，提交申请给管理员添加图书
     * @param session
     * @param advice
     * @return
     */
    @RequestMapping("advice.do")
    @ResponseBody
    public ServerResponse adviceAddBook(HttpSession session, Advice advice){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(advice == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        advice.setUserId(user.getUserId());
        return iBookInfoService.adviceAddBook(advice);
    }


    /**
     * 查看自己提交申请图书列表
     * @param session
     * @return
     */
    @RequestMapping("get_advice_list.do")
    @ResponseBody
    public ServerResponse getAdviceList(HttpSession session,
                                        @RequestParam(defaultValue = "1")int pageNum,
                                        @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iBookInfoService.getAdviceList(user.getUserId(),pageNum,pageSize);
    }

    /**
     * 查看某条自己的申请详情
     * @param session
     * @param adviceId
     * @return
     */
    @RequestMapping("get_advice_detail.do")
    @ResponseBody
    public ServerResponse getAdviceDetail(HttpSession session,Long adviceId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iBookInfoService.getAdviceDetail(adviceId);
    }

    /**
     * 获取可此书可交换信息，在对方书籍状态为可交换，且你的可交换书库中包含对方想读书籍时方显示信息
     * @param session
     * @param bookId
     * @return
     */
    @RequestMapping("exchange_one_info.do")
    @ResponseBody
    public ServerResponse getExchangeInfoOne(HttpSession session,Long bookId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iBookInfoService.getExchangeInfoOne(user.getUserId(),bookId);
    }

    /**
     * 获取此书可交换信息，在对方书籍状态为可交换，且你可使用任意本书进行交换
     * @param session
     * @param bookId
     * @return
     */
    @RequestMapping("exchange_info.do")
    @ResponseBody
    public ServerResponse getExchangeInfo(HttpSession session,Long bookId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return iBookInfoService.getExchangeInfo(user.getUserId(),bookId);
    }

    @RequestMapping("self_list.do")
    @ResponseBody
    public ServerResponse getSelfBookList(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iBookInfoService.getSelfBookList(user.getUserId());
    }





}
