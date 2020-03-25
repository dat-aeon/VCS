/**************************************************************************
 * $Date: 2018-09-04$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AdminListPaginationController extends LazyDataModel<AdminListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private List<AdminListLineBean> allDataList;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public AdminListPaginationController(int rowCount, List<AdminListLineBean> allDataList) {
        this.rowCount = rowCount;
        this.allDataList = allDataList;
    }

    @Override
    public Object getRowKey(AdminListLineBean line) {
        return line.getAdminName();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<AdminListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Admin Search Pagination Process started.", LogLevel.INFO);

        List<AdminListLineBean> resultList = new ArrayList<AdminListLineBean>();

        int endIndex = first + pageSize;

        if (endIndex > allDataList.size()) {
            endIndex = allDataList.size();
        }

        if (sortField != null) {
            Collections.sort(allDataList, new AdminListLazySorter(sortField, sortOrder));
        }

        resultList = allDataList.subList(first, endIndex);

        applicationLogger.log("Admin Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
