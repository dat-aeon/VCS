/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfoManagement;

import java.util.List;

import mm.aeon.com.ass.base.dto.companyInfoSearch.CompanyInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.companyInfoSearch.CompanyInfoSearchResDto;
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

public class CompanyInfoSearchController extends AbstractController
        implements IControllerAccessor<CompanyInfoManagementFormBean, CompanyInfoManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CompanyInfoManagementFormBean process(CompanyInfoManagementFormBean formBean) {

        // formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Company Information List]", new InvalidScreenTransitionException(),
         * LogLevel.ERROR); throw new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Company Information Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        CompanyInfoSearchReqDto reqDto = new CompanyInfoSearchReqDto();

        try {
            List<CompanyInfoSearchResDto> resCompanyInfoList =
                    (List<CompanyInfoSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);

            if (resCompanyInfoList.size() > 0) {
                CompanyInfoSearchResDto resdto = resCompanyInfoList.get(0);

                CompanyInfoBean lineBean = new CompanyInfoBean();

                String addSpacePhno = null;

                if (null != resdto.getHotLine() & resdto.getHotLine() != "")
                    addSpacePhno = addSpace(resdto.getHotLine());

                lineBean.setInfoId(resdto.getInfoId());
                lineBean.setAddress_eng(resdto.getAddress_eng());
                lineBean.setAddress_mya(resdto.getAddress_mya());
                lineBean.setHotLine(addSpacePhno);
                lineBean.setWebAddress(resdto.getWebAddress());
                lineBean.setSocialMedia(resdto.getSocialMedia());
                lineBean.setAboutCompanyEng(resdto.getAboutCompanyEng());
                lineBean.setAboutCompanyMya(resdto.getAboutCompanyMya());
                lineBean.setChatAutoReplyMessage(resdto.getChatAutoReplyMessage());
                lineBean.setCreatedBy(resdto.getCreatedBy());
                lineBean.setUpdatedBy(resdto.getUpdatedBy());
                lineBean.setCreatedTime(resdto.getCreatedTime());
                lineBean.setUpdatedTime(resdto.getUpdatedTime());

                formBean.setCompanyInfoBean(lineBean);

            } else {
                msgBean = new MessageBean(MessageId.MI0008);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Company Information searching finished.", LogLevel.INFO);
            }

        } catch (

        PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

    public static String addSpace(String hotline) {
        String str[] = new String[2];
        String number = "";
        String prefix = "";

        if (hotline.contains("+")) {
            str = hotline.split("\\+95");
            prefix = "+95";
        } else if (hotline.contains("09")) {
            str = hotline.split("09");
            prefix = "09";
        }

        number = prefix + " " + str[1];

        System.out.println(number);

        return number;
    }

}
