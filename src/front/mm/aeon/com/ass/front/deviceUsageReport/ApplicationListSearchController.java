/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.deviceUsageReport;

import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ActualAppUsageSearchReqDto;
import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ActualAppUsageSelectCountReqDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ApplicationListSearchController extends AbstractController
        implements IControllerAccessor<ApplicationListFormBean, ApplicationListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ApplicationListFormBean process(ApplicationListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Application Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        ActualAppUsageSelectCountReqDto reqDto = new ActualAppUsageSelectCountReqDto();
        reqDto.setOsType(formBean.getAppHeaderBean().getOsType());
        ActualAppUsageSearchReqDto actualAppUsageSearchReqDto = new ActualAppUsageSearchReqDto();
        actualAppUsageSearchReqDto.setOsType(formBean.getAppHeaderBean().getOsType());
        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(reqDto);
            formBean.setTotalCount(totalCount);
            formBean.setActualAppUsageSearchReqDto(actualAppUsageSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Application searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
