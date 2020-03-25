/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoManagement;

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

public class PromotionsInfoImageUploadController extends AbstractController
        implements IControllerAccessor<PromotionsInfoManagementFormBean, PromotionsInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public PromotionsInfoManagementFormBean process(PromotionsInfoManagementFormBean promotionsInfoManagementFormBean) {
        MessageBean msgBean;

        promotionsInfoManagementFormBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("News info image upload process started.", LogLevel.INFO);

        if (!checkImageDimension(promotionsInfoManagementFormBean)) {
            msgBean =
                    new MessageBean(MessageId.ME1034, DisplayItemBean.getDisplayItemName(DisplayItem.PROMOTIONS_IMAGE));
            msgBean.addColumnId(DisplayItem.PROMOTIONS_IMAGE);
            msgBean.setMessageType(MessageType.ERROR);
            promotionsInfoManagementFormBean.getMessageContainer().addMessage(msgBean);
            return promotionsInfoManagementFormBean;
        }

        try {
            UploadedFile promotionsInfoImageUploadFile =
                    promotionsInfoManagementFormBean.getPromotionsInfoManagementImageBean().getPromotionsInfoImage();

            String fileName = CommonUtil.formatByPattern(CommonUtil.getCurrentTime(), "yyyyMMddhhmmssSSS") + ".png";
            String temp = "/temp";
            String promotionsInfoImageFilePath = CommonUtil.getPromotionsInfoUploadImageFolder();
            String tempPromotionsInfoImageFilePath = temp + promotionsInfoImageFilePath + fileName;

            applicationLogger.log("Image file path " + tempPromotionsInfoImageFilePath, LogLevel.INFO);

            applicationLogger.log("Promotions info image upload process started." + tempPromotionsInfoImageFilePath,
                    LogLevel.INFO);

            applicationLogger.log("Created file." + tempPromotionsInfoImageFilePath, LogLevel.INFO);

            FileHandler.createFile(new File(FileHandler.getSystemPath() + tempPromotionsInfoImageFilePath),
                    promotionsInfoImageUploadFile.getContents());

            promotionsInfoManagementFormBean.setOldUploadImageFilePath(promotionsInfoManagementFormBean
                    .getPromotionsInfoManagementHeaderBean().getUploadedImageFilePath());

            promotionsInfoManagementFormBean.setTempUploadImageFilePath(tempPromotionsInfoImageFilePath);
            promotionsInfoManagementFormBean.setUploadImageFileName(fileName);
            promotionsInfoManagementFormBean.getPromotionsInfoManagementHeaderBean().setImagePath(fileName);
            promotionsInfoManagementFormBean.getPromotionsInfoManagementHeaderBean().setUploadedImageFilePath(fileName);

            msgBean = new MessageBean(MessageId.MI0012, DisplayItem.PROMOTIONS_IMAGE);
            msgBean.setMessageType(MessageType.INFO);
            promotionsInfoManagementFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Promotions info image upload process finished.", LogLevel.INFO);

        } catch (IOException e) {
            e.printStackTrace();
            applicationLogger.log("ERROR------" + e.getMessage(), LogLevel.INFO);
        }
        return promotionsInfoManagementFormBean;
    }

    public static boolean checkImageDimension(PromotionsInfoManagementFormBean promotionsInfoManagementFormBean) {
        boolean isValid = true;
        MessageBean msgBean;
        byte[] image = promotionsInfoManagementFormBean.getPromotionsInfoManagementImageBean().getPromotionsInfoImage()
                .getContents();
        try {
            BufferedImage bimage;
            bimage = ImageIO.read(new ByteArrayInputStream(image));
            int width = bimage.getWidth();
            int height = bimage.getHeight();
            if (width != VCSCommon.NEWSANDPROMOTIONIMAGEWIDTH | height != VCSCommon.NEWSANDPROMOTIONIMAGEHEIGHT) {
                msgBean = new MessageBean(MessageId.MI0015, promotionsInfoManagementFormBean
                        .getPromotionsInfoManagementImageBean().getPromotionsInfoImage().getFileName());
                msgBean.setMessageType(MessageType.INFO);
                promotionsInfoManagementFormBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

}
