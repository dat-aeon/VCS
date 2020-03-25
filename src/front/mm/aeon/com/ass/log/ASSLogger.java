/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.log;

import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.main.log.LoggerPattern;
import mm.com.dat.presto.main.log.PrestoLogging;

public class ASSLogger {

    private PrestoLogging pmwLogger = PrestoLogging.getInstance("SERVER", new LoggerPattern());

    /**
     * Log output method.
     * 
     * @param message
     *            Message
     * @param cause
     *            The desired Throwable object
     */
    public void log(String message, Throwable cause) {
        log(message, cause, LogLevel.DEBUG);
    }

    /**
     * Log output method.
     * 
     * @param message
     *            Message
     */
    public void log(String message) {
        log(message, LogLevel.DEBUG);
    }

    /**
     * This method is to write log message with the specific LogLevel.
     * <p>
     * 
     * <pre>
     * [Method Details]
     * 1. Check the LogLevel and write the log message.
     * </pre>
     * 
     * </p>
     * 
     * @param message
     *            Message
     * @param logLevel
     *            LogLevel
     */
    public void log(String message, LogLevel logLevel) {

        switch (logLevel) {
            case DEBUG:
                pmwLogger.debug(message);
                break;
            case INFO:
                pmwLogger.info(message);
                break;
            case WARN:
                pmwLogger.warn(message);
                break;
            case ERROR:
                pmwLogger.error(message);
                break;
            case FATAL:
                pmwLogger.fatal(message);
                break;
            case TRACE:
                pmwLogger.trace(message);
                break;
            default:
                pmwLogger.debug(message);
        }
    }

    /**
     * This method write the log message with the specific level and exception.
     * <p>
     * 
     * <pre>
     * [Method Details]
     * 1. Check the LogLevel and write the log message and error info.
     * </pre>
     * 
     * </p>
     * 
     * @param message
     *            Message
     * @param cause
     *            The desired Throwable object
     * @param logLevel
     *            LogLevel
     */
    public void log(String message, Throwable cause, LogLevel logLevel) {

        switch (logLevel) {
            case DEBUG:
                pmwLogger.debug(message, cause);
                break;
            case INFO:
                pmwLogger.info(message, cause);
                break;
            case WARN:
                pmwLogger.warn(message, cause);
                break;
            case ERROR:
                pmwLogger.error(message, cause);
                break;
            case FATAL:
                pmwLogger.fatal(message, cause);
                break;
            case TRACE:
                pmwLogger.trace(message, cause);
                break;
            default:
                pmwLogger.debug(message, cause);
        }
    }

}
