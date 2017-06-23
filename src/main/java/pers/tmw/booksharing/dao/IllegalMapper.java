package pers.tmw.booksharing.dao;

import pers.tmw.booksharing.pojo.Illegal;

import java.util.List;

public interface IllegalMapper {
    int deleteByPrimaryKey(Long illegalId);

    int insert(Illegal record);

    int insertSelective(Illegal record);

    Illegal selectByPrimaryKey(Long illegalId);

    int updateByPrimaryKeySelective(Illegal record);

    int updateByPrimaryKey(Illegal record);

    List<Illegal> getIllegalListByUserId(Long userId);
}