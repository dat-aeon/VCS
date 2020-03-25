/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeList;

import java.util.List;

import mm.aeon.com.ass.base.dto.storeCount.BranchSearchByStoreReqDto;
import mm.aeon.com.ass.base.dto.storeCount.BranchSearchByStoreResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.storeManagement.StoreManagementFormBean;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class BranchDeleteController extends AbstractController
        implements IControllerAccessor<StoreManagementFormBean, StoreManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public StoreManagementFormBean process(StoreManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Branch update started.", LogLevel.INFO);

        MessageBean msgBean;

        BranchSearchByStoreReqDto branchSearchByStoreReqDto = new BranchSearchByStoreReqDto();
        if (formBean.getLineBean() != null & formBean.getLineBean().getBranchId() != null) {
            branchSearchByStoreReqDto.setBranch_id(Integer.parseInt(formBean.getLineBean().getBranchId()));
            branchSearchByStoreReqDto.setShop_id(formBean.getTempStoreId());

            try {
                List<BranchSearchByStoreResDto> branchSearchByStoreResDto = (List<BranchSearchByStoreResDto>) CommonUtil
                        .getDaoServiceInvoker().execute(branchSearchByStoreReqDto);
                if (branchSearchByStoreResDto != null & branchSearchByStoreResDto.size() > 0) {

                    for (BranchSearchByStoreResDto dto : branchSearchByStoreResDto) {
                        if (dto.getDel_flag() == 0) {
                            msgBean = new MessageBean(MessageId.ME1030, "");
                            msgBean.addColumnId("Branch");
                            msgBean.setMessageType(MessageType.ERROR);
                            formBean.getMessageContainer().addMessage(msgBean);
                            formBean.setDisable(false);

                            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                            applicationLogger.log("Updating branch data.", LogLevel.ERROR);

                        }
                    }

                }
            } catch (PrestoDBException e) {
                e.printStackTrace();
            }
        }
        return formBean;
    }

}
