/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.login;

import org.apache.log4j.MDC;

import mm.aeon.com.ass.base.dto.loginInfoRefer.LoginInfoReferReqDto;
import mm.aeon.com.ass.base.dto.loginInfoRefer.LoginInfoReferResDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.sessions.LoginUserInfo;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.authenticate.PasswordEncoder;
import mm.com.dat.presto.main.core.base.controller.ServiceInvoker;
import mm.com.dat.presto.main.core.dao.controller.DaoServiceInvoker;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

/**
 * 
 * LoginController Class.
 * <p>
 * 
 * <pre>
 * Controller class for handling login process.
 * </pre>
 * 
 * </p>
 */
public class LoginController extends AbstractASSController
        implements IControllerAccessor<LoginFormBean, LoginFormBean> {

    private MessageBean msgBean;

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public LoginFormBean process(LoginFormBean loginFormBean) {

        applicationLogger.log("Login Process started.", LogLevel.INFO);

        loginFormBean.getMessageContainer().clearAllMessages(true);

        // Check User Input Data
        if (InputChecker.isBlankOrNull(loginFormBean.getLoginInfo().getUserId())
                || InputChecker.isBlankOrNull(loginFormBean.getLoginInfo().getPassword())) {
            msgBean = new MessageBean(MessageId.ME0001);
            msgBean.addColumnId(DisplayItem.USER_ID);
            msgBean.addColumnId(DisplayItem.PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            loginFormBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log("Single Point Check failed.", LogLevel.INFO);
            applicationLogger.log("Login Process finished.", LogLevel.INFO);
            return loginFormBean;
        }

        applicationLogger.log("Authentication in System started.", LogLevel.INFO);

        // Check User Info in Management User Info
        try {

            LoginInfoReferReqDto loginInfoReferReq = new LoginInfoReferReqDto();
            loginInfoReferReq.setLoginId(loginFormBean.getLoginInfo().getUserId());
            String hashPassword = PasswordEncoder.encode(loginFormBean.getLoginInfo().getPassword());
            loginInfoReferReq.setPassword(hashPassword);
            LoginInfoReferResDto loginInfoReferRes =
                    (LoginInfoReferResDto) CommonUtil.getDaoServiceInvoker().execute(loginInfoReferReq);

            if (loginInfoReferRes != null) {

                // if (loginFormBean.getLoginUserInfo() == null) {
                loginFormBean.setLoginUserInfo(new LoginUserInfo());
                // }

                loginFormBean.getLoginUserInfo().setId(loginInfoReferRes.getUserId());
                loginFormBean.getLoginUserInfo().setUserId(loginInfoReferRes.getLoginId());
                loginFormBean.getLoginUserInfo().setUserName(loginInfoReferRes.getName());
                loginFormBean.getLoginUserInfo().setUserTypeId(loginInfoReferRes.getUserTypeId());
                loginFormBean.getLoginUserInfo().setUserTypeName(loginInfoReferRes.getUserType().toUpperCase());
                loginFormBean.getLoginUserInfo().setUpdatedBy(loginInfoReferRes.getUpdatedBy());
                loginFormBean.getLoginUserInfo().setUpdatedTime(loginInfoReferRes.getUpdatedTime());
                loginFormBean.getLoginUserInfo().setLoggedIn(true);
                MDC.put("UserID", loginFormBean.getLoginUserInfo().getUserId());

                loginFormBean.getCredentials().setUsername(loginFormBean.getLoginInfo().getUserId());
                loginFormBean.getCredentials().setPassword(loginFormBean.getLoginInfo().getPassword());
                loginFormBean.getIdentity().login();

                applicationLogger.log("Authentication in System is success.", LogLevel.INFO);
            } else {
                msgBean = new MessageBean(MessageId.ME1001);
                msgBean.addColumnId(DisplayItem.USER_ID);
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                loginFormBean.getMessageContainer().addMessage(msgBean);
                applicationLogger.log("Account is not existed in System.", LogLevel.INFO);
                applicationLogger.log("Authentication in System is failed.", LogLevel.INFO);
            }

            applicationLogger.log("Authentication in System finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());

            }
        }
        applicationLogger.log("Login Process finished.", LogLevel.INFO);
        return loginFormBean;
    }

    @Override
    public void setServiceInvoker(ServiceInvoker serviceInvoker) {

    }

    @Override
    public void setDaoServiceInvoker(DaoServiceInvoker daoServiceInvoker) {

    }

}
