/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.UploadedFile;

public class CouponListLineBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8161874823546091547L;

    private Integer coupon_id;

    private String couponName;

    private String storeName;

    private Date couponExpDate;

    private UploadedFile importFile;

    private int couponNoOfCus;

    private int status;

    private String unuse_image_path;

    private String use_image_path;

    private String uploadedImageFilePath;

    private int cupon_no_per_cust;

    private String special_event;

    private String couponNameMya;

    private String couponSpEventMya;

    private String couponDescriptionMya;

    private Integer cid;

    private int validStatus;

    private String coupon_code;

    private double coupon_amount;

    private int coupon_discount;

    private String couponDescription;

    private Date couponStrDate;

    private String couponSpEvent;

    private int couponTlNo;

    private UploadedFile couponImage;

    private String discount_unit;

    private int total_no;

    public Date getCouponExpDate() {
        return couponExpDate;
    }

    public void setCouponExpDate(Date couponExpDate) {
        this.couponExpDate = couponExpDate;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public int getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(int coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public Date getCouponStrDate() {
        return couponStrDate;
    }

    public void setCouponStrDate(Date couponStrDate) {
        this.couponStrDate = couponStrDate;
    }

    public String getCouponSpEvent() {
        return couponSpEvent;
    }

    public void setCouponSpEvent(String couponSpEvent) {
        this.couponSpEvent = couponSpEvent;
    }

    public int getCouponTlNo() {
        return couponTlNo;
    }

    public void setCouponTlNo(int couponTlNo) {
        this.couponTlNo = couponTlNo;
    }

    public UploadedFile getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(UploadedFile couponImage) {
        this.couponImage = couponImage;
    }

    public UploadedFile getImportFile() {
        return importFile;
    }

    public void setImportFile(UploadedFile importFile) {
        this.importFile = importFile;
    }

    public int getCouponNoOfCus() {
        return couponNoOfCus;
    }

    public void setCouponNoOfCus(int couponNoOfCus) {
        this.couponNoOfCus = couponNoOfCus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getUploadedImageFilePath() {
        return uploadedImageFilePath;
    }

    public void setUploadedImageFilePath(String uploadedImageFilePath) {
        this.uploadedImageFilePath = uploadedImageFilePath;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getDiscount_unit() {
        return discount_unit;
    }

    public void setDiscount_unit(String discount_unit) {
        this.discount_unit = discount_unit;
    }

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
