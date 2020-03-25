/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messagingHistoryList;

import mm.aeon.com.ass.base.dto.messagingHistorySearch.MessagingHistorySearchReqDto;
import mm.aeon.com.ass.base.dto.messagingHistorySearch.MessagingHistorySelectCountReqDto;
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

public class MessagingHistoryListSearchController extends AbstractController
        implements IControllerAccessor<MessagingHistoryListFormBean, MessagingHistoryListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public MessagingHistoryListFormBean process(MessagingHistoryListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Messaging history searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        MessagingHistorySelectCountReqDto messagingHistorySelectCountReqDto = new MessagingHistorySelectCountReqDto();
        messagingHistorySelectCountReqDto
                .setCustomerName(formBean.getMessagingHistoryListHeaderBean().getCustomerName());
        messagingHistorySelectCountReqDto.setFromDate(formBean.getMessagingHistoryListHeaderBean().getFromDate());
        messagingHistorySelectCountReqDto.setToDate(formBean.getMessagingHistoryListHeaderBean().getToDate());
        messagingHistorySelectCountReqDto.setNrcNo(formBean.getMessagingHistoryListHeaderBean().getNrcNo());

        MessagingHistorySearchReqDto messagingHistorySearchReqDto = new MessagingHistorySearchReqDto();
        messagingHistorySearchReqDto.setCustomerName(messagingHistorySelectCountReqDto.getCustomerName());
        messagingHistorySearchReqDto.setNrcNo(messagingHistorySelectCountReqDto.getNrcNo());
        messagingHistorySearchReqDto.setFromDate(messagingHistorySelectCountReqDto.getFromDate());
        messagingHistorySearchReqDto.setToDate(messagingHistorySelectCountReqDto.getToDate());

        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(messagingHistorySelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setMessagingHistorySearchReqDto(messagingHistorySearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Messaging history searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
