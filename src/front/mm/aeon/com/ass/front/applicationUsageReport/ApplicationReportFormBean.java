/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationUsageReport;

import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ApplicationSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("applicationReportFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class ApplicationReportFormBean extends AbstractFormBean implements IRequest, IResponse {
    /**
     * 
     */
    private static final long serialVersionUID = 1860059025584262364L;

    private ApplicationSearchReqDto applicationSearchReqDto;

    private List<ApplicationReportListLineBean> lineBeans;

    private LazyDataModel<ApplicationReportListLineBean> lazyModel;

    private ApplicationReportSearchLineBean applicationReportSearchLineBean;

    private boolean init = true;

    private int pageFirst;

    private Date rptStrFrom;

    private Date rptStrTo;

    private Date lstUseFrom;

    private Date lstUseTo;

    private int totalCount;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        applicationReportSearchLineBean = new ApplicationReportSearchLineBean();
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            lazyModel = new ReportListPaginationController(totalCount, applicationSearchReqDto);
        }
        return LinkTarget.OK;
    }

    @Action
    public String export() {
        return LinkTarget.OK;
    }

    public void reset() {
        applicationReportSearchLineBean = new ApplicationReportSearchLineBean();
        rptStrFrom = null;
        rptStrTo = null;
        lstUseFrom = null;
        lstUseTo = null;
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

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public List<ApplicationReportListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<ApplicationReportListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<ApplicationReportListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ApplicationReportListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public ApplicationReportSearchLineBean getApplicationReportSearchLineBean() {
        return applicationReportSearchLineBean;
    }

    public void setApplicationReportSearchLineBean(ApplicationReportSearchLineBean applicationReportSearchLineBean) {
        this.applicationReportSearchLineBean = applicationReportSearchLineBean;
    }

    public Date getRptStrTo() {
        return rptStrTo;
    }

    public void setRptStrTo(Date rptStrTo) {
        this.rptStrTo = rptStrTo;
    }

    public Date getLstUseFrom() {
        return lstUseFrom;
    }

    public void setLstUseFrom(Date lstUseFrom) {
        this.lstUseFrom = lstUseFrom;
    }

    public Date getLstUseTo() {
        return lstUseTo;
    }

    public void setLstUseTo(Date lstUseTo) {
        this.lstUseTo = lstUseTo;
    }

    public Date getRptStrFrom() {
        return rptStrFrom;
    }

    public void setRptStrFrom(Date rptStrFrom) {
        this.rptStrFrom = rptStrFrom;
    }

    public ApplicationSearchReqDto getApplicationSearchReqDto() {
        return applicationSearchReqDto;
    }

    public void setApplicationSearchReqDto(ApplicationSearchReqDto applicationSearchReqDto) {
        this.applicationSearchReqDto = applicationSearchReqDto;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
