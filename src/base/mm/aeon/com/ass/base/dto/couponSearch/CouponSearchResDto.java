/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.couponSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CouponSearchResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = 4729324171051406623L;

    private String coupon_name;

    private String coupon_code;

    private double coupon_amount;

    private int discount_percent;

    private int del_flag;

    public int getTotal_no() {
        return total_no;
    }

    public void setTotal_no(int total_no) {
        this.total_no = total_no;
    }

    public int getCupon_no_per_cust() {
        return cupon_no_per_cust;
    }

    public void setCupon_no_per_cust(int cupon_no_per_cust) {
        this.cupon_no_per_cust = cupon_no_per_cust;
    }

    public String getSpecial_event() {
        return special_event;
    }

    public void setSpecial_event(String special_event) {
        this.special_event = special_event;
    }

    private int total_no;

    private int cupon_no_per_cust;

    private String special_event;

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public int getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(int discount_percent) {
        this.discount_percent = discount_percent;
    }

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

    private String store_name;

    private String coupon_desc;

    private Timestamp startDate;

    private Timestamp expireDate;

    public String getCoupon_desc() {
        return coupon_desc;
    }

    public void setCoupon_desc(String coupon_desc) {
        this.coupon_desc = coupon_desc;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }

    public String getUnuse_image_path() {
        return unuse_image_path;
    }

    public void setUnuse_image_path(String unuse_image_path) {
        this.unuse_image_path = unuse_image_path;
    }

    public String getUse_image_path() {
        return use_image_path;
    }

    public void setUse_image_path(String use_image_path) {
        this.use_image_path = use_image_path;
    }

    private String unuse_image_path;

    private String use_image_path;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private int cid;

    private String discount_unit;

    public String getDiscount_unit() {
        return discount_unit;
    }

    public void setDiscount_unit(String discount_unit) {
        this.discount_unit = discount_unit;
    }

    private String couponNameMya;

    private String couponSpEventMya;

    private String couponDescriptionMya;

    public String getCouponNameMya() {
        return couponNameMya;
    }

    public void setCouponNameMya(String couponNameMya) {
        this.couponNameMya = couponNameMya;
    }

    public String getCouponSpEventMya() {
        return couponSpEventMya;
    }

    public void setCouponSpEventMya(String couponSpEventMya) {
        this.couponSpEventMya = couponSpEventMya;
    }

    public String getCouponDescriptionMya() {
        return couponDescriptionMya;
    }

    public void setCouponDescriptionMya(String couponDescriptionMya) {
        this.couponDescriptionMya = couponDescriptionMya;
    }

}
