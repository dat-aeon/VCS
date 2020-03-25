/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.couponDeleteService;

import mm.aeon.com.ass.base.dto.couponDelete.CusCouponDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class CusCouponDeleteService extends AbstractService
        implements IService<CusCouponDeleteServiceReqBean, CusCouponDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CusCouponDeleteServiceResBean execute(CusCouponDeleteServiceReqBean cusCouponDeleteServiceReqBean) {

        CusCouponDeleteReqDto cusCouponDeleteReqDto = new CusCouponDeleteReqDto();
        CusCouponDeleteServiceResBean cusCouponDeleteServiceResBean = new CusCouponDeleteServiceResBean();
        try {
            
            cusCouponDeleteReqDto.setCoupon_id(cusCouponDeleteServiceReqBean.getCoupon_id());
            cusCouponDeleteReqDto.setCustomer_id(cusCouponDeleteServiceReqBean.getCustomer_id());

            this.getDaoServiceInvoker().execute(cusCouponDeleteReqDto);

            cusCouponDeleteServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                cusCouponDeleteServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                cusCouponDeleteServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return cusCouponDeleteServiceResBean;
    }

}
