/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.securityManagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class SecurityManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4998619738834744521L;

    private Integer secId;

    private String questionMyan;

    private String questionEng;

    private int delFlag;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    private boolean isUpdate;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getSecId() {
        return secId;
    }

    public void setSecId(Integer secId) {
        this.secId = secId;
    }

    public String getQuestionMyan() {
        return questionMyan;
    }

    public void setQuestionMyan(String questionMyan) {
        this.questionMyan = questionMyan;
    }

    public String getQuestionEng() {
        return questionEng;
    }

    public void setQuestionEng(String questionEng) {
        this.questionEng = questionEng;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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

    public SecurityManagementHeaderBean copySecurityManagementHeaderBean(
            SecurityManagementHeaderBean securityManagementHeaderBean) {

        this.questionEng = securityManagementHeaderBean.getQuestionEng();
        this.questionMyan = securityManagementHeaderBean.getQuestionMyan();
        this.delFlag = securityManagementHeaderBean.getDelFlag();
        this.createdBy = securityManagementHeaderBean.getCreatedBy();
        this.createdTime = securityManagementHeaderBean.getCreatedTime();
        this.updatedBy = securityManagementHeaderBean.getUpdatedBy();
        this.updatedTime = securityManagementHeaderBean.getUpdatedTime();

        return this;
    }
}
