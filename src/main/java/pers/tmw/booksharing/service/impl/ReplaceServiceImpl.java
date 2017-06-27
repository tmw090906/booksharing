package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.*;
import pers.tmw.booksharing.pojo.*;
import pers.tmw.booksharing.service.IReplaceService;
import pers.tmw.booksharing.vo.ReplaceListVo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by tmw090906 on 2017/6/20.
 */
@Service("iReplaceService")
public class ReplaceServiceImpl implements IReplaceService {


    @Autowired
    private ReplaceMapper replaceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private SelfLibraryMapper selfLibraryMapper;



    @Override
    public ServerResponse getList(Long userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Replace> replaceList = replaceMapper.getListByUserId(userId);
        PageInfo pageInfo = new PageInfo(replaceList);
        List<ReplaceListVo> replaceListVoList = Lists.newArrayList();
        ReplaceListVo replaceListVo;
        for(Replace replace : replaceList){
            if(replace.getApplyUser() == userId){
                replaceListVo = this.assembleApplyReplaceListVo(replace);
            }else {
                replaceListVo = this.assembleAppliedReplaceListVo(replace);
            }
            replaceListVoList.add(replaceListVo);
        }
        pageInfo.setList(replaceListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private ReplaceListVo assembleApplyReplaceListVo(Replace replace){
        ReplaceListVo replaceListVo = new ReplaceListVo();
        Apply apply = applyMapper.selectByPrimaryKey(replace.getApplyId());

        replaceListVo.setReplaceId(replace.getReplaceId());
        replaceListVo.setApplyId(replace.getApplyId());

        replaceListVo.setOtherShippingId(replace.getAppliedShipping());
        replaceListVo.setOtherDeliverNo(replace.getAppliedDeliverNo());
        replaceListVo.setOtherStatus(replace.getAppliedStatus());
        replaceListVo.setOtherUserId(replace.getAppliedUser());
        replaceListVo.setOtherUserName(userMapper.getUserNameByUserId(replace.getAppliedUser()));
        replaceListVo.setOtherBookId(apply.getAppliedBook());
        replaceListVo.setOtherBookName(bookInfoMapper.getBookNameByBookId(apply.getAppliedBook()));

        replaceListVo.setBookId(apply.getApplyBook());
        replaceListVo.setBookName(bookInfoMapper.getBookNameByBookId(apply.getApplyBook()));
        replaceListVo.setDeliverNo(replace.getApplyDeliverNo().longValue());
        replaceListVo.setUserId(replace.getApplyUser());
        replaceListVo.setStatus(replace.getApplyStatus());
        replaceListVo.setShippingId(replace.getApplyShipping());

        replaceListVo.setReplaceStatus(replace.getStatus());

        return replaceListVo;
    }

    private ReplaceListVo assembleAppliedReplaceListVo(Replace replace){
        ReplaceListVo replaceListVo = new ReplaceListVo();
        Apply apply = applyMapper.selectByPrimaryKey(replace.getApplyId());

        replaceListVo.setReplaceId(replace.getReplaceId());
        replaceListVo.setApplyId(replace.getApplyId());

        replaceListVo.setOtherShippingId(replace.getApplyShipping());
        replaceListVo.setOtherDeliverNo(replace.getApplyDeliverNo().longValue());
        replaceListVo.setOtherStatus(replace.getApplyStatus());
        replaceListVo.setOtherUserId(replace.getApplyUser());
        replaceListVo.setOtherUserName(userMapper.getUserNameByUserId(replace.getApplyUser()));
        replaceListVo.setOtherBookId(apply.getApplyBook());
        replaceListVo.setOtherBookName(bookInfoMapper.getBookNameByBookId(apply.getApplyBook()));

        replaceListVo.setBookId(apply.getAppliedBook());
        replaceListVo.setBookName(bookInfoMapper.getBookNameByBookId(apply.getAppliedBook()));
        replaceListVo.setDeliverNo(replace.getAppliedDeliverNo());
        replaceListVo.setUserId(replace.getAppliedUser());
        replaceListVo.setStatus(replace.getAppliedStatus());
        replaceListVo.setShippingId(replace.getAppliedShipping());

        replaceListVo.setReplaceStatus(replace.getStatus());

        return replaceListVo;
    }


    @Override
    public ServerResponse updateShipping(Long userId, Long shippingId,Long replaceId) {
        Shipping shipping = shippingMapper.selectByUserIdShippingId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Replace replace = replaceMapper.selectByPrimaryKey(replaceId);
        if(replace == null){
            return ServerResponse.createByErrorMessage("没有该置换信息");
        }
        if(replace.getStatus() >= Const.ReplaceStatus.SUCCESS){
            return ServerResponse.createByErrorMessage("当前置换状态无法修改收货地址");
        }
        int rowCount = 0;
        if(replace.getApplyUser() == userId){
            if(replace.getAppliedStatus() >= Const.ReplaceStatus.SendStatus.SEND){
                return ServerResponse.createByErrorMessage("对方已经发货，无法修改收货地址");
            }
            replace.setApplyShipping(shippingId);
            rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        }else if(replace.getAppliedUser() == userId){
            if(replace.getApplyStatus() >= Const.ReplaceStatus.SendStatus.SEND){
                return ServerResponse.createByErrorMessage("对方已经发货，无法修改收货地址");
            }
            replace.setAppliedShipping(shippingId);
            rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        }
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改地址成功");
        }
        return ServerResponse.createByErrorMessage("修改地址失败");
    }

    @Override
    public ServerResponse sendBook(Long userId,Long replaceId,Long deliverNo){
        Replace replace = replaceMapper.selectByPrimaryKey(replaceId);
        if(replace == null){
            return ServerResponse.createByErrorMessage("没有该置换信息");
        }
        if(replace.getStatus() >= Const.ReplaceStatus.SUCCESS){
            return ServerResponse.createByErrorMessage("当前置换状态无法修改");
        }
        int rowCount = 0;
        if(replace.getApplyUser() == userId){
            if(replace.getApplyStatus() >= Const.ReplaceStatus.SendStatus.SEND){
                return ServerResponse.createByErrorMessage("请勿重复操作");
            }
            replace.setApplyStatus(Const.ReplaceStatus.SendStatus.SEND);
            replace.setApplyDeliverNo(new BigDecimal(deliverNo));
            rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        }else if(replace.getAppliedUser() == userId){
            if(replace.getAppliedStatus() >= Const.ReplaceStatus.SendStatus.SEND){
                return ServerResponse.createByErrorMessage("请勿重复操作");
            }
            replace.setAppliedStatus(Const.ReplaceStatus.SendStatus.SEND);
            replace.setAppliedDeliverNo(deliverNo);
            rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        }
        if(rowCount > 0){
            return ServerResponse.createBySuccess("发货成功");
        }
        return ServerResponse.createByErrorMessage("发货失败");
    }


    @Transactional
    @Override
    public ServerResponse deliverBook(Long userId,Long replaceId){
        Replace replace = replaceMapper.selectByPrimaryKey(replaceId);
        if(replace == null){
            return ServerResponse.createByErrorMessage("没有该置换信息");
        }
        if(replace.getStatus() >= Const.ReplaceStatus.SUCCESS){
            return ServerResponse.createByErrorMessage("当前置换状态无法修改");
        }
        int rowCount = 0;
        int flag = 0;
        if(replace.getApplyUser() == userId){
            if(replace.getAppliedStatus() != Const.ReplaceStatus.SendStatus.SEND){
                return ServerResponse.createByErrorMessage("当前状态无法操作");
            }
            replace.setAppliedStatus(Const.ReplaceStatus.SendStatus.DELIVER);
            if(replace.getApplyStatus() == Const.ReplaceStatus.SendStatus.DELIVER && replace.getAppliedStatus() == Const.ReplaceStatus.SendStatus.DELIVER){
                replace.setStatus(Const.ReplaceStatus.SUCCESS);
                flag = 1;
            }
            rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        }else if(replace.getAppliedUser() == userId){
            if(replace.getApplyStatus() != Const.ReplaceStatus.SendStatus.SEND){
                return ServerResponse.createByErrorMessage("当前状态无法操作");
            }
            replace.setApplyStatus(Const.ReplaceStatus.SendStatus.DELIVER);
            if(replace.getApplyStatus() == Const.ReplaceStatus.SendStatus.DELIVER && replace.getAppliedStatus() == Const.ReplaceStatus.SendStatus.DELIVER){
                replace.setStatus(Const.ReplaceStatus.SUCCESS);
                flag = 1;
            }
            rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        }
        //双方确认收货后，flag = 1
        //上面把置换状态更新为SUCCESS
        //释放双方保证金
        //这里 将交换出去的书从 1 或 2设置为 3已经交换出去了
        //把获得的书 添加到自己的图书库中的 4
        if(flag == 1 && rowCount > 0){

            //释放双方保证金
            Apply apply = applyMapper.selectByPrimaryKey(replace.getApplyId());
            Long appliedUserId = apply.getAppliedUser();
            Long applyUserId = apply.getApplyUser();
            Long appliedBookId = apply.getAppliedBook();
            Long applyBookId = apply.getApplyBook();
            BookInfo appliedBook = bookInfoMapper.selectByPrimaryKey(appliedBookId);
            BookInfo applyBook = bookInfoMapper.selectByPrimaryKey(applyBookId);
            userMapper.addApproveDeposit(appliedUserId,applyBook.getBookDeposit());
            userMapper.addApproveDeposit(applyUserId,appliedBook.getBookDeposit());


            //更新双方图书库图书状态，具体如上
            SelfLibrary applyUserApplyBook = selfLibraryMapper.selectLibraryByUserIdBookId(applyUserId,applyBookId);
            SelfLibrary applyUserAppliedBook = selfLibraryMapper.selectLibraryByUserIdBookId(applyUserId,appliedBookId);
            applyUserApplyBook.setStatus(Const.SelfLibraryStatus.EXCHANGED);
            selfLibraryMapper.updateByPrimaryKey(applyUserApplyBook);
            this.setSelfLibrary(applyUserAppliedBook,applyUserId,appliedBookId);
            SelfLibrary appliedUserApplyBook = selfLibraryMapper.selectLibraryByUserIdBookId(appliedUserId,applyBookId);
            SelfLibrary appliedUserAppliedBook = selfLibraryMapper.selectLibraryByUserIdBookId(appliedUserId,appliedBookId);
            appliedUserAppliedBook.setStatus(Const.SelfLibraryStatus.EXCHANGED);
            selfLibraryMapper.updateByPrimaryKey(appliedUserAppliedBook);
            this.setSelfLibrary(appliedUserApplyBook,appliedUserId,applyBookId);
        }
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("确认收货成功！");
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    private void setSelfLibrary(SelfLibrary selfLibrary , Long userId, Long bookId){
        if(selfLibrary != null){
            selfLibrary.setStatus(Const.SelfLibraryStatus.NO_READE_HAD);
            selfLibraryMapper.updateByPrimaryKey(selfLibrary);
        }else {
            selfLibrary = new SelfLibrary();
            selfLibrary.setStatus(Const.SelfLibraryStatus.NO_READE_HAD);
            selfLibrary.setBookId(bookId);
            selfLibrary.setUserId(userId);
            selfLibraryMapper.insert(selfLibrary);
        }
    }

    @Override
    public ServerResponse cancelReplace(Long replaceId){
        Replace replace = replaceMapper.selectByPrimaryKey(replaceId);
        if(replace == null){
            return ServerResponse.createByErrorMessage("没有该置换信息");
        }
        if(replace.getStatus() >= Const.ReplaceStatus.SUCCESS){
            return ServerResponse.createByErrorMessage("当前置换状态无法修改");
        }
        if(replace.getAppliedStatus() >= Const.ReplaceStatus.SendStatus.SEND || replace.getApplyStatus() >= Const.ReplaceStatus.SendStatus.SEND){
            return ServerResponse.createByErrorMessage("已经发货或已经收货，无法取消置换");
        }
        replace.setStatus(Const.ReplaceStatus.CANCEL);
        int rowCount = replaceMapper.updateByPrimaryKeySelective(replace);
        //取消接口释放双方保证金
        if(rowCount > 0){
            Long applyUserId = replace.getApplyUser();
            Long appliedUserId = replace.getAppliedUser();
            Apply apply = applyMapper.selectByPrimaryKey(replace.getApplyId());
            Long applyBookId = apply.getApplyBook();
            Long appliedBookId = apply.getAppliedBook();
            BookInfo applyBook = bookInfoMapper.selectByPrimaryKey(applyBookId);
            BookInfo appliedBook = bookInfoMapper.selectByPrimaryKey(appliedBookId);
            userMapper.addApproveDeposit(appliedUserId,applyBook.getBookDeposit());
            userMapper.addApproveDeposit(applyUserId,appliedBook.getBookDeposit());
            return ServerResponse.createBySuccessMessage("取消置换成功");
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }


















}
