/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class StoreCouponDataBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1676940075188103628L;

    private Integer branch_id;

    private String branch_name;

    private String coupon_password;

    private Integer store_id;

    private String branch_code;

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

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StoreCouponDataBean other = (StoreCouponDataBean) obj;
        if (branch_id == null) {
            if (other.branch_id != null)
                return false;
        } else if (!branch_id.equals(other.branch_id))
            return false;
        if (branch_code == null) {
            if (other.branch_code != null)
                return false;
        } else if (!branch_code.equals(other.branch_code))
            return false;
        return true;
    }

    /**
     * Compare employee select dto
     *
     */
    public int compareTo(StoreCouponDataBean storeCouponDataBean) {
        Integer branchId = storeCouponDataBean.getBranch_id();

        // ascending order
        return storeCouponDataBean.getBranch_id().compareTo(branchId);
    }

    public static Comparator<StoreCouponDataBean> branchIdComparator = new Comparator<StoreCouponDataBean>() {
        public int compare(StoreCouponDataBean storeCouponDataBean1, StoreCouponDataBean storeCouponDataBean2) {
            Integer branchId1 = storeCouponDataBean1.getBranch_id();
            Integer branchId2 = storeCouponDataBean2.getBranch_id();
            // ascending order
            return branchId1.compareTo(branchId2);
        }
    };

}
