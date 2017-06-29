package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.IllegalMapper;
import pers.tmw.booksharing.dao.UserMapper;
import pers.tmw.booksharing.pojo.Illegal;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IUserService;
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.util.MD5Util;
import pers.tmw.booksharing.vo.IllegalVo;

import java.util.List;

/**
 * Created by tmw090906 on 2017/6/7.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IllegalMapper illegalMapper;

    @Override
    public ServerResponse<User> login(String username,String pwd){
        User user = userMapper.selectByUsername(username);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        if(!user.getPwd().equals(MD5Util.getMD5(pwd))){
            System.out.println(user.getPwd());
            System.out.println(MD5Util.getMD5(pwd));
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPwd(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",user);
    }

    @Override
    public ServerResponse register(User user){
        ServerResponse checkValid = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!checkValid.isSuccess()){
            return checkValid;
        }
        checkValid = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!checkValid.isSuccess()){
            return checkValid;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密密码
        user.setPwd(MD5Util.getMD5(user.getPwd()));
        int resultCount = userMapper.insertSelective(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse checkValid(String str,String type){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse selectQuestion(String username) {
        ServerResponse checkValid = this.checkValid(username,Const.USERNAME);
        if(checkValid.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    @Override
    public ServerResponse checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("问题答案正确");
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public ServerResponse resetPasswordByAnswer(String username,String passwordNew){
        ServerResponse checkValid = this.checkValid(username,Const.USERNAME);
        if(checkValid.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String MD5PasswordNew =  MD5Util.getMD5(passwordNew);
        int rowCount = userMapper.resetPasswordByForget(username,MD5PasswordNew);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("重置密码成功");
        }
        return ServerResponse.createByErrorMessage("重置密码失败");
    }

    @Override
    public ServerResponse resetPassword(User user,String passwordOld,String passwordNew){
        //防止横向越权，校验用户旧密码
        int resultCount = userMapper.checkPassword(MD5Util.getMD5(passwordOld),user.getUserId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码输入错误");
        }
        user.setPwd(MD5Util.getMD5(passwordNew));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("密码修改成功");
        }
        return ServerResponse.createByErrorMessage("未知错误，密码修改失败");
    }

    @Override
    public ServerResponse updateUserInfo(User user){
        //username不能被修改
        //email也要进行校验，校验新的Email是否存在，并且如果相同的话不能是当前这个用户的
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getUserId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已经存在，请更换后再进行更新");
        }
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setAnwser(user.getAnwser());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setQq(user.getQq());
        updateUser.setWechart(user.getWechart());
        updateUser.setIdcard(user.getIdcard());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0 ){
            updateUser = userMapper.selectByPrimaryKey(updateUser.getUserId());
            updateUser.setPwd(org.apache.commons.lang3.StringUtils.EMPTY);
            return ServerResponse.createBySuccess("修改个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("未知错误，修改个人信息失败");

    }

    @Override
    public ServerResponse<User> getInformation(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPwd(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    @Override
    public ServerResponse getIllegalList(Long userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("UPDATE_TIME desc");
        List<Illegal> illegalList = illegalMapper.getIllegalListByUserId(userId);
        List<IllegalVo> illegalVoList = assembleIllegalVoList(illegalList);
        PageInfo pageInfo = new PageInfo(illegalVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<IllegalVo> assembleIllegalVoList(List<Illegal> illegalList){
        List<IllegalVo> illegalVoList = Lists.newArrayList();
        for(Illegal illegalTemp : illegalList){
            IllegalVo illegalVo = new IllegalVo();
            illegalVo.setDeposit(illegalTemp.getDeposit());
            illegalVo.setIllegalId(illegalTemp.getIllegalId());
            illegalVo.setIllegalTime(DateTimeUtil.dateToStr(illegalTemp.getCreateTime()));
            illegalVo.setReplaceId(illegalTemp.getReplaceId());
            illegalVo.setVictimUserName(userMapper.getUserNameByUserId(illegalTemp.getVictimId()));
            illegalVoList.add(illegalVo);
        }
        return illegalVoList;
    }





    //后台
    @Override
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }




}
