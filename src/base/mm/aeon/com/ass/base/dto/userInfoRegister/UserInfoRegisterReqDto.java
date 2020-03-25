/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.userInfoRegister;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "UserInfo")
public class UserInfoRegisterReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 6801423902491492578L;

    private Integer userId;

    private String loginId;

    private String name;

    private Integer userTypeId;

    private Integer delFlag;

    private String createdBy;

    private Timestamp createdTime;

    private Integer allowMessagingFlag;

    private Integer allowFreeMessagingFlag;

    @Override
    public DaoType getDaoType() {
        return DaoType.INSERT;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
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
