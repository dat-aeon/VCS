package mm.aeon.com.ass.base.dto.passwordInfoSelectForUpdate;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class PasswordInfoSelectForUpdateResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -2984883292668226332L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
