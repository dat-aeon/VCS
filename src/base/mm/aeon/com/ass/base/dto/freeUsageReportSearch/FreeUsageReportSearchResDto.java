/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.freeUsageReportSearch;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class FreeUsageReportSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1700741043921707147L;

    private Integer freeCustomerInfoId;

    private String phoneNo;

    private Date joinDate;

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

    public Integer getFreeCustomerInfoId() {
        return freeCustomerInfoId;
    }

    public void setFreeCustomerInfoId(Integer freeCustomerInfoId) {
        this.freeCustomerInfoId = freeCustomerInfoId;
    }

}
