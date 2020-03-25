/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.importMemberSearch.ImportMemberSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("importMemberListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class ImportMemberListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -532801949885579872L;

    private List<ImportCustomerAgreementListLineBean> importCustomerAgreementListLineBeanList;

    private ImportMemberSearchReqDto importMemberSearchReqDto;

    private ImportMemberListHeaderBean searchHeaderBean;

    private ImportMemberListLineBean lineBean;

    private List<ImportMemberListLineBean> lineBeanList;

    private LazyDataModel<ImportMemberListLineBean> lazyModel;

    private ArrayList<SelectItem> genderSelectItemList;

    private boolean init = true;

    private int pageFirst;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int totalCount;

    private Integer importCustomerId;

    @In(required = false, value = "clear")
    @Out(required = false, value = "clear")
    private boolean clear;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new ImportMemberListHeaderBean();
        this.doReload = new Boolean(true);
        init = false;
        clear = false;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            lazyModel = new ImportMemberListPaginationController(totalCount, importMemberSearchReqDto);
        }

        return LinkTarget.OK;
    }

    @Action
    public void importCustomerAgreementListDetail() {
        getMessageContainer().clearAllMessages(true);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('importCustomerAgreementListDialog').show();");
        context.update("importCustomerAgreementListForm");
    }

    public String back() {
        getMessageContainer().clearAllMessages(true);
        doReload = true;
        return LinkTarget.BACK;
    }

    public void reset() {
        this.searchHeaderBean = new ImportMemberListHeaderBean();
    }

    public String getGenderValue() {
        for (SelectItem selectItem : genderSelectItemList) {
            if (this.lineBean.getGender().equals(selectItem.getValue())) {
                return selectItem.getLabel();
            }
        }
        return "";
    }

    public ImportMemberListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(ImportMemberListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public ImportMemberListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(ImportMemberListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public List<ImportMemberListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<ImportMemberListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public LazyDataModel<ImportMemberListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ImportMemberListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
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

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public ArrayList<SelectItem> getGenderSelectItemList() {
        return genderSelectItemList;
    }

    public void setGenderSelectItemList(ArrayList<SelectItem> genderSelectItemList) {
        this.genderSelectItemList = genderSelectItemList;
    }

    public ImportMemberSearchReqDto getImportMemberSearchReqDto() {
        return importMemberSearchReqDto;
    }

    public void setImportMemberSearchReqDto(ImportMemberSearchReqDto importMemberSearchReqDto) {
        this.importMemberSearchReqDto = importMemberSearchReqDto;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getImportCustomerId() {
        return importCustomerId;
    }

    public void setImportCustomerId(Integer importCustomerId) {
        this.importCustomerId = importCustomerId;
    }

    public List<ImportCustomerAgreementListLineBean> getImportCustomerAgreementListLineBeanList() {
        return importCustomerAgreementListLineBeanList;
    }

    public void setImportCustomerAgreementListLineBeanList(
            List<ImportCustomerAgreementListLineBean> importCustomerAgreementListLineBeanList) {
        this.importCustomerAgreementListLineBeanList = importCustomerAgreementListLineBeanList;
    }

    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

}
