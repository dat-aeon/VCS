/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.customerSearch.CustomerSecurityQuestionReqDto;
import mm.aeon.com.ass.base.dto.customerSearch.CustomerSecurityQuestionResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerSecurityQuestionListController extends AbstractController
        implements IControllerAccessor<CustomerListFormBean, CustomerListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CustomerListFormBean process(CustomerListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Customer security question list searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        CustomerSecurityQuestionReqDto reqDto = new CustomerSecurityQuestionReqDto();
        reqDto.setCustomerId(formBean.getCustomerId());

        try {
            List<CustomerSecurityQuestionResDto> resList =
                    (List<CustomerSecurityQuestionResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<CustomerSecurityQuestionListLineBean> lineBeanList =
                    new ArrayList<CustomerSecurityQuestionListLineBean>();

            for (CustomerSecurityQuestionResDto resdto : resList) {
                CustomerSecurityQuestionListLineBean lineBean = new CustomerSecurityQuestionListLineBean();

                lineBean.setCustSecQuesId(lineBean.getCustSecQuesId());
                lineBean.setSecQuesId(resdto.getSecQuesId());
                lineBean.setQuestionMyan(resdto.getQuestionMyan());
                lineBean.setQuestionEng(resdto.getQuestionEng());
                lineBeanList.add(lineBean);
            }

            formBean.setCustomerSecurityQuestionListLineBean(lineBeanList);

            if (lineBeanList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Customer security question list searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
