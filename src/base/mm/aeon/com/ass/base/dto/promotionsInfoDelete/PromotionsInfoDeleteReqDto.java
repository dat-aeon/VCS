/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.promotionsInfoDelete;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "PromotionsInfo")
public class PromotionsInfoDeleteReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 5550338976265666633L;

    private Integer promotionsInfoId;
    private int delFlag;
    private String updatedBy;
    private Timestamp updatedTime;

    public Integer getPromotionsInfoId() {
        return promotionsInfoId;
    }

    public void setPromotionsInfoId(Integer promotionsInfoId) {
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
    public DaoType getDaoType() {
        return DaoType.DELETE;
    }

}
