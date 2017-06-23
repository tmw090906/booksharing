package pers.tmw.booksharing.controller.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.ISelfLibraryService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/11.
 */
@RequestMapping("/library/")
@Controller
public class SelfLibraryController {
    

    @Autowired
    private ISelfLibraryService iSelfLibraryService;

    /**
     * 将图书添加到自己的图书库
     * @param session
     * @param bookId
     * @param status
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse addBook(HttpSession session,Long bookId,Short status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iSelfLibraryService.addBook(user.getUserId(),bookId,status);
    }

    /**
     * 获取不同的自己图书库列表
     * @param session
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, Short status,
                                  @RequestParam(defaultValue = "1")int pageNum,
                                  @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iSelfLibraryService.getList(user.getUserId(),status,pageNum,pageSize);
    }


    /**
     * 修改自图书库图书状态
     * @param session
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("update_status.do")
    @ResponseBody
    public ServerResponse updateStatus(HttpSession session,Long id,Short status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iSelfLibraryService.updateStatus(user.getUserId(),id,status);
    }




}
