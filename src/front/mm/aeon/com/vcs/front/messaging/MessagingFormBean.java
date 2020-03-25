/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.vcs.front.messaging;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;

@Name("messagingFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class MessagingFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    private static final long serialVersionUID = 8509574334720669310L;

    private boolean isInit = true;

    @Begin(nested = true)
    public String init() {
        isInit = false;
        return LinkTarget.SEARCH;
    }

    public String back() {
        isInit = true;
        return LinkTarget.BACK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
    }

    public boolean getIsInit() {
        return isInit;
    }

    public void setIsInit(boolean init) {
        this.isInit = init;
    }
}
