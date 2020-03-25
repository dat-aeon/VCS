/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.newsInfoInsertService;

import mm.aeon.com.ass.base.dto.newsInfoRegister.NewsInfoInsertReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class NewsInfoInsertService extends AbstractService
        implements IService<NewsInfoInsertServiceReqBean, NewsInfoInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public NewsInfoInsertServiceResBean execute(NewsInfoInsertServiceReqBean newsInfoInsertServiceReqBean) {

        NewsInfoInsertReqDto newsInfoInsertReqDto = new NewsInfoInsertReqDto();

        NewsInfoInsertServiceResBean newsInfoInsertServiceResBean = new NewsInfoInsertServiceResBean();

        newsInfoInsertReqDto.setTitleEng(newsInfoInsertServiceReqBean.getTitleEng());
        newsInfoInsertReqDto.setTitleMyn(newsInfoInsertServiceReqBean.getTitleMyn());
        newsInfoInsertReqDto.setContentEng(newsInfoInsertServiceReqBean.getContentEng());
        newsInfoInsertReqDto.setContentMyn(newsInfoInsertServiceReqBean.getContentMyn());
        newsInfoInsertReqDto.setPublishedFromDate(newsInfoInsertServiceReqBean.getPublishedFromDate());
        newsInfoInsertReqDto.setPublishedToDate(newsInfoInsertServiceReqBean.getPublishedToDate());
        newsInfoInsertReqDto.setImagePath(newsInfoInsertServiceReqBean.getImagePath());
        newsInfoInsertReqDto.setLongitude(newsInfoInsertServiceReqBean.getLongitude());
        newsInfoInsertReqDto.setLatitude(newsInfoInsertServiceReqBean.getLatitude());
        newsInfoInsertReqDto.setDelFlag(newsInfoInsertServiceReqBean.getDelFlag());
        newsInfoInsertReqDto.setCreatedBy(newsInfoInsertServiceReqBean.getCreatedBy());
        newsInfoInsertReqDto.setCreatedTime(newsInfoInsertServiceReqBean.getCreatedTime());
        newsInfoInsertReqDto.setNewsUrl(newsInfoInsertServiceReqBean.getNewsUrl());

        try {
            getDaoServiceInvoker().execute(newsInfoInsertReqDto);
            newsInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                newsInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                newsInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return newsInfoInsertServiceResBean;
    }

}
