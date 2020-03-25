/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.customerUpdateService.CustomerUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.customerUpdateService.CustomerUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerAccountLockController extends AbstractController
        implements IControllerAccessor<CustomerListFormBean, CustomerListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CustomerListFormBean process(CustomerListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Customer account lock status update started.", LogLevel.INFO);

        MessageBean msgBean;
        String serviceStatus;

        CustomerUpdateServiceReqBean customerUpdateServiceReqBean = new CustomerUpdateServiceReqBean();
        CustomerUpdateServiceResBean customerUpdateServiceResBean = new CustomerUpdateServiceResBean();
        customerUpdateServiceReqBean.setCustomerNo(formBean.getLineBean().getCustomerNo());
        customerUpdateServiceReqBean.setCustomerId(formBean.getLineBean().getCustomerId());
        customerUpdateServiceReqBean.setApplockFlag(
                formBean.getLineBean().getApplockFlag() == VCSCommon.ZERO_INTEGER ? VCSCommon.ONE_INTEGER
                        : VCSCommon.ZERO_INTEGER);
        customerUpdateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
        customerUpdateServiceReqBean.setUpdatedTime(formBean.getLineBean().getUpdatedTime());

        this.getServiceInvoker().addRequest(customerUpdateServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        customerUpdateServiceResBean = responseMessage.getMessageBean(0);
        serviceStatus = customerUpdateServiceResBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Customer account lock status update finished.", LogLevel.INFO);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update customer data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating customer data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating customer data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

}
