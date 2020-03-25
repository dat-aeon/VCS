/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.secQuestionCount;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AppConfig")
public class SecurityQuestionCountReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = 8470633811049594893L;

    @Override
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.COUNT;
    }

}
