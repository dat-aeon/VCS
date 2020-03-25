/**************************************************************************
 * $Date: 2019-02-04$
 * $Author: Aung Ko lin$
 * $Rev:  $
 * 2019 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.storeList;

import java.util.ArrayList;
import java.util.List;
import mm.aeon.com.ass.base.dto.storeSearch.BranchSearchReqDto;
import mm.aeon.com.ass.base.dto.storeSearch.StoreSearchResDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
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

public class BranchListSearchController extends AbstractController
        implements IControllerAccessor<StoreListFormBean, StoreListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public StoreListFormBean process(StoreListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

       /* if (!VCSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Store List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }*/

        applicationLogger.log("Branch Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;
        
        BranchSearchReqDto reqDto = new BranchSearchReqDto(); 
        reqDto.setStoreId(formBean.getSearchHeaderBean().getStoreId());
        
        try {
            List<StoreSearchResDto> resStoreList =
                    (List<StoreSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<StoreListLineBean> lineBeanList = new ArrayList<StoreListLineBean>();

            for (StoreSearchResDto resdto : resStoreList) {
                StoreListLineBean lineBean = new StoreListLineBean();
               
                lineBean.setBranchId(resdto.getBranchId());
                lineBean.setBranchName(resdto.getBranchName());
                lineBean.setBranchAddress(resdto.getBranchAddress());
                lineBean.setBranchCode(resdto.getBranchCode());

                lineBeanList.add(lineBean);
            }

            formBean.setBranchlineBeans(lineBeanList);

            if (lineBeanList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Branch searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
