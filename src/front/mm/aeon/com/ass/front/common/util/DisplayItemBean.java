/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.com.dat.presto.utils.common.InputChecker;
import mm.com.dat.presto.utils.common.PropertyUtil;

@Name("displayItemBean")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class DisplayItemBean {

    private static final String DISPLAY_ITEM_FILE = "display.item";

    private static final String DISPLAY_ITEM_FILE_RESOURCES = "/configure/display-item.properties";

    private static final String PROJECT_CONFIG = "configure/project-config";

    private static String displayItemProperties;

    public DisplayItemBean() {
        displayItemProperties = PropertyUtil.getProperty(PROJECT_CONFIG, DISPLAY_ITEM_FILE);

        if (InputChecker.isBlankOrNull(displayItemProperties)) {
            displayItemProperties = DISPLAY_ITEM_FILE_RESOURCES;
        }
    }

    public static String getDisplayItemName(String itemName) {
        return PropertyUtil.getProperty(displayItemProperties, itemName);
    }

}
