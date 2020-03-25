/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.ApplicationReportSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AppReport")
public class ApplicationSearchReqDto implements IReqServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7522982171910000132L;

    private String customer_no;

    private String name;

    private Timestamp rptStrFrom;

    private Timestamp rptStrTo;

    private Timestamp lstUseFrom;

    private Timestamp lstUseTo;

    private String osType;

    private Integer limit;

    private Integer offset;

    private String sortField;

    private String sortOrder;

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
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

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
