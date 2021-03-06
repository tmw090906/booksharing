package pers.tmw.booksharing.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.dao.OrderMapper;
import pers.tmw.booksharing.dao.PayInfoMapper;
import pers.tmw.booksharing.dao.UserMapper;
import pers.tmw.booksharing.pojo.Order;
import pers.tmw.booksharing.pojo.PayInfo;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.IDepositService;
import pers.tmw.booksharing.util.BigDecimalUtil;
import pers.tmw.booksharing.util.DateTimeUtil;
import pers.tmw.booksharing.util.FTPUtil;
import pers.tmw.booksharing.util.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by tmw090906 on 2017/6/9.
 */
@Service("iDepositService")
public class DepositServiceImpl implements IDepositService {

    private static AlipayTradeService tradeService;

    private static final Logger log = LoggerFactory.getLogger(DepositServiceImpl.class);

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

    }


    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private UserMapper userMapper;


    //支付相关业务逻辑
    @Override
    public ServerResponse pay(User user, String path, double money){
        Map<String,String> resultMap = Maps.newHashMap();

        Order order = new Order();
        order.setOrderMoney(new BigDecimal(money + ""));
        order.setUserId(user.getUserId());
        order.setOrderNumber(this.createOrderNo());
        order.setStatus(Const.OrderStatus.WAIT_PAY);


        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNumber().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("booksharing扫码支付，订单号").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getOrderMoney().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("为帐号").append(user.getUserId()).append("充值").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = Lists.newArrayList();


//         创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods = GoodsDetail.newInstance("goods_id001", "缴纳保证金", BigDecimalUtil.mul(new Double(totalAmount),100.0).longValue() , 1);
//         创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                int rowCount = orderMapper.insertSelective(order);
                if(rowCount == 0){
                    return ServerResponse.createByErrorMessage("创建订单失败");
                }
                resultMap.put("orderNo",String.valueOf(order.getOrderNumber()));

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                String qrPath = String.format(path+"/qr-%s.png", response.getOutTradeNo());
                String qrPathName = String.format("qr-%s.png",response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path,qrPathName);

                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    log.error("上传二维码异常",e);
                }
                log.info("qrPath:" + qrPath);
                //                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);


                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                resultMap.put("qrUrl",qrUrl);
                return ServerResponse.createBySuccess(resultMap);

            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }

    }

    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    private String createOrderNo(){
        return new StringBuilder().append(System.currentTimeMillis()).append(new Random().nextInt(10)).toString();
    }


    @Override
    public boolean checkedOrder(Map<String,String> params){
        String orderNo = String.valueOf(params.get("out_trade_no"));

        Order order = orderMapper.selectByOrderNo(orderNo);

        if(order == null){
            return false;
        }
        BigDecimal orderTotalPrice = new BigDecimal(params.get("total_amount"));
        if(orderTotalPrice.compareTo(order.getOrderMoney()) == 0){
            return true;
        }
        return false;
    }

    @Override
    public ServerResponse aliCallBack(Map<String,String> params){
        String orderNo = String.valueOf(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");

        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("非booksharing订单，回调忽略");
        }
        if(order.getStatus() >= Const.OrderStatus.PAID){
            return ServerResponse.createBySuccess("支付宝重复调用",order);
        }
        if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            User user = userMapper.selectByPrimaryKey(order.getUserId());
            //设置订单中的账户原有余额和新余额
            order.setOldMoney(user.getDeposit());
            order.setNewMoney(BigDecimalUtil.add(user.getDeposit().doubleValue(),order.getOrderMoney().doubleValue()));
            //设置账户新保证金余额
            user.setDeposit(order.getNewMoney());
            user.setApproveDeposit(BigDecimalUtil.add(user.getApproveDeposit().doubleValue(),order.getOrderMoney().doubleValue()));
            //更新账户信息
            userMapper.updateByPrimaryKey(user);

            order.setStatus(Const.OrderStatus.PAID);
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            orderMapper.updateByPrimaryKeySelective(order);
        }
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(new BigDecimal(orderNo));
        payInfo.setPlatForm((short)Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setUserId(order.getUserId());
        payInfo.setTradeStatus(tradeStatus);
        payInfo.setTradeNo(tradeNo);

        payInfoMapper.insert(payInfo);

        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<Boolean> queryOrderPayStatus(Long userId,String oderNo){
        Order order = orderMapper.selectByUserIdOderNo(userId, oderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if(order.getStatus() == Const.OrderStatus.PAID){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


}
