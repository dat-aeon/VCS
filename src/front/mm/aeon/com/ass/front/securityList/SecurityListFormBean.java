/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.securityList;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.securityManagement.SecurityManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("securityListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class SecurityListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -7135450431138864114L;

    private SecurityListHeaderBean searchHeaderBean;

    private List<SecurityListLineBean> lineBeans;

    private LazyDataModel<SecurityListLineBean> lazyModel;

    private SecurityListLineBean lineBean;

    private ArrayList<SelectItem> statusList;

    @Out(required = false, value = "securityUpdateParam")
    private SecurityManagementHeaderBean updateParam;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int securityDel;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new SecurityListHeaderBean();
        this.doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeans.size() != 0) {
            lazyModel = new SecurityListPaginationController(lineBeans.size(), lineBeans);
        }

        return LinkTarget.OK;
    }

    public String detail(SecurityListLineBean lineBean) {
        this.getMessageContainer().clearAllMessages(true);
        this.lineBean = lineBean;
        return LinkTarget.DETAIL;
    }

    public String prepareUpdate(SecurityListLineBean lineBean) {

        this.updateParam = new SecurityManagementHeaderBean();
        this.updateParam.setSecId(lineBean.getSecId());
        this.updateParam.setQuestionMyan(lineBean.getQuestionMyan());
        this.updateParam.setQuestionEng(lineBean.getQuestionEng());
        this.updateParam.setUpdatedTime(lineBean.getUpdatedTime());
        this.updateParam.setDelFlag(lineBean.getDelFlag());
        return LinkTarget.UPDATE_INIT;
    }

    public String prepareRegister() {
        this.updateParam = null;
        return LinkTarget.REGISTER;
    }

    @Action
    public String toggleValidStatus(SecurityListLineBean lineBean) {
        this.lineBean = null;
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    @Action
    public String delete() {
        this.doReload = false;
        this.lineBean = null;
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
        }

        return LinkTarget.OK;
    }

    public void reset() {
        this.searchHeaderBean = new SecurityListHeaderBean();
    }

    public SecurityManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(SecurityManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public SecurityListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(SecurityListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<SecurityListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<SecurityListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<SecurityListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<SecurityListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public SecurityListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(SecurityListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public ArrayList<SelectItem> getStatusList() {

        statusList = new ArrayList<SelectItem>();

        SelectItem item = new SelectItem();
        item.setLabel(VCSCommon.SPACE);
        item.setValue(VCSCommon.TWO);
        statusList.add(item);

        item = new SelectItem();
        item.setLabel(VCSCommon.DISABLED);
        item.setValue(VCSCommon.ZERO);
        statusList.add(item);

        item = new SelectItem();
        item.setLabel(VCSCommon.ENABLED);
        item.setValue(VCSCommon.ONE);
        statusList.add(item);

        return statusList;
    }

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public int getSecurityDel() {
        return securityDel;
    }

    public void setSecurityDel(int securityDel) {
        this.securityDel = securityDel;
    }

}
