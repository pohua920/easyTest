package com.sinosoft.claim.common.util;

import ins.framework.common.QueryRule;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLSendUndwrt;
import com.sinosoft.claim.schema.service.facade.PrpLSendUndwrtService;
import com.sinosoft.claim.sendUndwrt.service.facade.SendUndwrtService;
import com.sinosoft.sysframework.reference.AppConfig;

public class SendUndwrtViewHelper {
	private PrpDriskService prpDriskService;
	private PrpLSendUndwrtService prpLSendUndwrtService;
	private SendUndwrtService sendUndwrtService;

	/**
	 * 默认构造方法
	 */
	public SendUndwrtViewHelper() {
	}

	/**
	 * 节点送审信息初始化方法
	 */
	public void LoadingSendUndwrt(HttpServletRequest httpServletRequest, String businessNo, String nodeType) throws Exception {
		// 送审页面参数
		String undwrtFlag = "0";// 审核状态
		String sendUndwrtFlag = "";// 是否审核标志位
		String needUndwrtFlag = "";// 需送审标志位
		String undwrtSumPaid = "";// 审核金额限制

		String editType = httpServletRequest.getParameter("editType");
		if (!"SHOW".equals(editType) && !"COPY".equals(editType)) {
			// 险类险别字段
			String riskCode = httpServletRequest.getParameter("riskCode");
			String classCode = "";

			// 获取操作用户对象
			HttpSession session = httpServletRequest.getSession();
			UserDto user = (UserDto) session.getAttribute("user");

			// 获取工作流参数
			String logNo = httpServletRequest.getParameter("swfLogLogNo");

			// 获取赔案本节点最後一条记录
			String conditions = "";
			conditions = " businessno = '" + businessNo + "'";
			conditions += " and nodetype = '" + nodeType + "'";
			conditions += " and logno = '" + logNo + "'";
			conditions += " order by serialno desc";
			PrpLSendUndwrt prpLSendUndwrt = null;
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
			Iterator<PrpLSendUndwrt> iterator = prpLSendUndwrtService.findPrpLSendUndwrt(queryRule).iterator();
			if (iterator.hasNext()) {
				prpLSendUndwrt = (PrpLSendUndwrt) iterator.next();
			}

			// 判断送审状态是否为待审核或提交上级
			String userCode = "";
			if (prpLSendUndwrt != null) {
				undwrtFlag = prpLSendUndwrt.getUndwrtFlag();
				userCode = prpLSendUndwrt.getUndwrtCode();
			}
			if ("1".equals(undwrtFlag) || "4".equals(undwrtFlag)) {
				if (userCode.equals(user.getUserCode())) {
					if (prpLSendUndwrt.getNodeNo() == 12) {
						// 送审不送到AA
						undwrtSumPaid = "999999999999";
					} else {
						// 获取该审核人员的审核金额上限
						undwrtSumPaid = sendUndwrtService.findFactorValue(ConstantCodes.MAINCOMPANYCOMCODE, prpLSendUndwrt.getNodeNo(), riskCode);
					}
					needUndwrtFlag = "Y";
					sendUndwrtFlag = "Y";
				} else {
					throw new Exception("您沒有該案件的審核權限，請聯繫調派管理員！");
				}
			} else {
				sendUndwrtFlag = "N";
				needUndwrtFlag = "Y";
				// 未送审时
				if ("0".equals(undwrtFlag) && !ConstantCodes.CLASSCODE_D.equals(ConstantCodes.carClassMap.get(riskCode))) {
					undwrtSumPaid = String.valueOf(Integer.valueOf(sendUndwrtService.findFactorValue(ConstantCodes.MAINCOMPANYCOMCODE, 5, riskCode)) / 2);
				} else {
					undwrtSumPaid = "0";
				}
				if ("0".equals(undwrtFlag) || "2".equals(undwrtFlag)) {
					// 获取理赔节点是否需送审核标志位
					needUndwrtFlag = AppConfig.get("sysconst.NODESENDUNDWRT_" + nodeType);
					if ("Y".equals(needUndwrtFlag)) {
						// 获取险类是否需送审标志位
						if (!"".equals(riskCode)) {
							classCode = prpDriskService.findPrpDrisk(riskCode).getClassCode();
							if (!"".equals(classCode)) {
								needUndwrtFlag = AppConfig.get("sysconst.SENDUNDWRT_" + classCode);
							}
						}
					}
					// 如果没有配置节点送审或险类送审标志位，默认为不需送审
					if ("".equals(needUndwrtFlag) || null == needUndwrtFlag)
						needUndwrtFlag = "N";
				}
			}
		} else {
			needUndwrtFlag = "N";
		}

		// 赋值页面参数
		httpServletRequest.setAttribute("undwrtFlag", undwrtFlag);
		httpServletRequest.setAttribute("sendUndwrtFlag", sendUndwrtFlag);
		httpServletRequest.setAttribute("needUndwrtFlag", needUndwrtFlag);
		httpServletRequest.setAttribute("undwrtSumPaid", undwrtSumPaid);
		httpServletRequest.setAttribute("nodeType", nodeType);

	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpLSendUndwrtService getPrpLSendUndwrtService() {
		return prpLSendUndwrtService;
	}

	public void setPrpLSendUndwrtService(PrpLSendUndwrtService prpLSendUndwrtService) {
		this.prpLSendUndwrtService = prpLSendUndwrtService;
	}

	public SendUndwrtService getSendUndwrtService() {
		return sendUndwrtService;
	}

	public void setSendUndwrtService(SendUndwrtService sendUndwrtService) {
		this.sendUndwrtService = sendUndwrtService;
	}

}
