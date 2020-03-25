/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoManagement;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.primefaces.model.UploadedFile;

import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class NewsInfoImageUploadController extends AbstractController
        implements IControllerAccessor<NewsInfoManagementFormBean, NewsInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public NewsInfoManagementFormBean process(NewsInfoManagementFormBean newsInfoManagementFormBean) {
        MessageBean msgBean;

        newsInfoManagementFormBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("News info image upload process started.", LogLevel.INFO);

        if (!checkImageDimension(newsInfoManagementFormBean)) {
            msgBean = new MessageBean(MessageId.ME1034, DisplayItemBean.getDisplayItemName(DisplayItem.NEWS_IMAGE));
            msgBean.addColumnId(DisplayItem.NEWS_IMAGE);
            msgBean.setMessageType(MessageType.ERROR);
            newsInfoManagementFormBean.getMessageContainer().addMessage(msgBean);
            return newsInfoManagementFormBean;
        }

        try {
            UploadedFile newsInfoImageUploadFile =
                    newsInfoManagementFormBean.getNewsInfoManagementImageBean().getNewsInfoImage();

            String fileName = CommonUtil.formatByPattern(CommonUtil.getCurrentTime(), "yyyyMMddhhmmssSSS") + ".png";
            String temp = "/temp";
            String newsInfoImageFilePath = CommonUtil.getNewsInfoUploadImageFolder();
            String tempNewsInfoImageFilePath = temp + newsInfoImageFilePath + fileName;

            applicationLogger.log("Image file path " + tempNewsInfoImageFilePath, LogLevel.INFO);

            applicationLogger.log("News info image upload process started." + tempNewsInfoImageFilePath, LogLevel.INFO);

            applicationLogger.log("Created file." + tempNewsInfoImageFilePath, LogLevel.INFO);

            FileHandler.createFile(new File(FileHandler.getSystemPath() + tempNewsInfoImageFilePath),
                    newsInfoImageUploadFile.getContents());

            newsInfoManagementFormBean.setOldUploadImageFilePath(
                    newsInfoManagementFormBean.getNewsInfoManagementHeaderBean().getUploadedImageFilePath());

            newsInfoManagementFormBean.setTempUploadImageFilePath(tempNewsInfoImageFilePath);
            newsInfoManagementFormBean.setUploadImageFileName(fileName);
            newsInfoManagementFormBean.getNewsInfoManagementHeaderBean().setImagePath(fileName);
            newsInfoManagementFormBean.getNewsInfoManagementHeaderBean().setUploadedImageFilePath(fileName);

            msgBean = new MessageBean(MessageId.MI0012, DisplayItem.NEWS_IMAGE);
            msgBean.setMessageType(MessageType.INFO);
            newsInfoManagementFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("News info image upload process finished.", LogLevel.INFO);

        } catch (IOException e) {
            e.printStackTrace();
            applicationLogger.log("ERROR------" + e.getMessage(), LogLevel.INFO);
        }
        return newsInfoManagementFormBean;
    }

    public static boolean checkImageDimension(NewsInfoManagementFormBean newsInfoManagementFormBean) {
        boolean isValid = true;
        MessageBean msgBean;
        byte[] image = newsInfoManagementFormBean.getNewsInfoManagementImageBean().getNewsInfoImage().getContents();
        try {
            BufferedImage bimage;
            bimage = ImageIO.read(new ByteArrayInputStream(image));
            int width = bimage.getWidth();
            int height = bimage.getHeight();
            if (width != VCSCommon.NEWSANDPROMOTIONIMAGEWIDTH | height != VCSCommon.NEWSANDPROMOTIONIMAGEHEIGHT) {
                msgBean = new MessageBean(MessageId.MI0015,
                        newsInfoManagementFormBean.getNewsInfoManagementImageBean().getNewsInfoImage().getFileName());
                msgBean.setMessageType(MessageType.INFO);
                newsInfoManagementFormBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

}
