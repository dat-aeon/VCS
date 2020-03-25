/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/

package mm.aeon.com.ass.front.common.util;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import mm.com.dat.presto.utils.common.PropertyUtil;

/**
 * 
 * Utility class for PMW project.
 * <p>
 * 
 * <pre>
 * In this class, it includes the common methods for whole PMW project.
 * </pre>
 * 
 * </p>
 * 
 */ 
public final class MessageUtil {

    private static String MESSAGE_PROPERTIES = "configure/message-resources";
    
    private static String DISPLAY_ITEM_PROPERTIES="configure/display-item";

    /**
     * 
     * Default Constructor.
     * <p>
     * 
     * <pre>
     * </pre>
     * 
     * </p>
     * 
     * @return CommonUtil
     */
    private MessageUtil() {

    }

    /**
     * 
     * set Error Message to FacesContext
     * 
     * @return
     */
    public static void setErrorMsg(String messageId, String... params) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);

        facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMessage(messageId, params)));
    }

    /**
     * 
     * set Info Message to FacesContext
     * 
     * @return
     */
    public static void setInfoMsg( String messageId, String... params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMessage(messageId, params)));
    }

    /**
     * 
     * set warning Message to FacesContext
     * 
     * @return
     */
    public static void setWarnMsg(String messageId, String... params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "", getMessage(messageId, params)));
    }
    
    /**
     * 
     * Get Message from message-resourse.properties
     * <p>
     * 
     * <pre>
     * </pre>
     * 
     * </p>
     * 
     * @param msgKey
     *            the key of Message
     * 
     * @return the message according to the msgKey
     */
    public static String getMessage(String messageId, String... parameters) {

        String message = PropertyUtil.getProperty(MESSAGE_PROPERTIES, messageId);

        if (parameters != null) {
            String[] displayItems=parameters;
            
            for(int index=0;index<parameters.length;index++){
                String displayItem=PropertyUtil.getProperty(DISPLAY_ITEM_PROPERTIES, parameters[index]);
                if(displayItem!=null){
                    displayItems[index]=displayItem;
                }
            } 
            message = new MessageFormat(message).format(displayItems);
        }

        return message;
    }
    
    public static String getDisplayItem(String parameters){
        String displayItem=PropertyUtil.getProperty(DISPLAY_ITEM_PROPERTIES, parameters);
        return displayItem;
    }

}
