/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class EndDate {

    private static Date endDate = new Date("2019-02-10 16:02:05.043");
    
    private static Date startDate = new Date("2019-02-10 14:13:46.32");
    
    public static void main(String args[]) {
        if(endDate.compareTo(startDate) > 0)
          System.out.println(atEndOfDay(addDays(startDate, 1)));
    }
    
    public static Date atEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }
    
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
        
       // Timestamp ts=new Timestamp(System.currentTimeMillis());  
        //Date date1=new Date(ts.getTime());  
        //System.out.println(date1);
    }
    
}
