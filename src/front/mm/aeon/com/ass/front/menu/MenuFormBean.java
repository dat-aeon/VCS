/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.menu;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;

/**
 * 
 * MenuBean Class.
 * <p>
 * 
 * <pre>
 *      MenuBean Class.
 * </pre>
 * 
 * </p>
 */
@Name("mnu001FormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class MenuFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 7989511454148179966L;

    /**
     * propagate Method.
     * <p>
     * 
     * <pre>
     *      Propagate between menus.
     * </pre>
     * 
     * </p>
     * 
     * @param urlId
     *            URL ID.
     * @return String
     */
    public String propagate(String urlId) {
        return urlId;
    }

}
