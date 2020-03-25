/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.passwordChange;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("passwordChangeFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class PasswordChangeFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -8559169100855571603L;

    private PasswordChangeHeaderBean passwordChangeHeaderBean;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(join = true)
    public String init() {
        getMessageContainer().clearAllMessages(true);
        passwordChangeHeaderBean = new PasswordChangeHeaderBean();
        init = false;
        doReload = new Boolean(true);
        return LinkTarget.OK;
    }

    @Action
    public String change() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;

        }
        passwordChangeHeaderBean = new PasswordChangeHeaderBean();
        doReload = new Boolean(true);
        return LinkTarget.OK;
    }

    public void clear() {
        passwordChangeHeaderBean = new PasswordChangeHeaderBean();
        getMessageContainer().clearAllMessages(true);
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public PasswordChangeHeaderBean getPasswordChangeHeaderBean() {
        return passwordChangeHeaderBean;
    }

    public void setPasswordChangeHeaderBean(PasswordChangeHeaderBean passwordChangeHeaderBean) {
        this.passwordChangeHeaderBean = passwordChangeHeaderBean;
    }

}
