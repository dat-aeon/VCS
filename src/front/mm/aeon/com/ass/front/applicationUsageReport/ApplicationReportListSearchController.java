/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationUsageReport;

import java.sql.Timestamp;

import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ApplicationSearchReqDto;
import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ApplicationSelectCountReqDto;
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

public class ApplicationReportListSearchController extends AbstractController
        implements IControllerAccessor<ApplicationReportFormBean, ApplicationReportFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ApplicationReportFormBean process(ApplicationReportFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Report Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        ApplicationSelectCountReqDto applicationSelectCountReqDto = new ApplicationSelectCountReqDto();

        if (formBean.getApplicationReportSearchLineBean().getCustomerNo() != null
                & formBean.getApplicationReportSearchLineBean().getCustomerNo() != "")
            applicationSelectCountReqDto
                    .setCustomer_no("%" + formBean.getApplicationReportSearchLineBean().getCustomerNo() + "%");

        if (formBean.getApplicationReportSearchLineBean().getCustomerName() != null
                & formBean.getApplicationReportSearchLineBean().getCustomerName() != "")
            applicationSelectCountReqDto
                    .setName("%" + formBean.getApplicationReportSearchLineBean().getCustomerName() + "%");

        if (formBean.getApplicationReportSearchLineBean().getOsType() != null
                & formBean.getApplicationReportSearchLineBean().getOsType() != "")
            applicationSelectCountReqDto
                    .setOsType("%" + formBean.getApplicationReportSearchLineBean().getOsType() + "%");

        if (formBean.getRptStrFrom() != null) {
            applicationSelectCountReqDto.setRptStrFrom(new Timestamp(formBean.getRptStrFrom().getTime()));
        }

        if (formBean.getRptStrTo() != null)
            applicationSelectCountReqDto.setRptStrTo(new Timestamp(formBean.getRptStrTo().getTime()));

        if (formBean.getLstUseFrom() != null)
            applicationSelectCountReqDto.setLstUseFrom(new Timestamp(formBean.getLstUseFrom().getTime()));

        if (formBean.getLstUseTo() != null)
            applicationSelectCountReqDto.setLstUseTo(new Timestamp(formBean.getLstUseTo().getTime()));

        ApplicationSearchReqDto applicationSearchReqDto = new ApplicationSearchReqDto();
        applicationSearchReqDto.setCustomer_no(applicationSelectCountReqDto.getCustomer_no());
        applicationSearchReqDto.setName(applicationSelectCountReqDto.getName());
        applicationSearchReqDto.setOsType(applicationSelectCountReqDto.getOsType());
        applicationSearchReqDto.setRptStrFrom(applicationSelectCountReqDto.getRptStrFrom());
        applicationSearchReqDto.setRptStrTo(applicationSelectCountReqDto.getRptStrTo());
        applicationSearchReqDto.setLstUseFrom(applicationSelectCountReqDto.getLstUseFrom());
        applicationSearchReqDto.setLstUseTo(applicationSelectCountReqDto.getLstUseTo());

        try {

            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(applicationSelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setApplicationSearchReqDto(applicationSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }

            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Application Report searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
