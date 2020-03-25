/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class FaqListPaginationController extends LazyDataModel<FaqListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private List<FaqListLineBean> allDataList;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public FaqListPaginationController(int rowCount, List<FaqListLineBean> allDataList) {
        this.rowCount = rowCount;
        this.allDataList = allDataList;
    }

    @Override
    public Object getRowKey(FaqListLineBean line) {
        return line.getFaqId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<FaqListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Admin Search Pagination Process started.", LogLevel.INFO);

        List<FaqListLineBean> resultList = new ArrayList<FaqListLineBean>();

        int endIndex = first + pageSize;

        if (endIndex > allDataList.size()) {
            endIndex = allDataList.size();
        }

        if (sortField != null) {
            Collections.sort(allDataList, new FaqListLazySorter(sortField, sortOrder));
        }

        resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Admin Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
