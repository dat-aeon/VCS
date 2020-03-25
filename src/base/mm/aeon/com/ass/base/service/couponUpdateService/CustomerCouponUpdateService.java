/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.couponUpdateService;

import mm.aeon.com.ass.base.dto.couponUpdate.CusCouponUpdateReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerCouponUpdateService extends AbstractService implements IService<CustomerCouponUpdateServiceReqBean, CustomerCouponUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();
    
    @Override
    public CustomerCouponUpdateServiceResBean execute(CustomerCouponUpdateServiceReqBean reqBean) {
      
        CustomerCouponUpdateServiceResBean resBean = new CustomerCouponUpdateServiceResBean();
        
        CusCouponUpdateReqDto reqDto = new CusCouponUpdateReqDto();
        
        reqDto.setCoupon_id(reqBean.getCoupon_id());
        reqDto.setCustomer_id(reqBean.getCustomer_id());
        reqDto.setCreated_by(reqBean.getCreated_by());
        reqDto.setCreated_time(reqBean.getCreated_time());
        reqDto.setUpdated_by(reqBean.getUpdated_by());
        reqDto.setUpdated_time(reqBean.getUpdated_time());
        
        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

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
