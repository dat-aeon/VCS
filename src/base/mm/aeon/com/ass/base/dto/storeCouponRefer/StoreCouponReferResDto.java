/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeCouponRefer;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class StoreCouponReferResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7322498430793387666L;

    private String coupon_password;

    private Integer branch_id;

    public String getCoupon_password() {
        return coupon_password;
    }

    public void setCoupon_password(String coupon_password) {
        this.coupon_password = coupon_password;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    private Integer store_id;
}
