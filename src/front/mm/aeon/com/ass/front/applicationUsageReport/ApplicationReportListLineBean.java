/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationUsageReport;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplicationReportListLineBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1723955401144696045L;

    private String name;

    private Timestamp startTime;

    private Timestamp latestUsingTime;

    private Double avgOfLogPerDay;

    private Integer app_usage_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getLatestUsingTime() {
        return latestUsingTime;
    }

    public void setLatestUsingTime(Timestamp latestUsingTime) {
        this.latestUsingTime = latestUsingTime;
    }

    public Double getAvgOfLogPerDay() {
        return avgOfLogPerDay;
    }

    public void setAvgOfLogPerDay(Double avgOfLogPerDay) {
        this.avgOfLogPerDay = avgOfLogPerDay;
    }

    public int getNoOfUsgTol() {
        return noOfUsgTol;
    }

    public void setNoOfUsgTol(int noOfUsgTol) {
        this.noOfUsgTol = noOfUsgTol;
    }

    public int getNoOfPerLoanTol() {
        return noOfPerLoanTol;
    }

    public void setNoOfPerLoanTol(int noOfPerLoanTol) {
        this.noOfPerLoanTol = noOfPerLoanTol;
    }

    public int getNoOfComConTol() {
        return noOfComConTol;
    }

    public void setNoOfComConTol(int noOfComConTol) {
        this.noOfComConTol = noOfComConTol;
    }

    private String customer_no;

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public Integer getApp_usage_id() {
        return app_usage_id;
    }

    public void setApp_usage_id(Integer app_usage_id) {
        this.app_usage_id = app_usage_id;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    private int noOfUsgTol;

    private int noOfPerLoanTol;

    private int noOfComConTol;

    private int reportId;

    private String os_type;

    public String getOs_type() {
        return os_type;
    }

    public void setOs_type(String os_type) {
        this.os_type = os_type;
    }
}
