/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.faqCount;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CategorySearchByFaqResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -2501244970486790490L;

    private Integer del_flag;

    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

}
