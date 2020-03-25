/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.customerUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.customerSelectForUpdate.CustomerSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.customerSelectForUpdate.CustomerSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.customerUpdate.CustomerUpdateReqDto;
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

public class CustomerUpdateService extends AbstractService
        implements IService<CustomerUpdateServiceReqBean, CustomerUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CustomerUpdateServiceResBean execute(CustomerUpdateServiceReqBean reqBean) {

        CustomerUpdateServiceResBean resBean = new CustomerUpdateServiceResBean();

        CustomerSelectForUpdateReqDto selectForUpdateReqDto = new CustomerSelectForUpdateReqDto();
        selectForUpdateReqDto.setCustomerId(reqBean.getCustomerId());

        try {
            CustomerSelectForUpdateResDto selectForUpdateResDto =
                    (CustomerSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (selectForUpdateResDto.getUpdatedTime() != null
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {

                CustomerUpdateReqDto reqDto = new CustomerUpdateReqDto();

                reqDto.setCompanyName(reqBean.getCompanyName());
                reqDto.setCustomerId(reqBean.getCustomerId());
                reqDto.setCustomerNo(reqBean.getCustomerNo());
                reqDto.setCustomerTypeId(reqBean.getCustomerTypeId());
                reqDto.setDelFlag(reqBean.getDelFlag());
                reqDto.setDob(reqBean.getDob());
                reqDto.setGender(reqBean.getGender());
                reqDto.setJoinDate(reqBean.getJoinDate());
                reqDto.setName(reqBean.getName());
                reqDto.setNrcNo(reqBean.getNrcNo());
                reqDto.setPhoneNo(reqBean.getPhoneNo());
                reqDto.setSalary(reqBean.getSalary());
                reqDto.setTownship(reqBean.getTownship());
                reqDto.setUpdatedBy(reqBean.getUpdatedBy());
                reqDto.setApplockFlag(reqBean.getApplockFlag());
                reqDto.setCompanyName(reqBean.getCompanyName());

                // reqDto.setUpdatedBy(CommonUtil.getLoginUserName());
                reqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                this.getDaoServiceInvoker().execute(reqDto);
                resBean.setServiceStatus(ServiceStatusCode.OK);
                System.out.println();
            }

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
