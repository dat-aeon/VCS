/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.faqRegister;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "FaqInfo")
public class FaqInsertReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 2094966024562276904L;

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

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
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

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.INSERT;
    }

}
