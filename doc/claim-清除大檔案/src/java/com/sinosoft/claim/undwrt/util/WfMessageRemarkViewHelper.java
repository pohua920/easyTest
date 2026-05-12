/*
 * @(#)WfMessageRemarkViewHelper.java	Feb 20, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.undwrt.bl.facade.BLWfMessageFacade;
import com.sinosoft.undwrt.dto.domain.WfMessageDto;
import com.sinosoft.undwrt.ui.control.viewhelper.WfMessageViewHelper;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class WfMessageRemarkViewHelper {
	/** 分隔字符 */
	private static String LINECR = "\r\n";

	/**
	 * 保存
	 * @param request
	 * @throws Exception
	 */
	public void save(HttpServletRequest request) throws Exception {
		WfMessageViewHelper wfMessageViewHelper = new WfMessageViewHelper();
		List<?> wfMessageDtoList = (List<?>) wfMessageViewHelper.requestToWfMessageDto(request);
		new BLWfMessageFacade().save(wfMessageDtoList);
	}

	/**
	 * 查询
	 * @param request
	 * @throws Exception
	 */
	public void query(HttpServletRequest request) throws Exception {
		String businessNo = request.getParameter("businessNo");
		String messageId = request.getParameter("messageId");
		String conditions = " messageId = '" + messageId + "' order by businessNo,serialNo";
		List<WfMessageDto> wfMessageDtoList = (List<WfMessageDto>) new BLWfMessageFacade().findByConditions(conditions);
		List<WfMessageDto> wfMessageDispList = new ArrayList<WfMessageDto>();
		int count = 0, serialNo = 0;
		String context = null, operatorName = null, operatorTime = null;
		WfMessageDto wfMessageDto = null, wfMessageDispDto = null;
		for (int i = 0; i < wfMessageDtoList.size(); i++) {
			count = count + 1;
			wfMessageDto = (WfMessageDto) wfMessageDtoList.get(i);
			if (count == 1) {
				businessNo = wfMessageDto.getBusinessNo();
				serialNo = wfMessageDto.getSerialNo();
				context = wfMessageDto.getContext();
				operatorName = wfMessageDto.getOperatorName();
				operatorTime = wfMessageDto.getOperateTime();
				continue;
			}
			if (wfMessageDto.getBusinessNo().equals(businessNo) && wfMessageDto.getSerialNo() == serialNo) {
				context = context + LINECR + wfMessageDto.getContext();
				if (count == wfMessageDtoList.size()) {
					wfMessageDispDto = new WfMessageDto();
					wfMessageDispDto.setBusinessNo(businessNo);
					wfMessageDispDto.setMessageID(messageId);
					wfMessageDispDto.setOperatorName(operatorName);
					wfMessageDispDto.setOperateTime(operatorTime);
					wfMessageDispDto.setContext(context);
					wfMessageDispList.add(wfMessageDispDto);
				}
			} else {
				// 不等则将将上一条信息放入显示结果,並记录当前记录信息
				wfMessageDispDto = new WfMessageDto();
				wfMessageDispDto.setBusinessNo(businessNo);
				wfMessageDispDto.setMessageID(messageId);
				wfMessageDispDto.setOperatorName(operatorName);
				wfMessageDispDto.setOperateTime(operatorTime);
				wfMessageDispDto.setContext(context);
				wfMessageDispList.add(wfMessageDispDto);
				businessNo = wfMessageDto.getBusinessNo();
				serialNo = wfMessageDto.getSerialNo();
				context = wfMessageDto.getContext();
				operatorName = wfMessageDto.getOperatorName();
				operatorTime = wfMessageDto.getOperateTime();
				if (count == wfMessageDtoList.size()) {
					wfMessageDispDto = new WfMessageDto();
					wfMessageDispDto.setBusinessNo(businessNo);
					wfMessageDispDto.setMessageID(messageId);
					wfMessageDispDto.setOperatorName(operatorName);
					wfMessageDispDto.setOperateTime(operatorTime);
					wfMessageDispDto.setContext(context);
					wfMessageDispList.add(wfMessageDispDto);
				}
			}
		}
		if (count == 1) {
			wfMessageDispDto = new WfMessageDto();
			wfMessageDispDto.setBusinessNo(businessNo);
			wfMessageDispDto.setMessageID(messageId);
			wfMessageDispDto.setOperatorName(operatorName);
			wfMessageDispDto.setOperateTime(operatorTime);
			wfMessageDispDto.setContext(context);
			wfMessageDispList.add(wfMessageDispDto);
		}
		// ---------------------------------------------------------------------
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String userCode = user.getUserCode();
		String userName = user.getUserName();
		String operateTime = DateTime.current().toString().substring(0, 19);
		// 生成初始数据
		wfMessageDto = new WfMessageDto();
		wfMessageDto.setBusinessNo(businessNo);
		wfMessageDto.setMessageID(messageId);
		wfMessageDto.setOperateTime(operateTime);
		wfMessageDto.setOperatorCode(userCode);
		wfMessageDto.setOperatorName(userName);
		// ---------------------------------------------------------------------
		request.setAttribute("WfMessageDto", wfMessageDto);
		request.setAttribute("WfMessageList", wfMessageDispList);
	}

}
