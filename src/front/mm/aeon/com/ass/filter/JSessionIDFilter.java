/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.jboss.seam.web.AbstractFilter;

public class JSessionIDFilter extends AbstractFilter {

    /**
     * 
     * To filter out the jsessionid.
     * <p>
     * 
     * <pre>
     * [Method Details]
     * 1. Remove the jsessionid value in the servletReponse and
     *    return the url which does not contain the jsessionid value.
     * </pre>
     * 
     * </p>
     * 
     * @param req
     *            the ServletRequest value
     * @param res
     *            the ServletResponse value
     * @param chain
     *            the FilterChain value which is used to change the url value
     * @throws IOException
     *             throw IO exception
     * @throws ServletException
     *             throw servlet exception
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest)) {
            chain.doFilter(req, res);
            return;
        }

        HttpServletResponse response = (HttpServletResponse) res;

        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(response) {
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }

            @Override
            public String encodeUrl(String url) {
                return url;
            }

            @Override
            public String encodeURL(String url) {
                return url;
            }
        };
        chain.doFilter(req, wrappedResponse);

    }
}
