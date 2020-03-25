/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.promotionsInfoDeleteService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class PromotionsInfoDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6286005356358451257L;

    private int promotionsInfoId;
    private int delFlag;
    private String updatedBy;
    private Timestamp updatedTime;

    public int getPromotionsInfoId() {
        return promotionsInfoId;
    }

    public void setPromotionsInfoId(int promotionsInfoId) {
        this.promotionsInfoId = promotionsInfoId;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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
        return "PROMOTIONSINFODELETE";
    }

}
