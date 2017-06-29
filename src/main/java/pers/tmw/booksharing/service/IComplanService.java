package pers.tmw.booksharing.service;


import pers.tmw.booksharing.common.ServerResponse;

/**
 * Created by tmw090906 on 2017/6/20.
 */
public interface IComplanService {


    ServerResponse complan(Long userId,Long replaceId,String reason);

    ServerResponse getList(Long userId,int pageNum,int pageSize);

    ServerResponse manageGetList(Short status,int pageNum,int pageSize);

    ServerResponse manageComplan(Long managerId,Short status,Long complanId);


}
