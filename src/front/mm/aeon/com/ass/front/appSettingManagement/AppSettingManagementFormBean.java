/**************************************************************************
 * $Date : 2019-01-23$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.appSettingManagement;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("appSettingManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class AppSettingManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1583457870206052674L;

    private AppSettingManagementHeaderBean appSettingManagementHeaderBean;
    private AppSettingManagementHeaderBean appHearderBean;
    private AppSettingManagementHeaderBean backUpHeaderBean;
    
    private int noOfsecurityQuestion;
    private int noOfcharacterAnswer;

    public int getNoOfsecurityQuestion() {
        return noOfsecurityQuestion;
    }

    public void setNoOfsecurityQuestion(int noOfsecurityQuestion) {
        this.noOfsecurityQuestion = noOfsecurityQuestion;
    }

    public int getNoOfcharacterAnswer() {
        return noOfcharacterAnswer;
    }

    public void setNoOfcharacterAnswer(int noOfcharacterAnswer) {
        this.noOfcharacterAnswer = noOfcharacterAnswer;
    }

    private boolean init = true;
    private boolean updateFlag = true;

    @Begin(nested = true)
    public String init() {
        this.getMessageContainer().clearAllMessages(true);
        this.appSettingManagementHeaderBean = new AppSettingManagementHeaderBean();
        this.appHearderBean = new AppSettingManagementHeaderBean();
        init = false;
        return LinkTarget.REGISTER;
    }

    @Action
    public String update() {
        
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.appSettingManagementHeaderBean = new AppSettingManagementHeaderBean();
        init = false;
        updateFlag = true;
        return LinkTarget.OK;
    }

    @Action
    public String updateInit() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        updateFlag = false;
        this.appSettingManagementHeaderBean = new AppSettingManagementHeaderBean();
        appSettingManagementHeaderBean.setAppSettingId(appHearderBean.getAppSettingId());
        appSettingManagementHeaderBean.setNoOfsecurityQuestion(appHearderBean.getNoOfsecurityQuestion());
        appSettingManagementHeaderBean.setNoOfcharacterAnswer(appHearderBean.getNoOfcharacterAnswer());
        
        this.backUpHeaderBean = new AppSettingManagementHeaderBean().copyAppSettingManagementHeaderBean(appSettingManagementHeaderBean);
        
        return LinkTarget.OK;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        init = true;
        this.appSettingManagementHeaderBean = null;
        return LinkTarget.BACK;
    }
    
    public void reset() {
        this.getMessageContainer().clearAllMessages(true);
        //init = true;
       
        this.appSettingManagementHeaderBean = new AppSettingManagementHeaderBean().copyAppSettingManagementHeaderBean(backUpHeaderBean);
    }

    public AppSettingManagementHeaderBean getAppSettingManagementHeaderBean() {
        return appSettingManagementHeaderBean;
    }

    public void setAppSettingManagementHeaderBean(AppSettingManagementHeaderBean appSettingManagementHeaderBean) {
        this.appSettingManagementHeaderBean = appSettingManagementHeaderBean;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public AppSettingManagementHeaderBean getAppHearderBean() {
        return appHearderBean;
    }

    public void setAppHearderBean(AppSettingManagementHeaderBean appHearderBean) {
        this.appHearderBean = appHearderBean;
    }

    public AppSettingManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(AppSettingManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

}
