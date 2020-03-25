/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorManagement;

import mm.aeon.com.ass.base.service.userInfoRegisterService.UserInfoRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.userInfoRegisterService.UserInfoRegisterServiceResBean;
import mm.aeon.com.ass.base.service.userInfoUpdateService.UserInfoUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.userInfoUpdateService.UserInfoUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
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

public class OperatorManagementController extends AbstractController
        implements IControllerAccessor<OperatorManagementFormBean, OperatorManagementFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public OperatorManagementFormBean process(OperatorManagementFormBean formBean) {

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        if (!VCSCommon.USER_TYPE_ADMIN.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        String functionName = "";
        if (formBean.getManagementHeaderBean().getForUpdate()) {
            functionName = "Update";
        } else {
            functionName = "Register";
        }

        if (!isValidData(formBean)) {
            return formBean;
        }

        applicationLogger.log("Operator " + functionName + " Process started.", LogLevel.INFO);

        String serviceStatus = null;

        if (!formBean.getManagementHeaderBean().getForUpdate()) {

            UserInfoRegisterServiceReqBean registerReqBean = new UserInfoRegisterServiceReqBean();
            UserInfoRegisterServiceResBean operatorResBean;

            registerReqBean.setCreatedBy(CommonUtil.getLoginUserName());
            registerReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
            registerReqBean.setUserTypeId(VCSCommon.USER_TYPE_OPERATOR_ID);
            registerReqBean.setPassword(PasswordEncoder.encode(formBean.getManagementHeaderBean().getPassword()));
            registerReqBean.setLoginId(formBean.getManagementHeaderBean().getUserLoginId());
            registerReqBean.setName(formBean.getManagementHeaderBean().getUserName());

            this.getServiceInvoker().addRequest(registerReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            operatorResBean = responseMessage.getMessageBean(0);
            serviceStatus = operatorResBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.MI0001);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Operator " + functionName + " Process finished.", LogLevel.INFO);

            }

        } else {

            UserInfoUpdateServiceReqBean updateReqBean = new UserInfoUpdateServiceReqBean();
            UserInfoUpdateServiceResBean updateResBean;

            if (!InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getPassword())) {
                updateReqBean.setPassword(PasswordEncoder.encode(formBean.getManagementHeaderBean().getPassword()));
            }

            updateReqBean.setUserTypeId(VCSCommon.USER_TYPE_OPERATOR_ID);
            updateReqBean.setUserId(formBean.getManagementHeaderBean().getUserId());
            updateReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
            updateReqBean.setUpdatedTime(formBean.getManagementHeaderBean().getUpdatedTime());
            updateReqBean.setLoginId(formBean.getManagementHeaderBean().getUserLoginId());
            updateReqBean.setName(formBean.getManagementHeaderBean().getUserName());

            this.getServiceInvoker().addRequest(updateReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            updateResBean = responseMessage.getMessageBean(0);
            serviceStatus = updateResBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Operator Update Process finished.", LogLevel.INFO);
            }
        }

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, VCSCommon.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registered Operater data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Team data is deleted.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        // } catch (PrestoDBException e) {
        // if (e instanceof DaoSqlException) {
        // logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
        // throw new BaseException(e.getCause());
        // }
        // }

        return formBean;
    }

    private boolean isValidData(OperatorManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getUserLoginId())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LOGIN_ID));
            msgBean.addColumnId(DisplayItem.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getUserName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
            msgBean.addColumnId(DisplayItem.NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        isValid = isValid & isValidPassword(formBean);

        return isValid;
    }

    private boolean isValidPassword(OperatorManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (!formBean.getManagementHeaderBean().getForUpdate()
                || !InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getPassword())
                || !InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getConfirmPassword())) {

            if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getPassword())) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PASSWORD));
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (formBean.getManagementHeaderBean().getPassword().length() < 8) {
                msgBean = new MessageBean(MessageId.ME0005);
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getConfirmPassword())) {
                msgBean = new MessageBean(MessageId.ME0003,
                        DisplayItemBean.getDisplayItemName(DisplayItem.CONFIRM_PASSWORD));
                msgBean.addColumnId(DisplayItem.CONFIRM_PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (!(formBean.getManagementHeaderBean().getConfirmPassword()
                    .equals(formBean.getManagementHeaderBean().getPassword()))) {
                msgBean = new MessageBean(MessageId.ME0006);
                msgBean.addColumnId(DisplayItem.CONFIRM_PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }
        return isValid;
    }

}
