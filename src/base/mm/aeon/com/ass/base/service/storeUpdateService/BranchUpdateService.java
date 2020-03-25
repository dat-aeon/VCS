/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeUpdateService;

import mm.aeon.com.ass.base.dto.storeUpdate.BranchUpdateReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class BranchUpdateService extends AbstractService
        implements IService<BranchUpdateServiceReqBean, BranchUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public BranchUpdateServiceResBean execute(BranchUpdateServiceReqBean reqBean) {

        BranchUpdateReqDto reqDto = new BranchUpdateReqDto();
        BranchUpdateServiceResBean resBean = new BranchUpdateServiceResBean();

        reqDto.setBranchCode(reqBean.getBranchCode());
        reqDto.setBranchName(reqBean.getBranchName());
        reqDto.setBranchAddress(reqBean.getBranchAddress());
        reqDto.setUpdatedBy(reqBean.getUpdatedBy());
        reqDto.setUpdatedDate(reqBean.getUpdatedDate());
        reqDto.setBranchId(reqBean.getBranchId());
        if(null != reqBean.getDelFlage())
          reqDto.setDel_flag(Integer.parseInt(reqBean.getDelFlage()));

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
