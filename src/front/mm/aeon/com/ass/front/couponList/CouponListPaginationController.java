/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import mm.aeon.com.ass.base.dto.couponSearch.CouponSearchReqDto;
import mm.aeon.com.ass.base.dto.couponSearch.CouponSearchResDto;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CouponListPaginationController extends LazyDataModel<CouponListLineBean> {
    /**
     * 
     */
    private static final long serialVersionUID = -3757296483106598610L;

    private int rowCount;

    private CouponSearchReqDto couponSearchReqDto;

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public CouponListPaginationController(int rowCount, CouponSearchReqDto couponSearchReqDto) {
        this.rowCount = rowCount;
        this.couponSearchReqDto = couponSearchReqDto;
    }

    @Override
    public Object getRowKey(CouponListLineBean line) {
        return line.getCoupon_id();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public List<CouponListLineBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {

        applicationLogger.log("Coupon Search Pagination Process started.", LogLevel.INFO);
        couponSearchReqDto.setLimit(pageSize);
        couponSearchReqDto.setOffset(first);
        couponSearchReqDto.setSortField(sortField);
        couponSearchReqDto.setSortOrder(sortOrder.toString());

        List<CouponListLineBean> resultList = new ArrayList<CouponListLineBean>();
        try {
            List<CouponSearchResDto> resDtoList =
                    (List<CouponSearchResDto>) CommonUtil.getDaoServiceInvoker().execute(couponSearchReqDto);

            for (CouponSearchResDto resDto : resDtoList) {
                CouponListLineBean data = new CouponListLineBean();

                data.setCoupon_code(resDto.getCoupon_code());
                data.setCouponName(resDto.getCoupon_name());
                data.setCouponNameMya(resDto.getCouponNameMya());
                data.setStoreName(resDto.getStore_name());
                data.setCoupon_amount(resDto.getCoupon_amount());
                data.setCoupon_discount(resDto.getDiscount_percent());
                data.setValidStatus(resDto.getDel_flag());
                data.setCouponDescription(resDto.getCoupon_desc());
                data.setCouponDescriptionMya(resDto.getCouponDescriptionMya());
                data.setCouponStrDate(resDto.getStartDate());
                data.setCouponExpDate(resDto.getExpireDate());
                data.setUnuse_image_path(resDto.getUnuse_image_path());
                data.setUse_image_path(resDto.getUse_image_path());
                data.setUploadedImageFilePath(resDto.getUnuse_image_path());
                data.setCid(resDto.getCid());
                data.setDiscount_unit(resDto.getDiscount_unit());
                data.setTotal_no(resDto.getTotal_no());
                data.setCupon_no_per_cust(resDto.getCupon_no_per_cust());
                data.setSpecial_event(resDto.getSpecial_event());
                data.setCouponSpEventMya(resDto.getCouponSpEventMya());

                resultList.add(data);

            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                applicationLogger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        applicationLogger.log("Coupon Search Pagination Process finished.", LogLevel.INFO);
        return resultList;

    }

}
