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
import mm.aeon.com.ass.base.dto.storeCouponSearch.BranchCouponSearchReqDto;
import mm.aeon.com.ass.base.dto.storeCouponSearch.BranchCouponSearchResDto;
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

public class BranchListCouponListSearchController extends AbstractController
        implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Branch Searching Process started.", LogLevel.INFO);

        List<StoreCouponDataBean> storeCouponBranchList = new ArrayList<StoreCouponDataBean>();

        StoreSearchByNameReqDto reqNameDto = new StoreSearchByNameReqDto();

        if (formBean.getLineBean() != null & formBean.getStorelineBean() == null)
            reqNameDto.setStore_name(formBean.getLineBean().getStoreName());

        if (formBean.getStorelineBean() != null & formBean.getLineBean() == null)
            reqNameDto.setStore_name(formBean.getStorelineBean().getStoreName());

        // StoreCouponSearchReqDto reqDto = new StoreCouponSearchReqDto();
        BranchCouponSearchReqDto reqDto = new BranchCouponSearchReqDto();

        if (formBean.getLineBean() != null) {
            // reqDto.setCoupon_id(formBean.getLineBean().getCid());
            reqNameDto.setStore_name(formBean.getLineBean().getStoreName());
        }

        try {
            StoreSearchByNameResDto resNameDto =
                    (StoreSearchByNameResDto) this.getDaoServiceInvoker().execute(reqNameDto);

            reqDto.setStore_id(resNameDto.getStore_id());

            StoreBranchCouponSearchReqDto branchreqDto = new StoreBranchCouponSearchReqDto();

            branchreqDto.setStore_id(resNameDto.getStore_id());

            List<StoreBranchCouponSearchResDto> resDtoList =
                    (List<StoreBranchCouponSearchResDto>) this.getDaoServiceInvoker().execute(branchreqDto);

            // List<BranchCouponSearchResDto> resDtoList = (List<BranchCouponSearchResDto>)
            // this.getDaoServiceInvoker().execute(reqDto);

            StoreCouponSearchReqDto reqStoreDto = new StoreCouponSearchReqDto();

            if (null != formBean.getLineBean())
                reqStoreDto.setCoupon_id(formBean.getLineBean().getCid());

            reqStoreDto.setStore_id(reqDto.getStore_id());

            List<StoreCouponSearchResDto> resStoreList =
                    (List<StoreCouponSearchResDto>) this.getDaoServiceInvoker().execute(reqStoreDto);

            List<BranchCouponSearchResDto> tempDtoList = new ArrayList<BranchCouponSearchResDto>();

            if (resDtoList.size() != resStoreList.size() & resDtoList.size() > resStoreList.size()) {
                if (null != resDtoList & null != resStoreList) {
                    for (int i = 0; i < resDtoList.size(); i++) {
                        for (int j = 0; j < resStoreList.size(); j++) {
                            if (!resDtoList.get(i).isDone()) {
                                if (resDtoList.get(i).getBranch_id() != resStoreList.get(j).getBranch_id()) {
                                    BranchCouponSearchResDto data = new BranchCouponSearchResDto();
                                    data.setBranch_id(resDtoList.get(i).getBranch_id());
                                    data.setBranch_code(resDtoList.get(i).getBranch_code());
                                    data.setBranch_name(resDtoList.get(i).getBranch_name());
                                    data.setCoupon_password("");
                                    resDtoList.get(i).setDone(true);

                                    tempDtoList.add(data);
                                }
                            }
                        }
                    }
                }
            }

            if (null != tempDtoList & tempDtoList.size() > 0) {
                for (int i = 0; i < tempDtoList.size(); i++) {
                    StoreCouponSearchResDto data = new StoreCouponSearchResDto();

                    data.setBranch_code(tempDtoList.get(i).getBranch_code());
                    data.setBranch_id(tempDtoList.get(i).getBranch_id());
                    data.setBranch_name(tempDtoList.get(i).getBranch_name());
                    data.setCoupon_password(tempDtoList.get(i).getCoupon_password());

                    resStoreList.add(data);
                }
            }
            StoreCouponDataBean data = null;

            if (resDtoList.size() > 0) {

                for (StoreCouponSearchResDto resDto : resStoreList) {

                    // StoreBranchCouponReferReqDto brnchCountreqDto = new StoreBranchCouponReferReqDto();
                    // brnchCountreqDto.setBranch_id(resDto.getBranch_id());

                    // int count = (int) this.getDaoServiceInvoker().execute(brnchCountreqDto);

                    data = new StoreCouponDataBean();

                    // data.setStore_id(resDto.getShop_id());
                    data.setBranch_id(resDto.getBranch_id());
                    data.setBranch_name(resDto.getBranch_name());
                    data.setBranch_code(resDto.getBranch_code());

                    // if(count > 0)
                    data.setCoupon_password(resDto.getCoupon_password());
                    // else
                    // data.setCoupon_password("");

                    storeCouponBranchList.add(data);

                }

                // formBean.getStoreCouponDataBean().setStoreCouponList(storeCouponList);
                formBean.setStoreCouponBranchList(storeCouponBranchList);
            }
            formBean.getSearchHeaderBean().setStore_name(reqNameDto.getStore_name());
            formBean.setStoreCouponDataBean(data);

        } catch (PrestoDBException e) {
            e.printStackTrace();
        }

        return formBean;
    }

}
