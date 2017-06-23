package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.Shipping;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Long shippingId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Long shippingId);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByUserIdShippingId(@Param("userId") Long userId, @Param("shippingId")Long shippingId);

    int updateByShipping(Shipping shipping);

    Shipping selectByUserIdShippingId(@Param("userId") Long userId, @Param("shippingId")Long shippingId);

    List<Shipping> selectShippingListByUserId(Long userId);
}