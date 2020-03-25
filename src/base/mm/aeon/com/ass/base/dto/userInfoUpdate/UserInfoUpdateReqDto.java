/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.userInfoUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "UserInfo")
public class UserInfoUpdateReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = -748406496640180341L;

    private Integer userId;

    private String loginId;

    private String name;

    private Integer delFlag;

    private String updatedBy;

    private Timestamp updatedTime;

    private Integer allowMessagingFlag;

    private Integer allowFreeMessagingFlag;

    @Override
    public DaoType getDaoType() {
        return DaoType.UPDATE;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public void setUserId(Integer userId) {
        this.userId = userId;
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
