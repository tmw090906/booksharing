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
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.vo.OrderVo;

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
        PageHelper.orderBy("UPDATE_TIME desc");
        List<Order> orderList = orderMapper.getOrderListByUserId(userId);
        if(orderList == null || orderList.size() == 0){
            return ServerResponse.createByErrorMessage("没有充值记录");
        }
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList);
        PageInfo pageInfo = new PageInfo(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orderList){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for(Order orderItem : orderList){
            OrderVo orderVo = new OrderVo();
            orderVo.setNewMoney(orderItem.getNewMoney());
            orderVo.setOldMoney(orderItem.getOldMoney());
            orderVo.setOrderId(orderItem.getOrderId());
            orderVo.setOrderMoney(orderItem.getOrderMoney());
            orderVo.setOrderNumber(orderItem.getOrderNumber());
            orderVo.setPaymentTime(DateTimeUtil.dateToStr(orderItem.getPaymentTime()));
            short status = orderItem.getStatus();
            String statusStr;
            if(status == 1){
                statusStr = "未支付";
            }else if(status == 2){
                statusStr = "已支付";
            }else {
                statusStr = "已取消";
            }
            orderVo.setStatus(statusStr);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    @Override
    public ServerResponse cancelOrderByOrderNo(Long userId,String orderNo){
        System.out.println(111);
        Order order = orderMapper.selectByUserIdOderNo(userId,orderNo);
        System.out.println(111);
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
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList);
        PageInfo pageInfo = new PageInfo(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse manageSearch(String orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        List<OrderVo> orderVoList = this.assembleOrderVoList(Lists.newArrayList(order));
        if(order != null){
            PageInfo pageInfo = new PageInfo(orderVoList);
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }
}
