package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;

import java.util.Map;

/**
 * Created by tmw090906 on 2017/6/9.
 */
public interface IDepositService {

    ServerResponse pay(User user, String path, double money);

    boolean checkedOrder(Map<String,String> params);

    ServerResponse aliCallBack(Map<String,String> params);

    ServerResponse<Boolean> queryOrderPayStatus(Long userId,String oderNo);

}
