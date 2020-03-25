/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoManagement;

import java.io.IOException;

import org.drools.core.util.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.event.FileUploadEvent;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("newsInfoManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class NewsInfoManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -8559169100855571603L;

    @In(required = false, value = "newsInfoUpdateParam")
    @Out(required = false, value = "newsInfoUpdateParam")
    private NewsInfoManagementHeaderBean updateParam;

    private NewsInfoManagementHeaderBean newsInfoManagementHeaderBean;

    private NewsInfoManagementHeaderBean backUpHeaderBean;

    private NewsInfoManagementImageBean newsInfoManagementImageBean;

    private String tempUploadImageFilePath;

    private String oldUploadImageFilePath;

    private String uploadImageFileName;

    private boolean init = true;

    private boolean update;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(join = true)
    public String init() {
        getMessageContainer().clearAllMessages(true);
        newsInfoManagementHeaderBean = new NewsInfoManagementHeaderBean();
        newsInfoManagementImageBean = new NewsInfoManagementImageBean();
        init = false;
        update = false;
        doReload = new Boolean(true);
        return LinkTarget.REGISTER;
    }

    @Begin(join = true)
    public void updateInit() {
        getMessageContainer().clearAllMessages(true);

        init = false;
        update = true;
        newsInfoManagementHeaderBean = new NewsInfoManagementHeaderBean();
        newsInfoManagementHeaderBean = new NewsInfoManagementHeaderBean().copyNewsInfoManagementHeaderBean(updateParam);

        if (!StringUtils.isEmpty(updateParam.getUploadedImageFilePath())) {
            tempUploadImageFilePath =
                    "/temp" + CommonUtil.getNewsInfoUploadImageFolder() + updateParam.getUploadedImageFilePath();
        }

        backUpHeaderBean =
                new NewsInfoManagementHeaderBean().copyNewsInfoManagementHeaderBean(newsInfoManagementHeaderBean);
    }

    @Action
    public String importImage() {
        if (getMessageContainer().checkMessage(MessageType.INFO)) {
            newsInfoManagementImageBean = new NewsInfoManagementImageBean();
            return LinkTarget.OK;
        } else {
            return LinkTarget.ERROR;
        }
    }

    public void uploadImage(FileUploadEvent event) {
        if (newsInfoManagementImageBean == null) {
            newsInfoManagementImageBean = new NewsInfoManagementImageBean();
        }
        if (event != null) {
            newsInfoManagementImageBean.setNewsInfoImage(event.getFile());
        }
    }

    @Action
    public String register() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;

        } else if (updateParam != null) {

            if (!StringUtils.isEmpty(oldUploadImageFilePath)) {
                String oldUploadImageFolderPath = CommonUtil.getUploadImageBaseFilePath()
                        + CommonUtil.getNewsInfoUploadImageFolder() + oldUploadImageFilePath;
                try {
                    System.gc();// Added this part
                    Thread.sleep(1000);// This part gives the Bufferedreaders and the InputStreams time to close
                                       // Completely
                    FileHandler.forceDelete(oldUploadImageFolderPath);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            newsInfoManagementHeaderBean = new NewsInfoManagementHeaderBean();
            init = true;
            tempUploadImageFilePath = null;
            oldUploadImageFilePath = null;
            uploadImageFileName = null;
            doReload = new Boolean(true);
            return LinkTarget.SEARCH;
        }
        newsInfoManagementHeaderBean = new NewsInfoManagementHeaderBean();
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        doReload = new Boolean(true);
        return LinkTarget.OK;
    }

    public String back() {
        getMessageContainer().clearAllMessages(true);
        init = true;
        updateParam = null;

        newsInfoManagementHeaderBean = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        return LinkTarget.BACK;
    }

    public void clear() {
        newsInfoManagementHeaderBean = new NewsInfoManagementHeaderBean();
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        newsInfoManagementImageBean = new NewsInfoManagementImageBean();
        getMessageContainer().clearAllMessages(true);
    }

    public void reset() {
        newsInfoManagementHeaderBean =
                new NewsInfoManagementHeaderBean().copyNewsInfoManagementHeaderBean(backUpHeaderBean);
        if (!StringUtils.isEmpty(backUpHeaderBean.getUploadedImageFilePath())) {
            tempUploadImageFilePath = "/temp" + backUpHeaderBean.getUploadedImageFilePath();
        }

        oldUploadImageFilePath = null;
    }

    public NewsInfoManagementHeaderBean getNewsInfoManagementHeaderBean() {
        return newsInfoManagementHeaderBean;
    }

    public void setNewsInfoManagementHeaderBean(NewsInfoManagementHeaderBean newsInfoManagementHeaderBean) {
        this.newsInfoManagementHeaderBean = newsInfoManagementHeaderBean;
    }

    public NewsInfoManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(NewsInfoManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public NewsInfoManagementImageBean getNewsInfoManagementImageBean() {
        return newsInfoManagementImageBean;
    }

    public void setNewsInfoManagementImageBean(NewsInfoManagementImageBean newsInfoManagementImageBean) {
        this.newsInfoManagementImageBean = newsInfoManagementImageBean;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
    }

    public String getOldUploadImageFilePath() {
        return oldUploadImageFilePath;
    }

    public void setOldUploadImageFilePath(String oldUploadImageFilePath) {
        this.oldUploadImageFilePath = oldUploadImageFilePath;
    }

    public String getUploadImageFileName() {
        return uploadImageFileName;
    }

    public void setUploadImageFileName(String uploadImageFileName) {
        this.uploadImageFileName = uploadImageFileName;
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

    public NewsInfoManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(NewsInfoManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

}
