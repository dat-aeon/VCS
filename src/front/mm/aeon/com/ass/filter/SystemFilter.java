/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.Filter;
import org.jboss.seam.web.AbstractFilter;

import mm.aeon.com.ass.front.common.constants.VCSCommon;


@Startup
@Scope(ScopeType.APPLICATION)
@Name("systemFilter")
@BypassInterceptors
@Filter(within = "org.jboss.seam.web.exceptionFilter")
public class SystemFilter extends AbstractFilter {

    private List<String> transitionCheckAbsolvedList;

    private static final String TARGET_EXTENSION = ".seam";

    private static final String SESSION_CREATED_KEY = "SESSION_CREATED_KEY";

    private static final String EXCHANGE_EXTENSION = ".xhtml";

    private static final String ON_LOGOUT = "ON_LOGOUT";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();

        String url = request.getRequestURI();

        if (url.indexOf(TARGET_EXTENSION) > 0) {
            for (String tokenCheckUrl : transitionCheckAbsolvedList) {
                if (url.indexOf(tokenCheckUrl) > 0) {
                    session.setAttribute(SESSION_CREATED_KEY, Boolean.TRUE);
                    session.setAttribute(VCSCommon.SESSION_TIMEOUT_KEY, null);
                    break;
                }
            }

            session = request.getSession(false);
            if (session == null) {

                session = request.getSession();
                session.setAttribute(SESSION_CREATED_KEY, Boolean.TRUE);
                session.setAttribute(VCSCommon.SESSION_TIMEOUT_KEY, Boolean.TRUE);
            }
            if (session.getAttribute(SESSION_CREATED_KEY) == null) {

                session = request.getSession();
                session.setAttribute(SESSION_CREATED_KEY, Boolean.TRUE);
                session.setAttribute(VCSCommon.SESSION_TIMEOUT_KEY, Boolean.TRUE);

            }
        }

        if (chain != null) {
            chain.doFilter(request, response);
        }

        if (isOnLogut(request)) {
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);
            session.setAttribute(SESSION_CREATED_KEY, Boolean.TRUE);
            session.setAttribute(VCSCommon.SESSION_TIMEOUT_KEY, null);

        }
    }

    public static void setOnLogout(HttpServletRequest req, boolean onLogout) {
        if (onLogout) {
            req.setAttribute(SystemFilter.ON_LOGOUT, Boolean.TRUE);
        } else {
            req.removeAttribute(SystemFilter.ON_LOGOUT);
        }
    }

    public void setTransitionCheckAbsolvedList(List<String> pTransitionCheckAbsolvedList) {
        this.transitionCheckAbsolvedList = new ArrayList<String>();

        for (String view : pTransitionCheckAbsolvedList) {
            String modView = exchangeExtension(view);
            this.transitionCheckAbsolvedList.add(modView);
        }

    }

    private String exchangeExtension(String in) {
        if (in != null && in.endsWith(EXCHANGE_EXTENSION)) {
            in = in.substring(0, in.length() - EXCHANGE_EXTENSION.length()) + TARGET_EXTENSION;
        }
        return in;
    }

    private boolean isOnLogut(HttpServletRequest req) {
        if (req.getAttribute(SystemFilter.ON_LOGOUT) != null) {
            return true;
        }
        return false;
    }
}
