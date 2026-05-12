/*
 * @(#)MessageAction.java	Jan 25, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.common.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.util.MessageViewHelper;
import com.sinosoft.claim.schema.model.PrpLmessage;
import com.sinosoft.claim.schema.service.facade.PrpLmessageService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 赔案处理记录	
 */
public class MessageAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private String prpLmessageSave;
	private String prpLmessageContext;
	private String saved;
	private PrpLmessageService prpLmessageService;
	private MessageViewHelper messageViewHelper;

	public String message() throws Exception {
		String forward = "";
		HttpServletRequest httpServletRequest = this.getRequest();

		// 用viewHelper整理界面输入
		PrpLmessage prpLmessage = messageViewHelper.viewToDto(httpServletRequest);
		// 保存留言
		if (prpLmessageSave != null && "1".equals(saved)) {
			prpLmessageService.save(prpLmessage);
			messageViewHelper.getMessage(httpServletRequest);
			forward = "result";
		} else {
			// 查看留言
			messageViewHelper.queryRelateInfoToDto(httpServletRequest);
			forward = "success";
		}
		return forward;
	}

	/**
	 * @return
	 * @throws Exception 查看留言。。。。
	 */
	public String queryMessage() throws Exception {
		String forward = "";
		HttpServletRequest httpServletRequest = this.getRequest();
		// 查看留言
		messageViewHelper.getMessage(httpServletRequest);
		forward = "result";
		return forward;
	}

	public String getPrpLmessageSave() {
		return prpLmessageSave;
	}

	public void setPrpLmessageSave(String prpLmessageSave) {
		this.prpLmessageSave = prpLmessageSave;
	}

	public String getPrpLmessageContext() {
		return prpLmessageContext;
	}

	public void setPrpLmessageContext(String prpLmessageContext) {
		this.prpLmessageContext = prpLmessageContext;
	}

	public String getSaved() {
		return saved;
	}

	public void setSaved(String saved) {
		this.saved = saved;
	}

	public PrpLmessageService getPrpLmessageService() {
		return prpLmessageService;
	}

	public void setPrpLmessageService(PrpLmessageService prpLmessageService) {
		this.prpLmessageService = prpLmessageService;
	}

	public MessageViewHelper getMessageViewHelper() {
		return messageViewHelper;
	}

	public void setMessageViewHelper(MessageViewHelper messageViewHelper) {
		this.messageViewHelper = messageViewHelper;
	}

}
