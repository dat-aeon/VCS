/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqManagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class FaqManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9008363660504873917L;

    private String questionMyan;
    private String questionEng;
    private String answerMyan;
    private String answerEng;
    private Integer categoryId;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Integer faqId;
    private int delFlag;
    private String categoryName;
    private boolean forUpdate;

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
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

    public String getAnswerMyan() {
        return answerMyan;
    }

    public void setAnswerMyan(String answerMyan) {
        this.answerMyan = answerMyan;
    }

    public String getAnswerEng() {
        return answerEng;
    }

    public void setAnswerEng(String answerEng) {
        this.answerEng = answerEng;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public FaqManagementHeaderBean copyFaqManagementHeaderBean(FaqManagementHeaderBean faqManagementHeaderBean) {

        this.answerEng = faqManagementHeaderBean.getAnswerEng();
        this.answerMyan = faqManagementHeaderBean.getAnswerMyan();
        this.questionEng = faqManagementHeaderBean.getQuestionEng();
        this.questionMyan = faqManagementHeaderBean.getQuestionMyan();
        this.categoryId = faqManagementHeaderBean.getCategoryId();
        this.categoryName = faqManagementHeaderBean.getCategoryName();
        this.faqId = faqManagementHeaderBean.getFaqId();
        this.forUpdate = faqManagementHeaderBean.isForUpdate();

        return this;
    }
}
