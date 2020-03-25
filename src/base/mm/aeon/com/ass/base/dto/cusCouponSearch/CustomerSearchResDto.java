/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.cusCouponSearch;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerSearchResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = 399392341099155358L;

    private String cusotmer_no;

    private String cusotmer_name;

    private Integer customer_id;

    private String phoneNo;

    private String township;

    private Date dob;

    private String nrcNo;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNrcNo() {
        return nrcNo;
    }

    public void setNrcNo(String nrcNo) {
        this.nrcNo = nrcNo;
    }

    public String getCusotmer_no() {
        return cusotmer_no;
    }

    public void setCusotmer_no(String cusotmer_no) {
        this.cusotmer_no = cusotmer_no;
    }

    public String getCusotmer_name() {
        return cusotmer_name;
    }

    public void setCusotmer_name(String cusotmer_name) {
        this.cusotmer_name = cusotmer_name;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

}
