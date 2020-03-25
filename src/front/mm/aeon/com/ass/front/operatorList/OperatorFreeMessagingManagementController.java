/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.freeAdminCustomerRoomInsertService.FreeAdminCustomerRoomInsertServiceReqBean;
import mm.aeon.com.ass.base.service.freeAdminCustomerRoomInsertService.FreeAdminCustomerRoomInsertServiceResBean;
import mm.aeon.com.ass.base.service.userInfoUpdateService.UserInfoUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.userInfoUpdateService.UserInfoUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class OperatorFreeMessagingManagementController extends AbstractController
        implements IControllerAccessor<OperatorListFormBean, OperatorListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public OperatorListFormBean process(OperatorListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Operator Allow Free Messaging started.", LogLevel.INFO);

        MessageBean msgBean;
        String serviceStatus;

        UserInfoUpdateServiceReqBean updateReqBean = new UserInfoUpdateServiceReqBean();
        UserInfoUpdateServiceResBean updateResBean;

        updateReqBean.setUserTypeId(VCSCommon.USER_TYPE_OPERATOR_ID);
        updateReqBean.setUserId(formBean.getLineBean().getUserId());
        updateReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
        updateReqBean.setUpdatedTime(formBean.getLineBean().getUpdatedTime());
        updateReqBean.setAllowFreeMessagingFlag(1);

        this.getServiceInvoker().addRequest(updateReqBean);
        ResponseMessage responseupdateMessage = this.getServiceInvoker().invoke();

        FreeAdminCustomerRoomInsertServiceReqBean adminCustomerRoomInsertServiceReqBean =
                new FreeAdminCustomerRoomInsertServiceReqBean();
        FreeAdminCustomerRoomInsertServiceResBean freeAdminCustomerRoomInsertServiceResBean =
                new FreeAdminCustomerRoomInsertServiceResBean();

        adminCustomerRoomInsertServiceReqBean.setUserId(formBean.getLineBean().getUserId());

        this.getServiceInvoker().addRequest(adminCustomerRoomInsertServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        freeAdminCustomerRoomInsertServiceResBean = responseMessage.getMessageBean(0);
        serviceStatus = freeAdminCustomerRoomInsertServiceResBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Operator free message room insert finished.", LogLevel.INFO);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Insert free operator message room data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating free operator message room data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating free operator message room data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

}
