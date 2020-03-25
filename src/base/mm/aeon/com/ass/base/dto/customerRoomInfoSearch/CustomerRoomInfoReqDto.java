/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerRoomInfoSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CustomerRoomInfo")
public class CustomerRoomInfoReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 4776546844431438527L;

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
