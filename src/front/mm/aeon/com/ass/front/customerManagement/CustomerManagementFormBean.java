/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerManagement;

import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.event.SelectEvent;
import org.springframework.util.StringUtils;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("customerManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class CustomerManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 7849419172431489515L;

    private boolean init = true;

    @In(required = false, value = "customerUpdateParam")
    @Out(required = false, value = "customerUpdateParam")
    private CustomerManagementLineBean lineBean;

    private boolean isMember;

    private CustomerManagementLineBean backUpHeaderBean;

    private String nameChanged;

    private String customerNoChanged;

    private String phoneChanged;

    private String nrcChanged;

    private String dobChanged;

    private String salaryChanged;

    private String genderChanged;

    private String companyChanged;

    private String townshipChanged;

    private String customerTypeChanged;

    private String newValue;

    private String oldValue;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(join = true)
    public String init() {
        init = false;
        getMessageContainer().clearAllMessages(true);
        doReload = new Boolean(false);
        return LinkTarget.INIT;
    }

    @Begin(join = true)
    public void updateInit() {
        getMessageContainer().clearAllMessages(true);
        init = false;
        backUpHeaderBean = new CustomerManagementLineBean().copyCustomerManagementLineBean(lineBean);
    }

    @Action
    public String update() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        init = true;
        lineBean = null;
        doReload = new Boolean(true);
        nameChanged = null;
        customerNoChanged = null;
        phoneChanged = null;
        nrcChanged = null;
        dobChanged = null;
        salaryChanged = null;
        genderChanged = null;
        companyChanged = null;
        townshipChanged = null;
        customerTypeChanged = null;
        newValue = null;
        oldValue = null;

        return LinkTarget.SEARCH;
    }

    public String back() {
        getMessageContainer().clearAllMessages(true);
        init = true;
        lineBean = null;
        nameChanged = null;
        customerNoChanged = null;
        phoneChanged = null;
        nrcChanged = null;
        dobChanged = null;
        salaryChanged = null;
        genderChanged = null;
        companyChanged = null;
        townshipChanged = null;
        customerTypeChanged = null;
        newValue = null;
        oldValue = null;
        return LinkTarget.BACK;
    }

    public void reset() {
        getMessageContainer().clearAllMessages(true);
        nameChanged = null;
        customerNoChanged = null;
        phoneChanged = null;
        nrcChanged = null;
        dobChanged = null;
        salaryChanged = null;
        genderChanged = null;
        companyChanged = null;
        townshipChanged = null;
        customerTypeChanged = null;
        newValue = null;
        oldValue = null;
        lineBean = new CustomerManagementLineBean().copyCustomerManagementLineBean(backUpHeaderBean);
    }

    public void nameChange(ValueChangeEvent event) {
        nameChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getName()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getName().equals(event.getNewValue())) {
            nameChanged = "Name (" + backUpHeaderBean.getName() + "," + event.getNewValue().toString() + ")";
        }
    }

    public void cusNoChange(ValueChangeEvent event) {
        customerNoChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getCustomerNo()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getCustomerNo().equals(event.getNewValue())) {
            customerNoChanged =
                    "Customer No (" + backUpHeaderBean.getCustomerNo() + "," + event.getNewValue().toString() + ")";
        }

    }

    public void phoneChange(ValueChangeEvent event) {
        phoneChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getPhoneNo()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getPhoneNo().equals(event.getNewValue())) {
            phoneChanged = "Phone No (" + backUpHeaderBean.getPhoneNo() + "," + event.getNewValue().toString() + ")";
        }

    }

    public void nrcChange(ValueChangeEvent event) {
        nrcChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getNrcNo()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getNrcNo().equals(event.getNewValue())) {
            nrcChanged = "NRC No (" + backUpHeaderBean.getNrcNo() + "," + event.getNewValue().toString() + ")";
        }
    }

    public void dobChange(SelectEvent event) {
        dobChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getDob()) && !StringUtils.isEmpty(event.getObject()))
                || !backUpHeaderBean.getDob().equals(event.getObject())) {
            Date newDob = (Date) event.getObject();
            Date oldDob = backUpHeaderBean.getDob();

            dobChanged = "Date Of Birth (" + CommonUtil.formatByPattern(oldDob, "dd-MM-yyyy") + ","
                    + CommonUtil.formatByPattern(newDob, "dd-MM-yyyy") + ")";
        }

    }

    public void salaryChange(ValueChangeEvent event) {
        salaryChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getSalary()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getSalary().equals(event.getNewValue())) {
            salaryChanged = "Salary (" + backUpHeaderBean.getSalary() + "," + event.getNewValue().toString() + ")";
        }

    }

    public void genderChange(ValueChangeEvent event) {
        genderChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getGender()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getGender().equals(event.getNewValue())) {

            if (backUpHeaderBean.getGender() != null) {
                oldValue = (backUpHeaderBean.getGender() == 1) ? "Male" : "Female";
            } else {
                oldValue = null;
            }
            newValue = ((Integer) event.getNewValue() == 1) ? "Male" : "Female";

            genderChanged = "Gender (" + oldValue + "," + newValue + ")";
        }
    }

    public void companyChange(ValueChangeEvent event) {
        companyChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getCompanyName()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getCompanyName().equals(event.getNewValue())) {
            companyChanged =
                    "Company (" + backUpHeaderBean.getCompanyName() + "," + event.getNewValue().toString() + ")";
        }

    }

    public void townshipChange(ValueChangeEvent event) {
        townshipChanged = null;
        if ((StringUtils.isEmpty(backUpHeaderBean.getTownship()) && !StringUtils.isEmpty(event.getNewValue()))
                || !backUpHeaderBean.getTownship().equals(event.getNewValue())) {
            townshipChanged =
                    "Township (" + backUpHeaderBean.getTownship() + "," + event.getNewValue().toString() + ")";
        }

    }

    public void cusTypeChange(ValueChangeEvent event) {
        customerTypeChanged = null;
        newValue = ((boolean) event.getNewValue()) ? "Non-Member" : "Member";
        oldValue = (backUpHeaderBean.getCustomerTypeId() == 1) ? "Member" : "Non-Member";

        if (!backUpHeaderBean.getCustomerTypeId().toString().equals(newValue)) {
            customerTypeChanged = "Customer Type (" + oldValue + "," + newValue + ")";
        }
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public CustomerManagementLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(CustomerManagementLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public CustomerManagementLineBean getBackUpHeaderBean() {
        return backUpHeaderBean;
    }

    public void setBackUpHeaderBean(CustomerManagementLineBean backUpHeaderBean) {
        this.backUpHeaderBean = backUpHeaderBean;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean isMember) {
        this.isMember = isMember;
    }

    public String getNameChanged() {
        return nameChanged;
    }

    public void setNameChanged(String nameChanged) {
        this.nameChanged = nameChanged;
    }

    public String getCustomerNoChanged() {
        return customerNoChanged;
    }

    public void setCustomerNoChanged(String customerNoChanged) {
        this.customerNoChanged = customerNoChanged;
    }

    public String getPhoneChanged() {
        return phoneChanged;
    }

    public void setPhoneChanged(String phoneChanged) {
        this.phoneChanged = phoneChanged;
    }

    public String getNrcChanged() {
        return nrcChanged;
    }

    public void setNrcChanged(String nrcChanged) {
        this.nrcChanged = nrcChanged;
    }

    public String getDobChanged() {
        return dobChanged;
    }

    public void setDobChanged(String dobChanged) {
        this.dobChanged = dobChanged;
    }

    public String getSalaryChanged() {
        return salaryChanged;
    }

    public void setSalaryChanged(String salaryChanged) {
        this.salaryChanged = salaryChanged;
    }

    public String getGenderChanged() {
        return genderChanged;
    }

    public void setGenderChanged(String genderChanged) {
        this.genderChanged = genderChanged;
    }

    public String getCompanyChanged() {
        return companyChanged;
    }

    public void setCompanyChanged(String companyChanged) {
        this.companyChanged = companyChanged;
    }

    public String getTownshipChanged() {
        return townshipChanged;
    }

    public void setTownshipChanged(String townshipChanged) {
        this.townshipChanged = townshipChanged;
    }

    public String getCustomerTypeChanged() {
        return customerTypeChanged;
    }

    public void setCustomerTypeChanged(String customerTypeChanged) {
        this.customerTypeChanged = customerTypeChanged;
    }

}
