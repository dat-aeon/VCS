/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.customerResetPasswordService;

import mm.aeon.com.ass.base.dto.passwordInfoUpdate.PasswordInfoUpdateReqDto;
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

public class CustomerResetPasswordService extends AbstractService
        implements IService<CustomerResetPasswordServiceReqBean, CustomerResetPasswordServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CustomerResetPasswordServiceResBean execute(CustomerResetPasswordServiceReqBean reqBean) {

        CustomerResetPasswordServiceResBean resBean = new CustomerResetPasswordServiceResBean();

        PasswordInfoUpdateReqDto pwUpdateReqDto = new PasswordInfoUpdateReqDto();

        try {
            pwUpdateReqDto.setPassword(reqBean.getPassword());
            pwUpdateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
            pwUpdateReqDto.setUserId(reqBean.getUserId());
            pwUpdateReqDto.setUserTypeId(reqBean.getUserTypeId());

            this.getDaoServiceInvoker().execute(pwUpdateReqDto);

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
        resBean.setServiceStatus(ServiceStatusCode.OK);
        return resBean;
    }

}
