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
import pers.tmw.booksharing.service.IApplyService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/19.
 */
@Controller
@RequestMapping("/apply/")
public class ApplyController {

    @Autowired
    private IApplyService iApplyService;


    /**
     * 提交图书交换申请
     * @param session
     * @param userId
     * @param exchangeBookId
     * @param bookId
     * @return
     */
    @RequestMapping("commit.do")
    @ResponseBody
    public ServerResponse commitApply(HttpSession session,Long userId,Long exchangeBookId,Long bookId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iApplyService.commitApply(user.getUserId(),userId,exchangeBookId,bookId);
    }

    /**
     * 得到被申请列表，status为申请状态
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("applied_list.do")
    @ResponseBody
    public ServerResponse getAppliedList(HttpSession session,
                                         @RequestParam(defaultValue = "1")int pageNum,
                                         @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iApplyService.getAppliedList(user.getUserId(),pageNum,pageSize);
    }

    /**
     * 得到自己申请列表，status为申请状态
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("apply_list.do")
    @ResponseBody
    public ServerResponse getApplyList(HttpSession session,
                                         @RequestParam(defaultValue = "1")int pageNum,
                                         @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iApplyService.getApplyList(user.getUserId(),pageNum,pageSize);
    }

    /**
     * 处理申请
     * @param session
     * @param applyId
     * @param status
     * @param reason
     * @return
     */
    @RequestMapping("manage_apply.do")
    @ResponseBody
    public ServerResponse manageApply(HttpSession session,Long applyId,Short status,@RequestParam(value = "reason",required = false)String reason){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iApplyService.manageApply(user.getUserId(),applyId,status,reason);
    }



}
