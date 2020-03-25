/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.branchSearch.StoreBranchCouponSearchReqDto;
import mm.aeon.com.ass.base.dto.branchSearch.StoreBranchCouponSearchResDto;
import mm.aeon.com.ass.base.dto.storeCouponSearch.StoreCouponSearchReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchByNameReqDto;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class StoreCouponListSeachController extends AbstractController
        implements IControllerAccessor<CouponManagementFormBean, CouponManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CouponManagementFormBean process(CouponManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Store Coupon Searching Process started.", LogLevel.INFO);

        List<StoreCouponDataBean> storeCouponList = new ArrayList<StoreCouponDataBean>();

        List<StoreCouponDataBean> shopBranchList = new ArrayList<StoreCouponDataBean>();

        StoreSearchByNameReqDto reqNameDto = new StoreSearchByNameReqDto();

        reqNameDto.setStore_name(formBean.getLineBean().getStoreName());

        StoreCouponSearchReqDto reqDto = new StoreCouponSearchReqDto();

        StoreBranchCouponSearchReqDto branchreqDto = new StoreBranchCouponSearchReqDto();

        try {

            reqDto.setStore_id(formBean.getLineBean().getStoreId());

            branchreqDto.setStore_id(formBean.getLineBean().getStoreId());

            // List<StoreCouponSearchResDto> resDtoList = (List<StoreCouponSearchResDto>)
            // this.getDaoServiceInvoker().execute(reqDto);

            List<StoreBranchCouponSearchResDto> resDtoList =
                    (List<StoreBranchCouponSearchResDto>) this.getDaoServiceInvoker().execute(branchreqDto);

            if (resDtoList.size() > 0) {
                /*
                 * for(StoreCouponSearchResDto resDto: resDtoList) { data = new StoreCouponDataBean();
                 * 
                 * data.setBranch_id(resDto.getBranch_id()); data.setBranch_name(resDto.getBranch_name());
                 * data.setStore_id(reqDto.getStore_id()); //data.setCoupon_password(resDto.getCoupon_password());
                 * 
                 * storeCouponList.add(data); }
                 */

                for (StoreBranchCouponSearchResDto resDto : resDtoList) {
                    StoreCouponDataBean storeCouponDataBean = new StoreCouponDataBean();
                    storeCouponDataBean.setBranch_id(resDto.getBranch_id());
                    storeCouponDataBean.setBranch_name(resDto.getBranch_name());
                    storeCouponDataBean.setStore_id(reqDto.getStore_id());
                    storeCouponDataBean.setBranch_code(resDto.getBranch_code());
                    storeCouponList.add(storeCouponDataBean);

                    StoreCouponDataBean shopBranch = new StoreCouponDataBean();
                    shopBranch.setBranch_id(resDto.getBranch_id());
                    shopBranch.setBranch_name(resDto.getBranch_name());
                    shopBranch.setStore_id(reqDto.getStore_id());
                    shopBranch.setBranch_code(resDto.getBranch_code());
                    shopBranchList.add(shopBranch);
                }

                // formBean.getStoreCouponDataBean().setStoreCouponList(storeCouponList);
                formBean.setStoreCouponBranchList(storeCouponList);
                formBean.setShopBranchList(shopBranchList);
            }
            formBean.getCouponManagementHeaderBean().setStoreName(reqNameDto.getStore_name());

        } catch (PrestoDBException e) {
            e.printStackTrace();
        }

        return formBean;
    }

}
