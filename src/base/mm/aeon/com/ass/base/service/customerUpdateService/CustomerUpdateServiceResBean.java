/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.customerUpdateService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;

public class CustomerUpdateServiceResBean extends AbstractServiceResBean {

    /**
     * 
     */
    private static final long serialVersionUID = 8787922966951966652L;

    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}
