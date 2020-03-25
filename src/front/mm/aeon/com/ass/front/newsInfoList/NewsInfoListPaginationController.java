/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.newsInfoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.newsInfoSearch.NewsInfoSearchReqDto;
import mm.aeon.com.ass.base.dto.newsInfoSearch.NewsInfoSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class NewsInfoListPaginationController extends LazyDataModel<NewsInfoListLineBean> {

    /**
     * 
     */
    private static final long serialVersionUID = 2895710328228440321L;

    private int rowCount;

    private NewsInfoSearchReqDto newsInfoSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public NewsInfoListPaginationController(int rowCount, NewsInfoSearchReqDto newsInfoSearchReqDto) {
        this.rowCount = rowCount;
        this.newsInfoSearchReqDto = newsInfoSearchReqDto;
    }

    @Override
    public Object getRowKey(NewsInfoListLineBean newsInfoListLineBean) {
        return newsInfoListLineBean.getNewsInfoId();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<NewsInfoListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("News info search pagination process started.", LogLevel.INFO);
        newsInfoSearchReqDto.setLimit(pageSize);
        newsInfoSearchReqDto.setOffset(first);
        newsInfoSearchReqDto.setSortField(sortField);
        newsInfoSearchReqDto.setSortOrder(sortOrder.toString());

        List<NewsInfoListLineBean> resultList = new ArrayList<NewsInfoListLineBean>();
        try {
            List<NewsInfoSearchResDto> resDtoList =
                    (List<NewsInfoSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(newsInfoSearchReqDto);

            for (NewsInfoSearchResDto resDto : resDtoList) {
                NewsInfoListLineBean data = new NewsInfoListLineBean();

                data.setNewsInfoId(resDto.getNewsInfoId());
                data.setTitleEng(resDto.getTitleEng());
                data.setTitleMyn(resDto.getTitleMyn());
                data.setContentEng(resDto.getContentEng());
                data.setContentMyn(resDto.getContentMyn());
                data.setImagePath(resDto.getImagePath());
                data.setLatitude(resDto.getLatitude());
                data.setLongitude(resDto.getLongitude());
                data.setPublishedFromDate(resDto.getPublishedFromDate());
                data.setPublishedToDate(resDto.getPublishedToDate());
                data.setNewsUrl(resDto.getNewsUrl());

                resultList.add(data);

            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("News info search pagination process finished.", LogLevel.INFO);
        return resultList;

    }
}
