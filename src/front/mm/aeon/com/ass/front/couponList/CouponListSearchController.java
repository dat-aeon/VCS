/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import mm.aeon.com.ass.base.dto.couponSearch.CouponSearchReqDto;
import mm.aeon.com.ass.base.dto.couponSearch.CouponSelectCountReqDto;
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

public class CouponListSearchController extends AbstractController
        implements IControllerAccessor<CouponListFormBean, CouponListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CouponListFormBean process(CouponListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Coupon List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Coupon Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        CouponSelectCountReqDto couponSelectCountReqDto = new CouponSelectCountReqDto();

        if (formBean.getSearchHeaderBean().getStore_name() != null
                && formBean.getSearchHeaderBean().getStore_name() != "") {
            couponSelectCountReqDto.setStore_name("%" + formBean.getSearchHeaderBean().getStore_name() + "%");

        }

        if (formBean.getSearchHeaderBean().getCoupon_code() != null
                && formBean.getSearchHeaderBean().getCoupon_code() != "") {
            couponSelectCountReqDto.setCoupon_code("%" + formBean.getSearchHeaderBean().getCoupon_code() + "%");
        }

        if (formBean.getSearchHeaderBean().getCoupon_name() != null
                && formBean.getSearchHeaderBean().getCoupon_name() != "") {
            couponSelectCountReqDto.setCoupon_name("%" + formBean.getSearchHeaderBean().getCoupon_name() + "%");
        }

        if (formBean.getSearchHeaderBean().getAmount() != null && formBean.getSearchHeaderBean().getAmount() != "") {
            couponSelectCountReqDto.setCoupon_amount(Double.parseDouble(formBean.getSearchHeaderBean().getAmount()));
        }

        CouponSearchReqDto couponSearchReqDto = new CouponSearchReqDto();
        couponSearchReqDto.setStore_name(couponSelectCountReqDto.getStore_name());
        couponSearchReqDto.setCoupon_code(couponSelectCountReqDto.getCoupon_code());
        couponSearchReqDto.setCoupon_name(couponSelectCountReqDto.getCoupon_name());
        couponSearchReqDto.setCoupon_amount(couponSelectCountReqDto.getCoupon_amount());

        // reqDto.setCoupon_amount(formBean.getSearchHeaderBean().getCoupon_amount());

        try {
            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(couponSelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setCouponSearchReqDto(couponSearchReqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Coupon searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
