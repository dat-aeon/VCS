/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Swe Hsu Mon $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.changePassword;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * ChangePasswordReqDto Class.
 * <p>
 * 
 * <pre>
 *      ChangePasswordReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "Password")
public class ChangePasswordReqDto implements IReqDto {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -8681499932673487716L;

    private String password;

    private String staffId;
    
    private String updatedBy;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.UPDATE;
    }

}
