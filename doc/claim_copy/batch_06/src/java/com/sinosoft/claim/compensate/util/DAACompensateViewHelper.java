package com.sinosoft.claim.compensate.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCcarDriver;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.service.facade.PrpCcarDriverService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: CompensateViewHelper
 * </p>
 * <p>
 * Description:实赔ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
@SuppressWarnings("unchecked")
public class DAACompensateViewHelper extends CompensateViewHelper {
	/** 立案服务 */
	private ClaimService claimService;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	private PrpCcarDriverService prpCcarDriverService;
	/**
	 * 取初始化信息需要的数据的整理. 填写实赔单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return compensateDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public CompensateDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CompensateDto compensateDto = new CompensateDto();
		return compensateDto;
	}

	/**
	 * 填写实赔页面及查询实赔request的生成.
	 * 填写实赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws Exception {
		// 得到request的PrpLcompensateForm用於显示
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
	}

	/**
	 * 根据赔款计算书号和保单号,赔案号,案件状态，车牌号码，操作时间查询实赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception 增加车牌号，案件状态，操作时间查询条件
	 */

	public void setPrpLcompensateToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {
		// compensateNo,policyNo,claimNo
		// 根据输入的保单号，实赔号生成SQL where 子句
		String riskType = httpServletRequest.getParameter("riskType");
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String compensateNo = StringUtils.rightTrim(workFlowQueryDto.getCompensateNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String underWriteFlag = StringUtils.rightTrim(workFlowQueryDto.getUnderWriteFlag());
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("a.compensateNo", compensateNo, workFlowQueryDto.getCompensateNoSign()));
		conditions.append(StringConvert.convertString("a.claimNo", claimNo, workFlowQueryDto.getClaimNoSign()));
		conditions.append(StringConvert.convertString("a.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign()));
		conditions.append(StringConvert.convertString("c.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign()));
		conditions.append(StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign()));
		if (status != null && status.trim().length() > 0) {
			conditions.append(" AND b.status in (" + status + ")");
		}
		if (underWriteFlag != null && underWriteFlag.trim().length() > 0) {
			conditions.append(" AND a.underWriteFlag in (" + underWriteFlag + ") ");
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign()));
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(uiPowerInterface.addPower(userDto, "a", "", "ComCode"));
		if (!(riskType == null)) {
			if (riskType.equals("acci")) {
				conditions.append(" and (a.riskCode like '07%' or a.riskCode like '06%') ");
			}
		} else {
			conditions.append(" and (a.riskCode not like '07%' or a.riskCode like '06%' ) ");
		}
		// 查询预赔信息, 得到多行实赔主表信息
		List<PrpLcompensate> compensateList = this.getCompensateService().findByQueryConditions(conditions.toString());
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		prpLcompensate.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
	}

	public Page setPrpLcompensateDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto, String pageNo, String recordPerPage) throws Exception {
		// 根据输入的保单号，实赔号生成SQL where 子句
		int intPageNo = Integer.parseInt(pageNo);
		int intPageSize = Integer.parseInt(recordPerPage);
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String compensateNo = StringUtils.rightTrim(workFlowQueryDto.getCompensateNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String underWriteFlag = StringUtils.rightTrim(workFlowQueryDto.getUnderWriteFlag());
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		StringBuffer tempa = new StringBuffer("");
		tempa.append(StringConvert.convertString("a.compensateNo", compensateNo, workFlowQueryDto.getCompensateNoSign()));
		tempa.append(StringConvert.convertString("a.claimNo", claimNo, workFlowQueryDto.getClaimNoSign()));
		tempa.append(StringConvert.convertString("a.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign()));
		if (underWriteFlag != null && underWriteFlag.trim().length() > 0) {
			tempa.append(" AND a.underWriteFlag in (" + underWriteFlag + ") ");
		}
		/***业务表查询不再限制机构  delete by chenjie 20130614 start*/
//		UIPowerInterface uiPowerInterface = new UIPowerInterface();
//		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
//		tempa.append(uiPowerInterface.addPower(userDto, "a", "", "ComCode"));
		/***业务表查询不再限制机构  delete by chenjie 20130614 end*/
		conditions.append(tempa);//
		StringBuffer tempb = new StringBuffer("");
		if (status != null && status.trim().length() > 0) {
			tempb.append(" AND b.status in (" + status + ")");
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			tempb.append(StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign()));
		}
		conditions.append(tempb);//
		StringBuffer tempc = new StringBuffer("");
		tempc.append(StringConvert.convertString("PrpLregist.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign()));
		tempc.append(StringConvert.convertString("PrpLregist.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign()));
		if (registNo != null && !registNo.trim().equals("")) {
			tempc.append(StringConvert.convertString("PrpLregist.registNo", registNo, workFlowQueryDto.getRegistNoSign()));
		}
		if (tempc.length() > 0) {
			String claimList = this.getPrpLcompensateService().getClaimNoConditions(tempc.toString());
			if (claimList.length() > 0) {
				conditions.append(" and ");
				conditions.append(" claimNo in(" + claimList + ")");
			}else{
				return new Page();
			}
		}
		// 查询预赔信息,得到多行实赔主表信息
		Page page = this.getCompensateService().findPageByConditions(conditions.toString(), intPageNo, intPageSize);
		List<PrpLcompensate> compensateList = page.getResult();
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		prpLcompensate.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		return page;

	}

	/**
	 * 根据赔款计算书号和保单号和赔案号查询待复核的实赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public void getApproveCompensateList(HttpServletRequest httpServletRequest, String compensateNo, String policyNo, String claimNo) throws Exception {
		// 根据输入的保单号，实赔号生成SQL where 子句
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("prplcompensate.compensateNo", StringUtils.rightTrim(compensateNo), httpServletRequest.getParameter("CompensateNoSign")));
		conditions.append(StringConvert.convertString("prplcompensate.policyNo", StringUtils.rightTrim(policyNo), httpServletRequest.getParameter("PolicyNoSign")));
		conditions.append(StringConvert.convertString("prplcompensate.claimNo", StringUtils.rightTrim(claimNo), httpServletRequest.getParameter("ClaimNoSign")));
		conditions.append(" AND ( prplcompensate.ApproverCode IS NULL OR  prplcompensate.ApproverCode='' OR prplcompensate.UnderWriteFlag='2')");
		// 查询预赔信息,得到多行实赔主表信息
		List<PrpLcompensate> compensateList = this.getCompensateService().findByApproveConditions(conditions.toString());
		;
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		prpLcompensate.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
	}

	/**
	 * 检查缴费标志 返回值 int -1为未缴费，0为未缴全，1为缴全
	 * @param httpServletRequest 返回给页面的request
	 * @param policyNo 赔案号
	 * @throws Exception
	 */
	public int checkPay(HttpServletRequest httpServletRequest, String policyNo) throws Exception {
		// 取得赔款计算书信息
		String conditions = " policyno = '" + policyNo + "' ";
		return this.getPolicyService().checkPay(conditions);
	}

	/**
	 * 查询赔案号对应的实赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public void setPrpLcompensateByClaimNoDtoToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {

		claimNo = StringUtils.rightTrim(claimNo);
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" claimNo = '" + claimNo + "'");
		conditions.append(" AND caseType <> 'E'");
		// 查询预赔信息,得到多行实赔主表信息
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions.toString());
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		// 判断出险驾驶员是否非约定驾驶员
		ClaimDto claimDto = this.getClaimService().findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		
		List<PrpLdriver> prpLdriverList = claimDto.getPrpLdriverList();
		String driverName = "";
		String driverFlag = "";
		// 取出出险驾驶员的姓名
		if (!prpLdriverList.isEmpty()) {
			driverName = prpLdriverList.get(0).getDriverName();
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", prpLclaim.getPolicyNo());
		List<PrpCcarDriver>  prpCcarDriverList = this.prpCcarDriverService.findPrpCcarDriver(queryRule);
		driverFlag = "false";
		if (prpCcarDriverList == null || prpCcarDriverList.isEmpty()) {
			// 如果保单没有约定，出险时录入,算约定
			driverFlag = "true";
		} else {
			for (PrpCcarDriver prpCcarDriverDto : prpCcarDriverList) {
				if (prpCcarDriverDto.getDriverName().equals(driverName)) {
					driverFlag = "true";// 约定了驾驶员
					break;
				}
			}
		}
		httpServletRequest.setAttribute("driverFlag", driverFlag);
	}

	public void setPrpLcompensateDtoToPrint(HttpServletRequest httpServletRequest,String compensateNo,String claimNo,String registNo) throws Exception {
		String sql = null;
		if(DataUtils.emptyToNull(compensateNo) != null){
			sql = " claimNo in (select claimNo PrpLcompensate where compensateNo='"+compensateNo.trim()+"') and compensateNo like 'C%'";
		}else if(DataUtils.emptyToNull(claimNo) != null){
			sql = " claimNo ='"+claimNo.trim()+"' and compensateNo like 'C%'";
		}else if(DataUtils.emptyToNull(registNo) != null){
			sql = " claimNo in (select claimNo prpLclaim where registNo='"+registNo.trim()+"') and compensateNo like 'C%'";
		}else{
			throw new UserException(0, 0, "計算書", "數據錯誤！");
		}
		List<PrpLcompensate> prpLcompensateList = this.getCompensateService().findByConditions(sql);
		if(prpLcompensateList==null||prpLcompensateList.size()==0){
			throw new UserException(0, 0, "計算書", "沒有查詢到需要列印的計算書！");
		}
		httpServletRequest.setAttribute("prpLcompensateList", prpLcompensateList);
	}

	/**
	 * 根据赔款计算书号和保单号和赔案号查询实赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception
	 */

	public void searchPassCompensate(HttpServletRequest httpServletRequest, String compensateNo, String policyNo, String claimNo, String operateDate, String underWriteFlag, String pageNo, String pageSize) throws Exception {
		// compensateNo,policyNo,claimNo
		// 根据输入的保单号，实赔号生成SQL where 子句
		claimNo = StringUtils.rightTrim(claimNo);
		policyNo = StringUtils.rightTrim(policyNo);
		compensateNo = StringUtils.rightTrim(compensateNo);
		operateDate = StringUtils.rightTrim(operateDate);
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("compensateNo", StringUtils.rightTrim(compensateNo), httpServletRequest.getParameter("CompensateNoSign")));
		conditions.append(StringConvert.convertString("claimNo", StringUtils.rightTrim(claimNo), httpServletRequest.getParameter("ClaimNoSign")));
		conditions.append(StringConvert.convertString("policyNo", StringUtils.rightTrim(policyNo), httpServletRequest.getParameter("PolicyNoSign")));
		if (underWriteFlag != null && underWriteFlag.trim().length() > 0) {
			conditions.append(" AND underWriteFlag in (" + underWriteFlag + ") ");
		}
		operateDate = StringUtils.rightTrim(operateDate);
		if (operateDate != null && !"".equals(operateDate.trim())) {
			conditions.append(StringConvert.convertDate("InputDate", StringUtils.rightTrim(operateDate), httpServletRequest.getParameter("OperateDateSign")));
		}
		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(uiPowerInterface.addPower(userDto, "prplcompensate", "", "ComCode"));
		Page page = this.getCompensateService().findByConditions(conditions.toString(), Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(page.getResult());
		prpLcompensate.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);

	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrpCcarDriverService getPrpCcarDriverService() {
		return prpCcarDriverService;
	}

	public void setPrpCcarDriverService(PrpCcarDriverService prpCcarDriverService) {
		this.prpCcarDriverService = prpCcarDriverService;
	}

}