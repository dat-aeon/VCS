/**************************************************************************
 * $Date: 2019-07-23$
 * $Author: Thiha Htet Zaw $
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerUpdateHistoryList;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.ReflectionUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;

public class CustomerUpdateHistoryListLazySorter implements Comparator<CustomerUpdateHistoryListLineBean> {

    private SortOrder sortOrder;

    private String sortField;

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public CustomerUpdateHistoryListLazySorter(String sortField, SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        this.sortField = sortField;
    }

    public int compare(CustomerUpdateHistoryListLineBean member1, CustomerUpdateHistoryListLineBean member2) {
        applicationLogger.log("Customer Update History List Sorter Process started.", LogLevel.INFO);
        try {
            Object value1 = ReflectionUtil.getValue(member1, sortField);
            Object value2 = ReflectionUtil.getValue(member2, sortField);
            if (value1 == null) {
                value1 = VCSCommon.SPACE;
            }
            if (value2 == null) {
                value2 = VCSCommon.SPACE;
            }
            int value = ((Comparable) value1).compareTo(value2);

            applicationLogger.log("Customer Update History List Sorter Process finished.", LogLevel.INFO);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getMessage());
        }

    }
}
