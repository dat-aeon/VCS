/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoManagement;

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

@Name("categoryInfoManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CategoryInfoManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -8559169100855571603L;

    @In(required = false, value = "categoryInfoUpdateParam")
    @Out(required = false, value = "categoryInfoUpdateParam")
    private CategoryInfoManagementHeaderBean updateParam;

    private CategoryInfoManagementHeaderBean categoryInfoManagementHeaderBean;

    private CategoryInfoManagementHeaderBean backUpHeaderBean;

    private String tempUploadImageFilePath;

    private boolean init = true;

    private boolean update;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(join = true)
    public String init() {
        getMessageContainer().clearAllMessages(true);
        categoryInfoManagementHeaderBean = new CategoryInfoManagementHeaderBean();
        init = false;
        update = false;
        doReload = new Boolean(true);
        return LinkTarget.REGISTER;
    }

    @Begin(join = true)
    public void updateInit() {
        getMessageContainer().clearAllMessages(true);

        init = false;
        update = true;
        categoryInfoManagementHeaderBean = new CategoryInfoManagementHeaderBean();
        categoryInfoManagementHeaderBean =
                new CategoryInfoManagementHeaderBean().copycategoryInfoManagementHeaderBean(updateParam);

        backUpHeaderBean = new CategoryInfoManagementHeaderBean()
                .copycategoryInfoManagementHeaderBean(categoryInfoManagementHeaderBean);
    }

    @Action
    public String register() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;

        } else if (updateParam != null) {

            categoryInfoManagementHeaderBean = new CategoryInfoManagementHeaderBean();
            init = true;
            tempUploadImageFilePath = null;
            doReload = new Boolean(true);
            return LinkTarget.SEARCH;
        }
        categoryInfoManagementHeaderBean = new CategoryInfoManagementHeaderBean();
        tempUploadImageFilePath = null;
        doReload = new Boolean(true);
        return LinkTarget.OK;
    }

    public String back() {
        getMessageContainer().clearAllMessages(true);
        init = true;
        updateParam = null;

        categoryInfoManagementHeaderBean = null;
        tempUploadImageFilePath = null;
        return LinkTarget.BACK;
    }

    public void clear() {
        categoryInfoManagementHeaderBean = new CategoryInfoManagementHeaderBean();
        tempUploadImageFilePath = null;
        getMessageContainer().clearAllMessages(true);
    }

    public void reset() {
        categoryInfoManagementHeaderBean =
                new CategoryInfoManagementHeaderBean().copycategoryInfoManagementHeaderBean(backUpHeaderBean);
    }

    public CategoryInfoManagementHeaderBean getCategoryInfoManagementHeaderBean() {
        return categoryInfoManagementHeaderBean;
    }

    public void setCategoryInfoManagementHeaderBean(CategoryInfoManagementHeaderBean categoryInfoManagementHeaderBean) {
        this.categoryInfoManagementHeaderBean = categoryInfoManagementHeaderBean;
    }

    public CategoryInfoManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(CategoryInfoManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
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

    public CategoryInfoManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(CategoryInfoManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

}
