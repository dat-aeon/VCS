/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import java.io.Serializable;
import java.util.Date;

public class ImportMemberListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7671329201873425176L;

    private Integer importCustomerId;
    private String customerNo;
    private String phoneNo;
    private String membercardId;
    private String name;
    private Double salary;
    private String strSalary;
    private Date dob;
    private String gender;
    private String companyName;
    private String nrcNo;
    private String township;
    private Date createdDate;
    private Integer membercardStatus;
    private String membercardStatusStr;

    public Integer getImportCustomerId() {
        return importCustomerId;
    }

    public void setImportCustomerId(Integer importCustomerId) {
        this.importCustomerId = importCustomerId;
    }

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

    public String getStrSalary() {
        return strSalary;
    }

    public void setStrSalary(String strSalary) {
        this.strSalary = strSalary;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public String getMembercardStatusStr() {
        return membercardStatusStr;
    }

    public void setMembercardStatusStr(String membercardStatusStr) {
        this.membercardStatusStr = membercardStatusStr;
    }
}
