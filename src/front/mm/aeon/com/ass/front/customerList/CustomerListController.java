/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerList;

import mm.aeon.com.ass.base.dto.customerSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.base.dto.customerSearch.CustomerSelectCountReqDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
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

public class CustomerListController extends AbstractController
        implements IControllerAccessor<CustomerListFormBean, CustomerListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CustomerListFormBean process(CustomerListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Customer List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
         * throw new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Customer searching process stared.", LogLevel.INFO);
        MessageBean messageBean;

        CustomerSelectCountReqDto customerSelectCountReqDto = new CustomerSelectCountReqDto();

        CustomerListHeaderBean formBeanSearchHeaderBean = formBean.getSearchHeaderBean();

        if (formBeanSearchHeaderBean.getName() != null) {
            customerSelectCountReqDto.setName(formBeanSearchHeaderBean.getName().trim().toLowerCase());
        }

        if (formBeanSearchHeaderBean.getTownship() != null) {
            customerSelectCountReqDto.setTownship(formBeanSearchHeaderBean.getTownship().trim().toLowerCase());
        }

        if (formBeanSearchHeaderBean.getCustomerNo() != null) {
            customerSelectCountReqDto.setCustomerNo(formBeanSearchHeaderBean.getCustomerNo().trim());
        }

        if (formBeanSearchHeaderBean.getAgeFrom() != null) {
            customerSelectCountReqDto.setDobFrom(CommonUtil.minusYearsFromToday(formBeanSearchHeaderBean.getAgeFrom()));
        }

        if (formBeanSearchHeaderBean.getAgeTo() != null) {
            customerSelectCountReqDto.setDobTo(CommonUtil.minusYearsFromToday(formBeanSearchHeaderBean.getAgeTo()));
        }

        if (formBeanSearchHeaderBean.getPhoneNo() != null) {
            customerSelectCountReqDto.setPhoneNo(formBeanSearchHeaderBean.getPhoneNo().trim());
        }

        if (formBeanSearchHeaderBean.getNrcNo() != null) {
            customerSelectCountReqDto.setNrcNo(formBeanSearchHeaderBean.getNrcNo().trim());
        }

        customerSelectCountReqDto.setCustomerTypeId(formBeanSearchHeaderBean.getCustomerTypeId());
        customerSelectCountReqDto.setSalaryMax(formBeanSearchHeaderBean.getSalaryMax());
        customerSelectCountReqDto.setSalaryMin(formBeanSearchHeaderBean.getSalaryMin());
        customerSelectCountReqDto.setStartDateFrom(formBeanSearchHeaderBean.getStartDateFrom());
        customerSelectCountReqDto.setStartDateTo(formBeanSearchHeaderBean.getStartDateTo());

        CustomerSearchReqDto customerSearchReqDto = new CustomerSearchReqDto();
        customerSearchReqDto.setName(customerSelectCountReqDto.getName());
        customerSearchReqDto.setTownship(customerSelectCountReqDto.getTownship());
        customerSearchReqDto.setCustomerNo(customerSelectCountReqDto.getCustomerNo());
        customerSearchReqDto.setDobFrom(customerSelectCountReqDto.getDobFrom());
        customerSearchReqDto.setDobTo(customerSelectCountReqDto.getDobTo());
        customerSearchReqDto.setPhoneNo(customerSelectCountReqDto.getPhoneNo());
        customerSearchReqDto.setNrcNo(customerSelectCountReqDto.getNrcNo());
        customerSearchReqDto.setCustomerTypeId(customerSelectCountReqDto.getCustomerTypeId());
        customerSearchReqDto.setSalaryMax(customerSelectCountReqDto.getSalaryMax());
        customerSearchReqDto.setSalaryMin(customerSelectCountReqDto.getSalaryMin());
        customerSearchReqDto.setStartDateFrom(customerSelectCountReqDto.getStartDateFrom());
        customerSearchReqDto.setStartDateTo(customerSelectCountReqDto.getStartDateTo());
        try {

            int totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(customerSelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setCustomerSearchReqDto(customerSearchReqDto);

            if (totalCount == 0) {
                messageBean = new MessageBean(MessageId.MI0008);
            } else {
                messageBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            messageBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Customer searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
