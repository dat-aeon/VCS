/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.Serializable;
import java.util.ArrayList;

import org.primefaces.model.UploadedFile;

public class CouponManagementFileBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6309858335129556888L;

    private UploadedFile importFile;

    public UploadedFile getImportFile() {
        return importFile;
    }

    public void setImportFile(UploadedFile importFile) {
        this.importFile = importFile;
    }
    private ArrayList<CustomerUploadBean> customerUploadBeanList;

    public ArrayList<CustomerUploadBean> getCustomerUploadBeanList() {
        return customerUploadBeanList;
    }

    public void setCustomerUploadBeanList(ArrayList<CustomerUploadBean> customerUploadBeanList) {
        this.customerUploadBeanList = customerUploadBeanList;
    }
}
