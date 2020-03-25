/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.securityUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.securitySelectForUpdate.SecuritySelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.securitySelectForUpdate.SecuritySelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.securityUpdate.SecurityUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
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

public class SecurityUpdateService extends AbstractService
        implements IService<SecurityUpdateServiceReqBean, SecurityUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public SecurityUpdateServiceResBean execute(SecurityUpdateServiceReqBean reqBean) {

        SecurityUpdateServiceResBean resBean = new SecurityUpdateServiceResBean();
        SecuritySelectForUpdateReqDto selectForUpdateReqDto = new SecuritySelectForUpdateReqDto();
        selectForUpdateReqDto.setSecId(reqBean.getSecId());

        try {
            SecuritySelectForUpdateResDto selectForUpdateResDto =
                    (SecuritySelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                SecurityUpdateReqDto updateReqDto = new SecurityUpdateReqDto();
                updateReqDto.setSecId(reqBean.getSecId());
                updateReqDto.setQuestionMyan(reqBean.getQuestionMyan());
                updateReqDto.setQuestionEng(reqBean.getQuestionEng());
                updateReqDto.setDelFlag(reqBean.getDelFlag());
                updateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
                updateReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                this.getDaoServiceInvoker().execute(updateReqDto);
                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
                resBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                resBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);
            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return resBean;
    }

}
