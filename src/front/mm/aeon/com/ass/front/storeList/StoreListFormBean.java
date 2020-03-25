/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeList;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.storeManagement.StoreManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("storeListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class StoreListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -7135450431138864114L;

    private StoreListHeaderBean searchHeaderBean;

    private List<StoreListLineBean> lineBeans;

    private List<StoreListLineBean> branchlineBeans;

    private LazyDataModel<StoreListLineBean> lazyModel;

    private StoreListLineBean lineBean;

    @Out(required = false, value = "storeUpdateParam")
    private StoreManagementHeaderBean updateParam;

    @In(required = false, value = "branch")
    @Out(required = false, value = "branch")
    private List<StoreListLineBean> branchList;

    private boolean init = true;
    private boolean branchSearchFlag = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int couponDel;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new StoreListHeaderBean();
        this.doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeans.size() != 0) {
            lazyModel = new StoreListPaginationController(lineBeans.size(), lineBeans);
        }

        return LinkTarget.OK;
    }

    public String prepareUpdate(StoreListLineBean lineBean) {
        this.doReload = new Boolean(true);
        this.updateParam = new StoreManagementHeaderBean();
        this.updateParam.setStoreCode(lineBean.getStoreCode());
        // this.updateParam.setBranchId(lineBean.getBranchId());
        return LinkTarget.UPDATE_INIT;
    }

    public String detail(StoreListLineBean lineBean) {

        this.getMessageContainer().clearAllMessages(true);
        this.lineBean = lineBean;
        this.searchHeaderBean.setStoreId(lineBean.getStoreId());
        return LinkTarget.DETAIL;
    }

    @Action
    public String searchBranch() {
        branchSearchFlag = false;
        return LinkTarget.DETAIL;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        branchSearchFlag = true;
        return LinkTarget.BACK;
    }

    public void reset() {
        this.searchHeaderBean = new StoreListHeaderBean();
    }

    @Action
    public String toggleValidStatus(StoreListLineBean lineBean) {
        this.lineBean = null;
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    public String prepareRegister() {

        if (branchList != null) {
            branchList = null;
        }
        this.updateParam = null;
        return LinkTarget.REGISTER;
    }

    public StoreManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(StoreManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public StoreListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(StoreListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<StoreListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<StoreListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<StoreListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<StoreListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public StoreListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(StoreListLineBean lineBean) {
        this.lineBean = lineBean;
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

    public List<StoreListLineBean> getBranchlineBeans() {
        return branchlineBeans;
    }

    public void setBranchlineBeans(List<StoreListLineBean> branchlineBeans) {
        this.branchlineBeans = branchlineBeans;
    }

    public boolean isBranchSearchFlag() {
        return branchSearchFlag;
    }

    public void setBranchSearchFlag(boolean branchSearchFlag) {
        this.branchSearchFlag = branchSearchFlag;
    }

    public int getCouponDel() {
        return couponDel;
    }

    public void setCouponDel(int couponDel) {
        this.couponDel = couponDel;
    }

}
