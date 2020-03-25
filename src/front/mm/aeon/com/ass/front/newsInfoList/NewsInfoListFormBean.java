/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoList;

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

import mm.aeon.com.ass.base.dto.newsInfoSearch.NewsInfoSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.front.newsInfoManagement.NewsInfoManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("newsInfoListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class NewsInfoListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 975166208748325376L;

    private NewsInfoSearchReqDto newsInfoSearchReqDto;

    private NewsInfoListLineBean newsInfoListLineBean;

    private List<NewsInfoListLineBean> newsInfoListLineBeanList;

    private LazyDataModel<NewsInfoListLineBean> newsInfoListLineBeanLazyModel;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    private String tempUploadImageFilePath;

    @Out(required = false, value = "newsInfoUpdateParam")
    private NewsInfoManagementHeaderBean updateParam;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        newsInfoListLineBeanLazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            newsInfoListLineBeanLazyModel = new NewsInfoListPaginationController(totalCount, newsInfoSearchReqDto);
        }

        return LinkTarget.OK;
    }

    @Action
    public String delete() {
        doReload = false;
        newsInfoListLineBean = null;
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            doReload = true;
        }

        return LinkTarget.OK;
    }

    public String detail(NewsInfoListLineBean newsInfoListLineBean) {
        getMessageContainer().clearAllMessages(true);

        if (!StringUtils.isEmpty(newsInfoListLineBean.getImagePath())) {
            String uploadPath = CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getNewsInfoUploadImageFolder();
            File sourceFile = new File(uploadPath + newsInfoListLineBean.getImagePath());
            File destinationFile = new File(FileHandler.getSystemPath() + "/temp"
                    + CommonUtil.getNewsInfoUploadImageFolder() + newsInfoListLineBean.getImagePath());

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

            tempUploadImageFilePath =
                    "/temp" + CommonUtil.getNewsInfoUploadImageFolder() + newsInfoListLineBean.getImagePath();
        }

        this.newsInfoListLineBean = newsInfoListLineBean;
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

    public String prepareUpdate(NewsInfoListLineBean lineBean) {
        updateParam = new NewsInfoManagementHeaderBean();
        updateParam.setNewsInfoId(lineBean.getNewsInfoId());
        updateParam.setTitleEng(lineBean.getTitleEng());
        updateParam.setTitleMyn(lineBean.getTitleMyn());
        updateParam.setContentEng(lineBean.getContentEng());
        updateParam.setContentMyn(lineBean.getContentMyn());
        updateParam.setPublishedFromDate(lineBean.getPublishedFromDate());
        updateParam.setPublishedToDate(lineBean.getPublishedToDate());
        updateParam.setUploadedImageFilePath(lineBean.getImagePath());
        updateParam.setLongitude(lineBean.getLongitude());
        updateParam.setLatitude(lineBean.getLatitude());
        updateParam.setNewsUrl(lineBean.getNewsUrl());

        if (!StringUtils.isEmpty(lineBean.getImagePath())) {
            String uploadPath = CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getNewsInfoUploadImageFolder();
            File sourceFile = new File(uploadPath + lineBean.getImagePath());
            File destinationFile = new File(FileHandler.getSystemPath() + "/temp"
                    + CommonUtil.getNewsInfoUploadImageFolder() + lineBean.getImagePath());

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

    public List<NewsInfoListLineBean> getNewsInfoListLineBeanList() {
        return newsInfoListLineBeanList;
    }

    public void setNewsInfoListLineBeanList(List<NewsInfoListLineBean> newsInfoListLineBeanList) {
        this.newsInfoListLineBeanList = newsInfoListLineBeanList;
    }

    public LazyDataModel<NewsInfoListLineBean> getNewsInfoListLineBeanLazyModel() {
        return newsInfoListLineBeanLazyModel;
    }

    public void setNewsInfoListLineBeanLazyModel(LazyDataModel<NewsInfoListLineBean> newsInfoListLineBeanLazyModel) {
        this.newsInfoListLineBeanLazyModel = newsInfoListLineBeanLazyModel;
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

    public NewsInfoSearchReqDto getNewsInfoSearchReqDto() {
        return newsInfoSearchReqDto;
    }

    public void setNewsInfoSearchReqDto(NewsInfoSearchReqDto newsInfoSearchReqDto) {
        this.newsInfoSearchReqDto = newsInfoSearchReqDto;
    }

    public NewsInfoListLineBean getNewsInfoListLineBean() {
        return newsInfoListLineBean;
    }

    public void setNewsInfoListLineBean(NewsInfoListLineBean newsInfoListLineBean) {
        this.newsInfoListLineBean = newsInfoListLineBean;
    }

    public NewsInfoManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(NewsInfoManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
    }

}
