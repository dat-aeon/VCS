/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messagingHistoryList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.messagingHistorySearch.MessagingHistorySearchReqDto;
import mm.aeon.com.ass.base.dto.messagingHistorySearch.MessagingHistorySearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class MessagingHistoryListPaginationController extends LazyDataModel<MessagingHistoryListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -4935428057110192632L;

    private int rowCount;

    private MessagingHistorySearchReqDto messagingHistorySearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public MessagingHistoryListPaginationController(int rowCount,
            MessagingHistorySearchReqDto messagingHistorySearchReqDto) {
        this.rowCount = rowCount;
        this.messagingHistorySearchReqDto = messagingHistorySearchReqDto;
    }

    @Override
    public Object getRowKey(MessagingHistoryListLineBean messagingHistoryListLineBean) {
        return messagingHistoryListLineBean.getMessageId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<MessagingHistoryListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Messaging history search pagination process started.", LogLevel.INFO);
        messagingHistorySearchReqDto.setLimit(pageSize);
        messagingHistorySearchReqDto.setOffset(first);
        messagingHistorySearchReqDto.setSortField(sortField);
        messagingHistorySearchReqDto.setSortOrder(sortOrder.toString());

        List<MessagingHistoryListLineBean> resultList = new ArrayList<MessagingHistoryListLineBean>();
        try {
            List<MessagingHistorySearchResDto> resDtoList = (List<MessagingHistorySearchResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(messagingHistorySearchReqDto);

            for (MessagingHistorySearchResDto resDto : resDtoList) {
                MessagingHistoryListLineBean data = new MessagingHistoryListLineBean();

                data.setMessageId(resDto.getMessageId());
                data.setCustomerName(resDto.getCustomerName());
                data.setNrcNo(resDto.getNrcNo());
                data.setSenderName(resDto.getSenderName());
                data.setSendingTime(resDto.getSendingTime());
                data.setMessageDetail(resDto.getMessageDetail());

                resultList.add(data);

            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("Messaging history search pagination process finished.", LogLevel.INFO);
        return resultList;

    }

}
