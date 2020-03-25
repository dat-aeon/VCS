/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.ApplicationReportSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class ApplicationSearchResDto implements IResServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = -6382621224350722250L;

    private String customer_no;

    private String name;

    private String os_type;

    private Timestamp start_time;

    private Timestamp lst_used_time;

    private double avgOfLogPerDay;

    private int noOfUsgTol;

    private Integer app_usage_id;

    public Integer getApp_usage_id() {
        return app_usage_id;
    }

    public void setApp_usage_id(Integer app_usage_id) {
        this.app_usage_id = app_usage_id;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs_type() {
        return os_type;
    }

    public void setOs_type(String os_type) {
        this.os_type = os_type;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getLst_used_time() {
        return lst_used_time;
    }

    public void setLst_used_time(Timestamp lst_used_time) {
        this.lst_used_time = lst_used_time;
    }

    public double getAvgOfLogPerDay() {
        return avgOfLogPerDay;
    }

    public void setAvgOfLogPerDay(double avgOfLogPerDay) {
        this.avgOfLogPerDay = avgOfLogPerDay;
    }

    public int getNoOfUsgTol() {
        return noOfUsgTol;
    }

    public void setNoOfUsgTol(int noOfUsgTol) {
        this.noOfUsgTol = noOfUsgTol;
    }

}
