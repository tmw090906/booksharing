package pers.tmw.booksharing.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by tmw090906 on 2017/6/7.
 */
public class Const {

    public static final String CURRENT_USER = "CURRENT_USER";

    public static final String EMAIL= "EMAIL";

    public static final String USERNAME= "USERNAME";

    public interface Role{
        short ROLE_CUSTOMER = 0; //普通用户
        short ROLE_ADMIN = 1; //管理员
    }


    public interface BookListOrders{
        Set<String> ORDERS = Sets.newHashSet("CREATE_TIME","PUBLISH_TIME");
    }

    public interface AdviceStatus{
        short COMMIT = 1;
        short WORKING = 2;
        short NOT_FIND = 3;
        short SUCCESS = 4;
    }

    public interface OrderStatus{
        short WAIT_PAY = 1;
        short PAID = 2;
        short CANCEL = 3;
    }

    public interface  AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }


    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public interface SelfLibraryStatus{
        Set<Short> STATUS = Sets.newHashSet((short)1,(short)2,(short)3,(short)4,(short)5);
    }

    public interface ApplyStatus{
        Set<Short> STATUS = Sets.newHashSet((short)1,(short)2,(short)3);
        Short SEND = (short)1;
        Short ACCEPT = (short)2;
        Short REFUSED = (short)3;
    }

    public interface ReplaceStatus{
        Short WAIT_MANAGE = (short)1;
        Short SUCCESS = (short)2;
        Short USER_COMPLAN = (short)3;
        Short SOLVE_COMPLAN_ = (short)4;
        Short CANCEL = (short)5;
        interface SendStatus{
            Short WAIT_SEND = (short)1;
            Short SEND = (short)2;
            Short DELIVER = (short)3;
        }
    }
    public interface ComplanStatus{
        Short WAIT_MANAGE = (short)1;
        Short COMPLAN_SUCCESS = (short)2;
        Short COMPLAN_FAIL = (short)3;
    }




}
