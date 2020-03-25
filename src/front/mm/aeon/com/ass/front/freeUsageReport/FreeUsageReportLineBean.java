/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeUsageReport;

import java.io.Serializable;
import java.util.Date;

public class FreeUsageReportLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7082720316411042671L;

    private Integer freeCustomerInfoId;

    private String phoneNo;

    private Date joinDate;

    public Integer getFreeCustomerInfoId() {
        return freeCustomerInfoId;
    }

    public void setFreeCustomerInfoId(Integer freeCustomerInfoId) {
        this.freeCustomerInfoId = freeCustomerInfoId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

}
