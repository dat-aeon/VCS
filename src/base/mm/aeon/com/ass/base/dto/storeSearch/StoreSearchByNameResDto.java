/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class StoreSearchByNameResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = 5744301707326433660L;

    private Integer store_id;

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

}
