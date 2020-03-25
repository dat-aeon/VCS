/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerUpdateHistoryList;

import java.io.Serializable;
import java.util.Date;

public class CustomerUpdateHistoryListHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 42293481942974157L;

    private int customerInfoUpdateHistoryId;
    private String customerName;
    private String customerNo;
    private String description;
    private String updatedBy;
    private String updatedTime;
    private Date updatedTimeFrom;
    private Date updatedTimeTo;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

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

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTimeFrom() {
        return updatedTimeFrom;
    }

    public void setUpdatedTimeFrom(Date updatedTimeFrom) {
        this.updatedTimeFrom = updatedTimeFrom;
    }

    public Date getUpdatedTimeTo() {
        return updatedTimeTo;
    }

    public void setUpdatedTimeTo(Date updatedTimeTo) {
        this.updatedTimeTo = updatedTimeTo;
    }

}
