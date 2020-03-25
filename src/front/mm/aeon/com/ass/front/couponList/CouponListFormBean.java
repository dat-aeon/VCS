/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.couponSearch.CouponSearchReqDto;
import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.front.cuponManagement.CouponManagementHeaderBean;
import mm.aeon.com.ass.front.cuponManagement.StoreCouponDataBean;
import mm.aeon.com.ass.front.storeList.StoreListLineBean;
import mm.aeon.com.ass.front.storeList.StoreListPaginationController;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("couponListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CouponListFormBean extends AbstractFormBean implements IRequest, IResponse {
    /**
     * 
     */
    private static final long serialVersionUID = 793503049432696686L;

    @Out(required = false, value = "couponCustomerSearchReqDto")
    private CustomerSearchReqDto couponCustomerSearchReqDto;

    private CouponListHeaderBean searchHeaderBean;

    private CouponSearchReqDto couponSearchReqDto;

    private List<CouponListLineBean> lineBeans;

    private LazyDataModel<CouponListLineBean> lazyModel;

    private List<StoreListLineBean> storelineBeans;

    private LazyDataModel<StoreListLineBean> storelazyModel;

    @Out(required = false, value = "customerDataBeanLazyModel")
    private LazyDataModel<CustomerDataBean> customerDataBeanLazyModel;

    private StoreListLineBean storelineBean;

    private StoreCouponDataBean storeCouponDataBean;

    List<StoreCouponDataBean> storeCouponBranchList = new ArrayList<StoreCouponDataBean>();

    List<CustomerDataBean> customerList = new ArrayList<CustomerDataBean>();

    private int storepageFirst;

    private CouponListLineBean lineBean;

    private ArrayList<SelectItem> statusList;

    @Out(required = false, value = "couponUpdateParam")
    private CouponManagementHeaderBean updateParam;

    @Out(required = false, value = "couponBranchUpdateParam")
    private List<StoreCouponDataBean> updateBranchParam;

    @Out(required = false, value = "couponCustomerParam")
    private List<CustomerDataBean> couponCustomerParam;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    @Out(required = false, value = "couponCustomerTotalCount")
    private int couponCustomerTotalCount;

    private List<StoreCouponDataBean> originStoreCouponBranchList;

    private List<StoreCouponDataBean> originShopBranchList;

    private List<StoreCouponDataBean> shopBranchList = new ArrayList<StoreCouponDataBean>();

    private String tempUploadImageFilePath;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new CouponListHeaderBean();
        lineBean = null;
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        updateParam = null;
        updateBranchParam = null;
        couponCustomerParam = null;
        lazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            lazyModel = new CouponListPaginationController(totalCount, couponSearchReqDto);
        }

        return LinkTarget.OK;
    }

    @Action
    public String store_search() {
        storelazyModel = null;
        updateParam = null;
        updateBranchParam = null;
        couponCustomerParam = null;
        doReload = new Boolean(false);

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && storelineBeans.size() != 0) {
            storelazyModel = new StoreListPaginationController(storelineBeans.size(), storelineBeans);
        }

        return "";
    }

    @Action
    public String select() {
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            storeCouponDataBean = new StoreCouponDataBean();
        }
        return LinkTarget.OK;
    }

    @Action
    public String prepareUpdate(CouponListLineBean lineBean) {
        updateParam = new CouponManagementHeaderBean();
        updateParam.setCouponName(lineBean.getCouponName());
        updateParam.setCouponNameMya(lineBean.getCouponNameMya());
        updateParam.setCoupon_code(lineBean.getCoupon_code());
        updateParam.setCouponDescription(lineBean.getCouponDescription());
        updateParam.setCouponDescriptionMya(lineBean.getCouponDescriptionMya());
        updateParam.setStoreName(lineBean.getStoreName());
        updateParam.setCouponStrDate(lineBean.getCouponStrDate());
        updateParam.setCouponExpDate(lineBean.getCouponExpDate());
        updateParam.setCouponDiscount(lineBean.getCoupon_discount());
        updateParam.setCouponAmount(lineBean.getCoupon_amount());
        updateParam.setCouponSpEvent(lineBean.getSpecial_event());
        updateParam.setCouponSpEventMya(lineBean.getCouponSpEventMya());
        updateParam.setCouponNoOfCus(lineBean.getCupon_no_per_cust());
        updateParam.setCouponTlNo(lineBean.getTotal_no());
        updateParam.setUploadedImageFilePath(lineBean.getUnuse_image_path());

        String uploadPath = CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getCouponUploadImageFolder();
        File sourceFile = new File(uploadPath + lineBean.getUnuse_image_path());
        File destinationFile = new File(FileHandler.getSystemPath() + "/temp" + CommonUtil.getCouponUploadImageFolder()
                + lineBean.getUnuse_image_path());

        try {
            FileHandler.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateParam.setCid(lineBean.getCid());
        updateParam.setDiscount_unit(lineBean.getDiscount_unit());
        updateBranchParam = storeCouponBranchList;
        couponCustomerParam = customerList;
        updateParam.setOriginShopBranchList(originShopBranchList);
        updateParam.setOriginStoreCouponBranchList(originStoreCouponBranchList);
        updateParam.setShopBranchList(shopBranchList);
        this.reset();

        return LinkTarget.UPDATE_INIT;
    }

    @Action
    public String delete() {
        doReload = false;
        lineBean = null;
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            doReload = true;
        }

        return LinkTarget.OK;
    }

    public String prepareRegister() {
        updateParam = null;
        updateBranchParam = null;
        couponCustomerParam = null;
        return LinkTarget.REGISTER;
    }

    @Action
    public String detail(CouponListLineBean lineBean) {
        getMessageContainer().clearAllMessages(true);
        customerDataBeanLazyModel = null;

        String uploadPath = CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getCouponUploadImageFolder();
        File sourceFile = new File(uploadPath + lineBean.getUnuse_image_path());
        File destinationFile = new File(FileHandler.getSystemPath() + "/temp" + CommonUtil.getCouponUploadImageFolder()
                + lineBean.getUnuse_image_path());

        try {
            FileHandler.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempUploadImageFilePath = "/temp" + CommonUtil.getCouponUploadImageFolder() + lineBean.getUnuse_image_path();

        this.lineBean = lineBean;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && couponCustomerTotalCount != 0) {
            customerDataBeanLazyModel = new CouponCustomerListPaginationController(couponCustomerTotalCount,
                    couponCustomerSearchReqDto, null);
        }

        reset();
        return LinkTarget.DETAIL;
    }

    public String back() {
        return LinkTarget.BACK;
    }

    public void reset() {
        this.searchHeaderBean = new CouponListHeaderBean();
    }

    public StoreCouponDataBean getStoreCouponDataBean() {
        return storeCouponDataBean;
    }

    public void setStoreCouponDataBean(StoreCouponDataBean storeCouponDataBean) {
        this.storeCouponDataBean = storeCouponDataBean;
    }

    public List<StoreCouponDataBean> getStoreCouponBranchList() {
        return storeCouponBranchList;
    }

    public void setStoreCouponBranchList(List<StoreCouponDataBean> storeCouponBranchList) {
        this.storeCouponBranchList = storeCouponBranchList;
    }

    public List<CustomerDataBean> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerDataBean> customerList) {
        this.customerList = customerList;
    }

    public List<StoreListLineBean> getStorelineBeans() {
        return storelineBeans;
    }

    public void setStorelineBeans(List<StoreListLineBean> storelineBeans) {
        this.storelineBeans = storelineBeans;
    }

    public LazyDataModel<StoreListLineBean> getStorelazyModel() {
        return storelazyModel;
    }

    public void setStorelazyModel(LazyDataModel<StoreListLineBean> storelazyModel) {
        this.storelazyModel = storelazyModel;
    }

    public StoreListLineBean getStorelineBean() {
        return storelineBean;
    }

    public void setStorelineBean(StoreListLineBean storelineBean) {
        this.storelineBean = storelineBean;
    }

    public int getStorepageFirst() {
        return storepageFirst;
    }

    public void setStorepageFirst(int storepageFirst) {
        this.storepageFirst = storepageFirst;
    }

    public CouponListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(CouponListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<CouponListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<CouponListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<CouponListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<CouponListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public CouponListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(CouponListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public ArrayList<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public CouponManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(CouponManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
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

    public CouponSearchReqDto getCouponSearchReqDto() {
        return couponSearchReqDto;
    }

    public void setCouponSearchReqDto(CouponSearchReqDto couponSearchReqDto) {
        this.couponSearchReqDto = couponSearchReqDto;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public LazyDataModel<CustomerDataBean> getCustomerDataBeanLazyModel() {
        return customerDataBeanLazyModel;
    }

    public void setCustomerDataBeanLazyModel(LazyDataModel<CustomerDataBean> customerDataBeanLazyModel) {
        this.customerDataBeanLazyModel = customerDataBeanLazyModel;
    }

    public CustomerSearchReqDto getCouponCustomerSearchReqDto() {
        return couponCustomerSearchReqDto;
    }

    public void setCouponCustomerSearchReqDto(CustomerSearchReqDto couponCustomerSearchReqDto) {
        this.couponCustomerSearchReqDto = couponCustomerSearchReqDto;
    }

    public int getCouponCustomerTotalCount() {
        return couponCustomerTotalCount;
    }

    public void setCouponCustomerTotalCount(int couponCustomerTotalCount) {
        this.couponCustomerTotalCount = couponCustomerTotalCount;
    }

    public List<StoreCouponDataBean> getShopBranchList() {
        return shopBranchList;
    }

    public void setShopBranchList(List<StoreCouponDataBean> shopBranchList) {
        this.shopBranchList = shopBranchList;
    }

    public List<StoreCouponDataBean> getOriginStoreCouponBranchList() {
        return originStoreCouponBranchList;
    }

    public void setOriginStoreCouponBranchList(List<StoreCouponDataBean> originStoreCouponBranchList) {
        this.originStoreCouponBranchList = originStoreCouponBranchList;
    }

    public List<StoreCouponDataBean> getOriginShopBranchList() {
        return originShopBranchList;
    }

    public void setOriginShopBranchList(List<StoreCouponDataBean> originShopBranchList) {
        this.originShopBranchList = originShopBranchList;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
    }

}
