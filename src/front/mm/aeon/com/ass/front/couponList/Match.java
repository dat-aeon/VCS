/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.couponList;

import org.apache.commons.codec.binary.Base64;

public class Match {

    public static void main(String args[]) {
        
        Base64 codec = new Base64();
        byte[] temp;
        byte[] encode;
       
            encode = codec.encode("1234".getBytes()); // UI
            temp = codec.decode(encode); // db
            String plainPassword = new String(temp); // DB
            
        System.out.println("Encoded " + encode);
        System.out.println("Decoded " + plainPassword);
    }
    
}
