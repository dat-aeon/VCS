/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.importMemberSearch.ImportCustomerAgreementReqDto;
import mm.aeon.com.ass.base.dto.importMemberSearch.ImportCustomerAgreementResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
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

public class ImportCustomerAgreementListController extends AbstractController
        implements IControllerAccessor<ImportMemberListFormBean, ImportMemberListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ImportMemberListFormBean process(ImportMemberListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Import customer agreement list searching process started.", LogLevel.INFO);

        MessageBean msgBean;

        ImportCustomerAgreementReqDto reqDto = new ImportCustomerAgreementReqDto();
        reqDto.setImportCustomerId(formBean.getImportCustomerId());

        try {
            List<ImportCustomerAgreementResDto> resList =
                    (List<ImportCustomerAgreementResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<ImportCustomerAgreementListLineBean> lineBeanList =
                    new ArrayList<ImportCustomerAgreementListLineBean>();

            for (ImportCustomerAgreementResDto resdto : resList) {
                ImportCustomerAgreementListLineBean lineBean = new ImportCustomerAgreementListLineBean();

                lineBean.setAgreementNo(resdto.getAgreementNo());
                lineBean.setCustAgreementId(resdto.getCustAgreementId());
                lineBean.setFinancialAmt(resdto.getFinancialAmt());
                lineBean.setFinancialStatus(resdto.getFinancialStatus());
                lineBean.setFinancialTerm(resdto.getFinancialTerm());
                lineBean.setQrShow(resdto.getQrShow());
                lineBeanList.add(lineBean);
            }

            formBean.setImportCustomerAgreementListLineBeanList(lineBeanList);

            if (lineBeanList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Import customer agreement list searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
