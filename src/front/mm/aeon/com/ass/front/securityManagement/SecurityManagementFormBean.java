/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.securityManagement;

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

@Name("securityManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class SecurityManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 2561404663068363440L;

    @In(required = false, value = "securityUpdateParam")
    @Out(required = false, value = "securityUpdateParam")
    private SecurityManagementHeaderBean updateParam;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private boolean init = true;

    private SecurityManagementHeaderBean registerHeaderBean;
    
    private SecurityManagementHeaderBean backUpHeaderBean;

    private boolean isUpdate;

    private boolean isValidStatusUpdate;

    private boolean isError = false;

    @Begin(join = true)
    public void init() {
        init = false;
        isUpdate = false;
        this.getMessageContainer().clearAllMessages(true);
        registerHeaderBean = new SecurityManagementHeaderBean();
    }

    @Begin(join = true)
    public void updateInit() {
        this.getMessageContainer().clearAllMessages(true);

        init = false;
        isUpdate = true;
        registerHeaderBean = new SecurityManagementHeaderBean();
        this.registerHeaderBean.setSecId(updateParam.getSecId());
        this.registerHeaderBean.setQuestionMyan(updateParam.getQuestionMyan());
        this.registerHeaderBean.setQuestionEng(updateParam.getQuestionEng());
        this.registerHeaderBean.setDelFlag(updateParam.getDelFlag());
        this.registerHeaderBean.setCreatedBy(updateParam.getCreatedBy());
        this.registerHeaderBean.setUpdatedBy(updateParam.getUpdatedBy());
        this.registerHeaderBean.setCreatedTime(updateParam.getCreatedTime());
        this.registerHeaderBean.setUpdatedTime(updateParam.getUpdatedTime());
        
        this.backUpHeaderBean = new SecurityManagementHeaderBean().copySecurityManagementHeaderBean(registerHeaderBean);
        
    }

    @Action
    public String register() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        } else if (updateParam != null) {
            doReload = new Boolean(true);
            registerHeaderBean = new SecurityManagementHeaderBean();
            init = true;
            return LinkTarget.SEARCH;
        }
        doReload = new Boolean(true);
        registerHeaderBean = new SecurityManagementHeaderBean();
        return LinkTarget.OK;
    }

    
    
    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        this.init = true;
        this.updateParam = null;
        this.registerHeaderBean = null;
       
        return LinkTarget.SEARCH;
    }
    
    public void clear() {
        this.registerHeaderBean = new SecurityManagementHeaderBean();
    }

    public void reset() {
        this.registerHeaderBean = new SecurityManagementHeaderBean().copySecurityManagementHeaderBean(backUpHeaderBean);
    }
    
    public SecurityManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(SecurityManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public SecurityManagementHeaderBean getRegisterHeaderBean() {
        return registerHeaderBean;
    }

    public void setRegisterHeaderBean(SecurityManagementHeaderBean registerHeaderBean) {
        this.registerHeaderBean = registerHeaderBean;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isValidStatusUpdate() {
        return isValidStatusUpdate;
    }

    public void setValidStatusUpdate(boolean isValidStatusUpdate) {
        this.isValidStatusUpdate = isValidStatusUpdate;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public SecurityManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(SecurityManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

}
