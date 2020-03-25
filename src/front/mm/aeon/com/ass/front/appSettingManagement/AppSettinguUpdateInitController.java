/**************************************************************************
 * $Date: 2019-02-04$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.appSettingManagement;

import java.util.List;
import mm.aeon.com.ass.base.dto.appSettingSearch.AppSettingSelectReqDto;
import mm.aeon.com.ass.base.dto.appSettingSearch.AppSettingSelectResDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AppSettinguUpdateInitController extends AbstractController
        implements IControllerAccessor<AppSettingManagementFormBean, AppSettingManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public AppSettingManagementFormBean process(AppSettingManagementFormBean formBean) {

        /*if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[App Setting]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }*/

        applicationLogger.log("App Setting Process started.", LogLevel.INFO);

        try {

            AppSettingSelectReqDto reqDto = new AppSettingSelectReqDto();
            List<AppSettingSelectResDto> resList =
                    (List<AppSettingSelectResDto>) this.getDaoServiceInvoker().execute(reqDto);

            for (AppSettingSelectResDto appList : resList) {
                formBean.getAppHearderBean().setAppSettingId(appList.getAppSettingId());
                formBean.getAppHearderBean().setNoOfcharacterAnswer(appList.getNoOfcharacterAnswer());
                formBean.getAppHearderBean().setNoOfsecurityQuestion(appList.getNoOfsecurityQuestion());
            }

            applicationLogger.log("App Setting searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
