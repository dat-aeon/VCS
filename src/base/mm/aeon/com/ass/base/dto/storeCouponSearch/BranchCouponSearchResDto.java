/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.storeCouponSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class BranchCouponSearchResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -8219372934168204580L;

    private Integer branch_id;

    private String branch_name;

    private String coupon_password;

    private Integer shop_id;

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getCoupon_password() {
        return coupon_password;
    }

    public void setCoupon_password(String coupon_password) {
        this.coupon_password = coupon_password;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public String branch_code;

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }
}
