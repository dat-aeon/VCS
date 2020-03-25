/**************************************************************************
 * $Date: 2018-09-04$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.deviceUsageReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ActualAppUsageSearchReqDto;
import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ActualAppUsageSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ApplicationListPaginationController extends LazyDataModel<ApplicationListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private ActualAppUsageSearchReqDto actualAppUsageSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public ApplicationListPaginationController(int rowCount, ActualAppUsageSearchReqDto actualAppUsageSearchReqDto) {
        this.rowCount = rowCount;
        this.actualAppUsageSearchReqDto = actualAppUsageSearchReqDto;
    }

    @Override
    public Object getRowKey(ApplicationListLineBean line) {
        return line.getAppUsageId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<ApplicationListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("App Search Pagination Process started.", LogLevel.INFO);
        actualAppUsageSearchReqDto.setLimit(pageSize);
        actualAppUsageSearchReqDto.setOffset(first);
        actualAppUsageSearchReqDto.setSortField(sortField);
        actualAppUsageSearchReqDto.setSortOrder(sortOrder.toString());

        List<ApplicationListLineBean> resultList = new ArrayList<ApplicationListLineBean>();
        try {
            List<ActualAppUsageSearchResDto> resDtoList = (List<ActualAppUsageSearchResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(actualAppUsageSearchReqDto);

            for (ActualAppUsageSearchResDto actualAppUsageSearchResDto : resDtoList) {
                ApplicationListLineBean lineBean = new ApplicationListLineBean();

                lineBean.setCustomerName(actualAppUsageSearchResDto.getCustomerName());
                lineBean.setCustomerNo(actualAppUsageSearchResDto.getCustomerNo());
                lineBean.setManufacture(actualAppUsageSearchResDto.getManufacture());
                lineBean.setModel(actualAppUsageSearchResDto.getModel());
                lineBean.setOsType(actualAppUsageSearchResDto.getOsType());
                lineBean.setOsVersion(actualAppUsageSearchResDto.getOsVersion());
                lineBean.setSdk(actualAppUsageSearchResDto.getSdk());
                lineBean.setAppUsageId(actualAppUsageSearchResDto.getAppUsageId());
                lineBean.setResolution(actualAppUsageSearchResDto.getResolution());

                resultList.add(lineBean);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("App Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
