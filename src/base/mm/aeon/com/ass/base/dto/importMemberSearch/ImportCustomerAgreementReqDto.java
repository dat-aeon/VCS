/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.importMemberSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "ImportCustomerAgreement")
public class ImportCustomerAgreementReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7026889882465913713L;

    private Integer importCustomerId;

    public Integer getImportCustomerId() {
        return importCustomerId;
    }

    public void setImportCustomerId(Integer importCustomerId) {
        this.importCustomerId = importCustomerId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
