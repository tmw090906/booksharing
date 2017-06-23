package pers.tmw.booksharing.dao;

import pers.tmw.booksharing.pojo.Advice;

import java.util.List;

public interface AdviceMapper {
    int deleteByPrimaryKey(Long adviceId);

    int insert(Advice record);

    int insertSelective(Advice record);

    Advice selectByPrimaryKey(Long adviceId);

    int updateByPrimaryKeySelective(Advice record);

    int updateByPrimaryKey(Advice record);

    List<Advice> selectList();

    List<Advice> selectListByManagerId(Long userId);

    List<Advice> selectListByUserId(Long userId);
}