/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messagingHistoryList;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.base.dto.messagingHistorySearch.MessagingHistorySearchReqDto;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("messagingHistoryListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class MessagingHistoryListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 2963684102186598349L;

    private MessagingHistorySearchReqDto messagingHistorySearchReqDto;

    private List<MessagingHistoryListLineBean> messagingHistoryListLineBeanList;

    private LazyDataModel<MessagingHistoryListLineBean> messagingHistoryListLineBeanLazyModel;

    private MessagingHistoryListHeaderBean messagingHistoryListHeaderBean;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private int pageFirst;

    private int totalCount;

    @Begin(nested = true)
    public void init() {
        getMessageContainer().clearAllMessages(true);
        messagingHistoryListHeaderBean = new MessagingHistoryListHeaderBean();
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        doReload = new Boolean(false);
        messagingHistoryListLineBeanLazyModel = null;

        if (!getMessageContainer().checkMessage(MessageType.ERROR) && totalCount != 0) {
            messagingHistoryListLineBeanLazyModel =
                    new MessagingHistoryListPaginationController(totalCount, messagingHistorySearchReqDto);
        }

        return LinkTarget.OK;
    }

    public void reset() {
        messagingHistoryListHeaderBean = new MessagingHistoryListHeaderBean();
    }

    public List<MessagingHistoryListLineBean> getMessagingHistoryListLineBeanList() {
        return messagingHistoryListLineBeanList;
    }

    public void setMessagingHistoryListLineBeanList(
            List<MessagingHistoryListLineBean> messagingHistoryListLineBeanList) {
        this.messagingHistoryListLineBeanList = messagingHistoryListLineBeanList;
    }

    public LazyDataModel<MessagingHistoryListLineBean> getMessagingHistoryListLineBeanLazyModel() {
        return messagingHistoryListLineBeanLazyModel;
    }

    public void setMessagingHistoryListLineBeanLazyModel(
            LazyDataModel<MessagingHistoryListLineBean> messagingHistoryListLineBeanLazyModel) {
        this.messagingHistoryListLineBeanLazyModel = messagingHistoryListLineBeanLazyModel;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public MessagingHistorySearchReqDto getMessagingHistorySearchReqDto() {
        return messagingHistorySearchReqDto;
    }

    public void setMessagingHistorySearchReqDto(MessagingHistorySearchReqDto messagingHistorySearchReqDto) {
        this.messagingHistorySearchReqDto = messagingHistorySearchReqDto;
    }

    public MessagingHistoryListHeaderBean getMessagingHistoryListHeaderBean() {
        return messagingHistoryListHeaderBean;
    }

    public void setMessagingHistoryListHeaderBean(MessagingHistoryListHeaderBean messagingHistoryListHeaderBean) {
        this.messagingHistoryListHeaderBean = messagingHistoryListHeaderBean;
    }

}
