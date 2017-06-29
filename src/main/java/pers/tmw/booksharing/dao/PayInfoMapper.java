package pers.tmw.booksharing.dao;

import pers.tmw.booksharing.pojo.PayInfo;

import java.util.List;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Long payId);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Long payId);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

}