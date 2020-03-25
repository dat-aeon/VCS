/**************************************************************************
 * $Date : 2019-01-23$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqManagement;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.faqList.FaqListLineBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("faqManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class FaqManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1583457870206052674L;

    @In(required = false, value = "faqUpdateParam")
    @Out(required = false, value = "faqUpdateParam")
    private FaqManagementHeaderBean updateParam;
    
    private FaqManagementHeaderBean backUpHeaderBean;

    private FaqManagementHeaderBean faqManagementHeaderBean;

    @In(required = false, value = "categoryList")
    @Out(required = false, value = "categoryList")
    private List<FaqListLineBean> categorylineBeansList;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;
    private boolean init = true;

    private boolean isUpdate;

    @Begin(nested = true)
    public String init() {
        this.getMessageContainer().clearAllMessages(true);
        
        if(this.categorylineBeansList.size() == 0) {
            MessageBean msgBean = new MessageBean(MessageId.MI0008);
            msgBean.setMessageType(MessageType.INFO);
            this.getMessageContainer().addMessage(msgBean);
        }
        this.faqManagementHeaderBean = new FaqManagementHeaderBean();
        init = false;
        isUpdate = false;
        return LinkTarget.REGISTER;
    }

    @Action
    public String register() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.faqManagementHeaderBean = new FaqManagementHeaderBean();
        return LinkTarget.OK;
    }

    @Begin(join = true)
    public void updateInit() {
        this.getMessageContainer().clearAllMessages(true);

        init = false;
        isUpdate = true;
        this.faqManagementHeaderBean = new FaqManagementHeaderBean();
        this.faqManagementHeaderBean.setAnswerEng(updateParam.getAnswerEng());
        this.faqManagementHeaderBean.setAnswerMyan(updateParam.getAnswerMyan());
        this.faqManagementHeaderBean.setQuestionEng(updateParam.getQuestionEng());
        this.faqManagementHeaderBean.setQuestionMyan(updateParam.getQuestionMyan());
        this.faqManagementHeaderBean.setCategoryName(updateParam.getCategoryName());
        this.faqManagementHeaderBean.setCategoryId(updateParam.getCategoryId());
        this.faqManagementHeaderBean.setFaqId(updateParam.getFaqId());
        this.faqManagementHeaderBean.setUpdatedTime(updateParam.getUpdatedTime());
        this.faqManagementHeaderBean.setForUpdate(true);
        
        this.backUpHeaderBean = new FaqManagementHeaderBean().copyFaqManagementHeaderBean(faqManagementHeaderBean);
    }

    @Action
    public String update() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.init = true;
        this.faqManagementHeaderBean = null;
        this.doReload = new Boolean(true);

        return LinkTarget.SEARCH;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        init = true;
        this.faqManagementHeaderBean = null;
        return LinkTarget.BACK;
    }

    public void clear() {
        this.getMessageContainer().clearAllMessages(true);
        /*this.faqManagementHeaderBean = new FaqManagementHeaderBean();
        return LinkTarget.REGISTER;*/
        init = true;
        this.faqManagementHeaderBean = new FaqManagementHeaderBean();
    }
    
    public void reset() {
        this.getMessageContainer().clearAllMessages(true);
        /*this.faqManagementHeaderBean = new FaqManagementHeaderBean();
        return LinkTarget.REGISTER;*/
        init = true;
        this.faqManagementHeaderBean = new FaqManagementHeaderBean().copyFaqManagementHeaderBean(backUpHeaderBean);
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

    public FaqManagementHeaderBean getFaqManagementHeaderBean() {
        return faqManagementHeaderBean;
    }

    public void setFaqManagementHeaderBean(FaqManagementHeaderBean faqManagementHeaderBean) {
        this.faqManagementHeaderBean = faqManagementHeaderBean;
    }

    public FaqManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(FaqManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public List<FaqListLineBean> getCategorylineBeansList() {
        return categorylineBeansList;
    }

    public void setCategorylineBeansList(List<FaqListLineBean> categorylineBeansList) {
        this.categorylineBeansList = categorylineBeansList;
    }

    public FaqManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(FaqManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

}
