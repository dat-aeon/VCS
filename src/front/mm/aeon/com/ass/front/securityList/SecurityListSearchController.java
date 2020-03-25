/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.securityList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.securitySearch.SecuritySearchReqDto;
import mm.aeon.com.ass.base.dto.securitySearch.SecuritySearchResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
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

public class SecurityListSearchController extends AbstractController
        implements IControllerAccessor<SecurityListFormBean, SecurityListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public SecurityListFormBean process(SecurityListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Security question List]", new InvalidScreenTransitionException(),
         * LogLevel.ERROR); throw new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Security question Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        SecuritySearchReqDto reqDto = new SecuritySearchReqDto();

        try {
            List<SecuritySearchResDto> resSecurityList =
                    (List<SecuritySearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<SecurityListLineBean> lineBeanList = new ArrayList<SecurityListLineBean>();

            for (SecuritySearchResDto resdto : resSecurityList) {
                SecurityListLineBean lineBean = new SecurityListLineBean();
                lineBean.setSecId(resdto.getSecId());
                lineBean.setQuestionMyan(resdto.getQuestionMyan());
                lineBean.setQuestionEng(resdto.getQuestionEng());
                lineBean.setDelFlag(resdto.getDelFlag());
                lineBean.setCreatedBy(resdto.getCreatedBy());
                lineBean.setCreatedTime(resdto.getCreatedTime());
                lineBean.setUpdatedBy(resdto.getUpdatedBy());
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
            applicationLogger.log("Security question searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
