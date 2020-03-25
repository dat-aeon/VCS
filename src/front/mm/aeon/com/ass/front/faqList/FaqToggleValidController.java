/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqList;

import java.util.ArrayList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.faqCount.CategorySearchByFaqReqDto;
import mm.aeon.com.ass.base.dto.faqCount.CategorySearchByFaqResDto;
import mm.aeon.com.ass.base.service.faqUpdateService.FaqUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.faqUpdateService.FaqUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
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

public class FaqToggleValidController extends AbstractController
        implements IControllerAccessor<FaqListFormBean, FaqListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public FaqListFormBean process(FaqListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[FAQ List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        if (formBean.getLineBean().getDelFlag() == VCSCommon.ONE_INTEGER) {

            CategorySearchByFaqReqDto CategorySearchByFaqReqDto = new CategorySearchByFaqReqDto();
            CategorySearchByFaqReqDto.setCategoryId(formBean.getLineBean().getCategoryId());

            try {
                ArrayList<CategorySearchByFaqResDto> CategorySearchByFaqResDto =
                        (ArrayList<CategorySearchByFaqResDto>) CommonUtil.getDaoServiceInvoker()
                                .execute(CategorySearchByFaqReqDto);
                if (null != CategorySearchByFaqResDto & CategorySearchByFaqResDto.size() > 0) {
                    formBean.setCategoryDel(CategorySearchByFaqResDto.get(0).getDel_flag());
                    if (!isValidData(formBean)) {
                        return formBean;
                    }
                }
            } catch (PrestoDBException e) {
                e.printStackTrace();
            }

        }

        applicationLogger.log("FAQ status update started.", LogLevel.INFO);

        MessageBean msgBean;
        String serviceStatus;

        FaqUpdateServiceReqBean faqUpdateReqBean = new FaqUpdateServiceReqBean();
        FaqUpdateServiceResBean faqUpdateResBean = new FaqUpdateServiceResBean();

        faqUpdateReqBean.setQuestionEng(formBean.getLineBean().getQuestionEng());
        faqUpdateReqBean.setQuestionMyan(formBean.getLineBean().getQuestionMyan());
        faqUpdateReqBean.setAnswerEng(formBean.getLineBean().getAnswerEng());
        faqUpdateReqBean.setAnswerMyan(formBean.getLineBean().getAnswerMyan());
        faqUpdateReqBean.setCategoryId(formBean.getLineBean().getCategoryId());
        faqUpdateReqBean.setFaqId(formBean.getLineBean().getFaqId());
        faqUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
        faqUpdateReqBean.setUpdatedTime(formBean.getLineBean().getUpdatedTime());

        faqUpdateReqBean
                .setDelFlag(formBean.getLineBean().getDelFlag() == VCSCommon.ZERO_INTEGER ? VCSCommon.ONE_INTEGER
                        : VCSCommon.ZERO_INTEGER);

        this.getServiceInvoker().addRequest(faqUpdateReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        faqUpdateResBean = responseMessage.getMessageBean(0);
        serviceStatus = faqUpdateResBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("FAQ status update finished.", LogLevel.INFO);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update Faq data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating Faq data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating Faq data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(FaqListFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (formBean.getCategoryDel() == 1) {
            msgBean = new MessageBean(MessageId.ME1039, "");
            msgBean.addColumnId(DisplayItem.FAQ_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
