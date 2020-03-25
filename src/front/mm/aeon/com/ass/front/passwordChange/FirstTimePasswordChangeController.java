/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.passwordChange;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.passwordInfoUpdateService.PasswordInfoUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.authenticate.PasswordEncoder;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class FirstTimePasswordChangeController extends AbstractController
        implements IControllerAccessor<FirstTimePasswordChangeFormBean, FirstTimePasswordChangeFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public FirstTimePasswordChangeFormBean process(FirstTimePasswordChangeFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean msgBean;
        String serviceStatus = null;

        applicationLogger.log("Password Change process stared.", LogLevel.INFO);

        PasswordInfoUpdateServiceReqBean PasswordInfoUpdateServiceReqBean = new PasswordInfoUpdateServiceReqBean();
        PasswordInfoUpdateServiceReqBean.setUserId(CommonUtil.getLoginUserId());
        PasswordInfoUpdateServiceReqBean.setUserTypeId(Integer.parseInt(CommonUtil.getLoginUserTypeId()));
        String hashNewPassword = PasswordEncoder.encode(formBean.getPasswordChangeHeaderBean().getNewPassword());
        PasswordInfoUpdateServiceReqBean.setPassword(hashNewPassword);
        PasswordInfoUpdateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
        PasswordInfoUpdateServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

        this.getServiceInvoker().addRequest(PasswordInfoUpdateServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();

        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {

            msgBean = new MessageBean(MessageId.ME1041);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Password Change Process is finished.", LogLevel.INFO);

        } else {
            setErrorMessage(formBean, serviceStatus);
        }

        return formBean;
    }

    private FirstTimePasswordChangeFormBean setErrorMessage(FirstTimePasswordChangeFormBean formBean,
            String serviceStatus) {

        MessageBean msgBean;

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, VCSCommon.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd category info data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update category info data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating category info data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating category info data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(FirstTimePasswordChangeFormBean formBean) {
        boolean isValid = true;
        // boolean isCheckCode = false;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getPasswordChangeHeaderBean().getNewPassword())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NEW_PASSWORD));
            msgBean.addColumnId(DisplayItem.NEW_PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getPasswordChangeHeaderBean().getConfirmPassword())) {
            msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CONFIRM_PASSWORD));
            msgBean.addColumnId(DisplayItem.CONFIRM_PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else if (formBean.getPasswordChangeHeaderBean().getNewPassword().length() < 8) {
            msgBean = new MessageBean(MessageId.ME0005);
            msgBean.addColumnId(DisplayItem.PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!(formBean.getPasswordChangeHeaderBean().getConfirmPassword()
                .equals(formBean.getPasswordChangeHeaderBean().getNewPassword()))
                && !InputChecker.isBlankOrNull(formBean.getPasswordChangeHeaderBean().getConfirmPassword())
                && !InputChecker.isBlankOrNull(formBean.getPasswordChangeHeaderBean().getNewPassword())) {
            msgBean = new MessageBean(MessageId.ME1042, DisplayItemBean.getDisplayItemName(DisplayItem.NEW_PASSWORD));
            msgBean.addColumnId(DisplayItem.NEW_PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
