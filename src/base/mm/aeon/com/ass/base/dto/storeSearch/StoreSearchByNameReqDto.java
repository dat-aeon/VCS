/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "StoreInfo")
public class StoreSearchByNameReqDto implements IReqServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -8223317948889641196L;
    private String store_name;
    
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.REFER;
    }

}
