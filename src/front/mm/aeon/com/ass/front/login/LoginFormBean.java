/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.login;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.web.Session;

import mm.aeon.com.ass.filter.SystemFilter;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.sessions.LoginUserInfo;
import mm.com.dat.presto.main.core.authenticate.LoginInfo;
import mm.com.dat.presto.main.core.authenticate.LoginUtility;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

/**
 * 
 * LoginFormBean Class.
 * <p>
 * 
 * <pre>
 *      LoginFormBean.
 * </pre>
 * 
 * </p>
 */
@Name("loginFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class LoginFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8861449951601426176L;

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    @In
    private Identity identity;

    @In
    private Credentials credentials;

    @In(create = true)
    private LoginUtility loginUtility;

    @In(required = false, value = "userInfo")
    @Out(required = false, value = "userInfo")
    private LoginUserInfo loginUserInfo;

    @In(required = false)
    private LoginInfo loginInfo;

    private boolean loginInitFlag = true;

    private boolean PasswordChangeFlag = true;

    private boolean init = true;

    /**
     * init Method.
     * <p>
     * 
     * <pre>
     *      init Method.
     * </pre>
     * 
     * </p>
     */
    @Begin(join = true)
    public String init() {
        getMessageContainer().clearAllMessages(true);
        applicationLogger.log("Login Initialization Process started.", LogLevel.INFO);
        if (loginUserInfo != null) {

            applicationLogger.log("Clear old user information.", LogLevel.INFO);

            if (loginUserInfo.isLoggedIn()) {
                loginUtility.logout();
            } else {
                this.setLoginInitFlag(false);
            }
        } else {
            applicationLogger.log("Create new user information.", LogLevel.INFO);

            loginUserInfo = new LoginUserInfo();
            this.setLoginInitFlag(false);
        }

        applicationLogger.log("Login Initialization Process finished.", LogLevel.INFO);

        return LinkTarget.INIT;
    }

    /**
     * login Method.
     * <p>
     * 
     * <pre>
     *      Perform login operation.
     * </pre>
     * 
     * </p>
     * 
     * @return boolean
     */
    @Action
    public String login() {

        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        } else if (loginUserInfo.getUserTypeName().equals(VCSCommon.TWO)) {
            return LinkTarget.APP_STATUS;
        } else {
            if (loginUserInfo.getUpdatedBy() == null && loginUserInfo.getUpdatedTime() == null) {
                PasswordChangeFlag = true;
                init = true;
                return LinkTarget.CHANGE;
            } else {
                return LinkTarget.OK;
            }
        }

    }

    /**
     * logout Method.
     * <p>
     * 
     * <pre>
     *      Perform logout operation.
     * </pre>
     * 
     * </p>
     * 
     * @return String
     */
    @End(root = true)
    public String logout() {

        identity.logout();

        HttpServletRequest req =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        SystemFilter.setOnLogout(req, true);

        Session.instance().invalidate();

        applicationLogger.log("Logout process is successfully finished.", LogLevel.INFO);

        return LinkTarget.LOGOUT;

    }

    public String notLoggedInMessage() {

        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Boolean sessionTimeOut = (Boolean) request.getSession().getAttribute(VCSCommon.SESSION_TIMEOUT_KEY);

        if (sessionTimeOut == null) {
            MessageBean msgBean = new MessageBean(MessageId.MW0001);
            msgBean.setMessageType(MessageType.WARN);
            this.getMessageContainer().addMessage(msgBean);
        }
        return LinkTarget.ERROR;
    }

    /**
     * Get loginUtility.
     * 
     * @return loginUtility
     */
    public LoginUtility getLoginUtility() {
        return loginUtility;
    }

    /**
     * Set loginUtility.
     * 
     * @param loginUtility
     *            set loginUtility
     */
    public void setLoginUtility(LoginUtility loginUtility) {
        this.loginUtility = loginUtility;
    }

    /**
     * Get loginInfo.
     * 
     * @return loginInfo
     */
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    /**
     * Set loginInfo.
     * 
     * @param loginInfo
     *            set loginInfo
     */
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    /**
     * Get identity.
     * 
     * @return identity
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Set identity.
     * 
     * @param identity
     *            set identity
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    /**
     * Get credentials.
     * 
     * @return credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Set credentials.
     * 
     * @param credentials
     *            set credentials
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public LoginUserInfo getLoginUserInfo() {
        return loginUserInfo;
    }

    public void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        this.loginUserInfo = loginUserInfo;
    }

    public boolean isLoginInitFlag() {
        return loginInitFlag;
    }

    public void setLoginInitFlag(boolean loginInitFlag) {
        this.loginInitFlag = loginInitFlag;
    }

    public boolean isPasswordChangeFlag() {
        return PasswordChangeFlag;
    }

    public void setPasswordChangeFlag(boolean passwordChangeFlag) {
        PasswordChangeFlag = passwordChangeFlag;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

}
