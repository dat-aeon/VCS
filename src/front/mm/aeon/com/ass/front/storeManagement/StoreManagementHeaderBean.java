/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeManagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class StoreManagementHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9008363660504873917L;

    private String storeCode;
    private String storeName;
    private String storeAddress;
    private String branchId;
    private String branchName;
    private String branchAddress;
    private String createdBy;
    private String createdTime;
    private String updatedBy;
    private Timestamp updatedTime;
    private String delFlag;
    private Integer storeId;
    private boolean forUpdate;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    public StoreManagementHeaderBean copyStoreManagementHeaderBean(
            StoreManagementHeaderBean storeManagementHeaderBean) {

        this.storeName = storeManagementHeaderBean.getStoreName();
        this.storeCode = storeManagementHeaderBean.getStoreCode();
        this.storeAddress = storeManagementHeaderBean.getStoreAddress();
        this.storeId = storeManagementHeaderBean.getStoreId();
        this.updatedTime = storeManagementHeaderBean.getUpdatedTime();
        this.forUpdate = storeManagementHeaderBean.isForUpdate();

        return this;
    }

}
