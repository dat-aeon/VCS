/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.cusSecQueSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "CustSecQues")
public class CusSecQuestionReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -4658660553210684328L;
    private Integer sec_ques_id;

    public Integer getSec_ques_id() {
        return sec_ques_id;
    }

    public void setSec_ques_id(Integer sec_ques_id) {
        this.sec_ques_id = sec_ques_id;
    }

    @Override
    public DaoType getDaoType() {
        // TODO Auto-generated method stub
        return DaoType.SELECT_LIST;
    }

}
