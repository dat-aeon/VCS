/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import mm.aeon.com.ass.front.common.abstractController.AbstractVCSController;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ImportMemberListInitController extends AbstractVCSController
        implements IControllerAccessor<ImportMemberListFormBean, ImportMemberListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ImportMemberListFormBean process(ImportMemberListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        /*if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Customer List Init]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }*/

        applicationLogger.log("Import Member Init process stared.", LogLevel.INFO);
        MessageBean messageBean;

        /*formBean.setCustomerTypeSelectItemList(super.getCustomerTypeSelectList());
        if (formBean.getCustomerTypeSelectItemList().size() == 0) {
            messageBean = new MessageBean(MessageId.ME1006, "CUSTOMER_TYPE");
            messageBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
        }*/

        formBean.setGenderSelectItemList(super.getGenderSelectList());
        if (formBean.getGenderSelectItemList().size() == 0) {
            messageBean = new MessageBean(MessageId.ME1006, "GENDER");
            messageBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
        }

        // }catch(
        //
        // PrestoDBException e)
        // {
        // if (e instanceof DaoSqlException) {
        // logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
        // throw new BaseException(e.getCause());
        // }
        // }

        applicationLogger.log("Import Member Init process ended.", LogLevel.INFO);
        return formBean;
    }

}
