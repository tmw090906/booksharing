package pers.tmw.booksharing.controller.backen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IUserService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/7.
 * 后台用户相关接口
 */
@RequestMapping("/manage/user/")
@Controller
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    /**
     * 后台管理员登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            User user = response.getDate();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createBySuccessMessage("权限低，无法登陆");
            }
        }
        return response;
    }
}
