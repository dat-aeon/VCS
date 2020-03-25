/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.userInfoSelectList.UserInfoSelectListReqDto;
import mm.aeon.com.ass.base.dto.userInfoSelectList.UserInfoSelectListResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class AdminListSearchController extends AbstractController
        implements IControllerAccessor<AdminListFormBean, AdminListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminListFormBean process(AdminListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!VCSCommon.USER_TYPE_ADMIN.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Admin List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Admin Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        UserInfoSelectListReqDto reqDto = new UserInfoSelectListReqDto();

        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getAdminLoginId())) {
            reqDto.setLoginId(formBean.getSearchHeaderBean().getAdminLoginId().toLowerCase());
        }

        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getAdminName())) {
            reqDto.setName(formBean.getSearchHeaderBean().getAdminName().toLowerCase());
        }

        reqDto.setUserTypeId(VCSCommon.USER_TYPE_ADMIN_ID);

        try {
            List<UserInfoSelectListResDto> resDtoList =
                    (List<UserInfoSelectListResDto>) this.getDaoServiceInvoker().execute(reqDto);

            List<AdminListLineBean> lineBeanList = new ArrayList<AdminListLineBean>();

            for (UserInfoSelectListResDto resdto : resDtoList) {
                AdminListLineBean lineBean = new AdminListLineBean();

                lineBean.setAdminId(resdto.getUserId());
                lineBean.setAdminLoginId(resdto.getLoginId());
                lineBean.setAdminName(resdto.getName());
                lineBean.setUpdatedTime(resdto.getUpdatedTime());

                lineBeanList.add(lineBean);
            }

            formBean.setLineBeans(lineBeanList);

            if (lineBeanList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Admin searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
