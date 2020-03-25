/**************************************************************************
 * $Date : 2019-01-23$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.faqUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.faqSelectUpdate.FaqSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.faqSelectUpdate.FaqSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.faqUpdate.FaqUpdateReqDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
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

public class FaqUpdateService extends AbstractService
        implements IService<FaqUpdateServiceReqBean, FaqUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public FaqUpdateServiceResBean execute(FaqUpdateServiceReqBean reqBean) {

        FaqSelectForUpdateReqDto selectForUpdateReqDto = new FaqSelectForUpdateReqDto();
        FaqUpdateServiceResBean resBean = new FaqUpdateServiceResBean();
        selectForUpdateReqDto.setId(reqBean.getFaqId());

        try {
            FaqSelectForUpdateResDto selectForUpdateResDto =
                    (FaqSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedDate()
                    && !selectForUpdateResDto.getUpdatedDate().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                FaqUpdateReqDto updateReqDto = new FaqUpdateReqDto();
                updateReqDto.setCategoryId(reqBean.getCategoryId());
                updateReqDto.setAnswerEng(reqBean.getAnswerEng());
                updateReqDto.setAnswerMyan(reqBean.getAnswerMyan());
                updateReqDto.setUpdatedBy(reqBean.getUpdatedBy());
                updateReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
                updateReqDto.setFaqId(reqBean.getFaqId());
                updateReqDto.setQuestionEng(reqBean.getQuestionEng());
                updateReqDto.setQuestionMyan(reqBean.getQuestionMyan());
                updateReqDto.setDelFlag(reqBean.getDelFlag());
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
