/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.companyInfoUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.companyInfoSelectForUpdate.CompanyInfoSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.companyInfoSelectForUpdate.CompanyInfoSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.companyInfoUpdate.CompanyInfoUpdateReqDto;
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

public class CompanyInfoUpdateService extends AbstractService
        implements IService<CompanyInfoUpdateServiceReqBean, CompanyInfoUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public CompanyInfoUpdateServiceResBean execute(CompanyInfoUpdateServiceReqBean reqBean) {

        CompanyInfoUpdateServiceResBean resBean = new CompanyInfoUpdateServiceResBean();
        CompanyInfoSelectForUpdateReqDto selectForUpdateReqDto = new CompanyInfoSelectForUpdateReqDto();
        selectForUpdateReqDto.setInfoId(reqBean.getInfoId());

        try {
            CompanyInfoSelectForUpdateResDto selectForUpdateResDto =
                    (CompanyInfoSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                CompanyInfoUpdateReqDto updateReqDto = new CompanyInfoUpdateReqDto();
                updateReqDto.setInfoId(reqBean.getInfoId());
                updateReqDto.setAddress_eng(reqBean.getAddress_eng());
                updateReqDto.setAddress_mya(reqBean.getAddress_mya());
                updateReqDto.setHotLine(reqBean.getHotLine());
                updateReqDto.setWebAddress(reqBean.getWebAddress());
                updateReqDto.setSocialMedia(reqBean.getSocialMedia());
                updateReqDto.setAboutCompanyEng(reqBean.getAboutCompanyEng());
                updateReqDto.setAboutCompanyMya(reqBean.getAboutCompanyMya());
                updateReqDto.setChatAutoReplyMessage(reqBean.getChatAutoReplyMessage());
                updateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
                updateReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
                /*
                 * updateReqDto.setValidStatus(reqBean.getValidStatus());
                 * updateReqDto.setValidStatusToggle(reqBean.isValidStatusToggle());
                 */

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
