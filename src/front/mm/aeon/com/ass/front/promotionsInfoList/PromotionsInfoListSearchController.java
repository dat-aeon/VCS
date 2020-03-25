/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoList;

import mm.aeon.com.ass.base.dto.promotionsInfoSearch.PromotionsInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.promotionsInfoSearch.PromotionsInfoSelectCountReqDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
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

public class PromotionsInfoListSearchController extends AbstractController
        implements IControllerAccessor<PromotionsInfoListFormBean, PromotionsInfoListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public PromotionsInfoListFormBean process(PromotionsInfoListFormBean promotionsInfoListFormBean) {

        promotionsInfoListFormBean.getMessageContainer().clearAllMessages(!promotionsInfoListFormBean.getDoReload());

        applicationLogger.log("Promotions info searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        PromotionsInfoSelectCountReqDto promotionsInfoSelectCountReqDto = new PromotionsInfoSelectCountReqDto();

        PromotionsInfoSearchReqDto promotionsInfoSearchReqDto = new PromotionsInfoSearchReqDto();

        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(promotionsInfoSelectCountReqDto);
            promotionsInfoListFormBean.setTotalCount(totalCount);
            promotionsInfoListFormBean.setPromotionsInfoSearchReqDto(promotionsInfoSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            promotionsInfoListFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Promotions info searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return promotionsInfoListFormBean;
    }

}
