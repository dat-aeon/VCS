/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ShopListCouponListSearchController extends AbstractController
        implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Shop Searching Process started.", LogLevel.INFO);

        formBean.getSearchHeaderBean().setStore_name(formBean.getStorelineBean().getStoreName());

        return formBean;
    }

}
