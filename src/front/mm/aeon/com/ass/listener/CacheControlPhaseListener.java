/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class CacheControlPhaseListener implements PhaseListener {

    /**
     * 
     */
    private static final long serialVersionUID = 742945817497331857L;
    private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    public void afterPhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES || event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS
                || event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
            FacesContext facesContext = event.getFacesContext();
            this.saveMessages(facesContext);
        }
    }

    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        this.saveMessages(facesContext);

        if (PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())) {
            if (!facesContext.getResponseComplete()) {
                this.restoreMessages(facesContext);
            }
        }

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        // Stronger according to blog comment below that references HTTP spec
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");

    }

    @SuppressWarnings("unchecked")
    private int saveMessages(final FacesContext facesContext) {
        List<FacesMessage> messages = new ArrayList<FacesMessage>();
        for (Iterator<FacesMessage> iter = facesContext.getMessages(null); iter.hasNext();) {
            messages.add(iter.next());
            iter.remove();
        }

        if (messages.isEmpty()) {
            return 0;
        }

        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        List<FacesMessage> existingMessages = (List<FacesMessage>) sessionMap.get(sessionToken);
        if (existingMessages != null) {
            existingMessages.addAll(messages);
        } else {
            sessionMap.put(sessionToken, messages);
        }
        return messages.size();
    }

    @SuppressWarnings("unchecked")
    private int restoreMessages(final FacesContext facesContext) {
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        List<FacesMessage> messages = (List<FacesMessage>) sessionMap.remove(sessionToken);

        if (messages == null) {
            return 0;
        }

        int restoredCount = messages.size();
        for (Object element : messages) {
            facesContext.addMessage(null, (FacesMessage) element);
        }
        return restoredCount;
    }
}
