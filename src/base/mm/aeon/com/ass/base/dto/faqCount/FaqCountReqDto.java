/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.faqCount;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "FaqInfo")
public class FaqCountReqDto implements IReqServiceDto{

    /**
     * 
     */
    private static final long serialVersionUID = 8817042648904997573L;

    @Override
    public DaoType getDaoType() {
        return DaoType.COUNT;
    }

}
