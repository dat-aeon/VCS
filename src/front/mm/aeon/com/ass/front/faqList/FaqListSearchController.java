/**************************************************************************
 * $Date: 2019-01-28$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.faqList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.faqCategorySelect.FaqCategorySearchReqDto;
import mm.aeon.com.ass.base.dto.faqCategorySelect.FaqCategorySearchResDto;
import mm.aeon.com.ass.base.dto.faqSearch.FaqSearchReqDto;
import mm.aeon.com.ass.base.dto.faqSearch.FaqSearchResDto;
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

public class FaqListSearchController extends AbstractController
        implements IControllerAccessor<FaqListFormBean, FaqListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public FaqListFormBean process(FaqListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[FAQ List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("FAQ Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        FaqSearchReqDto reqDto = new FaqSearchReqDto();
        FaqCategorySearchReqDto categoryReqDto = new FaqCategorySearchReqDto();
        try {

            List<FaqCategorySearchResDto> resCategoryList =
                    (List<FaqCategorySearchResDto>) this.getDaoServiceInvoker().execute(categoryReqDto);
            List<FaqListLineBean> categoryList = new ArrayList<FaqListLineBean>();

            for (FaqCategorySearchResDto faqCategoryList : resCategoryList) {
                FaqListLineBean lineBean = new FaqListLineBean();
                lineBean.setCategoryId(faqCategoryList.getCategoryId());
                lineBean.setCategoryName(faqCategoryList.getCategoryName());
                categoryList.add(lineBean);
            }

            formBean.setCategorylineBeansList(categoryList);

            List<FaqSearchResDto> resFaqList = (List<FaqSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<FaqListLineBean> lineBeanList = new ArrayList<FaqListLineBean>();

            for (FaqSearchResDto resdto : resFaqList) {
                FaqListLineBean lineBean = new FaqListLineBean();

                lineBean.setQuestionEng(resdto.getQuestionEng());
                lineBean.setQuestionMyan(resdto.getQuestionMyan());
                lineBean.setAnswerEng(resdto.getAnswerEng());
                lineBean.setAnswerMyan(resdto.getAnswerMyan());
                lineBean.setCreatedBy(resdto.getCreatedBy());
                lineBean.setCreatedTime(resdto.getCreatedTime());
                lineBean.setUpdatedBy(resdto.getUpdatedBy());
                lineBean.setUpdatedTime(resdto.getUpdatedTime());
                lineBean.setCategoryId(resdto.getCategoryId());
                lineBean.setCategoryName(resdto.getCategoryName());
                lineBean.setFaqId(resdto.getFaqId());
                lineBean.setDelFlag(resdto.getDelFlag());

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
            applicationLogger.log("FAQ searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
