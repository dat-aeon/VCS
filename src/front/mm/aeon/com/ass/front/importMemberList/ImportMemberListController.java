/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import mm.aeon.com.ass.base.dto.importMemberSearch.ImportMemberSearchReqDto;
import mm.aeon.com.ass.base.dto.importMemberSearch.ImportMemberSelectCountReqDto;
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

public class ImportMemberListController extends AbstractController
        implements IControllerAccessor<ImportMemberListFormBean, ImportMemberListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ImportMemberListFormBean process(ImportMemberListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Customer List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
         * throw new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Imported Member searching process started.", LogLevel.INFO);
        MessageBean messageBean;

        if (formBean.isClear()) {
            formBean.setSearchHeaderBean(new ImportMemberListHeaderBean());
            formBean.setClear(false);
        }

        ImportMemberSelectCountReqDto importMemberSelectCountReqDto = new ImportMemberSelectCountReqDto();

        ImportMemberListHeaderBean formBeanSearchHeaderBean = formBean.getSearchHeaderBean();

        if (null != formBeanSearchHeaderBean.getName()) {
            importMemberSelectCountReqDto.setName(formBeanSearchHeaderBean.getName().trim().toLowerCase());
        }

        if (null != formBeanSearchHeaderBean.getTownship()) {
            importMemberSelectCountReqDto.setTownship(formBeanSearchHeaderBean.getTownship().trim().toLowerCase());
        }

        if (null != formBeanSearchHeaderBean.getCustomerNo()) {
            importMemberSelectCountReqDto.setCustomerNo(formBeanSearchHeaderBean.getCustomerNo().trim());
        }

        if (null != formBeanSearchHeaderBean.getAgeFrom()) {
            importMemberSelectCountReqDto
                    .setDobFrom(CommonUtil.minusYearsFromToday(formBeanSearchHeaderBean.getAgeFrom()));
        }

        if (null != formBeanSearchHeaderBean.getAgeTo()) {
            importMemberSelectCountReqDto.setDobTo(CommonUtil.minusYearsFromToday(formBeanSearchHeaderBean.getAgeTo()));
        }

        if (null != formBeanSearchHeaderBean.getPhoneNo()) {
            importMemberSelectCountReqDto.setPhoneNo(formBeanSearchHeaderBean.getPhoneNo().trim());
        }

        if (null != formBeanSearchHeaderBean.getNrcNo()) {
            importMemberSelectCountReqDto.setNrcNo(formBeanSearchHeaderBean.getNrcNo().trim());
        }

        if (null != formBeanSearchHeaderBean.getMemberCardId()) {
            importMemberSelectCountReqDto.setMemberCardId(formBeanSearchHeaderBean.getMemberCardId().trim());
        }

        importMemberSelectCountReqDto.setCreatedDateFrom(formBeanSearchHeaderBean.getCreatedDateFrom());
        importMemberSelectCountReqDto.setCreatedDateTo(formBeanSearchHeaderBean.getCreatedDateTo());
        importMemberSelectCountReqDto.setSalaryMax(formBeanSearchHeaderBean.getSalaryMax());
        importMemberSelectCountReqDto.setSalaryMin(formBeanSearchHeaderBean.getSalaryMin());
        importMemberSelectCountReqDto.setMembercardStatus(formBeanSearchHeaderBean.getMembercardStatus());

        ImportMemberSearchReqDto importMemberSearchReqDto = new ImportMemberSearchReqDto();
        importMemberSearchReqDto.setName(importMemberSelectCountReqDto.getName());
        importMemberSearchReqDto.setTownship(importMemberSelectCountReqDto.getTownship());
        importMemberSearchReqDto.setCustomerNo(importMemberSelectCountReqDto.getCustomerNo());
        importMemberSearchReqDto.setDobFrom(importMemberSelectCountReqDto.getDobFrom());
        importMemberSearchReqDto.setDobTo(importMemberSelectCountReqDto.getDobTo());
        importMemberSearchReqDto.setPhoneNo(importMemberSelectCountReqDto.getPhoneNo());
        importMemberSearchReqDto.setNrcNo(importMemberSelectCountReqDto.getNrcNo());
        importMemberSearchReqDto.setSalaryMax(importMemberSelectCountReqDto.getSalaryMax());
        importMemberSearchReqDto.setSalaryMin(importMemberSelectCountReqDto.getSalaryMin());
        importMemberSearchReqDto.setMemberCardId(importMemberSelectCountReqDto.getMemberCardId());
        importMemberSearchReqDto.setCreatedDateFrom(importMemberSelectCountReqDto.getCreatedDateFrom());
        importMemberSearchReqDto.setCreatedDateTo(importMemberSelectCountReqDto.getCreatedDateTo());
        importMemberSearchReqDto.setMembercardStatus(importMemberSelectCountReqDto.getMembercardStatus());

        try {

            int totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(importMemberSelectCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setImportMemberSearchReqDto(importMemberSearchReqDto);

            if (totalCount == 0) {
                messageBean = new MessageBean(MessageId.MI0008);
            } else {
                messageBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            messageBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Imported Member searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
