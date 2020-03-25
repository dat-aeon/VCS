/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.promotionsInfoUpdateService;

import mm.aeon.com.ass.base.dto.promotionsInfoUpdate.PromotionsInfoUpdateReqDto;
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

public class PromotionsInfoUpdateService extends AbstractService
        implements IService<PromotionsInfoUpdateServiceReqBean, PromotionsInfoUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public PromotionsInfoUpdateServiceResBean execute(
            PromotionsInfoUpdateServiceReqBean promotionsInfoUpdateServiceReqBean) {

        PromotionsInfoUpdateServiceResBean promotionsInfoUpdateServiceResBean =
                new PromotionsInfoUpdateServiceResBean();

        PromotionsInfoUpdateReqDto promotionsInfoUpdateReqDto = new PromotionsInfoUpdateReqDto();

        try {
            promotionsInfoUpdateReqDto.setPromotionsInfoId(promotionsInfoUpdateServiceReqBean.getPromotionsInfoId());
            promotionsInfoUpdateReqDto.setTitleEng(promotionsInfoUpdateServiceReqBean.getTitleEng());
            promotionsInfoUpdateReqDto.setTitleMyn(promotionsInfoUpdateServiceReqBean.getTitleMyn());
            promotionsInfoUpdateReqDto.setContentEng(promotionsInfoUpdateServiceReqBean.getContentEng());
            promotionsInfoUpdateReqDto.setContentMyn(promotionsInfoUpdateServiceReqBean.getContentMyn());
            promotionsInfoUpdateReqDto.setPublishedFromDate(promotionsInfoUpdateServiceReqBean.getPublishedFromDate());
            promotionsInfoUpdateReqDto.setPublishedToDate(promotionsInfoUpdateServiceReqBean.getPublishedToDate());
            promotionsInfoUpdateReqDto.setImagePath(promotionsInfoUpdateServiceReqBean.getImagePath());
            promotionsInfoUpdateReqDto.setLongitude(promotionsInfoUpdateServiceReqBean.getLongitude());
            promotionsInfoUpdateReqDto.setLatitude(promotionsInfoUpdateServiceReqBean.getLatitude());
            promotionsInfoUpdateReqDto.setUpdatedBy(promotionsInfoUpdateServiceReqBean.getUpdatedBy());
            promotionsInfoUpdateReqDto.setUpdatedTime(promotionsInfoUpdateServiceReqBean.getUpdatedTime());
            promotionsInfoUpdateReqDto.setAnnouncementUrl(promotionsInfoUpdateServiceReqBean.getAnnouncementUrl());

            this.getDaoServiceInvoker().execute(promotionsInfoUpdateReqDto);
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                promotionsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
                promotionsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                promotionsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);
            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        promotionsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.OK);

        return promotionsInfoUpdateServiceResBean;
    }

}
