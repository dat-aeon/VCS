/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerManagement;

import org.springframework.util.StringUtils;

import mm.aeon.com.ass.base.dto.userInfoRefer.CustomerIdReferReqDto;
import mm.aeon.com.ass.base.dto.userInfoRefer.CustomerIdReferResDto;
import mm.aeon.com.ass.base.service.customerUpdateHistoryInsertService.CustomerUpdateHistoryInsertServiceReqBean;
import mm.aeon.com.ass.base.service.customerUpdateHistoryInsertService.CustomerUpdateHistoryInsertServiceResBean;
import mm.aeon.com.ass.base.service.customerUpdateService.CustomerUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.customerUpdateService.CustomerUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.sessions.LoginUserInfo;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class CustomerManagementController extends AbstractController
        implements IControllerAccessor<CustomerManagementFormBean, CustomerManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CustomerManagementFormBean process(CustomerManagementFormBean formBean) {

        MessageBean msgBean;
        String serviceStatus = null;
        String servStatus = null;
        formBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("Customer Update Process started.", LogLevel.INFO);
        boolean isMember = false;

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
         * throw new InvalidScreenTransitionException(); }
         */
        if (formBean.getLineBean().getMember())
            isMember = true;

        formBean.setMember(isMember);

        if (!isValidData(formBean)) {
            return formBean;
        }

        LoginUserInfo loginUser = new LoginUserInfo();
        loginUser = CommonUtil.getLoginUserInfo();
        String userId = null;
        String userTypeId = null;

        if (!loginUser.getUserTypeId().equals("3")) {
            userId = String.valueOf(loginUser.getId());
            userTypeId = loginUser.getUserTypeId();
        } else {
            try {
                CustomerIdReferReqDto customerReferReqDto = new CustomerIdReferReqDto();
                customerReferReqDto.setCustomer_id(formBean.getLineBean().getCustomerId());

                CustomerIdReferResDto customerReferResDto =
                        (CustomerIdReferResDto) CommonUtil.getDaoServiceInvoker().execute(customerReferReqDto);

                userId = String.valueOf(customerReferResDto.getCustomer_id());
                userTypeId = customerReferResDto.getUserTypeId();

            } catch (PrestoDBException e) {
                e.printStackTrace();
            }
        }

        CustomerUpdateServiceReqBean reqBean = new CustomerUpdateServiceReqBean();
        CustomerUpdateServiceResBean resBean;

        reqBean.setCompanyName(formBean.getLineBean().getCompanyName());
        reqBean.setCustomerId(formBean.getLineBean().getCustomerId());
        if (StringUtils.isEmpty(formBean.getLineBean().getCustomerNo())) {
            reqBean.setCustomerNo(null);
        } else {
            reqBean.setCustomerNo(formBean.getLineBean().getCustomerNo());
        }

        if (formBean.getLineBean().getNonMember()) {
            reqBean.setCustomerTypeId(2);
        }
        reqBean.setDelFlag(formBean.getLineBean().getDelFlag());
        reqBean.setDob(formBean.getLineBean().getDob());
        reqBean.setGender(formBean.getLineBean().getGender());
        reqBean.setJoinDate(formBean.getLineBean().getJoinDate());
        reqBean.setName(formBean.getLineBean().getName());
        reqBean.setNrcNo(formBean.getLineBean().getNrcNo().trim());
        reqBean.setPhoneNo(formBean.getLineBean().getPhoneNo().trim());
        reqBean.setSalary(formBean.getLineBean().getSalary());
        reqBean.setTownship(formBean.getLineBean().getTownship());
        reqBean.setCompanyName(formBean.getLineBean().getCompanyName());
        // reqBean.setUpdatedBy(formBean.getLineBean().getUpdatedBy());
        reqBean.setUpdatedBy(String.valueOf(userId) + "," + userTypeId);
        reqBean.setUpdatedTime(formBean.getLineBean().getUpdatedTime());
        this.getServiceInvoker().addRequest(reqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {

            CustomerUpdateHistoryInsertServiceReqBean historyReqBean = new CustomerUpdateHistoryInsertServiceReqBean();
            CustomerUpdateHistoryInsertServiceResBean historyResBean;

            String historyDescription = "";

            if (formBean.getNameChanged() != null) {
                historyDescription += formBean.getNameChanged() + " <br> ";
            }

            if (formBean.getCustomerNoChanged() != null) {
                historyDescription += formBean.getCustomerNoChanged() + " <br> ";
            }

            if (formBean.getPhoneChanged() != null) {
                historyDescription += formBean.getPhoneChanged() + " <br> ";
            }

            if (formBean.getNrcChanged() != null) {
                historyDescription += formBean.getNrcChanged() + " <br> ";
            }

            if (formBean.getDobChanged() != null) {
                historyDescription += formBean.getDobChanged() + " <br> ";
            }

            if (formBean.getSalaryChanged() != null) {
                historyDescription += formBean.getSalaryChanged() + " <br> ";
            }

            if (formBean.getGenderChanged() != null) {
                historyDescription += formBean.getGenderChanged() + " <br> ";
            }

            if (formBean.getCompanyChanged() != null) {
                historyDescription += formBean.getCompanyChanged() + " <br> ";
            }

            if (formBean.getTownshipChanged() != null) {
                historyDescription += formBean.getTownshipChanged() + " <br> ";
            }

            if (formBean.getCustomerTypeChanged() != null) {
                historyDescription += formBean.getCustomerTypeChanged() + " <br> ";
            }

            historyReqBean.setDescription(historyDescription);
            historyReqBean.setCustomerId(formBean.getLineBean().getCustomerId());
            historyReqBean.setUpdatedBy(String.valueOf(userId) + "," + userTypeId);
            historyReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

            if (!historyDescription.isEmpty()) {
                this.getServiceInvoker().addRequest(historyReqBean);
                ResponseMessage resMessage = this.getServiceInvoker().invoke();
                historyResBean = resMessage.getMessageBean(0);
                servStatus = historyResBean.getServiceStatus();
                applicationLogger.log("Customer Info Update History Insert Process started.", LogLevel.INFO);
            }

            applicationLogger.log("Customer Update Process started.", LogLevel.INFO);
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Customer Update Process finished.", LogLevel.INFO);

        } else if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO)
                    + " or " + DisplayItemBean.getDisplayItemName(DisplayItem.NRC_NO));
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updated Customer data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Customer data is deleted.", LogLevel.ERROR);

            // } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            // throw new BaseException();
            // }
        } else {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(CustomerManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (formBean.isMember()) {
            if (InputChecker.isBlankOrNull(formBean.getLineBean().getName())
                    || formBean.getLineBean().getName().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
                msgBean.addColumnId(DisplayItem.NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getCustomerNo())
                    || formBean.getLineBean().getCustomerNo().trim().isEmpty()) {
                msgBean =
                        new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CUSTOMER_NO));
                msgBean.addColumnId(DisplayItem.CUSTOMER_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getPhoneNo())
                    || formBean.getLineBean().getPhoneNo().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
                msgBean.addColumnId(DisplayItem.PHONE_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (!InputChecker.isBlankOrNull(formBean.getLineBean().getPhoneNo())
                    && !formBean.getLineBean().getPhoneNo().matches(VCSCommon.MOBILENO)) {
                msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
                msgBean.addColumnId(DisplayItem.PHONE_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getNrcNo())
                    || formBean.getLineBean().getNrcNo().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NRC_NO));
                msgBean.addColumnId(DisplayItem.NRC_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (!InputChecker.isBlankOrNull(formBean.getLineBean().getNrcNo())
                    && !formBean.getLineBean().getNrcNo().matches(VCSCommon.NRC_NO_PATTERN)) {
                msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.NRC_NO));
                msgBean.addColumnId(DisplayItem.NRC_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getCompanyName())
                    || formBean.getLineBean().getCompanyName().trim().isEmpty()) {
                msgBean =
                        new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.COMPANY_NAME));
                msgBean.addColumnId(DisplayItem.COMPANY_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getTownship())
                    || formBean.getLineBean().getTownship().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TOWNSHIP));
                msgBean.addColumnId(DisplayItem.TOWNSHIP);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (formBean.getLineBean().getDob() == null) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.DOB));
                msgBean.addColumnId(DisplayItem.DOB + VCSCommon.INPUT);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            /*
             * if (null == formBean.getLineBean().getJoinDate()) { msgBean = new MessageBean(MessageId.ME0003,
             * DisplayItemBean.getDisplayItemName(DisplayItem.START_DATE)); msgBean.addColumnId(DisplayItem.START_DATE);
             * msgBean.setMessageType(MessageType.ERROR); formBean.getMessageContainer().addMessage(msgBean); isValid =
             * false; }
             */

            // if (formBean.getLineBean().getSalary() == null) {
            // msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SALARY));
            // msgBean.addColumnId(DisplayItem.SALARY + VCSCommon.INPUT);
            // msgBean.setMessageType(MessageType.ERROR);
            // formBean.getMessageContainer().addMessage(msgBean);
            // isValid = false;
            // }

            if (formBean.getLineBean().getGender() == null) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.GENDER));
                msgBean.addColumnId(DisplayItem.GENDER);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (null == formBean.getLineBean().getCustomerTypeId()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.IS_MEMBER));
                msgBean.addColumnId(DisplayItem.IS_MEMBER);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }
        if (!formBean.isMember()) {

            if (!StringUtils.isEmpty(formBean.getLineBean().getCustomerNo())) {
                msgBean =
                        new MessageBean(MessageId.ME1043, DisplayItemBean.getDisplayItemName(DisplayItem.CUSTOMER_NO));
                msgBean.addColumnId(DisplayItem.CUSTOMER_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getName())
                    || formBean.getLineBean().getName().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
                msgBean.addColumnId(DisplayItem.NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getPhoneNo())
                    || formBean.getLineBean().getPhoneNo().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
                msgBean.addColumnId(DisplayItem.PHONE_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (!InputChecker.isBlankOrNull(formBean.getLineBean().getPhoneNo())
                    && !formBean.getLineBean().getPhoneNo().matches(VCSCommon.MOBILENO)) {
                msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
                msgBean.addColumnId(DisplayItem.PHONE_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getLineBean().getNrcNo())
                    || formBean.getLineBean().getNrcNo().trim().isEmpty()) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NRC_NO));
                msgBean.addColumnId(DisplayItem.NRC_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (!InputChecker.isBlankOrNull(formBean.getLineBean().getNrcNo())
                    && !formBean.getLineBean().getNrcNo().matches(VCSCommon.NRC_NO_PATTERN)) {
                msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.NRC_NO));
                msgBean.addColumnId(DisplayItem.NRC_NO);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (formBean.getLineBean().getDob() == null) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.DOB));
                msgBean.addColumnId(DisplayItem.DOB + VCSCommon.INPUT);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            /*
             * if (null == formBean.getLineBean().getJoinDate()) { msgBean = new MessageBean(MessageId.ME0003,
             * DisplayItemBean.getDisplayItemName(DisplayItem.START_DATE)); msgBean.addColumnId(DisplayItem.START_DATE);
             * msgBean.setMessageType(MessageType.ERROR); formBean.getMessageContainer().addMessage(msgBean); isValid =
             * false; }
             */

            // if (formBean.getLineBean().getSalary() == null) {
            // msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SALARY));
            // msgBean.addColumnId(DisplayItem.SALARY + VCSCommon.INPUT);
            // msgBean.setMessageType(MessageType.ERROR);
            // formBean.getMessageContainer().addMessage(msgBean);
            // isValid = false;
            // }

            if (formBean.getLineBean().getCustomerTypeId() == null) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.IS_MEMBER));
                msgBean.addColumnId(DisplayItem.IS_MEMBER);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (formBean.getLineBean().getGender() == null) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.GENDER));
                msgBean.addColumnId(DisplayItem.GENDER);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }
        return isValid;
    }

}
