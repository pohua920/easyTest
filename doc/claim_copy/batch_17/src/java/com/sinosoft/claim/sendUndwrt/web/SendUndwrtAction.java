package com.sinosoft.claim.sendUndwrt.web;

import ins.framework.web.Struts2Action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.util.MessageViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLSendUndwrt;
import com.sinosoft.claim.schema.model.PrpLSendUndwrtId;
import com.sinosoft.claim.schema.model.PrpLmessage;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.service.facade.PrpLSendUndwrtService;
import com.sinosoft.claim.sendUndwrt.service.facade.SendUndwrtService;
import com.sinosoft.sysframework.common.datatype.DateTime;

/*******************************************************************************
 * 送审Action
 * @author 中科软
 */
@SuppressWarnings("serial")
public class SendUndwrtAction extends Struts2Action {
	private SendUndwrtService sendUndwrtService;
	private PrpLSendUndwrtService prpLSendUndwrtService;
	private MessageViewHelper messageViewHelper;

	public String sendUndwrt() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String actionType = httpServletRequest.getParameter("actionType");
		String forward = "";
		String businessNo = httpServletRequest.getParameter("businessNo");
		String nodeType = httpServletRequest.getParameter("nodeType");
		String riskCode = httpServletRequest.getParameter("riskcode");
		if (riskCode == null) {
			riskCode = httpServletRequest.getParameter("riskCode");
		}
		String flowID = httpServletRequest.getParameter("swfLogFlowID");
		String logNo = httpServletRequest.getParameter("swfLogLogNo");
		HttpSession session = httpServletRequest.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		String comCode = userDto.getComCode();
		PrpLSendUndwrt prpLSendUndwrt = new PrpLSendUndwrt();
		// 送审
		if (actionType.equals("Send")) {
			UtiUwLevel utiUwLevel = sendUndwrtService.findUpUwLevel(comCode, 0, riskCode);

			// 组装送审对象DTO
			prpLSendUndwrt.getId().setBusinessNo(businessNo);
			prpLSendUndwrt.setComCode(comCode);
			prpLSendUndwrt.getId().setLogNo(Integer.valueOf(logNo));
			prpLSendUndwrt.setNodeNo(utiUwLevel.getId().getNodeNo());
			prpLSendUndwrt.setNodeType(nodeType);
			prpLSendUndwrt.setOperatorCode(userDto.getUserCode());
			prpLSendUndwrt.setOperatorName(userDto.getUserName());
			prpLSendUndwrt.getId().setSerialNo(prpLSendUndwrtService.getCount("businessno = '" + businessNo + "' and logno = " + logNo) + 1);
			prpLSendUndwrt.setUndwrtCode(utiUwLevel.getId().getUserCode());
			prpLSendUndwrt.setUndwrtName(utiUwLevel.getUserName());
			prpLSendUndwrt.setUndwrtFlag("1");
			prpLSendUndwrt.setFlowId(flowID);
			prpLSendUndwrt.setInputDate(new DateTime(new Date()));
			prpLSendUndwrtService.save(prpLSendUndwrt);

			// 送审调派
			sendUndwrtService.proxy(flowID, Integer.valueOf(logNo), utiUwLevel.getId().getUserCode());

			if ("claim".equals(nodeType)) {
				userDto.setUserMessage("报案号" + businessNo + "立案送审成功！");
			} else if ("check".equals(nodeType)) {
				userDto.setUserMessage("报案号" + businessNo + "查勘送审成功！");
			} else if ("compe".equals(nodeType)) {
				userDto.setUserMessage("立案号" + businessNo + "理算送审成功！");
			} else if ("compp".equals(nodeType)) {
				userDto.setUserMessage("计算书号" + businessNo + "理算送审成功！");
			}
		}
		// 审核
		else if (actionType.equals("Undwrt")) {
			String method = httpServletRequest.getParameter("method");

			int serialNo = prpLSendUndwrtService.getCount("businessno = '" + businessNo + "' and logno = " + logNo);
			PrpLSendUndwrtId prpLSendUndwrtId = new PrpLSendUndwrtId();
			prpLSendUndwrtId.setBusinessNo(businessNo);
			prpLSendUndwrtId.setLogNo(Integer.valueOf(logNo));
			prpLSendUndwrtId.setSerialNo(serialNo);
			prpLSendUndwrt = prpLSendUndwrtService.findPrpLSendUndwrt(prpLSendUndwrtId);
			// 增加审核信息 start
			// MessageViewHelper messageViewHelper = new MessageViewHelper();
			PrpLmessage prpLmessage = messageViewHelper.viewToUndwrtDto(httpServletRequest);
			prpLSendUndwrt.setPrpLmessage(prpLmessage);
			// 增加审核信息 end
			if ("Pass".equals(method) || "NoPass".equals(method)) {
				if ("Pass".equals(method)) {
					prpLSendUndwrt.setUndwrtFlag("2");
					userDto.setUserMessage("审核【通过】，操作成功！");
				} else if ("NoPass".equals(method)) {
					prpLSendUndwrt.setUndwrtFlag("3");
					userDto.setUserMessage("审核【不通过】，操作成功！");
				}
				prpLSendUndwrtService.update(prpLSendUndwrt);
				// 调派给原操作人
				sendUndwrtService.proxy(flowID, Integer.valueOf(logNo), prpLSendUndwrt.getOperatorCode());
			} else if ("SendUp".equals(method)) {
				prpLSendUndwrt.setUndwrtFlag("4");
				prpLSendUndwrtService.update(prpLSendUndwrt);
				// 生成下一个审核信息
				PrpLSendUndwrt prpLSendUndwrtNext = new PrpLSendUndwrt();
				prpLSendUndwrtNext = prpLSendUndwrt;
				UtiUwLevel utiUwLevel = sendUndwrtService.findUpUwLevel(comCode, prpLSendUndwrt.getNodeNo(), riskCode);
				prpLSendUndwrtNext.setNodeNo(utiUwLevel.getId().getNodeNo());
				prpLSendUndwrtNext.getId().setSerialNo(prpLSendUndwrt.getId().getSerialNo() + 1);
				prpLSendUndwrtNext.setUndwrtCode(utiUwLevel.getId().getUserCode());
				prpLSendUndwrtNext.setUndwrtName(utiUwLevel.getUserName());
				prpLSendUndwrtNext.setUndwrtFlag("1");
				prpLSendUndwrtNext.setInputDate(new DateTime(new Date()));
				prpLSendUndwrtService.save(prpLSendUndwrtNext);
				// 提交上级调派
				sendUndwrtService.proxy(flowID, Integer.valueOf(logNo), utiUwLevel.getId().getUserCode());
				userDto.setUserMessage("审核任务【提交】给  " + prpLSendUndwrtNext.getUndwrtName() + "  ,操作成功！");
			} else {
				userDto.setUserMessage("审核操作成功！");
			}
		}
		forward = actionType;
		return forward;
	}

	public SendUndwrtService getSendUndwrtService() {
		return sendUndwrtService;
	}

	public void setSendUndwrtService(SendUndwrtService sendUndwrtService) {
		this.sendUndwrtService = sendUndwrtService;
	}

	public PrpLSendUndwrtService getPrpLSendUndwrtService() {
		return prpLSendUndwrtService;
	}

	public void setPrpLSendUndwrtService(PrpLSendUndwrtService prpLSendUndwrtService) {
		this.prpLSendUndwrtService = prpLSendUndwrtService;
	}

	public MessageViewHelper getMessageViewHelper() {
		return messageViewHelper;
	}

	public void setMessageViewHelper(MessageViewHelper messageViewHelper) {
		this.messageViewHelper = messageViewHelper;
	}

}
