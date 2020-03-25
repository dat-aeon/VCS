/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerUpdateHistorySearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CustomerInfoUpdateHistory")
public class CustomerUpdateHistoryInfoSearchReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3803676498382125285L;

    private Integer limit;

    private Integer offset;

    private String sortField;

    private String sortOrder;

    private int customerInfoUpdateHistoryId;

    private String description;

    private String updatedBy;

    private String updatedTime;

    private Timestamp updatedTimeFrom;

    private Timestamp updatedTimeTo;

    private String customerName;

    private String customerNo;

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

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
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

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
