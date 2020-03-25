/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.appSettingManagement;

import mm.aeon.com.ass.base.dto.secQuestionCount.SecurityQuestionCountReqDto;
import mm.aeon.com.ass.base.service.appSettingUpdateService.AppSettingUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.appSettingUpdateService.AppSettingUpdateServiceResBean;
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
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class AppSettingManagementController extends AbstractController
        implements IControllerAccessor<AppSettingManagementFormBean, AppSettingManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public AppSettingManagementFormBean process(AppSettingManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[APP Setting]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean messageBean;
        String serviceStatus = null;

        try {
            applicationLogger.log("App Setting Update Process Stared.", LogLevel.INFO);
            AppSettingUpdateServiceReqBean appSettingReqBean = new AppSettingUpdateServiceReqBean();
            AppSettingUpdateServiceResBean appSettingResBean = new AppSettingUpdateServiceResBean();

            if (!InputChecker.isBlankOrNull(
                    String.valueOf(formBean.getAppSettingManagementHeaderBean().getNoOfcharacterAnswer()))) {
                appSettingReqBean
                        .setNoOfcharacterAnswer(formBean.getAppSettingManagementHeaderBean().getNoOfcharacterAnswer());
            }

            if (!InputChecker.isBlankOrNull(
                    String.valueOf(formBean.getAppSettingManagementHeaderBean().getNoOfsecurityQuestion()))) {
                appSettingReqBean.setNoOfsecurityQuestion(
                        formBean.getAppSettingManagementHeaderBean().getNoOfsecurityQuestion());
            }

            SecurityQuestionCountReqDto securityCountReqDto = new SecurityQuestionCountReqDto();
            int count = (int) CommonUtil.getDaoServiceInvoker().execute(securityCountReqDto);

            if (count >= formBean.getAppSettingManagementHeaderBean().getNoOfsecurityQuestion()) {

                appSettingReqBean.setAppSettingId(formBean.getAppSettingManagementHeaderBean().getAppSettingId());

                this.getServiceInvoker().addRequest(appSettingReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                appSettingResBean = responseMessage.getMessageBean(0);
                serviceStatus = appSettingResBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {
                    messageBean = new MessageBean(MessageId.MI0002);
                    messageBean.setMessageType(MessageType.INFO);
                    formBean.getMessageContainer().addMessage(messageBean);

                    applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("App Setting Update Process finished.", LogLevel.INFO);

                }

            } else {
                messageBean = new MessageBean(MessageId.ME1031);
                messageBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(messageBean);

                applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("App Setting Update Process cancel.", LogLevel.INFO);
            }
        } catch (Exception e) {

            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
                messageBean = new MessageBean(MessageId.MI0001);

                applicationLogger.log(messageBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Record Duplicated.", LogLevel.ERROR);
                throw new BaseException();

            } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
                applicationLogger.log("SQL Error.", LogLevel.ERROR);
                throw new BaseException();
            } else {
                applicationLogger.log("Error.", LogLevel.ERROR);
                throw new BaseException();
            }
        }
        return formBean;
    }

    private boolean isValidData(AppSettingManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (formBean.getAppSettingManagementHeaderBean().getNoOfcharacterAnswer() == VCSCommon.ZERO_INTEGER) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.NO_CHARACTER_OF_ANS));
            msgBean.addColumnId(DisplayItem.NO_CHARACTER_OF_ANS + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getAppSettingManagementHeaderBean().getNoOfsecurityQuestion() == VCSCommon.ZERO_INTEGER) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.NO_SECURITY_QUESTION));
            msgBean.addColumnId(DisplayItem.NO_SECURITY_QUESTION + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        return isValid;
    }
}
