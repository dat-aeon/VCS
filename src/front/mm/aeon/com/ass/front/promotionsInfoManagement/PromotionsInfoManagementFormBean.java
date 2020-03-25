/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoManagement;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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

@Name("promotionsInfoManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class PromotionsInfoManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 5281625987308775003L;

    @In(required = false, value = "promotionsInfoUpdateParam")
    @Out(required = false, value = "promotionsInfoUpdateParam")
    private PromotionsInfoManagementHeaderBean updateParam;

    private PromotionsInfoManagementHeaderBean promotionsInfoManagementHeaderBean;

    private PromotionsInfoManagementHeaderBean backUpHeaderBean;

    private PromotionsInfoManagementImageBean promotionsInfoManagementImageBean;

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
        promotionsInfoManagementHeaderBean = new PromotionsInfoManagementHeaderBean();
        promotionsInfoManagementImageBean = new PromotionsInfoManagementImageBean();
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
        promotionsInfoManagementHeaderBean = new PromotionsInfoManagementHeaderBean();
        promotionsInfoManagementHeaderBean =
                new PromotionsInfoManagementHeaderBean().copyPromotionsInfoManagementHeaderBean(updateParam);

        if (!StringUtils.isEmpty(updateParam.getUploadedImageFilePath())) {
            tempUploadImageFilePath =
                    "/temp" + CommonUtil.getPromotionsInfoUploadImageFolder() + updateParam.getUploadedImageFilePath();
        }

        backUpHeaderBean = new PromotionsInfoManagementHeaderBean()
                .copyPromotionsInfoManagementHeaderBean(promotionsInfoManagementHeaderBean);
    }

    @Action
    public String importImage() {
        if (getMessageContainer().checkMessage(MessageType.INFO)) {
            promotionsInfoManagementImageBean = new PromotionsInfoManagementImageBean();
            return LinkTarget.OK;
        } else {
            return LinkTarget.ERROR;
        }
    }

    public void uploadImage(FileUploadEvent event) {
        if (promotionsInfoManagementImageBean == null) {
            promotionsInfoManagementImageBean = new PromotionsInfoManagementImageBean();
        }
        if (event != null) {
            promotionsInfoManagementImageBean.setPromotionsInfoImage(event.getFile());
        }
    }

    @Action
    public String register() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;

        } else if (updateParam != null) {
            if (!StringUtils.isEmpty(oldUploadImageFilePath)) {
                String oldUploadImageFolderPath = CommonUtil.getUploadImageBaseFilePath()
                        + CommonUtil.getPromotionsInfoUploadImageFolder() + oldUploadImageFilePath;
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
            promotionsInfoManagementHeaderBean = new PromotionsInfoManagementHeaderBean();
            init = true;
            tempUploadImageFilePath = null;
            oldUploadImageFilePath = null;
            uploadImageFileName = null;
            doReload = new Boolean(true);
            return LinkTarget.SEARCH;
        }
        promotionsInfoManagementHeaderBean = new PromotionsInfoManagementHeaderBean();
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

        promotionsInfoManagementHeaderBean = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        return LinkTarget.BACK;
    }

    public void clear() {
        promotionsInfoManagementHeaderBean = new PromotionsInfoManagementHeaderBean();
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        promotionsInfoManagementImageBean = new PromotionsInfoManagementImageBean();
        getMessageContainer().clearAllMessages(true);
    }

    public void reset() {
        promotionsInfoManagementHeaderBean =
                new PromotionsInfoManagementHeaderBean().copyPromotionsInfoManagementHeaderBean(backUpHeaderBean);
        if (!StringUtils.isEmpty(backUpHeaderBean.getUploadedImageFilePath())) {
            tempUploadImageFilePath = "/temp" + backUpHeaderBean.getUploadedImageFilePath();
        }

        oldUploadImageFilePath = null;
    }

    public PromotionsInfoManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(PromotionsInfoManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public PromotionsInfoManagementHeaderBean getPromotionsInfoManagementHeaderBean() {
        return promotionsInfoManagementHeaderBean;
    }

    public void setPromotionsInfoManagementHeaderBean(
            PromotionsInfoManagementHeaderBean promotionsInfoManagementHeaderBean) {
        this.promotionsInfoManagementHeaderBean = promotionsInfoManagementHeaderBean;
    }

    public PromotionsInfoManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(PromotionsInfoManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public PromotionsInfoManagementImageBean getPromotionsInfoManagementImageBean() {
        return promotionsInfoManagementImageBean;
    }

    public void setPromotionsInfoManagementImageBean(
            PromotionsInfoManagementImageBean promotionsInfoManagementImageBean) {
        this.promotionsInfoManagementImageBean = promotionsInfoManagementImageBean;
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

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

}
