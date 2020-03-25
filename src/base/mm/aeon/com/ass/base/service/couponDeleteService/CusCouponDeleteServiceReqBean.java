/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.couponDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class CusCouponDeleteServiceReqBean extends AbstractServiceReqBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1368480256186521353L;

    private int coupon_id;

    private int customer_id;

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "CUSCOUPONDE";
    }

}
