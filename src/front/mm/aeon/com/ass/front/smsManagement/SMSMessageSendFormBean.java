/**************************************************************************
 * $Date: $
 * $Author: $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.smsManagement;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.LogLevel;

@Name("smsMessageSendFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class SMSMessageSendFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3233439807273933964L;

    @In(required = false, value = "smsMessageSendHeaderBean")
    private SMSMessageSendHeaderBean headerBean;

    private boolean isInit = true;

    private ASSLogger logger = new ASSLogger();

    @Begin(nested = true)
    public void init() {
        /*if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Receiving File List]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }*/

        if (null == headerBean) {
            headerBean = new SMSMessageSendHeaderBean();
        }

        this.clearErrorMessage();
    }

    @Action
    public void send() {
        headerBean = null;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
    }

    public SMSMessageSendHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(SMSMessageSendHeaderBean headerBean) {
        this.headerBean = headerBean;
    }

    public boolean getIsInit() {
        return isInit;
    }

    public void setIsInit(boolean init) {
        this.isInit = init;
    }
}
