/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.appSettingUpdate;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AppConfig")
public class AppSettingUpdateReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 2094966024562276904L;

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

    @Override
    public DaoType getDaoType() {
        return DaoType.UPDATE;
    }

}
