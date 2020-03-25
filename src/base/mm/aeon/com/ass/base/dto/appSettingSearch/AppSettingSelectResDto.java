/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.appSettingSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AppSettingSelectResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7591551575539498312L;

    private Integer appSettingId;
    private int noOfsecurityQuestion;
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

    public int getNoOfcharacterAnswer() {
        return noOfcharacterAnswer;
    }

    public void setNoOfcharacterAnswer(int noOfcharacterAnswer) {
        this.noOfcharacterAnswer = noOfcharacterAnswer;
    }

}
