/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.companyInfoUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CompanyInfo")
public class CompanyInfoUpdateReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = -748406496640180341L;
    private Integer infoId;

    private String address_eng;

    private String address_mya;

    private String hotLine;

    private String webAddress;

    private String socialMedia;

    private String aboutCompanyEng;

    private String aboutCompanyMya;

    private String updatedBy;

    private Timestamp updatedTime;

    private String chatAutoReplyMessage;

    // private boolean isValidStatusToggle;

    @Override
    public DaoType getDaoType() {
        return DaoType.UPDATE;
    }

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

}
