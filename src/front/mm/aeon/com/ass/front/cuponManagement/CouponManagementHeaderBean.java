/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import mm.aeon.com.ass.front.storeList.StoreListLineBean;

public class CouponManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4714833881992989588L;

    private String couponName;

    private String couponNameMya;

    private String couponDescription;

    private String couponDescriptionMya;

    private Date couponStrDate;

    private Date couponExpDate;

    private int couponDiscount;

    private double couponAmount;

    private String couponSpEvent;

    private String couponSpEventMya;

    private int couponTlNo;

    private int couponNoOfCus = 1;

    private int del_flag;

    private UploadedFile couponImage;

    private UploadedFile importFile;

    private String storeName;

    private int status;

    private String uploadedImageFilePath;

    private String discount_unit = "Kyat";

    private List<StoreListLineBean> lineBeans;

    private Integer cid;

    private String coupon_code;

    private ArrayList<SelectItem> itemList;

    private List<StoreCouponDataBean> originStoreCouponBranchList;

    private List<StoreCouponDataBean> originShopBranchList;

    private List<StoreCouponDataBean> shopBranchList;

    public CouponManagementHeaderBean copyCouponManagementHeaderBean(
            CouponManagementHeaderBean couponManagementHeaderBean) {

        this.coupon_code = couponManagementHeaderBean.getCoupon_code();
        this.couponName = couponManagementHeaderBean.getCouponName();
        this.couponNameMya = couponManagementHeaderBean.getCouponNameMya();
        this.couponDescription = couponManagementHeaderBean.getCouponDescription();
        this.couponDescriptionMya = couponManagementHeaderBean.getCouponDescriptionMya();
        this.storeName = couponManagementHeaderBean.getStoreName();
        this.couponStrDate = couponManagementHeaderBean.getCouponStrDate();
        this.couponExpDate = couponManagementHeaderBean.getCouponExpDate();
        this.couponDiscount = couponManagementHeaderBean.getCouponDiscount();
        this.couponAmount = couponManagementHeaderBean.getCouponAmount();
        this.couponNoOfCus = couponManagementHeaderBean.getCouponNoOfCus();
        this.couponSpEvent = couponManagementHeaderBean.getCouponSpEvent();
        this.couponSpEventMya = couponManagementHeaderBean.getCouponSpEventMya();
        this.couponTlNo = couponManagementHeaderBean.getCouponTlNo();
        this.couponImage = couponManagementHeaderBean.getCouponImage();
        this.cid = couponManagementHeaderBean.getCid();
        this.discount_unit = couponManagementHeaderBean.getDiscount_unit();
        this.uploadedImageFilePath = couponManagementHeaderBean.getUploadedImageFilePath();
        this.originStoreCouponBranchList = couponManagementHeaderBean.getOriginStoreCouponBranchList();
        this.originShopBranchList = couponManagementHeaderBean.getOriginShopBranchList();

        return this;
    }

    public ArrayList<SelectItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<SelectItem> itemList) {
        this.itemList = itemList;
    }

    public List<StoreListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<StoreListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getDiscount_unit() {
        return discount_unit;
    }

    public void setDiscount_unit(String discount_unit) {
        this.discount_unit = discount_unit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUploadedImageFilePath() {
        return uploadedImageFilePath;
    }

    public void setUploadedImageFilePath(String uploadedImageFilePath) {
        this.uploadedImageFilePath = uploadedImageFilePath;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getCouponNameMya() {
        return couponNameMya;
    }

    public void setCouponNameMya(String couponNameMya) {
        this.couponNameMya = couponNameMya;
    }

    public String getCouponDescriptionMya() {
        return couponDescriptionMya;
    }

    public void setCouponDescriptionMya(String couponDescriptionMya) {
        this.couponDescriptionMya = couponDescriptionMya;
    }

    public String getCouponSpEventMya() {
        return couponSpEventMya;
    }

    public void setCouponSpEventMya(String couponSpEventMya) {
        this.couponSpEventMya = couponSpEventMya;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    public Date getCouponExpDate() {
        return couponExpDate;
    }

    public void setCouponExpDate(Date couponExpDate) {
        this.couponExpDate = couponExpDate;
    }

    public int getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(int couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
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

    public int getCouponNoOfCus() {
        return couponNoOfCus;
    }

    public void setCouponNoOfCus(int couponNoOfCus) {
        this.couponNoOfCus = couponNoOfCus;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<StoreCouponDataBean> getOriginShopBranchList() {
        return originShopBranchList;
    }

    public void setOriginShopBranchList(List<StoreCouponDataBean> originShopBranchList) {
        this.originShopBranchList = originShopBranchList;
    }

    public List<StoreCouponDataBean> getShopBranchList() {
        return shopBranchList;
    }

    public void setShopBranchList(List<StoreCouponDataBean> shopBranchList) {
        this.shopBranchList = shopBranchList;
    }

    public List<StoreCouponDataBean> getOriginStoreCouponBranchList() {
        return originStoreCouponBranchList;
    }

    public void setOriginStoreCouponBranchList(List<StoreCouponDataBean> originStoreCouponBranchList) {
        this.originStoreCouponBranchList = originStoreCouponBranchList;
    }

}
