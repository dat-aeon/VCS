/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeUsageReport;

import mm.aeon.com.ass.base.dto.freeUsageReportSearch.FreeUsageReportSearchReqDto;
import mm.aeon.com.ass.base.dto.freeUsageReportSearch.FreeUsageReportSelectCountReqDto;
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

public class FreeUsageReportSearchController extends AbstractController
        implements IControllerAccessor<FreeUsageReportFormBean, FreeUsageReportFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public FreeUsageReportFormBean process(FreeUsageReportFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Free usage report search count started.", LogLevel.INFO);

        MessageBean msgBean;

        FreeUsageReportSelectCountReqDto freeUsageReportSelectCountReqDto = new FreeUsageReportSelectCountReqDto();
        freeUsageReportSelectCountReqDto.setEndDate(formBean.getFreeUsageReportSearchLineBean().getEndDate());
        freeUsageReportSelectCountReqDto.setPhoneNo(formBean.getFreeUsageReportSearchLineBean().getPhoneNo());
        freeUsageReportSelectCountReqDto.setStartDate(formBean.getFreeUsageReportSearchLineBean().getStartDate());

        FreeUsageReportSearchReqDto freeUsageReportSearchReqDto = new FreeUsageReportSearchReqDto();
        freeUsageReportSearchReqDto.setEndDate(formBean.getFreeUsageReportSearchLineBean().getEndDate());
        freeUsageReportSearchReqDto.setPhoneNo(formBean.getFreeUsageReportSearchLineBean().getPhoneNo());
        freeUsageReportSearchReqDto.setStartDate(formBean.getFreeUsageReportSearchLineBean().getStartDate());

        try {

            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(freeUsageReportSelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setFreeUsageReportSearchReqDto(freeUsageReportSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }

            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Free usage report search count finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }
}
