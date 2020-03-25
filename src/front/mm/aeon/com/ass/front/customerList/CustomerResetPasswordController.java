/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerList;

import org.apache.commons.lang3.RandomStringUtils;

import mm.aeon.com.ass.base.service.customerResetPasswordService.CustomerResetPasswordServiceReqBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.sessions.LoginUserInfo;
import mm.com.dat.presto.main.core.authenticate.PasswordEncoder;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerResetPasswordController extends AbstractController
        implements IControllerAccessor<CustomerListFormBean, CustomerListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CustomerListFormBean process(CustomerListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Customer reset password process started.", LogLevel.INFO);

        MessageBean msgBean;

        CustomerResetPasswordServiceReqBean reqBean = new CustomerResetPasswordServiceReqBean();
        LoginUserInfo loginUser = new LoginUserInfo();
        loginUser = CommonUtil.getLoginUserInfo();

        String generatedPassword = RandomStringUtils.randomAlphanumeric(7);
        String randomSpecialCharacter = RandomStringUtils.random(1, VCSCommon.PASSWORD_SPECIAL_CHAR);
        generatedPassword += randomSpecialCharacter;

        String hashPassword = PasswordEncoder.encode(generatedPassword);

        reqBean.setUserId(formBean.getCustomerId());
        reqBean.setUserTypeId(VCSCommon.USER_TYPE_CUSTOMER_ID);
        reqBean.setUpdatedBy(String.valueOf(loginUser.getId()));
        reqBean.setPassword(hashPassword);

        this.getServiceInvoker().addRequest(reqBean);
        this.getServiceInvoker().invoke();

        formBean.setGeneratedPassword(generatedPassword);

        msgBean = new MessageBean(MessageId.MI0016);
        msgBean.setMessageType(MessageType.INFO);
        formBean.getMessageContainer().addMessage(msgBean);
        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
        applicationLogger.log("Customer reset password finished.", LogLevel.INFO);

        return formBean;
    }

}
