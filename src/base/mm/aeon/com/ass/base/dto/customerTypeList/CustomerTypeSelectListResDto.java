/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2019 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerTypeList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerTypeSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -7318982398749871155L;

    private Integer customerTypeId;
    private Integer delFlag;
    private String custType;

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

}
