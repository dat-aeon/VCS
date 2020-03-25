/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.freeAdminCustomerRoomSyncService;

import java.util.List;

import mm.aeon.com.ass.base.dto.freeAdminCustomerRoomSync.FreeAdminCustomerRoomSyncReqDto;
import mm.aeon.com.ass.base.dto.freeCustomerRoomInfoSearch.FreeCustomerRoomInfoReqDto;
import mm.aeon.com.ass.base.dto.freeCustomerRoomInfoSearch.FreeCustomerRoomInfoResDto;
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

public class FreeAdminCustomerRoomSyncService extends AbstractService
        implements IService<FreeAdminCustomerRoomSyncServiceReqBean, FreeAdminCustomerRoomSyncServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public FreeAdminCustomerRoomSyncServiceResBean execute(FreeAdminCustomerRoomSyncServiceReqBean reqBean) {

        FreeAdminCustomerRoomSyncServiceResBean resBean = new FreeAdminCustomerRoomSyncServiceResBean();

        try {
            FreeCustomerRoomInfoReqDto customerRoomInfoReqDto = new FreeCustomerRoomInfoReqDto();
            List<FreeCustomerRoomInfoResDto> customerRoomInfoResDtoList =
                    (List<FreeCustomerRoomInfoResDto>) this.getDaoServiceInvoker().execute(customerRoomInfoReqDto);

            for (FreeCustomerRoomInfoResDto customerRoomInfoResDto : customerRoomInfoResDtoList) {
                FreeAdminCustomerRoomSyncReqDto adminCustomerRoomSyncReqDto = new FreeAdminCustomerRoomSyncReqDto();
                adminCustomerRoomSyncReqDto.setCustRoomId(customerRoomInfoResDto.getFreeCustRoomId());
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
