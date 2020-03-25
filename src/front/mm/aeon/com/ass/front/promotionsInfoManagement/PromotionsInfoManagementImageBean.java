/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoManagement;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class PromotionsInfoManagementImageBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -36145096726776886L;
    private UploadedFile promotionsInfoImage;

    public UploadedFile getPromotionsInfoImage() {
        return promotionsInfoImage;
    }

    public void setPromotionsInfoImage(UploadedFile promotionsInfoImage) {
        this.promotionsInfoImage = promotionsInfoImage;
    }

}
