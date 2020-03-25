/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeCount;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class StoreMaxResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -8605528039303193953L;

    private Integer max_id;

    public Integer getMax_id() {
        return max_id;
    }

    public void setMax_id(Integer max_id) {
        this.max_id = max_id;
    }

}
