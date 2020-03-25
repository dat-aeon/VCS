/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.companyInfoRegisterService;

import mm.aeon.com.ass.base.dto.companyInfoRegister.CompanyInfoRegisterReqDto;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class CompanyInfoRegisterService extends AbstractService implements IService<CompanyInfoRegisterServiceReqBean, SecurityRegisterServiceResBean> {
    
    private ASSLogger logger = new ASSLogger();
    
    @Override
    public SecurityRegisterServiceResBean execute(CompanyInfoRegisterServiceReqBean reqBean) {
        
        CompanyInfoRegisterReqDto reqDto  = new CompanyInfoRegisterReqDto();
        SecurityRegisterServiceResBean resBean = new SecurityRegisterServiceResBean();
         
        reqDto.setInfoId(reqBean.getInfoId());
        reqDto.setAddress(reqBean.getAddress());
        reqDto.setHotLine(reqBean.getHotLine());
        reqDto.setWebAddress(reqBean.getWebAddress());
        reqDto.setSocialMedia(reqBean.getSocialMedia());
        reqDto.setAboutCompany(reqBean.getAboutCompany());
        reqDto.setCreatedBy(reqBean.getCreatedBy());
        reqDto.setCreatedTime(reqBean.getCreatedTime());
        
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
