/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminCustomerRoomSyncService;

import java.util.List;

import mm.aeon.com.ass.base.dto.adminCustomerRoomInsert.AdminCustomerRoomInsertReqDto;
import mm.aeon.com.ass.base.dto.adminCustomerRoomSync.AdminCustomerRoomSyncReqDto;
import mm.aeon.com.ass.base.dto.customerRoomInfoSearch.CustomerRoomInfoReqDto;
import mm.aeon.com.ass.base.dto.customerRoomInfoSearch.CustomerRoomInfoResDto;
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

public class AdminCustomerRoomSyncService extends AbstractService
        implements IService<AdminCustomerRoomSyncServiceReqBean, AdminCustomerRoomSyncServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminCustomerRoomSyncServiceResBean execute(AdminCustomerRoomSyncServiceReqBean reqBean) {

        AdminCustomerRoomSyncServiceResBean resBean = new AdminCustomerRoomSyncServiceResBean();

        try {
            CustomerRoomInfoReqDto customerRoomInfoReqDto = new CustomerRoomInfoReqDto();
            List<CustomerRoomInfoResDto> customerRoomInfoResDtoList =
                    (List<CustomerRoomInfoResDto>) this.getDaoServiceInvoker().execute(customerRoomInfoReqDto);

            for (CustomerRoomInfoResDto customerRoomInfoResDto : customerRoomInfoResDtoList) {
                AdminCustomerRoomSyncReqDto adminCustomerRoomSyncReqDto = new AdminCustomerRoomSyncReqDto();
                adminCustomerRoomSyncReqDto.setCustRoomId(customerRoomInfoResDto.getCustRoomId());
                adminCustomerRoomSyncReqDto.setUserId(reqBean.getUserId());
                adminCustomerRoomSyncReqDto.setFinishFlag(1);
                adminCustomerRoomSyncReqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                this.getDaoServiceInvoker().execute(adminCustomerRoomSyncReqDto);

                resBean.setServiceStatus(ServiceStatusCode.OK);
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
