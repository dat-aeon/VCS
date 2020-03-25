/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoManagement;

import java.io.Serializable;
import java.util.Date;

public class PromotionsInfoManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4515048905656956761L;

    private int promotionsInfoId;
    private String titleEng;
    private String titleMyn;
    private String contentEng;
    private String contentMyn;
    private Date publishedFromDate;
    private Date publishedToDate;
    private String imagePath;
    private String longitude;
    private String latitude;
    private String uploadedImageFilePath;
    private String announcementUrl;

    public PromotionsInfoManagementHeaderBean copyPromotionsInfoManagementHeaderBean(
            PromotionsInfoManagementHeaderBean promotionsInfoManagementHeaderBean) {

        this.promotionsInfoId = promotionsInfoManagementHeaderBean.getPromotionsInfoId();
        this.titleEng = promotionsInfoManagementHeaderBean.getTitleEng();
        this.titleMyn = promotionsInfoManagementHeaderBean.getTitleMyn();
        this.contentEng = promotionsInfoManagementHeaderBean.getContentEng();
        this.contentMyn = promotionsInfoManagementHeaderBean.getContentMyn();
        this.publishedFromDate = promotionsInfoManagementHeaderBean.getPublishedFromDate();
        this.publishedToDate = promotionsInfoManagementHeaderBean.getPublishedToDate();
        this.imagePath = promotionsInfoManagementHeaderBean.getImagePath();
        this.longitude = promotionsInfoManagementHeaderBean.getLongitude();
        this.latitude = promotionsInfoManagementHeaderBean.getLatitude();
        this.uploadedImageFilePath = promotionsInfoManagementHeaderBean.getUploadedImageFilePath();
        this.announcementUrl = promotionsInfoManagementHeaderBean.getAnnouncementUrl();

        return this;
    }

    public String getAnnouncementUrl() {
        return announcementUrl;
    }

    public void setAnnouncementUrl(String announcementUrl) {
        this.announcementUrl = announcementUrl;
    }

    public int getPromotionsInfoId() {
        return promotionsInfoId;
    }

    public void setPromotionsInfoId(int promotionsInfoId) {
        this.promotionsInfoId = promotionsInfoId;
    }

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getTitleMyn() {
        return titleMyn;
    }

    public void setTitleMyn(String titleMyn) {
        this.titleMyn = titleMyn;
    }

    public String getContentEng() {
        return contentEng;
    }

    public void setContentEng(String contentEng) {
        this.contentEng = contentEng;
    }

    public String getContentMyn() {
        return contentMyn;
    }

    public void setContentMyn(String contentMyn) {
        this.contentMyn = contentMyn;
    }

    public Date getPublishedFromDate() {
        return publishedFromDate;
    }

    public void setPublishedFromDate(Date publishedFromDate) {
        this.publishedFromDate = publishedFromDate;
    }

    public Date getPublishedToDate() {
        return publishedToDate;
    }

    public void setPublishedToDate(Date publishedToDate) {
        this.publishedToDate = publishedToDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUploadedImageFilePath() {
        return uploadedImageFilePath;
    }

    public void setUploadedImageFilePath(String uploadedImageFilePath) {
        this.uploadedImageFilePath = uploadedImageFilePath;
    }

}
