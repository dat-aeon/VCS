/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.branchSearch.StoreBranchCouponSearchReqDto;
import mm.aeon.com.ass.base.dto.branchSearch.StoreBranchCouponSearchResDto;
import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSearchResDto;
import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSelectCountReqDto;
import mm.aeon.com.ass.base.dto.storeCouponSearch.StoreCouponSearchReqDto;
import mm.aeon.com.ass.base.dto.storeCouponSearch.StoreCouponSearchResDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchByNameReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchByNameResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.cuponManagement.StoreCouponDataBean;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CusCustomerListSeachController extends AbstractController
        implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Customer Searching Process started.", LogLevel.INFO);

        List<CustomerDataBean> customerList = new ArrayList<CustomerDataBean>();

        List<StoreCouponDataBean> storeCouponBranchList = new ArrayList<StoreCouponDataBean>();
        List<StoreCouponDataBean> originStoreCouponBranchList = new ArrayList<StoreCouponDataBean>();

        CustomerSelectCountReqDto customerSelectCountReqDto = new CustomerSelectCountReqDto();
        customerSelectCountReqDto.setCoupon_id(formBean.getLineBean().getCid());

        CustomerSearchReqDto couponCustomerSearchReqDto = new CustomerSearchReqDto();
        couponCustomerSearchReqDto.setCoupon_id(formBean.getLineBean().getCid());

        StoreCouponSearchReqDto reqStoreDto = new StoreCouponSearchReqDto();
        reqStoreDto.setCoupon_id(formBean.getLineBean().getCid());

        StoreSearchByNameReqDto reqNameDto = new StoreSearchByNameReqDto();
        reqNameDto.setStore_name(formBean.getLineBean().getStoreName());

        try {
            List<CustomerSearchResDto> resList =
                    (List<CustomerSearchResDto>) this.getDaoServiceInvoker().execute(couponCustomerSearchReqDto);

            CustomerDataBean data = null;

            for (CustomerSearchResDto resDto : resList) {
                data = new CustomerDataBean();

                data.setCusotmer_name(resDto.getCusotmer_name());
                data.setCusotmer_no(resDto.getCusotmer_no());
                data.setCustomer_id(resDto.getCustomer_id());
                data.setDob(resDto.getDob());
                data.setNrcNo(resDto.getNrcNo());
                data.setPhoneNo(resDto.getPhoneNo());
                data.setTownship(resDto.getTownship());

                customerList.add(data);
            }

            formBean.setCustomerList(customerList);

            Integer couponCustomerCount =
                    (Integer) CommonUtil.getDaoServiceInvoker().execute(customerSelectCountReqDto);
            formBean.setCouponCustomerTotalCount(couponCustomerCount);
            formBean.setCouponCustomerSearchReqDto(couponCustomerSearchReqDto);

            StoreSearchByNameResDto resNameDto =
                    (StoreSearchByNameResDto) this.getDaoServiceInvoker().execute(reqNameDto);

            reqStoreDto.setStore_id(resNameDto.getStore_id());

            List<StoreCouponSearchResDto> resDtoList =
                    (List<StoreCouponSearchResDto>) this.getDaoServiceInvoker().execute(reqStoreDto);

            for (StoreCouponSearchResDto resDto : resDtoList) {
                StoreCouponDataBean storeCouponBranch = new StoreCouponDataBean();
                storeCouponBranch.setBranch_id(resDto.getBranch_id());
                storeCouponBranch.setBranch_code(resDto.getBranch_code());
                storeCouponBranch.setBranch_name(resDto.getBranch_name());
                storeCouponBranch.setStore_id(reqStoreDto.getStore_id());
                storeCouponBranchList.add(storeCouponBranch);

                StoreCouponDataBean originStoreCouponBranch = new StoreCouponDataBean();
                originStoreCouponBranch.setBranch_id(resDto.getBranch_id());
                originStoreCouponBranch.setBranch_code(resDto.getBranch_code());
                originStoreCouponBranch.setBranch_name(resDto.getBranch_name());
                originStoreCouponBranch.setStore_id(reqStoreDto.getStore_id());
                originStoreCouponBranchList.add(originStoreCouponBranch);
            }

            StoreBranchCouponSearchReqDto branchreqDto = new StoreBranchCouponSearchReqDto();
            branchreqDto.setStore_id(resNameDto.getStore_id());

            List<StoreCouponDataBean> shopBranchResultList = new ArrayList<StoreCouponDataBean>();
            List<StoreCouponDataBean> originShopBranchList = new ArrayList<StoreCouponDataBean>();

            List<StoreBranchCouponSearchResDto> shopBranchList =
                    (List<StoreBranchCouponSearchResDto>) this.getDaoServiceInvoker().execute(branchreqDto);

            for (StoreBranchCouponSearchResDto resDto : shopBranchList) {
                StoreCouponDataBean shopBranch = new StoreCouponDataBean();
                shopBranch.setBranch_id(resDto.getBranch_id());
                shopBranch.setBranch_name(resDto.getBranch_name());
                shopBranch.setStore_id(branchreqDto.getStore_id());
                shopBranch.setBranch_code(resDto.getBranch_code());
                shopBranchResultList.add(shopBranch);

                StoreCouponDataBean originShopBranch = new StoreCouponDataBean();
                originShopBranch.setBranch_id(resDto.getBranch_id());
                originShopBranch.setBranch_name(resDto.getBranch_name());
                originShopBranch.setStore_id(branchreqDto.getStore_id());
                originShopBranch.setBranch_code(resDto.getBranch_code());
                originShopBranchList.add(shopBranch);
            }

            formBean.setShopBranchList(shopBranchResultList);
            formBean.setStoreCouponBranchList(storeCouponBranchList);
            formBean.setOriginStoreCouponBranchList(originStoreCouponBranchList);
            formBean.setOriginShopBranchList(originShopBranchList);

        } catch (

        PrestoDBException e) {
            e.printStackTrace();
        }

        return formBean;
    }

}
