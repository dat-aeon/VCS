/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminManagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdminManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4998619738834744521L;

    private Integer userId;

    private String loginId;

    private String password;

    private String confirmPassword;

    private String name;

    private Integer userTypeId;

    private Timestamp updatedTime;

    private boolean isUpdate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public AdminManagementHeaderBean copyAdminManagementHeaderBean(
            AdminManagementHeaderBean adminManagementHeaderBean) {

        this.loginId = adminManagementHeaderBean.getLoginId();
        this.name = adminManagementHeaderBean.getName();
        this.password = adminManagementHeaderBean.getPassword();
        this.userId = adminManagementHeaderBean.getUserId();
        this.updatedTime = adminManagementHeaderBean.getUpdatedTime();

        return this;
    }

}
