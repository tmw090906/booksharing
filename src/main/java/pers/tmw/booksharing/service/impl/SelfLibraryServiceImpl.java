package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.BookInfoMapper;
import pers.tmw.booksharing.dao.SelfLibraryMapper;
import pers.tmw.booksharing.pojo.BookInfo;
import pers.tmw.booksharing.pojo.SelfLibrary;
import pers.tmw.booksharing.service.ISelfLibraryService;
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.vo.SelfLibraryVo;

import java.util.List;

/**
 * Created by tmw090906 on 2017/6/11.
 */
@Service("iSelfLibraryService")
public class SelfLibraryServiceImpl implements ISelfLibraryService {

    @Autowired
    private SelfLibraryMapper selfLibraryMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public ServerResponse addBook(Long userId, Long bookId, Short status) {
        if(bookId==null || status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if(!Const.SelfLibraryStatus.STATUS.contains(status)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        SelfLibrary selfLibraryTest = selfLibraryMapper.selectLibraryByUserIdBookId(userId,bookId);
        if(selfLibraryTest != null){
            return ServerResponse.createByErrorMessage("此书已经在自建图书库中");
        }
        SelfLibrary selfLibrary = new SelfLibrary();
        selfLibrary.setBookId(bookId);
        selfLibrary.setStatus(status);
        selfLibrary.setUserId(userId);
        int rowCount = selfLibraryMapper.insert(selfLibrary);
        if(rowCount > 0){
            return ServerResponse.createBySuccess(selfLibrary);
        }
        return ServerResponse.createByErrorMessage("添加出错");
    }


    @Override
    public ServerResponse getList(Long userId, Short status, int pageNum, int pageSize) {
        if(status == null || !Const.SelfLibraryStatus.STATUS.contains(status)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SelfLibrary> selfLibraryList = selfLibraryMapper.selectSelfLibraryList(userId,status);
        PageInfo pageInfo = new PageInfo(selfLibraryList);
        List<SelfLibraryVo> selfLibraryVoList = assembleSelfLibraryVoList(selfLibraryList);
        pageInfo.setList(selfLibraryVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<SelfLibraryVo> assembleSelfLibraryVoList(List<SelfLibrary> selfLibraryList){
        List<SelfLibraryVo> selfLibraryVoList = Lists.newArrayList();
        for(SelfLibrary selfLibraryItem : selfLibraryList){
            BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(selfLibraryItem.getBookId());
            SelfLibraryVo selfLibraryVo = new SelfLibraryVo();
            selfLibraryVo.setSelfLibraryId(selfLibraryItem.getId());
            selfLibraryVo.setStatus(selfLibraryItem.getStatus());
            selfLibraryVo.setBookAuthor(bookInfo.getBookAuthor());
            selfLibraryVo.setBookId(bookInfo.getBookId());
            selfLibraryVo.setBookName(bookInfo.getBookName());
            selfLibraryVo.setPublishTrim(bookInfo.getPublishTrim());
            selfLibraryVo.setUpdateTime(DateTimeUtil.dateToStr(bookInfo.getUpdateTime()));
            selfLibraryVoList.add(selfLibraryVo);
        }
        return selfLibraryVoList;
    }


    @Override
    public ServerResponse updateStatus(Long userId, Long id, Short status) {
        if(id==null || status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if(!Const.SelfLibraryStatus.STATUS.contains(status)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        SelfLibrary selfLibrary = selfLibraryMapper.selectSelfLibraryIdUserId(userId,id);
        if(selfLibrary == null){
            return ServerResponse.createByErrorMessage("用户图书库无此记录");
        }
        selfLibrary.setStatus(status);
        int rowCount = selfLibraryMapper.updateByPrimaryKey(selfLibrary);
        if(rowCount > 0){
            return ServerResponse.createBySuccess(selfLibrary);
        }
        return ServerResponse.createByErrorMessage("更新出错");
    }
}
