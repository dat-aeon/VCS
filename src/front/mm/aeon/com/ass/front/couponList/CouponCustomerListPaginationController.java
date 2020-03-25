/**************************************************************************
 * $Date: 2019-02-04$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CouponCustomerListPaginationController extends LazyDataModel<CustomerDataBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private CustomerSearchReqDto couponCustomerSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private List<CustomerDataBean> removedCouponCustomerList;

    public CouponCustomerListPaginationController(int rowCount, CustomerSearchReqDto couponCustomerSearchReqDto,
            List<CustomerDataBean> removedCouponCustomerList) {
        this.rowCount = rowCount;
        this.couponCustomerSearchReqDto = couponCustomerSearchReqDto;
        this.removedCouponCustomerList = removedCouponCustomerList;

    }

    @Override
    public Object getRowKey(CustomerDataBean line) {
        return line.getCusotmer_no();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<CustomerDataBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Store Search Pagination Process started.", LogLevel.INFO);
        couponCustomerSearchReqDto.setLimit(pageSize);
        couponCustomerSearchReqDto.setOffset(first);
        couponCustomerSearchReqDto.setSortField(sortField);
        couponCustomerSearchReqDto.setSortOrder(sortOrder.toString());

        List<CustomerDataBean> resultList = new ArrayList<CustomerDataBean>();
        CustomerDataBean data = null;
        try {
            List<CustomerSearchResDto> resList =
                    (List<CustomerSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(couponCustomerSearchReqDto);

            for (CustomerSearchResDto resDto : resList) {
                data = new CustomerDataBean();
                data.setCusotmer_name(resDto.getCusotmer_name());
                data.setCusotmer_no(resDto.getCusotmer_no());
                data.setCustomer_id(resDto.getCustomer_id());
                data.setDob(resDto.getDob());
                data.setNrcNo(resDto.getNrcNo());
                data.setPhoneNo(resDto.getPhoneNo());
                data.setTownship(resDto.getTownship());
                resultList.add(data);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Store Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
