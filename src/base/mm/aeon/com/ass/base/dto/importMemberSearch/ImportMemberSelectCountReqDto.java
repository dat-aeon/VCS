/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.importMemberSearch;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ImportMember")
public class ImportMemberSelectCountReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1608355373851066018L;

    private String name;
    private Double salaryMin;
    private Double salaryMax;
    private Date dobFrom;
    private Date dobTo;
    private String companyName;
    private String township;
    private String customerNo;
    private String nrcNo;
    private String phoneNo;
    private String memberCardId;
    private Integer membercardStatus;
    private Date createdDateFrom;
    private Date createdDateTo;

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public Date getDobFrom() {
        return dobFrom;
    }

    public void setDobFrom(Date dobFrom) {
        this.dobFrom = dobFrom;
    }

    public Date getDobTo() {
        return dobTo;
    }

    public void setDobTo(Date dobTo) {
        this.dobTo = dobTo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getNrcNo() {
        return nrcNo;
    }

    public void setNrcNo(String nrcNo) {
        this.nrcNo = nrcNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(String memberCardId) {
        this.memberCardId = memberCardId;
    }

    public Date getCreatedDateFrom() {
        return createdDateFrom;
    }

    public void setCreatedDateFrom(Date createdDateFrom) {
        this.createdDateFrom = createdDateFrom;
    }

    public Date getCreatedDateTo() {
        return createdDateTo;
    }

    public void setCreatedDateTo(Date createdDateTo) {
        this.createdDateTo = createdDateTo;
    }

    public Integer getMembercardStatus() {
        return membercardStatus;
    }

    public void setMembercardStatus(Integer membercardStatus) {
        this.membercardStatus = membercardStatus;
    }

}
