/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.storeSearch.ShopSearchForCouponReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.ShopSearchForCouponResDto;
import mm.aeon.com.ass.front.storeList.StoreListLineBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class StoreListCouponListSeachController extends AbstractController
        implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Store List Searching Process started.", LogLevel.INFO);

        ShopSearchForCouponReqDto reqDto = new ShopSearchForCouponReqDto();

        if (formBean.getSearchHeaderBean().getStore_name() != null) {
            /*
             * if(formBean.getCouponManagementHeaderBean().getStoreName() != "" ) { reqDto.setStore_name("%" +
             * formBean.getCouponManagementHeaderBean().getStoreName() + "%"); }
             */
        }

        try {
            List<ShopSearchForCouponResDto> resFaqList =
                    (List<ShopSearchForCouponResDto>) this.getDaoServiceInvoker().execute(reqDto);

            List<StoreListLineBean> lineBeanList = new ArrayList<StoreListLineBean>();

            for (ShopSearchForCouponResDto resDto : resFaqList) {
                StoreListLineBean lineBean = new StoreListLineBean();

                lineBean.setStoreId(resDto.getStoreId());
                lineBean.setStoreName(resDto.getStoreName());
                lineBean.setStoreCode(resDto.getStoreCode());

                lineBeanList.add(lineBean);
            }

            formBean.getSearchHeaderBean().setLineBeans(lineBeanList);
            formBean.setStorelineBeans(lineBeanList);

            /*
             * if (lineBeanList.size() == 0) { msgBean = new MessageBean(MessageId.MI0008); } else { msgBean = new
             * MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size())); }
             * msgBean.setMessageType(MessageType.INFO); formBean.getMessageContainer().addMessage(msgBean);
             * applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
             */
            applicationLogger.log("Store searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
