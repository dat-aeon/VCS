/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.categoryInfoDeleteService;

import mm.aeon.com.ass.base.dto.categoryInfoDelete.CategoryInfoDeleteReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.LogLevel;

public class CategoryInfoDeleteService extends AbstractService
        implements IService<CategoryInfoDeleteServiceReqBean, CategoryInfoDeleteServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CategoryInfoDeleteServiceResBean execute(CategoryInfoDeleteServiceReqBean categoryInfoDeleteServiceReqBean) {

        CategoryInfoDeleteReqDto CategoryInfoDeleteReqDto = new CategoryInfoDeleteReqDto();
        CategoryInfoDeleteServiceResBean categoryInfoDeleteServiceResBean = new CategoryInfoDeleteServiceResBean();
        try {
            CategoryInfoDeleteReqDto.setCategoryId(categoryInfoDeleteServiceReqBean.getCategoryId());
            CategoryInfoDeleteReqDto.setDelFlag(categoryInfoDeleteServiceReqBean.getDelFlag());
            CategoryInfoDeleteReqDto.setUpdatedBy(categoryInfoDeleteServiceReqBean.getUpdatedBy());
            CategoryInfoDeleteReqDto.setUpdatedTime(categoryInfoDeleteServiceReqBean.getUpdatedTime());

            this.getDaoServiceInvoker().execute(CategoryInfoDeleteReqDto);

            categoryInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (PrestoDBException e) {
            if (e instanceof PhysicalRecordLockedException) {
                categoryInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                categoryInfoDeleteServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return categoryInfoDeleteServiceResBean;
    }

}
