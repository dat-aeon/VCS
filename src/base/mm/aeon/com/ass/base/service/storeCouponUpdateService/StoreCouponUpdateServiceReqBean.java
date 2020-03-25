/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeCouponUpdateService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class StoreCouponUpdateServiceReqBean extends AbstractServiceReqBean {
    /**
     * 
     */
    private static final long serialVersionUID = -5169513205359208518L;

    private int branch_id;

    private String coupon_password;

    private Timestamp updatedTime;

    private String updated_by;

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getCoupon_password() {
        return coupon_password;
    }

    public void setCoupon_password(String coupon_password) {
        this.coupon_password = coupon_password;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    @Override
    public String getServiceId() {
        return "STORECOUPONUPDATE";
    }

}
