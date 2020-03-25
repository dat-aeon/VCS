/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.storeSelectUpdate.StoreSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.storeSelectUpdate.StoreSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.storeUpdate.StoreUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class StoreUpdateService extends AbstractService
        implements IService<StoreUpdateServiceReqBean, StoreUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public StoreUpdateServiceResBean execute(StoreUpdateServiceReqBean reqBean) {

        StoreSelectForUpdateReqDto selectForUpdateReqDto = new StoreSelectForUpdateReqDto();
        StoreUpdateServiceResBean resBean = new StoreUpdateServiceResBean();
        selectForUpdateReqDto.setStoreId(reqBean.getStoreId());

        try {
            StoreSelectForUpdateResDto selectForUpdateResDto =
                    (StoreSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedDate()
                    && !selectForUpdateResDto.getUpdatedDate().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {

                StoreUpdateReqDto reqDto = new StoreUpdateReqDto();

                reqDto.setStoreId(reqBean.getStoreId());
                reqDto.setStoreName(reqBean.getStoreName());
                reqDto.setStoreCode(reqBean.getStoreCode());
                reqDto.setStoreAddress(reqBean.getStoreAddress());
                reqDto.setUpdatedBy(reqBean.getUpdatedBy());
                reqDto.setUpdatedDate(CommonUtil.getCurrentTimeStamp());
                reqDto.setDelFlag(reqBean.getDelFlag());

                this.getDaoServiceInvoker().execute(reqDto);
                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

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
