/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoManagement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.promotionsInfoInsertService.PromotionsInfoInsertServiceReqBean;
import mm.aeon.com.ass.base.service.promotionsInfoUpdateService.PromotionsInfoUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.common.util.FileHandler;
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

public class PromotionsInfoManagementController extends AbstractController
        implements IControllerAccessor<PromotionsInfoManagementFormBean, PromotionsInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public PromotionsInfoManagementFormBean process(PromotionsInfoManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean msgBean;
        String serviceStatus = null;

        if (!formBean.isUpdate()) {
            applicationLogger.log("Promotions info registration process stared.", LogLevel.INFO);

            PromotionsInfoInsertServiceReqBean promotionsInfoInsertServiceReqBean =
                    new PromotionsInfoInsertServiceReqBean();
            promotionsInfoInsertServiceReqBean
                    .setTitleEng(formBean.getPromotionsInfoManagementHeaderBean().getTitleEng());
            promotionsInfoInsertServiceReqBean
                    .setTitleMyn(formBean.getPromotionsInfoManagementHeaderBean().getTitleMyn());
            promotionsInfoInsertServiceReqBean
                    .setContentEng(formBean.getPromotionsInfoManagementHeaderBean().getContentEng());
            promotionsInfoInsertServiceReqBean
                    .setContentMyn(formBean.getPromotionsInfoManagementHeaderBean().getContentMyn());
            promotionsInfoInsertServiceReqBean
                    .setPublishedFromDate(formBean.getPromotionsInfoManagementHeaderBean().getPublishedFromDate());
            promotionsInfoInsertServiceReqBean
                    .setPublishedToDate(formBean.getPromotionsInfoManagementHeaderBean().getPublishedToDate());

            if (StringUtils.isEmpty(formBean.getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath())) {
                promotionsInfoInsertServiceReqBean.setImagePath(new String());
            } else {
                promotionsInfoInsertServiceReqBean
                        .setImagePath(formBean.getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath());
            }

            promotionsInfoInsertServiceReqBean
                    .setLongitude(formBean.getPromotionsInfoManagementHeaderBean().getLongitude());
            promotionsInfoInsertServiceReqBean
                    .setLatitude(formBean.getPromotionsInfoManagementHeaderBean().getLatitude());
            promotionsInfoInsertServiceReqBean.setDelFlag(0);
            promotionsInfoInsertServiceReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
            promotionsInfoInsertServiceReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
            promotionsInfoInsertServiceReqBean
                    .setAnnouncementUrl(formBean.getPromotionsInfoManagementHeaderBean().getAnnouncementUrl());

            this.getServiceInvoker().addRequest(promotionsInfoInsertServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {

                    File sourceFile = new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                    File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                            + CommonUtil.getPromotionsInfoUploadImageFolder() + formBean.getUploadImageFileName());

                    try {
                        FileHandler.copyFile(sourceFile, destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                msgBean = new MessageBean(MessageId.MI0001);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Promotions info registration finished.", LogLevel.INFO);

            } else {
                setErrorMessage(formBean, serviceStatus);
            }
        } else {

            applicationLogger.log("Promotions info update process started.", LogLevel.INFO);

            PromotionsInfoUpdateServiceReqBean promotionsInfoUpdateServiceReqBean =
                    new PromotionsInfoUpdateServiceReqBean();
            promotionsInfoUpdateServiceReqBean
                    .setPromotionsInfoId(formBean.getPromotionsInfoManagementHeaderBean().getPromotionsInfoId());
            promotionsInfoUpdateServiceReqBean
                    .setTitleEng(formBean.getPromotionsInfoManagementHeaderBean().getTitleEng());
            promotionsInfoUpdateServiceReqBean
                    .setTitleMyn(formBean.getPromotionsInfoManagementHeaderBean().getTitleMyn());
            promotionsInfoUpdateServiceReqBean
                    .setContentEng(formBean.getPromotionsInfoManagementHeaderBean().getContentEng());
            promotionsInfoUpdateServiceReqBean
                    .setContentMyn(formBean.getPromotionsInfoManagementHeaderBean().getContentMyn());
            promotionsInfoUpdateServiceReqBean
                    .setPublishedFromDate(formBean.getPromotionsInfoManagementHeaderBean().getPublishedFromDate());
            promotionsInfoUpdateServiceReqBean
                    .setPublishedToDate(formBean.getPromotionsInfoManagementHeaderBean().getPublishedToDate());

            if (StringUtils.isEmpty(formBean.getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath())) {
                promotionsInfoUpdateServiceReqBean.setImagePath(new String());
            } else {
                promotionsInfoUpdateServiceReqBean
                        .setImagePath(formBean.getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath());
            }

            promotionsInfoUpdateServiceReqBean
                    .setLongitude(formBean.getPromotionsInfoManagementHeaderBean().getLongitude());
            promotionsInfoUpdateServiceReqBean
                    .setLatitude(formBean.getPromotionsInfoManagementHeaderBean().getLatitude());
            promotionsInfoUpdateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
            promotionsInfoUpdateServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
            promotionsInfoUpdateServiceReqBean
                    .setAnnouncementUrl(formBean.getPromotionsInfoManagementHeaderBean().getAnnouncementUrl());

            this.getServiceInvoker().addRequest(promotionsInfoUpdateServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                if (formBean.getOldUploadImageFilePath() != null) {
                    if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                        File sourceFile = new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                        File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                                + CommonUtil.getPromotionsInfoUploadImageFolder()
                                + formBean.getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath());

                        try {
                            FileHandler.copyFile(sourceFile, destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    if (formBean.getUploadImageFileName() != null) {
                        if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                            File sourceFile =
                                    new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                            File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                                    + CommonUtil.getNewsInfoUploadImageFolder() + formBean.getUploadImageFileName());

                            try {
                                FileHandler.copyFile(sourceFile, destFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Promotions info update process finished.", LogLevel.INFO);
            } else {
                setErrorMessage(formBean, serviceStatus);
            }

        }

        return formBean;

    }

    private PromotionsInfoManagementFormBean setErrorMessage(PromotionsInfoManagementFormBean formBean,
            String serviceStatus) {

        MessageBean msgBean;

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, VCSCommon.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd promotions info data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update promotions info data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating promotions info data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating promotions info data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(PromotionsInfoManagementFormBean formBean) {
        boolean isValid = true;
        // boolean isCheckCode = false;
        MessageBean msgBean;

        if (!StringUtils.isEmpty(formBean.getPromotionsInfoManagementHeaderBean().getAnnouncementUrl())) {
            try {
                URL url = new URL(formBean.getPromotionsInfoManagementHeaderBean().getAnnouncementUrl());
            } catch (MalformedURLException e) {
                msgBean = new MessageBean(MessageId.ME1003,
                        DisplayItemBean.getDisplayItemName(DisplayItem.ANNOUNCEMENT_URL));
                msgBean.addColumnId(DisplayItem.ANNOUNCEMENT_URL);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getTitleEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_ENG));
            msgBean.addColumnId(DisplayItem.TITLE_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (!CommonUtil.isPureAscii(formBean.getPromotionsInfoManagementHeaderBean().getTitleEng())) {
        // msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_ENG));
        // msgBean.addColumnId(DisplayItem.TITLE_ENG);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getTitleMyn())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_MYN));
            msgBean.addColumnId(DisplayItem.TITLE_MYN);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getContentEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CONTENT_ENG));
            msgBean.addColumnId(DisplayItem.CONTENT_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (!CommonUtil.isPureAscii(formBean.getPromotionsInfoManagementHeaderBean().getContentEng())) {
        // msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.CONTENT_ENG));
        // msgBean.addColumnId(DisplayItem.CONTENT_ENG);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getContentMyn())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CONTENT_MYN));
            msgBean.addColumnId(DisplayItem.CONTENT_MYN);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getPromotionsInfoManagementHeaderBean().getPublishedFromDate() == null) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.PUBLISHED_FROM_DATE));
            msgBean.addColumnId(DisplayItem.PUBLISHED_FROM_DATE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getPromotionsInfoManagementHeaderBean().getPublishedToDate() == null) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.PUBLISHED_TO_DATE));
            msgBean.addColumnId(DisplayItem.PUBLISHED_TO_DATE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getLongitude())) {
        // msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LONGITUDE));
        // msgBean.addColumnId(DisplayItem.LONGITUDE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (!formBean.getPromotionsInfoManagementHeaderBean().getLongitude().matches(VCSCommon.NUMBER_PATTERN)
                && !InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getLongitude())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.LONGITUDE));
            msgBean.addColumnId(DisplayItem.LONGITUDE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getLatitude())) {
        // msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LATITUDE));
        // msgBean.addColumnId(DisplayItem.LATITUDE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (!formBean.getPromotionsInfoManagementHeaderBean().getLatitude().matches(VCSCommon.NUMBER_PATTERN)
                && !InputChecker.isBlankOrNull(formBean.getPromotionsInfoManagementHeaderBean().getLatitude())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.LATITUDE));
            msgBean.addColumnId(DisplayItem.LATITUDE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getPromotionsInfoManagementHeaderBean().getPublishedFromDate() != null
                && formBean.getPromotionsInfoManagementHeaderBean().getPublishedToDate() != null) {
            if (formBean.getPromotionsInfoManagementHeaderBean().getPublishedFromDate()
                    .after(formBean.getPromotionsInfoManagementHeaderBean().getPublishedToDate())) {
                msgBean = new MessageBean(MessageId.ME1036, "");
                msgBean.addColumnId(DisplayItem.PUBLISHED_FROM_DATE + VCSCommon.INPUT);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        // if (formBean.getUpdateParam() == null) {
        // if (formBean.getPromotionsInfoManagementHeaderBean() != null) {
        // if (formBean.getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath() == null) {
        // msgBean = new MessageBean(MessageId.ME1025,
        // DisplayItemBean.getDisplayItemName(DisplayItem.PROMOTIONS_IMAGE));
        // msgBean.addColumnId(DisplayItem.PROMOTIONS_IMAGE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }
        // }
        //
        // } else {
        // if (formBean.getTempUploadImageFilePath() == null) {
        // msgBean = new MessageBean(MessageId.ME1025,
        // DisplayItemBean.getDisplayItemName(DisplayItem.PROMOTIONS_IMAGE));
        // msgBean.addColumnId(DisplayItem.PROMOTIONS_IMAGE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }
        // }
        return isValid;
    }

}
