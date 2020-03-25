/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoManagement;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.categoryInfoInsertService.CategoryInfoInsertServiceReqBean;
import mm.aeon.com.ass.base.service.categoryInfoUpdateService.CategoryInfoUpdateServiceReqBean;
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

public class CategoryInfoManagementController extends AbstractController
        implements IControllerAccessor<CategoryInfoManagementFormBean, CategoryInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CategoryInfoManagementFormBean process(CategoryInfoManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean msgBean;
        String serviceStatus = null;

        if (!formBean.isUpdate()) {
            applicationLogger.log("Category info registration process stared.", LogLevel.INFO);

            CategoryInfoInsertServiceReqBean categoryInfoInsertServiceReqBean = new CategoryInfoInsertServiceReqBean();
            categoryInfoInsertServiceReqBean
                    .setCategoryEng(formBean.getCategoryInfoManagementHeaderBean().getCategoryEng());
            categoryInfoInsertServiceReqBean
                    .setCategoryMyn(formBean.getCategoryInfoManagementHeaderBean().getCategoryMyn());
            categoryInfoInsertServiceReqBean
                    .setDescription(formBean.getCategoryInfoManagementHeaderBean().getDescription());
            categoryInfoInsertServiceReqBean.setDelFlag(0);
            categoryInfoInsertServiceReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
            categoryInfoInsertServiceReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());

            this.getServiceInvoker().addRequest(categoryInfoInsertServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                msgBean = new MessageBean(MessageId.MI0001);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Category info registration finished.", LogLevel.INFO);

            } else {
                setErrorMessage(formBean, serviceStatus);
            }
        } else {

            applicationLogger.log("Category info update process started.", LogLevel.INFO);

            CategoryInfoUpdateServiceReqBean categoryInfoUpdateServiceReqBean = new CategoryInfoUpdateServiceReqBean();
            categoryInfoUpdateServiceReqBean
                    .setCategoryId(formBean.getCategoryInfoManagementHeaderBean().getCategoryId());
            categoryInfoUpdateServiceReqBean
                    .setCategoryEng(formBean.getCategoryInfoManagementHeaderBean().getCategoryEng());
            categoryInfoUpdateServiceReqBean
                    .setCategoryMyn(formBean.getCategoryInfoManagementHeaderBean().getCategoryMyn());
            categoryInfoUpdateServiceReqBean
                    .setDescription(formBean.getCategoryInfoManagementHeaderBean().getDescription());
            categoryInfoUpdateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
            categoryInfoUpdateServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

            this.getServiceInvoker().addRequest(categoryInfoUpdateServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Category info update process finished.", LogLevel.INFO);
            } else {
                setErrorMessage(formBean, serviceStatus);
            }

        }

        return formBean;
    }

    private CategoryInfoManagementFormBean setErrorMessage(CategoryInfoManagementFormBean formBean,
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

    private boolean isValidData(CategoryInfoManagementFormBean formBean) {
        boolean isValid = true;
        // boolean isCheckCode = false;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getCategoryInfoManagementHeaderBean().getCategoryEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CATEGORY_ENG));
            msgBean.addColumnId(DisplayItem.CATEGORY_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getCategoryInfoManagementHeaderBean().getCategoryEng())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.CATEGORY_ENG));
            msgBean.addColumnId(DisplayItem.CATEGORY_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCategoryInfoManagementHeaderBean().getCategoryMyn())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CATEGORY_MYN));
            msgBean.addColumnId(DisplayItem.CATEGORY_MYN);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCategoryInfoManagementHeaderBean().getDescription())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.DESCRIPTION));
            msgBean.addColumnId(DisplayItem.DESCRIPTION);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
