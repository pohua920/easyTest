package com.sinosoft.claim.claim.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplexcludeclaimService;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * <p>
 * Title: 立案除外
 * </p>
 * <p>
 * Description: 立案除外
 * </p>
 * @author 中科软
 * @version
 */
public class ExcludeClaimViewHelper {
	/** 立案除外信息服务 */
	private PrplexcludeclaimService prplexcludeclaimService = null;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService = null;
	/** 理赔状态节点信息服务 */
	private PrpLclaimStatusService prpLclaimStatusService;

	/**
	 * 构造方法
	 */
	public ExcludeClaimViewHelper() {

	}

	/**
	 * 封装立案除外查询的条件
	 * @param request
	 * @throws Exception
	 */
	public String generateConditions(HttpServletRequest request) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String conditions = " 1=1";
		String registNo = (paramUtils.getParameter("RegistNo")).trim();
		String policyNo = (paramUtils.getParameter("PolicyNo")).trim();
		String giveComCode = (paramUtils.getParameter("InsuredName")).trim();
		String riskCode = (paramUtils.getParameter("RiskCode")).trim();
		String startReportDate = (paramUtils.getParameter("StartReportDate")).trim();
		String endReportDate = (paramUtils.getParameter("EndReportDate")).trim();
		String claimNO = (paramUtils.getParameter("ClaimNo")).trim();
		
		if (!"".equals(registNo)) {
			conditions += SqlUtils.convertString("PRPLREGIST.REGISTNO", registNo);
		}
		if (!"".equals(policyNo)) {
			conditions += SqlUtils.convertString("PRPLREGIST.POLICYNO", policyNo);
		}
		if (!"".equals(giveComCode)) {
			conditions += SqlUtils.convertString("PRPLREGIST.INSUREDNAME", giveComCode);
		}
		if (!"".equals(riskCode)) {
			conditions += SqlUtils.convertString("PRPLREGIST.RISKCODE", riskCode);
		}

		if (!"".equals(startReportDate)) {
			conditions += " AND PRPLREGIST.REPORTDATE >=TO_DATE('" + startReportDate + "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (!"".equals(endReportDate)) {
			conditions += " AND PRPLREGIST.REPORTDATE <=TO_DATE('" + endReportDate + "','yyyy-mm-dd hh24:mi:ss')";
		}
		if(!"".equals(claimNO)){
			conditions += " AND PRPLREGIST.CANCELDATE IS NULL AND PRPLREGIST.CLASSCODE not in ('"+ConstantCodes.CLASSCODE_D_A+"','"+ConstantCodes.CLASSCODE_D_B+"') AND NOT EXISTS " + " (SELECT * FROM PRPLCLAIM PRPLCLAIM WHERE PRPLCLAIM.REGISTNO = PRPLREGIST.REGISTNO AND PRPLREGIST.registNO in PRPLCLAIM.Registno)";
			return conditions;
		}
		conditions += " AND PRPLREGIST.CANCELDATE IS NULL AND PRPLREGIST.CLASSCODE not in ('"+ConstantCodes.CLASSCODE_D_A+"','"+ConstantCodes.CLASSCODE_D_B+"') AND NOT EXISTS " + " (SELECT * FROM PRPLCLAIM PRPLCLAIM WHERE PRPLCLAIM.REGISTNO = PRPLREGIST.REGISTNO)";
		return conditions;
	}

	/**
	 * 封装立案除外历史查询的条件
	 * @param request
	 * @throws Exception
	 */
	public String generateHistoryConditions(HttpServletRequest request) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String conditions = " 1=1";
		String registNo = (paramUtils.getParameter("RegistNo")).trim();
		String policyNo = (paramUtils.getParameter("PolicyNo")).trim();
		String giveComCode = (paramUtils.getParameter("ComCode")).trim();
		String riskCode = (paramUtils.getParameter("RiskCode")).trim();
		String inputStartDate = (paramUtils.getParameter("InputStartDate")).trim();
		String inputEndDate = (paramUtils.getParameter("InputEndDate")).trim();
		String claimno = (paramUtils.getParameter("ClaimNo")).trim();
		StringBuffer claimConditions = new StringBuffer("");
		if (!"".equals(registNo)) {
			conditions += SqlUtils.convertString("REGISTNO", registNo);
		}
		if (!"".equals(policyNo)) {
			conditions += SqlUtils.convertString("POLICYNO", policyNo);
		}
		if (!"".equals(giveComCode)) {
			conditions += SqlUtils.convertString("COMCODE", giveComCode);
		}
		if (!"".equals(riskCode)) {
			conditions += SqlUtils.convertString("RISKCODE", riskCode);
		}
		claimConditions.append(conditions);//查询立案表的条件
		if (!"".equals(inputStartDate)) {
			conditions += " AND INPUTDATE >=TO_DATE('" + inputStartDate + "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (!"".equals(inputEndDate)) {
			conditions += " AND INPUTDATE <=TO_DATE('" + inputEndDate + "','yyyy-mm-dd hh24:mi:ss')";
		}
		if(!"".equals(claimno)){
			conditions+=" AND registNO in (select registNO from prplclaim where "+claimConditions.toString()+")";
		}
		return conditions;
	}

	/**
	 * 立案除外引导页查询引导
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return String
	 */
	public Page insertQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null || pageNo.trim().equals("")) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = Integer.parseInt(pageNo);
		String conditions = "";
		// 翻页查询条件
		String condition = request.getParameter("condition");
		if (condition != null && condition.trim().length() > 0) {
			conditions = condition;
		} else {
			conditions = this.generateConditions(request);
		}
		Page page = prpLregistService.findPrpLregist(conditions, intPageNo, intRecordPerPage);
		List<PrpLregist> result = page.getResult();
		for (PrpLregist prpLregist : result) {
			String sql = " nodetype = 'regis' and businessNo = '"+ prpLregist.getRegistNo() +"'";
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(sql);
			List<PrpLclaimStatus> prpLclaimStatusList = prpLclaimStatusService.findPrpLclaimStatus(queryRule);
			for (PrpLclaimStatus prpLclaimStatus : prpLclaimStatusList) {
				prpLregist.setStatus(prpLclaimStatus.getStatus());
			}
		}
		return page;
	}

	/**
	 * 进入立案除外处理页面
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return String
	 */
	public void prepareInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String registNo = request.getParameter("registNo");
		PrpLregist prpLregist  =  prpLregistService.findPrpLregist(registNo);
		request.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 立案除外接收提交
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return String
	 */
	public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		String excludeReason = (request.getParameter("excludereason")).trim();
		ParamUtils paramUtils = new ParamUtils(request);
		String registNo = (paramUtils.getParameter("registNo")).trim();
		if (this.isExcluded(registNo)) {
			throw new UserException(1, 3, "立案除外", "該賠案已進行立案除外輸入操作，請不要重複提交！");
		} else {
			prplexcludeclaimService.save(registNo, excludeReason, userDto);
			userDto.setUserMessage("立案除外任務提交成功");
			request.setAttribute("user", userDto);
		}
	}

	/**
	 * 立案除外历史查询
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return String
	 */
	public Page historyQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		 String generalType = request.getParameter("generalType");
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null || pageNo.trim().equals("")) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = Integer.parseInt(pageNo);
		String conditions = "";
		// 翻页查询条件
		String condition = request.getParameter("condition");
		if (condition != null && condition.trim().length() > 0) {
			conditions = condition;
		} else {
			conditions = this.generateHistoryConditions(request);
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = prplexcludeclaimService.findPrplexcludeclaim(queryRule, intPageNo, intRecordPerPage);
		return page;
	}

	/**
	 * 是否做过除外
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return String
	 */
	public boolean isExcluded(String registNo) throws Exception {
		return prplexcludeclaimService.isExcluded(registNo);
	}

	public PrplexcludeclaimService getPrplexcludeclaimService() {
		return prplexcludeclaimService;
	}

	public void setPrplexcludeclaimService(PrplexcludeclaimService prplexcludeclaimService) {
		this.prplexcludeclaimService = prplexcludeclaimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	
}