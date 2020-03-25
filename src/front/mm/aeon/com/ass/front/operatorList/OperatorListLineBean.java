/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import java.io.Serializable;
import java.sql.Timestamp;

public class OperatorListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1734777366621170975L;

    private Integer userId;

    private String userName;

    private String userLoginId;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
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
