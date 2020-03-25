/**************************************************************************
 * $Date: 2018-09-05$
 * $Author: Arjun $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.importMemberList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.importMemberSearch.ImportMemberSearchReqDto;
import mm.aeon.com.ass.base.dto.importMemberSearch.ImportMemberSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ImportMemberListPaginationController extends LazyDataModel<ImportMemberListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -8785598350999473739L;

    private int rowCount;

    private ImportMemberSearchReqDto importMemberSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public ImportMemberListPaginationController(int rowCount, ImportMemberSearchReqDto importMemberSearchReqDto) {
        this.rowCount = rowCount;
        this.importMemberSearchReqDto = importMemberSearchReqDto;
    }

    @Override
    public Object getRowKey(ImportMemberListLineBean line) {
        return line.getCustomerNo();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<ImportMemberListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Import Member Search Pagination Process started.", LogLevel.INFO);
        importMemberSearchReqDto.setLimit(pageSize);
        importMemberSearchReqDto.setOffset(first);
        importMemberSearchReqDto.setSortField(sortField);
        importMemberSearchReqDto.setSortOrder(sortOrder.toString());

        List<ImportMemberListLineBean> resultList = new ArrayList<ImportMemberListLineBean>();

        try {

            List<ImportMemberSearchResDto> resDtoList = (List<ImportMemberSearchResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(importMemberSearchReqDto);

            ImportMemberListLineBean lineBean;
            DecimalFormat df = new DecimalFormat(".##");
            String strGender = null;
            for (ImportMemberSearchResDto resDto : resDtoList) {
                lineBean = new ImportMemberListLineBean();

                lineBean.setCompanyName(resDto.getCompanyName());
                if (resDto.getGender() == 1) {
                    strGender = "Male";
                } else {
                    strGender = "Female";
                }
                lineBean.setImportCustomerId(resDto.getImportCustomerId());
                lineBean.setCustomerNo(resDto.getCustomerNo());
                lineBean.setPhoneNo(resDto.getPhoneNo());
                lineBean.setMembercardId(resDto.getMembercardId());
                lineBean.setName(resDto.getName());
                lineBean.setSalary(resDto.getSalary());
                lineBean.setStrSalary(df.format(resDto.getSalary()));
                lineBean.setDob(resDto.getDob());
                lineBean.setGender(strGender);
                lineBean.setCompanyName(resDto.getCompanyName());
                lineBean.setNrcNo(resDto.getNrcNo());
                lineBean.setTownship(resDto.getTownship());
                lineBean.setCreatedDate(resDto.getCreatedDate());
                lineBean.setMembercardStatus(resDto.getMembercardStatus());
                if (lineBean.getMembercardStatus() != null && lineBean.getMembercardStatus() == 0) {
                    lineBean.setMembercardStatusStr("Active");
                }
                if (lineBean.getMembercardStatus() != null && lineBean.getMembercardStatus() > 0) {
                    lineBean.setMembercardStatusStr("Inactive");
                }

                resultList.add(lineBean);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Import Member Search Pagination Process finished.", LogLevel.INFO);
        return resultList;
    }

}
