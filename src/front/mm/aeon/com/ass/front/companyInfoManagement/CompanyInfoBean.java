/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfoManagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class CompanyInfoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -573584367570393877L;

    private Integer infoId;

    private String address_eng;

    private String address_mya;

    private String hotLine;

    private String webAddress;

    private String socialMedia;

    private String aboutCompanyEng;

    private String aboutCompanyMya;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    private boolean isUpdate;

    private String chatAutoReplyMessage;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getAddress_eng() {
        return address_eng;
    }

    public void setAddress_eng(String address_eng) {
        this.address_eng = address_eng;
    }

    public String getAddress_mya() {
        return address_mya;
    }

    public void setAddress_mya(String address_mya) {
        this.address_mya = address_mya;
    }

    public String getAboutCompanyEng() {
        return aboutCompanyEng;
    }

    public void setAboutCompanyEng(String aboutCompanyEng) {
        this.aboutCompanyEng = aboutCompanyEng;
    }

    public String getAboutCompanyMya() {
        return aboutCompanyMya;
    }

    public void setAboutCompanyMya(String aboutCompanyMya) {
        this.aboutCompanyMya = aboutCompanyMya;
    }

    public String getChatAutoReplyMessage() {
        return chatAutoReplyMessage;
    }

    public void setChatAutoReplyMessage(String chatAutoReplyMessage) {
        this.chatAutoReplyMessage = chatAutoReplyMessage;
    }

    public CompanyInfoBean copyCompanyInfoBean(CompanyInfoBean companyInfoBean) {
        this.infoId = companyInfoBean.getInfoId();
        this.address_mya = companyInfoBean.getAddress_mya();
        this.address_eng = companyInfoBean.getAddress_eng();
        this.hotLine = companyInfoBean.getHotLine();
        this.webAddress = companyInfoBean.getWebAddress();
        this.socialMedia = companyInfoBean.getSocialMedia();
        this.aboutCompanyEng = companyInfoBean.getAboutCompanyEng();
        this.aboutCompanyMya = companyInfoBean.getAboutCompanyMya();
        this.chatAutoReplyMessage = companyInfoBean.getChatAutoReplyMessage();

        return this;
    }

}
