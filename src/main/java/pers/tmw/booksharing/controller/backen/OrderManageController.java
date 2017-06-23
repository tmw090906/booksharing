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
import pers.tmw.booksharing.service.IOrderService;
import pers.tmw.booksharing.service.IUserService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/9.
 */
@RequestMapping("/manage/order/")
@Controller
public class OrderManageController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IUserService iUserService;



      /*
    后台接口：
    1、查询所有订单
    2、根据订单号获取订单
     */
    /**
     * 后台获取订单列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(defaultValue = "1")int pageNum,
                               @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iOrderService.manageList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    /**
     * 后台通过订单号获取详情，应返回一个List,实际上这里是一个模糊查询？
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse search(HttpSession session,String orderNo,
                                 @RequestParam(defaultValue = "1")int pageNum,
                                 @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iOrderService.manageSearch(orderNo,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }
}
