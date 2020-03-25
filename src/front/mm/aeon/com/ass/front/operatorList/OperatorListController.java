/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import mm.aeon.com.ass.base.dto.userInfoSelectList.UserInfoSelectCountReqDto;
import mm.aeon.com.ass.base.dto.userInfoSelectList.UserInfoSelectListReqDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
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
import mm.com.dat.presto.utils.common.InputChecker;

public class OperatorListController extends AbstractController
        implements IControllerAccessor<OperatorListFormBean, OperatorListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorListFormBean process(OperatorListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!VCSCommon.USER_TYPE_ADMIN.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Operator searching process stared.", LogLevel.INFO);
        MessageBean messageBean;
        UserInfoSelectCountReqDto userInfoSelectCountReqDto = new UserInfoSelectCountReqDto();
        userInfoSelectCountReqDto.setUserTypeId(VCSCommon.USER_TYPE_OPERATOR_ID);
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getUserLoginId())) {
            userInfoSelectCountReqDto.setLoginId(formBean.getSearchHeaderBean().getUserLoginId());
        }
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getUserName())) {
            userInfoSelectCountReqDto.setName(formBean.getSearchHeaderBean().getUserName());
        }

        UserInfoSelectListReqDto userInfoSelectListReqDto = new UserInfoSelectListReqDto();
        userInfoSelectListReqDto.setUserTypeId(VCSCommon.USER_TYPE_OPERATOR_ID);
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getUserLoginId())) {
            userInfoSelectListReqDto.setLoginId(formBean.getSearchHeaderBean().getUserLoginId());
        }
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getUserName())) {
            userInfoSelectListReqDto.setName(formBean.getSearchHeaderBean().getUserName());
        }

        try {
            int totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(userInfoSelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setUserInfoSelectListReqDto(userInfoSelectListReqDto);

            if (totalCount == 0) {
                messageBean = new MessageBean(MessageId.MI0008);
            } else {
                messageBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }

            messageBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Operator searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
