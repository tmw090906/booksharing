package pers.tmw.booksharing.controller.backen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IComplanService;
import pers.tmw.booksharing.service.IUserService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/20.
 */
@RequestMapping("/manage/complan")
@Controller
public class ComplanManageController {

    @Autowired
    private IComplanService iComplanService;
    @Autowired
    private IUserService iUserService;


    /**
     * 后台获取所有投诉列表接口
     * @param session
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getComplanList(HttpSession session,Short status,
                                         @RequestParam(defaultValue = "1")int pageNum,
                                         @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iComplanService.manageGetList(status,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }


    /**
     * 处理投诉接口
     * @param session
     * @param status
     * @param complanId
     * @return
     */
    @RequestMapping("manage.do")
    @ResponseBody
    public ServerResponse manageComplan(HttpSession session,Short status,Long complanId)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iComplanService.manageComplan(user.getUserId(),status,complanId);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }











}
