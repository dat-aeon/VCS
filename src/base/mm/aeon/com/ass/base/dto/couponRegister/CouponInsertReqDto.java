/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.couponRegister;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CouponInfo")
public class CouponInsertReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 9161815948635900871L;

    private Integer coupon_id;

    private String coupon_name;

    private String coupon_code;

    private String coupon_desc;

    private double coupon_amount;

    private Timestamp startDate;

    private Timestamp expireDate;

    private int discount_percent;

    private String unuse_image_path;

    private String use_image_path;

    private int del_flag;

    private String created_by;

    private int couponTlNo;

    private String discount_unit;

    private String couponSpEventMya;

    private String couponDescriptionMya;

    private String couponNameMya;

    private int couponNoOfCus;

    private String couponSpEvent;

    private String updated_by;

    private Timestamp created_time;

    private Timestamp updated_time;

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
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

    public String getCouponNameMya() {
        return couponNameMya;
    }

    public void setCouponNameMya(String couponNameMya) {
        this.couponNameMya = couponNameMya;
    }

    public String getDiscount_unit() {
        return discount_unit;
    }

    public void setDiscount_unit(String discount_unit) {
        this.discount_unit = discount_unit;
    }

    public int getCouponTlNo() {
        return couponTlNo;
    }

    public void setCouponTlNo(int couponTlNo) {
        this.couponTlNo = couponTlNo;
    }

    public int getCouponNoOfCus() {
        return couponNoOfCus;
    }

    public void setCouponNoOfCus(int couponNoOfCus) {
        this.couponNoOfCus = couponNoOfCus;
    }

    public String getCouponSpEvent() {
        return couponSpEvent;
    }

    public void setCouponSpEvent(String couponSpEvent) {
        this.couponSpEvent = couponSpEvent;
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

    public String getCoupon_desc() {
        return coupon_desc;
    }

    public void setCoupon_desc(String coupon_desc) {
        this.coupon_desc = coupon_desc;
    }

    public double getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(double coupon_amount) {
        this.coupon_amount = coupon_amount;
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

    public int getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(int discount_percent) {
        this.discount_percent = discount_percent;
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

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Timestamp getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }

    public Timestamp getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Timestamp updated_time) {
        this.updated_time = updated_time;
    }

    @Override
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.INSERT;
    }

}
