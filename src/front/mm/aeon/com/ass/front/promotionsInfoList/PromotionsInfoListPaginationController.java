/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.promotionsInfoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.promotionsInfoSearch.PromotionsInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.promotionsInfoSearch.PromotionsInfoSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class PromotionsInfoListPaginationController extends LazyDataModel<PromotionsInfoListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = 3543624519796309068L;

    private int rowCount;

    private PromotionsInfoSearchReqDto promotionsInfoSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public PromotionsInfoListPaginationController(int rowCount, PromotionsInfoSearchReqDto promotionsInfoSearchReqDto) {
        this.rowCount = rowCount;
        this.promotionsInfoSearchReqDto = promotionsInfoSearchReqDto;
    }

    @Override
    public Object getRowKey(PromotionsInfoListLineBean promotionsInfoListLineBean) {
        return promotionsInfoListLineBean.getPromotionsInfoId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<PromotionsInfoListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Promotions info search pagination process started.", LogLevel.INFO);
        promotionsInfoSearchReqDto.setLimit(pageSize);
        promotionsInfoSearchReqDto.setOffset(first);
        promotionsInfoSearchReqDto.setSortField(sortField);
        promotionsInfoSearchReqDto.setSortOrder(sortOrder.toString());

        List<PromotionsInfoListLineBean> resultList = new ArrayList<PromotionsInfoListLineBean>();
        try {
            List<PromotionsInfoSearchResDto> resDtoList = (List<PromotionsInfoSearchResDto>) CommonUtil
                    .getDaoServiceInvoker().execute(promotionsInfoSearchReqDto);

            for (PromotionsInfoSearchResDto resDto : resDtoList) {
                PromotionsInfoListLineBean data = new PromotionsInfoListLineBean();

                data.setPromotionsInfoId(resDto.getPromotionsInfoId());
                data.setTitleEng(resDto.getTitleEng());
                data.setTitleMyn(resDto.getTitleMyn());
                data.setContentEng(resDto.getContentEng());
                data.setContentMyn(resDto.getContentMyn());
                data.setImagePath(resDto.getImagePath());
                data.setLatitude(resDto.getLatitude());
                data.setLongitude(resDto.getLongitude());
                data.setPublishedFromDate(resDto.getPublishedFromDate());
                data.setPublishedToDate(resDto.getPublishedToDate());
                data.setAnnouncementUrl(resDto.getAnnouncementUrl());

                resultList.add(data);

            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("Promotion info search pagination process finished.", LogLevel.INFO);
        return resultList;

    }

}
