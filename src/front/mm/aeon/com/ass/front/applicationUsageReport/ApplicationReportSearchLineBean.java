/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationUsageReport;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplicationReportSearchLineBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3927718775501841221L;

    private Timestamp rptStrFrom;

    private Timestamp rptStrTo;

    private Timestamp lstUseFrom;

    private Timestamp lstUseTo;

    private String customerNo;
    
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String osType;

    public Timestamp getRptStrFrom() {
        return rptStrFrom;
    }

    public void setRptStrFrom(Timestamp rptStrFrom) {
        this.rptStrFrom = rptStrFrom;
    }

    public Timestamp getRptStrTo() {
        return rptStrTo;
    }

    public void setRptStrTo(Timestamp rptStrTo) {
        this.rptStrTo = rptStrTo;
    }

    public Timestamp getLstUseFrom() {
        return lstUseFrom;
    }

    public void setLstUseFrom(Timestamp lstUseFrom) {
        this.lstUseFrom = lstUseFrom;
    }

    public Timestamp getLstUseTo() {
        return lstUseTo;
    }

    public void setLstUseTo(Timestamp lstUseTo) {
        this.lstUseTo = lstUseTo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

}
