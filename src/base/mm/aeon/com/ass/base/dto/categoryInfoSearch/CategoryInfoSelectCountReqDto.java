/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.categoryInfoSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CategoryInfoCount")
public class CategoryInfoSelectCountReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5376898487674767104L;

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

}
