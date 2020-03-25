/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfo;

import java.io.Serializable;
import java.sql.Timestamp;

public class CompanyInfoLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1373078522115084916L;

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

}
