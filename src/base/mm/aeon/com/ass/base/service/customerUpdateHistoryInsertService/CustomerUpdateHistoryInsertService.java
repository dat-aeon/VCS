/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.customerUpdateHistoryInsertService;

import mm.aeon.com.ass.base.dto.customerUpdateHistoryInsert.CustomerInfoUpdateHistoryInsertReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerUpdateHistoryInsertService extends AbstractService
        implements IService<CustomerUpdateHistoryInsertServiceReqBean, CustomerUpdateHistoryInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CustomerUpdateHistoryInsertServiceResBean execute(
            CustomerUpdateHistoryInsertServiceReqBean customerUpdateHistoryInsertServiceReqBean) {

        CustomerInfoUpdateHistoryInsertReqDto customerInfoUpdateHistoryInsertReqDto =
                new CustomerInfoUpdateHistoryInsertReqDto();

        CustomerUpdateHistoryInsertServiceResBean CustomerUpdateHistoryInsertServiceResBean =
                new CustomerUpdateHistoryInsertServiceResBean();
        customerInfoUpdateHistoryInsertReqDto.setCustomerId(customerUpdateHistoryInsertServiceReqBean.getCustomerId());
        customerInfoUpdateHistoryInsertReqDto
                .setDescription(customerUpdateHistoryInsertServiceReqBean.getDescription());
        customerInfoUpdateHistoryInsertReqDto.setUpdatedBy(customerUpdateHistoryInsertServiceReqBean.getUpdatedBy());
        customerInfoUpdateHistoryInsertReqDto
                .setUpdatedTime(customerUpdateHistoryInsertServiceReqBean.getUpdatedTime());

        try {
            getDaoServiceInvoker().execute(customerInfoUpdateHistoryInsertReqDto);
            CustomerUpdateHistoryInsertServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                CustomerUpdateHistoryInsertServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                CustomerUpdateHistoryInsertServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return CustomerUpdateHistoryInsertServiceResBean;
    }

}
