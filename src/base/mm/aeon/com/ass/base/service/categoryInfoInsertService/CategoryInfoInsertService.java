/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.categoryInfoInsertService;

import mm.aeon.com.ass.base.dto.categoryInfoRegister.CategoryInfoInsertReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class CategoryInfoInsertService extends AbstractService
        implements IService<CategoryInfoInsertServiceReqBean, CategorysInfoInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CategorysInfoInsertServiceResBean execute(
            CategoryInfoInsertServiceReqBean categoryInfoInsertServiceReqBean) {

        CategoryInfoInsertReqDto categoryInfoInsertReqDto = new CategoryInfoInsertReqDto();

        CategorysInfoInsertServiceResBean categoryInfoInsertServiceResBean = new CategorysInfoInsertServiceResBean();

        categoryInfoInsertReqDto.setCategoryEng(categoryInfoInsertServiceReqBean.getCategoryEng());
        categoryInfoInsertReqDto.setCategoryMyn(categoryInfoInsertServiceReqBean.getCategoryMyn());
        categoryInfoInsertReqDto.setDescription(categoryInfoInsertServiceReqBean.getDescription());
        categoryInfoInsertReqDto.setDelFlag(categoryInfoInsertServiceReqBean.getDelFlag());
        categoryInfoInsertReqDto.setCreatedBy(categoryInfoInsertServiceReqBean.getCreatedBy());
        categoryInfoInsertReqDto.setCreatedTime(categoryInfoInsertServiceReqBean.getCreatedTime());

        try {
            getDaoServiceInvoker().execute(categoryInfoInsertReqDto);
            categoryInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                categoryInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                categoryInfoInsertServiceResBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return categoryInfoInsertServiceResBean;
    }

}
