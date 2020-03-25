/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.appSettingUpdateService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AppSettingUpdateServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -2262142641518551235L;

    private int appSettingId;
    private int noOfsecurityQuestion;
    private int noOfcorrectAnswer;
    private int noOfcharacterAnswer;

    public int getAppSettingId() {
        return appSettingId;
    }

    public void setAppSettingId(int appSettingId) {
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

    @Override
    public String getServiceId() {
        return "APPSETTINGINSERT";
    }

}
