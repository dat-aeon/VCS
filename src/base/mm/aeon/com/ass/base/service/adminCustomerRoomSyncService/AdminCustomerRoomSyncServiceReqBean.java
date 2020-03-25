/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.adminCustomerRoomSyncService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AdminCustomerRoomSyncServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -4169794649391715455L;

    private Integer adCustRoomId;
    private Integer custRoomId;
    private Integer userId;
    private int finishFlag;
    private Timestamp createdTime;

    public Integer getAdCustRoomId() {
        return adCustRoomId;
    }

    public void setAdCustRoomId(Integer adCustRoomId) {
        this.adCustRoomId = adCustRoomId;
    }

    public Integer getCustRoomId() {
        return custRoomId;
    }

    public void setCustRoomId(Integer custRoomId) {
        this.custRoomId = custRoomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(int finishFlag) {
        this.finishFlag = finishFlag;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String getServiceId() {
        return "ADMINCUSTOMERROOMSYNC";
    }

}
