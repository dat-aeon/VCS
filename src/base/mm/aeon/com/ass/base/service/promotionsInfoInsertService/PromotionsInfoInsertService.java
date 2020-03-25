/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.promotionsInfoInsertService;

import mm.aeon.com.ass.base.dto.promotionsInfoRegister.PromotionsInfoInsertReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class PromotionsInfoInsertService extends AbstractService
        implements IService<PromotionsInfoInsertServiceReqBean, PromotionsInfoInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public PromotionsInfoInsertServiceResBean execute(
            PromotionsInfoInsertServiceReqBean promotionsInfoInsertServiceReqBean) {

        PromotionsInfoInsertReqDto promotionsInfoInsertReqDto = new PromotionsInfoInsertReqDto();

        PromotionsInfoInsertServiceResBean promotionsInfoInsertServiceResBean =
                new PromotionsInfoInsertServiceResBean();

        promotionsInfoInsertReqDto.setTitleEng(promotionsInfoInsertServiceReqBean.getTitleEng());
        promotionsInfoInsertReqDto.setTitleMyn(promotionsInfoInsertServiceReqBean.getTitleMyn());
        promotionsInfoInsertReqDto.setContentEng(promotionsInfoInsertServiceReqBean.getContentEng());
        promotionsInfoInsertReqDto.setContentMyn(promotionsInfoInsertServiceReqBean.getContentMyn());
        promotionsInfoInsertReqDto.setPublishedFromDate(promotionsInfoInsertServiceReqBean.getPublishedFromDate());
        promotionsInfoInsertReqDto.setPublishedToDate(promotionsInfoInsertServiceReqBean.getPublishedToDate());
        promotionsInfoInsertReqDto.setImagePath(promotionsInfoInsertServiceReqBean.getImagePath());
        promotionsInfoInsertReqDto.setLongitude(promotionsInfoInsertServiceReqBean.getLongitude());
        promotionsInfoInsertReqDto.setLatitude(promotionsInfoInsertServiceReqBean.getLatitude());
        promotionsInfoInsertReqDto.setDelFlag(promotionsInfoInsertServiceReqBean.getDelFlag());
        promotionsInfoInsertReqDto.setCreatedBy(promotionsInfoInsertServiceReqBean.getCreatedBy());
        promotionsInfoInsertReqDto.setCreatedTime(promotionsInfoInsertServiceReqBean.getCreatedTime());
        promotionsInfoInsertReqDto.setAnnouncementUrl(promotionsInfoInsertServiceReqBean.getAnnouncementUrl());

        try {
            getDaoServiceInvoker().execute(promotionsInfoInsertReqDto);
            promotionsInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                promotionsInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                promotionsInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return promotionsInfoInsertServiceResBean;
    }

}
