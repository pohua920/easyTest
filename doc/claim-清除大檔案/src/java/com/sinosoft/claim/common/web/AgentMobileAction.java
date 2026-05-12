package com.sinosoft.claim.common.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schema.model.PrpDagent;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 理赔代理人手机号码维护
 * @Description 
 * @author 中科软
 * @date Mar 8, 2013 5:49:12 PM
 */
public class AgentMobileAction extends Struts2Action {
	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 理赔代理人表接口 */
	private PrpDagentService prpDagentService;
	/** 理赔代理人代码 */
	private String AgentCode;
	/** 理赔代理人手机 */
	private String MobileNo;
	/** 理赔代理人名称简写 */
	private String agentNameSimple;

	/**
	 * 理赔代理人手机号码维护
	 * @return
	 * @throws Exception
	 */
	public String agentMobile() throws Exception {
		HttpServletRequest request = super.getRequest();
		String forward = "";
		String editType = request.getParameter("editType");
		super.clearErrorsAndMessages();
		if ("insert".equals(editType)) {
			PrpDagent prpDagent = this.prpDagentService.findPrpDagent(AgentCode);
			if (prpDagent == null) {
				throw new UserException(1, 3, "理賠代理人手機維護", "代理人信息不存在");
			}
			prpDagent.setAgentCode(AgentCode);
			prpDagent.setMobileNo(DataUtils.nullToEmpty(MobileNo));
			prpDagent.setAgentNameSimple(DataUtils.nullToEmpty(agentNameSimple));
			this.prpDagentService.update(prpDagent);
			super.addActionMessage(super.getText("agent.save.success"));
			return SUCCESS;
		} else if ("select".equals(editType)) {
			try {
				if (pageNo == 0) {
					pageNo = 1;
				}
				if (pageSize == 0) {
					pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
				}
				String conditions = "(MobileNo is not null or MobileNo<>'') ";
				if (!"".equals(DataUtils.nullToEmpty(AgentCode))) {
					conditions += "AND AgentCode= '" + AgentCode + "' ORDER BY AgentCode";
				} else {
					conditions += "ORDER BY AgentCode";
				}
				Page page = this.prpDagentService.findPrpDagent(conditions, pageNo, pageSize);
				this.writeJSONData(page, "agentCode", "agentName", "mobileNo");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return NONE;
		} else if ("update".equals(editType)) {
			PrpDagent prpDagent = this.prpDagentService.findPrpDagent(AgentCode);
			if (prpDagent == null) {
				throw new UserException(1, 3, "理賠代理人手機維護", "代理人信息不存在");
			}
			request.setAttribute("prpDagent", prpDagent);
			forward = editType;
		} else if ("delete".equals(editType)) {
			PrpDagent prpDagent = this.prpDagentService.findPrpDagent(AgentCode);
			if (prpDagent == null) {
				throw new UserException(1, 3, "理賠代理人手機維護", "代理人信息不存在");
			}
			prpDagent.setMobileNo("");
			this.prpDagentService.update(prpDagent);
			super.addActionMessage(super.getText("agent.delete.success"));
			return SUCCESS;
		}
		return forward;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public String getAgentCode() {
		return AgentCode;
	}

	public void setAgentCode(String agentCode) {
		AgentCode = agentCode;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public String getAgentNameSimple() {
		return agentNameSimple;
	}

	public void setAgentNameSimple(String agentNameSimple) {
		this.agentNameSimple = agentNameSimple;
	}
}
