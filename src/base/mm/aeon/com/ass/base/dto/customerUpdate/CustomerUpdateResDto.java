/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerUpdate;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerUpdateResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 7333216919910556282L;

    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}
