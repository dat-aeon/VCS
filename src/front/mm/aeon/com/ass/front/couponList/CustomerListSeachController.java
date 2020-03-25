/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.customerSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.base.dto.customerSearch.CustomerSearchResDto;
import mm.aeon.com.ass.base.dto.storeCouponSearch.StoreCouponSearchReqDto;
import mm.aeon.com.ass.base.dto.storeCouponSearch.StoreCouponSearchResDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchByNameReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchByNameResDto;
import mm.aeon.com.ass.front.cuponManagement.StoreCouponDataBean;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerListSeachController extends AbstractController
        implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Customer Searching Process started.", LogLevel.INFO);

        List<CustomerDataBean> customerList = new ArrayList<CustomerDataBean>();

        List<StoreCouponDataBean> storeCouponBranchList = new ArrayList<StoreCouponDataBean>();

        CustomerSearchReqDto reqDto = new CustomerSearchReqDto();

        // reqDto.setCoupon_id(formBean.getLineBean().getCid());

        StoreCouponSearchReqDto reqStoreDto = new StoreCouponSearchReqDto();

        reqStoreDto.setCoupon_id(formBean.getLineBean().getCid());

        StoreSearchByNameReqDto reqNameDto = new StoreSearchByNameReqDto();

        reqNameDto.setStore_name(formBean.getLineBean().getStoreName());

        try {
            List<CustomerSearchResDto> resList =
                    (List<CustomerSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);

            CustomerDataBean data = null;

            for (CustomerSearchResDto resDto : resList) {
                data = new CustomerDataBean();

                // data.setCusotmer_name(resDto.getCusotmer_name());
                // data.setCusotmer_no(resDto.getCusotmer_no());
                // data.setCustomer_id(resDto.getCustomer_id());

                customerList.add(data);
            }

            formBean.setCustomerList(customerList);

            StoreSearchByNameResDto resNameDto =
                    (StoreSearchByNameResDto) this.getDaoServiceInvoker().execute(reqNameDto);

            reqStoreDto.setStore_id(resNameDto.getStore_id());

            List<StoreCouponSearchResDto> resDtoList =
                    (List<StoreCouponSearchResDto>) this.getDaoServiceInvoker().execute(reqStoreDto);

            StoreCouponDataBean branch = null;

            for (StoreCouponSearchResDto resDto : resDtoList) {
                branch = new StoreCouponDataBean();

                branch.setBranch_id(resDto.getBranch_id());
                branch.setBranch_name(resDto.getBranch_name());

                storeCouponBranchList.add(branch);
            }

            formBean.setStoreCouponBranchList(storeCouponBranchList);

        } catch (PrestoDBException e) {
            e.printStackTrace();
        }

        return formBean;
    }

}
