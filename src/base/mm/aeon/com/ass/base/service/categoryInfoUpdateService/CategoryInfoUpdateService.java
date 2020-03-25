/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.categoryInfoUpdateService;

import mm.aeon.com.ass.base.dto.categoryInfoUpdate.CategoryInfoUpdateReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class CategoryInfoUpdateService extends AbstractService
        implements IService<CategoryInfoUpdateServiceReqBean, CategoryInfoUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CategoryInfoUpdateServiceResBean execute(CategoryInfoUpdateServiceReqBean categoryInfoUpdateServiceReqBean) {

        CategoryInfoUpdateServiceResBean categoryInfoUpdateServiceResBean = new CategoryInfoUpdateServiceResBean();

        CategoryInfoUpdateReqDto categoryInfoUpdateReqDto = new CategoryInfoUpdateReqDto();

        try {
            categoryInfoUpdateReqDto.setCategoryId(categoryInfoUpdateServiceReqBean.getCategoryId());
            categoryInfoUpdateReqDto.setCategoryEng(categoryInfoUpdateServiceReqBean.getCategoryEng());
            categoryInfoUpdateReqDto.setCategoryMyn(categoryInfoUpdateServiceReqBean.getCategoryMyn());
            categoryInfoUpdateReqDto.setDescription(categoryInfoUpdateServiceReqBean.getDescription());
            categoryInfoUpdateReqDto.setUpdatedBy(categoryInfoUpdateServiceReqBean.getUpdatedBy());
            categoryInfoUpdateReqDto.setUpdatedTime(categoryInfoUpdateServiceReqBean.getUpdatedTime());

            this.getDaoServiceInvoker().execute(categoryInfoUpdateReqDto);
        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                categoryInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
                categoryInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                categoryInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);
            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        categoryInfoUpdateServiceResBean.setServiceStatus(ServiceStatusCode.OK);

        return categoryInfoUpdateServiceResBean;
    }

}
