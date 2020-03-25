/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerUpdateHistoryList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.customerUpdateHistorySearch.CustomerUpdateHistoryInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.customerUpdateHistorySearch.CustomerUpdateHistoryInfoSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerUpdateHistoryListPaginationController extends LazyDataModel<CustomerUpdateHistoryListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = 2895710328228440321L;

    private int rowCount;

    private CustomerUpdateHistoryInfoSearchReqDto CustomerUpdateHistoryInfoSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public CustomerUpdateHistoryListPaginationController(int rowCount,
            CustomerUpdateHistoryInfoSearchReqDto CustomerUpdateHistoryInfoSearchReqDto) {
        this.rowCount = rowCount;
        this.CustomerUpdateHistoryInfoSearchReqDto = CustomerUpdateHistoryInfoSearchReqDto;
    }

    @Override
    public Object getRowKey(CustomerUpdateHistoryListLineBean customerUpdateHistoryInfoListLineBean) {
        return customerUpdateHistoryInfoListLineBean.getCustomerInfoUpdateHistoryId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<CustomerUpdateHistoryListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Customer Update History info search pagination process started.", LogLevel.INFO);
        CustomerUpdateHistoryInfoSearchReqDto.setLimit(pageSize);
        CustomerUpdateHistoryInfoSearchReqDto.setOffset(first);
        CustomerUpdateHistoryInfoSearchReqDto.setSortField(sortField);
        CustomerUpdateHistoryInfoSearchReqDto.setSortOrder(sortOrder.toString());

        List<CustomerUpdateHistoryListLineBean> resultList = new ArrayList<CustomerUpdateHistoryListLineBean>();
        try {
            List<CustomerUpdateHistoryInfoSearchResDto> resDtoList =
                    (List<CustomerUpdateHistoryInfoSearchResDto>) CommonUtil.getDaoServiceInvoker()
                            .execute(CustomerUpdateHistoryInfoSearchReqDto);

            for (CustomerUpdateHistoryInfoSearchResDto resDto : resDtoList) {
                CustomerUpdateHistoryListLineBean data = new CustomerUpdateHistoryListLineBean();

                data.setCustomerInfoUpdateHistoryId(resDto.getCustomerInfoUpdateHistoryId());
                data.setCustomerNo(resDto.getCustomerNo());
                data.setName(resDto.getName());
                data.setDescription(resDto.getDescription());
                data.setUpdatedBy(resDto.getUpdatedBy());
                data.setUpdatedTime(resDto.getUpdatedTime());

                resultList.add(data);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("Customer Update History info search pagination process finished.", LogLevel.INFO);
        return resultList;

    }
}
