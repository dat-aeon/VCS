/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerSearchByCoupon;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerSearchByCouponResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -8133167864720214401L;
    
    private int customer_id;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

}
