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
import pers.tmw.booksharing.service.IDepositService;
import pers.tmw.booksharing.service.IOrderService;
import pers.tmw.booksharing.service.IUserService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/9.
 */
@RequestMapping("/order/")
@Controller
public class OrderController {

    @Autowired
    private IOrderService iOrderService;


    //前台接口
    /**
     * 个人中心查看自己所有订单
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
            return ServerResponse.createByErrorMessage("用户未登录，请先登陆");
        }
        return iOrderService.getOrderList(user.getUserId(),pageNum,pageSize);
    }


    /**
     * 取消当前订单
     * 若订单已经付款，则无法取消
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpSession session,String orderNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录，请先登陆");
        }
        return iOrderService.cancelOrderByOrderNo(user.getUserId(),orderNo);
    }

}
