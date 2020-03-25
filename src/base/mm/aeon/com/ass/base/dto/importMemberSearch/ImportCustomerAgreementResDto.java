/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.importMemberSearch;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class ImportCustomerAgreementResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 6718815262168822545L;
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
