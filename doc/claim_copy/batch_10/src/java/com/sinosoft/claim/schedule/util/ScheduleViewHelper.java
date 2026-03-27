package com.sinosoft.claim.schedule.util;


import ins.framework.common.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * <p>
 * Title: ScheduleViewHelper
 * </p>
 * <p>
 * Description:调度ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2004
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public abstract class ScheduleViewHelper {
	/** 调度服务 */
	private ScheduleService scheduleService;

	/**
	 * 默认构造方法
	 */
	public ScheduleViewHelper() {
	}

	/**
	 * 保存调度时调度页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return scheduleDto 调度数据传输数据结构
	 * @throws Exception
	 */
	public ScheduleDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 取得当前用户信息，写操作员信息到Dto中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");

		ScheduleDto scheduleDto = new ScheduleDto();
		/*---------------------调度主表prpLscheduleMainWF------------------------------------*/
		String prpLscheduleMainWFScheduleID = "";
		String prpLscheduleMainWFRegistNo = "";
		String prpLscheduleMainWFSurveyNo = "1";
		String prpLscheduleMainWFClaimComCode = "";
		String prpLscheduleMainWFRiskCode = "";
		String prpLscheduleMainWFPolicyNo = "";
		String prpLscheduleMainWFOperatorCode = "";
		String prpLscheduleMainWFScheduleObjectID = "";
		String prpLscheduleMainWFScheduleObjectName = "";
		String prpLscheduleMainWFScheduleType = "";
		String prpLscheduleMainWFCheckOperatorCode = "";
		String prpLscheduleMainWFCheckFlag = "4";
		String prpLscheduleMainWFCheckInfo = "";
		String prpLscheduleMainWFSaveType = "";
		String prpLscheduleMainWFCheckSite = "";
		String prpLscheduleMainWFScheduleFlag = ""; // 是否已经调度，如果调度了就是1
		String prpLscheduleMainWFNextHandlerCode = "";
		String prpLscheduleMainWFNextHandlerName = "";
		String prpLscheduleMainWFNextNodeNo = "";
		String prpLscheduleMainWFFlag = "";
//		String prpLscheduleMainWFCommiItemFlag = ""; // 查勘项目双代标志 add by liyanjie
														// 2005-12-12
		String prpLscheduleMainWFDtoCommiFlag = ""; // 案件双代标志 add by liyanjie
													// 2005-12-12

		PrpLscheduleMainWF prpLscheduleMainWF = new PrpLscheduleMainWF();

		// 加到Dto中
		prpLscheduleMainWFScheduleID = httpServletRequest.getParameter("prpLscheduleMainWFScheduleID");
		prpLscheduleMainWFRegistNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo");
		// modify by liyanjie start因为双代的关系,查勘和定损项目都可能为不同的调度中心代码,所以要单独区分
		prpLscheduleMainWFClaimComCode = httpServletRequest.getParameter("prpLscheduleMainWFCheckClaimComCode");
		// modify by liyanjie end

		prpLscheduleMainWFRiskCode = httpServletRequest.getParameter("prpLscheduleMainWFRiskCode");
		prpLscheduleMainWFPolicyNo = httpServletRequest.getParameter("prpLscheduleMainWFPolicyNo");
		prpLscheduleMainWFOperatorCode = user.getUserCode();
		prpLscheduleMainWFScheduleObjectID = httpServletRequest.getParameter("prpLscheduleMainWFScheduleObjectID");
		prpLscheduleMainWFCheckSite = httpServletRequest.getParameter("prpLscheduleMainWFCheckSite");
		prpLscheduleMainWFScheduleObjectName = httpServletRequest.getParameter("prpLscheduleMainWFScheduleObjectName");
		prpLscheduleMainWFSaveType = httpServletRequest.getParameter("saveType");
		prpLscheduleMainWFCheckInfo = httpServletRequest.getParameter("prpLscheduleMainWFCheckInfo");
		prpLscheduleMainWFScheduleFlag = httpServletRequest.getParameter("prpLscheduleMainWFScheduleFlag");
		prpLscheduleMainWFNextHandlerCode = httpServletRequest.getParameter("nextHandlerCode1");
		prpLscheduleMainWFNextHandlerName = httpServletRequest.getParameter("nextHandlerName1");
		prpLscheduleMainWFNextNodeNo = httpServletRequest.getParameter("nextNodeNo1");
		prpLscheduleMainWFScheduleType = httpServletRequest.getParameter("prpLscheduleMainWFScheduleType");
		prpLscheduleMainWFScheduleFlag = httpServletRequest.getParameter("prpLscheduleMainWFScheduleFlag");
//		prpLscheduleMainWFCommiItemFlag = httpServletRequest.getParameter("checkCommiItemFlag"); // 查勘项目双代标志
		prpLscheduleMainWFDtoCommiFlag = httpServletRequest.getParameter("prpLscheduleMainWFDtoCommiFlag"); // 案件双代标志
		// 从数据库里查询是否已经查勘操作过了，如果操作过，则不再重复更新了
		// add by lixiang start at 2005-8-17
		// 不能每次都覆盖查勘调度
		scheduleService = (ScheduleService) ServiceFactory.getService("scheduleService");
		ScheduleDto scheduleTemp = scheduleService.findByRegistNo(1,prpLscheduleMainWFRegistNo);
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		if (scheduleTemp.getPrpLscheduleMainWF() != null && scheduleTemp.getPrpLscheduleMainWF().getScheduleFlag().equals("1")) {
//			prpLscheduleMainWF = scheduleTemp.getPrpLscheduleMainWF();
			BeanUtils.copyProperties(scheduleTemp.getPrpLscheduleMainWF(),prpLscheduleMainWF);
			prpLclaimStatus = scheduleTemp.getPrpLclaimStatus();
		} else {
			prpLscheduleMainWF.getId().setScheduleID(Integer.parseInt(prpLscheduleMainWFScheduleID));
			prpLscheduleMainWF.getId().setRegistNo((String) prpLscheduleMainWFRegistNo);
			prpLscheduleMainWF.setSurveyNo(Integer.parseInt(prpLscheduleMainWFSurveyNo));
			prpLscheduleMainWF.setClaimComCode(prpLscheduleMainWFClaimComCode);
			prpLscheduleMainWF.setRiskCode(prpLscheduleMainWFRiskCode);
			prpLscheduleMainWF.setPolicyNo(prpLscheduleMainWFPolicyNo);
			prpLscheduleMainWF.setOperatorCode(prpLscheduleMainWFOperatorCode);
			prpLscheduleMainWF.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLscheduleMainWF.setInputHour(DateTime.current().getHour());
			prpLscheduleMainWF.setScheduleType(prpLscheduleMainWFScheduleType);
			prpLscheduleMainWF.setCheckOperatorCode(prpLscheduleMainWFCheckOperatorCode);
			prpLscheduleMainWF.setCheckFlag(prpLscheduleMainWFCheckFlag);
			prpLscheduleMainWF.setScheduleArea(0);
			prpLscheduleMainWF.setFlag(prpLscheduleMainWFFlag);
			prpLscheduleMainWF.setSaveType(prpLscheduleMainWFSaveType);
			prpLscheduleMainWF.setCommiFlag(prpLscheduleMainWFDtoCommiFlag); // 案件双代标志
																				// add
																				// by
																				// liyanjie
																				// 2005-12-12
			prpLscheduleMainWF.setNextNodeNo(prpLscheduleMainWFNextNodeNo);

			// 判断查勘是否被选中
			String checkSelectSend = httpServletRequest.getParameter("checkSelectSend");
			if ("1".equals(checkSelectSend)) {
				prpLscheduleMainWFScheduleFlag = "1";
			}
			prpLscheduleMainWF.setScheduleFlag(prpLscheduleMainWFScheduleFlag); // 如果查勘调度过了，就是1

			/*---------------------文本表------------------------------------*/
			// 目前还没有
			/*---------------------调度操作状态内容prpLclaimStatus------------------------------------*/

			prpLclaimStatus.setStatus("4");
			prpLclaimStatus.getId().setBusinessNo(prpLscheduleMainWF.getId().getRegistNo());
			prpLclaimStatus.setPolicyNo(prpLscheduleMainWF.getPolicyNo());
			prpLclaimStatus.getId().setNodeType("sched");

			prpLclaimStatus.setInputDate(prpLscheduleMainWF.getInputDate());
			prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLclaimStatus.setHandlerCode(user.getUserCode());
			prpLclaimStatus.setRiskCode(prpLscheduleMainWF.getRiskCode());
			// 把scheduleid暂时放在serialNo中了。。。
			// 如果先做的是三者什么的调度，那么,不做查勘调度
			if ("0".equals(checkSelectSend)) {
				prpLscheduleMainWF.setSaveType("schel"); // 只保存定损
			}

			prpLclaimStatus.getId().setSerialNo(1);
		}
		if(null==prpLscheduleMainWFScheduleObjectID||"".equals(prpLscheduleMainWFScheduleObjectID)){
			prpLscheduleMainWFScheduleObjectID = "_";
		}
		prpLscheduleMainWF.setScheduleObjectID(prpLscheduleMainWFScheduleObjectID);
		prpLscheduleMainWF.setScheduleObjectName(prpLscheduleMainWFScheduleObjectName);
		prpLscheduleMainWF.setCheckInfo(prpLscheduleMainWFCheckInfo);
		prpLscheduleMainWF.setNextHandlerCode(prpLscheduleMainWFNextHandlerCode);
		prpLscheduleMainWF.setNextHandlerName(prpLscheduleMainWFNextHandlerName);
		prpLscheduleMainWF.setCheckSite(prpLscheduleMainWFCheckSite);

		if(prpLscheduleMainWFRegistNo == null && prpLscheduleMainWFScheduleObjectID == null) {
			prpLscheduleMainWF.setSaveType("schel"); // 只保存定损
		}
		 // 设置没有查勘
		if ("NOCK".equals(prpLscheduleMainWFScheduleType)){
			prpLscheduleMainWF.setSaveType("schel"); // 只保存定损
		}
		String strCheckInfo = StringUtils.rightTrim(httpServletRequest.getParameter("prpLscheduleMainWFCheckInfo"));
		prpLscheduleMainWF.setCheckInfo(strCheckInfo);
		// 加到ArrayList中
		scheduleDto.setPrpLscheduleMainWF(prpLscheduleMainWF);
		scheduleDto.setPrpLclaimStatus(prpLclaimStatus);

		return scheduleDto;

	}

	/**
	 * 取初始化信息需要的数据的整理. 填写调度单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract ScheduleDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写调度页面及查询调度request的生成.
	 * 填写调度时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param claimDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto) throws Exception;

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

}
