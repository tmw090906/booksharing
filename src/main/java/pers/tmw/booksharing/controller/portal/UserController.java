package pers.tmw.booksharing.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IDepositService;
import pers.tmw.booksharing.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tmw090906 on 2017/6/6.
 * User相关前后台接口
 */
@RequestMapping("/user/")
@Controller
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDepositService iDepositService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 登陆接口 1
     * @param username
     * @param pwd
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String username, String pwd, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, pwd);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getDate());
        }
        return response;
    }

    /**
     * 登出接口 1
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }


    /**
     * 注册接口 1
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(User user){
        return iUserService.register(user);
    }

    /** 1
     * 校验用户名和邮箱是否存在接口
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "checkValid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /** 1
     * 获取用户信息接口
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登陆，无法获取信息");
    }

    /** 1
     * 获取用户的问题提示接口
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetByQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /** 1
     * 用户根据问题回答接口
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /** 1
     * 未登录状态下重置密码接口
     * @param username
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "forget_reset_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPasswordByAnswer(String username,String passwordNew){
        return iUserService.resetPasswordByAnswer(username,passwordNew);
    }

    /** 1
     * 登陆状态下修改密码接口
     * @param passwordOld
     * @param passwordNew
     * @param session
     * @return
     */
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(user,passwordOld,passwordNew);
    }

    /** 1
     * 修改个人信息接口
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInfo(HttpSession session,User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //防止横向越权
        user.setUserId(currentUser.getUserId());
        ServerResponse response = iUserService.updateUserInfo(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getDate());
        }
        return response;
    }

    /** 1
     * 获取用户详细信息接口
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserDetail(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，请登陆后查看个人信息");
        }
        return iUserService.getInformation(currentUser.getUserId());
    }

    /**
     * 充值保证金接口
     * @param session
     * @param money
     * @return
     */
    @RequestMapping(value = "pay.do")
    @ResponseBody
    public ServerResponse rechargeDeposit(HttpSession session, Double money , HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iDepositService.pay(user,path,money);
    }

    /**
     * 阿里回调接口
     * @param request
     * @return
     */
    @RequestMapping("callback.do")
    @ResponseBody
    public Object callback(HttpServletRequest request){
        Map<String,String> params = Maps.newHashMap();
        Map requestMap = request.getParameterMap();
        for(Iterator iterator = requestMap.keySet().iterator(); iterator.hasNext() ;){
            String name = (String)iterator.next();
            String[] values = (String[])requestMap.get(name);
            String valueStr = "";
            for(int i = 0;i < values.length ; i++){
                valueStr = ( i == values.length - 1)? valueStr + values[i]:valueStr + values[i] + ",";
            }
            params.put(name,valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数：{}",params.get("sign"),params.get("trade_status"),params);

        //验证回调的正确性，还要避免重复通知
        params.remove("sign_type");
        try {
            //订单号，和订单实际金额在Service中判定
            boolean orderChecked = iDepositService.checkedOrder(params);
            boolean aliPayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            boolean sellerIdChecked = Configs.getPid().equals(params.get("seller_id"));
            boolean resultChecked = orderChecked && aliPayRSACheckedV2 && sellerIdChecked;
            if(!resultChecked){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过,再恶意请求我就报警找网警了");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝回调异常",e);
        }


        ServerResponse serverResponse = iDepositService.aliCallBack(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;

    }

    /**
     * 查询是否充值成功接口
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOderPayStatus(HttpSession session, String orderNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iDepositService.queryOrderPayStatus(user.getUserId(),orderNo);

    }








    @RequestMapping("illegal.do")
    @ResponseBody
    public ServerResponse getIllegalList(HttpSession session,
                                         @RequestParam(defaultValue = "1")int pageNum,
                                         @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iUserService.getIllegalList(user.getUserId(),pageNum,pageSize);
    }























}
