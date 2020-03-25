/**************************************************************************
 * $Date: 2018-09-05$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.userInfoSelectList.UserInfoSelectListReqDto;
import mm.aeon.com.ass.base.dto.userInfoSelectList.UserInfoSelectListResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class OperatorListPaginationController extends LazyDataModel<OperatorListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private UserInfoSelectListReqDto userInfoSelectListReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public OperatorListPaginationController(int rowCount, UserInfoSelectListReqDto userInfoSelectListReqDto) {
        this.rowCount = rowCount;
        this.userInfoSelectListReqDto = userInfoSelectListReqDto;
    }

    @Override
    public Object getRowKey(OperatorListLineBean line) {
        return line.getUserId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<OperatorListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Operator search pagination process started.", LogLevel.INFO);
        userInfoSelectListReqDto.setLimit(pageSize);
        userInfoSelectListReqDto.setOffset(first);
        userInfoSelectListReqDto.setSortField(sortField);
        userInfoSelectListReqDto.setSortOrder(sortOrder.toString());

        List<OperatorListLineBean> lineBeanList = new ArrayList<OperatorListLineBean>();
        try {
            List<UserInfoSelectListResDto> resDtoList = (List<UserInfoSelectListResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(userInfoSelectListReqDto);

            for (UserInfoSelectListResDto resDto : resDtoList) {
                OperatorListLineBean lineBean = new OperatorListLineBean();

                lineBean.setUpdatedTime(resDto.getUpdatedTime());
                lineBean.setUserId(resDto.getUserId());
                lineBean.setUserLoginId(resDto.getLoginId());
                lineBean.setUserName(resDto.getName());
                lineBean.setAllowMessagingFlag(resDto.getAllowMessagingFlag());
                lineBean.setAllowFreeMessagingFlag(resDto.getAllowFreeMessagingFlag());

                lineBeanList.add(lineBean);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("Operator search pagination process finished.", LogLevel.INFO);
        return lineBeanList;

    }

}
