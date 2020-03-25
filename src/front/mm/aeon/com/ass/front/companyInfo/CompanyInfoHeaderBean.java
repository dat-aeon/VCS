/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.companyInfo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class CompanyInfoHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 42293481942974157L;

    private String userName;

    private String userLoginId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    /*private ArrayList<SelectItem> statusList;

    private String validStatus;*/

    

}
