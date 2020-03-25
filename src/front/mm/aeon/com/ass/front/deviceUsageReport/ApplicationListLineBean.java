/**************************************************************************
 * $Date: 2019-02-12$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.deviceUsageReport;

import java.io.Serializable;

public class ApplicationListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1373078522115084916L;

    private String customerNo;
    private String customerName;
    private String osType;
    private String model;
    private String manufacture;
    private String sdk;
    private String osVersion;
    private Integer appUsageId;
    private String resolution;

    public Integer getAppUsageId() {
        return appUsageId;
    }

    public void setAppUsageId(Integer appUsageId) {
        this.appUsageId = appUsageId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

}
