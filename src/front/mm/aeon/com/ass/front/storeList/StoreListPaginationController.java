/**************************************************************************
 * $Date: 2019-02-04$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class StoreListPaginationController extends LazyDataModel<StoreListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private List<StoreListLineBean> allDataList;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public StoreListPaginationController(int rowCount, List<StoreListLineBean> allDataList) {
        this.rowCount = rowCount;
        this.allDataList = allDataList;
    }

    @Override
    public Object getRowKey(StoreListLineBean line) {
        return line.getStoreId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<StoreListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Store Search Pagination Process started.", LogLevel.INFO);

        List<StoreListLineBean> resultList = new ArrayList<StoreListLineBean>();

        int endIndex = first + pageSize;

        if (endIndex > allDataList.size()) {
            endIndex = allDataList.size();
        }

        if (sortField != null) {
            Collections.sort(allDataList, new StoreListLazySorter(sortField, sortOrder));
        }

        resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Store Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
