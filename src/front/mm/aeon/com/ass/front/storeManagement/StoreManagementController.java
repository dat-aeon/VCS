/**************************************************************************
 * $Date : 2019/02/07$
 * $Author : Aung Ko Lin$
 * $Rev : $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeManagement;

import java.util.ArrayList;

import mm.aeon.com.ass.base.dto.storeCount.BranchCountReqDto;
import mm.aeon.com.ass.base.dto.storeCount.StoreCountReqDto;
import mm.aeon.com.ass.base.dto.storeCount.StoreMaxReqDto;
import mm.aeon.com.ass.base.dto.storeCount.StoreMaxResDto;
import mm.aeon.com.ass.base.dto.storeSearch.BranchSearchReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchResDto;
import mm.aeon.com.ass.base.service.storeRegisterService.BranchInsertServiceReqBean;
import mm.aeon.com.ass.base.service.storeRegisterService.BranchInsertServiceResBean;
import mm.aeon.com.ass.base.service.storeRegisterService.StoreInsertServiceReqBean;
import mm.aeon.com.ass.base.service.storeRegisterService.StoreInsertServiceResBean;
import mm.aeon.com.ass.base.service.storeUpdateService.BranchUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.storeUpdateService.BranchUpdateServiceResBean;
import mm.aeon.com.ass.base.service.storeUpdateService.StoreUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.storeUpdateService.StoreUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.storeList.StoreListLineBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
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

public class StoreManagementController extends AbstractController
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

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean messageBean;
        String serviceStatus = null;
        String updateStatus = null;
        String insertStatus = null;

        try {

            if (!formBean.getStoreManagementHeaderBean().isForUpdate()) {
                applicationLogger.log("Store Registration Process Stared.", LogLevel.INFO);
                StoreInsertServiceReqBean storeInsertReqBean = new StoreInsertServiceReqBean();
                StoreInsertServiceResBean storeInsertResBean = new StoreInsertServiceResBean();

                int storeCount = VCSCommon.ZERO_INTEGER;
                StoreCountReqDto storeCountReqDto = new StoreCountReqDto();

                storeCount = (int) CommonUtil.getDaoServiceInvoker().execute(storeCountReqDto);
                storeCount = storeCount + VCSCommon.ONE_INTEGER;
                // storeInsertReqBean.setStoreId(storeCount);
                storeInsertReqBean.setStoreName(formBean.getStoreManagementHeaderBean().getStoreName());
                storeInsertReqBean.setStoreCode("S" + storeCount);
                storeInsertReqBean.setStoreAddress(formBean.getStoreManagementHeaderBean().getStoreAddress());
                storeInsertReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
                storeInsertReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp().toString());

                this.getServiceInvoker().addRequest(storeInsertReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                storeInsertResBean = responseMessage.getMessageBean(0);
                serviceStatus = storeInsertResBean.getServiceStatus();

                StoreMaxReqDto storeMaxReqDto = new StoreMaxReqDto();
                StoreMaxResDto storeMaxResDto =
                        (StoreMaxResDto) CommonUtil.getDaoServiceInvoker().execute(storeMaxReqDto);

                if (ServiceStatusCode.OK.equals(serviceStatus)) {
                    BranchInsertServiceReqBean branchInsertReqBean = new BranchInsertServiceReqBean();
                    BranchInsertServiceResBean branchInsertResBean = new BranchInsertServiceResBean();

                    int branchCount = VCSCommon.ZERO_INTEGER;
                    BranchCountReqDto branchCountReqDto = new BranchCountReqDto();
                    branchCountReqDto.setShop_id(storeMaxResDto.getMax_id());

                    branchCount = (int) CommonUtil.getDaoServiceInvoker().execute(branchCountReqDto);
                    if (branchCount == VCSCommon.ZERO_INTEGER)
                        branchCount = VCSCommon.ONE_INTEGER;

                    for (StoreListLineBean storeList : formBean.getBranchList()) {

                        branchInsertReqBean.setBranchCode("B" + branchCount);
                        branchInsertReqBean.setBranchName(storeList.getBranchName());
                        branchInsertReqBean.setBranchAddress(storeList.getBranchAddress());
                        branchInsertReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
                        branchInsertReqBean.setCreatedDate(CommonUtil.getCurrentTimeStamp());
                        // branchInsertReqBean.setBranchId(branchCount + VCSCommon.ONE_INTEGER);
                        branchInsertReqBean.setStoreId(storeMaxResDto.getMax_id());

                        this.getServiceInvoker().addRequest(branchInsertReqBean);
                        responseMessage = this.getServiceInvoker().invoke();
                        branchInsertResBean = responseMessage.getMessageBean(0);
                        serviceStatus = branchInsertResBean.getServiceStatus();

                        branchCount++;
                    }

                    if (ServiceStatusCode.OK.equals(serviceStatus)) {
                        messageBean = new MessageBean(MessageId.MI0001);
                        messageBean.setMessageType(MessageType.INFO);
                        formBean.getMessageContainer().addMessage(messageBean);

                        applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
                        applicationLogger.log("Store Registration Process finished.", LogLevel.INFO);

                    }
                }

            } else {

                /*
                 * if(null != formBean.getRemovedBranchList() & formBean.getRemovedBranchList().size() > 0) { for(int
                 * i=0;i<formBean.getRemovedBranchList().size();i++) {
                 * 
                 * BranchCouponCountReqDto branchCountReqDto = new BranchCouponCountReqDto();
                 * branchCountReqDto.setBranch_id(Integer.parseInt(formBean.getRemovedBranchList().get(i).getBranchId())
                 * ); branchCountReqDto.setShop_id(formBean.getStoreManagementHeaderBean().getStoreId());
                 * 
                 * int count = (int)CommonUtil.getDaoServiceInvoker().execute(branchCountReqDto);
                 * 
                 * if(count > 0 ) { messageBean = new MessageBean(MessageId.ME1028, "");
                 * messageBean.addColumnId("Branch"); messageBean.setMessageType(MessageType.ERROR);
                 * formBean.getMessageContainer().addMessage(messageBean);
                 * 
                 * return formBean; }
                 * 
                 * }
                 * 
                 * }
                 */

                applicationLogger.log("Store Update Process Stared.", LogLevel.INFO);
                StoreUpdateServiceReqBean storeUpdateReqBean = new StoreUpdateServiceReqBean();
                StoreUpdateServiceResBean storeUpdateResBean = new StoreUpdateServiceResBean();

                storeUpdateReqBean.setStoreName(formBean.getStoreManagementHeaderBean().getStoreName());
                storeUpdateReqBean.setStoreCode(formBean.getStoreManagementHeaderBean().getStoreCode());
                storeUpdateReqBean.setStoreAddress(formBean.getStoreManagementHeaderBean().getStoreAddress());
                storeUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
                storeUpdateReqBean.setUpdatedTime(formBean.getStoreManagementHeaderBean().getUpdatedTime());
                storeUpdateReqBean.setStoreId(formBean.getStoreManagementHeaderBean().getStoreId());
                storeUpdateReqBean.setDelFlage(
                        Integer.parseInt(formBean.getStoreManagementHeaderBean().getDelFlag()) == VCSCommon.ZERO_INTEGER
                                ? VCSCommon.ZERO_INTEGER
                                : VCSCommon.ONE_INTEGER);

                this.getServiceInvoker().addRequest(storeUpdateReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                storeUpdateResBean = responseMessage.getMessageBean(0);
                serviceStatus = storeUpdateResBean.getServiceStatus();
                if (ServiceStatusCode.OK.equals(serviceStatus)) {
                    BranchUpdateServiceReqBean branchUpdateReqBean = new BranchUpdateServiceReqBean();
                    BranchUpdateServiceResBean branchUpdateResBean = new BranchUpdateServiceResBean();

                    BranchSearchReqDto branchSearchReqDto = new BranchSearchReqDto();
                    branchSearchReqDto.setStoreId(storeUpdateReqBean.getStoreId());
                    // ArrayList<StoreSearchResDto> branchSearchResDto = (ArrayList<StoreSearchResDto>)
                    // CommonUtil.getDaoServiceInvoker().execute(branchSearchReqDto);

                    ArrayList<StoreListLineBean> updateList = null;
                    ArrayList<StoreListLineBean> insertList = new ArrayList<StoreListLineBean>();

                    BranchInsertServiceReqBean branchInsertReqBean = new BranchInsertServiceReqBean();
                    BranchInsertServiceResBean branchInsertResBean = new BranchInsertServiceResBean();

                    if (null != formBean.getRemovedBranchList() & formBean.getRemovedBranchList().size() > 0) {
                        for (StoreListLineBean store : formBean.getRemovedBranchList()) {

                            branchUpdateReqBean.setBranchCode(store.getBranchCode());
                            branchUpdateReqBean.setBranchName(store.getBranchName());
                            branchUpdateReqBean.setBranchAddress(store.getBranchAddress());
                            branchUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
                            branchUpdateReqBean.setUpdatedDate(CommonUtil.getCurrentTimeStamp());
                            branchUpdateReqBean.setDelFlage(String.valueOf(VCSCommon.ONE_INTEGER));
                            branchUpdateReqBean.setBranchId(Integer.parseInt(store.getBranchId()));

                            this.getServiceInvoker().addRequest(branchUpdateReqBean);
                            responseMessage = this.getServiceInvoker().invoke();
                            branchUpdateResBean = responseMessage.getMessageBean(0);
                            serviceStatus = branchUpdateResBean.getServiceStatus();
                        }
                    }

                    ArrayList<StoreSearchResDto> branchSearchResDto = (ArrayList<StoreSearchResDto>) CommonUtil
                            .getDaoServiceInvoker().execute(branchSearchReqDto);

                    if ((null != branchSearchResDto) & (branchSearchResDto.size() == formBean.getBranchList().size())) {
                        branchUpdateReqBean = new BranchUpdateServiceReqBean();
                        branchUpdateResBean = new BranchUpdateServiceResBean();
                        for (StoreListLineBean storeList : formBean.getBranchList()) {

                            branchUpdateReqBean.setBranchCode(storeList.getBranchCode());
                            branchUpdateReqBean.setBranchName(storeList.getBranchName());
                            branchUpdateReqBean.setBranchAddress(storeList.getBranchAddress());
                            branchUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
                            branchUpdateReqBean.setUpdatedDate(CommonUtil.getCurrentTimeStamp());

                            branchUpdateReqBean.setBranchId(Integer.parseInt(storeList.getBranchId()));

                            this.getServiceInvoker().addRequest(branchUpdateReqBean);
                            responseMessage = this.getServiceInvoker().invoke();
                            branchUpdateResBean = responseMessage.getMessageBean(0);
                            serviceStatus = branchUpdateResBean.getServiceStatus();

                        }
                    } /*
                       * else if((null != branchSearchResDto) & (formBean.getBranchList().size() <
                       * branchSearchResDto.size())) { updateResList = new ArrayList<StoreSearchResDto>(); insertResList
                       * = new ArrayList<StoreSearchResDto>(); for(int i=0;i< branchSearchResDto.size();i++) { for(int
                       * j=0;j<formBean.getBranchList().size();j++) { if(!formBean.getBranchList().get(i).isDone()) {
                       * if(branchSearchResDto.get(j).getBranchId().equals(formBean.getBranchList().get(i).getBranchId()
                       * )) { formBean.getBranchList().get(i).setDone(true); StoreSearchResDto data = new
                       * StoreSearchResDto(); data = branchSearchResDto.get(j); updateResList.add(data); break; }else {
                       * StoreSearchResDto data = new StoreSearchResDto(); data = branchSearchResDto.get(j);
                       * insertResList.add(data); } } } }
                       * 
                       * branchUpdateReqBean = new BranchUpdateServiceReqBean(); branchUpdateResBean = new
                       * BranchUpdateServiceResBean();
                       * 
                       * for(int k=0;k<updateResList.size();k++) {
                       * 
                       * branchUpdateReqBean.setBranchCode(updateResList.get(k).getBranchCode());
                       * branchUpdateReqBean.setBranchAddress(updateResList.get(k).getBranchAddress());
                       * branchUpdateReqBean.setBranchName(updateResList.get(k).getBranchName());
                       * branchUpdateReqBean.setBranchId(Integer.parseInt(updateResList.get(k).getBranchId()));
                       * branchUpdateReqBean.setDelFlage(String.valueOf(VCSCommon.ONE_INTEGER));
                       * branchUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
                       * branchUpdateReqBean.setUpdatedDate(CommonUtil.getCurrentTimeStamp());
                       * 
                       * this.getServiceInvoker().addRequest(branchUpdateReqBean); responseMessage =
                       * this.getServiceInvoker().invoke(); branchUpdateResBean = responseMessage.getMessageBean(0);
                       * updateStatus = branchUpdateResBean.getServiceStatus(); }
                       * 
                       * branchInsertReqBean = new BranchInsertServiceReqBean(); branchInsertResBean = new
                       * BranchInsertServiceResBean();
                       * 
                       * for(int l=0;l<insertResList.size();l++) {
                       * branchInsertReqBean.setBranchCode(insertResList.get(l).getBranchCode());
                       * branchInsertReqBean.setBranchName(insertResList.get(l).getBranchName());
                       * branchInsertReqBean.setBranchAddress(insertResList.get(l).getBranchAddress());
                       * branchInsertReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
                       * branchInsertReqBean.setCreatedDate(CommonUtil.getCurrentTimeStamp());
                       * branchInsertReqBean.setStoreId(updateResList.get(0).getStoreId());
                       * 
                       * 
                       * this.getServiceInvoker().addRequest(branchInsertReqBean); responseMessage =
                       * this.getServiceInvoker().invoke(); branchInsertResBean = responseMessage.getMessageBean(0);
                       * insertStatus = branchInsertResBean.getServiceStatus(); }
                       * 
                       * }
                       */
                    else if (null != branchSearchResDto & branchSearchResDto.size() < formBean.getBranchList().size()) {
                        updateList = new ArrayList<StoreListLineBean>();
                        for (int i = 0; i < formBean.getBranchList().size(); i++) {
                            if (null != formBean.getBranchList().get(i).getBranchId()) {
                                for (int j = 0; j < branchSearchResDto.size(); j++) {
                                    if (!formBean.getBranchList().get(i).isDone()) {
                                        if (formBean.getBranchList().get(i).getBranchId()
                                                .equals(branchSearchResDto.get(j).getBranchId())) {
                                            formBean.getBranchList().get(i).setDone(true);
                                            StoreListLineBean data = new StoreListLineBean();
                                            data = formBean.getBranchList().get(i);
                                            updateList.add(data);
                                            break;
                                        }
                                    }

                                }
                            } else {
                                StoreListLineBean data = new StoreListLineBean();
                                data = formBean.getBranchList().get(i);
                                insertList.add(data);
                            }
                        }

                        branchUpdateReqBean = new BranchUpdateServiceReqBean();
                        branchUpdateResBean = new BranchUpdateServiceResBean();

                        for (StoreListLineBean storeList : updateList) {

                            branchUpdateReqBean.setBranchCode(storeList.getBranchCode());
                            branchUpdateReqBean.setBranchName(storeList.getBranchName());
                            branchUpdateReqBean.setBranchAddress(storeList.getBranchAddress());
                            branchUpdateReqBean.setUpdatedBy(CommonUtil.getLoginUserName().toString());
                            branchUpdateReqBean.setUpdatedDate(CommonUtil.getCurrentTimeStamp());
                            branchUpdateReqBean.setBranchId(Integer.parseInt(storeList.getBranchId()));

                            this.getServiceInvoker().addRequest(branchUpdateReqBean);
                            responseMessage = this.getServiceInvoker().invoke();
                            branchUpdateResBean = responseMessage.getMessageBean(0);
                            updateStatus = branchUpdateResBean.getServiceStatus();

                        }

                        branchInsertReqBean = new BranchInsertServiceReqBean();
                        branchInsertResBean = new BranchInsertServiceResBean();

                        // StoreMaxReqDto storeMaxReqDto = new StoreMaxReqDto();
                        // StoreMaxResDto storeMaxResDto = (StoreMaxResDto)
                        // CommonUtil.getDaoServiceInvoker().execute(storeMaxReqDto);

                        // BranchMaxReqDto branchMaxReqDto = new BranchMaxReqDto();
                        // branchMaxReqDto.setShop_id(storeMaxResDto.getMax_id());
                        // BranchMaxResDto branchMaxResDto = (BranchMaxResDto)
                        // CommonUtil.getDaoServiceInvoker().execute(branchMaxReqDto);

                        /*
                         * int branchCount = VCSCommon.ZERO_INTEGER; BranchCountReqDto branchCountReqDto = new
                         * BranchCountReqDto(); branchCountReqDto.setShop_id(storeMaxResDto.getMax_id());
                         * 
                         * branchCount = (int) CommonUtil.getDaoServiceInvoker().execute(branchCountReqDto);
                         * if(branchCount == VCSCommon.ZERO_INTEGER) branchCount = VCSCommon.ONE_INTEGER; else
                         * branchCount++;
                         */

                        for (StoreListLineBean storeList : insertList) {

                            branchInsertReqBean.setBranchCode(storeList.getBranchCode());
                            branchInsertReqBean.setBranchName(storeList.getBranchName());
                            branchInsertReqBean.setBranchAddress(storeList.getBranchAddress());
                            branchInsertReqBean.setCreatedBy(CommonUtil.getLoginUserName().toString());
                            branchInsertReqBean.setCreatedDate(CommonUtil.getCurrentTimeStamp());
                            branchInsertReqBean.setStoreId(formBean.getStoreManagementHeaderBean().getStoreId());

                            this.getServiceInvoker().addRequest(branchInsertReqBean);
                            responseMessage = this.getServiceInvoker().invoke();
                            branchInsertResBean = responseMessage.getMessageBean(0);
                            insertStatus = branchInsertResBean.getServiceStatus();

                        }

                    }

                    if (ServiceStatusCode.OK.equals(serviceStatus)
                            | (ServiceStatusCode.OK.equals(updateStatus) & ServiceStatusCode.OK.equals(insertStatus))) {
                        messageBean = new MessageBean(MessageId.MI0002);
                        messageBean.setMessageType(MessageType.INFO);
                        formBean.getMessageContainer().addMessage(messageBean);

                        applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
                        applicationLogger.log("Store Update Process finished.", LogLevel.INFO);

                    }
                }

            }
        } catch (PrestoDBException e) {

            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
                messageBean = new MessageBean(MessageId.MI0001);

                applicationLogger.log(messageBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Record Duplicated.", LogLevel.ERROR);
                throw new BaseException();

            }
            if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
                applicationLogger.log("SQL Error.", LogLevel.ERROR);
                throw new BaseException();
            }
        }
        return formBean;
    }

    private boolean isValidData(StoreManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getStoreManagementHeaderBean().getStoreName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SHOP_NAME));
            msgBean.addColumnId(DisplayItem.STORE_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        /*
         * if (InputChecker.isBlankOrNull(formBean.getStoreManagementHeaderBean().getStoreCode())) { msgBean = new
         * MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SHOP_CODE));
         * msgBean.addColumnId(DisplayItem.SHOP_CODE); msgBean.setMessageType(MessageType.ERROR);
         * formBean.getMessageContainer().addMessage(msgBean); isValid = false; }
         */

        if (InputChecker.isBlankOrNull(formBean.getStoreManagementHeaderBean().getStoreAddress())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.ADDRESS));
            msgBean.addColumnId(DisplayItem.ADDRESS);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getBranchList() == null || formBean.getBranchList().isEmpty()) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.BRANCH));
            msgBean.addColumnId(DisplayItem.BRANCH);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        for (StoreListLineBean storeListLineBean : formBean.getBranchList()) {
            if (InputChecker.isBlankOrNull(storeListLineBean.getBranchName())
                    || InputChecker.isBlankOrNull(storeListLineBean.getBranchAddress())) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.BRANCH));
                msgBean.addColumnId(DisplayItem.BRANCH);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
                break;
            }
        }

        return isValid;
    }

}
