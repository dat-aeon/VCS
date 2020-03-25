/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.couponDeleteService;

import mm.aeon.com.ass.base.dto.couponDelete.CouponDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class CouponDeleteService extends AbstractService
        implements IService<CouponDeleteServiceReqBean, CouponDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CouponDeleteServiceResBean execute(CouponDeleteServiceReqBean serviceReqBean) {

        CouponDeleteReqDto reqDto = new CouponDeleteReqDto();
        CouponDeleteServiceResBean serviceResBean = new CouponDeleteServiceResBean();
        try {
            reqDto.setCoupon_id(serviceReqBean.getCoupon_id());
            reqDto.setDel_flag(serviceReqBean.getDel_flag());
            reqDto.setUpdated_by(serviceReqBean.getUpdated_by());
            reqDto.setUpdated_time(serviceReqBean.getUpdated_time());

            this.getDaoServiceInvoker().execute(reqDto);

            serviceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                serviceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                serviceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return serviceResBean;
    }

}
