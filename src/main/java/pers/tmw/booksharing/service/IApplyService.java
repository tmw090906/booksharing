package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;

/**
 * Created by tmw090906 on 2017/6/19.
 */
public interface IApplyService {

    ServerResponse commitApply(Long exchangeUserId,Long userId,Long exchangeBookId,Long bookId);

    ServerResponse getAppliedList(Long userId,int pageNum,int pageSize);

    ServerResponse getApplyList(Long userId,int pageNum,int pageSize);

    ServerResponse manageApply(Long userId,Long applyId,Short status,String reason);
}
