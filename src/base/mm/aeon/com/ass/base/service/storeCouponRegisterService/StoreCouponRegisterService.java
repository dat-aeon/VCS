/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeCouponRegisterService;


import mm.aeon.com.ass.base.dto.storeCouponRegister.StoreCouponRegisterReqDto;
import mm.aeon.com.ass.base.dto.storeCouponUpdate.StoreCouponUpdateReqDto;
import mm.aeon.com.ass.base.service.storeCouponUpdateService.StoreCouponUpdateServiceResBean;
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

public class StoreCouponRegisterService extends AbstractService implements IService<StoreCouponRegisterServiceReqBean, StoreCouponRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();
    
    @Override
    public StoreCouponRegisterServiceResBean execute(StoreCouponRegisterServiceReqBean reqBean) {
       
        StoreCouponRegisterServiceResBean resBean = new StoreCouponRegisterServiceResBean();
        try {
            StoreCouponRegisterReqDto reqDto = new StoreCouponRegisterReqDto();
            reqDto.setBranch_id(reqBean.getBranch_id());
            reqDto.setCoupon_id(reqBean.getCoupon_id());
            reqDto.setStore_id(reqBean.getStore_id());
            reqDto.setCoupon_password(reqBean.getCoupon_password());
            //reqDto.setCreated_by(reqBean.getCreated_by());
            //reqDto.setCreated_time(reqBean.getCreated_time());
            reqDto.setUpdated_by(reqBean.getUpdated_by());
            reqDto.setUpdated_time(reqBean.getUpdated_time());

            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);

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
