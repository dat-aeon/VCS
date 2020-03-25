/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.sessions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.menu.MenuModel;

import mm.aeon.com.ass.front.common.exception.ASSFrontException;

/**
 * PMWUserInfo Session Bean. In this, login user information, menu tree list are stored.
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * <p>
 * 
 */
@Name("userInfo")
@Scope(ScopeType.SESSION)
public class LoginUserInfo {

    private Integer id;

    private String userId;

    private String userName;

    private String userTypeName;

    private String lastOperationDateTime;

    private boolean isLoggedIn;

    private MenuModel menuModel;

    private String userTypeId;

    private String updatedBy;

    private String updatedTime;

    /**
     * Set lastOperationDateTime.
     * 
     * @param lastOperationDateTime
     *            set lastOperationDateTime
     */
    public void setLastOperationDateTime(String lastOperationDateTime) {
        this.lastOperationDateTime = lastOperationDateTime;
    }

    public String getLastOperationDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        lastOperationDateTime = dateFormat.format(cal.getTime());
        return lastOperationDateTime;
    }

    public void isSessionValid() {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.sendRedirect("/view/Login.xhtml");
        } catch (Exception e) {
            throw new ASSFrontException();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

}
