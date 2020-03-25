/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeRegisterService;

import java.sql.Timestamp;

import mm.aeon.com.ass.base.dto.storeRegister.StoreInsertReqDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class StoreInsertService extends AbstractService
        implements IService<StoreInsertServiceReqBean, StoreInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public StoreInsertServiceResBean execute(StoreInsertServiceReqBean reqBean) {

        StoreInsertReqDto reqDto = new StoreInsertReqDto();
        StoreInsertServiceResBean resBean = new StoreInsertServiceResBean();

        reqDto.setStoreName(reqBean.getStoreName());
        reqDto.setStoreCode(reqBean.getStoreCode());
        reqDto.setStoreAddress(reqBean.getStoreAddress());
        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedTime(Timestamp.valueOf(reqBean.getCreatedTime()));
        reqDto.setDelFlag(VCSCommon.ZERO_INTEGER);

        try {
            this.getDaoServiceInvoker().execute(reqDto);
            resBean.setServiceStatus(ServiceStatusCode.OK);
        } catch (Exception e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);

            } else if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                resBean.setServiceStatus(ServiceStatusCode.SQL_ERROR);

            } else {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return resBean;
    }

}
