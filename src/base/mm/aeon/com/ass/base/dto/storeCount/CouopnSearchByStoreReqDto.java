/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeCount;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "SMSMessage")
public class CouopnSearchByStoreReqDto implements IReqServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -9039647492854075761L;

    private Integer shop_id;

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.SELECT_LIST;
    }

}
