/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.appSettingManagement;

import java.io.Serializable;

public class AppSettingManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9008363660504873917L;

    private Integer appSettingId;
    private int noOfsecurityQuestion;
    private int noOfcorrectAnswer;
    private int noOfcharacterAnswer;

    public Integer getAppSettingId() {
        return appSettingId;
    }

    public void setAppSettingId(Integer appSettingId) {
        this.appSettingId = appSettingId;
    }

    public int getNoOfsecurityQuestion() {
        return noOfsecurityQuestion;
    }

    public void setNoOfsecurityQuestion(int noOfsecurityQuestion) {
        this.noOfsecurityQuestion = noOfsecurityQuestion;
    }

    public int getNoOfcorrectAnswer() {
        return noOfcorrectAnswer;
    }

    public void setNoOfcorrectAnswer(int noOfcorrectAnswer) {
        this.noOfcorrectAnswer = noOfcorrectAnswer;
    }

    public int getNoOfcharacterAnswer() {
        return noOfcharacterAnswer;
    }

    public void setNoOfcharacterAnswer(int noOfcharacterAnswer) {
        this.noOfcharacterAnswer = noOfcharacterAnswer;
    }

    public AppSettingManagementHeaderBean copyAppSettingManagementHeaderBean(
            AppSettingManagementHeaderBean appSettingManagementHeaderBean) {

        this.appSettingId = appSettingManagementHeaderBean.getAppSettingId();
        this.noOfcharacterAnswer = appSettingManagementHeaderBean.getNoOfcharacterAnswer();
        this.noOfsecurityQuestion = appSettingManagementHeaderBean.getNoOfsecurityQuestion();

        return this;
    }
}
