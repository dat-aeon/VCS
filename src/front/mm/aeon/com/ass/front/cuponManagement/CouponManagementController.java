/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.util.CollectionUtils;

import mm.aeon.com.ass.base.dto.couponCount.CouponCountReqDto;
import mm.aeon.com.ass.base.dto.couponCount.CouponCountResDto;
import mm.aeon.com.ass.base.dto.couponCount.SearchCouponCodeReqDto;
import mm.aeon.com.ass.base.dto.storeCouponRefer.StoreCouponReferReqDto;
import mm.aeon.com.ass.base.dto.storeCouponRefer.StoreCouponReferResDto;
import mm.aeon.com.ass.base.service.couponDeleteService.CusCouponDeleteServiceReqBean;
import mm.aeon.com.ass.base.service.couponInsertService.CouponInsertServiceReqBean;
import mm.aeon.com.ass.base.service.couponInsertService.CusCouponInsertServiceReqBean;
import mm.aeon.com.ass.base.service.couponUpdateService.CouponUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.storeCouponRegisterService.StoreCouponRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.storeCouponUpdateService.StoreCouponDeleteServiceReqBean;
import mm.aeon.com.ass.base.service.storeCouponUpdateService.StoreCouponUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.front.couponList.CustomerDataBean;
import mm.aeon.com.ass.front.sessions.LoginUserInfo;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.authenticate.PasswordEncoder;
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

public class CouponManagementController extends AbstractController
        implements IControllerAccessor<CouponManagementFormBean, CouponManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CouponManagementFormBean process(CouponManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Coupon Registration]", new InvalidScreenTransitionException(),
         * LogLevel.ERROR); throw new InvalidScreenTransitionException(); }
         */

        if (!isValidData(formBean)) {
            return formBean;
        }

        MessageBean msgBean;
        CouponInsertServiceReqBean couponReqBean = new CouponInsertServiceReqBean();

        String serviceStatus = "READY";

        CouponCountReqDto countReqDto = new CouponCountReqDto();
        CouponCountResDto countResDto = new CouponCountResDto();
        // String originalPath = null;
        // String mobilePath = null;

        if (!formBean.getIsUpdate()) {
            applicationLogger.log("Coupon Registration Process Stared.", LogLevel.INFO);

            try {

                int couponCount = VCSCommon.ZERO_INTEGER;
                countResDto = (CouponCountResDto) CommonUtil.getDaoServiceInvoker().execute(countReqDto);
                if (null != countResDto)
                    couponCount = countResDto.getMax_id() + VCSCommon.ONE_INTEGER;

                SearchCouponCodeReqDto searchCode = new SearchCouponCodeReqDto();
                searchCode.setCoupon_code(VCSCommon.COUPON_CODE + couponCount);
                int count = VCSCommon.ZERO_INTEGER;
                count = (int) CommonUtil.getDaoServiceInvoker().execute(searchCode);
                if (count < 1) {

                    couponReqBean.setCreated_by(CommonUtil.getLoginUserName().toString());
                    couponReqBean.setCreated_time(CommonUtil.getCurrentTimeStamp());
                    couponReqBean.setCoupon_name(formBean.getCouponManagementHeaderBean().getCouponName());
                    couponReqBean.setCouponNameMya(formBean.getCouponManagementHeaderBean().getCouponNameMya());
                    couponReqBean.setCoupon_desc(formBean.getCouponManagementHeaderBean().getCouponDescription());
                    couponReqBean.setCouponDescriptionMya(
                            formBean.getCouponManagementHeaderBean().getCouponDescriptionMya());
                    Date startTime =
                            CommonUtil.getStartOfDay(formBean.getCouponManagementHeaderBean().getCouponStrDate());
                    Date endTime = CommonUtil.getEndOfDay(formBean.getCouponManagementHeaderBean().getCouponExpDate());
                    couponReqBean.setStartDate(new Timestamp(startTime.getTime()));
                    couponReqBean.setExpireDate(new Timestamp(endTime.getTime()));
                    couponReqBean.setDiscount_percent(formBean.getCouponManagementHeaderBean().getCouponDiscount());
                    couponReqBean.setCoupon_amount(formBean.getCouponManagementHeaderBean().getCouponAmount());
                    couponReqBean.setDel_flag(0);
                    couponReqBean.setCoupon_code(VCSCommon.COUPON_CODE + couponCount);
                    couponReqBean.setUnuse_image_path(formBean.getUploadImageFileName());
                    couponReqBean.setUse_image_path(formBean.getUploadImageFileName());
                    couponReqBean.setCouponNoOfCus(formBean.getCouponManagementHeaderBean().getCouponNoOfCus());
                    couponReqBean.setCouponTlNo(formBean.getCouponManagementHeaderBean().getCouponTlNo());
                    couponReqBean.setCouponSpEvent(formBean.getCouponManagementHeaderBean().getCouponSpEvent());
                    couponReqBean.setCouponSpEventMya(formBean.getCouponManagementHeaderBean().getCouponSpEventMya());
                    couponReqBean.setDiscount_unit(formBean.getCouponManagementHeaderBean().getDiscount_unit());

                    this.getServiceInvoker().addRequest(couponReqBean);
                    ResponseMessage responseMessage = this.getServiceInvoker().invoke();

                    AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
                    serviceStatus = resBean.getServiceStatus();

                    countResDto = (CouponCountResDto) CommonUtil.getDaoServiceInvoker().execute(countReqDto);
                    couponReqBean.setCoupon_id(countResDto.getMax_id());

                    if (ServiceStatusCode.OK.equals(serviceStatus)) {
                        /*
                         * for (int i = 0; i <
                         * formBean.getCouponManagementFileBean().getCustomerUploadBeanList().size(); i++) {
                         * CustomerSearchByCouponReqDto customerReqDto = new CustomerSearchByCouponReqDto();
                         * CustomerSearchByCouponResDto customerResDto = new CustomerSearchByCouponResDto();
                         * 
                         * customerReqDto.setCustomer_no(formBean.getCouponManagementFileBean().
                         * getCustomerUploadBeanList().get(i).getCustomer_no());
                         * 
                         * customerResDto = (CustomerSearchByCouponResDto)
                         * this.getDaoServiceInvoker().execute(customerReqDto);
                         * 
                         * customerListResDto.add(customerResDto); }
                         */
                        LoginUserInfo loginUser = new LoginUserInfo();
                        loginUser = CommonUtil.getLoginUserInfo();

                        for (CustomerUploadBean customerUploadBean : formBean.getCouponManagementFileBean()
                                .getCustomerUploadBeanList()) {

                            CusCouponInsertServiceReqBean cusReqBean = new CusCouponInsertServiceReqBean();

                            cusReqBean.setCustomer_id(customerUploadBean.getCustomer_id());
                            cusReqBean.setCoupon_id(couponReqBean.getCoupon_id());
                            // cusReqBean.setStatus(formBean.getCouponManagementHeaderBean().getStatus());
                            cusReqBean.setStatus(VCSCommon.UNUSED);
                            // cusReqBean.setUpdated_by(CommonUtil.getLoginUserName().toString());
                            cusReqBean
                                    .setUpdated_by(String.valueOf(loginUser.getId()) + "," + loginUser.getUserTypeId());
                            cusReqBean.setUpdated_time(CommonUtil.getCurrentTimeStamp());

                            this.getServiceInvoker().addRequest(cusReqBean);
                            ResponseMessage responseMessage1 = this.getServiceInvoker().invoke();

                            AbstractServiceResBean resBean1 = responseMessage1.getMessageBean(0);

                            serviceStatus = resBean1.getServiceStatus();
                        }

                        if (ServiceStatusCode.OK.equals(serviceStatus)) {

                            for (StoreCouponDataBean store : formBean.getStoreCouponBranchList()) {
                                // StoreCouponUpdateServiceReqBean storeReqBean = new StoreCouponUpdateServiceReqBean();
                                StoreCouponRegisterServiceReqBean storeReqBean =
                                        new StoreCouponRegisterServiceReqBean();

                                storeReqBean.setBranch_id(store.getBranch_id());
                                storeReqBean.setStore_id(store.getStore_id());
                                storeReqBean.setCoupon_id(couponReqBean.getCoupon_id());
                                String hashPassword = PasswordEncoder.encode(store.getCoupon_password());
                                storeReqBean.setCoupon_password(hashPassword);
                                // storeReqBean.setCoupon_password(CommonUtil.decodePassword(store.getCoupon_password(),
                                // false));
                                storeReqBean.setUpdated_by(CommonUtil.getLoginUserName().toString());
                                storeReqBean.setUpdated_time(CommonUtil.getCurrentTimeStamp());

                                this.getServiceInvoker().addRequest(storeReqBean);

                                ResponseMessage responseMessage2 = this.getServiceInvoker().invoke();

                                AbstractServiceResBean resBean2 = responseMessage2.getMessageBean(0);
                                serviceStatus = resBean2.getServiceStatus();
                            }

                            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                                File sourceFile =
                                        new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                                File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                                        + CommonUtil.getCouponUploadImageFolder() + formBean.getUploadImageFileName());

                                try {
                                    FileHandler.copyFile(sourceFile, destFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                msgBean = new MessageBean(MessageId.MI0001);
                                msgBean.setMessageType(MessageType.INFO);
                                formBean.getMessageContainer().addMessage(msgBean);

                                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                                applicationLogger.log("Coupon Registration Process finished.", LogLevel.INFO);
                            }
                        }

                    }

                } else {
                    msgBean = new MessageBean(MessageId.ME1012,
                            DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_CODE));
                    msgBean.addColumnId(DisplayItem.COUPON_CODE);
                    msgBean.setMessageType(MessageType.ERROR);
                    formBean.getMessageContainer().addMessage(msgBean);
                }

            } catch (PrestoDBException e) {
                if (e instanceof DaoSqlException) {
                    logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                    throw new BaseException(e.getCause());
                }
            }
        } else {

            try {

                applicationLogger.log("Coupon Update Process started.", LogLevel.INFO);

                CouponUpdateServiceReqBean updateServiceReqBean = new CouponUpdateServiceReqBean();
                updateServiceReqBean.setCoupon_id(formBean.getCouponManagementHeaderBean().getCid());
                updateServiceReqBean.setCoupon_name(formBean.getCouponManagementHeaderBean().getCouponName());
                updateServiceReqBean.setCouponNameMya(formBean.getCouponManagementHeaderBean().getCouponNameMya());
                updateServiceReqBean.setCoupon_desc(formBean.getCouponManagementHeaderBean().getCouponDescription());
                updateServiceReqBean
                        .setCouponDescriptionMya(formBean.getCouponManagementHeaderBean().getCouponDescriptionMya());
                Date startTime = CommonUtil.getStartOfDay(formBean.getCouponManagementHeaderBean().getCouponStrDate());
                Date endTime = CommonUtil.getEndOfDay(formBean.getCouponManagementHeaderBean().getCouponExpDate());
                updateServiceReqBean.setStartDate(new Timestamp(startTime.getTime()));
                updateServiceReqBean.setExpireDate(new Timestamp(endTime.getTime()));
                updateServiceReqBean
                        .setCouponSpEventMya(formBean.getCouponManagementHeaderBean().getCouponSpEventMya());
                updateServiceReqBean.setDiscount_percent(formBean.getCouponManagementHeaderBean().getCouponDiscount());
                updateServiceReqBean.setCoupon_amount(formBean.getCouponManagementHeaderBean().getCouponAmount());
                updateServiceReqBean.setDel_flag(0);
                updateServiceReqBean.setCoupon_code(formBean.getCouponManagementHeaderBean().getCoupon_code());

                /*
                 * if(formBean.getCouponManagementImageBean() == null &
                 * formBean.getCouponManagementHeaderBean().getImageFilePath() != null) { //originalPath =
                 * formBean.getCouponManagementHeaderBean().getImageFilePath(); //mobilePath =
                 * originalPath.replace(originalPath.substring(0, originalPath.indexOf("\\P")+1),
                 * VCSCommon.IMAGESAVEPATH);
                 * updateServiceReqBean.setUnuse_image_path(formBean.getCouponManagementHeaderBean().getImageFilePath())
                 * ; } else if(formBean.getCouponManagementImageBean() != null &
                 * formBean.getCouponManagementHeaderBean().getImageFilePath() == null) { //originalPath =
                 * formBean.getCouponManagementImageBean().getImageFilePath(); //mobilePath =
                 * originalPath.replace(originalPath.substring(0, originalPath.indexOf("\\P")+1),
                 * VCSCommon.IMAGESAVEPATH);
                 * updateServiceReqBean.setUnuse_image_path(formBean.getCouponManagementImageBean().getImageFilePath());
                 * } else if(formBean.getCouponManagementHeaderBean().getCopyImageFilePath() != null &
                 * formBean.getCouponManagementHeaderBean().getImageFilePath() == null) { //originalPath =
                 * formBean.getCouponManagementHeaderBean().getUploadedImageFilePath(); //mobilePath =
                 * originalPath.replace(originalPath.substring(0, originalPath.indexOf("\\P")+1),
                 * VCSCommon.IMAGESAVEPATH);
                 * updateServiceReqBean.setUnuse_image_path(formBean.getCouponManagementHeaderBean().
                 * getCopyImageFilePath()); } else { //originalPath =
                 * formBean.getCouponManagementHeaderBean().getImageFilePath(); //mobilePath =
                 * originalPath.replace(originalPath.substring(0, originalPath.indexOf("\\P")+1),
                 * VCSCommon.IMAGESAVEPATH);
                 * updateServiceReqBean.setUnuse_image_path(formBean.getCouponManagementHeaderBean().getImageFilePath())
                 * ; }
                 */
                // updateServiceReqBean.setUnuse_image_path(formBean.getUploadedImage().substring(
                // formBean.getUploadedImage().indexOf("images" + File.separator) + 7,
                // formBean.getUploadedImage().length()));

                updateServiceReqBean
                        .setUnuse_image_path(formBean.getCouponManagementHeaderBean().getUploadedImageFilePath());
                updateServiceReqBean
                        .setUse_image_path(formBean.getCouponManagementHeaderBean().getUploadedImageFilePath());
                updateServiceReqBean.setCreated_by(CommonUtil.getLoginUserName().toString());
                updateServiceReqBean.setCreated_time(CommonUtil.getCurrentTimeStamp());
                updateServiceReqBean.setUpdated_by(CommonUtil.getLoginUserName().toString());
                updateServiceReqBean.setUpdated_time(CommonUtil.getCurrentTimeStamp());
                updateServiceReqBean.setCouponNoOfCus(formBean.getCouponManagementHeaderBean().getCouponNoOfCus());
                updateServiceReqBean.setCouponTlNo(formBean.getCouponManagementHeaderBean().getCouponTlNo());
                updateServiceReqBean.setCouponSpEvent(formBean.getCouponManagementHeaderBean().getCouponSpEvent());
                updateServiceReqBean
                        .setCouponSpEventMya(formBean.getCouponManagementHeaderBean().getCouponSpEventMya());
                updateServiceReqBean.setDiscount_unit(formBean.getCouponManagementHeaderBean().getDiscount_unit());

                this.getServiceInvoker().addRequest(updateServiceReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();

                AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
                serviceStatus = resBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {

                    // delete removed customer
                    for (CustomerDataBean customerDataBean : formBean.getRemovedCouponCustomerList()) {

                        CusCouponDeleteServiceReqBean cusCouponDeleteServiceReqBean =
                                new CusCouponDeleteServiceReqBean();

                        cusCouponDeleteServiceReqBean.setCustomer_id(customerDataBean.getCustomer_id());
                        cusCouponDeleteServiceReqBean.setCoupon_id(updateServiceReqBean.getCoupon_id());

                        this.getServiceInvoker().addRequest(cusCouponDeleteServiceReqBean);
                        ResponseMessage responseMessage1 = this.getServiceInvoker().invoke();

                        AbstractServiceResBean resBean1 = responseMessage1.getMessageBean(0);

                        serviceStatus = resBean1.getServiceStatus();
                    }

                    if (formBean.getCustomerUploadBeanList() != null) {
                        LoginUserInfo loginUser = new LoginUserInfo();
                        loginUser = CommonUtil.getLoginUserInfo();

                        for (CustomerUploadBean customerUploadBean : formBean.getCustomerUploadBeanList()) {
                            CusCouponInsertServiceReqBean cusReqBean = new CusCouponInsertServiceReqBean();

                            cusReqBean.setCustomer_id(customerUploadBean.getCustomer_id());
                            cusReqBean.setCoupon_id(updateServiceReqBean.getCoupon_id());
                            cusReqBean.setStatus(VCSCommon.UNUSED);
                            cusReqBean
                                    .setUpdated_by(String.valueOf(loginUser.getId()) + "," + loginUser.getUserTypeId());
                            cusReqBean.setUpdated_time(CommonUtil.getCurrentTimeStamp());

                            this.getServiceInvoker().addRequest(cusReqBean);
                            ResponseMessage responseMessage1 = this.getServiceInvoker().invoke();

                            AbstractServiceResBean resBean1 = responseMessage1.getMessageBean(0);

                            serviceStatus = resBean1.getServiceStatus();
                        }
                    }

                    if (ServiceStatusCode.OK.equals(serviceStatus)) {

                        StoreCouponUpdateServiceReqBean storeReqBean = null;
                        StoreCouponReferResDto storeResBean = new StoreCouponReferResDto();
                        StoreCouponRegisterServiceReqBean storeRegReqBean = new StoreCouponRegisterServiceReqBean();

                        // delete removed shop coupon branch
                        for (StoreCouponDataBean storeCouponDataBean : formBean.getRemovedShopBranchList()) {

                            StoreCouponDeleteServiceReqBean storeCouponDeleteServiceReqBean =
                                    new StoreCouponDeleteServiceReqBean();

                            storeCouponDeleteServiceReqBean.setBranch_id(storeCouponDataBean.getBranch_id());
                            storeCouponDeleteServiceReqBean.setCoupon_id(updateServiceReqBean.getCoupon_id());
                            storeCouponDeleteServiceReqBean.setStore_id(storeCouponDataBean.getStore_id());

                            this.getServiceInvoker().addRequest(storeCouponDeleteServiceReqBean);
                            ResponseMessage responseMessage1 = this.getServiceInvoker().invoke();

                            AbstractServiceResBean resBean1 = responseMessage1.getMessageBean(0);

                            serviceStatus = resBean1.getServiceStatus();
                        }

                        for (StoreCouponDataBean store : formBean.getStoreCouponBranchList()) {

                            StoreCouponReferReqDto referReqDto = new StoreCouponReferReqDto();
                            referReqDto.setBranch_id(store.getBranch_id());
                            referReqDto.setCoupon_id(updateServiceReqBean.getCoupon_id());

                            storeResBean =
                                    (StoreCouponReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);

                            if (storeResBean != null) {
                                // String encodedPassword = storeResBean.getCoupon_password();
                                // if(!InputChecker.isBlankOrNull(encodedPassword)) {
                                String password = PasswordEncoder.encode(store.getCoupon_password().trim());
                                // if(password.equals(encodedPassword)) {

                                storeReqBean = new StoreCouponUpdateServiceReqBean();

                                storeReqBean.setBranch_id(store.getBranch_id());
                                // String hashPassword = PasswordEncoder.encode(store.getCoupon_password());
                                storeReqBean.setCoupon_password(password);
                                storeReqBean.setUpdated_by(CommonUtil.getLoginUserName().toString());
                                storeReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                                this.getServiceInvoker().addRequest(storeReqBean);

                                ResponseMessage responseMessage2 = this.getServiceInvoker().invoke();

                                AbstractServiceResBean resBean2 = responseMessage2.getMessageBean(0);
                                serviceStatus = resBean2.getServiceStatus();
                                /*
                                 * }else { msgBean = new MessageBean(MessageId.ME1026);
                                 * msgBean.setMessageType(MessageType.WARN);
                                 * formBean.getMessageContainer().addMessage(msgBean);
                                 * 
                                 * applicationLogger.log(msgBean.getMessage(), LogLevel.WARN);
                                 * applicationLogger.log("Password does not match.", LogLevel.WARN);
                                 * 
                                 * return formBean; }
                                 */
                                // }
                            } else {
                                if (ServiceStatusCode.OK.equals(serviceStatus)) {

                                    storeRegReqBean = new StoreCouponRegisterServiceReqBean();

                                    storeRegReqBean.setBranch_id(store.getBranch_id());
                                    storeRegReqBean.setStore_id(store.getStore_id());
                                    storeRegReqBean.setCoupon_id(formBean.getCouponManagementHeaderBean().getCid());
                                    String hashPassword = PasswordEncoder.encode(store.getCoupon_password());
                                    storeRegReqBean.setCoupon_password(hashPassword);
                                    // storeRegReqBean.setCoupon_password(CommonUtil.decodePassword(store.getCoupon_password(),false));
                                    storeRegReqBean.setUpdated_by(CommonUtil.getLoginUserName().toString());
                                    storeRegReqBean.setUpdated_time(CommonUtil.getCurrentTimeStamp());

                                    this.getServiceInvoker().addRequest(storeRegReqBean);

                                    ResponseMessage responseMessage2 = this.getServiceInvoker().invoke();

                                    AbstractServiceResBean resBean2 = responseMessage2.getMessageBean(0);
                                    serviceStatus = resBean2.getServiceStatus();

                                }
                            }
                        }

                        if (ServiceStatusCode.OK.equals(serviceStatus)) {

                            if (formBean.getOldUploadImageFilePath() != null) {
                                File sourceFile =
                                        new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                                File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                                        + CommonUtil.getCouponUploadImageFolder()
                                        + formBean.getCouponManagementHeaderBean().getUploadedImageFilePath());

                                try {
                                    FileHandler.copyFile(sourceFile, destFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            msgBean = new MessageBean(MessageId.MI0002);
                            msgBean.setMessageType(MessageType.INFO);
                            formBean.getMessageContainer().addMessage(msgBean);

                            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                            applicationLogger.log("Coupon Update Process finished.", LogLevel.INFO);
                        }

                    }
                }

            } catch (PrestoDBException e) {
                e.printStackTrace();
            }
        }

        return formBean;
    }

    private boolean isValidData(CouponManagementFormBean formBean) {
        boolean isValid = true;
        // boolean isCheckCode = false;
        MessageBean msgBean;

        /*
         * if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCoupon_code())) { msgBean = new
         * MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_CODE));
         * msgBean.addColumnId(DisplayItem.COUPON_CODE); msgBean.setMessageType(MessageType.ERROR);
         * formBean.getMessageContainer().addMessage(msgBean); isCheckCode = true; isValid = false; }
         * 
         * if (!isCheckCode & formBean.getUpdateParam() == null) { if
         * (!formBean.getCouponManagementHeaderBean().getCoupon_code().matches("\\d+")) { msgBean = new
         * MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_CODE));
         * msgBean.addColumnId(DisplayItem.COUPON_CODE); msgBean.setMessageType(MessageType.ERROR);
         * formBean.getMessageContainer().addMessage(msgBean); isValid = false; } }
         */
        if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_NAME));
            msgBean.addColumnId(DisplayItem.COUPON_NAME_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getCouponManagementHeaderBean().getCouponName())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_NAME));
            msgBean.addColumnId(DisplayItem.COUPON_NAME_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (formBean.getCouponManagementHeaderBean().getCouponNameMya().matches(VCSCommon.PATTERN)
        // && !InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponNameMya())) {
        // msgBean =
        // new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_NAME_MYA));
        // msgBean.addColumnId(DisplayItem.COUPON_NAME_MYA);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponNameMya())) {
            msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_NAME_MYA));
            msgBean.addColumnId(DisplayItem.COUPON_NAME_MYA);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponDescription())) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_DESCRIPTION));
            msgBean.addColumnId(DisplayItem.COUPON_DESCRIPTION_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getCouponManagementHeaderBean().getCouponDescription())) {
            msgBean = new MessageBean(MessageId.ME1003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_DESCRIPTION));
            msgBean.addColumnId(DisplayItem.COUPON_DESCRIPTION_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponDescriptionMya())) {
            msgBean = new MessageBean(MessageId.ME0003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_DESCRIPTION_MYA));
            msgBean.addColumnId(DisplayItem.COUPON_DESCRIPTION_MYA);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        // if (formBean.getCouponManagementHeaderBean().getCouponDescriptionMya().matches(VCSCommon.PATTERN)
        // && !InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponDescriptionMya())) {
        // msgBean = new MessageBean(MessageId.ME1003,
        // DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_DESCRIPTION_MYA));
        // msgBean.addColumnId(DisplayItem.COUPON_DESCRIPTION_MYA);
        // msgBean.setMessageType(MessageType.ERROR);
        // formBean.getMessageContainer().addMessage(msgBean);
        // isValid = false;
        // }

        if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getStoreName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SHOP_NAME));
            msgBean.addColumnId(DisplayItem.STORE_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getCouponManagementHeaderBean().getCouponStrDate() == null) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.STR_DATE));
            msgBean.addColumnId(DisplayItem.STR_DATE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getCouponManagementHeaderBean().getCouponExpDate() == null) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.EXP_DATE));
            msgBean.addColumnId(DisplayItem.EXP_DATE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getCouponManagementHeaderBean().getCouponStrDate() != null
                && formBean.getCouponManagementHeaderBean().getCouponExpDate() != null) {
            if (formBean.getCouponManagementHeaderBean().getCouponStrDate()
                    .after(formBean.getCouponManagementHeaderBean().getCouponExpDate())) {
                msgBean = new MessageBean(MessageId.ME1035, "");
                msgBean.addColumnId(DisplayItem.STR_DATE + VCSCommon.INPUT);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (formBean.getCouponManagementHeaderBean().getCouponDiscount() == 0) {
            msgBean = new MessageBean(MessageId.ME0007, DisplayItemBean.getDisplayItemName(DisplayItem.GOOD_PRICE));
            msgBean.addColumnId(DisplayItem.GOOD_PRICE + VCSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (CollectionUtils.isEmpty(formBean.getStoreCouponBranchList())) {
            msgBean = new MessageBean(MessageId.ME1033);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!CommonUtil.isPureAscii(formBean.getCouponManagementHeaderBean().getCouponSpEvent())) {
            msgBean = new MessageBean(MessageId.ME1003,
                    DisplayItemBean.getDisplayItemName(DisplayItem.SPECIAL_EVENT_ENG));
            msgBean.addColumnId(DisplayItem.SPECIAL_EVENT_ENG);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getStoreCouponBranchList().size() > 0) {

            for (int i = 0; i < formBean.getStoreCouponBranchList().size(); i++) {
                if (InputChecker.isBlankOrNull(formBean.getStoreCouponBranchList().get(i).getCoupon_password())
                        | formBean.getStoreCouponBranchList().get(i).getCoupon_password() == null) {
                    msgBean =
                            new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PASSWORD));
                    msgBean.addColumnId(DisplayItem.PASSWORD);
                    msgBean.setMessageType(MessageType.ERROR);
                    formBean.getMessageContainer().addMessage(msgBean);
                    isValid = false;
                }
            }
        }

        /*
         * if (InputChecker.isBlankOrNull(formBean.getCouponManagementHeaderBean().getCouponSpEvent())) { msgBean = new
         * MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.SPECIAL_EVENT));
         * msgBean.addColumnId(DisplayItem.SPECIAL_EVENT); msgBean.setMessageType(MessageType.ERROR);
         * formBean.getMessageContainer().addMessage(msgBean); isValid = false; }
         */

        if (formBean.getUpdateParam() == null) {
            if (null == formBean.getCouponManagementImageBean()) {
                msgBean =
                        new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_IMAGE));
                msgBean.addColumnId(DisplayItem.COUPON_IMAGE);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
            if (null != formBean.getCouponManagementImageBean()) {
                if (formBean.getCouponManagementImageBean().getCouponImage() == null
                        & formBean.getCouponManagementHeaderBean().getCouponImage() == null) {
                    msgBean = new MessageBean(MessageId.ME1025,
                            DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_IMAGE));
                    msgBean.addColumnId(DisplayItem.COUPON_IMAGE);
                    msgBean.setMessageType(MessageType.ERROR);
                    formBean.getMessageContainer().addMessage(msgBean);
                    isValid = false;
                }
            }

            if (null == formBean.getCouponManagementFileBean()) {
                msgBean =
                        new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_FILE));
                msgBean.addColumnId(DisplayItem.COUPON_FILE);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (null != formBean.getCouponManagementFileBean()) {
                if (null == formBean.getCouponManagementFileBean().getImportFile()
                        & null == formBean.getCouponManagementHeaderBean().getImportFile()) {
                    msgBean = new MessageBean(MessageId.ME1025,
                            DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_FILE));
                    msgBean.addColumnId(DisplayItem.COUPON_FILE);
                    msgBean.setMessageType(MessageType.ERROR);
                    formBean.getMessageContainer().addMessage(msgBean);
                    isValid = false;
                }
            }
        } else {
            if (formBean.getTempUploadImageFilePath() == null) {
                /*
                 * if (null == formBean.getCouponManagementHeaderBean().getCopyImageFilePath() & null ==
                 * formBean.getCouponManagementHeaderBean().getCouponImage()) {
                 */
                msgBean =
                        new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.COUPON_IMAGE));
                msgBean.addColumnId(DisplayItem.COUPON_IMAGE);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }
        return isValid;
    }

}
