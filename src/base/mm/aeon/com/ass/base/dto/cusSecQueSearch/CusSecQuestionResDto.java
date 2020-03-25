/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.cusSecQueSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CusSecQuestionResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -2828256497604180164L;
    private int del_flag;

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

}
