/**************************************************************************
 * $Date : 2019-02-04$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeManagement;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.storeList.StoreListLineBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("storeManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class StoreManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1583457870206052674L;

    @In(required = false, value = "storeUpdateParam")
    @Out(required = false, value = "storeUpdateParam")
    private StoreManagementHeaderBean updateParam;

    private StoreManagementHeaderBean backUpHeaderBean;

    private StoreManagementHeaderBean storeManagementHeaderBean;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private boolean init = true;

    @In(required = false, value = "branch")
    @Out(required = false, value = "branch")
    private List<StoreListLineBean> branchList;
    private int tempStoreId;

    private boolean updateFlag = true;

    private List<StoreListLineBean> operationBranchList;

    private List<StoreListLineBean> removedBranchList;

    private StoreListLineBean lineBean;

    private boolean isDisable = true;

    @Begin(nested = true)
    public String init() {
        this.getMessageContainer().clearAllMessages(true);
        this.storeManagementHeaderBean = new StoreManagementHeaderBean();
        init = false;
        updateFlag = false;
        branchList = new ArrayList<StoreListLineBean>();
        removedBranchList = new ArrayList<StoreListLineBean>();
        StoreListLineBean branchItemList = new StoreListLineBean();
        branchList.add(branchItemList);

        return LinkTarget.REGISTER;
    }

    @Action
    public String register() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.storeManagementHeaderBean = new StoreManagementHeaderBean();
        this.doReload = new Boolean(true);
        // init =true;
        this.branchList.clear();
        return LinkTarget.OK;
    }

    @Action
    public String updateInit() {
        this.getMessageContainer().clearAllMessages(true);
        init = false;
        updateFlag = false;
        for (StoreListLineBean storeList : branchList) {
            if (tempStoreId == storeList.getStoreId()) {
                this.storeManagementHeaderBean.setStoreName(storeList.getStoreName());
                this.storeManagementHeaderBean.setStoreId(storeList.getStoreId());
                this.storeManagementHeaderBean.setStoreAddress(storeList.getStoreAddress());
                this.storeManagementHeaderBean.setDelFlag(storeList.getDelFlag());
                this.storeManagementHeaderBean.setStoreCode(storeList.getStoreCode());
                this.storeManagementHeaderBean.setUpdatedTime(storeList.getUpdatedTime());
            }
        }

        this.storeManagementHeaderBean.setForUpdate(true);

        this.backUpHeaderBean =
                new StoreManagementHeaderBean().copyStoreManagementHeaderBean(storeManagementHeaderBean);

        return LinkTarget.UPDATE_INIT;
    }

    public String prepareUpdate(StoreListLineBean lineBean) {

        if (this.storeManagementHeaderBean == null) {
            this.storeManagementHeaderBean = new StoreManagementHeaderBean();
        }
        this.getMessageContainer().clearAllMessages(true);
        tempStoreId = lineBean.getStoreId();
        this.storeManagementHeaderBean.setStoreId(tempStoreId);
        removedBranchList = new ArrayList<StoreListLineBean>();
        updateFlag = true;
        return LinkTarget.UPDATE_INIT;
    }

    @Action
    public String update() {

        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.init = true;
        this.storeManagementHeaderBean = null;
        this.doReload = new Boolean(true);
        updateFlag = false;
        this.removedBranchList.clear();
        return LinkTarget.SEARCH;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        init = true;
        this.storeManagementHeaderBean = null;
        updateFlag = true;
        return LinkTarget.BACK;
    }

    public void clear() {
        this.storeManagementHeaderBean = new StoreManagementHeaderBean();
        this.branchList.clear();
        this.removedBranchList.clear();
        StoreListLineBean branchItemList = new StoreListLineBean();
        this.branchList.add(branchItemList);
    }

    public String reset() {
        this.storeManagementHeaderBean =
                new StoreManagementHeaderBean().copyStoreManagementHeaderBean(backUpHeaderBean);
        this.branchList = new ArrayList<StoreListLineBean>();
        for (StoreListLineBean storeListLineBean : this.operationBranchList) {

            this.branchList.add(storeListLineBean);
        }
        if (storeManagementHeaderBean.isForUpdate()) {
            updateFlag = true;
            return LinkTarget.UPDATE_INIT;
        }
        return LinkTarget.INIT;

    }

    public void onAddNewBranch() {
        if (branchList == null) {
            branchList = new ArrayList<StoreListLineBean>();
        }
        int branchId = 0;

        /*
         * if(InputChecker.isBlankOrNull(branchList.get(0).getBranchCode())) { operationBranchList = branchList;
         * branchList.clear(); }
         */

        StoreListLineBean branchItemList = new StoreListLineBean();
        if (branchList.size() > 0 && branchList.get(0).getBranchCode() != null) {
            String tem = branchList.get(branchList.size() - 1).getBranchCode();
            branchId = Integer.parseInt(tem.substring(tem.indexOf('B') + 1, tem.length()));
            branchId++;
            branchItemList.setBranchCode("B" + branchId);
        } else {
            branchId++;
            branchItemList.setBranchCode("B" + branchId);
        }

        branchList.add(branchItemList);
    }

    public void deleteRow(StoreListLineBean branchItem) {
        branchList.remove(branchItem);
        removedBranchList.add(branchItem);
    }

    @Action
    public String deleteBranch(StoreListLineBean branchItem) {

        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }

        branchList.remove(branchItem);
        removedBranchList.add(branchItem);

        return LinkTarget.OK;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isDoReload() {
        return doReload;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(boolean doReload) {
        this.doReload = doReload;
    }

    public StoreManagementHeaderBean getStoreManagementHeaderBean() {
        return storeManagementHeaderBean;
    }

    public void setStoreManagementHeaderBean(StoreManagementHeaderBean storeManagementHeaderBean) {
        this.storeManagementHeaderBean = storeManagementHeaderBean;
    }

    public StoreManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(StoreManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public List<StoreListLineBean> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<StoreListLineBean> branchList) {
        this.branchList = branchList;
    }

    public List<StoreListLineBean> getOperationBranchList() {
        return operationBranchList;
    }

    public void setOperationBranchList(List<StoreListLineBean> operationBranchList) {
        this.operationBranchList = operationBranchList;
    }

    public StoreListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(StoreListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean isDisable) {
        this.isDisable = isDisable;
    }

    public StoreManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(StoreManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public List<StoreListLineBean> getRemovedBranchList() {
        return removedBranchList;
    }

    public void setRemovedBranchList(List<StoreListLineBean> removedBranchList) {
        this.removedBranchList = removedBranchList;
    }

    public int getTempStoreId() {
        return tempStoreId;
    }

    public void setTempStoreId(int tempStoreId) {
        this.tempStoreId = tempStoreId;
    }

}
