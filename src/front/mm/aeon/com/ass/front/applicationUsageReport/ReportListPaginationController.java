/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationUsageReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ApplicationSearchReqDto;
import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ApplicationSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ReportListPaginationController extends LazyDataModel<ApplicationReportListLineBean> {
    /**
     * 
     */
    private static final long serialVersionUID = -6748245545656453264L;

    private int rowCount;

    private ApplicationSearchReqDto applicationSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public ReportListPaginationController(int rowCount, ApplicationSearchReqDto applicationSearchReqDto) {
        this.rowCount = rowCount;
        this.applicationSearchReqDto = applicationSearchReqDto;
    }

    @Override
    public Object getRowKey(ApplicationReportListLineBean line) {
        return line.getReportId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<ApplicationReportListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Report Search Pagination Process started.", LogLevel.INFO);
        applicationSearchReqDto.setLimit(pageSize);
        applicationSearchReqDto.setOffset(first);
        applicationSearchReqDto.setSortField(sortField);
        applicationSearchReqDto.setSortOrder(sortOrder.toString());

        List<ApplicationReportListLineBean> resultList = new ArrayList<ApplicationReportListLineBean>();

        try {

            List<ApplicationSearchResDto> resDtoList =
                    (List<ApplicationSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(applicationSearchReqDto);

            for (ApplicationSearchResDto resDto : resDtoList) {
                ApplicationReportListLineBean data = new ApplicationReportListLineBean();

                data.setCustomer_no(resDto.getCustomer_no());
                data.setName(resDto.getName());
                data.setOs_type(resDto.getOs_type());
                data.setStartTime(resDto.getStart_time());
                data.setLatestUsingTime(resDto.getLst_used_time());
                data.setAvgOfLogPerDay(resDto.getAvgOfLogPerDay());
                data.setNoOfUsgTol(resDto.getNoOfUsgTol());
                data.setApp_usage_id(resDto.getApp_usage_id());

                resultList.add(data);

            }
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Report Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }
}
