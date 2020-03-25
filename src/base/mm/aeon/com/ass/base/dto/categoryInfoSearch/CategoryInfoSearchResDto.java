/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.categoryInfoSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CategoryInfoSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 4430319299892516331L;
    private Integer categoryId;
    private String categoryEng;
    private String categoryMyn;
    private String description;
    private int delFlag;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdTime;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

}
