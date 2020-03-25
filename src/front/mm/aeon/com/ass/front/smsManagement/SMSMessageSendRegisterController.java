/**************************************************************************
 * $Date: 2018-11-26$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.smsManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mm.aeon.com.ass.base.service.smsRegisterService.SMSRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.smsRegisterService.SMSRegisterServiceResBean;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.JsonHeaderBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.constants.VCSCommon;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;

public class SMSMessageSendRegisterController extends AbstractASSController
        implements IControllerAccessor<SMSMessageSendFormBean, SMSMessageSendFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public SMSMessageSendFormBean process(SMSMessageSendFormBean formBean) {

        applicationLogger.log("SMS Message Send Process started.", LogLevel.INFO);
        formBean.getMessageContainer().clearAllMessages(true);
        
        try {

            if (isValidData(formBean)) {

                JsonHeaderBean jsonHeaderBean = connectURL(formBean, formBean.getHeaderBean());

                if (jsonHeaderBean != null) {

                    SMSRegisterServiceReqBean reqBean = new SMSRegisterServiceReqBean();

                    reqBean.setCategory(formBean.getHeaderBean().getCategory());
                    reqBean.setCreatedBy(CommonUtil.getLoginUserInfo().getUserName());
                    reqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                    reqBean.setCustomerId(formBean.getHeaderBean().getCustomerId());
                    reqBean.setDelFlag(0);
                    reqBean.setMessageContent(formBean.getHeaderBean().getMessageContent());
                    reqBean.setSendTime(CommonUtil.getCurrentTimeStamp());
                    reqBean.setUpdatedBy(CommonUtil.getLoginUserInfo().getUserName());
                    reqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                    this.getServiceInvoker().addRequest(reqBean);
                    ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                    SMSRegisterServiceResBean resBean = responseMessage.getMessageBean(0);
                    if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
                        formBean.getMessageContainer().clearAllMessages(true);
                        MessageBean msgBean = new MessageBean(MessageId.MI0014);
                        msgBean.setMessageType(MessageType.INFO);
                        formBean.getMessageContainer().addMessage(msgBean);
                        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                        return formBean;
                    }

                }
            }

            formBean.getMessageContainer().clearAllMessages(true);
            MessageBean msgBean = new MessageBean(MessageId.ME1027);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);

        } catch (Exception e) {

            logger.log(e.getCause().getMessage(), e, LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getCause());

        }
        applicationLogger.log("SMS Message Send Process finished.", LogLevel.INFO);
        return formBean;

    }

    private boolean isValidData(SMSMessageSendFormBean formBean) {
        boolean isValid = true;
        String message = formBean.getHeaderBean().getMessageContent();

        if (message == null) {
            MessageBean msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.MESSAGE));
            msgBean.addColumnId(DisplayItem.MESSAGE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        }

        return isValid;
    }

    private JsonHeaderBean connectURL(SMSMessageSendFormBean formBean, SMSMessageSendHeaderBean infoBean)
            throws IOException, JSONException {

        /*String url = DisplayItemBean.getDisplayItemName(CouponSms.MAIN_URL) + DisplayItemBean.getDisplayItemName(CouponSms.PHONE_URL) + DisplayItemBean.getDisplayItemName(CouponSms.PHONE_95) 
        + DisplayItemBean.getDisplayItemName(CouponSms.USER_NAME_URL) + DisplayItemBean.getDisplayItemName(CouponSms.PASSWORD_URL) + DisplayItemBean.getDisplayItemName(CouponSms.MESSAGE_URL);*/
        
        /*String url =DisplayItem.MAIN_URL + DisplayItem.PHONE_URL + DisplayItem.PHONE_95 + infoBean.getPhoneNo()
        + DisplayItem.USER_NAME_URL + DisplayItem.PASSWORD_URL + DisplayItem.UNICODE + DisplayItem.MESSAGE_URL + DisplayItem.PRJID;*/
        
        String url = DisplayItemBean.getDisplayItemName("mainUrl") + DisplayItemBean.getDisplayItemName("phoneUrl")
        + DisplayItemBean.getDisplayItemName("p95") + infoBean.getPhoneNo()
        + DisplayItemBean.getDisplayItemName("usrUrl")
        + DisplayItemBean.getDisplayItemName("pwdUrl") + DisplayItemBean.getDisplayItemName("uniCode").trim()
        + DisplayItemBean.getDisplayItemName("msgUrl") + DisplayItemBean.getDisplayItemName("prjId");

        System.out.println(url);
        

        String message = formBean.getHeaderBean().getMessageContent();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url + URLEncoder.encode(message, "UTF-8"));

        // add request header
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        JsonHeaderBean jsonHeaderBean = null;

        if (status == 200) {

            jsonHeaderBean = new JsonHeaderBean();
            String jsonString = VCSCommon.OPEN_BRACKET + result.toString() + VCSCommon.CLOSE_BRACKET;
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                // get the JSON Object
                JSONObject obj = jsonArray.getJSONObject(i);
                jsonHeaderBean.setStatus(obj.getString("status"));
                jsonHeaderBean.setIdentifier(obj.getString("identifier"));
                jsonHeaderBean.setGateway(obj.getString("gateway"));
                jsonHeaderBean.setMessageId(obj.getString("messageId"));
            }
        }
        return jsonHeaderBean;
    }

}
