/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoList;

import mm.aeon.com.ass.base.service.newsInfoDeleteService.NewsInfoDeleteServiceReqBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class NewsInfoDeleteController extends AbstractController
        implements IControllerAccessor<NewsInfoListFormBean, NewsInfoListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public NewsInfoListFormBean process(NewsInfoListFormBean newsInfoListFormBean) {

        newsInfoListFormBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("News info deleting process started.", LogLevel.INFO);
        MessageBean msgBean;

        NewsInfoDeleteServiceReqBean newsInfoDeleteServiceReqBean = new NewsInfoDeleteServiceReqBean();
        newsInfoDeleteServiceReqBean.setNewsInfoId(newsInfoListFormBean.getNewsInfoListLineBean().getNewsInfoId());
        newsInfoDeleteServiceReqBean.setDelFlag(1);
        newsInfoDeleteServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
        newsInfoDeleteServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

        this.getServiceInvoker().addRequest(newsInfoDeleteServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            newsInfoListFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("News info deleting process finished.", LogLevel.INFO);
        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("News info deleting process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }

        return newsInfoListFormBean;
    }

}
