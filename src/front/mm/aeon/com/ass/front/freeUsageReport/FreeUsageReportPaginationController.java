/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeUsageReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.freeUsageReportSearch.FreeUsageReportSearchReqDto;
import mm.aeon.com.ass.base.dto.freeUsageReportSearch.FreeUsageReportSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class FreeUsageReportPaginationController extends LazyDataModel<FreeUsageReportLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = 477347974227202350L;

    private int rowCount;

    private FreeUsageReportSearchReqDto freeUsageReportSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public FreeUsageReportPaginationController(int rowCount, FreeUsageReportSearchReqDto freeUsageReportSearchReqDto) {
        this.rowCount = rowCount;
        this.freeUsageReportSearchReqDto = freeUsageReportSearchReqDto;
    }

    @Override
    public Object getRowKey(FreeUsageReportLineBean line) {
        return line.getFreeCustomerInfoId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<FreeUsageReportLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Free usage report search pagination started.", LogLevel.INFO);
        freeUsageReportSearchReqDto.setLimit(pageSize);
        freeUsageReportSearchReqDto.setOffset(first);
        freeUsageReportSearchReqDto.setSortField(sortField);
        freeUsageReportSearchReqDto.setSortOrder(sortOrder.toString());

        List<FreeUsageReportLineBean> resultList = new ArrayList<FreeUsageReportLineBean>();

        try {

            List<FreeUsageReportSearchResDto> resDtoList = (List<FreeUsageReportSearchResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(freeUsageReportSearchReqDto);

            for (FreeUsageReportSearchResDto resDto : resDtoList) {
                FreeUsageReportLineBean data = new FreeUsageReportLineBean();

                data.setFreeCustomerInfoId(resDto.getFreeCustomerInfoId());
                data.setPhoneNo(resDto.getPhoneNo());
                data.setJoinDate(resDto.getJoinDate());
                resultList.add(data);

            }
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Free usage report search pagination finished.", LogLevel.INFO);
        return resultList;
    }

}
