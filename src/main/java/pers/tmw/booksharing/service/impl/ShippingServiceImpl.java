package pers.tmw.booksharing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.ShippingMapper;
import pers.tmw.booksharing.pojo.Shipping;
import pers.tmw.booksharing.service.IShippingService;

import java.util.List;
import java.util.Map;

/**
 * Created by tmw090906 on 2017/6/9.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse addShipping(Long userId, Shipping shipping) {
        if(shipping == null){
            return ServerResponse.createByErrorMessage("新建地址失败");
        }
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0 ){
            Map temp = Maps.newHashMap();
            temp.put("shippingId",shipping.getShippingId());
            return ServerResponse.createBySuccess("新建地址成功",temp);
        }else {
            return ServerResponse.createByErrorMessage("新建地址失败");
        }

    }


    @Override
    public ServerResponse deleteShipping(Long userId, Long shippingId) {
        int rowCount = shippingMapper.deleteByUserIdShippingId(userId,shippingId);
        if(rowCount > 0 ){
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }else {
            return ServerResponse.createByErrorMessage("删除地址失败");
        }
    }

    @Override
    public ServerResponse updateShipping(Long userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0 ){
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }else {
            return ServerResponse.createByErrorMessage("更新地址失败");
        }
    }


    @Override
    public ServerResponse<Shipping> selectShippingDetail(Long shippingId) {
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse<PageInfo> getShippingList(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectShippingListByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
























}
