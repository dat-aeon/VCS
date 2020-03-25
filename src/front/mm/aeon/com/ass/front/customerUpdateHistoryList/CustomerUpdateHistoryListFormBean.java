/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerUpdateHistoryList;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.customerUpdateHistorySearch.CustomerUpdateHistoryInfoSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("customerUpdateHistoryInfoListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CustomerUpdateHistoryListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 975166208748325376L;

    private CustomerUpdateHistoryListHeaderBean searchHeaderBean;

    private CustomerUpdateHistoryInfoSearchReqDto customerUpdateHistoryInfoSearchReqDto;

    private CustomerUpdateHistoryListLineBean customerUpdateHistoryListLineBean;

    private List<CustomerUpdateHistoryListLineBean> customerUpdateHistoryListLineBeanList;

    private LazyDataModel<CustomerUpdateHistoryListLineBean> customerUpdateHistoryListLineBeanLazyModel;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    private String tempUploadImageFilePath;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new CustomerUpdateHistoryListHeaderBean();
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        customerUpdateHistoryListLineBeanLazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            customerUpdateHistoryListLineBeanLazyModel = new CustomerUpdateHistoryListPaginationController(totalCount,
                    customerUpdateHistoryInfoSearchReqDto);
        }

        return LinkTarget.OK;
    }

    public void reset() {
        this.searchHeaderBean = new CustomerUpdateHistoryListHeaderBean();
    }

    public String back() {
        return LinkTarget.BACK;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
    }

    public CustomerUpdateHistoryInfoSearchReqDto getCustomerUpdateHistoryInfoSearchReqDto() {
        return customerUpdateHistoryInfoSearchReqDto;
    }

    public void setCustomerUpdateHistoryInfoSearchReqDto(
            CustomerUpdateHistoryInfoSearchReqDto customerUpdateHistoryInfoSearchReqDto) {
        this.customerUpdateHistoryInfoSearchReqDto = customerUpdateHistoryInfoSearchReqDto;
    }

    public CustomerUpdateHistoryListLineBean getCustomerUpdateHistoryListLineBean() {
        return customerUpdateHistoryListLineBean;
    }

    public void setCustomerUpdateHistoryListLineBean(
            CustomerUpdateHistoryListLineBean CustomerUpdateHistoryListLineBean) {
        this.customerUpdateHistoryListLineBean = CustomerUpdateHistoryListLineBean;
    }

    public List<CustomerUpdateHistoryListLineBean> getCustomerUpdateHistoryListLineBeanList() {
        return customerUpdateHistoryListLineBeanList;
    }

    public void setCustomerUpdateHistoryListLineBeanList(
            List<CustomerUpdateHistoryListLineBean> CustomerUpdateHistoryListLineBeanList) {
        this.customerUpdateHistoryListLineBeanList = CustomerUpdateHistoryListLineBeanList;
    }

    public LazyDataModel<CustomerUpdateHistoryListLineBean> getCustomerUpdateHistoryListLineBeanLazyModel() {
        return customerUpdateHistoryListLineBeanLazyModel;
    }

    public void setCustomerUpdateHistoryListLineBeanLazyModel(
            LazyDataModel<CustomerUpdateHistoryListLineBean> CustomerUpdateHistoryListLineBeanLazyModel) {
        this.customerUpdateHistoryListLineBeanLazyModel = CustomerUpdateHistoryListLineBeanLazyModel;
    }

    public CustomerUpdateHistoryListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(CustomerUpdateHistoryListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

}
