/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoManagement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.newsInfoInsertService.NewsInfoInsertServiceReqBean;
import mm.aeon.com.ass.base.service.newsInfoUpdateService.NewsInfoUpdateServiceReqBean;
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

public class NewsInfoManagementController extends AbstractController
        implements IControllerAccessor<NewsInfoManagementFormBean, NewsInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public NewsInfoManagementFormBean process(NewsInfoManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean msgBean;
        String serviceStatus = null;

        if (!formBean.isUpdate()) {
            applicationLogger.log("News info registration process stared.", LogLevel.INFO);

            NewsInfoInsertServiceReqBean newsInfoInsertServiceReqBean = new NewsInfoInsertServiceReqBean();
            newsInfoInsertServiceReqBean.setTitleEng(formBean.getNewsInfoManagementHeaderBean().getTitleEng());
            newsInfoInsertServiceReqBean.setTitleMyn(formBean.getNewsInfoManagementHeaderBean().getTitleMyn());
            newsInfoInsertServiceReqBean.setContentEng(formBean.getNewsInfoManagementHeaderBean().getContentEng());
            newsInfoInsertServiceReqBean.setContentMyn(formBean.getNewsInfoManagementHeaderBean().getContentMyn());
            newsInfoInsertServiceReqBean
                    .setPublishedFromDate(formBean.getNewsInfoManagementHeaderBean().getPublishedFromDate());
            newsInfoInsertServiceReqBean
                    .setPublishedToDate(formBean.getNewsInfoManagementHeaderBean().getPublishedToDate());

            if (StringUtils.isEmpty(formBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath())) {
                newsInfoInsertServiceReqBean.setImagePath(new String());
            } else {
                newsInfoInsertServiceReqBean
                        .setImagePath(formBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath());
            }

            newsInfoInsertServiceReqBean.setLongitude(formBean.getNewsInfoManagementHeaderBean().getLongitude());
            newsInfoInsertServiceReqBean.setLatitude(formBean.getNewsInfoManagementHeaderBean().getLatitude());
            newsInfoInsertServiceReqBean.setDelFlag(0);

            newsInfoInsertServiceReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
            newsInfoInsertServiceReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
            newsInfoInsertServiceReqBean.setNewsUrl(formBean.getNewsInfoManagementHeaderBean().getNewsUrl());

            this.getServiceInvoker().addRequest(newsInfoInsertServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                    File sourceFile = new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                    File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                            + CommonUtil.getNewsInfoUploadImageFolder() + formBean.getUploadImageFileName());

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
                applicationLogger.log("News info registration finished.", LogLevel.INFO);

            } else {
                setErrorMessage(formBean, serviceStatus);
            }
        } else {

            applicationLogger.log("News info update process started.", LogLevel.INFO);

            NewsInfoUpdateServiceReqBean newsInfoUpdateServiceReqBean = new NewsInfoUpdateServiceReqBean();
            newsInfoUpdateServiceReqBean.setNewsInfoId(formBean.getNewsInfoManagementHeaderBean().getNewsInfoId());
            newsInfoUpdateServiceReqBean.setTitleEng(formBean.getNewsInfoManagementHeaderBean().getTitleEng());
            newsInfoUpdateServiceReqBean.setTitleMyn(formBean.getNewsInfoManagementHeaderBean().getTitleMyn());
            newsInfoUpdateServiceReqBean.setContentEng(formBean.getNewsInfoManagementHeaderBean().getContentEng());
            newsInfoUpdateServiceReqBean.setContentMyn(formBean.getNewsInfoManagementHeaderBean().getContentMyn());
            newsInfoUpdateServiceReqBean
                    .setPublishedFromDate(formBean.getNewsInfoManagementHeaderBean().getPublishedFromDate());
            newsInfoUpdateServiceReqBean
                    .setPublishedToDate(formBean.getNewsInfoManagementHeaderBean().getPublishedToDate());

            if (StringUtils.isEmpty(formBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath())) {
                newsInfoUpdateServiceReqBean.setImagePath(new String());
            } else {
                newsInfoUpdateServiceReqBean
                        .setImagePath(formBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath());
            }

            newsInfoUpdateServiceReqBean.setLongitude(formBean.getNewsInfoManagementHeaderBean().getLongitude());
            newsInfoUpdateServiceReqBean.setLatitude(formBean.getNewsInfoManagementHeaderBean().getLatitude());
            newsInfoUpdateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
            newsInfoUpdateServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
            newsInfoUpdateServiceReqBean.setNewsUrl(formBean.getNewsInfoManagementHeaderBean().getNewsUrl());

            this.getServiceInvoker().addRequest(newsInfoUpdateServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                if (formBean.getOldUploadImageFilePath() != null) {
                    if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                        File sourceFile = new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                        File destFile = new File(
                                CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getNewsInfoUploadImageFolder()
                                        + formBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath());

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
                applicationLogger.log("News info update process finished.", LogLevel.INFO);
            } else {
                setErrorMessage(formBean, serviceStatus);
            }
        }
        return formBean;
    }

    private NewsInfoManagementFormBean setErrorMessage(NewsInfoManagementFormBean formBean, String serviceStatus) {

        MessageBean msgBean;

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, VCSCommon.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd news info data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update news info data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating news info data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating news info data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(NewsInfoManagementFormBean formBean) {
        boolean isValid = true;
        // boolean isCheckCode = false;
        MessageBean msgBean;

        if (!StringUtils.isEmpty(formBean.getNewsInfoManagementHeaderBean().getNewsUrl())) {
            try {
                URL url = new URL(formBean.getNewsInfoManagementHeaderBean().getNewsUrl());
            } catch (MalformedURLException e) {
                msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.NEWS_URL));
                msgBean.addColumnId(DisplayItem.NEWS_URL);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getTitleEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_ENG));
            msgBean.addColumnId(DisplayItem.TITLE_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (!CommonUtil.isPureAscii(formBean.getNewsInfoManagementHeaderBean().getTitleEng())) {
        // msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_ENG));
        // msgBean.addColumnId(DisplayItem.TITLE_ENG);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getTitleMyn())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TITLE_MYN));
            msgBean.addColumnId(DisplayItem.TITLE_MYN);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getContentEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CONTENT_ENG));
            msgBean.addColumnId(DisplayItem.CONTENT_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (!CommonUtil.isPureAscii(formBean.getNewsInfoManagementHeaderBean().getContentEng())) {
        // msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.CONTENT_ENG));
        // msgBean.addColumnId(DisplayItem.CONTENT_ENG);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getContentMyn())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CONTENT_MYN));
            msgBean.addColumnId(DisplayItem.CONTENT_MYN);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getNewsInfoManagementHeaderBean().getPublishedFromDate() == null) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.PUBLISHED_FROM_DATE));
            msgBean.addColumnId(DisplayItem.PUBLISHED_FROM_DATE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getNewsInfoManagementHeaderBean().getPublishedToDate() == null) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.PUBLISHED_TO_DATE));
            msgBean.addColumnId(DisplayItem.PUBLISHED_TO_DATE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getLongitude())) {
        // msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LONGITUDE));
        // msgBean.addColumnId(DisplayItem.LONGITUDE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (!formBean.getNewsInfoManagementHeaderBean().getLongitude().matches(VCSCommon.NUMBER_PATTERN)
                && !InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getLongitude())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.LONGITUDE));
            msgBean.addColumnId(DisplayItem.LONGITUDE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getLatitude())) {
        // msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LATITUDE));
        // msgBean.addColumnId(DisplayItem.LATITUDE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (!formBean.getNewsInfoManagementHeaderBean().getLatitude().matches(VCSCommon.NUMBER_PATTERN)
                && !InputChecker.isBlankOrNull(formBean.getNewsInfoManagementHeaderBean().getLatitude())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.LATITUDE));
            msgBean.addColumnId(DisplayItem.LATITUDE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getNewsInfoManagementHeaderBean().getPublishedFromDate() != null
                && formBean.getNewsInfoManagementHeaderBean().getPublishedToDate() != null) {
            if (formBean.getNewsInfoManagementHeaderBean().getPublishedFromDate()
                    .after(formBean.getNewsInfoManagementHeaderBean().getPublishedToDate())) {
                msgBean = new MessageBean(MessageId.ME1036, "");
                msgBean.addColumnId(DisplayItem.PUBLISHED_FROM_DATE + VCSCommon.INPUT);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        // if (formBean.getUpdateParam() == null) {
        // if (formBean.getNewsInfoManagementHeaderBean() != null) {
        // if (formBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath() == null) {
        // msgBean = new MessageBean(MessageId.ME1025,
        // DisplayItemBean.getDisplayItemName(DisplayItem.NEWS_IMAGE));
        // msgBean.addColumnId(DisplayItem.NEWS_IMAGE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }
        // }
        //
        // } else {
        // if (formBean.getTempUploadImageFilePath() == null) {
        // msgBean = new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.NEWS_IMAGE));
        // msgBean.addColumnId(DisplayItem.NEWS_IMAGE);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }
        // }
        return isValid;
    }

}
