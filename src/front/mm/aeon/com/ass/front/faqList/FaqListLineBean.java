/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqList;

import java.io.Serializable;
import java.sql.Timestamp;

public class FaqListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1373078522115084916L;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

}
