/**************************************************************************
 * $Date : 2019/02/07$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeList;

import java.util.ArrayList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.storeCount.CouopnSearchByStoreReqDto;
import mm.aeon.com.ass.base.dto.storeCount.CouopnSearchByStoreResDto;
import mm.aeon.com.ass.base.service.storeUpdateService.StoreUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.storeUpdateService.StoreUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
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

public class StoreToggleValidController extends AbstractController
        implements IControllerAccessor<StoreListFormBean, StoreListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public StoreListFormBean process(StoreListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Store List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */
        applicationLogger.log("Store status update started.", LogLevel.INFO);

        MessageBean msgBean;
        String serviceStatus = null;

        CouopnSearchByStoreReqDto couopnSearchByStoreReqDto = new CouopnSearchByStoreReqDto();
        couopnSearchByStoreReqDto.setShop_id(formBean.getLineBean().getStoreId());

        try {
            ArrayList<CouopnSearchByStoreResDto> couopnSearchByStoreResDto =
                    (ArrayList<CouopnSearchByStoreResDto>) CommonUtil.getDaoServiceInvoker()
                            .execute(couopnSearchByStoreReqDto);
            for (CouopnSearchByStoreResDto dto : couopnSearchByStoreResDto) {
                if (dto.getDel_flag() == 0) {
                    formBean.setCouponDel(0);
                    if (!isValidData(formBean)) {
                        return formBean;
                    }
                }

            }
        } catch (PrestoDBException e) {
            e.printStackTrace();
        }

        StoreUpdateServiceReqBean storeUpdateReqBean = new StoreUpdateServiceReqBean();
        StoreUpdateServiceResBean storeUpdateResBean = new StoreUpdateServiceResBean();

        storeUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
        storeUpdateReqBean.setStoreId(formBean.getLineBean().getStoreId());
        storeUpdateReqBean.setDelFlage(
                Integer.parseInt(formBean.getLineBean().getDelFlag()) == VCSCommon.ZERO_INTEGER ? VCSCommon.ONE_INTEGER
                        : VCSCommon.ZERO_INTEGER);
        storeUpdateReqBean.setUpdatedTime(formBean.getLineBean().getUpdatedTime());
        storeUpdateReqBean.setStoreAddress(formBean.getLineBean().getStoreAddress());
        storeUpdateReqBean.setStoreName(formBean.getLineBean().getStoreName());

        this.getServiceInvoker().addRequest(storeUpdateReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        storeUpdateResBean = responseMessage.getMessageBean(0);
        serviceStatus = storeUpdateResBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Shop disable update finished.", LogLevel.INFO);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update shop disabl data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating shop data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating shop data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(StoreListFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (formBean.getCouponDel() == 0) {
            msgBean = new MessageBean(MessageId.ME1028, "");
            msgBean.addColumnId(DisplayItem.SHOP_CODE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
