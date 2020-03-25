/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.storeSearch.ShopSearchForCouponReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.ShopSearchForCouponResDto;
import mm.aeon.com.ass.front.cuponManagement.CouponManagementFormBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ShopListCouponSearchController extends AbstractController
        implements IControllerAccessor<CouponManagementFormBean, CouponManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CouponManagementFormBean process(CouponManagementFormBean formBean) {

        applicationLogger.log("Store Searching Process started.", LogLevel.INFO);

        // MessageBean msgBean;

        ShopSearchForCouponReqDto reqDto = new ShopSearchForCouponReqDto();

        if (formBean.getCouponManagementHeaderBean().getStoreName() != null) {
            /*
             * if(formBean.getCouponManagementHeaderBean().getStoreName() != "" ) { reqDto.setStore_name("%" +
             * formBean.getCouponManagementHeaderBean().getStoreName() + "%"); }
             */
        }

        try {
            List<ShopSearchForCouponResDto> resFaqList =
                    (List<ShopSearchForCouponResDto>) this.getDaoServiceInvoker().execute(reqDto);

            List<StoreListLineBean> lineBeanList = new ArrayList<StoreListLineBean>();

            // ArrayList<SelectItem> StoreList = new ArrayList<SelectItem>();

            for (ShopSearchForCouponResDto resDto : resFaqList) {

                StoreListLineBean lineBean = new StoreListLineBean();

                lineBean.setStoreId(resDto.getStoreId());
                lineBean.setStoreName(resDto.getStoreName());
                lineBean.setStoreCode(resDto.getStoreCode());

                /*
                 * SelectItem selectedItem = new SelectItem(); selectedItem.setLabel(resDto.getStoreName());
                 * selectedItem.setValue(lineBean);
                 * 
                 * StoreList.add(selectedItem);
                 */

                lineBeanList.add(lineBean);
            }

            formBean.getCouponManagementHeaderBean().setLineBeans(lineBeanList);
            formBean.setLineBeans(lineBeanList);

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
