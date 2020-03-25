/**************************************************************************
 * $Date: 2019-07-23$
 * $Author: Thiha Htet Zawn$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.customerUpdateHistoryList;

import java.sql.Timestamp;

import mm.aeon.com.ass.base.dto.customerUpdateHistorySearch.CustomerUpdateHistoryInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.customerUpdateHistorySearch.CustomerUpdateHistoryInfoSelectCountReqDto;
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

public class CustomerUpdateHistoryListSearchController extends AbstractController
        implements IControllerAccessor<CustomerUpdateHistoryListFormBean, CustomerUpdateHistoryListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public CustomerUpdateHistoryListFormBean process(CustomerUpdateHistoryListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        /*
         * if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
         * logger.log("Invalid URL Access.[Store List]", new InvalidScreenTransitionException(), LogLevel.ERROR); throw
         * new InvalidScreenTransitionException(); }
         */

        applicationLogger.log("Customer Update History Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        CustomerUpdateHistoryInfoSelectCountReqDto customerUpdateHistoryCountReqDto =
                new CustomerUpdateHistoryInfoSelectCountReqDto();

        if (formBean.getSearchHeaderBean().getUpdatedTimeFrom() != null) {
            customerUpdateHistoryCountReqDto
                    .setUpdatedTimeFrom(new Timestamp(formBean.getSearchHeaderBean().getUpdatedTimeFrom().getTime()));
        }

        if (formBean.getSearchHeaderBean().getUpdatedTimeTo() != null) {
            customerUpdateHistoryCountReqDto
                    .setUpdatedTimeTo(new Timestamp(formBean.getSearchHeaderBean().getUpdatedTimeTo().getTime()));
        }
        customerUpdateHistoryCountReqDto.setCustomerName(formBean.getSearchHeaderBean().getCustomerName());
        customerUpdateHistoryCountReqDto.setCustomerNo(formBean.getSearchHeaderBean().getCustomerNo());

        CustomerUpdateHistoryInfoSearchReqDto reqDto = new CustomerUpdateHistoryInfoSearchReqDto();

        if (formBean.getSearchHeaderBean().getUpdatedTimeFrom() != null) {
            reqDto.setUpdatedTimeFrom(new Timestamp(formBean.getSearchHeaderBean().getUpdatedTimeFrom().getTime()));
        }
        if (formBean.getSearchHeaderBean().getUpdatedTimeTo() != null) {
            reqDto.setUpdatedTimeTo(new Timestamp(formBean.getSearchHeaderBean().getUpdatedTimeTo().getTime()));
        }
        reqDto.setCustomerName(formBean.getSearchHeaderBean().getCustomerName());
        reqDto.setCustomerNo(formBean.getSearchHeaderBean().getCustomerNo());
        try {

            Integer totalCount = (Integer) CommonUtil.getDaoServiceInvoker().execute(customerUpdateHistoryCountReqDto);
            formBean.setTotalCount(totalCount);
            formBean.setCustomerUpdateHistoryInfoSearchReqDto(reqDto);

            if (totalCount == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(totalCount));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Customer Update History searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
