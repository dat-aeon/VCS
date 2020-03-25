/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfoManagement;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.companyInfoUpdateService.CompanyInfoUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
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
import mm.com.dat.presto.utils.common.InputChecker;

public class CompanyInfoManagementController extends AbstractController
        implements IControllerAccessor<CompanyInfoManagementFormBean, CompanyInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CompanyInfoManagementFormBean process(CompanyInfoManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);
        MessageBean msgBean;

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Admin Management]", new InvalidScreenTransitionException(), LogLevel.ERROR);
         * throw new InvalidScreenTransitionException(); }
         */

        String removeSpace = "";

        if (null != formBean.getCompanyInfoBean().getHotLine() & formBean.getCompanyInfoBean().getHotLine() != "") {
            removeSpace = formBean.getCompanyInfoBean().getHotLine().trim().replaceAll("\\s", "");
        }

        formBean.getCompanyInfoBean().setHotLine(removeSpace);

        if (!isValidData(formBean)) {
            return formBean;
        }

        String serviceStatus = null;

        applicationLogger.log("CompanyInfo Update Process started.", LogLevel.INFO);

        CompanyInfoUpdateServiceReqBean updateServiceReqBean = new CompanyInfoUpdateServiceReqBean();
        updateServiceReqBean.setInfoId(formBean.getCompanyInfoBean().getInfoId());
        updateServiceReqBean.setAddress_eng(formBean.getCompanyInfoBean().getAddress_eng());
        updateServiceReqBean.setAddress_mya(formBean.getCompanyInfoBean().getAddress_mya());
        updateServiceReqBean.setHotLine(formBean.getCompanyInfoBean().getHotLine().trim());
        updateServiceReqBean.setWebAddress(formBean.getCompanyInfoBean().getWebAddress());
        updateServiceReqBean.setSocialMedia(formBean.getCompanyInfoBean().getSocialMedia());
        updateServiceReqBean.setAboutCompanyEng(formBean.getCompanyInfoBean().getAboutCompanyEng());
        updateServiceReqBean.setAboutCompanyMya(formBean.getCompanyInfoBean().getAboutCompanyMya());
        updateServiceReqBean.setChatAutoReplyMessage(formBean.getCompanyInfoBean().getChatAutoReplyMessage());
        updateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserInfo().getUserName());
        updateServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

        this.getServiceInvoker().addRequest(updateServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {

            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("CompanyInfo update process finished.", LogLevel.INFO);
        } else {
            setErrorMessage(formBean, serviceStatus);
        }

        return formBean;
    }

    private CompanyInfoManagementFormBean setErrorMessage(CompanyInfoManagementFormBean formBean,
            String serviceStatus) {

        MessageBean msgBean;

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, VCSCommon.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd admin data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update admin data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            // formBean.getUpdateParam().setIsUpdate(true);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating admin data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating admin data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(CompanyInfoManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getAddress_eng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.ADDRESS_ENG));
            msgBean.addColumnId(DisplayItem.ADDRESS_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getCompanyInfoBean().getAddress_eng())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.ADDRESS_ENG));
            msgBean.addColumnId(DisplayItem.ADDRESS_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
            // return isValid;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getAddress_mya())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.ADDRESS_MYA));
            msgBean.addColumnId(DisplayItem.ADDRESS_MYA);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getHotLine())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
            msgBean.addColumnId(DisplayItem.PHONE_NO);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getHotLine())
                && !formBean.getCompanyInfoBean().getHotLine().matches(VCSCommon.MOBILENO)) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
            msgBean.addColumnId(DisplayItem.PHONE_NO);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getWebAddress())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.WEB_ADDRESS));
            msgBean.addColumnId(DisplayItem.WEB_ADDRESS);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getSocialMedia())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SOCIAL_MEDIA));
            msgBean.addColumnId(DisplayItem.SOCIAL_MEDIA);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getAboutCompanyEng())) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.ABOUT_COMPANY_ENG));
            msgBean.addColumnId(DisplayItem.ABOUT_COMPANY_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getCompanyInfoBean().getAboutCompanyEng())) {
            msgBean = new MessageBean(MessageId.ME1003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.ABOUT_COMPANY_ENG));
            msgBean.addColumnId(DisplayItem.ABOUT_COMPANY_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
            // return isValid;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getAboutCompanyMya())) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.ABOUT_COMPANY_MYA));
            msgBean.addColumnId(DisplayItem.ABOUT_COMPANY_MYA);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCompanyInfoBean().getChatAutoReplyMessage())) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.CHAT_AUTO_REPLY_MESSAGE));
            msgBean.addColumnId(DisplayItem.CHAT_AUTO_REPLY_MESSAGE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }
}