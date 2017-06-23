package pers.tmw.booksharing.service;

import com.github.pagehelper.PageInfo;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.Shipping;

/**
 * Created by tmw090906 on 2017/6/9.
 */
public interface IShippingService {

    ServerResponse addShipping(Long userId, Shipping shipping);

    ServerResponse deleteShipping(Long userId, Long shippingId);

    ServerResponse updateShipping(Long userId,Shipping shipping);

    ServerResponse<Shipping> selectShippingDetail(Long userId,Long shippingId);

    ServerResponse<PageInfo> getShippingList(Long userId, int pageNum, int pageSize);

}
