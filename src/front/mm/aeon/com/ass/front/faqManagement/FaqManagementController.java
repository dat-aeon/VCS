/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqManagement;

import mm.aeon.com.ass.base.service.faqInsertService.FaqInsertServiceReqBean;
import mm.aeon.com.ass.base.service.faqInsertService.FaqInsertServiceResBean;
import mm.aeon.com.ass.base.service.faqUpdateService.FaqUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.faqUpdateService.FaqUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class FaqManagementController extends AbstractController
        implements IControllerAccessor<FaqManagementFormBean, FaqManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public FaqManagementFormBean process(FaqManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[FAQ List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean messageBean;
        String serviceStatus = null;

        if (!formBean.getFaqManagementHeaderBean().isForUpdate()) {
            applicationLogger.log("FAQ Registration Process Stared.", LogLevel.INFO);
            FaqInsertServiceReqBean faqInsertReqBean = new FaqInsertServiceReqBean();
            FaqInsertServiceResBean faqInsertResBean = new FaqInsertServiceResBean();

            faqInsertReqBean.setQuestionEng(formBean.getFaqManagementHeaderBean().getQuestionEng());
            faqInsertReqBean.setQuestionMyan(formBean.getFaqManagementHeaderBean().getQuestionMyan());
            faqInsertReqBean.setAnswerEng(formBean.getFaqManagementHeaderBean().getAnswerEng());
            faqInsertReqBean.setAnswerMyan(formBean.getFaqManagementHeaderBean().getAnswerMyan());
            faqInsertReqBean.setCategoryId(formBean.getFaqManagementHeaderBean().getCategoryId());
            faqInsertReqBean.setCreatedBy(CommonUtil.getLoginUserName());
            faqInsertReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());

            this.getServiceInvoker().addRequest(faqInsertReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            faqInsertResBean = responseMessage.getMessageBean(0);
            serviceStatus = faqInsertResBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                messageBean = new MessageBean(MessageId.MI0001);
                messageBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(messageBean);

                applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("FAQ Registration Process finished.", LogLevel.INFO);

            }

        } else {

            applicationLogger.log("FAQ Update Process Stared.", LogLevel.INFO);
            FaqUpdateServiceReqBean faqUpdateReqBean = new FaqUpdateServiceReqBean();
            FaqUpdateServiceResBean faqUpdateResBean = new FaqUpdateServiceResBean();

            faqUpdateReqBean.setQuestionEng(formBean.getFaqManagementHeaderBean().getQuestionEng());
            faqUpdateReqBean.setQuestionMyan(formBean.getFaqManagementHeaderBean().getQuestionMyan());
            faqUpdateReqBean.setAnswerEng(formBean.getFaqManagementHeaderBean().getAnswerEng());
            faqUpdateReqBean.setAnswerMyan(formBean.getFaqManagementHeaderBean().getAnswerMyan());
            faqUpdateReqBean.setCategoryId(formBean.getFaqManagementHeaderBean().getCategoryId());
            faqUpdateReqBean.setFaqId(formBean.getFaqManagementHeaderBean().getFaqId());
            faqUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
            faqUpdateReqBean.setUpdatedTime(formBean.getFaqManagementHeaderBean().getUpdatedTime());
            faqUpdateReqBean.setDelFlag(VCSCommon.ZERO_INTEGER);

            this.getServiceInvoker().addRequest(faqUpdateReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            faqUpdateResBean = responseMessage.getMessageBean(0);
            serviceStatus = faqUpdateResBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                messageBean = new MessageBean(MessageId.MI0002);
                messageBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(messageBean);

                applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("FAQ Update Process finished.", LogLevel.INFO);

            }

        }
        return formBean;
    }

    private boolean isValidData(FaqManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getFaqManagementHeaderBean().getQuestionEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.Q_ENGLISH));
            msgBean.addColumnId(DisplayItem.Q_ENGLISH);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getFaqManagementHeaderBean().getQuestionMyan())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.Q_MYANMAR));
            msgBean.addColumnId(DisplayItem.Q_MYANMAR);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getFaqManagementHeaderBean().getAnswerEng())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.ANS_ENGLISH));
            msgBean.addColumnId(DisplayItem.ANS_ENGLISH);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getFaqManagementHeaderBean().getAnswerMyan())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.ANS_MYANMAR));
            msgBean.addColumnId(DisplayItem.ANS_MYANMAR);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getFaqManagementHeaderBean().getCategoryId() == 0) {
            msgBean = new MessageBean(MessageId.ME0004, DisplayItemBean.getDisplayItemName(DisplayItem.CATEGORY));
            msgBean.addColumnId(DisplayItem.CATEGORY);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        }
        /* --------------------- */

        if (!CommonUtil.isPureAscii(formBean.getFaqManagementHeaderBean().getQuestionEng())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.Q_ENGLISH));
            msgBean.addColumnId(DisplayItem.Q_ENGLISH);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getFaqManagementHeaderBean().getAnswerEng())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.ANS_ENGLISH));
            msgBean.addColumnId(DisplayItem.ANS_ENGLISH);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
