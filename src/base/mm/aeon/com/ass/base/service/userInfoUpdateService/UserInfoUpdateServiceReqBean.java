/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.userInfoUpdateService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class UserInfoUpdateServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -3883054262804630617L;

    private Integer userId;

    private Integer userTypeId;

    private String loginId;

    private Integer delFlag;

    private String password;

    private String name;

    private String updatedBy;

    private Timestamp updatedTime;

    private Integer allowMessagingFlag;

    private Integer allowFreeMessagingFlag;

    @Override
    public String getServiceId() {
        return "USERINFOSU";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getAllowMessagingFlag() {
        return allowMessagingFlag;
    }

    public void setAllowMessagingFlag(Integer allowMessagingFlag) {
        this.allowMessagingFlag = allowMessagingFlag;
    }

    public Integer getAllowFreeMessagingFlag() {
        return allowFreeMessagingFlag;
    }

    public void setAllowFreeMessagingFlag(Integer allowFreeMessagingFlag) {
        this.allowFreeMessagingFlag = allowFreeMessagingFlag;
    }

}
