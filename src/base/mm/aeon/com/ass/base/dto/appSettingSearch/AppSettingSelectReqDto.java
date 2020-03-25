/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.appSettingSearch;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "AppConfig")
public class AppSettingSelectReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 622234158884618063L;

    private Integer appSettingId;

    public Integer getAppSettingId() {
        return appSettingId;
    }

    public void setAppSettingId(Integer appSettingId) {
        this.appSettingId = appSettingId;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }
}
