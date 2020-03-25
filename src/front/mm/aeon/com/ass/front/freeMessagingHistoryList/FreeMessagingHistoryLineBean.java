/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.freeMessagingHistoryList;

import java.io.Serializable;
import java.util.Date;

public class FreeMessagingHistoryLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8726001730381931873L;

    private Integer messageId;
    private String customerName;

    private String senderName;
    private Date sendingTime;
    private String messageDetail;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

}
