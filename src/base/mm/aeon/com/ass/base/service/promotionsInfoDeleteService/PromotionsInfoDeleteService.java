/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.promotionsInfoDeleteService;

import mm.aeon.com.ass.base.dto.promotionsInfoDelete.PromotionsInfoDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class PromotionsInfoDeleteService extends AbstractService
        implements IService<PromotionsInfoDeleteServiceReqBean, PromotionsInfoDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public PromotionsInfoDeleteServiceResBean execute(
            PromotionsInfoDeleteServiceReqBean promotionsInfoDeleteServiceReqBean) {

        PromotionsInfoDeleteReqDto promotionsInfoDeleteReqDto = new PromotionsInfoDeleteReqDto();
        PromotionsInfoDeleteServiceResBean promotionsInfoDeleteServiceResBean =
                new PromotionsInfoDeleteServiceResBean();
        try {
            promotionsInfoDeleteReqDto.setPromotionsInfoId(promotionsInfoDeleteServiceReqBean.getPromotionsInfoId());
            promotionsInfoDeleteReqDto.setDelFlag(promotionsInfoDeleteServiceReqBean.getDelFlag());
            promotionsInfoDeleteReqDto.setUpdatedBy(promotionsInfoDeleteServiceReqBean.getUpdatedBy());
            promotionsInfoDeleteReqDto.setUpdatedTime(promotionsInfoDeleteServiceReqBean.getUpdatedTime());

            this.getDaoServiceInvoker().execute(promotionsInfoDeleteReqDto);

            promotionsInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                promotionsInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                promotionsInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return promotionsInfoDeleteServiceResBean;
    }

}
