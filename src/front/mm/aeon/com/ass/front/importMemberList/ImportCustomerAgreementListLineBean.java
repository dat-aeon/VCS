/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import java.io.Serializable;

public class ImportCustomerAgreementListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7908513773501819222L;
    private Integer custAgreementId;
    private String agreementNo;
    private String qrShow;
    private String financialStatus;
    private Double financialAmt;
    private Integer financialTerm;

    public Integer getCustAgreementId() {
        return custAgreementId;
    }

    public void setCustAgreementId(Integer custAgreementId) {
        this.custAgreementId = custAgreementId;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getQrShow() {
        return qrShow;
    }

    public void setQrShow(String qrShow) {
        this.qrShow = qrShow;
    }

    public String getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public Double getFinancialAmt() {
        return financialAmt;
    }

    public void setFinancialAmt(Double financialAmt) {
        this.financialAmt = financialAmt;
    }

    public Integer getFinancialTerm() {
        return financialTerm;
    }

    public void setFinancialTerm(Integer financialTerm) {
        this.financialTerm = financialTerm;
    }

}
