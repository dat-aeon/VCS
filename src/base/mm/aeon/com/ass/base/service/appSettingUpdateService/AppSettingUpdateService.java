/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.appSettingUpdateService;

import mm.aeon.com.ass.base.dto.appSettingUpdate.AppSettingUpdateReqDto;
import mm.aeon.com.ass.base.dto.faqRegister.FaqInsertReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class AppSettingUpdateService extends AbstractService
        implements IService<AppSettingUpdateServiceReqBean, AppSettingUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AppSettingUpdateServiceResBean execute(AppSettingUpdateServiceReqBean reqBean) {

        AppSettingUpdateReqDto reqDto = new AppSettingUpdateReqDto();
        AppSettingUpdateServiceResBean resBean = new AppSettingUpdateServiceResBean();

        reqDto.setAppSettingId(reqBean.getAppSettingId());
        reqDto.setNoOfcharacterAnswer(reqBean.getNoOfcharacterAnswer());
        reqDto.setNoOfsecurityQuestion(reqBean.getNoOfsecurityQuestion());
        

        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                resBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return resBean;
    }

}
