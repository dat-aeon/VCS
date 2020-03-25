/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.freeAdminCustomerRoomInsertService;

import java.util.List;

import mm.aeon.com.ass.base.dto.freeAdminCustomerRoomInsert.FreeAdminCustomerRoomInsertReqDto;
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

public class FreeAdminCustomerRoomInsertService extends AbstractService
        implements IService<FreeAdminCustomerRoomInsertServiceReqBean, FreeAdminCustomerRoomInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public FreeAdminCustomerRoomInsertServiceResBean execute(FreeAdminCustomerRoomInsertServiceReqBean reqBean) {

        FreeAdminCustomerRoomInsertServiceResBean resBean = new FreeAdminCustomerRoomInsertServiceResBean();

        try {
            FreeCustomerRoomInfoReqDto customerRoomInfoReqDto = new FreeCustomerRoomInfoReqDto();
            List<FreeCustomerRoomInfoResDto> customerRoomInfoResDtoList =
                    (List<FreeCustomerRoomInfoResDto>) this.getDaoServiceInvoker().execute(customerRoomInfoReqDto);

            for (FreeCustomerRoomInfoResDto customerRoomInfoResDto : customerRoomInfoResDtoList) {
                FreeAdminCustomerRoomInsertReqDto adminCustomerRoomInsertReqDto =
                        new FreeAdminCustomerRoomInsertReqDto();
                adminCustomerRoomInsertReqDto.setCustRoomId(customerRoomInfoResDto.getFreeCustRoomId());
                adminCustomerRoomInsertReqDto.setUserId(reqBean.getUserId());
                adminCustomerRoomInsertReqDto.setFinishFlag(1);
                adminCustomerRoomInsertReqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                this.getDaoServiceInvoker().execute(adminCustomerRoomInsertReqDto);

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
