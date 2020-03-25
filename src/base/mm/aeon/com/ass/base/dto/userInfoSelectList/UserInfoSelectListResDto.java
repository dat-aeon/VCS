/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.userInfoSelectList;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class UserInfoSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8085767478555013466L;

    private Integer userId;

    private String loginId;

    private String name;

    private Integer userTypeId;

    private Timestamp updatedTime;

    private Integer allowMessagingFlag;

    private Integer allowFreeMessagingFlag;

    public Integer getAllowMessagingFlag() {
        return allowMessagingFlag;
    }

    public void setAllowMessagingFlag(Integer allowMessagingFlag) {
        this.allowMessagingFlag = allowMessagingFlag;
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

    public Integer getAllowFreeMessagingFlag() {
        return allowFreeMessagingFlag;
    }

    public void setAllowFreeMessagingFlag(Integer allowFreeMessagingFlag) {
        this.allowFreeMessagingFlag = allowFreeMessagingFlag;
    }

}
