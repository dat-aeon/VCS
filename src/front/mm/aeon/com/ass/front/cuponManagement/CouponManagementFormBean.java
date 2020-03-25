/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import mm.aeon.com.ass.base.dto.cusCouponSearch.CustomerSearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.front.couponList.CustomerDataBean;
import mm.aeon.com.ass.front.storeList.StoreListHeaderBean;
import mm.aeon.com.ass.front.storeList.StoreListLineBean;
import mm.aeon.com.ass.front.storeList.StoreListPaginationController;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("couponManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CouponManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3952459113049191493L;

    @In(required = false, value = "couponCustomerSearchReqDto")
    @Out(required = false, value = "couponCustomerSearchReqDto")
    private CustomerSearchReqDto couponCustomerSearchReqDto;

    @In(required = false, value = "couponUpdateParam")
    @Out(required = false, value = "couponUpdateParam")
    private CouponManagementHeaderBean updateParam;

    @In(required = false, value = "couponBranchUpdateParam")
    @Out(required = false, value = "couponBranchUpdateParam")
    private List<StoreCouponDataBean> updateBranchParam;

    @In(required = false, value = "couponCustomerParam")
    @Out(required = false, value = "couponCustomerParam")
    private List<CustomerDataBean> couponCustomerParam;

    private CouponManagementHeaderBean couponManagementHeaderBean;

    private CouponManagementHeaderBean backUpHeaderBean;

    private CouponManagementImageBean couponManagementImageBean;

    private CouponManagementFileBean couponManagementFileBean;

    private ArrayList<CustomerUploadBean> customerUploadBeanList;

    private StoreListHeaderBean searchHeaderBean;

    private StoreCouponDataBean storeCouponDataBean;

    private List<CustomerDataBean> removedCouponCustomerList = new ArrayList<CustomerDataBean>();

    private List<StoreCouponDataBean> removedShopBranchList = new ArrayList<StoreCouponDataBean>();

    private List<CustomerDataBean> couponCustomerList = new ArrayList<CustomerDataBean>();

    private List<StoreCouponDataBean> storeCouponBranchList = new ArrayList<StoreCouponDataBean>();

    private List<StoreCouponDataBean> shopBranchList = new ArrayList<StoreCouponDataBean>();

    private List<StoreCouponDataBean> passwordCouponList = new ArrayList<StoreCouponDataBean>();

    private List<StoreListLineBean> lineBeans;

    private LazyDataModel<StoreListLineBean> lazyModel;

    private StoreListLineBean lineBean;

    private int pageFirst;

    private boolean couponCode;

    private boolean showCode;

    private boolean showFile;

    private String uploadFileName;

    private String tempUploadImageFilePath;

    private String oldUploadImageFilePath;

    private String uploadImageFileName;

    private boolean init = true;

    private boolean isUpdate;

    private String fileName;

    private String uploadedFile;

    @In(required = false, value = "couponCustomerTotalCount")
    @Out(required = false, value = "couponCustomerTotalCount")
    private int couponCustomerTotalCount;

    @In(required = false, value = "couponCustomerTotalCount")
    @Out(required = false, value = "couponCustomerTotalCount")
    private int tempCouponCustomerTotalCount;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(join = true)
    public String init() {
        getMessageContainer().clearAllMessages(true);
        couponManagementHeaderBean = new CouponManagementHeaderBean();
        init = false;
        isUpdate = false;
        couponCode = false;
        showCode = false;
        showFile = true;
        doReload = new Boolean(true);
        customerUploadBeanList = new ArrayList<CustomerUploadBean>();
        return LinkTarget.REGISTER;
    }

    @Action
    public String register() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;

        } else if (updateParam != null) {
            if (oldUploadImageFilePath != null) {
                String oldUploadImageFolderPath = CommonUtil.getUploadImageBaseFilePath()
                        + CommonUtil.getCouponUploadImageFolder() + oldUploadImageFilePath;
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
            couponManagementHeaderBean = new CouponManagementHeaderBean();
            init = true;
            uploadedFile = null;
            uploadFileName = null;
            tempUploadImageFilePath = null;
            oldUploadImageFilePath = null;
            uploadImageFileName = null;
            storeCouponBranchList.clear();
            doReload = new Boolean(true);
            couponCustomerList.clear();
            customerUploadBeanList = new ArrayList<CustomerUploadBean>();
            removedCouponCustomerList = new ArrayList<CustomerDataBean>();
            removedShopBranchList = new ArrayList<StoreCouponDataBean>();
            return LinkTarget.SEARCH;
        }
        couponManagementHeaderBean = new CouponManagementHeaderBean();
        uploadedFile = null;
        uploadFileName = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        storeCouponBranchList.clear();
        doReload = new Boolean(true);
        return LinkTarget.OK;
    }

    @Action
    public String search() {
        lazyModel = null;
        doReload = new Boolean(false);
        lazyModel = new StoreListPaginationController(lineBeans.size(), lineBeans);
        return "";
    }

    public void removeCouponCustomer(CustomerDataBean customerDataBean) {
        removedCouponCustomerList.add(customerDataBean);
        couponCustomerTotalCount = tempCouponCustomerTotalCount;
        couponCustomerTotalCount -= removedCouponCustomerList.size();
        couponCustomerList.remove(customerDataBean);

    }

    public void removeShopBranch(StoreCouponDataBean storeCouponBranch) {
        if (!removedShopBranchList.contains(storeCouponBranch)) {
            removedShopBranchList.add(storeCouponBranch);
        }
        if (storeCouponBranchList.contains(storeCouponBranch)) {
            storeCouponBranchList.remove(storeCouponBranch);
        }
        if (!shopBranchList.contains(storeCouponBranch)) {
            shopBranchList.add(storeCouponBranch);
        }

    }

    public void addShopBranch(StoreCouponDataBean storeCouponBranch) {

        if (!storeCouponBranchList.contains(storeCouponBranch)) {
            storeCouponBranchList.add(storeCouponBranch);
        }
        if (shopBranchList.contains(storeCouponBranch)) {
            shopBranchList.remove(storeCouponBranch);
        }

    }

    public void removeShopBranchList(List<StoreCouponDataBean> storeCouponList,
            List<StoreCouponDataBean> shopBranchList) {
        for (StoreCouponDataBean storeCoupon : storeCouponList) {
            for (StoreCouponDataBean shopBranch : shopBranchList) {
                if (storeCoupon.equals(shopBranch)) {
                    shopBranchList.remove(shopBranch);
                    break;
                }
            }
        }
    }

    @Action
    public String importImage() {
        if (getMessageContainer().checkMessage(MessageType.INFO)) {
            // couponManagementHeaderBean = new CouponManagementHeaderBean();
            couponManagementImageBean = new CouponManagementImageBean();
            return LinkTarget.OK;
        } else {
            return LinkTarget.ERROR;
        }
    }

    public void uploadImage(FileUploadEvent event) {
        /*
         * if (couponManagementHeaderBean == null) { couponManagementHeaderBean = new CouponManagementHeaderBean(); }
         */
        if (couponManagementImageBean == null) {
            couponManagementImageBean = new CouponManagementImageBean();
        }
        if (event != null) {
            couponManagementHeaderBean.setCouponImage(event.getFile());
            couponManagementImageBean.setCouponImage(event.getFile());
            // this.fileName = this.couponManagementHeaderBean.getCouponImage().getFileName();
            /*
             * byte[] file = event.getFile().getContents(); String contentType = event.getFile().getContentType();
             * FacesMessage msg = new FacesMessage("Success", event.getFile().getFileName() + " was Upload! .");
             * FacesContext.getCurrentInstance().addMessage(null, msg); this.content = new DefaultStreamedContent(new
             * ByteArrayInputStream(file), contentType);
             */
        }
    }

    public DefaultStreamedContent getContext(UploadedFile file) {
        if (file != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(file.getContents()), "image/png");
        }
        return null;
    }

    @Action
    public String importCustomerFile() {
        if (getMessageContainer().checkMessage(MessageType.INFO)) {
            return LinkTarget.OK;
        } else {
            return LinkTarget.ERROR;
        }
    }

    public void uploadFile(FileUploadEvent event) {
        /*
         * if (couponManagementHeaderBean == null) { couponManagementHeaderBean = new CouponManagementHeaderBean(); }
         */
        if (couponManagementFileBean == null) {
            couponManagementFileBean = new CouponManagementFileBean();
        }
        if (event != null) {
            couponManagementHeaderBean.setImportFile(event.getFile());
            couponManagementFileBean.setImportFile(event.getFile());
        }
    }

    public String removeFile() {
        getMessageContainer().clearAllMessages(true);
        uploadedFile = null;
        uploadFileName = null;
        return LinkTarget.OK;
    }

    @Action
    public String selectStore() {
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            storeCouponDataBean = new StoreCouponDataBean();
        }
        removeShopBranchList(storeCouponBranchList, shopBranchList);
        return LinkTarget.OK;
    }

    @Begin(join = true)
    public void updateInit() {
        getMessageContainer().clearAllMessages(true);

        init = false;
        isUpdate = true;
        couponCode = true;
        showCode = true;
        showFile = false;
        couponManagementHeaderBean = new CouponManagementHeaderBean();
        couponManagementHeaderBean.setCoupon_code(updateParam.getCoupon_code());
        couponManagementHeaderBean.setCouponName(updateParam.getCouponName());
        couponManagementHeaderBean.setCouponNameMya(updateParam.getCouponNameMya());
        couponManagementHeaderBean.setCouponDescription(updateParam.getCouponDescription());
        couponManagementHeaderBean.setCouponDescriptionMya(updateParam.getCouponDescriptionMya());
        couponManagementHeaderBean.setStoreName(updateParam.getStoreName());
        couponManagementHeaderBean.setCouponStrDate(updateParam.getCouponStrDate());
        couponManagementHeaderBean.setCouponExpDate(updateParam.getCouponExpDate());
        couponManagementHeaderBean.setCouponDiscount(updateParam.getCouponDiscount());
        couponManagementHeaderBean.setCouponAmount(updateParam.getCouponAmount());
        couponManagementHeaderBean.setCouponNoOfCus(updateParam.getCouponNoOfCus());
        couponManagementHeaderBean.setCouponSpEvent(updateParam.getCouponSpEvent());
        couponManagementHeaderBean.setCouponSpEventMya(updateParam.getCouponSpEventMya());
        couponManagementHeaderBean.setCouponTlNo(updateParam.getCouponTlNo());
        couponManagementHeaderBean.setCouponImage(updateParam.getCouponImage());
        couponManagementHeaderBean.setCid(updateParam.getCid());
        couponManagementHeaderBean.setDiscount_unit(updateParam.getDiscount_unit());
        couponManagementHeaderBean.setOriginShopBranchList(updateParam.getOriginShopBranchList());
        couponManagementHeaderBean.setOriginStoreCouponBranchList(updateParam.getOriginStoreCouponBranchList());
        storeCouponBranchList = updateBranchParam;
        removedCouponCustomerList = new ArrayList<CustomerDataBean>();
        removedShopBranchList = new ArrayList<StoreCouponDataBean>();
        couponCustomerList = new ArrayList<CustomerDataBean>();
        customerUploadBeanList = new ArrayList<CustomerUploadBean>();
        for (CustomerDataBean customerDataBean : couponCustomerParam) {
            couponCustomerList.add(customerDataBean);
        }
        shopBranchList = updateParam.getShopBranchList();
        couponManagementHeaderBean.setUploadedImageFilePath(updateParam.getUploadedImageFilePath());
        tempUploadImageFilePath =
                "/temp" + CommonUtil.getCouponUploadImageFolder() + updateParam.getUploadedImageFilePath();
        uploadedFile = "file";

        removeShopBranchList(storeCouponBranchList, shopBranchList);

        backUpHeaderBean = new CouponManagementHeaderBean().copyCouponManagementHeaderBean(couponManagementHeaderBean);

    }

    // @PreDestroy
    // public void clearMessageWithPreDestroy() {
    // this.getMessageContainer().clearAllMessages(true);
    // }

    public String back() {
        getMessageContainer().clearAllMessages(true);
        init = true;
        updateParam = null;

        updateBranchParam = null;
        couponManagementHeaderBean = null;
        uploadedFile = null;
        uploadFileName = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        storeCouponBranchList.clear();
        return LinkTarget.BACK;
    }

    public void clear() {
        couponManagementHeaderBean = new CouponManagementHeaderBean();
        storeCouponBranchList.clear();
        uploadedFile = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        uploadFileName = null;
        couponManagementImageBean = new CouponManagementImageBean();
        couponManagementFileBean = new CouponManagementFileBean();
        getMessageContainer().clearAllMessages(true);
    }

    public void reset() {
        couponManagementHeaderBean = new CouponManagementHeaderBean().copyCouponManagementHeaderBean(backUpHeaderBean);
        couponCustomerList = new ArrayList<CustomerDataBean>();
        for (CustomerDataBean customerDataBean : couponCustomerParam) {
            couponCustomerList.add(customerDataBean);
        }

        shopBranchList = new ArrayList<StoreCouponDataBean>();
        for (StoreCouponDataBean originShopBranch : couponManagementHeaderBean.getOriginShopBranchList()) {
            shopBranchList.add(originShopBranch);
        }

        storeCouponBranchList = new ArrayList<StoreCouponDataBean>();
        for (StoreCouponDataBean originStoreCouponBranch : couponManagementHeaderBean
                .getOriginStoreCouponBranchList()) {
            storeCouponBranchList.add(originStoreCouponBranch);
        }
        removeShopBranchList(storeCouponBranchList, shopBranchList);
        tempUploadImageFilePath = "/temp" + backUpHeaderBean.getUploadedImageFilePath();
        oldUploadImageFilePath = null;
    }

    public StoreListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public CouponManagementFileBean getCouponManagementFileBean() {
        return couponManagementFileBean;
    }

    public void setCouponManagementFileBean(CouponManagementFileBean couponManagementFileBean) {
        this.couponManagementFileBean = couponManagementFileBean;
    }

    public void setSearchHeaderBean(StoreListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<StoreListLineBean> getLineBeans() {
        return lineBeans;
    }

    public void setLineBeans(List<StoreListLineBean> lineBeans) {
        this.lineBeans = lineBeans;
    }

    public LazyDataModel<StoreListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<StoreListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public StoreListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(StoreListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public CouponManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(CouponManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public CouponManagementHeaderBean getCouponManagementHeaderBean() {
        return couponManagementHeaderBean;
    }

    public void setCouponManagementHeaderBean(CouponManagementHeaderBean couponManagementHeaderBean) {
        this.couponManagementHeaderBean = couponManagementHeaderBean;
    }

    public boolean isDoReload() {
        return doReload;
    }

    public void setDoReload(boolean doReload) {
        this.doReload = doReload;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public ArrayList<CustomerUploadBean> getCustomerUploadBeanList() {
        return customerUploadBeanList;
    }

    public void setCustomerUploadBeanList(ArrayList<CustomerUploadBean> customerUploadBeanList) {
        this.customerUploadBeanList = customerUploadBeanList;
    }

    public boolean isShowFile() {
        return showFile;
    }

    public void setShowFile(boolean showFile) {
        this.showFile = showFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public CouponManagementHeaderBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(CouponManagementHeaderBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public CouponManagementImageBean getCouponManagementImageBean() {
        return couponManagementImageBean;
    }

    public void setCouponManagementImageBean(CouponManagementImageBean couponManagementImageBean) {
        this.couponManagementImageBean = couponManagementImageBean;
    }

    public List<StoreCouponDataBean> getPasswordCouponList() {
        return passwordCouponList;
    }

    public void setPasswordCouponList(List<StoreCouponDataBean> passwordCouponList) {
        this.passwordCouponList = passwordCouponList;
    }

    public StoreCouponDataBean getStoreCouponDataBean() {
        return storeCouponDataBean;
    }

    public void setStoreCouponDataBean(StoreCouponDataBean storeCouponDataBean) {
        this.storeCouponDataBean = storeCouponDataBean;
    }

    public boolean isShowCode() {
        return showCode;
    }

    public void setShowCode(boolean showCode) {
        this.showCode = showCode;
    }

    public boolean isCouponCode() {
        return couponCode;
    }

    public void setCouponCode(boolean couponCode) {
        this.couponCode = couponCode;
    }

    public String getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(String uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<CustomerDataBean> getCouponCustomerList() {
        return couponCustomerList;
    }

    public void setCouponCustomerList(List<CustomerDataBean> couponCustomerList) {
        this.couponCustomerList = couponCustomerList;
    }

    public List<CustomerDataBean> getRemovedCouponCustomerList() {
        return removedCouponCustomerList;
    }

    public void setRemovedCouponCustomerList(List<CustomerDataBean> removedCouponCustomerList) {
        this.removedCouponCustomerList = removedCouponCustomerList;
    }

    public int getCouponCustomerTotalCount() {
        return couponCustomerTotalCount;
    }

    public void setCouponCustomerTotalCount(int couponCustomerTotalCount) {
        this.couponCustomerTotalCount = couponCustomerTotalCount;
    }

    public CustomerSearchReqDto getCouponCustomerSearchReqDto() {
        return couponCustomerSearchReqDto;
    }

    public void setCouponCustomerSearchReqDto(CustomerSearchReqDto couponCustomerSearchReqDto) {
        this.couponCustomerSearchReqDto = couponCustomerSearchReqDto;
    }

    public int getTempCouponCustomerTotalCount() {
        return tempCouponCustomerTotalCount;
    }

    public void setTempCouponCustomerTotalCount(int tempCouponCustomerTotalCount) {
        this.tempCouponCustomerTotalCount = tempCouponCustomerTotalCount;
    }

    public String getUploadImageFileName() {
        return uploadImageFileName;
    }

    public void setUploadImageFileName(String uploadImageFileName) {
        this.uploadImageFileName = uploadImageFileName;
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

    public List<StoreCouponDataBean> getShopBranchList() {
        return shopBranchList;
    }

    public void setShopBranchList(List<StoreCouponDataBean> shopBranchList) {
        this.shopBranchList = shopBranchList;
    }

    public List<StoreCouponDataBean> getRemovedShopBranchList() {
        return removedShopBranchList;
    }

    public void setRemovedShopBranchList(List<StoreCouponDataBean> removedShopBranchList) {
        this.removedShopBranchList = removedShopBranchList;
    }

    public List<StoreCouponDataBean> getStoreCouponBranchList() {
        return storeCouponBranchList;
    }

    public void setStoreCouponBranchList(List<StoreCouponDataBean> storeCouponBranchList) {
        this.storeCouponBranchList = storeCouponBranchList;
    }

}
