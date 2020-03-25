/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class CouponManagementImageBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6570890301664797307L;
    private UploadedFile couponImage;
    private String imageFilePath;

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public UploadedFile getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(UploadedFile couponImage) {
        this.couponImage = couponImage;
    }

}
