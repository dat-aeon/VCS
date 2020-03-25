/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/

package mm.aeon.com.ass.front.common.util;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.model.SelectItem;

import org.apache.commons.codec.binary.Base64;
import org.jboss.seam.contexts.Contexts;

import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.sessions.LoginUserInfo;
import mm.com.dat.presto.main.core.dao.controller.DaoServiceInvoker;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.common.ApplicationContextProvider;
import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;
import mm.com.dat.presto.utils.common.InputChecker;
import mm.com.dat.presto.utils.common.PropertyUtil;

/**
 * 
 * Utility class for Fast project.
 * <p>
 * 
 * <pre>
 * In this class, it includes the common methods for whole Fast project.
 * </pre>
 * 
 * </p>
 * 
 */
public final class CommonUtil {

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
    private CommonUtil() {

    }

    /**
     * 
     * Compare two BigDecimal values.
     * <p>
     * 
     * <pre>
     * </pre>
     * 
     * </p>
     * 
     * @param amount
     *            the value to compare
     * @param compareAmount
     *            the value to compare
     * @return the status of greater than or not
     */
    public static boolean isGreaterThan(BigDecimal amount, BigDecimal compareAmount) {

        if (amount.compareTo(compareAmount) < 1) {
            return false;
        }
        return true;
    }

    /**
     * 
     * Get current Timestamp.
     * 
     * @return current Timestamp
     */
    public static Timestamp getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        return timestamp;
    }

    /**
     * 
     * Get current Timestamp.
     * 
     * @return current Timestamp
     */
    public static String getPlainCurrentTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
        String timestamp = dateFormat.format(new Timestamp(new Date().getTime()));
        String[] time = timestamp.split(VCSCommon.SPACE);
        return time[1];
    }

    /**
     * 
     * Get Timestamp From String.
     * 
     * @return Timestamp
     */
    public static Timestamp getChangeStringToTimeStamp(String date) {

        Timestamp timestamp;
        try {
            // Long time = System.currentTimeMillis();
            date = date.replace("/", "-");
            date = date.concat(" 00:00:00");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new Timestamp(parsedDate.getTime());

        } catch (ParseException e) {
            throw new BaseException();
        }

        return timestamp;
    }

    /**
     * 
     * Get Timestamp From String.
     * 
     * @return Timestamp
     */
    public static Timestamp getChangeDMYStringToTimeStamp(String date) {

        Timestamp timestamp;
        try {
            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = output.format(input.parse(date));
            dateString = dateString.replace("/", "-");
            dateString = dateString.concat(" 00:00:00");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(dateString);
            timestamp = new Timestamp(parsedDate.getTime());

        } catch (ParseException e) {
            throw new BaseException();
        }

        return timestamp;
    }

    public static String getChangeTimestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String string = dateFormat.format(timestamp);
        return string;
    }

    /**
     * 
     * Get current Year
     * 
     * @return year
     */
    public static String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        int year;

        // if month = 2 => March
        if (now.get(Calendar.MONTH) < 3) {
            year = now.get(Calendar.YEAR) - 1;
        } else {
            year = now.get(Calendar.YEAR);
        }
        String currentYear = String.valueOf(year);

        return currentYear;
    }

    /**
     * 
     * Convert String to Double
     * 
     * @return double (after convertion)
     * 
     * @return null (error occur)
     */
    public static double convertDouble(String strValue) {

        if (!InputChecker.isBlankOrNull(strValue)) {
            Double doubleValue = Double.parseDouble(strValue);
            if (doubleValue > -1.00) {
                return doubleValue;
            }
        }
        return 0.0;
    }

    /**
     * 
     * convert String to int
     * 
     * @return int (after convertion)
     * 
     * @return null (error occur)
     */
    public static int convertInteger(String strValue) {
        int intValue = Integer.parseInt(strValue);
        if (intValue < 0) {
            return -1;
        }
        return intValue;
    }

    /**
     * 
     * check startPeriod is less than endPeriod
     * 
     * @return true (if startPeriod is less than endPeriod)
     */
    public static boolean isBeforeEndPeriod(Date startPeriod, Date endPeriod) {
        boolean isBefore = true;
        if (startPeriod.compareTo(endPeriod) <= 0) {
            isBefore = false;
        }
        return isBefore;
    }

    /**
     * 
     * get month 0 for January- 11 for December
     * 
     * @return int (index of month)
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    /**
     * 
     * get year depand on inputting date
     * 
     * @return int (index of year)
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static Date changeStringToDate(String dateStr) {
        Date date = null;
        try {
            if (dateStr != null) {
                if (dateStr.length() == 6) {
                    DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMM);
                    date = format.parse(dateStr);

                } else {
                    DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMMDD);
                    date = format.parse(dateStr);
                }
            }

        } catch (ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }
        return date;

    }

    public static Date changeYMDSlashStringToDate(String dateStr) {

        try {
            DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMMDD_SLASH);
            return format.parse(dateStr);
        } catch (

        ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }

    }

    public static Date changeYMSlashStringToDate(String dateStr) {

        try {
            DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMM_SLASH);
            return format.parse(dateStr);
        } catch (

        ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }

    }

    public static String changeDateToString(Date date) {

        if (date == null) {
            return null;
        } else {
            DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMMDD);
            return format.format(date);
        }
    }

    public static String changeDateToYMDSlashString(Date date) {

        if (date == null) {
            return null;
        } else {
            DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMMDD_SLASH);
            return format.format(date);
        }
    }

    public static String changeDateToYMSlashString(Date date) {

        if (date == null) {
            return null;
        } else {
            DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMM_SLASH);
            return format.format(date);
        }
    }

    public static Date changeDMYSlashStringToDate(String dateStr) {

        try {
            DateFormat format = new SimpleDateFormat(VCSCommon.DDMMYYYY_SLASH);
            return format.parse(dateStr);

        } catch (ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }

    }

    public static boolean isUnitPrice(String unitPrice) {
        Pattern p = Pattern.compile("^[0-9]{1,4}(\\.[0-9]*)?$");
        Matcher m = p.matcher(unitPrice);

        return m.matches();
    }

    public static boolean isNumeric(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,4}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidAmount(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,10}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidManMonth(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,6}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidBudgetTotal(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,16}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidInteger(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,2}$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidDirId(String dirId) {

        Pattern p = Pattern.compile("[a-zA-Z0-9]{7}");
        Matcher m = p.matcher(dirId);

        return m.matches();
    }

    public static boolean isValidTeamName(String dirId) {
        Pattern p = Pattern.compile("^[a-zA-Z]+\\d*[a-zA-Z]*");
        Matcher m = p.matcher(dirId);

        return m.matches();
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(VCSCommon.YYYYMMDD_SLASH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getPlainCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(VCSCommon.YYYYMMDD);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getFormattedNumberfor2Digit(String number) {
        return String.format("%.2f", Double.parseDouble(number));
    }

    public static String getFormattedNumberFor4Digit(String number) {
        return String.format("%.4f", Double.parseDouble(number));
    }

    public static String getFormattedNumber(String number, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        String numberAsString = decimalFormat.format(Double.parseDouble(number));

        return numberAsString;
    }

    /**
     * 
     * Get current Month
     * 
     * @return month(int)
     */
    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1;

        return currentMonth;
    }

    public static DaoServiceInvoker getDaoServiceInvoker() {
        DaoServiceInvoker daoServiceInvoker =
                (DaoServiceInvoker) ApplicationContextProvider.getBean("daoServiceInvoker");
        return daoServiceInvoker;
    }

    public static String getLoginUserName() {
        LoginUserInfo pmwUserInfo = (LoginUserInfo) Contexts.getSessionContext().get("userInfo");
        return pmwUserInfo.getUserId().trim();
    }

    public static int getLoginUserId() {
        LoginUserInfo pmwUserInfo = (LoginUserInfo) Contexts.getSessionContext().get("userInfo");
        return pmwUserInfo.getId();
    }

    public static String getLoginUserTypeId() {
        LoginUserInfo pmwUserInfo = (LoginUserInfo) Contexts.getSessionContext().get("userInfo");
        return pmwUserInfo.getUserTypeId();
    }

    public static LoginUserInfo getLoginUserInfo() {
        LoginUserInfo pmwUserInfo = (LoginUserInfo) Contexts.getSessionContext().get("userInfo");
        return pmwUserInfo;
    }

    public static String[] separateString(String param) {
        String[] output = param.split(VCSCommon.COMMA);
        return output;
    }

    /**
     * 
     * check firstDate is less than secondDate
     * 
     * @return firstDate (if firstDate is less than secondDate else secondDate)
     */
    public static String getStartDate(String firstDate, String secondDate) {
        String startDate = null;
        if (firstDate == null) {
            startDate = secondDate;
        } else {
            if (changeStringToDate(firstDate).compareTo(changeStringToDate(secondDate)) < 0) {
                startDate = firstDate;
            } else {
                startDate = secondDate;
            }
        }
        return startDate;
    }

    public static String increaseYear(String year) {
        int currentYear = Integer.parseInt(year) + 1;
        return Integer.toString(currentYear);
    }

    public static Date getStartDate(String dateStr) {
        Date date = null;
        try {
            if (dateStr != null) {
                dateStr = dateStr + "/01";
                DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMMDD_SLASH);
                date = format.parse(dateStr);

            }
        } catch (ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }
        return date;
    }

    public static Date getEndDate(String dateStr) {
        Date date = null;
        try {
            if (dateStr != null) {
                Calendar calendar = Calendar.getInstance();
                // passing month-1 because 0-->jan, 1-->feb... 11-->dec
                calendar.set(Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        1);
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                DateFormat format = new SimpleDateFormat(VCSCommon.YYYYMMDD_SLASH);
                date = format.parse(format.format(calendar.getTime()));

            }
        } catch (ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }
        return date;
    }

    public static int getFileUploadSize() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String FILE_UPLOAD_SIZE = "file-upload-size";

        int fileUploadSize = Integer.parseInt(PropertyUtil.getProperty(PROJECT_CONFIG, FILE_UPLOAD_SIZE));
        return fileUploadSize;
    }

    public static String getUploadImageBaseFilePath() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String IMAGE_BASE_FILE_PATH = "imageBaseFilePath";

        String imageBaseFilePath = PropertyUtil.getProperty(PROJECT_CONFIG, IMAGE_BASE_FILE_PATH);
        return imageBaseFilePath;
    }

    public static String getPLMessagingImageBaseFilePath() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String IMAGE_BASE_FILE_PATH = "plMessagingImageFolder";

        String plMessagingImageBaseFilePath = PropertyUtil.getProperty(PROJECT_CONFIG, IMAGE_BASE_FILE_PATH);
        return plMessagingImageBaseFilePath;
    }

    public static String getProfileUploadImageFolder() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String PROFILE_IMAGE_FOLDER = "profileImageFolder";

        String profileImageFolder = PropertyUtil.getProperty(PROJECT_CONFIG, PROFILE_IMAGE_FOLDER);
        return profileImageFolder;
    }

    public static String getCouponUploadImageFolder() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String COUPON_IMAGE_FOLDER = "couponImageFolder";

        String couponImageFolder = PropertyUtil.getProperty(PROJECT_CONFIG, COUPON_IMAGE_FOLDER);
        return couponImageFolder;
    }

    public static String getNewsInfoUploadImageFolder() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String NEWS_IMAGE_FOLDER = "newsImageFolder";

        String newsImageFolder = PropertyUtil.getProperty(PROJECT_CONFIG, NEWS_IMAGE_FOLDER);
        return newsImageFolder;
    }

    public static String getPromotionsInfoUploadImageFolder() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String PROMOTION_IMAGE_FOLDER = "promotionImageFolder";

        String promotionImageFolder = PropertyUtil.getProperty(PROJECT_CONFIG, PROMOTION_IMAGE_FOLDER);
        return promotionImageFolder;
    }

    public static int getCSVFileUploadSize() {
        final String PROJECT_CONFIG = "configure/project-config";
        final String FILE_UPLOAD_SIZE = "csv-file-upload-size";

        int fileUploadSize = Integer.parseInt(PropertyUtil.getProperty(PROJECT_CONFIG, FILE_UPLOAD_SIZE));
        return fileUploadSize;
    }

    public static String getFilePath() {
        final String projectConfig = "configure/project-config";
        final String filePathAttr = "server-file-path";

        String filePath = PropertyUtil.getProperty(projectConfig, filePathAttr);
        return filePath;
    }

    public static boolean hasDuplicateYear(String year, ArrayList<String> yearList) {
        int count = 0;
        if (yearList != null && yearList.size() > 1) {
            for (String tempYear : yearList) {
                if (tempYear == year) {
                    count++;
                }
            }
            if (count > 1) {
                return true;
            }
        }
        return false;
    }

    public static String getTotalManMonth(ArrayList<Double> totalPlanList) {
        double totalManMonth = 0.00;
        for (int i = 0; i < totalPlanList.size(); i++) {
            totalManMonth += totalPlanList.get(i);
        }
        return Double.toString(totalManMonth);

    }

    public static String removeBackSlash(String date) {
        if (date.matches("\\d{4}/\\d{2}/\\d{2}")) {
            date = date.replace(VCSCommon.BACK_SLASH, VCSCommon.BLANK);
        }
        return date;
    }

    /**
     * 
     * check two dates difference
     * 
     * @return days (difference days)
     */
    public static long getDifferenceDays(String sendDate, String currentDate) {
        long diffDays = 0;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date send = format.parse(sendDate);
            Date current = format.parse(currentDate);

            long diffTime = current.getTime() - send.getTime();
            diffDays = diffTime / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {
            throw new PrestoRuntimeException(e.getMessage());
        }
        return diffDays;
    }

    public static boolean isValidPhoneNo(String inputStr) {
        Pattern p = Pattern.compile("[09][0-9]{7,10}");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidDMYDate(String dateString) {
        Pattern p = Pattern.compile("[0-9]{1,2}(/|-)[0-9]{1,2}(/|-)[0-9]{4}");
        Matcher m = p.matcher(dateString);

        return m.matches();
    }

    public static boolean isValidAgreementNo(String agreementNo) {
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{1}-[0-9]{10}-[0-9]{1}");
        Matcher m = p.matcher(agreementNo);

        return m.matches();
    }

    public static Map<Integer, String> getLocationMap() {
        Map<Integer, String> locationMap = new HashMap<>();
        locationMap.put(0, VCSCommon.YANGON);
        locationMap.put(1, VCSCommon.MANDALAY);

        return locationMap;
    }

    public static String changeLocation(int location) {
        String result = null;
        switch (location) {
            case VCSCommon.NOT_VALID:
                result = VCSCommon.YGN_LOCATION;
                break;
            case VCSCommon.IS_VALID:
                result = VCSCommon.MDY_LOCATION;
                break;
            default:
                break;
        }
        return result;
    }

    public static String changeLocationValue(String location) {
        String result = null;
        switch (location) {
            case VCSCommon.YGN_LOCATION:
                result = VCSCommon.ZERO;
                break;
            case VCSCommon.MDY_LOCATION:
                result = VCSCommon.ONE;
                break;
            default:
                break;
        }
        return result;
    }

    public static String showRecord(int sumCount) {
        String recordCount = null;

        if (sumCount == VCSCommon.ONE_INTEGER) {
            recordCount =
                    VCSCommon.OPEN_BRACKET + String.valueOf(sumCount) + VCSCommon.CLOSE_BRACKET + VCSCommon.RECORD;
        } else if (sumCount == VCSCommon.ZERO_INTEGER) {
            recordCount = null;
        } else {
            recordCount =
                    VCSCommon.OPEN_BRACKET + String.valueOf(sumCount) + VCSCommon.CLOSE_BRACKET + VCSCommon.RECORDS;
        }
        return recordCount;
    }

    public static Date getFirstDayOfMonth() {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.DAY_OF_MONTH, 1);
        return cl.getTime();
    }

    public static ArrayList<SelectItem> getJudgementStatusList(boolean isSearch) {

        ArrayList<SelectItem> judgementStatusList = new ArrayList<SelectItem>();

        SelectItem item = new SelectItem();
        if (isSearch) {
            item = new SelectItem();
            item.setLabel(VCSCommon.BLANK);
            item.setValue(0);
            judgementStatusList.add(item);
        }

        item = new SelectItem();
        item.setLabel(VCSCommon.JD_STATUS_APPROVE);
        item.setValue(2);
        judgementStatusList.add(item);

        item = new SelectItem();
        item.setLabel(VCSCommon.JD_STATUS_REJECT);
        item.setValue(3);
        judgementStatusList.add(item);

        item = new SelectItem();
        item.setLabel(VCSCommon.JD_STATUS_CANCEL);
        item.setValue(4);
        judgementStatusList.add(item);

        return judgementStatusList;
    }

    public static List<String> getApprovedHeaderList() {
        List<String> headerList = new ArrayList<String>();
        headerList.add(VCSCommon.HEADER_NO);
        headerList.add(VCSCommon.HEADER_APPROVED_DATE);
        headerList.add(VCSCommon.HEADER_BRANCH_NAME);
        headerList.add(VCSCommon.HEADER_CUSTOMER_NAME);
        headerList.add(VCSCommon.HEADER_CUSTOMER_CODE);
        headerList.add(VCSCommon.HEADER_APPLICATION_FORM_NO);
        headerList.add(VCSCommon.HEADER_AGREEMENT_NO);
        headerList.add(VCSCommon.HEADER_TERMS);
        headerList.add(VCSCommon.HEADER_FINANCE_AMOUNT);
        headerList.add(VCSCommon.HEADER_PRODUCT);

        return headerList;
    }

    public static List<String> getRejectedHeaderList() {
        List<String> headerList = new ArrayList<String>();
        headerList.add(VCSCommon.HEADER_REJECT_NO);
        headerList.add(VCSCommon.HEADER_APPLICATION_DATE);
        headerList.add(VCSCommon.HEADER_BRANCH_NAME);
        headerList.add(VCSCommon.HEADER_CUSTOMER_NAME);
        headerList.add(VCSCommon.HEADER_CUSTOMER_CODE);
        headerList.add(VCSCommon.HEADER_TOWNSHIP);
        headerList.add(VCSCommon.HEADER_APPLICATION_NO);
        headerList.add(VCSCommon.HEADER_INTEREST_RATE);
        headerList.add(VCSCommon.HEADER_TERMS_OF_FINANCE);
        headerList.add(VCSCommon.HEADER_FINANCE_AMOUNT_MMK);
        headerList.add(VCSCommon.HEADER_INTEREST_TOTAL_MMK);
        headerList.add(VCSCommon.HEADER_REPAYMENT_TOTAL_MMK);
        headerList.add(VCSCommon.HEADER_REJECT_REASON);
        headerList.add(VCSCommon.HEADER_COMMENT);

        return headerList;
    }

    public static List<String> getCancelledHeaderList() {
        List<String> headerList = new ArrayList<String>();
        headerList.add(VCSCommon.HEADER_NO);
        headerList.add(VCSCommon.HEADER_APPLICATION_DATE);
        headerList.add(VCSCommon.HEADER_BRANCH_NAME);
        headerList.add(VCSCommon.HEADER_CUSTOMER_NAME);
        headerList.add(VCSCommon.HEADER_CUSTOMER_CODE);
        headerList.add(VCSCommon.HEADER_TOWNSHIP);
        headerList.add(VCSCommon.HEADER_APPLICATION_NO);
        headerList.add(VCSCommon.HEADER_INTEREST_RATE);
        headerList.add(VCSCommon.HEADER_TERMS_OF_FINANCE);
        headerList.add(VCSCommon.HEADER_FINANCE_AMOUNT_MMK);
        headerList.add(VCSCommon.HEADER_CANCEL_INTEREST_TOTAL_MMK);
        headerList.add(VCSCommon.HEADER_CANCEL_REPAYMENT_TOTAL_MMK);
        headerList.add(VCSCommon.HEADER_DECLINE_REASON);
        headerList.add(VCSCommon.HEADER_COMMENT);

        return headerList;
    }

    public static List<String> getCustomerHeaderList() {
        List<String> headerList = new ArrayList<String>();
        headerList.add(VCSCommon.HEADER_CUSTOMER_ID);
        headerList.add(VCSCommon.HEADER_CUSTOMER_NAME);

        return headerList;
    }

    public static String generateDigit() {
        int randomPIN = (int) (Math.random() * 90000) + 10000;
        return String.valueOf(randomPIN);
    }

    public static String decodePassword(String encodedPassword, boolean isDecode) {

        Base64 codec = new Base64();
        byte[] temp = null;
        String plainPassword = null;

        if (isDecode) {
            if (encodedPassword != null) {
                temp = codec.decode(encodedPassword.getBytes());
            }
        } else {
            temp = codec.encode(encodedPassword.getBytes());
        }
        plainPassword = new String(temp);

        return plainPassword;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static char[] getOTP() {

        String numbers = "0123456789";
        Random rndm_method = new Random();
        int len = VCSCommon.OTP_LENGTH;

        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }

    public static Date minusYearsFromToday(int years) {
        return minusYearFromDate(getCurrentTimeStamp(), years);
    }

    public static Date minusYearFromDate(Date date, int years) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, (years * -1));

        return cal.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * Get Current Time.
     */

    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Convert date into custom pattern String.
     */
    public static String formatByPattern(Date input, String pattern) {

        // Empty value check.
        if (input == null) {
            return null;
        }

        if (pattern == null || "".equals(pattern)) {
            return null;
        }

        // Convert process.
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(input);
    }

    /**
     * Get the current year, month and date in string form.
     */
    public static String getCurrentDateInString() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String currentDate = df.format(Calendar.getInstance().getTime());
        return currentDate;
    }

    /**
     * Get DateTime as String.
     */
    public static String getDateTimeString(Date date) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String currentDate = df.format(date);
        return currentDate;
    }

    /**
     * Get the last date of last month.
     */
    public static Date getLastMonthEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getFirstDayOfMonth(Date criDate) {
        Date firstDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(criDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        firstDate = cal.getTime();

        return firstDate;
    }

    public static Date getCurrentMonthLastDate(Date updateDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(updateDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getNextMonthFirstDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getPreviousMonthLastDayOfInputDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static boolean isPureAscii(String v) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(v);
        // or "ISO-8859-1" for ISO Latin 1
        // or StandardCharsets.US_ASCII with JDK1.7+
    }
}
