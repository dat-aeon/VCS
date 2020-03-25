/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoList;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.promotionsInfoSearch.PromotionsInfoSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.front.promotionsInfoManagement.PromotionsInfoManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("promotionsInfoListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class PromotionsInfoListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -1764450348067404714L;

    private PromotionsInfoSearchReqDto promotionsInfoSearchReqDto;

    private PromotionsInfoListLineBean promotionsInfoListLineBean;

    private List<PromotionsInfoListLineBean> promotionsInfoListLineBeanList;

    private LazyDataModel<PromotionsInfoListLineBean> promotionsInfoListLineBeanLazyModel;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    private String tempUploadImageFilePath;

    @Out(required = false, value = "promotionsInfoUpdateParam")
    private PromotionsInfoManagementHeaderBean updateParam;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        promotionsInfoListLineBeanLazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            promotionsInfoListLineBeanLazyModel =
                    new PromotionsInfoListPaginationController(totalCount, promotionsInfoSearchReqDto);
        }

        return LinkTarget.OK;
    }

    @Action
    public String delete() {
        doReload = false;
        promotionsInfoListLineBean = null;
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            doReload = true;
        }

        return LinkTarget.OK;
    }

    public String detail(PromotionsInfoListLineBean promotionsInfoListLineBean) {
        getMessageContainer().clearAllMessages(true);

        if (!StringUtils.isEmpty(promotionsInfoListLineBean.getImagePath())) {
            String uploadPath =
                    CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getPromotionsInfoUploadImageFolder();
            File sourceFile = new File(uploadPath + promotionsInfoListLineBean.getImagePath());
            File destinationFile = new File(FileHandler.getSystemPath() + "/temp"
                    + CommonUtil.getPromotionsInfoUploadImageFolder() + promotionsInfoListLineBean.getImagePath());

            try {
                System.gc();// Added this part
                Thread.sleep(1000);// This part gives the Bufferedreaders and the InputStreams time to close
                                   // Completely
                FileHandler.copyFile(sourceFile, destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tempUploadImageFilePath = "/temp" + CommonUtil.getPromotionsInfoUploadImageFolder()
                    + promotionsInfoListLineBean.getImagePath();
        }

        this.promotionsInfoListLineBean = promotionsInfoListLineBean;
        return LinkTarget.DETAIL;
    }

    public String back() {
        tempUploadImageFilePath = null;
        return LinkTarget.BACK;
    }

    public String prepareRegister() {
        updateParam = null;
        return LinkTarget.REGISTER;
    }

    public String prepareUpdate(PromotionsInfoListLineBean lineBean) {
        updateParam = new PromotionsInfoManagementHeaderBean();
        updateParam.setPromotionsInfoId(lineBean.getPromotionsInfoId());
        updateParam.setTitleEng(lineBean.getTitleEng());
        updateParam.setTitleMyn(lineBean.getTitleMyn());
        updateParam.setContentEng(lineBean.getContentEng());
        updateParam.setContentMyn(lineBean.getContentMyn());
        updateParam.setPublishedFromDate(lineBean.getPublishedFromDate());
        updateParam.setPublishedToDate(lineBean.getPublishedToDate());
        updateParam.setUploadedImageFilePath(lineBean.getImagePath());
        updateParam.setLongitude(lineBean.getLongitude());
        updateParam.setLatitude(lineBean.getLatitude());
        updateParam.setAnnouncementUrl(lineBean.getAnnouncementUrl());

        if (!StringUtils.isEmpty(lineBean.getImagePath())) {
            String uploadPath =
                    CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getPromotionsInfoUploadImageFolder();
            File sourceFile = new File(uploadPath + lineBean.getImagePath());
            File destinationFile = new File(FileHandler.getSystemPath() + "/temp"
                    + CommonUtil.getPromotionsInfoUploadImageFolder() + lineBean.getImagePath());

            try {
                System.gc();// Added this part
                Thread.sleep(1000);// This part gives the Bufferedreaders and the InputStreams time to close
                                   // Completely
                FileHandler.copyFile(sourceFile, destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return LinkTarget.UPDATE_INIT;
    }

    public PromotionsInfoSearchReqDto getPromotionsInfoSearchReqDto() {
        return promotionsInfoSearchReqDto;
    }

    public void setPromotionsInfoSearchReqDto(PromotionsInfoSearchReqDto promotionsInfoSearchReqDto) {
        this.promotionsInfoSearchReqDto = promotionsInfoSearchReqDto;
    }

    public PromotionsInfoListLineBean getPromotionsInfoListLineBean() {
        return promotionsInfoListLineBean;
    }

    public void setPromotionsInfoListLineBean(PromotionsInfoListLineBean promotionsInfoListLineBean) {
        this.promotionsInfoListLineBean = promotionsInfoListLineBean;
    }

    public List<PromotionsInfoListLineBean> getPromotionsInfoListLineBeanList() {
        return promotionsInfoListLineBeanList;
    }

    public void setPromotionsInfoListLineBeanList(List<PromotionsInfoListLineBean> promotionsInfoListLineBeanList) {
        this.promotionsInfoListLineBeanList = promotionsInfoListLineBeanList;
    }

    public LazyDataModel<PromotionsInfoListLineBean> getPromotionsInfoListLineBeanLazyModel() {
        return promotionsInfoListLineBeanLazyModel;
    }

    public void setPromotionsInfoListLineBeanLazyModel(
            LazyDataModel<PromotionsInfoListLineBean> promotionsInfoListLineBeanLazyModel) {
        this.promotionsInfoListLineBeanLazyModel = promotionsInfoListLineBeanLazyModel;
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

    public PromotionsInfoManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(PromotionsInfoManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

}
