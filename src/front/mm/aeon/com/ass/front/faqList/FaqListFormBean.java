/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqList;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.faqManagement.FaqManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("faqListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class FaqListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -7135450431138864114L;

    private List<FaqListLineBean> lineBeans;

    private LazyDataModel<FaqListLineBean> lazyModel;

    private FaqListLineBean lineBean;

    @In(required = false, value = "categoryList")
    @Out(required = false, value = "categoryList")
    private List<FaqListLineBean> categorylineBeansList;

    @Out(required = false, value = "faqUpdateParam")
    private FaqManagementHeaderBean updateParam;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int categoryDel;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        this.doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeans.size() != 0) {
            lazyModel = new FaqListPaginationController(lineBeans.size(), lineBeans);
        }

        return LinkTarget.OK;
    }

    public String prepareUpdate(FaqListLineBean lineBean) {

        this.updateParam = new FaqManagementHeaderBean();
        this.updateParam.setAnswerEng(lineBean.getAnswerEng());
        this.updateParam.setAnswerMyan(lineBean.getAnswerMyan());
        this.updateParam.setQuestionEng(lineBean.getQuestionEng());
        this.updateParam.setQuestionMyan(lineBean.getQuestionMyan());
        this.updateParam.setCategoryName(lineBean.getCategoryName());
        this.updateParam.setCategoryId(lineBean.getCategoryId());
        this.updateParam.setFaqId(lineBean.getFaqId());
        this.updateParam.setUpdatedTime(lineBean.getUpdatedTime());

        return LinkTarget.UPDATE_INIT;
    }

    public String detail(FaqListLineBean lineBean) {
        this.getMessageContainer().clearAllMessages(true);
        this.lineBean = lineBean;
        return LinkTarget.DETAIL;
    }

    @Action
    public String toggleValidStatus(FaqListLineBean lineBean) {
        this.lineBean = null;
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    public String prepareRegister() {
        this.updateParam = null;
        this.doReload = new Boolean(true);
        return LinkTarget.REGISTER;
    }

    public FaqManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(FaqManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public List<FaqListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<FaqListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<FaqListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<FaqListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public FaqListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(FaqListLineBean lineBean) {
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

    public List<FaqListLineBean> getCategorylineBeansList() {
        return categorylineBeansList;
    }

    public void setCategorylineBeansList(List<FaqListLineBean> categorylineBeansList) {
        this.categorylineBeansList = categorylineBeansList;
    }

    public int getCategoryDel() {
        return categoryDel;
    }

    public void setCategoryDel(int categoryDel) {
        this.categoryDel = categoryDel;
    }

}
