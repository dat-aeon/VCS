/**************************************************************************
 * $Date: 2019-02-04$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeManagement;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.storeSelectUpdate.StoreSelectAllForUpdateReqDto;
import mm.aeon.com.ass.base.dto.storeSelectUpdate.StoreSelectAllForUpdateResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.storeList.StoreListLineBean;
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

public class StoreManagementInitController extends AbstractController
        implements IControllerAccessor<StoreManagementFormBean, StoreManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public StoreManagementFormBean process(StoreManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Store List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Store Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        StoreSelectAllForUpdateReqDto reqDto = new StoreSelectAllForUpdateReqDto();
        reqDto.setStoreId(formBean.getStoreManagementHeaderBean().getStoreId());
        try {
            List<StoreSelectAllForUpdateResDto> resStoreList =
                    (List<StoreSelectAllForUpdateResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<StoreListLineBean> lineBeanList = new ArrayList<StoreListLineBean>();

            for (StoreSelectAllForUpdateResDto resdto : resStoreList) {
                StoreListLineBean lineBean = new StoreListLineBean();

                lineBean.setStoreId(resdto.getStoreId());
                lineBean.setStoreName(resdto.getStoreName());
                lineBean.setStoreCode(resdto.getStoreCode());
                lineBean.setStoreAddress(resdto.getStoreAddress());
                lineBean.setCreatedBy(resdto.getCreatedBy());
                lineBean.setUpdatedTime(resdto.getUpdatedTime());
                lineBean.setUpdatedBy(resdto.getUpdatedBy());
                lineBean.setBranchName(resdto.getBranchName());
                lineBean.setBranchId(resdto.getBranchId());
                lineBean.setBranchAddress(resdto.getBranchAddress());
                lineBean.setBranchCode(resdto.getBranchCode());
                lineBean.setDelFlag(resdto.getDelFlag());

                if (resdto.getUpdatedTime() != null) {
                    lineBean.setUpdatedTime(resdto.getUpdatedTime());
                }

                lineBeanList.add(lineBean);
            }

            formBean.setOperationBranchList(lineBeanList);
            formBean.setBranchList(lineBeanList);

            if (lineBeanList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Store searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
