/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoList;

import java.util.ArrayList;

import mm.aeon.com.ass.base.dto.categoryCount.FaqSearchByCategoryReqDto;
import mm.aeon.com.ass.base.dto.categoryCount.FaqSearchByCategoryResDto;
import mm.aeon.com.ass.base.service.categoryInfoDeleteService.CategoryInfoDeleteServiceReqBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CategoryInfoToggleValidController extends AbstractController
        implements IControllerAccessor<CategoryInfoListFormBean, CategoryInfoListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public CategoryInfoListFormBean process(CategoryInfoListFormBean CategoryInfoListFormBean) {

        CategoryInfoListFormBean.getMessageContainer().clearAllMessages(true);

        FaqSearchByCategoryReqDto faqSearchByCategoryReqDto = new FaqSearchByCategoryReqDto();
        faqSearchByCategoryReqDto.setCategoryId(CategoryInfoListFormBean.getCategoryInfoListLineBean().getCategoryId());

        try {
            ArrayList<FaqSearchByCategoryResDto> faqSearchByCategoryResDto =
                    (ArrayList<FaqSearchByCategoryResDto>) CommonUtil.getDaoServiceInvoker()
                            .execute(faqSearchByCategoryReqDto);
            if (null != faqSearchByCategoryResDto & faqSearchByCategoryResDto.size() > 0) {
                for (FaqSearchByCategoryResDto dto : faqSearchByCategoryResDto) {
                    if (CategoryInfoListFormBean.getCategoryInfoListLineBean().getDelFlag() == 0) {
                        if (dto.getDel_flag() == 0) {
                            CategoryInfoListFormBean.setCouponDel(dto.getDel_flag());
                            if (!isValidData(CategoryInfoListFormBean)) {
                                return CategoryInfoListFormBean;
                            }
                        }
                    }

                }
            }
        } catch (PrestoDBException e) {
            e.printStackTrace();
        }

        applicationLogger.log("Category info status update process started.", LogLevel.INFO);
        MessageBean msgBean;

        CategoryInfoDeleteServiceReqBean CategoryInfoDeleteServiceReqBean = new CategoryInfoDeleteServiceReqBean();
        CategoryInfoDeleteServiceReqBean
                .setCategoryId(CategoryInfoListFormBean.getCategoryInfoListLineBean().getCategoryId());
        CategoryInfoDeleteServiceReqBean.setDelFlag(
                CategoryInfoListFormBean.getCategoryInfoListLineBean().getDelFlag() == VCSCommon.ZERO_INTEGER
                        ? VCSCommon.ONE_INTEGER
                        : VCSCommon.ZERO_INTEGER);
        CategoryInfoDeleteServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
        CategoryInfoDeleteServiceReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

        this.getServiceInvoker().addRequest(CategoryInfoDeleteServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            CategoryInfoListFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Category info status update process finished.", LogLevel.INFO);
        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Category info status update process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }

        return CategoryInfoListFormBean;
    }

    private boolean isValidData(CategoryInfoListFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (formBean.getCouponDel() == 0) {
            msgBean = new MessageBean(MessageId.ME1038, "");
            msgBean.addColumnId(DisplayItem.CATEGORY_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
