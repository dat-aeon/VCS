/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoList;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.categoryInfoSearch.CategoryInfoSearchReqDto;
import mm.aeon.com.ass.front.categoryInfoManagement.CategoryInfoManagementHeaderBean;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("categoryInfoListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CategoryInfoListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 975166208748325376L;

    private CategoryInfoSearchReqDto categoryInfoSearchReqDto;

    private CategoryInfoListLineBean categoryInfoListLineBean;

    private List<CategoryInfoListLineBean> categoryInfoListLineBeanList;

    private LazyDataModel<CategoryInfoListLineBean> categoryInfoListLineBeanLazyModel;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    private int couponDel;

    private String tempUploadImageFilePath;

    @Out(required = false, value = "categoryInfoUpdateParam")
    private CategoryInfoManagementHeaderBean updateParam;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        categoryInfoListLineBeanLazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            categoryInfoListLineBeanLazyModel =
                    new CategoryInfoListPaginationController(totalCount, categoryInfoSearchReqDto);
        }

        return LinkTarget.OK;
    }

    @Action
    public String toggleValidStatus(CategoryInfoListLineBean lineBean) {
        this.categoryInfoListLineBean = null;
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    public String detail(CategoryInfoListLineBean categoryInfoListLineBean) {
        getMessageContainer().clearAllMessages(true);

        this.categoryInfoListLineBean = categoryInfoListLineBean;
        return LinkTarget.DETAIL;
    }

    public String back() {
        return LinkTarget.BACK;
    }

    public String prepareRegister() {
        updateParam = null;
        return LinkTarget.REGISTER;
    }

    public String prepareUpdate(CategoryInfoListLineBean lineBean) {
        updateParam = new CategoryInfoManagementHeaderBean();
        updateParam.setCategoryId(lineBean.getCategoryId());
        updateParam.setCategoryEng(lineBean.getCategoryEng());
        updateParam.setCategoryMyn(lineBean.getCategoryMyn());
        updateParam.setDescription(lineBean.getDescription());

        return LinkTarget.UPDATE_INIT;
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

    public CategoryInfoSearchReqDto getCategoryInfoSearchReqDto() {
        return categoryInfoSearchReqDto;
    }

    public void setCategoryInfoSearchReqDto(CategoryInfoSearchReqDto categoryInfoSearchReqDto) {
        this.categoryInfoSearchReqDto = categoryInfoSearchReqDto;
    }

    public CategoryInfoListLineBean getCategoryInfoListLineBean() {
        return categoryInfoListLineBean;
    }

    public void setCategoryInfoListLineBean(CategoryInfoListLineBean categoryInfoListLineBean) {
        this.categoryInfoListLineBean = categoryInfoListLineBean;
    }

    public List<CategoryInfoListLineBean> getCategoryInfoListLineBeanList() {
        return categoryInfoListLineBeanList;
    }

    public void setCategoryInfoListLineBeanList(List<CategoryInfoListLineBean> categoryInfoListLineBeanList) {
        this.categoryInfoListLineBeanList = categoryInfoListLineBeanList;
    }

    public LazyDataModel<CategoryInfoListLineBean> getCategoryInfoListLineBeanLazyModel() {
        return categoryInfoListLineBeanLazyModel;
    }

    public void setCategoryInfoListLineBeanLazyModel(
            LazyDataModel<CategoryInfoListLineBean> categoryInfoListLineBeanLazyModel) {
        this.categoryInfoListLineBeanLazyModel = categoryInfoListLineBeanLazyModel;
    }

    public CategoryInfoManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(CategoryInfoManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public int getCouponDel() {
        return couponDel;
    }

    public void setCouponDel(int couponDel) {
        this.couponDel = couponDel;
    }

}
