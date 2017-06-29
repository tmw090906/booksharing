package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;

/**
 * Created by tmw090906 on 2017/6/7.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String pwd);

    ServerResponse register(User user);

    ServerResponse checkValid(String str,String type);

    ServerResponse selectQuestion(String username);

    ServerResponse checkAnswer(String username,String question,String answer);

    ServerResponse resetPasswordByAnswer(String username,String passwordNew);

    ServerResponse resetPassword(User user,String passwordOld,String passwordNew);

    ServerResponse updateUserInfo(User user);

    ServerResponse<User> getInformation(Long userId);

    ServerResponse checkAdminRole(User user);

    ServerResponse getIllegalList(Long userId,int pageNum,int pageSize);

}
