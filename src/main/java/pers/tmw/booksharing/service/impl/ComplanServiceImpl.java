package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.Page;
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
import pers.tmw.booksharing.service.IComplanService;
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.vo.ComplanVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tmw090906 on 2017/6/20.
 */
@Service("iComplanService")
public class ComplanServiceImpl implements IComplanService {

    @Autowired
    private ComplanMapper complanMapper;
    @Autowired
    private ReplaceMapper replaceMapper;
    @Autowired
    private IllegalMapper illegalMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private UserMapper userMapper;


    //前台
    @Transactional
    @Override
    public ServerResponse complan(Long userId, Long replaceId,String reason){
        Replace replace = replaceMapper.selectByPrimaryKey(replaceId);
        if(replace == null){
            return ServerResponse.createByErrorMessage("没有该置换信息");
        }
        if(replace.getStatus() >= Const.ReplaceStatus.SUCCESS){
            return ServerResponse.createByErrorMessage("当前置换状态无法修改");
        }
        replace.setStatus(Const.ReplaceStatus.USER_COMPLAN);
        Complan complan = new Complan();
        complan.setReplaceId(replace.getReplaceId());
        complan.setUserId(userId);
        complan.setReason(reason);
        complan.setStatus(Const.ComplanStatus.WAIT_MANAGE);

        int updateCount = replaceMapper.updateByPrimaryKeySelective(replace);
        if( updateCount > 0 ) {
            int insertCount = complanMapper.insert(complan);
            if (insertCount > 0) {
                return ServerResponse.createBySuccessMessage("投诉成功");
            }
        }
        return ServerResponse.createByErrorMessage("投诉失败");
    }

    @Override
    public ServerResponse getList(Long userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("UPDATE_TIME desc");
        List<Complan> complanList = complanMapper.getListByUserId(userId);
        List<ComplanVo> complanVoList = this.assembleComplanVoList(complanList);
        PageInfo pageInfo = new PageInfo(complanVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<ComplanVo> assembleComplanVoList(List<Complan> complanList){
        List<ComplanVo> complanVoList = Lists.newArrayList();
        for(Complan complan : complanList){
            ComplanVo complanVo = new ComplanVo();
            complanVo.setComplanId(complan.getComplanId());
            complanVo.setCreateTime(DateTimeUtil.dateToStr(complan.getCreateTime()));
            complanVo.setReplaceId(complan.getReplaceId());
            short status = complan.getStatus();
            complanVo.setStatus(status);
            complanVo.setUpdateTime(DateTimeUtil.dateToStr(complan.getUpdateTime()));
            complanVo.setUserId(complan.getUserId());
            String statusStr;
            if(status == 1){
                statusStr = "管理员未处理";
            }else if(status == 2){
                statusStr = "投诉成功";
            }else {
                statusStr = "投诉失败";
            }
            complanVo.setStatusStr(statusStr);
            complanVoList.add(complanVo);
        }
        return complanVoList;
    }










    //后台
    @Override
    public ServerResponse manageGetList(Short status,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Complan> complanList = complanMapper.getListByStatus(status);
        PageInfo pageInfo = new PageInfo(complanList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Transactional
    @Override
    public ServerResponse manageComplan(Long managerId,Short status,Long complanId){
        //投诉成功时，对被投诉用户添加违规信息
        //同时，将被投诉用户保证金存入投诉用户中   这里的保证金是：投诉用户书籍所需保证金
        //释放投诉用户的保证金  这里的保证金是：被投诉用户书籍所需的保证金
        if(status == Const.ComplanStatus.COMPLAN_SUCCESS){
            Complan complan = complanMapper.selectByPrimaryKey(complanId);
            if(complan == null){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
            }
            complan.setStatus(status);
            Illegal illegal = new Illegal();
            illegal.setReplaceId(complan.getReplaceId());


            Long victimUserId = complan.getUserId();
            Replace replace = replaceMapper.selectByPrimaryKey(complan.getReplaceId());
            Long illegalUserId = replace.getApplyUser() == victimUserId ? replace.getAppliedUser() : replace.getApplyUser();
            Apply apply = applyMapper.selectByPrimaryKey(replace.getApplyId());
            Long victimUserBookId = replace.getApplyUser() == victimUserId ? apply.getApplyBook() : apply.getAppliedBook();
            Long IllegalUserBookId = replace.getApplyUser() == illegalUserId ?  apply.getApplyBook() : apply.getAppliedBook();
            BookInfo victimUserBook = bookInfoMapper.selectByPrimaryKey(victimUserBookId);
            BookInfo illegalUserBook = bookInfoMapper.selectByPrimaryKey(IllegalUserBookId);


            illegal.setVictimId(victimUserId);
            illegal.setUserId(illegalUserId);
            illegal.setDeposit(victimUserBook.getBookDeposit());
            illegal.setManager(managerId);
            int insertCount = illegalMapper.insert(illegal);
            if(insertCount > 0){
                userMapper.subMoneyByIllegal(illegalUserId,victimUserBook.getBookDeposit());
                userMapper.addMoneyByIllegal(victimUserId,victimUserBook.getBookDeposit());
                userMapper.addApproveDeposit(victimUserId,illegalUserBook.getBookDeposit());
                int rowCount = complanMapper.updateByPrimaryKeySelective(complan);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("投诉处理成功");
                }
            }
        }


        if(status == Const.ComplanStatus.COMPLAN_FAIL){
            if(status == Const.ComplanStatus.COMPLAN_SUCCESS) {
                Complan complan = complanMapper.selectByPrimaryKey(complanId);
                if (complan == null) {
                    return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
                }
                complan.setStatus(status);
                int rowCount = complanMapper.updateByPrimaryKeySelective(complan);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("投诉处理成功");
                }
            }
        }
        return ServerResponse.createByErrorMessage("投诉处理失败");
    }







}
