/**************************************************************************
 * $Date : 2017-01-25 $
 * $Author : Khin Yadanar Thein $
 * $Rev : $
 * Copyright (c) 2016~2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("YMDConverter")
public class YMDConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext contex, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext contex, UIComponent component, Object value) {

        return value.toString();
    }
}
