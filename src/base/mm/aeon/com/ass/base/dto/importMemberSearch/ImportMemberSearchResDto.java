/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.importMemberSearch;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class ImportMemberSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8560691324402863459L;

    private Integer importCustomerId;
    private String customerNo;
    private String phoneNo;
    private String membercardId;
    private String name;
    private Double salary;
    private Date dob;
    private Integer gender;
    private String companyName;
    private String nrcNo;
    private String township;
    private Date createdDate;
    private Integer membercardStatus;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMembercardId() {
        return membercardId;
    }

    public void setMembercardId(String membercardId) {
        this.membercardId = membercardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNrcNo() {
        return nrcNo;
    }

    public void setNrcNo(String nrcNo) {
        this.nrcNo = nrcNo;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public Integer getImportCustomerId() {
        return importCustomerId;
    }

    public void setImportCustomerId(Integer importCustomerId) {
        this.importCustomerId = importCustomerId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getMembercardStatus() {
        return membercardStatus;
    }

    public void setMembercardStatus(Integer membercardStatus) {
        this.membercardStatus = membercardStatus;
    }

}
