/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeMessagingHistoryList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.freeMessagingHistorySearch.FreeMessagingHistorySearchReqDto;
import mm.aeon.com.ass.base.dto.freeMessagingHistorySearch.FreeMessagingHistorySearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class FreeMessagingHistoryPaginationController extends LazyDataModel<FreeMessagingHistoryLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = 8245401136291431516L;
    private int rowCount;

    private FreeMessagingHistorySearchReqDto reqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public FreeMessagingHistoryPaginationController(int rowCount, FreeMessagingHistorySearchReqDto reqDto) {
        this.rowCount = rowCount;
        this.reqDto = reqDto;
    }

    @Override
    public Object getRowKey(FreeMessagingHistoryLineBean lineBean) {
        return lineBean.getMessageId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<FreeMessagingHistoryLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Free Messaging history search pagination process started.", LogLevel.INFO);
        reqDto.setLimit(pageSize);
        reqDto.setOffset(first);
        reqDto.setSortField(sortField);
        reqDto.setSortOrder(sortOrder.toString());
        // ----------------------------------------
        List<FreeMessagingHistoryLineBean> resultList = new ArrayList<FreeMessagingHistoryLineBean>();
        try {
            List<FreeMessagingHistorySearchResDto> resDtoList =
                    (List<FreeMessagingHistorySearchResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            for (FreeMessagingHistorySearchResDto resDto : resDtoList) {
                FreeMessagingHistoryLineBean data = new FreeMessagingHistoryLineBean();

                data.setMessageId(resDto.getMessageId());
                data.setCustomerName(resDto.getCustomerName());
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
        applicationLogger.log("Free Messaging history search pagination process finished.", LogLevel.INFO);
        return resultList;

    }

}
