/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfoManagement;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.adminManagement.AdminManagementHeaderBean;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.operatorManagement.OperatorManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("companyInfoManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CompanyInfoManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 2561404663068363440L;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private boolean init = true;
    private boolean isUpdate = true;
    private boolean isError = false;
    private CompanyInfoBean companyInfoBean;
    
    private CompanyInfoBean backUpHeaderBean;

    @Begin(join = true)
    @Action
    public String init() {
        init = false;
        this.getMessageContainer().clearAllMessages((null == this.doReload || !this.doReload));
        doReload = false;
        this.backUpHeaderBean = new CompanyInfoBean().copyCompanyInfoBean(this.companyInfoBean);
        return LinkTarget.OK;
    }

   /* @Begin(join = true)
    public void updateInit() {
        this.getMessageContainer().clearAllMessages(true);
        init = false;
        
        this.backUpHeaderBean = new CompanyInfoBean().copyCompanyInfoBean(this.companyInfoBean);
        
        isUpdate = false;
    }*/
    
    @Action
    public String update() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.init = true;
        this.isUpdate = true;
        doReload = new Boolean(true);
        companyInfoBean = new CompanyInfoBean();
        return LinkTarget.OK;
    }
    
    public void reset() {
        this.companyInfoBean = new CompanyInfoBean().copyCompanyInfoBean(backUpHeaderBean);
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public CompanyInfoBean getCompanyInfoBean() {
        return companyInfoBean;
    }

    public void setCompanyInfoBean(CompanyInfoBean companyInfoBean) {
        this.companyInfoBean = companyInfoBean;
    }

    public CompanyInfoBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(CompanyInfoBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

}
