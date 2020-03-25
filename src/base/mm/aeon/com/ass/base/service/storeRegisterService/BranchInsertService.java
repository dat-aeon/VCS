/**************************************************************************
 * $Date : 2019/01/24$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.storeRegisterService;

import mm.aeon.com.ass.base.dto.storeRegister.BranchInsertReqDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class BranchInsertService extends AbstractService
        implements IService<BranchInsertServiceReqBean, BranchInsertServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public BranchInsertServiceResBean execute(BranchInsertServiceReqBean reqBean) {

        BranchInsertReqDto reqDto = new BranchInsertReqDto();
        BranchInsertServiceResBean resBean = new BranchInsertServiceResBean();

        reqDto.setStoreId(reqBean.getStoreId());
        reqDto.setBranchCode(reqBean.getBranchCode());
        reqDto.setBranchName(reqBean.getBranchName());
        reqDto.setBranchAddress(reqBean.getBranchAddress());
        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedDate(reqBean.getCreatedDate());
        reqDto.setDelFlag(VCSCommon.ZERO_INTEGER);
        //reqDto.setBranchId(reqBean.getBranchId());

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
