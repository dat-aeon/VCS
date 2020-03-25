/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeUsageReport;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.freeUsageReportSearch.FreeUsageReportSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("freeUsageReportFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class FreeUsageReportFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -359830508895927707L;

    private List<FreeUsageReportLineBean> freeUsageReportLineBeanList;

    private LazyDataModel<FreeUsageReportLineBean> freeUsageReportLineBeanlazyModel;

    private FreeUsageReportSearchLineBean freeUsageReportSearchLineBean;

    private FreeUsageReportSearchReqDto freeUsageReportSearchReqDto;

    private boolean init = true;

    private int totalCount;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        freeUsageReportSearchLineBean = new FreeUsageReportSearchLineBean();
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        freeUsageReportLineBeanlazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            freeUsageReportLineBeanlazyModel =
                    new FreeUsageReportPaginationController(totalCount, freeUsageReportSearchReqDto);
        }
        return LinkTarget.OK;
    }

    public void reset() {
        freeUsageReportSearchLineBean = new FreeUsageReportSearchLineBean();
    }

    public List<FreeUsageReportLineBean> getFreeUsageReportLineBeanList() {
        return freeUsageReportLineBeanList;
    }

    public void setFreeUsageReportLineBeanList(List<FreeUsageReportLineBean> freeUsageReportLineBeanList) {
        this.freeUsageReportLineBeanList = freeUsageReportLineBeanList;
    }

    public LazyDataModel<FreeUsageReportLineBean> getFreeUsageReportLineBeanlazyModel() {
        return freeUsageReportLineBeanlazyModel;
    }

    public void setFreeUsageReportLineBeanlazyModel(
            LazyDataModel<FreeUsageReportLineBean> freeUsageReportLineBeanlazyModel) {
        this.freeUsageReportLineBeanlazyModel = freeUsageReportLineBeanlazyModel;
    }

    public FreeUsageReportSearchLineBean getFreeUsageReportSearchLineBean() {
        return freeUsageReportSearchLineBean;
    }

    public void setFreeUsageReportSearchLineBean(FreeUsageReportSearchLineBean freeUsageReportSearchLineBean) {
        this.freeUsageReportSearchLineBean = freeUsageReportSearchLineBean;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public FreeUsageReportSearchReqDto getFreeUsageReportSearchReqDto() {
        return freeUsageReportSearchReqDto;
    }

    public void setFreeUsageReportSearchReqDto(FreeUsageReportSearchReqDto freeUsageReportSearchReqDto) {
        this.freeUsageReportSearchReqDto = freeUsageReportSearchReqDto;
    }

}
