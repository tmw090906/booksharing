package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;
import pers.tmw.booksharing.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNo(String orderNo);

    Order selectByUserIdOderNo(@Param("userId") Long userId, @Param("orderNo") String orderNo);

    List<Order> getOrderListByUserId(@Param("userId") Long userId);

    List<Order> getAllOrder();
}