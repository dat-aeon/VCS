/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.util;

public class ReflectionUtil {
    
    public static Object getValue(Object object, String fieldName) {
        try {
            String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            return object.getClass().getMethod(getterName).invoke(object);
        } catch (Exception e) {
            return null;
        }
    }

}
