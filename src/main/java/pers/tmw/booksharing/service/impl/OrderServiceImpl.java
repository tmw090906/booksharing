package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.OrderMapper;
import pers.tmw.booksharing.pojo.Order;
import pers.tmw.booksharing.service.IOrderService;

import java.util.List;

/**
 * Created by tmw090906 on 2017/6/9.
 */
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ServerResponse getOrderList(Long userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.getOrderListByUserId(userId);
        PageInfo pageInfo = new PageInfo(orderList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse cancelOrderByOrderNo(Long userId,String orderNo){
        Order order = orderMapper.selectByUserIdOderNo(userId,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("找不到该订单");
        }
        if(order.getStatus() != Const.OrderStatus.WAIT_PAY){
            return ServerResponse.createByErrorMessage("订单已经付款或已经取消，无法操作");
        }
        order.setStatus(Const.OrderStatus.CANCEL);
        int rowCount = orderMapper.updateByPrimaryKey(order);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("订单取消成功");
        }
        return ServerResponse.createByErrorMessage("订单取消失败");
    }

    @Override
    public ServerResponse manageList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.getAllOrder();
        PageInfo pageInfo = new PageInfo(orderList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse manageSearch(String orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            PageInfo pageInfo = new PageInfo(Lists.newArrayList(order));
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }
}
