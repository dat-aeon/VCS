/**************************************************************************
 * $Date: 2019-02-04$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerUpdateHistoryList;

import java.io.Serializable;
import java.util.Date;

public class CustomerUpdateHistoryListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1373078522115084916L;

    private int customerInfoUpdateHistoryId;
    private String customerNo;
    private String name;
    private String description;
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
