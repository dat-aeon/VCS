/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.freeAdminCustomerRoomSync;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "FreeAdminCustomerRoomSync")
public class FreeAdminCustomerRoomSyncReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 3056436361859481386L;

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
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.INSERT;
    }

}
