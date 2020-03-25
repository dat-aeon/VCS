/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoManagement;

import java.io.Serializable;
import java.util.Date;

public class NewsInfoManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8648009809984173159L;

    private Integer newsInfoId;
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
    private String newsUrl;

    public NewsInfoManagementHeaderBean copyNewsInfoManagementHeaderBean(
            NewsInfoManagementHeaderBean newsInfoManagementHeaderBean) {

        this.newsInfoId = newsInfoManagementHeaderBean.getNewsInfoId();
        this.titleEng = newsInfoManagementHeaderBean.getTitleEng();
        this.titleMyn = newsInfoManagementHeaderBean.getTitleMyn();
        this.contentEng = newsInfoManagementHeaderBean.getContentEng();
        this.contentMyn = newsInfoManagementHeaderBean.getContentMyn();
        this.publishedFromDate = newsInfoManagementHeaderBean.getPublishedFromDate();
        this.publishedToDate = newsInfoManagementHeaderBean.getPublishedToDate();
        this.imagePath = newsInfoManagementHeaderBean.getImagePath();
        this.longitude = newsInfoManagementHeaderBean.getLongitude();
        this.latitude = newsInfoManagementHeaderBean.getLatitude();
        this.uploadedImageFilePath = newsInfoManagementHeaderBean.getUploadedImageFilePath();
        this.newsUrl = newsInfoManagementHeaderBean.getNewsUrl();

        return this;
    }

    public Integer getNewsInfoId() {
        return newsInfoId;
    }

    public void setNewsInfoId(Integer newsInfoId) {
        this.newsInfoId = newsInfoId;
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

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

}
