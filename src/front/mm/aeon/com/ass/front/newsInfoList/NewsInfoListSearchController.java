/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoList;

import mm.aeon.com.ass.base.dto.newsInfoSearch.NewsInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.newsInfoSearch.NewsInfoSelectCountReqDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class NewsInfoListSearchController extends AbstractController
        implements IControllerAccessor<NewsInfoListFormBean, NewsInfoListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public NewsInfoListFormBean process(NewsInfoListFormBean newsInfoListFormBean) {

        newsInfoListFormBean.getMessageContainer().clearAllMessages(!newsInfoListFormBean.getDoReload());

        applicationLogger.log("News info searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        NewsInfoSelectCountReqDto newsInfoSelectCountReqDto = new NewsInfoSelectCountReqDto();

        NewsInfoSearchReqDto newsInfoSearchReqDto = new NewsInfoSearchReqDto();

        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(newsInfoSelectCountReqDto);
            newsInfoListFormBean.setTotalCount(totalCount);
            newsInfoListFormBean.setNewsInfoSearchReqDto(newsInfoSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            newsInfoListFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("News info searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return newsInfoListFormBean;
    }

}
