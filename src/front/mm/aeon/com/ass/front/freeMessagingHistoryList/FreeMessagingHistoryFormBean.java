/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeMessagingHistoryList;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.freeMessagingHistorySearch.FreeMessagingHistorySearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("freeMessagingHistoryFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class FreeMessagingHistoryFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8622270323003518015L;
    private FreeMessagingHistorySearchReqDto freeMessagingHistorySearchReqDto;

    private List<FreeMessagingHistoryLineBean> lineBeanList;

    private LazyDataModel<FreeMessagingHistoryLineBean> lazyModel;

    private FreeMessagingHistoryHeaderBean headerBean;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        headerBean = new FreeMessagingHistoryHeaderBean();
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        lazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            lazyModel = new FreeMessagingHistoryPaginationController(totalCount, freeMessagingHistorySearchReqDto);
        }

        return LinkTarget.OK;
    }

    public void reset() {
        headerBean = new FreeMessagingHistoryHeaderBean();
    }

    public FreeMessagingHistorySearchReqDto getFreeMessagingHistorySearchReqDto() {
        return freeMessagingHistorySearchReqDto;
    }

    public void setFreeMessagingHistorySearchReqDto(FreeMessagingHistorySearchReqDto freeMessagingHistorySearchReqDto) {
        this.freeMessagingHistorySearchReqDto = freeMessagingHistorySearchReqDto;
    }

    public List<FreeMessagingHistoryLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<FreeMessagingHistoryLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public LazyDataModel<FreeMessagingHistoryLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<FreeMessagingHistoryLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public FreeMessagingHistoryHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(FreeMessagingHistoryHeaderBean headerBean) {
        this.headerBean = headerBean;
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

}
