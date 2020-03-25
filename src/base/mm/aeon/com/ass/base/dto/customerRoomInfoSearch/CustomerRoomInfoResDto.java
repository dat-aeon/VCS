/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerRoomInfoSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerRoomInfoResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 202421123748639904L;
    private Integer custRoomId;
    private Integer customerId;

    public Integer getCustRoomId() {
        return custRoomId;
    }

    public void setCustRoomId(Integer custRoomId) {
        this.custRoomId = custRoomId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}
