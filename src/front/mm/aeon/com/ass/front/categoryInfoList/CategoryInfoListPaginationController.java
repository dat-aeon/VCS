/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.categoryInfoSearch.CategoryInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.categoryInfoSearch.CategoryInfoSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CategoryInfoListPaginationController extends LazyDataModel<CategoryInfoListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = 2895710328228440321L;

    private int rowCount;

    private CategoryInfoSearchReqDto CategoryInfoSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public CategoryInfoListPaginationController(int rowCount, CategoryInfoSearchReqDto CategoryInfoSearchReqDto) {
        this.rowCount = rowCount;
        this.CategoryInfoSearchReqDto = CategoryInfoSearchReqDto;
    }

    @Override
    public Object getRowKey(CategoryInfoListLineBean categoryInfoListLineBean) {
        return categoryInfoListLineBean.getCategoryId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<CategoryInfoListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Category info search pagination process started.", LogLevel.INFO);
        CategoryInfoSearchReqDto.setLimit(pageSize);
        CategoryInfoSearchReqDto.setOffset(first);
        CategoryInfoSearchReqDto.setSortField(sortField);
        CategoryInfoSearchReqDto.setSortOrder(sortOrder.toString());

        List<CategoryInfoListLineBean> resultList = new ArrayList<CategoryInfoListLineBean>();
        try {
            List<CategoryInfoSearchResDto> resDtoList = (List<CategoryInfoSearchResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(CategoryInfoSearchReqDto);

            for (CategoryInfoSearchResDto resDto : resDtoList) {
                CategoryInfoListLineBean data = new CategoryInfoListLineBean();

                data.setCategoryId(resDto.getCategoryId());
                data.setCategoryEng(resDto.getCategoryEng());
                data.setCategoryMyn(resDto.getCategoryMyn());
                data.setDescription(resDto.getDescription());
                data.setDelFlag(resDto.getDelFlag());
                data.setCreatedBy(resDto.getCreatedBy());
                data.setUpdatedBy(resDto.getUpdatedBy());
                data.setCreatedTime(resDto.getCreatedTime());
                data.setUpdatedTime(resDto.getUpdatedTime());

                resultList.add(data);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("Category info search pagination process finished.", LogLevel.INFO);
        return resultList;

    }
}
