package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;

/**
 * Created by tmw090906 on 2017/6/9.
 */
public interface IOrderService {



    ServerResponse getOrderList(Long userId, int pageNum, int pageSize);

    ServerResponse<String> cancelOrderByOrderNo(Long userId,String orderNo);




    ServerResponse manageList(int pageNum,int pageSize);

    ServerResponse manageSearch(String orderNo,int pageNum,int pageSize);
}
