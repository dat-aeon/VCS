/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.passwordInfoUpdateService;

import mm.aeon.com.ass.base.dto.passwordInfoUpdate.PasswordInfoUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class PasswordInfoUpdateService extends AbstractService
        implements IService<PasswordInfoUpdateServiceReqBean, PasswordInfoUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public PasswordInfoUpdateServiceResBean execute(PasswordInfoUpdateServiceReqBean reqBean) {

        PasswordInfoUpdateServiceResBean resBean = new PasswordInfoUpdateServiceResBean();

        try {

            PasswordInfoUpdateReqDto pwUpdateReqDto = new PasswordInfoUpdateReqDto();

            pwUpdateReqDto.setPassword(reqBean.getPassword());
            pwUpdateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
            pwUpdateReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
            pwUpdateReqDto.setUserId(reqBean.getUserId());
            pwUpdateReqDto.setUserTypeId(reqBean.getUserTypeId());
            pwUpdateReqDto.setUpdatedTime(reqBean.getUpdatedTime());

            this.getDaoServiceInvoker().execute(pwUpdateReqDto);

            resBean.setServiceStatus(ServiceStatusCode.OK);

        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
                resBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
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
