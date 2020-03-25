/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.categoryInfoUpdateService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class CategoryInfoUpdateServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -4466854187628219684L;
    private Integer categoryId;
    private String categoryEng;
    private String categoryMyn;
    private String description;
    private String updatedBy;
    private Timestamp updatedTime;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryEng() {
        return categoryEng;
    }

    public void setCategoryEng(String categoryEng) {
        this.categoryEng = categoryEng;
    }

    public String getCategoryMyn() {
        return categoryMyn;
    }

    public void setCategoryMyn(String categoryMyn) {
        this.categoryMyn = categoryMyn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "CATEGORYINFOUPDATE";
    }

}
