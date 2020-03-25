/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.cuponManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.UploadedFile;

import com.opencsv.CSVReader;

import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.couponList.CustomerDataBean;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class CustomerFileUploadController extends AbstractController
        implements IControllerAccessor<CouponManagementFormBean, CouponManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    public static final String PREFIX = "streamTofile";
    public static final String SUFFIX = ".tmp";
    boolean isValidHeader = false;
    File temp = null;

    @Override
    public CouponManagementFormBean process(CouponManagementFormBean formBean) {

        ArrayList<CustomerUploadBean> lineBeanList = new ArrayList<CustomerUploadBean>();

        formBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("Customer File Upload Process Stared.", LogLevel.INFO);

        formBean.setCustomerUploadBeanList(new ArrayList<CustomerUploadBean>());

        if (!isValidData(formBean)) {
            return formBean;
        }

        try {
            InputStream inputStream = null;
            MessageBean msgBean;
            UploadedFile customerListFile = formBean.getCouponManagementFileBean().getImportFile();
            inputStream = customerListFile.getInputstream();

            temp = streamTofile(inputStream);

            CSVReader reader = new CSVReader(new FileReader(temp), VCSCommon.COMMA_CHAR);
            String[] line;
            int rowIndex = 0;
            boolean isCheckHeader = false;

            while ((line = reader.readNext()) != null) {
                rowIndex++;

                CustomerUploadBean data = new CustomerUploadBean();

                if (line.length > 0) {
                    // String customer_no = null;
                    String customer_name = null;
                    int customer_id = 0;

                    if (rowIndex == 1 && (line[0].equalsIgnoreCase(VCSCommon.HEADER_CUSTOMER_ID))
                            && isCheckHeader == false) {
                        isValidHeader = validCustomerHeader(CommonUtil.getCustomerHeaderList());

                        if (isValidHeader == false) {
                            msgBean = new MessageBean(MessageId.ME1004,
                                    DisplayItemBean.getDisplayItemName(DisplayItem.FILE));
                            msgBean.setMessageType(MessageType.ERROR);
                            formBean.getMessageContainer().addMessage(msgBean);

                            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                            return formBean;
                        }
                        isCheckHeader = true;
                    } else {
                        if (rowIndex >= 2) {
                            customer_id = Integer.parseInt(line[0]);
                            customer_name = line[1];
                            // data.setCustomer_no(customer_no);
                            data.setCustomer_id(customer_id);
                            data.setCustomer_name(customer_name);

                            lineBeanList.add(data);
                        }
                    }
                }

            }

            for (CustomerDataBean customerDataBean : formBean.getCouponCustomerList()) {

                Iterator lineBeanListIterator = lineBeanList.iterator();
                while (lineBeanListIterator.hasNext()) {
                    CustomerUploadBean customerUploadBean = (CustomerUploadBean) lineBeanListIterator.next();
                    if (customerDataBean.getCustomer_id().equals(customerUploadBean.getCustomer_id())) {
                        lineBeanListIterator.remove();
                        break;
                    }
                }

            }

            formBean.setUploadedFile(customerListFile.getFileName());
            formBean.setUploadFileName(customerListFile.getFileName());

            msgBean = new MessageBean(MessageId.MI0012, "Customer List File");
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Customer List File Upload process finished.", LogLevel.INFO);

        } catch (IOException e) {
            e.printStackTrace();
        }

        formBean.getCouponManagementFileBean().setCustomerUploadBeanList(lineBeanList);
        formBean.setCustomerUploadBeanList(lineBeanList);
        return formBean;
    }

    public File streamTofile(InputStream in) throws IOException {
        File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    public static void copyFile(File source, File destination) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        FileInputStream inputStream;
        FileOutputStream outputStream;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(destination);
            inputChannel = inputStream.getChannel();
            outputChannel = outputStream.getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputChannel.close();
            outputChannel.close();
        }

    }

    private boolean isValidData(CouponManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (formBean.getCouponManagementFileBean().getImportFile() == null) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.FILE));
            msgBean.addColumnId(DisplayItem.CSV_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else if (!FilenameUtils.getExtension(formBean.getCouponManagementFileBean().getImportFile().getFileName())
                .equalsIgnoreCase(VCSCommon.CSV)) {
            msgBean = new MessageBean(MessageId.ME1020, VCSCommon.CSV);
            msgBean.addColumnId(DisplayItem.CSV_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else if (formBean.getCouponManagementFileBean().getImportFile().getSize() > CommonUtil
                .getCSVFileUploadSize()) {
            Integer fileUploadSize = CommonUtil.getCSVFileUploadSize() / 1000000;
            msgBean = new MessageBean(MessageId.ME1037, fileUploadSize.toString());
            msgBean.addColumnId(DisplayItem.CSV_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

    public boolean validCustomerHeader(List<String> headerNameList) {
        boolean valid = false;
        if (headerNameList.size() == 2)
            valid = true;
        return valid;
    }

}
