/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.newsInfoUpdate;

import java.sql.Timestamp;
import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "NewsInfo")
public class NewsInfoUpdateReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 8931616799343470002L;
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
    private String createdBy;
    private Timestamp createdTime;
    private String updatedBy;
    private Timestamp updatedTime;
    private String newsUrl;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.UPDATE;
    }

}
