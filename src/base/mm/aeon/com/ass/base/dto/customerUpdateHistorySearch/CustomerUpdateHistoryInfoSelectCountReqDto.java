/**************************************************************************
 * $Date : 2019-07-23$
 * $Author : Thiha Htet Zaw $
 * $Rev : $
 * Copyright (c) 2019 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerUpdateHistorySearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CustomerInfoUpdateHistory")
public class CustomerUpdateHistoryInfoSelectCountReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5376898487674767104L;

    private String customerName;

    private String customerNo;

    private Timestamp updatedTimeFrom;

    private Timestamp updatedTimeTo;

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

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

    public Timestamp getUpdatedTimeFrom() {
        return updatedTimeFrom;
    }

    public void setUpdatedTimeFrom(Timestamp updatedTimeFrom) {
        this.updatedTimeFrom = updatedTimeFrom;
    }

    public Timestamp getUpdatedTimeTo() {
        return updatedTimeTo;
    }

    public void setUpdatedTimeTo(Timestamp updatedTimeTo) {
        this.updatedTimeTo = updatedTimeTo;
    }

}
