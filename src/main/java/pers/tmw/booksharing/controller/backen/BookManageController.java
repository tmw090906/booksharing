package pers.tmw.booksharing.controller.backen;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ResponseCode;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.BookInfo;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IBookInfoService;
import pers.tmw.booksharing.service.IUserService;
import pers.tmw.booksharing.util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by tmw090906 on 2017/6/9.
 */
@RequestMapping("/manage/info/")
@Controller
public class BookManageController {

    @Autowired
    private IBookInfoService iBookInfoService;

    @Autowired
    private IUserService iUserService;


    /**
     * 新增或保存图书接口
     * @param session
     * @param bookInfo
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse bookSave(HttpSession session, BookInfo bookInfo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.saveOrUpdateBook(user,bookInfo);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }


    /**
     * 获取图书详情接口，因后台管理员不用匹配图书交换，所以没有复杂的业务逻辑
     * @param session
     * @param bookId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getBookDetail(HttpSession session,Long bookId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.manageBookDetail(bookId);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }


    /**
     * 根据关键字，分类，查询图书列表
     * @param textSearch
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("get_book_list.do")
    @ResponseBody
    public ServerResponse getBookList(@RequestParam(value = "textSearch",required = false) String textSearch,
                                      @RequestParam(value = "categoryId",required = false)Long categoryId,
                                      @RequestParam(defaultValue = "1")int pageNum,
                                      @RequestParam(defaultValue = "10")int pageSize,
                                      @RequestParam(value = "orderBy",defaultValue = "CREATE_TIME") String orderBy,
                                      HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.getBookList(textSearch, categoryId, pageNum, pageSize, orderBy);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }




    /**
     * 上传图片接口
     * @param session
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse uploadBookImage(HttpSession session, MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path  = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iBookInfoService.upload(file,path,user.getUsername());
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);

            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }

    }

    /**
     * 得到未处理申请列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("advice_list.do")
    @ResponseBody
    public ServerResponse getCommitAdviceList(HttpSession session,
                                        @RequestParam(defaultValue = "1")int pageNum,
                                        @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.getCommitAdviceList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    /**
     * 处理某条申请
     * @param session
     * @param adviceId
     * @param status
     * @return
     */
    @RequestMapping("manage_advice.do")
    @ResponseBody
    public ServerResponse manageAdvice(HttpSession session,Long adviceId,short status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.manageAdvice(user.getUserId(),adviceId,status);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    /**
     * 得到某申请详情
     * @param session
     * @param adviceId
     * @return
     */
    @RequestMapping("advice_detail.do")
    @ResponseBody
    public ServerResponse getAdviceDetail(HttpSession session,Long adviceId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.getAdviceDetail(adviceId);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }


    /**
     * 获取自己处理的Advice列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("self_advice_list.do")
    @ResponseBody
    public ServerResponse getAdviceListBySelf(HttpSession session,
                                              @RequestParam(defaultValue = "1")int pageNum,
                                              @RequestParam(defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登陆");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iBookInfoService.manageGetAdviceList(user.getUserId(),pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }



}
