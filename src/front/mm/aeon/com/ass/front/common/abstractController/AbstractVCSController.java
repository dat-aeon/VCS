/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.abstractController;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import mm.aeon.com.ass.base.dto.customerTypeList.CustomerTypeSelectListReqDto;
import mm.aeon.com.ass.base.dto.customerTypeList.CustomerTypeSelectListResDto;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

/**
 * AbstractProjectController Class.
 * <p>
 * 
 * <pre>
 * 
 * </pre>
 * 
 * </p>
 */
public abstract class AbstractVCSController extends AbstractController {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    protected ArrayList<SelectItem> getEmptyList() {

        applicationLogger.log("User Search Process started.", LogLevel.INFO);

        ArrayList<SelectItem> emptyList = new ArrayList<SelectItem>();

        SelectItem item = new SelectItem();
        item.setLabel(VCSCommon.SPACE);
        item.setValue(null);
        emptyList.add(item);

        return emptyList;
    }

    protected ArrayList<SelectItem> getCustomerTypeSelectList() {
        ArrayList<SelectItem> customerTypeSelectList = new ArrayList<>();
        CustomerTypeSelectListReqDto reqDto = new CustomerTypeSelectListReqDto();
        reqDto.setDelFlag(0);

        try {
            ArrayList<CustomerTypeSelectListResDto> resDtoList =
                    (ArrayList<CustomerTypeSelectListResDto>) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            for (CustomerTypeSelectListResDto resDto : resDtoList) {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(resDto.getCustType());
                selectItem.setValue(resDto.getCustomerTypeId());

                customerTypeSelectList.add(selectItem);
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return customerTypeSelectList;
    }

    protected ArrayList<SelectItem> getGenderSelectList() {
        ArrayList<SelectItem> genderSelectList = new ArrayList<>();

        genderSelectList.add(new SelectItem(new Integer(1), "Male"));
        genderSelectList.add(new SelectItem(new Integer(2), "Female"));

        return genderSelectList;
    }

}
