/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.model.SelectItem;

public class CustomerManagementLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4736862039127252049L;

    private String companyName;
    private String createdBy;
    private Date createdTime;
    private Integer customerId;
    private String customerNo;
    private Integer customerTypeId;
    private Integer delFlag;
    private Date dob;
    private Integer gender;
    private Date joinDate;
    private String name;
    private String nrcNo;
    private String phoneNo;
    private Double salary;
    private String township;
    private String updatedBy;
    private Date updatedTime;
    private Integer userTypeId;
    private boolean member;
    private boolean nonMember = false;

    private ArrayList<SelectItem> customerTypeSelectItemList;
    private ArrayList<SelectItem> genderSelectItemList;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
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

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public boolean getMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public boolean getNonMember() {
        return nonMember;
    }

    public void setNonMember(boolean nonMember) {
        this.nonMember = nonMember;
    }

    public ArrayList<SelectItem> getCustomerTypeSelectItemList() {
        return customerTypeSelectItemList;
    }

    public void setCustomerTypeSelectItemList(ArrayList<SelectItem> customerTypeSelectItemList) {
        this.customerTypeSelectItemList = customerTypeSelectItemList;
    }

    public ArrayList<SelectItem> getGenderSelectItemList() {
        return genderSelectItemList;
    }

    public void setGenderSelectItemList(ArrayList<SelectItem> genderSelectItemList) {
        this.genderSelectItemList = genderSelectItemList;
    }

    public CustomerManagementLineBean copyCustomerManagementLineBean(
            CustomerManagementLineBean customerManagementLineBean) {

        this.member = customerManagementLineBean.getCustomerId().equals(1);
        this.companyName = customerManagementLineBean.getCompanyName();
        this.name = customerManagementLineBean.getName();
        this.createdBy = customerManagementLineBean.getCreatedBy();
        this.createdTime = customerManagementLineBean.getCreatedTime();
        this.customerId = customerManagementLineBean.getCustomerId();
        this.customerNo = customerManagementLineBean.getCustomerNo();
        this.customerTypeId = customerManagementLineBean.getCustomerTypeId();
        this.delFlag = customerManagementLineBean.getDelFlag();
        this.dob = customerManagementLineBean.getDob();
        this.gender = customerManagementLineBean.getGender();
        this.joinDate = customerManagementLineBean.getJoinDate();
        this.nrcNo = customerManagementLineBean.getNrcNo();
        this.phoneNo = customerManagementLineBean.getPhoneNo();
        this.salary = customerManagementLineBean.getSalary();
        this.township = customerManagementLineBean.getTownship();
        this.updatedBy = customerManagementLineBean.getUpdatedBy();
        this.updatedTime = customerManagementLineBean.getUpdatedTime();
        this.customerTypeSelectItemList =
                new ArrayList<SelectItem>(customerManagementLineBean.customerTypeSelectItemList);
        this.genderSelectItemList = new ArrayList<SelectItem>(customerManagementLineBean.genderSelectItemList);
        this.member = customerManagementLineBean.getMember();

        return this;
    }

}
