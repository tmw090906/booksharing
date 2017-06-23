package pers.tmw.booksharing.util;

import java.math.BigDecimal;

/**
 * Created by tmw090906 on 2017/6/9.
 */
public class BigDecimalUtil {

    public static BigDecimal add(double v1, double v2){
        BigDecimal x1 = new BigDecimal(Double.toString(v1));
        BigDecimal x2 = new BigDecimal(Double.toString(v2));
        return x1.add(x2);
    }
    public static BigDecimal sub(double v1, double v2){
        BigDecimal x1 = new BigDecimal(Double.toString(v1));
        BigDecimal x2 = new BigDecimal(Double.toString(v2));
        return x1.subtract(x2);
    }
    public static BigDecimal mul(double v1, double v2){
        BigDecimal x1 = new BigDecimal(Double.toString(v1));
        BigDecimal x2 = new BigDecimal(Double.toString(v2));
        return x1.multiply(x2);
    }
    public static BigDecimal div(double v1, double v2){
        BigDecimal x1 = new BigDecimal(Double.toString(v1));
        BigDecimal x2 = new BigDecimal(Double.toString(v2));
        return x1.divide(x2);
    }
}
