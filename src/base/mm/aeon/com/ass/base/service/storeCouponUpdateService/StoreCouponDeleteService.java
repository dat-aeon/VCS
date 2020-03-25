/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeCouponUpdateService;

import mm.aeon.com.ass.base.dto.storeCouponDelete.StoreCouponDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class StoreCouponDeleteService extends AbstractService
        implements IService<StoreCouponDeleteServiceReqBean, StoreCouponDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public StoreCouponDeleteServiceResBean execute(StoreCouponDeleteServiceReqBean storeCouponDeleteServiceReqBean) {

        StoreCouponDeleteReqDto reqDto = new StoreCouponDeleteReqDto();
        StoreCouponDeleteServiceResBean storeCouponDeleteServiceResBean = new StoreCouponDeleteServiceResBean();
        try {
            reqDto.setCoupon_id(storeCouponDeleteServiceReqBean.getCoupon_id());
            reqDto.setBranch_id(storeCouponDeleteServiceReqBean.getBranch_id());
            reqDto.setStore_id(storeCouponDeleteServiceReqBean.getStore_id());

            this.getDaoServiceInvoker().execute(reqDto);

            storeCouponDeleteServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                storeCouponDeleteServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                storeCouponDeleteServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return storeCouponDeleteServiceResBean;
    }

}
