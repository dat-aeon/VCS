/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.ApplicationReportRefer;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AppReport")
public class ApplicationReferReqDto implements IReqServiceDto {
    /**
     * 
     */
    private static final long serialVersionUID = 4163869201395549293L;

    private Timestamp end_usgae_date_time;

    private Integer app_usage_id;

    public Integer getApp_usage_id() {
        return app_usage_id;
    }

    public void setApp_usage_id(Integer app_usage_id) {
        this.app_usage_id = app_usage_id;
    }

    public Timestamp getEnd_usgae_date_time() {
        return end_usgae_date_time;
    }

    public void setEnd_usgae_date_time(Timestamp end_usgae_date_time) {
        this.end_usgae_date_time = end_usgae_date_time;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

}
