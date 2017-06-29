package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.*;
import pers.tmw.booksharing.pojo.*;
import pers.tmw.booksharing.service.IBookInfoService;
import pers.tmw.booksharing.service.ICategoryService;
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.util.FTPUtil;
import pers.tmw.booksharing.util.PropertiesUtil;
import pers.tmw.booksharing.vo.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by tmw090906 on 2017/6/8.
 */
@Service("iBookInfoService")
public class BookInfoServiceImpl implements IBookInfoService {

    private Logger logger = LoggerFactory.getLogger(BookInfoServiceImpl.class);

    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private SelfLibraryMapper selfLibraryMapper;
    @Autowired
    private UserMapper userMapper;


    //前台
    @Override
    public ServerResponse getBookList(String textSearch, Long categoryId, int pageNum, int pageSize, String orderBy){
        List<Long> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(textSearch)){
                //没有该分类,并且还不用关键字查找,这个时候返回一个空的结果集,不报错
                List<BookListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = (List<Long>)iCategoryService.selectCategoryAndDeepChildrenCategory(category.getCategoryId()).getDate();
        }

        if(StringUtils.isNotBlank(textSearch)){
            textSearch = new StringBuilder().append("%").append(textSearch).append("%").toString();
        }


        //分页在这里开始的原因是，上面有一个SQL查询语句
        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(Const.BookListOrders.ORDERS.contains(orderBy)){
            PageHelper.orderBy(orderBy + " desc");
        }
        List<BookInfo> bookInfoList = bookInfoMapper.selectByNameAndCategoryIds(StringUtils.isBlank(textSearch)?null:textSearch,categoryIdList.size()==0?null:categoryIdList);
        List<BookListVo> bookListVoList = Lists.newArrayList();
        for(BookInfo bookInfo : bookInfoList){
            BookListVo bookListVo = this.assembleBookListVo(bookInfo);
            bookListVoList.add(bookListVo);
        }

        PageInfo pageInfo = new PageInfo(bookInfoList);
        pageInfo.setList(bookListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    /**
     * 将BookInfo拼接成前台需要的BookListVo方法
     * @param bookInfo
     * @return
     */
    private BookListVo assembleBookListVo(BookInfo bookInfo){
        BookListVo bookListVo = new BookListVo();
        bookListVo.setBookAuthor(bookInfo.getBookAuthor());
        bookListVo.setBookId(bookInfo.getBookId());
        bookListVo.setBookImage(bookInfo.getBookImage());
        bookListVo.setBookName(bookInfo.getBookName());
        bookListVo.setCreateTime(DateTimeUtil.dateToStr(bookInfo.getCreateTime()));
        bookListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return bookListVo;
    }


    @Override
    public ServerResponse adviceAddBook(Advice advice){
        advice.setStatus(Const.AdviceStatus.COMMIT);
        int resultCount = adviceMapper.insertSelective(advice);
        if(resultCount > 0){
            return ServerResponse.createBySuccess("成功提交申请",advice);
        }
        return ServerResponse.createByErrorMessage("提交申请失败");
    }



    //后台
    @Override
    public ServerResponse saveOrUpdateBook(User user,BookInfo bookInfo){
        if(bookInfo != null){
            bookInfo.setCreatedby(user.getUsername());
            if(bookInfo.getBookId() != null){
                int rowCount = bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }else {
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            }else {
                int rowCount = bookInfoMapper.insertSelective(bookInfo);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }else {
                    return ServerResponse.createByErrorMessage("新增产品失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse manageBookDetail(Long bookId){
        if(bookId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        if(bookInfo == null){
            return ServerResponse.createByErrorMessage("图书不存在");
        }
        BookInfoDetailVo bookInfoDetailVo = this.assembleBookInfoDetailVo(bookInfo);
        return ServerResponse.createBySuccess(bookInfoDetailVo);

    }

    private BookInfoDetailVo assembleBookInfoDetailVo(BookInfo bookInfo){
        BookInfoDetailVo bookInfoDetailVo = new BookInfoDetailVo();
        bookInfoDetailVo.setBookAuthor(bookInfo.getBookAuthor());
        bookInfoDetailVo.setBookDeposit(bookInfo.getBookDeposit());
        bookInfoDetailVo.setBookDetail(bookInfo.getBookDetail());
        bookInfoDetailVo.setBookId(bookInfo.getBookId());
        bookInfoDetailVo.setBookImage(bookInfo.getBookImage());
        bookInfoDetailVo.setBookName(bookInfo.getBookName());
        bookInfoDetailVo.setBookPrice(bookInfo.getBookPrice());
        Category category = categoryMapper.selectByPrimaryKey(bookInfo.getCategoryId());
        bookInfoDetailVo.setCategoryName(category.getCategoryName());
        bookInfoDetailVo.setCreateTime(DateTimeUtil.dateToStr(bookInfo.getCreateTime()));
        bookInfoDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        bookInfoDetailVo.setIsbn(bookInfo.getIsbn());
        bookInfoDetailVo.setPublishEdition(bookInfo.getPublishEdition());
        bookInfoDetailVo.setPublishTime(bookInfo.getPublishTime());
        bookInfoDetailVo.setPublishTrim(bookInfo.getPublishTrim());
        return bookInfoDetailVo;
    }

    @Override
    public ServerResponse getBookList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<BookInfo> bookInfoList = bookInfoMapper.selectList();
        List<BookInfoDetailVo> bookInfoDetailVoList = Lists.newArrayList();
        for(BookInfo bookInfoTemp : bookInfoList){
            BookInfoDetailVo bookInfoDetailVo = this.assembleBookInfoDetailVo(bookInfoTemp);
            bookInfoDetailVoList.add(bookInfoDetailVo);
        }
        PageInfo pageInfo = new PageInfo(bookInfoDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse getCommitAdviceList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Advice> adviceList = adviceMapper.selectList();
        List<AdviceListVo> adviceListVoList = this.assembleAdviceListVo(adviceList);
        PageInfo pageInfo = new PageInfo(adviceListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse manageAdvice(Long userId,Long adviceId,Short status){
        if(userId==null || adviceId==null || status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if(status != Const.AdviceStatus.NOT_FIND && status != Const.AdviceStatus.SUCCESS){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Advice advice = adviceMapper.selectByPrimaryKey(adviceId);
        if(advice == null){
            return ServerResponse.createByErrorMessage("找不到该申请");
        }
        advice.setStatus(status);
        advice.setManagerId(userId);
        int rowCount = adviceMapper.updateByPrimaryKeySelective(advice);
        if(rowCount > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("处理失败");
    }

    private List<AdviceListVo> assembleAdviceListVo(List<Advice> adviceList){
        List<AdviceListVo> adviceListVoList = Lists.newArrayList();
        if(adviceList.size() > 0){
            for(Advice adviceItem : adviceList){
                AdviceListVo adviceListVo = new AdviceListVo();
                adviceListVo.setAdviceId(adviceItem.getAdviceId());
                adviceListVo.setCreateTime(DateTimeUtil.dateToStr(adviceItem.getCreateTime()));
                short status = adviceItem.getStatus();
                adviceListVo.setStatus(status);
                String statusStr;
                if(status == 1){
                    statusStr = "正在处理";
                }else if(status == 2){
                    statusStr = "找不到此书";
                }else {
                    statusStr = "成功添加";
                }
                adviceListVo.setStatusStr(statusStr);
                adviceListVoList.add(adviceListVo);
            }
        }
        return adviceListVoList;
    }

    @Override
    public ServerResponse getAdviceDetail(Long adviceId){
        Advice advice = adviceMapper.selectByPrimaryKey(adviceId);
        if(advice == null){
            return ServerResponse.createByErrorMessage("找不到该申请");
        }
        AdviceVo adviceVo = new AdviceVo(advice);
        short status = advice.getStatus();
        String statusStr;
        if(status == 1){
            statusStr = "正在处理";
        }else if(status == 2){
            statusStr = "找不到此书";
        }else {
            statusStr = "成功添加";
        }
        adviceVo.setStautsStr(statusStr);
        return ServerResponse.createBySuccess(adviceVo);
    }

    @Override
    public ServerResponse manageGetAdviceList(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Advice> adviceList = adviceMapper.selectListByManagerId(userId);
        List<AdviceListVo> adviceListVoList = this.assembleAdviceListVo(adviceList);
        PageInfo pageInfo = new PageInfo(adviceListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse getAdviceList(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Advice> adviceList = adviceMapper.selectListByUserId(userId);
        List<AdviceListVo> adviceListVoList = this.assembleAdviceListVo(adviceList);
        PageInfo pageInfo = new PageInfo(adviceListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getExchangeInfoOne(Long userId,Long bookId){
        if(bookId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Long> userIdList = selfLibraryMapper.getUserIdByBookId(bookId);
        if(userIdList == null || userIdList.size() == 0){
            return ServerResponse.createByErrorMessage("此书暂无指定兑换信息");
        }
        List<ExchangeOneListVo> exchangeOneListVoList = Lists.newArrayList();
        for(Long userIdTemp : userIdList){
            if(userIdTemp != userId){
                User userTemp = userMapper.selectByPrimaryKey(userIdTemp);
                List<Long> bookIdList = selfLibraryMapper.getBookIdByUserId(userIdTemp);
                for(Long bookIdTemp : bookIdList){
                    BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookIdTemp);
                    ExchangeOneListVo exchangeOneListVo = this.assembleExchangeOneListVo(userTemp,bookInfo,bookId,userId);
                    exchangeOneListVoList.add(exchangeOneListVo);
                }
            }
        }
        Set<Long> bookIdList = selfLibraryMapper.getBookIdBySelfUserId(userId);
        for(int i = exchangeOneListVoList.size() - 1 ; i >= 0 ; i--){
            if(!bookIdList.contains(exchangeOneListVoList.get(i).getExchangeBookId())){
                exchangeOneListVoList.remove(i);
            }
        }
        if(exchangeOneListVoList.size() == 0 || exchangeOneListVoList ==null){
            return ServerResponse.createByErrorMessage("没有匹配的指定兑换信息");
        }
        return ServerResponse.createBySuccess(exchangeOneListVoList);
    }

    private ExchangeOneListVo assembleExchangeOneListVo(User userTemp,BookInfo bookInfo,Long bookId,Long userId){
        BookInfo exchangeBook = bookInfoMapper.selectByPrimaryKey(bookId);
        ExchangeOneListVo exchangeOneListVo = new ExchangeOneListVo();
        exchangeOneListVo.setUserId(userTemp.getUserId());
        exchangeOneListVo.setUserName(userTemp.getUsername());
        exchangeOneListVo.setBookAuthor(exchangeBook.getBookAuthor());
        exchangeOneListVo.setBookId(exchangeBook.getBookId());
        exchangeOneListVo.setBookName(exchangeBook.getBookName());
        exchangeOneListVo.setExchangeBookId(bookInfo.getBookId());
        exchangeOneListVo.setExchangeBookName(bookInfo.getBookName());
        exchangeOneListVo.setExchangeUserId(userId);
        return exchangeOneListVo;
    }

    @Override
    public ServerResponse getExchangeInfo(Long userId, Long bookId) {
        if(bookId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Long> userIdList = selfLibraryMapper.getUserIdByAllBookId(bookId);
        if(userIdList == null || userIdList.size() == 0){
            return ServerResponse.createByErrorMessage("此书暂无非指定兑换信息");
        }
        List<ExchangeListVo> exchangeListVoList = Lists.newArrayList();
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        for(Long userIdTemp : userIdList){
            exchangeListVoList.add(this.assembleExchangeListVo(userIdTemp,bookInfo,userId));
        }
        return ServerResponse.createBySuccess(exchangeListVoList);
    }

    private ExchangeListVo assembleExchangeListVo(Long userIdTemp,BookInfo bookInfo,Long userId){
        ExchangeListVo exchangeListVo = new ExchangeListVo();
        exchangeListVo.setBookAuthor(bookInfo.getBookAuthor());
        exchangeListVo.setBookId(bookInfo.getBookId());
        exchangeListVo.setBookName(bookInfo.getBookName());
        exchangeListVo.setExchangeUserId(userId);
        User user = userMapper.selectByPrimaryKey(userIdTemp);
        exchangeListVo.setUserId(user.getUserId());
        exchangeListVo.setUserName(user.getUsername());
        return exchangeListVo;
    }

    @Override
    public ServerResponse getSelfBookList(Long userId){
        List<Long> bookIdList = selfLibraryMapper.getSelfBookIdByHad(userId);
        if(bookIdList == null || bookIdList.size() ==0){
            return ServerResponse.createByErrorMessage("您当前没有可交换的图书,请先添加");
        }
        List<BookInfo> bookInfoList = bookInfoMapper.getBookListByBookIds(bookIdList);
        List<HadBookListVo> hadBookListVoList = this.assembleHadBookListVoList(bookInfoList);
        return ServerResponse.createBySuccess(hadBookListVoList);
    }
    private List<HadBookListVo> assembleHadBookListVoList(List<BookInfo> bookInfoList){
        List<HadBookListVo> hadBookListVoList = Lists.newArrayList();
        for(BookInfo bookInfo : bookInfoList){
            HadBookListVo hadBookListVo = new HadBookListVo();
            hadBookListVo.setBookAuthor(bookInfo.getBookAuthor());
            hadBookListVo.setBookId(bookInfo.getBookId());
            hadBookListVo.setBookName(bookInfo.getBookName());
            hadBookListVoList.add(hadBookListVo);
        }
        return hadBookListVoList;
    }
















    @Override
    public String upload(MultipartFile file,String path,String currentUser){
        String filename = file.getOriginalFilename();
        //拓展名
        String fileExtensionName = filename.substring(filename.lastIndexOf(".")+1);
        String uploadFileName = DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd_HH_mm_ss") + "_" + currentUser + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名：{},上传的路径：{},新文件名:{}",filename,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            //文件上传成功
            file.transferTo(targetFile);


            //已经上传到ftp服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            //删除targetFile里的文件
            targetFile.delete();


        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }

}
