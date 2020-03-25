/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationUsageReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CSVUtil;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ApplicationReportExportController extends AbstractController
        implements IControllerAccessor<ApplicationReportFormBean, ApplicationReportFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public ApplicationReportFormBean process(ApplicationReportFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        applicationLogger.log("Report Exporting Process started.", LogLevel.INFO);
        
        MessageBean msgBean;

        try {
            
            Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
            String systemPath = ((ServletContext) context).getRealPath(VCSCommon.BACK_SLASH);
            String[] filePath = systemPath.split(CommonUtil.getFilePath());
            applicationLogger.log("export file path " + systemPath, LogLevel.INFO);
            
            systemPath = filePath[0] + "Export" + VCSCommon.BACK_SLASH + CommonUtil.getPlainCurrentDate()
            + VCSCommon.BACK_SLASH + CommonUtil.getPlainCurrentTimeStamp();
            
            File file = new File(systemPath);
            
            if (!file.exists()) {
                file.mkdirs();
            }
            
            systemPath += VCSCommon.BACK_SLASH + "ReportExport.csv";
            File csvFile = new File(systemPath);
            
            FileWriter writer = new FileWriter(csvFile);

            if (formBean.getLineBeans() != null & formBean.getLineBeans().size() > 0) {

                CSVUtil.writeLine(writer,
                        Arrays.asList(VCSCommon.CUSTOMENO, VCSCommon.CUSTOMENAME, VCSCommon.OSTYPE,
                                VCSCommon.REGISTERTIME, VCSCommon.LASTUSINGTIME, VCSCommon.AVGNOOFLOGIN,
                                VCSCommon.TOLNOOFUSAGE));
                
                
                for(ApplicationReportListLineBean data : formBean.getLineBeans()) {
                    
                    List<String> list = new ArrayList<>();
                    list.add(data.getCustomer_no());
                    list.add(data.getName());
                    list.add(data.getOs_type());
                    list.add(String.valueOf(data.getStartTime()));
                    list.add(String.valueOf(data.getLatestUsingTime()));
                    list.add(String.valueOf(data.getAvgOfLogPerDay()));
                    list.add(String.valueOf(data.getNoOfUsgTol()));
                    
                    CSVUtil.writeLine(writer, list);
                }

                writer.flush();
                writer.close();
                
                
               
                msgBean = new MessageBean(MessageId.MI0013, systemPath);
                
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Report exporting finished.", LogLevel.INFO);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return formBean;
    }

}
