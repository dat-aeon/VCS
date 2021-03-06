/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfo;

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

import mm.aeon.com.ass.front.companyInfoManagement.CompanyInfoBean;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

//@Name("companyInfoFormBean")
//@Scope(ScopeType.CONVERSATION)
//@FormBean
public class CompanyInfoFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -7135450431138864114L;

    private CompanyInfoHeaderBean searchHeaderBean;

    private List<CompanyInfoLineBean> lineBeans;

    private LazyDataModel<CompanyInfoLineBean> lazyModel;

    private CompanyInfoLineBean lineBean;

    private ArrayList<SelectItem> statusList;

    @Out(required = false, value = "companyInfoUpdateParam")
    private CompanyInfoBean updateParam;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new CompanyInfoHeaderBean();
        this.doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        /*if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeans.size() != 0) {
            lazyModel = new CompanyInfoPaginationController(lineBeans.size(), lineBeans);
        }*/

        return LinkTarget.OK;
    }

    public String prepareUpdate(CompanyInfoLineBean lineBean) {

        this.updateParam = new CompanyInfoBean();
        this.updateParam.setInfoId(lineBean.getInfoId());
        this.updateParam.setAddress_eng(lineBean.getAddress_eng());
        this.updateParam.setAddress_mya(lineBean.getAddress_mya());
        this.updateParam.setHotLine(lineBean.getHotLine());
        this.updateParam.setWebAddress(lineBean.getWebAddress());
        this.updateParam.setSocialMedia(lineBean.getSocialMedia());
        this.updateParam.setAboutCompanyEng(lineBean.getAboutCompanyEng());
        this.updateParam.setAboutCompanyMya(lineBean.getAboutCompanyMya());
        this.updateParam.setCreatedBy(lineBean.getCreatedBy());
        this.updateParam.setUpdatedBy(lineBean.getUpdatedBy());
        this.updateParam.setCreatedTime(lineBean.getCreatedTime());
        this.updateParam.setUpdatedTime(lineBean.getUpdatedTime());

        return LinkTarget.UPDATE_INIT;
    }

    @Action
    public String toggleValidStatus(CompanyInfoLineBean lineBean) {
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
        this.searchHeaderBean = new CompanyInfoHeaderBean();
    }

    public String prepareRegister() {
        this.updateParam = null;
        return LinkTarget.REGISTER;
    }

    public CompanyInfoBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(CompanyInfoBean updateParam) {
        this.updateParam = updateParam;
    }

    public CompanyInfoHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(CompanyInfoHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<CompanyInfoLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<CompanyInfoLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<CompanyInfoLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<CompanyInfoLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public CompanyInfoLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(CompanyInfoLineBean lineBean) {
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

}
