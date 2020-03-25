/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.io.Serializable;
import java.util.List;

import mm.aeon.com.ass.front.storeList.StoreListLineBean;

public class CouponListHeaderBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -890874397342997938L;
    
    private String coupon_name;

    private String coupon_code;
    
    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public double getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(double coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    private double coupon_amount;
    
    private String amount;
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String store_name;
    
    private List<StoreListLineBean> lineBeans;

    public List<StoreListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<StoreListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    private int coupon_id;

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }
}
