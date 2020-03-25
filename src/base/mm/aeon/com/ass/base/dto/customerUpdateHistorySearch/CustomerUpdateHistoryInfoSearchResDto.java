/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerUpdateHistorySearch;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerUpdateHistoryInfoSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 4430319299892516331L;
    private int customerInfoUpdateHistoryId;
    private String description;
    private String customerNo;
    private String name;
    private String updatedBy;
    private Date updatedTime;

    public int getCustomerInfoUpdateHistoryId() {
        return customerInfoUpdateHistoryId;
    }

    public void setCustomerInfoUpdateHistoryId(int customerInfoUpdateHistoryId) {
        this.customerInfoUpdateHistoryId = customerInfoUpdateHistoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
