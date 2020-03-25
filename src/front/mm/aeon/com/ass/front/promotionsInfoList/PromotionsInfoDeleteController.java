/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoList;

import mm.aeon.com.ass.base.service.promotionsInfoDeleteService.PromotionsInfoDeleteServiceReqBean;
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

public class PromotionsInfoDeleteController extends AbstractController
        implements IControllerAccessor<PromotionsInfoListFormBean, PromotionsInfoListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public PromotionsInfoListFormBean process(PromotionsInfoListFormBean promotionsInfoListFormBean) {

        promotionsInfoListFormBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Promotions info deleting process started.", LogLevel.INFO);
        MessageBean msgBean;

        PromotionsInfoDeleteServiceReqBean promotionsInfoDeleteServiceReqBean =
                new PromotionsInfoDeleteServiceReqBean();
        promotionsInfoDeleteServiceReqBean
                .setPromotionsInfoId(promotionsInfoListFormBean.getPromotionsInfoListLineBean().getPromotionsInfoId());
        promotionsInfoDeleteServiceReqBean.setDelFlag(1);
        promotionsInfoDeleteServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
        promotionsInfoDeleteServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

        this.getServiceInvoker().addRequest(promotionsInfoDeleteServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            promotionsInfoListFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Promotions info deleting process finished.", LogLevel.INFO);
        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Promotions info deleting process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }

        return promotionsInfoListFormBean;
    }

}
