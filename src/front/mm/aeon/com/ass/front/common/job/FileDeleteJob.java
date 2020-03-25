/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.job;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import mm.aeon.com.ass.front.common.ProjectPath;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class FileDeleteJob extends QuartzJobBean {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    /**
     * This method is used to delete file under temp folder every 12 pm.
     */
    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

        try {
            applicationLogger.log("Delete temp file process started.", LogLevel.INFO);
            String deletePath = ProjectPath.dirPath + "/temp";
            FileUtils.cleanDirectory(new File(deletePath));
        } catch (Exception e) {
            logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
        }
        applicationLogger.log("Delete temp file process finished.", LogLevel.INFO);

    }
}
