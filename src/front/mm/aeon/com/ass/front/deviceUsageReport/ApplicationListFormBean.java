/**************************************************************************
 * $Date: 2019-02-12$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.deviceUsageReport;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.ApplicationReportSearch.ActualAppUsageSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("applicationListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class ApplicationListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -7135450431138864114L;

    private List<ApplicationListLineBean> lineBeans;

    private LazyDataModel<ApplicationListLineBean> lazyModel;

    private ApplicationListLineBean lineBean;

    private ApplicationListHeaderBean appHeaderBean;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;
    
    private boolean init = true;

    private int pageFirst;

    private int totalCount;

    private ActualAppUsageSearchReqDto actualAppUsageSearchReqDto;

    @Begin(nested = true)
    public void init() {
        appHeaderBean = new ApplicationListHeaderBean();
        this.getMessageContainer().clearAllMessages(true);
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        lazyModel = null;
        doReload = false;
        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            lazyModel = new ApplicationListPaginationController(totalCount, actualAppUsageSearchReqDto);
        }

        return LinkTarget.OK;
    }

    public void reset() {
        this.appHeaderBean = new ApplicationListHeaderBean();
    }

    public List<ApplicationListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<ApplicationListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<ApplicationListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ApplicationListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public ApplicationListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(ApplicationListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public ApplicationListHeaderBean getAppHeaderBean() {
        return appHeaderBean;
    }

    public void setAppHeaderBean(ApplicationListHeaderBean appHeaderBean) {
        this.appHeaderBean = appHeaderBean;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ActualAppUsageSearchReqDto getActualAppUsageSearchReqDto() {
        return actualAppUsageSearchReqDto;
    }

    public void setActualAppUsageSearchReqDto(ActualAppUsageSearchReqDto actualAppUsageSearchReqDto) {
        this.actualAppUsageSearchReqDto = actualAppUsageSearchReqDto;
    }

}
