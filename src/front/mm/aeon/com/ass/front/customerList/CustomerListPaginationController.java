/**************************************************************************
 * $Date: 2018-09-05$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.customerSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.base.dto.customerSearch.CustomerSearchResDto;
import mm.aeon.com.ass.base.dto.userInfoRefer.CustomerIdReferReqDto;
import mm.aeon.com.ass.base.dto.userInfoRefer.CustomerIdReferResDto;
import mm.aeon.com.ass.base.dto.userInfoRefer.UserInfoReferReqDto;
import mm.aeon.com.ass.base.dto.userInfoRefer.UserInfoReferResDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerListPaginationController extends LazyDataModel<CustomerListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private CustomerSearchReqDto customerSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public CustomerListPaginationController(int rowCount, CustomerSearchReqDto customerSearchReqDto) {
        this.rowCount = rowCount;
        this.customerSearchReqDto = customerSearchReqDto;
    }

    @Override
    public Object getRowKey(CustomerListLineBean line) {
        return line.getCustomerNo();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<CustomerListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Customer Search Pagination Process started.", LogLevel.INFO);
        customerSearchReqDto.setLimit(pageSize);
        customerSearchReqDto.setOffset(first);
        customerSearchReqDto.setSortField(sortField);
        customerSearchReqDto.setSortOrder(sortOrder.toString());

        List<CustomerListLineBean> resultList = new ArrayList<CustomerListLineBean>();
        CustomerListLineBean lineBean;
        DecimalFormat df = new DecimalFormat(".##");
        UserInfoReferReqDto userInfoReferReqDto;
        UserInfoReferResDto userInfoReferResDto;
        String[] str;
        try {
            List<CustomerSearchResDto> resDtoList =
                    (List<CustomerSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(customerSearchReqDto);
            for (CustomerSearchResDto resDto : resDtoList) {
                lineBean = new CustomerListLineBean();
                String user_name = null;

                if (null != resDto.getUpdatedBy()) {
                    user_name = resDto.getUpdatedBy();

                    if (resDto.getUpdatedBy().contains(",")) {
                        userInfoReferReqDto = new UserInfoReferReqDto();
                        userInfoReferResDto = new UserInfoReferResDto();
                        str = new String[2];
                        str = resDto.getUpdatedBy().split(VCSCommon.REXCOMMA);

                        if (!str[1].equals("3")) {
                            userInfoReferReqDto.setUser_id(Integer.parseInt(str[0]));
                            userInfoReferReqDto.setUser_type_id(Integer.parseInt(str[1]));
                            userInfoReferResDto = (UserInfoReferResDto) CommonUtil.getDaoServiceInvoker()
                                    .execute(userInfoReferReqDto);
                            user_name = userInfoReferResDto.getUser_name();
                        } else {
                            CustomerIdReferReqDto customerReferReqDto = new CustomerIdReferReqDto();
                            customerReferReqDto.setCustomer_id(resDto.getCustomerId());

                            CustomerIdReferResDto customerReferResDto = (CustomerIdReferResDto) CommonUtil
                                    .getDaoServiceInvoker().execute(customerReferReqDto);

                            user_name = customerReferResDto.getName();
                        }
                    }
                }

                lineBean.setCompanyName(resDto.getCompanyName());
                lineBean.setCreatedBy(resDto.getCreatedBy());
                lineBean.setCreatedTime(resDto.getCreatedTime());
                lineBean.setCustomerId(resDto.getCustomerId());
                lineBean.setCustomerNo(resDto.getCustomerNo());
                lineBean.setCustomerTypeId(resDto.getCustomerTypeId());
                lineBean.setDelFlag(resDto.getDelFlag());
                lineBean.setDob(resDto.getDob());
                lineBean.setGender(resDto.getGender());
                lineBean.setJoinDate(resDto.getJoinDate());
                lineBean.setName(resDto.getName());
                lineBean.setNrcNo(resDto.getNrcNo());
                lineBean.setPhoneNo(resDto.getPhoneNo());
                if (resDto.getSalary() != null) {
                    lineBean.setStrSalary(df.format(resDto.getSalary()));
                }

                lineBean.setSalary(resDto.getSalary());
                lineBean.setTownship(resDto.getTownship());
                lineBean.setPhotoPath(resDto.getPhotoPath());
                if (null != resDto.getUpdatedBy())
                    lineBean.setUpdatedBy(user_name);
                else
                    lineBean.setUpdatedBy(resDto.getUpdatedBy());
                lineBean.setUpdatedTime(resDto.getUpdatedTime());
                lineBean.setUserTypeId(resDto.getUserTypeId());
                lineBean.setApplockFlag(resDto.getApplockFlag());

                resultList.add(lineBean);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Customer Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

    public CustomerSearchReqDto getCustomerSearchReqDto() {
        return customerSearchReqDto;
    }

    public void setCustomerSearchReqDto(CustomerSearchReqDto customerSearchReqDto) {
        this.customerSearchReqDto = customerSearchReqDto;
    }

}
