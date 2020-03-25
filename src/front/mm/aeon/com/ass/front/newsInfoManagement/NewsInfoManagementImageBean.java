/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoManagement;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class NewsInfoManagementImageBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4840971777152050323L;

    private UploadedFile newsInfoImage;

    public UploadedFile getNewsInfoImage() {
        return newsInfoImage;
    }

    public void setNewsInfoImage(UploadedFile newsInfoImage) {
        this.newsInfoImage = newsInfoImage;
    }

}
