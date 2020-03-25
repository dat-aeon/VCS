/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.freeCustomerRoomInfoSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class FreeCustomerRoomInfoResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 202421123748639904L;
    private Integer freeCustRoomId;
    private Integer freeCustomerId;

    public Integer getFreeCustRoomId() {
        return freeCustRoomId;
    }

    public Integer getFreeCustomerId() {
        return freeCustomerId;
    }

    public void setFreeCustRoomId(Integer freeCustRoomId) {
        this.freeCustRoomId = freeCustRoomId;
    }

    public void setFreeCustomerId(Integer freeCustomerId) {
        this.freeCustomerId = freeCustomerId;
    }

}
