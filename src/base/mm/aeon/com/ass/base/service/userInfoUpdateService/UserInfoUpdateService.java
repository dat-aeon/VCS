/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.userInfoUpdateService;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.passwordInfoUpdate.PasswordInfoUpdateReqDto;
import mm.aeon.com.ass.base.dto.userInfoSelectForUpdate.UserInfoSelectForUpdateReqDto;
import mm.aeon.com.ass.base.dto.userInfoSelectForUpdate.UserInfoSelectForUpdateResDto;
import mm.aeon.com.ass.base.dto.userInfoUpdate.UserInfoUpdateReqDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractService;
import mm.com.dat.presto.main.common.service.bean.IService;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PhysicalRecordLockedException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.exception.RecordDuplicatedException;
import mm.com.dat.presto.main.log.LogLevel;

public class UserInfoUpdateService extends AbstractService
        implements IService<UserInfoUpdateServiceReqBean, UserInfoUpdateServiceResBean> {

    private ASSLogger logger = new ASSLogger();

    @Override
    public UserInfoUpdateServiceResBean execute(UserInfoUpdateServiceReqBean reqBean) {

        UserInfoUpdateServiceResBean resBean = new UserInfoUpdateServiceResBean();

        UserInfoSelectForUpdateReqDto selectForUpdateReqDto = new UserInfoSelectForUpdateReqDto();
        selectForUpdateReqDto.setUserId(reqBean.getUserId());

        try {
            UserInfoSelectForUpdateResDto selectForUpdateResDto =
                    (UserInfoSelectForUpdateResDto) this.getDaoServiceInvoker().execute(selectForUpdateReqDto);

            if (selectForUpdateResDto == null) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_NOT_FOUND_ERROR);
            } else if (null != selectForUpdateResDto.getUpdatedTime()
                    && !selectForUpdateResDto.getUpdatedTime().equals(reqBean.getUpdatedTime())) {
                resBean.setServiceStatus(ASSServiceStatusCommon.RECORD_ALREADY_UPDATE);
            } else {
                UserInfoUpdateReqDto updateReqDto = new UserInfoUpdateReqDto();

                updateReqDto.setUserId(reqBean.getUserId());
                // updateReqDto.setUserTypeId(reqBean.getUserTypeId());
                updateReqDto.setDelFlag(reqBean.getDelFlag());
                updateReqDto.setLoginId(reqBean.getLoginId());
                updateReqDto.setName(reqBean.getName());
                updateReqDto.setUpdatedBy(CommonUtil.getLoginUserInfo().getUserId());
                updateReqDto.setUpdatedTime(CommonUtil.getCurrentTimeStamp());
                if (reqBean.getAllowMessagingFlag() != null) {
                    updateReqDto.setAllowMessagingFlag(reqBean.getAllowMessagingFlag());
                }
                if (reqBean.getAllowFreeMessagingFlag() != null) {
                    updateReqDto.setAllowFreeMessagingFlag(reqBean.getAllowFreeMessagingFlag());
                }

                this.getDaoServiceInvoker().execute(updateReqDto);

                if (null != reqBean.getPassword()) {
                    PasswordInfoUpdateReqDto pwUpdateReqDto = new PasswordInfoUpdateReqDto();

                    pwUpdateReqDto.setPassword(reqBean.getPassword());
                    pwUpdateReqDto.setUserId(reqBean.getUserId());
                    pwUpdateReqDto.setUserTypeId(reqBean.getUserTypeId());

                    this.getDaoServiceInvoker().execute(pwUpdateReqDto);
                }

                resBean.setServiceStatus(ServiceStatusCode.OK);
            }

        } catch (PrestoDBException e) {
            if (e instanceof RecordDuplicatedException) {
                resBean.setServiceStatus(ServiceStatusCode.RECORD_DUPLICATED_ERROR);
            } else if (e instanceof PhysicalRecordLockedException) {
                resBean.setServiceStatus(ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR);
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
