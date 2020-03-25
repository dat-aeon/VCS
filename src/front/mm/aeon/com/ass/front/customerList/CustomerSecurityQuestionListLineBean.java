/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerList;

import java.io.Serializable;

public class CustomerSecurityQuestionListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4219447223755165910L;
    private Integer custSecQuesId;
    private Integer secQuesId;
    private String questionMyan;
    private String questionEng;

    public Integer getCustSecQuesId() {
        return custSecQuesId;
    }

    public void setCustSecQuesId(Integer custSecQuesId) {
        this.custSecQuesId = custSecQuesId;
    }

    public Integer getSecQuesId() {
        return secQuesId;
    }

    public void setSecQuesId(Integer secQuesId) {
        this.secQuesId = secQuesId;
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
}
