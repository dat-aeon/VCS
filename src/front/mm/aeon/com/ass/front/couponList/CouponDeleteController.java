/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import mm.aeon.com.ass.base.service.couponDeleteService.CouponDeleteServiceReqBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CouponDeleteController extends AbstractController implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    
    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {
       
        formBean.getMessageContainer().clearAllMessages(true);
        
        applicationLogger.log("Coupon deleting Process started.", LogLevel.INFO);
        MessageBean msgBean;
        
        CouponDeleteServiceReqBean serviceReqBean = new CouponDeleteServiceReqBean();
        serviceReqBean.setCoupon_id(formBean.getLineBean().getCid());
        serviceReqBean.setDel_flag(1);
        serviceReqBean.setUpdated_by(CommonUtil.getLoginUserName().toString());
        serviceReqBean.setUpdated_time(CommonUtil.getCurrentTimeStamp());
        
        this.getServiceInvoker().addRequest(serviceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        
        if(ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Coupon deleting Process finished.", LogLevel.INFO);
        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Coupon deleting Process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }
        
        return formBean;
    }

}
