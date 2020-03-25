/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.couponInsertService;

import mm.aeon.com.ass.base.dto.couponRegister.CouponInsertReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class CouponInsertService extends AbstractService implements IService<CouponInsertServiceReqBean, CouponInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();
    
    @Override
    public CouponInsertServiceResBean execute(CouponInsertServiceReqBean reqBean) {
       
        CouponInsertReqDto  reqDto = new CouponInsertReqDto();
        
        CouponInsertServiceResBean resBean = new CouponInsertServiceResBean();
        
        reqDto.setCoupon_code(reqBean.getCoupon_code());
        reqDto.setCoupon_name(reqBean.getCoupon_name());
        reqDto.setCouponNameMya(reqBean.getCouponNameMya());
        reqDto.setCoupon_desc(reqBean.getCoupon_desc());
        reqDto.setCouponDescriptionMya(reqBean.getCouponDescriptionMya());
        reqDto.setCoupon_amount(reqBean.getCoupon_amount());
        reqDto.setStartDate(reqBean.getStartDate());
        reqDto.setExpireDate(reqBean.getExpireDate());
        reqDto.setDiscount_percent(reqBean.getDiscount_percent());
        reqDto.setUnuse_image_path(reqBean.getUnuse_image_path());
        reqDto.setUse_image_path(reqBean.getUse_image_path());
        reqDto.setDel_flag(reqBean.getDel_flag());
        reqDto.setCouponSpEvent(reqBean.getCouponSpEvent());
        reqDto.setCouponSpEventMya(reqBean.getCouponSpEventMya());
        reqDto.setCouponTlNo(reqBean.getCouponTlNo());
        reqDto.setCouponNoOfCus(reqBean.getCouponNoOfCus());
        reqDto.setDiscount_unit(reqBean.getDiscount_unit());
        reqDto.setCreated_by(reqBean.getCreated_by());
        reqDto.setUpdated_by(reqBean.getUpdated_by());
        reqDto.setCreated_time(reqBean.getCreated_time());
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
