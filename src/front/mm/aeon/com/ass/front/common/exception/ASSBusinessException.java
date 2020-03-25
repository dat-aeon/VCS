/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.exception;

import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;

/**
 * 
 * Exception class for SERVICE_CLOSE_ERROR.
 * 
 * 
 */
public class ASSBusinessException extends PrestoRuntimeException{
    /**
     * Default serial Id.
     */
    private static final long serialVersionUID = 4632026938558087306L;

    /**
     * 
     * Default Constructor.
     * 
     */
    public ASSBusinessException() {
        super();
    }

    /**
     * Default constructor with error message.
     * 
     * @param message
     *            the message which is desired.
     */
    public ASSBusinessException(String message) {
        super(message);
    }

    /**
     * 
     * Default constructor with Throwable object.
     * 
     * @param cause
     *            the throwable object which is desired.
     */
    public ASSBusinessException(Throwable cause) {
        super(cause);

    }

    /**
     * 
     * Default constructor with message and throwable object.
     * 
     * @param message
     *            the message which is desired.
     * @param cause
     *            the throwable object which is desired.
     * 
     */
    public ASSBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
