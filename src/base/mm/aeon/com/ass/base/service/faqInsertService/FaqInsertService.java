/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.faqInsertService;

import mm.aeon.com.ass.base.dto.faqRegister.FaqInsertReqDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class FaqInsertService extends AbstractService
        implements IService<FaqInsertServiceReqBean, FaqInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public FaqInsertServiceResBean execute(FaqInsertServiceReqBean reqBean) {

        FaqInsertReqDto reqDto = new FaqInsertReqDto();
        FaqInsertServiceResBean resBean = new FaqInsertServiceResBean();

        reqDto.setAnswerEng(reqBean.getAnswerEng());
        reqDto.setAnswerMyan(reqBean.getAnswerMyan());
        reqDto.setCategoryId(reqBean.getCategoryId());
        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setDelFlag(VCSCommon.ZERO_INTEGER);
        reqDto.setCreatedTime(reqBean.getCreatedTime());
        reqDto.setQuestionEng(reqBean.getQuestionEng());
        reqDto.setQuestionMyan(reqBean.getQuestionMyan());

        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

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
