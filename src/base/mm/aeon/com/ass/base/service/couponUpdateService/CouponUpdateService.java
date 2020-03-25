/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.couponUpdateService;

import mm.aeon.com.ass.base.dto.couponUpdate.CouponUpdateReqDto;
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

public class CouponUpdateService extends AbstractService
        implements IService<CouponUpdateServiceReqBean, CouponUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CouponUpdateServiceResBean execute(CouponUpdateServiceReqBean reqBean) {

        CouponUpdateServiceResBean resBean = new CouponUpdateServiceResBean();

        CouponUpdateReqDto updateReqDto = new CouponUpdateReqDto();

        try {
            updateReqDto.setCoupon_name(reqBean.getCoupon_name());
            updateReqDto.setCouponNameMya(reqBean.getCouponNameMya());
            updateReqDto.setCoupon_code(reqBean.getCoupon_code());
            updateReqDto.setCoupon_desc(reqBean.getCoupon_desc());
            updateReqDto.setCouponDescriptionMya(reqBean.getCouponDescriptionMya());
            updateReqDto.setCoupon_amount(reqBean.getCoupon_amount());
            updateReqDto.setStartDate(reqBean.getStartDate());
            updateReqDto.setExpireDate(reqBean.getExpireDate());
            updateReqDto.setDiscount_percent(reqBean.getDiscount_percent());
            updateReqDto.setDel_flag(reqBean.getDel_flag());
            updateReqDto.setUnuse_image_path(reqBean.getUnuse_image_path());
            updateReqDto.setUse_image_path(reqBean.getUse_image_path());
            updateReqDto.setCouponSpEvent(reqBean.getCouponSpEvent());
            updateReqDto.setCouponSpEventMya(reqBean.getCouponSpEventMya());
            updateReqDto.setCouponTlNo(reqBean.getCouponTlNo());
            updateReqDto.setCouponNoOfCus(reqBean.getCouponNoOfCus());
            updateReqDto.setCreated_by(reqBean.getCreated_by());
            updateReqDto.setCreated_time(reqBean.getCreated_time());
            updateReqDto.setUpdated_by(reqBean.getUpdated_by());
            updateReqDto.setUpdated_time(reqBean.getUpdated_time());
            updateReqDto.setDiscount_unit(reqBean.getDiscount_unit());
            updateReqDto.setCoupon_id(reqBean.getCoupon_id());

            this.getDaoServiceInvoker().execute(updateReqDto);
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
        resBean.setServiceStatus(ServiceStatusCode.OK);

        return resBean;
    }

}
