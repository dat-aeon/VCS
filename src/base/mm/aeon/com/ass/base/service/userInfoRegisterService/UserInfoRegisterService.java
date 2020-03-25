/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.userInfoRegisterService;

import java.util.List;

import mm.aeon.com.ass.base.dto.adminCustomerRoomInsert.AdminCustomerRoomInsertReqDto;
import mm.aeon.com.ass.base.dto.customerRoomInfoSearch.CustomerRoomInfoReqDto;
import mm.aeon.com.ass.base.dto.customerRoomInfoSearch.CustomerRoomInfoResDto;
import mm.aeon.com.ass.base.dto.freeAdminCustomerRoomInsert.FreeAdminCustomerRoomInsertReqDto;
import mm.aeon.com.ass.base.dto.freeCustomerRoomInfoSearch.FreeCustomerRoomInfoReqDto;
import mm.aeon.com.ass.base.dto.freeCustomerRoomInfoSearch.FreeCustomerRoomInfoResDto;
import mm.aeon.com.ass.base.dto.passwordInfoRegister.PasswordInfoRegisterReqDto;
import mm.aeon.com.ass.base.dto.userInfoRegister.UserInfoRegisterReqDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class UserInfoRegisterService extends AbstractService
        implements IService<UserInfoRegisterServiceReqBean, UserInfoRegisterServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public UserInfoRegisterServiceResBean execute(UserInfoRegisterServiceReqBean reqBean) {

        UserInfoRegisterReqDto reqDto = new UserInfoRegisterReqDto();
        PasswordInfoRegisterReqDto pwReqDto = null;

        UserInfoRegisterServiceResBean resBean = new UserInfoRegisterServiceResBean();

        reqDto.setCreatedBy(CommonUtil.getLoginUserInfo().getUserId());
        reqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());
        reqDto.setLoginId(reqBean.getLoginId());
        reqDto.setName(reqBean.getName());
        reqDto.setDelFlag(reqBean.getDelFlag());
        reqDto.setUserTypeId(reqBean.getUserTypeId());
        if (reqBean.getUserTypeId() == VCSCommon.USER_TYPE_ADMIN_ID) {
            reqDto.setAllowMessagingFlag(1);
        }
        if (reqBean.getUserTypeId() == VCSCommon.USER_TYPE_ADMIN_ID) {
            reqDto.setAllowFreeMessagingFlag(1);
        }

        try {
            this.getDaoServiceInvoker().execute(reqDto);
            if (reqDto.getUserId() != null) {
                pwReqDto = new PasswordInfoRegisterReqDto();

                pwReqDto.setUserId(reqDto.getUserId());
                pwReqDto.setCreatedBy(CommonUtil.getLoginUserInfo().getUserId());
                pwReqDto.setUserTypeId(reqBean.getUserTypeId());
                pwReqDto.setPassword(reqBean.getPassword());
                pwReqDto.setDelFlag(0);
                pwReqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());

                this.getDaoServiceInvoker().execute(pwReqDto);

                if (reqDto.getUserTypeId() == VCSCommon.USER_TYPE_ADMIN_ID) {

                    // Admin Customer Room For Messaging
                    CustomerRoomInfoReqDto customerRoomInfoReqDto = new CustomerRoomInfoReqDto();
                    List<CustomerRoomInfoResDto> customerRoomInfoResDtoList =
                            (List<CustomerRoomInfoResDto>) this.getDaoServiceInvoker().execute(customerRoomInfoReqDto);

                    for (CustomerRoomInfoResDto customerRoomInfoResDto : customerRoomInfoResDtoList) {

                        AdminCustomerRoomInsertReqDto adminCustomerRoomInsertReqDto =
                                new AdminCustomerRoomInsertReqDto();

                        adminCustomerRoomInsertReqDto.setCustRoomId(customerRoomInfoResDto.getCustRoomId());
                        adminCustomerRoomInsertReqDto.setUserId(reqDto.getUserId());
                        adminCustomerRoomInsertReqDto.setFinishFlag(1);
                        adminCustomerRoomInsertReqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                        this.getDaoServiceInvoker().execute(adminCustomerRoomInsertReqDto);
                    }

                    // Free Admin Customer Room For Messaging
                    FreeCustomerRoomInfoReqDto freeCustomerRoomInfoReqDto = new FreeCustomerRoomInfoReqDto();
                    List<FreeCustomerRoomInfoResDto> freeCustomerRoomInfoResDtoList =
                            (List<FreeCustomerRoomInfoResDto>) this.getDaoServiceInvoker()
                                    .execute(freeCustomerRoomInfoReqDto);

                    for (FreeCustomerRoomInfoResDto freeCustomerRoomInfoResDto : freeCustomerRoomInfoResDtoList) {

                        FreeAdminCustomerRoomInsertReqDto freeAdminCustomerRoomInsertReqDto =
                                new FreeAdminCustomerRoomInsertReqDto();

                        freeAdminCustomerRoomInsertReqDto.setCustRoomId(freeCustomerRoomInfoResDto.getFreeCustRoomId());
                        freeAdminCustomerRoomInsertReqDto.setUserId(reqDto.getUserId());
                        freeAdminCustomerRoomInsertReqDto.setFinishFlag(1);
                        freeAdminCustomerRoomInsertReqDto.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                        this.getDaoServiceInvoker().execute(freeAdminCustomerRoomInsertReqDto);
                    }
                }

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
