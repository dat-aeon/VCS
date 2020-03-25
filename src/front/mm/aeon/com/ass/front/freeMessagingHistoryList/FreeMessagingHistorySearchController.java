/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeMessagingHistoryList;

import mm.aeon.com.ass.base.dto.freeMessagingHistorySearch.FreeMessagingHistorySearchCountReqDto;
import mm.aeon.com.ass.base.dto.freeMessagingHistorySearch.FreeMessagingHistorySearchReqDto;
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

public class FreeMessagingHistorySearchController extends AbstractController
        implements IControllerAccessor<FreeMessagingHistoryFormBean, FreeMessagingHistoryFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public FreeMessagingHistoryFormBean process(FreeMessagingHistoryFormBean formBean) {
        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Messaging history searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        FreeMessagingHistorySearchCountReqDto countReqDto = new FreeMessagingHistorySearchCountReqDto();
        if (formBean.getHeaderBean().getPhoneNo() != null) {
            countReqDto.setPhoneNo(formBean.getHeaderBean().getPhoneNo().trim());
        }
        countReqDto.setFromDate(formBean.getHeaderBean().getFromDate());
        countReqDto.setToDate(formBean.getHeaderBean().getToDate());

        FreeMessagingHistorySearchReqDto reqDto = new FreeMessagingHistorySearchReqDto();
        reqDto.setPhoneNo(countReqDto.getPhoneNo());
        reqDto.setFromDate(countReqDto.getFromDate());
        reqDto.setToDate(countReqDto.getToDate());

        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(countReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setFreeMessagingHistorySearchReqDto(reqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Free Messaging history searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        // TODO Auto-generated method stub
        return formBean;
    }

}
