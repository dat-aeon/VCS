/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.newsInfoUpdateService;

import mm.aeon.com.ass.base.dto.newsInfoUpdate.NewsInfoUpdateReqDto;
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

public class NewsInfoUpdateService extends AbstractService
        implements IService<NewsInfoUpdateServiceReqBean, NewsInfoUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public NewsInfoUpdateServiceResBean execute(NewsInfoUpdateServiceReqBean newsInfoUpdateServiceReqBean) {

        NewsInfoUpdateServiceResBean newsInfoUpdateServiceResBean = new NewsInfoUpdateServiceResBean();

        NewsInfoUpdateReqDto newsInfoUpdateReqDto = new NewsInfoUpdateReqDto();

        try {
            newsInfoUpdateReqDto.setNewsInfoId(newsInfoUpdateServiceReqBean.getNewsInfoId());
            newsInfoUpdateReqDto.setTitleEng(newsInfoUpdateServiceReqBean.getTitleEng());
            newsInfoUpdateReqDto.setTitleMyn(newsInfoUpdateServiceReqBean.getTitleMyn());
            newsInfoUpdateReqDto.setContentEng(newsInfoUpdateServiceReqBean.getContentEng());
            newsInfoUpdateReqDto.setContentMyn(newsInfoUpdateServiceReqBean.getContentMyn());
            newsInfoUpdateReqDto.setPublishedFromDate(newsInfoUpdateServiceReqBean.getPublishedFromDate());
            newsInfoUpdateReqDto.setPublishedToDate(newsInfoUpdateServiceReqBean.getPublishedToDate());
            newsInfoUpdateReqDto.setImagePath(newsInfoUpdateServiceReqBean.getImagePath());
            newsInfoUpdateReqDto.setLongitude(newsInfoUpdateServiceReqBean.getLongitude());
            newsInfoUpdateReqDto.setLatitude(newsInfoUpdateServiceReqBean.getLatitude());
            newsInfoUpdateReqDto.setUpdatedBy(newsInfoUpdateServiceReqBean.getUpdatedBy());
            newsInfoUpdateReqDto.setUpdatedTime(newsInfoUpdateServiceReqBean.getUpdatedTime());
            newsInfoUpdateReqDto.setNewsUrl(newsInfoUpdateServiceReqBean.getNewsUrl());

            this.getDaoServiceInvoker().execute(newsInfoUpdateReqDto);
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                newsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
                newsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                newsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);
            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        newsInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.OK);

        return newsInfoUpdateServiceResBean;
    }

}
