/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.newsInfoDeleteService;

import mm.aeon.com.ass.base.dto.newsInfoDelete.NewsInfoDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class NewsInfoDeleteService extends AbstractService
        implements IService<NewsInfoDeleteServiceReqBean, NewsInfoDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public NewsInfoDeleteServiceResBean execute(NewsInfoDeleteServiceReqBean newsInfoDeleteServiceReqBean) {

        NewsInfoDeleteReqDto newsInfoDeleteReqDto = new NewsInfoDeleteReqDto();
        NewsInfoDeleteServiceResBean newsInfoDeleteServiceResBean = new NewsInfoDeleteServiceResBean();
        try {
            newsInfoDeleteReqDto.setNewsInfoId(newsInfoDeleteServiceReqBean.getNewsInfoId());
            newsInfoDeleteReqDto.setDelFlag(newsInfoDeleteServiceReqBean.getDelFlag());
            newsInfoDeleteReqDto.setUpdatedBy(newsInfoDeleteServiceReqBean.getUpdatedBy());
            newsInfoDeleteReqDto.setUpdatedTime(newsInfoDeleteServiceReqBean.getUpdatedTime());

            this.getDaoServiceInvoker().execute(newsInfoDeleteReqDto);

            newsInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                newsInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                newsInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return newsInfoDeleteServiceResBean;
    }

}
