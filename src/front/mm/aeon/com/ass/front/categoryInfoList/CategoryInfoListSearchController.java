/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoList;

import mm.aeon.com.ass.base.dto.categoryInfoSearch.CategoryInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.categoryInfoSearch.CategoryInfoSelectCountReqDto;
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

public class CategoryInfoListSearchController extends AbstractController
        implements IControllerAccessor<CategoryInfoListFormBean, CategoryInfoListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CategoryInfoListFormBean process(CategoryInfoListFormBean categoryInfoListFormBean) {

        categoryInfoListFormBean.getMessageContainer().clearAllMessages(!categoryInfoListFormBean.getDoReload());

        applicationLogger.log("Category info searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        CategoryInfoSelectCountReqDto CategoryInfoSelectCountReqDto = new CategoryInfoSelectCountReqDto();

        CategoryInfoSearchReqDto CategoryInfoSearchReqDto = new CategoryInfoSearchReqDto();

        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(CategoryInfoSelectCountReqDto);
            categoryInfoListFormBean.setTotalCount(totalCount);
            categoryInfoListFormBean.setCategoryInfoSearchReqDto(CategoryInfoSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            categoryInfoListFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Category info searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return categoryInfoListFormBean;
    }

}
