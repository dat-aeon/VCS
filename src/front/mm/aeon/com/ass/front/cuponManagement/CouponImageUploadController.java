/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

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

public class CouponImageUploadController extends AbstractController
        implements IControllerAccessor<CouponManagementFormBean, CouponManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CouponManagementFormBean process(CouponManagementFormBean formBean) {
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("Coupon Image Upload Process Stared.", LogLevel.INFO);

        if (!checkImageDimension(formBean)) {
            msgBean = new MessageBean(MessageId.ME1032, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_IMAGE));
            msgBean.addColumnId(DisplayItem.COUPON_IMAGE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            return formBean;
        }

        try {
            UploadedFile couponImageUploadFile = formBean.getCouponManagementImageBean().getCouponImage();

            String fileName = CommonUtil.formatByPattern(CommonUtil.getCurrentTime(), "yyyyMMddhhmmssSSS") + ".png";
            String temp = "/temp";
            String couponImageFilePath = CommonUtil.getCouponUploadImageFolder();
            String tempCouponImageFilePath = temp + couponImageFilePath + fileName;

            applicationLogger.log("Image file path " + tempCouponImageFilePath, LogLevel.INFO);

            applicationLogger.log("Coupon Image Upload Process Started." + tempCouponImageFilePath, LogLevel.INFO);

            applicationLogger.log("Created File." + tempCouponImageFilePath, LogLevel.INFO);

            FileHandler.createFile(new File(FileHandler.getSystemPath() + tempCouponImageFilePath),
                    couponImageUploadFile.getContents());

            formBean.setOldUploadImageFilePath(formBean.getCouponManagementHeaderBean().getUploadedImageFilePath());
            formBean.setTempUploadImageFilePath(tempCouponImageFilePath);
            formBean.setUploadImageFileName(fileName);
            formBean.getCouponManagementImageBean().setImageFilePath(fileName);
            formBean.getCouponManagementHeaderBean().setUploadedImageFilePath(fileName);

            msgBean = new MessageBean(MessageId.MI0012, DisplayItem.COUPON_IMAGE);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Coupon Image Upload process finished.", LogLevel.INFO);

        } catch (IOException e) {
            e.printStackTrace();
            applicationLogger.log("ERROR------" + e.getMessage(), LogLevel.INFO);
        }
        return formBean;
    }

    public static boolean checkImageDimension(CouponManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;
        byte[] image = formBean.getCouponManagementImageBean().getCouponImage().getContents();
        try {
            BufferedImage bimage;
            bimage = ImageIO.read(new ByteArrayInputStream(image));
            int width = bimage.getWidth();
            int height = bimage.getHeight();
            if (width != VCSCommon.IMAGEWIDTH | height != VCSCommon.IMAGEHEIGHT) {
                msgBean = new MessageBean(MessageId.MI0015,
                        formBean.getCouponManagementImageBean().getCouponImage().getFileName());
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

}
