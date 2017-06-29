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
import pers.tmw.booksharing.service.IComplanService;
import pers.tmw.booksharing.service.IReplaceService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/20.
 */
@Controller
@RequestMapping("/replace/")
public class ReplaceController {


    @Autowired
    private IReplaceService iReplaceService;
    @Autowired
    private IComplanService icomplanService;

    /**
     * 得到置换列表接口
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,Short status,
                                  @RequestParam(defaultValue = "1")int pageNum,
                                  @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iReplaceService.getList(user.getUserId(),pageNum,pageSize,status);

    }

    /**
     * 修改地址列表接口
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("update_shipping.do")
    @ResponseBody
    public ServerResponse updateSipping(HttpSession session,Long replaceId,Long shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iReplaceService.updateShipping(user.getUserId(),shippingId,replaceId);

    }


    /**
     * 点击发货并填写订单接口
     * @param session
     * @param replaceId
     * @param deliverNo
     * @return
     */
    @RequestMapping("send.do")
    @ResponseBody
    public ServerResponse sendBook(HttpSession session,Long replaceId,Long deliverNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iReplaceService.sendBook(user.getUserId(),replaceId,deliverNo);

    }


    /**
     * 确认收货接口
     * @param session
     * @param replaceId
     * @return
     */
    @RequestMapping("deliver.do")
    @ResponseBody
    public ServerResponse deliverBook(HttpSession session,Long replaceId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iReplaceService.deliverBook(user.getUserId(),replaceId);
    }

    /**
     * 取消置换接口
     * @param session
     * @param replaceId
     * @return
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpSession session,Long replaceId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iReplaceService.cancelReplace(replaceId);
    }


    /**
     * 投诉接口
     * @param session
     * @param replaceId
     * @return
     */
    @RequestMapping("complan.do")
    @ResponseBody
    public ServerResponse complan(HttpSession session,Long replaceId,@RequestParam(required = false) String reason){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return icomplanService.complan(user.getUserId(),replaceId,reason);
    }


    /**
     * 得到投诉列表接口
     * @param session
     * @return
     */
    @RequestMapping("complan_list.do")
    @ResponseBody
    public ServerResponse getComplanList(HttpSession session,
                                         @RequestParam(defaultValue = "1")int pageNum,
                                         @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return icomplanService.getList(user.getUserId(),pageNum,pageSize);
    }




























}
