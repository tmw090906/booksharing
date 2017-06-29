package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.*;
import pers.tmw.booksharing.pojo.*;
import pers.tmw.booksharing.service.IApplyService;
import pers.tmw.booksharing.util.BigDecimalUtil;
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.vo.ApplyListVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tmw090906 on 2017/6/19.
 */
@Service("iApplyService")
public class ApplyServiceImpl implements IApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReplaceMapper replaceMapper;
    @Autowired
    private SelfLibraryMapper selfLibraryMapper;


    @Override
    public ServerResponse commitApply(Long exchangeUserId, Long userId, Long exchangeBookId, Long bookId){
        if(userId == null || exchangeBookId == null || bookId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        User user = userMapper.selectByPrimaryKey(exchangeUserId);
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        SelfLibrary selfLibrary = selfLibraryMapper.selectLibraryByUserIdBookId(exchangeUserId,bookId);
        //若自图书库无此书，先将此书添加到  5 未读想读
        if(selfLibrary == null){
            selfLibrary = new SelfLibrary();
            selfLibrary.setUserId(exchangeUserId);
            selfLibrary.setStatus(Const.SelfLibraryStatus.NO_READE_NOT_HAD);
            selfLibrary.setBookId(bookId);
            selfLibraryMapper.insert(selfLibrary);
        }
        //若图书库中有此书，且已经读过或拥有则不能提交交换申请
        if(selfLibrary.getStatus() != Const.SelfLibraryStatus.NO_READE_NOT_HAD){
            return ServerResponse.createByErrorMessage("此书已读过或已经拥有");
        }
        //判断保证金是否足够，不足则无法提交申请
        if(bookInfo.getBookDeposit().compareTo(user.getApproveDeposit()) == 1){
            return ServerResponse.createByErrorMessage("可用保证金不足，无法提交申请");
        }
        Apply apply = new Apply();
        apply.setAppliedUser(userId);
        apply.setAppliedBook(bookId);
        apply.setApplyBook(exchangeBookId);
        apply.setApplyUser(exchangeUserId);
        apply.setStatus(Const.ApplyStatus.SEND);
        int repeatTest = applyMapper.repeatApplyTest(userId,bookId,exchangeUserId,exchangeBookId);
        if(repeatTest > 0){
            return ServerResponse.createByErrorMessage("无法重复发送申请");
        }
        int resultCount = applyMapper.insert(apply);
        if(resultCount > 0){
            //足够则扣掉保证金
            userMapper.subApproveDeposit(exchangeUserId,bookInfo.getBookDeposit());
            return ServerResponse.createBySuccessMessage("申请已经发送成功");
        }
        return ServerResponse.createByErrorMessage("申请发送失败");
    }

    @Override
    public ServerResponse getAppliedList(Long userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("UPDATE_TIME desc");
        List<Apply> applyList = applyMapper.getApplyListByAppliedUserIdStatus(userId);
        PageInfo pageInfo = new PageInfo(applyList);
        List<ApplyListVo> applyListVoList = this.assembleApplyListVoList(applyList);
        pageInfo.setList(applyListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<ApplyListVo> assembleApplyListVoList(List<Apply> applyList){
        List<ApplyListVo> applyListVoList = Lists.newArrayList();
        for(Apply apply : applyList){
            ApplyListVo applyListVo = new ApplyListVo();
            applyListVo.setApplyId(apply.getApplyId());
            applyListVo.setAppliedBookId(apply.getAppliedBook());
            applyListVo.setAppliedBookName(bookInfoMapper.getBookNameByBookId(apply.getAppliedBook()));
            applyListVo.setAppliedUserId(apply.getAppliedUser());
            applyListVo.setAppliedUserName(userMapper.getUserNameByUserId(apply.getAppliedUser()));
            applyListVo.setApplyBookId(apply.getApplyBook());
            applyListVo.setApplyBookName(bookInfoMapper.getBookNameByBookId(apply.getApplyBook()));
            applyListVo.setApplyUserId(apply.getApplyUser());
            applyListVo.setApplyUserName(userMapper.getUserNameByUserId(apply.getApplyUser()));
            applyListVo.setUpdateTime(DateTimeUtil.dateToStr(apply.getUpdateTime()));
            String statusStr;
            short status = apply.getStatus();
            if(status == 1){
                statusStr = "申请未处理";
            }else if (status == 2){
                statusStr = "申请已经同意";
            }else {
                statusStr = "申请已被拒绝";
            }
            applyListVo.setStatusStr(statusStr);
            applyListVo.setStatus(status);
            applyListVo.setReason(apply.getReason());
            applyListVoList.add(applyListVo);
        }
        return applyListVoList;
    }

    @Override
    public ServerResponse getApplyList(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Apply> applyList = applyMapper.getApplyListByApplyUserIdStatus(userId);
        PageInfo pageInfo = new PageInfo(applyList);
        List<ApplyListVo> applyListVoList = this.assembleApplyListVoList(applyList);
        pageInfo.setList(applyListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Transactional
    @Override
    public ServerResponse manageApply(Long userId,Long applyId,Short status,String reason){
        if(!Const.ApplyStatus.STATUS.contains(status)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Apply apply = applyMapper.selectByApplyIdAndAppliedUserId(applyId,userId);
        if(apply == null){
            return ServerResponse.createByErrorMessage("没有此申请！");
        }
        apply.setStatus(status);
        apply.setReason(reason);
        //拒绝释放申请人保证金
        int rowCount;
        if(status == Const.ApplyStatus.REFUSED){
            BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(apply.getAppliedBook());
            applyMapper.updateByPrimaryKey(apply);
            userMapper.addApproveDeposit(apply.getApplyUser(),bookInfo.getBookDeposit());
            return ServerResponse.createBySuccessMessage("申请处理成功");
        }
        //接受则判断保证金是否足够，足够则对保证金进行预扣
        if(status == Const.ApplyStatus.ACCEPT){
            User user = userMapper.selectByPrimaryKey(apply.getAppliedUser());
            BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(apply.getApplyBook());
            if(bookInfo.getBookDeposit().compareTo(user.getApproveDeposit()) == 1){
                return ServerResponse.createByErrorMessage("保证金余额不足,无法进行本次置换");
            }
            userMapper.subApproveDeposit(apply.getAppliedUser(),bookInfo.getBookDeposit());
            Replace replace = new Replace();
            replace.setApplyId(apply.getApplyId());
            replace.setApplyUser(apply.getApplyUser());
            replace.setAppliedUser(apply.getAppliedUser());
            replace.setApplyStatus(Const.ReplaceStatus.SendStatus.WAIT_SEND);
            replace.setAppliedStatus(Const.ReplaceStatus.SendStatus.WAIT_SEND);
            replace.setStatus(Const.ReplaceStatus.WAIT_MANAGE);
            applyMapper.updateByPrimaryKey(apply);
            rowCount = replaceMapper.insertSelective(replace);
            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("申请处理成功");
            }
        }
        return ServerResponse.createByErrorMessage("申请处理失败");
    }


















}
