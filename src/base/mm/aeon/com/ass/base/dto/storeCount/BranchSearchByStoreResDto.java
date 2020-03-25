/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeCount;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class BranchSearchByStoreResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -4395846781687789888L;

    private Integer del_flag;

    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

}
