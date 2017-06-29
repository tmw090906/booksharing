package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;

/**
 * Created by tmw090906 on 2017/6/20.
 */
public interface IReplaceService {

    ServerResponse getList(Long userId,int pageNum,int pageSize,Short status);

    ServerResponse updateShipping(Long userId,Long shippingId,Long replaceId);

    ServerResponse sendBook(Long userId,Long replaceId,Long deliverNo);

    ServerResponse deliverBook(Long userId,Long replaceId);

    ServerResponse cancelReplace(Long replaceId);


}
