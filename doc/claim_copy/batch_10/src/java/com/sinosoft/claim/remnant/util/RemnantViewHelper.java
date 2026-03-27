package com.sinosoft.claim.remnant.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.remnant.service.facade.RemnantService;
import com.sinosoft.claim.remnant.vo.RemnantDto;
import com.sinosoft.claim.replevy.vo.ReplevyUndwrtDto;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemCarId;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLbuyer;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLremnant;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpLbuyerService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLremnantService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.log.Logger;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class RemnantViewHelper{
	/** Log日志对象 */
	private static Logger log = Logger.getLogger(RemnantViewHelper.class.getName());
	public static int RULE_LENGTH = 70; // rule字段的长度
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 联共保赔付金额分摊信息服务 */
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/** 代码service */
	private CodeService codeService;
	/** 立案Service */
	private PrpLclaimService prpLclaimService;
	/** 承保车辆Service */
	private PrpCitemCarService prpCitemCarService;
	/** 生成单号Service */
	private BillService billService;
	/** 支付对象信息Service */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 买受人service */
	private PrpLbuyerService prpLbuyerService;
	/** 残余物sevice */
	private PrpLremnantService prpLremnantService;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 残余物大对象服务 */
	private RemnantService remnantService;
	/**
	 * 立案危险单位信息
	 */
	private PrpLclaimLossService prpLclaimLossService;
	private WorkFlowService workFlowService;
	private UtiUserGradeService utiUserGradeService;
	private PowerService powerService;
	/**
	 * 默认构造方法
	 */
	public RemnantViewHelper() {
	}

	/**
	 * 残余物处理信息查询方法
	 * @param httpServletRequest
	 * @param pageNo 当前页
	 * @param recordPerPage 每页显示条数
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addQueryDtoToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 得到追偿查询条件
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 立案号
		String compelLicenseNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompelLicenseNo")); // 強制證號碼
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		String licenseNo = StringConvert.getParam(httpServletRequest, "LicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号码
		String status = StringUtils.rightTrim(httpServletRequest.getParameter("status"));
		String insuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET); // 被追偿人名称
		String remnantFlag = httpServletRequest.getParameter("remnantFlag");//理算是否標記有殘餘物
		// 得到操作符号
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compelLicenseNoSign = httpServletRequest.getParameter("CompelLicenseNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		// 组合查询条件
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		Page page = null;
		conditions.append(" AND CANCELDATE IS NULL AND CLAIMDATE IS NOT NULL AND ENDCASEDATE IS NOT NULL AND CASENO IS NOT NULL ");
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		if (!CommonUtils.isEmpty(licenseNo)) {
			conditions.append(" and exists ( ");
			conditions.append(" select 0 from prplregist where registno = PrpLclaim.registno ");
			conditions.append(StringConvert.convertString("licenseNo", licenseNo, licenseNoSign));
			conditions.append(" ) ");
		}
		if (!CommonUtils.isEmpty(compelLicenseNo)) {
			conditions.append(" and exists ( ");
			conditions.append(" select 0 from prpcmain where policyno = PrpLclaim.policyno ");
			conditions.append(StringConvert.convertString("printNo", compelLicenseNo, compelLicenseNoSign));
			conditions.append(" ) ");
		}
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		if ("0".equals(status)) {// 未处理;即理算计算书prplcompensate表中remnants字段为"1";
			conditions.append(" and claimNo not in ( select claimNo from prpLcompensate c where  c.caseType = 'S' ) ");
		} else {// 已处理;审核通过的 ;
			conditions.append(" and claimNo in ( select claimNo from prpLcompensate c where c.claimno = PrpLclaim.claimNo AND caseType = 'S' And underWriteFlag = '1') ");
			//不能有核赔未通过的残余物计算书
			conditions.append(" and claimNo not in ( select claimNo from prpLcompensate c where caseType = 'S' And underWriteFlag != '1') ");
		}
		//理算是否標記有殘餘物 
		conditions.append(" and claimNo in ( select claimNo from prpLcompensate c where remnants = '"+remnantFlag+"'  AND (caseType is null or caseType not in ('R', 'S')))");
		//核赔通过后，没有结束的
		conditions.append(" and claimNo not in ( select claimNo from prpLcompensate c where remnants='9' and caseType = 'S' And underWriteFlag = '1') ");
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "PrpLclaim","claim") + uiPowerInterface.addCustomerPower(userDto, "PrpLclaim", "", "ComCode"));
		conditions.append(" ORDER BY CLAIMDATE DESC");
		PrpLclaim prpLclaim = new PrpLclaim();
		page = this.prpLclaimService.findByConditions(conditions.toString(), pageNo, recordPerPage);
		List<PrpLclaim> prpLclaimList = page.getResult();
		for (int i = 0; i < prpLclaimList.size(); i++) {
			PrpLclaim prpLclaimtemp = new PrpLclaim();
			prpLclaimtemp = prpLclaimList.get(i);
			prpLclaimtemp.setRiskCodeName(this.codeService.translateRiskCode(prpLclaimtemp.getRiskCode(), true));
			prpLclaimtemp.setComName(this.codeService.translateComCode(prpLclaimtemp.getHandleDept(), true));
			if ("1".equals(status)) {
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("claimNo", prpLclaimtemp.getClaimNo());
				queryRule.addAscOrder("remnantDate");
				List<PrpLremnant> prpLremnantList = null;
				prpLremnantList = this.prpLremnantService.findPrpLremnant(queryRule);
				if (prpLremnantList != null && prpLremnantList.size() > 0) {
					prpLclaimtemp.setRemnantDate(prpLremnantList.get(0).getRemnantDate());
				}
			}
		}
		prpLclaim.setClaimList(prpLclaimList);
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		// 将查询信息发送到页面
		httpServletRequest.setAttribute("prpLclaimList", prpLclaimList);
		httpServletRequest.setAttribute("page", page);
	}

	/**
	 * 残余物审核信息查询
	 * @param httpServletRequest
	 * @param intPageNo 当前页
	 * @param intRecordPerPage 每页包含的数据的条数
	 * @throws Exception
	 */
	public void undwrtQueryDtoToView(HttpServletRequest httpServletRequest, int intPageNo, int intRecordPerPage) throws Exception {
		// 得到追偿查询条件
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 立案号
		String compelLicenseNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompelLicenseNo")); // 強制證號碼
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		String licenseNo = StringConvert.getParam(httpServletRequest, "LicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号码
		String insuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET); // 被追偿人名称
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompensateNo"));// 计算书号
		// 得到操作符号
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compelLicenseNoSign = httpServletRequest.getParameter("CompelLicenseNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String compensateNoSign = httpServletRequest.getParameter("CompensateNoSign");
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 组合查询条件
		StringBuffer conditions = new StringBuffer("");
		Page page = null;
		conditions.append("(s.flowID is null or ( ");
		conditions.append("s.nodeStatus < 4  ");
		conditions.append(" and (s.handlerCode='" + userDto.getUserCode() + "' or s.handlerCode ='" + SwfLog.HANDLERCODE_NONE + "' ) ");
		conditions.append(")) ");
		conditions.append(" and c.compensateNo <> CONCAT(CONCAT('S',c.claimno),'00')"); 
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("compensateNo", compensateNo, compensateNoSign));
		conditions.append(" and caseType='S' and underwriteFlag='0'");
		conditions.append("and claimNo in (select claimNo from PrpLclaim where 1 = 1 ");
		conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		conditions.append("and policyNo in (select policyNo from prpcmain where 1=1 ");
		conditions.append(StringConvert.convertString("printNo", compelLicenseNo, compelLicenseNoSign));
		conditions.append(")");
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append("and registNo in (select registNo from prpLregist where 1 = 1 ");
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append(StringConvert.convertString("licenseNo", licenseNo, licenseNoSign));
		conditions.append(")) ");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "c","claim") + uiPowerInterface.addCustomerPower(userDto, "c", "", "ComCode"));
		
		conditions.append(" ORDER BY INPUTDATE DESC ");
		page = remnantService.findUndwrtByConditions(conditions.toString(), intPageNo, intRecordPerPage);
//		page = this.prpLcompensateService.findByConditions(conditions.toString(), intPageNo, intRecordPerPage);
//		List<?> prpLcompensateList = page.getResult();
		Iterator<?> it = page.getResult().iterator();
		PrpLclaim prpLclaim = null;
		ReplevyUndwrtDto undwrtDto = null;
		while (it.hasNext()) {
			undwrtDto = (ReplevyUndwrtDto) it.next();
			prpLclaim = prpLclaimService.findPrpLclaim(undwrtDto.getClaimNo());
			undwrtDto.setRiskCodeName(this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
			undwrtDto.setComName(this.codeService.translateComCode(prpLclaim.getHandleDept(), true));
			List<PrpLremnant> prpLremnantList = this.prpLremnantService.findByCompensateNo(undwrtDto.getCompensateNo());
			if(prpLremnantList.size()>0){
				undwrtDto.setRemnantDate(prpLremnantList.get(0).getRemnantDate());
			}
		}
		httpServletRequest.setAttribute("page", page);
	}

	/**
	 * 残余物修改信息查询
	 * @param httpServletRequest
	 * @param intPageNo 当前页
	 * @param intRecordPerPage 每页包含的数据的条数
	 * @throws Exception
	 */
	public void editQueryDtoToView(HttpServletRequest httpServletRequest, int intPageNo, int intRecordPerPage) throws Exception {
		// 得到追偿查询条件
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 立案号
		String compelLicenseNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompelLicenseNo")); // 強制證號碼
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		String licenseNo = StringConvert.getParam(httpServletRequest, "LicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号码
		String insuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET); // 被追偿人名称
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompensateNo"));// 计算书号
		// 得到操作符号
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compelLicenseNoSign = httpServletRequest.getParameter("CompelLicenseNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String compensateNoSign = httpServletRequest.getParameter("CompensateNoSign");
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 组合查询条件
		StringBuffer conditions = new StringBuffer("");
		Page page = null;
		conditions.append("(s.flowID is null or ( ");
		conditions.append("s.nodeStatus < 4  ");
		conditions.append(" and (s.handlerCode='" + userDto.getUserCode() + "' or s.handlerCode ='" + SwfLog.HANDLERCODE_NONE + "' ) ");
		conditions.append(")) ");
		conditions.append(" and c.compensateNo <> CONCAT(CONCAT('S',c.claimno),'00')"); 
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("compensateNo", compensateNo, compensateNoSign));
		conditions.append(" and caseType='S' and underwriteFlag='2'");
		conditions.append("and claimNo in (select claimNo from PrpLclaim where 1 = 1 ");
		conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		conditions.append("and policyNo in (select policyNo from prpcmain where 1=1 ");
		conditions.append(StringConvert.convertString("printNo", compelLicenseNo, compelLicenseNoSign));
		conditions.append(")");
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append("and registNo in (select registNo from prpLregist where 1 = 1 ");
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append(StringConvert.convertString("licenseNo", licenseNo, licenseNoSign));
		conditions.append(")) ");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "c","claim") + uiPowerInterface.addCustomerPower(userDto, "c", "", "ComCode"));
		conditions.append(" and c.handlercode ='"+userDto.getUserCode()+"'");
		conditions.append(" ORDER BY INPUTDATE DESC ");
		page = this.remnantService.findUndwrtByConditions(conditions.toString(), intPageNo, intRecordPerPage);
//		page = this.prpLcompensateService.findByConditions(conditions.toString(), intPageNo, intRecordPerPage);
		Iterator<?> it = page.getResult().iterator();
		PrpLclaim prpLclaim = null;
		ReplevyUndwrtDto undwrtDto = null;
		while (it.hasNext()) {
			undwrtDto = (ReplevyUndwrtDto) it.next();
			prpLclaim = prpLclaimService.findPrpLclaim(undwrtDto.getClaimNo());
			undwrtDto.setRiskCodeName(this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
			undwrtDto.setComName(this.codeService.translateComCode(prpLclaim.getHandleDept(), true));
			List<PrpLremnant> prpLremnantList = this.prpLremnantService.findByCompensateNo(undwrtDto.getCompensateNo());
			if(prpLremnantList.size()>0){
				undwrtDto.setRemnantDate(prpLremnantList.get(0).getRemnantDate());
			}
		}
		httpServletRequest.setAttribute("page", page);
	}

	/**
	 * 审核通过的残余物信息查询
	 * @param httpServletRequest
	 * @param intPageNo 当前页
	 * @param intRecordPerPage 每页包含的数据的条数
	 * @throws Exception
	 */
	public void showQueryDtoToView(HttpServletRequest httpServletRequest, int intPageNo, int intRecordPerPage) throws Exception {
		// 得到追偿查询条件
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("ClaimNo")); // 立案号
		String compelLicenseNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompelLicenseNo")); // 強制證號碼
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("PolicyNo")); // 保单号
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("RegistNo"));// 报案号
		String licenseNo = StringConvert.getParam(httpServletRequest, "LicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号码
		String insuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET); // 被追偿人名称
		String compensateNo = StringUtils.rightTrim(httpServletRequest.getParameter("CompensateNo"));// 计算书号
		// 得到操作符号
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String compelLicenseNoSign = httpServletRequest.getParameter("CompelLicenseNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String compensateNoSign = httpServletRequest.getParameter("CompensateNoSign");
		// 组合查询条件
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		Page page = null;
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("compensateNo", compensateNo, compensateNoSign));
		conditions.append(" and caseType='S' and underwriteFlag='1'");// underwriteFlag的說明：0表示殘餘物提交，1表示審核通過，3表示打回修改。
		conditions.append("and claimNo in (select claimNo from PrpLclaim where 1 = 1 ");
		conditions.append(StringConvert.convertString("policyNo", policyNo, policyNoSign));
		conditions.append("and policyNo in (select policyNo from prpcmain where 1=1 ");
		conditions.append(StringConvert.convertString("printNo", compelLicenseNo, compelLicenseNoSign));
		conditions.append(")");
		conditions.append(StringConvert.convertString("claimNo", claimNo, claimNoSign));
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append("and registNo in (select registNo from prpLregist where 1 = 1 ");
		conditions.append(StringConvert.convertString("registNo", registNo, registNoSign));
		conditions.append(StringConvert.convertString("licenseNo", licenseNo, licenseNoSign));
		conditions.append(")) ");// remnants的說明：0表示没有殘餘物，1表示有残余物，未处理，9表示有残余物，已处理。
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "PrpLcompensate","claim") + uiPowerInterface.addCustomerPower(userDto, "PrpLcompensate", "", "ComCode"));
		conditions.append(" ORDER BY INPUTDATE DESC ");
		page = this.prpLcompensateService.findByConditions(conditions.toString(), intPageNo, intRecordPerPage);
		List<?> prpLcompensateList = page.getResult();
		Iterator<?> it = prpLcompensateList.iterator();
		PrpLclaim prpLclaim = null;
		while (it.hasNext()) {
			PrpLcompensate prpLcompensatetemp = (PrpLcompensate) it.next();
			prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensatetemp.getClaimNo());
			prpLcompensatetemp.setRiskCodeName(this.codeService.translateRiskCode(prpLcompensatetemp.getRiskCode(), true));
			prpLcompensatetemp.setComName(this.codeService.translateComCode(prpLclaim.getHandleDept(), true));
			List<PrpLremnant> prpLremnantList = this.prpLremnantService.findByCompensateNo(prpLcompensatetemp.getCompensateNo());
			if(prpLremnantList.size()>0){
				prpLcompensatetemp.setRemnantDate(prpLremnantList.get(0).getRemnantDate());
			}
		}
		httpServletRequest.setAttribute("page", page);
	}


	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prplclaim 立案对象
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLclaim prplclaim) throws Exception {
		String classCode = prplclaim.getClassCode();
		// 得到车辆种类列表
		List<PrpDcode> carKindCodes = codeService.getCodeTypeCarKind("CarKind", classCode);
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// 殘餘物費用代碼
		httpServletRequest.setAttribute("remnantCostList", ConstantsCollection.RemnantCostList);
		httpServletRequest.setAttribute("payObjectTypeList", ConstantsCollection.payObjectTypeList);
		httpServletRequest.setAttribute("prpdpaymentaccountCertificateTypeList", ConstantsCollection.prpdpaymentaccountCertificateTypeList);
		httpServletRequest.setAttribute("prpLpayObjectInfoCurrencyList",codeService.findPayCurrencyMap());
		
	}

	/**
	 * 收集页面信息生成残余物大对象
	 * @param httpServletRequest 页面传来的request
	 * @param claimNo 立案号
	 * @return RemnantDto 残余物大对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public RemnantDto viewToDto(HttpServletRequest httpServletRequest, String claimNo) throws SQLException, Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		RemnantDto remnantDto = new RemnantDto();
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		int year = DateTime.current().getYear();
		String compensateNo = httpServletRequest.getParameter("prpLcompensateCompensateNo");
		if (DataUtils.emptyToNull(compensateNo) == null) {
			compensateNo = this.billService.getNo("prpLremnant", claimNo, "", year); // 生成残余物计算书号
		}
		httpServletRequest.setAttribute("compensateNo",compensateNo);
		/*----------------------------残余物信息收集---------------------------------------------------------*/
		String riskCode = prpLclaim.getRiskCode();
		String classCode = UICodeAction.getInstance().translateClassCodeByRiskCode(riskCode);
		String prpLcompensateRemnantCode = httpServletRequest.getParameter("prpLcompensateRemnantCode");// 标的序号
		String prpLcompensateRemnantDate = httpServletRequest.getParameter("prpLcompensateRemnantDate");// 理赔确认日
		
		String[] prpLremnantSerialNo = httpServletRequest.getParameterValues("prpLremnantSerialNo");// 出险险别代码
		String[] prpLremnantKindCode = httpServletRequest.getParameterValues("prpLremnantKindCode");// 出险险别代码
		String[] prpLremnantKindName = httpServletRequest.getParameterValues("prpLremnantKindName");// 出险险别名称
		String[] prpLremnantAddress = httpServletRequest.getParameterValues("prpLremnantAddress");// 地点
		String[] prpLremnantGenerateDate = httpServletRequest.getParameterValues("prpLremnantGenerateDate");// 产生日期
		String[] prpLremnantEstimateAmount = httpServletRequest.getParameterValues("prpLremnantEstimateAmount");// 预估金额
		String[] prpLremnantAuctionDate = httpServletRequest.getParameterValues("prpLremnantAuctionDate");// 拍卖日期
		String[] prpLremnantAuctionAmount = httpServletRequest.getParameterValues("prpLremnantAuctionAmount");// 拍卖金额
		String[] prpLremnantHandleCost = httpServletRequest.getParameterValues("prpLremnantHandleCost");// 处理费用
		String[] prpLremnantRealPay = httpServletRequest.getParameterValues("prpLremnantRealPay");// 实缴金额
		String[] prpLremnantShareDate = httpServletRequest.getParameterValues("prpLremnantShareDate");// 摊回日期
		String[] prpLremnantBackAmount = httpServletRequest.getParameterValues("prpLremnantBackAmount");// 失窃车返还额
		String[] prpLremnantConfirmorName = httpServletRequest.getParameterValues("prpLremnantConfirmorName");// 确认人名称
		String[] prpLremnantConfirmDate = httpServletRequest.getParameterValues("prpLremnantConfirmDate");// 确认日期
		String[] prpLremnantRemnants = httpServletRequest.getParameterValues("prpLremnantRemnants");// 殘餘物任務是否結束,0否，1是，默认'是'
		String[] prpLremnantPayObjectSerialNo = httpServletRequest.getParameterValues("prpLremnantPayObjectSerialNo");// 赔付对象讯息
		String[] prpLremnantCurrency = httpServletRequest.getParameterValues("prpLremnantCurrency");// 理赔确认日
		String[] prpLremnantExchRate = httpServletRequest.getParameterValues("prpLremnantExchRate");// 理赔确认日
		
		PrpLremnant prpLremnant = null;
		for(int i=1;i<prpLremnantSerialNo.length;i++){
			prpLremnant = new PrpLremnant();
			prpLremnant.getId().setCompensateNo(compensateNo);
			prpLremnant.getId().setSerialNo(i);
			prpLremnant.setClaimNo(claimNo);
			prpLremnant.setRiskCode(riskCode);
			prpLremnant.setClassCode(classCode);
			prpLremnant.setPolicyNo(prpLclaim.getPolicyNo());
			prpLremnant.setKindCode(prpLremnantKindCode[i]);
			prpLremnant.setKindName(prpLremnantKindName[i]);
			prpLremnant.setAddress(prpLremnantAddress[i]);
			prpLremnant.setConfirmorCode(" ");
			if(!CommonUtils.isEmpty(prpLremnantGenerateDate[i])){
				prpLremnant.setGenerateDate(new DateTime(prpLremnantGenerateDate[i], DateTime.YEAR_TO_DAY));
			}
			prpLremnant.setEstimateAmount(DataUtils.getDouble(DataUtils.nullToZero(prpLremnantEstimateAmount[i])));
			if(!CommonUtils.isEmpty(prpLremnantAuctionDate[i])){
				prpLremnant.setAuctionDate(new DateTime(prpLremnantAuctionDate[i], DateTime.YEAR_TO_DAY));
			}
			prpLremnant.setAuctionAmount(DataUtils.getDouble(DataUtils.nullToZero(prpLremnantAuctionAmount[i])));
			prpLremnant.setHandleCost(DataUtils.getDouble(DataUtils.nullToZero(prpLremnantHandleCost[i])));
			prpLremnant.setRealPay(DataUtils.getDouble(DataUtils.nullToZero(prpLremnantRealPay[i])));
			if(!CommonUtils.isEmpty(prpLremnantShareDate[i])){
				prpLremnant.setShareDate(new DateTime(prpLremnantShareDate[i], DateTime.YEAR_TO_DAY));
			}
			prpLremnant.setBackAmount(DataUtils.getDouble(DataUtils.nullToZero(prpLremnantBackAmount[i])));
			prpLremnant.setConfirmorName(prpLremnantConfirmorName[i]);
			if(!CommonUtils.isEmpty(prpLremnantConfirmDate[i])){
				prpLremnant.setConfirmDate(new DateTime(prpLremnantConfirmDate[i], DateTime.YEAR_TO_DAY));
			}
			prpLremnant.setRemnants(prpLremnantRemnants[i]);
			prpLremnant.setPayObjectSerialNo(prpLremnantPayObjectSerialNo[i]);
			prpLremnant.setRemnantCode(prpLcompensateRemnantCode);
			prpLremnant.setComCode(user.getComCode());
			prpLremnant.setHandleCode(user.getUserCode());
			prpLremnant.setHandleName(user.getUserName());
			if(!CommonUtils.isEmpty(prpLcompensateRemnantDate)){
				prpLremnant.setRemnantDate(new DateTime(prpLcompensateRemnantDate, DateTime.YEAR_TO_DAY));
			}
			prpLremnant.setCurrency(prpLremnantCurrency[i]);
			prpLremnant.setExchRate(DataUtils.getDouble(prpLremnantExchRate[i]));
			prpLcompensate.setSumLoss(prpLcompensate.getSumLoss()-prpLremnant.getAuctionAmount());
			prpLcompensate.setSumDutyPaid(prpLcompensate.getSumDutyPaid()-prpLremnant.getRealPay()*prpLremnant.getExchRate());
			remnantDto.getPrpLremnantList().add(prpLremnant);
		}
		/*----------------------------买受人信息收集---------------------------------------------------------*/
		String[] prpLbuyerSerialNo = httpServletRequest.getParameterValues("prpLbuyerSerialNo");
		String[] prplbuyerBuyerName = httpServletRequest.getParameterValues("prplbuyerBuyerName");
		String[] prplbuyerUniformNo = httpServletRequest.getParameterValues("prplbuyerUniformNo");
		String[] prplbuyerLinkPhone = httpServletRequest.getParameterValues("prplbuyerLinkPhone");
		String[] prplbuyerAddress = httpServletRequest.getParameterValues("prplbuyerAddress");
		String[] prplbuyerExplanation = httpServletRequest.getParameterValues("prplbuyerExplanation");
		PrpLbuyer prpLbuyer = null;
		for(int i=1;i<prpLbuyerSerialNo.length;i++){
			prpLbuyer = new PrpLbuyer();
			prpLbuyer.getId().setCompensateNo(compensateNo);
			prpLbuyer.getId().setSerialNo(i);
			prpLbuyer.setBuyerName(prplbuyerBuyerName[i]);
			prpLbuyer.setUniformNo(prplbuyerUniformNo[i]);
			prpLbuyer.setLinkPhone(prplbuyerLinkPhone[i]);
			prpLbuyer.setAddress(prplbuyerAddress[i]);
			prpLbuyer.setExplanation(prplbuyerExplanation[i]);
			remnantDto.getPrpLbuyerList().add(prpLbuyer);
		}
		/*----------------------------支付对象信息收集---------------------------------------------------------*/
		String[] prpLpayObjectInfoSerialNo = httpServletRequest.getParameterValues("prpLpayObjectInfoSerialNo");// 序号
		String[] prpLpayObjectInfoPayAmount = httpServletRequest.getParameterValues("prpLpayObjectInfoPayAmount");
		String[] prpLpayObjectInfoPaymentKind = httpServletRequest.getParameterValues("prpLpayObjectInfoPaymentKind");
		String[] prpLpayObjectInfoOwnerShip = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerShip");
		String[] prpLpayObjectInfoOwnerName = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerName");
		String[] prpLpayObjectInfoUniformNo = httpServletRequest.getParameterValues("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoCutBack = httpServletRequest.getParameterValues("prpLpayObjectInfoCutBack");
		String[] prpLpayObjectInfoBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoBankCode");
		String[] prpLpayObjectInfoBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoBankName");
		String[] prpLpayObjectInfoAccountCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCode");
		String[] prpLpayObjectInfoCustomBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankCode");
		String[] prpLpayObjectInfoCustomBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankName");
		String[] prpLpayObjectInfoAreaCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAreaCode");
		String[] prpLpayObjectInfoCourierAddress = httpServletRequest.getParameterValues("prpLpayObjectInfoCourierAddress");
		String[] prpLpayObjectInfoCertificateCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCertificateCode");
		String[] prpLpayObjectInfoBeneficiaryPhone = httpServletRequest.getParameterValues("prpLpayObjectInfoBeneficiaryPhone");
		
		PrpLpayObjectInfo prpLpayObjectInfo = null;
		for (int i = 1; i < prpLpayObjectInfoSerialNo.length; i++) {
			prpLpayObjectInfo = new PrpLpayObjectInfo();
			prpLpayObjectInfo.getId().setSerialNo(DataUtils.getInteger(DataUtils.nullToZero(prpLpayObjectInfoSerialNo[i])));
			prpLpayObjectInfo.getId().setCompensateNo(compensateNo);
			prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);
			prpLpayObjectInfo.setRiskCode(riskCode);
			prpLpayObjectInfo.setPayAmount(DataUtils.getDouble(DataUtils.nullToZero(prpLpayObjectInfoPayAmount[i])));
			prpLpayObjectInfo.setPaymentKind(prpLpayObjectInfoPaymentKind[i]);
			prpLpayObjectInfo.setOwnerShip(prpLpayObjectInfoOwnerShip[i]);
			prpLpayObjectInfo.setOwnerName(prpLpayObjectInfoOwnerName[i]);
			prpLpayObjectInfo.setUniformNo(prpLpayObjectInfoUniformNo[i]);
			prpLpayObjectInfo.setCutBack(prpLpayObjectInfoCutBack[i]);
			prpLpayObjectInfo.setBankCode(prpLpayObjectInfoBankCode[i]);
			prpLpayObjectInfo.setBankName(prpLpayObjectInfoBankName[i]);
			prpLpayObjectInfo.setAccountCode(prpLpayObjectInfoAccountCode[i]);
			prpLpayObjectInfo.setCustomBankCode(prpLpayObjectInfoCustomBankCode[i]);
			prpLpayObjectInfo.setCustomBankName(prpLpayObjectInfoCustomBankName[i]);
			prpLpayObjectInfo.setAreaCode(prpLpayObjectInfoAreaCode[i]);
			prpLpayObjectInfo.setCourierAddress(prpLpayObjectInfoCourierAddress[i]);
			prpLpayObjectInfo.setCertificateCode(prpLpayObjectInfoCertificateCode[i]);
			prpLpayObjectInfo.setBeneficiaryPhone(prpLpayObjectInfoBeneficiaryPhone[i]);
			remnantDto.getPrpLpayObjectInfoList().add(prpLpayObjectInfo);
		}
		/******************* 费用资讯信息 start ******************************/
		// 从界面得到输入数组
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");
		String[] prpLchargeExchRate = httpServletRequest.getParameterValues("prpLchargeExchRate");
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");
		String[] prpLchargeSumRealPay = httpServletRequest.getParameterValues("prpLchargeSumRealPay");
		String[] prpLchargeFlag = httpServletRequest.getParameterValues("prpLchargeFlag");
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport");
		String[] prpLchargePayObjectType = httpServletRequest.getParameterValues("prpLchargePayObjectType"); //
		String[] prpLchargePayObjectCode = httpServletRequest.getParameterValues("prpLchargePayObjectCode"); //
		String[] prpLchargePayObjectName = httpServletRequest.getParameterValues("prpLchargePayObjectName");
		String[] prpLchargeFeeSerialNo = httpServletRequest.getParameterValues("prpLchargeFeeSerialNo");
		
		// 增加对支付对象的保存
		String[] prpLchargeOwnerShip = httpServletRequest.getParameterValues("prpLchargeOwnerShip");// 費用支付方式
		String[] prpLchargeOwnerName = httpServletRequest.getParameterValues("prpLchargeOwnerName");// 賠付對象
		String[] prpLchargeUniformNo = httpServletRequest.getParameterValues("prpLchargeUniformNo");// ID/統一編號
		String[] prpLchargeCutBack = httpServletRequest.getParameterValues("prpLchargeCutBack");// 禁背
		String[] prpLchargeBankCode = httpServletRequest.getParameterValues("prpLchargeBankCode");// 總行代號
		String[] prpLchargeBankName = httpServletRequest.getParameterValues("prpLchargeBankName");// 總行名稱
		String[] prpLchargeAccountCode = httpServletRequest.getParameterValues("prpLchargeAccountCode");// 匯款帳號
		String[] prpLchargeCustomBankCode = httpServletRequest.getParameterValues("prpLchargeCustomBankCode");// 分行代號
		String[] prpLchargeCustomBankName = httpServletRequest.getParameterValues("prpLchargeCustomBankName");// 分行名稱
		String[] prpLchargeAreaCode = httpServletRequest.getParameterValues("prpLchargeAreaCode");// 郵遞區號
		String[] prpLchargeCourierAddress = httpServletRequest.getParameterValues("prpLchargeCourierAddress");// 郵遞地址
		String[] prpLchargeCertificateCode =  httpServletRequest.getParameterValues("prpLchargeCertificateCode");//證件類型
		
		// 赔款费用信息
		// 支付对象信息，存储费用支付对象和赔款支付对象，certiType/**业务类型01赔款，02费用*/区分
		PrpLcharge prpLcharge = null;
		int dangerNo = 1;
		List<PrpLclaimLoss> prpLclaimLossList = prpLclaimLossService.findPrpLclaimLoss(prpLclaim.getClaimNo());
		if (prpLclaimLossList.size() > 0) {
			Integer i = prpLclaimLossList.get(0).getDangerNo();
			if (i != null) {
				dangerNo = i;
			}
		}
		// 对象赋值
		for (int index = 1; index < prpLchargeSerialNo.length; index++) {
			prpLcharge = new PrpLcharge();
			prpLcharge.setPolicyNo(prpLclaim.getPolicyNo());
			prpLcharge.setRiskCode(prpLclaim.getRiskCode());
			prpLcharge.getId().setCompensateNo(compensateNo);
			prpLcharge.getId().setSerialNo(index);
			prpLcharge.setKindCode(prpLchargeKindCode[index]);
			prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
			prpLcharge.setChargeName(prpLchargeChargeName[index]);
			prpLcharge.setCurrency(prpLchargeCurrency[index]);
			prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
			prpLcharge.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLchargeSumRealPay[index])));
			prpLcharge.setFlag(prpLchargeFlag[index]);
			prpLcharge.setPayObjectCode(prpLchargePayObjectCode[index]);
			prpLcharge.setPayObjectType(prpLchargePayObjectType[index]);
			prpLcharge.setPayObjectName(prpLchargePayObjectName[index]);
			prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
			prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index])));
			prpLcharge.setExchRate(Double.parseDouble(DataUtils.nullToZero(prpLchargeExchRate[index])));
			if(!CommonUtils.isEmpty(prpLchargeFeeSerialNo[index])){
				prpLcharge.setFeeSerialNo(DataUtils.getInteger(prpLchargeFeeSerialNo[index]));
			}
			
			prpLcharge.setDangerNo(dangerNo);// 增加危险单位信息
			// 增加对支付对象的保存
			prpLpayObjectInfo = new PrpLpayObjectInfo();
			prpLpayObjectInfo.getId().setCompensateNo(compensateNo);
			prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_CHARGE);
			prpLpayObjectInfo.getId().setSerialNo(index);
			prpLpayObjectInfo.setRiskCode(prpLclaim.getRiskCode());
			prpLpayObjectInfo.setKindCode(prpLchargeKindCode[index]);
			prpLpayObjectInfo.setOwnerName(prpLchargeOwnerName[index]);
			prpLpayObjectInfo.setUniformNo(prpLchargeUniformNo[index]);
			prpLpayObjectInfo.setAreaCode(prpLchargeAreaCode[index]);
			prpLpayObjectInfo.setCourierAddress(prpLchargeCourierAddress[index]);
			prpLpayObjectInfo.setOwnerShip(prpLchargeOwnerShip[index]);
			prpLpayObjectInfo.setBankCode(prpLchargeBankCode[index]);
			prpLpayObjectInfo.setBankName(prpLchargeBankName[index]);
			prpLpayObjectInfo.setAccountCode(prpLchargeAccountCode[index]);
			prpLpayObjectInfo.setCustomBankCode(prpLchargeCustomBankCode[index]);
			prpLpayObjectInfo.setCustomBankName(prpLchargeCustomBankName[index]);
			prpLpayObjectInfo.setCutBack(prpLchargeCutBack[index]);
			//存实际费用
			prpLpayObjectInfo.setPayAmount(prpLcharge.getChargeAmount()*prpLcharge.getExchRate());
			//證件類型
			prpLpayObjectInfo.setCertificateCode(prpLchargeCertificateCode[index]);
			remnantDto.getPrpLchargeList().add(prpLcharge);
			remnantDto.getPrpLpayObjectInfoList().add(prpLpayObjectInfo);
			prpLcompensate.setSumNoDutyFee(prpLcompensate.getSumNoDutyFee()- prpLcharge.getChargeAmount()*prpLcharge.getExchRate());
		}
		
		String TextTemp = httpServletRequest.getParameter("prpLctextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		PrpLctext prpLctext = null;
		for (int k = 0; k < rules.length; k++) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(compensateNo);
			prpLctext.setContext(rules[k]);
			prpLctext.getId().setLineNo(k + 1);
			prpLctext.getId().setTextType("1");
			remnantDto.getPrpLctextList().add(prpLctext);
		}
		prpLcompensate.setSumThisPaid(prpLcompensate.getSumDutyPaid()+ prpLcompensate.getSumPrePaid());// 實繳金額
		prpLcompensate.setSumPaid(prpLcompensate.getSumDutyPaid() + prpLcompensate.getSumNoDutyFee());// 實繳金額
		prpLcompensate.setCompensateNo(compensateNo);
		prpLcompensate.setTimes(1);
		prpLcompensate.setRemnants("1");
		prpLcompensate.setClassCode(classCode);
		prpLcompensate.setPolicyNo(prpLclaim.getPolicyNo());
		prpLcompensate.setRiskCode(riskCode);
		prpLcompensate.setClaimNo(claimNo);
		prpLcompensate.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		prpLcompensate.setMakeCom(prpLclaim.getMakeCom());
		prpLcompensate.setComCode(prpLclaim.getComCode());
		prpLcompensate.setHandlerCode(user.getUserCode());
		prpLcompensate.setOperatorCode(prpLclaim.getOperatorCode());
		prpLcompensate.setInputDate(new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
		prpLcompensate.setUnderWriteFlag("0");
		prpLcompensate.setSumPrePaid(0);
		prpLcompensate.setLflag("0");
		prpLcompensate.setCaseType("S");
		prpLcompensate.setOperatorCode(user.getUserCode());
		prpLcompensate.setFinallyFlag("0");
		prpLcompensate.setRemnants("1");
		// mantis： CLM0106 ，處理人員：BK007  蘇哲，需求單編號：CLM0106.新核心案件賠付速別預設值更改為速件
		prpLcompensate.setSpeedFlag("N"); // 默認速別為 "N"
		prpLcompensate.setExchangeRate(1d);
		remnantDto.setPrpLcompensate(prpLcompensate);
		remnantDto.setPrpLclaim(prpLclaim);
		return remnantDto;
	}

	/**
	 * 将残余物的信息展示到页面
	 * @param httpServletRequest
	 * @param compensateNo 残余物计算书号
	 * @throws Exception
	 */
	public void remnantDtoToView(HttpServletRequest httpServletRequest, String compensateNo) throws Exception {
		RemnantDto remnantDto = null;
		if(CommonUtils.isEmpty(compensateNo)){
			remnantDto = new RemnantDto();
		}else{
			remnantDto = remnantService.findByPrimaryKey(compensateNo);
		}
		String claimNo = httpServletRequest.getParameter("claimNo");
		PrpLclaim prpLclaim = remnantDto.getPrpLclaim();
		if(prpLclaim==null){
			prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
			remnantDto.setPrpLclaim(prpLclaim);
		}
		prpLclaim.setComName(this.codeService.translateComCode(prpLclaim.getHandleDept(), true));
		
		List<PrpLcompensate> prpLcompensateList = this.prpLcompensateService.findByClaimNo(claimNo);
		Double sumPaid = 0D;
		for (PrpLcompensate prpLcompensate : prpLcompensateList) {
			if (!prpLcompensate.getCompensateNo().startsWith("R") && !prpLcompensate.getCompensateNo().startsWith("S")) {// 页面的赔付总额为该立案号查到的所有计算书（除去追偿计算书和残余物计算书）的赔付金额之和
				sumPaid += prpLcompensate.getSumThisPaid();
			}
		}
		remnantDto.setSumPaid(sumPaid);
		Date nowDate = null;
		if (CommonUtils.isEmpty(remnantDto.getPrpLremnantList())) {
			nowDate = new Date();
		} else {
			nowDate = remnantDto.getPrpLremnantList().get(0).getRemnantDate();
		}
		remnantDto.setNowDate(nowDate);
		String policyNo = httpServletRequest.getParameter("policyNo");
		PrpCitemCarId prpCitemCarId = new PrpCitemCarId();
		prpCitemCarId.setPolicyNo(policyNo);
		prpCitemCarId.setItemNo(1);
		PrpCitemCar prpCitemCar = this.prpCitemCarService.findPrpCitemCar(prpCitemCarId);
		PrpLcompensate prpLcompensate = remnantDto.getPrpLcompensate();
		String itemNoStr = "";
		if (prpLcompensate == null) {// 如果找不到计算书
			String strCondition = " 1=1 and claimNo='" + claimNo + "' and compensateNo like 'S%' ORDER BY CompensateNo DESC";
			List<PrpLcompensate> itemNoList = prpLcompensateService.findByConditions(strCondition);
			if (itemNoList.size() > 0) {
				PrpLcompensate temp = (PrpLcompensate) itemNoList.get(0);
				itemNoStr = temp.getCompensateNo().substring(claimNo.length() + 1);
				remnantDto.setItemNo(String.valueOf(DataUtils.getInteger(itemNoStr)+1));
			}else{
				remnantDto.setItemNo("1");
			}
		} else {
			itemNoStr = remnantDto.getPrpLcompensate().getCompensateNo().substring(claimNo.length() + 1);
			remnantDto.setItemNo(String.valueOf(DataUtils.getInteger(itemNoStr)));
		}
		
		this.setSubInfo(httpServletRequest,remnantDto);
		this.setSelectionList(httpServletRequest, prpLclaim);
		httpServletRequest.setAttribute("prpLClaim", prpLclaim);
		httpServletRequest.setAttribute("remnantDto", remnantDto);
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		httpServletRequest.setAttribute("prpCitemCar", prpCitemCar);
		
		String editType = httpServletRequest.getParameter("editType");
		//审核讯息
		String flowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		if("show".equals(editType)){
			flowID = this.getWorkFlowService().findViewFlowIDBybusinessNo(compensateNo);
		}
		if (DataUtils.dbNullToEmpty(flowID).length() > 0) {
			boolean flag = "1".equals(prpLcompensate.getUnderWriteFlag());
			List<SwfLog> swfLogList = this.getWorkFlowService().findSwfLogWithNotion(flowID, flag);
			httpServletRequest.setAttribute("swfLogList", swfLogList);
			String logNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logNo
			if (DataUtils.dbNullToEmpty(logNo).length() > 0) {
				SwfLog swfLog = null;
				if (flag) {
					swfLog = this.getWorkFlowService().findSwfLogStoreDtoByPrimaryKey(flowID, Integer.parseInt(logNo)).toSwfLog();
				} else {
					swfLog = this.getWorkFlowService().findByPrimaryKey(flowID, Integer.parseInt(logNo));
				}
				httpServletRequest.setAttribute("swfLog", swfLog);
				if("undwrt".equals(editType)){
					UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
					this.checkPower(user,httpServletRequest,swfLog);
				}
			}
		}
	}
	/**
	 * 设置字表的讯息
	 * @param reqeust
	 * @param remnantDto
	 */
	public void setSubInfo(HttpServletRequest reqeust,RemnantDto remnantDto){
		if(!CommonUtils.isEmpty(remnantDto.getPrpLpayObjectInfoList())){
			List<PrpLpayObjectInfo> objectInfoList = new ArrayList<PrpLpayObjectInfo>();
			String kindName = null;
			for(PrpLpayObjectInfo info : remnantDto.getPrpLpayObjectInfoList()){
				if(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(info.getId().getCertiType())){
					objectInfoList.add(info);
				}else{
					for(PrpLcharge prpLcharge : remnantDto.getPrpLchargeList()){
						if(CommonUtils.isEmpty(prpLcharge.getKindName())){
							kindName = this.codeService.translateKindCode(prpLcharge.getRiskCode(), prpLcharge.getKindCode(), true);
							prpLcharge.setKindName(kindName);
						}
						if(prpLcharge.getId().getSerialNo().intValue() == info.getId().getSerialNo().intValue()){
							prpLcharge.setPrpLpayObjectInfo(info);
							break;
						}
					}
				}
			}
			remnantDto.setPrpLpayObjectInfoList(objectInfoList);
		}
		PrpLctext prpLctext = new PrpLctext();
		StringBuffer context = new StringBuffer("");
		if(!CommonUtils.isEmpty(remnantDto.getPrpLctextList())){
			for(PrpLctext temp : remnantDto.getPrpLctextList()){
				prpLctext.setId(temp.getId());
				context.append(temp.getContext());
			}
		}
		prpLctext.setContext(context.toString());
		remnantDto.setPrpLctext(prpLctext);
	}
	/***
	 * 校驗審核權限
	 * @param user
	 * @param prpLcompensate
	 * @throws Exception 
	 */
	public void checkPower(UserDto user, HttpServletRequest httpServletRequest, SwfLog currSwfLog) throws Exception {
		// String gradeCode = "";'006'
		// 業管中心科長,'009'部門理賠科長,'010'部門經理,'011'體系主管,'012'總經理,'013'董事長
		// 查询理赔岗位
		List<String> list = this.utiUserGradeService.findGradeCodeByUserCode(user.getUserCode());
		if (list == null || list.isEmpty()) {
			throw new UserException(1, 3, "殘餘物審核", "您無可審核的崗位！");
		}
		int level = 0;
		if(list.contains("013")){
			level = 5;
		}else if(list.contains("012")){
			level = 4;
		}else if(list.contains("011")){
			level = 3;
		}else if(list.contains("010")){
			level = 2;
		}else if(list.contains("009")){
			level = 1;
		}
		int nodeNo = 0;
		if(currSwfLog!=null){
			nodeNo = currSwfLog.getNodeNo();
		}
		if(nodeNo>level){
			throw new UserException(1, 3, "殘餘物審核", "您無權限審核，審核權限為："+currSwfLog.getNodeName()+"！");
		}
		RemnantDto remnantDto = (RemnantDto) httpServletRequest.getAttribute("remnantDto");
		Boolean submitSuperior = true;
		if(nodeNo>=2&&remnantDto.getPrpLbuyerList().size()<1&&remnantDto.getPrpLremnantList().size()<1){
			submitSuperior = false;
		}
		httpServletRequest.setAttribute("submitSuperior", submitSuperior);
		String flowID = httpServletRequest.getParameter("swfLogFlowID");
		if (DataUtils.emptyToNull(DataUtils.dbNullToEmpty(flowID)) != null) {
			String logNo = httpServletRequest.getParameter("swfLogLogNo");
			SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
			if (swfLogDto.getHoldNode() == false) {
				String msg = "該任務已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所占用,請選擇其它該任務進行處理!";
				throw new UserException(1, 3, "殘餘審批", msg);
			}
			
		}
//		} else {
//			double sumLoss = 0d;// 法務預估
//			// 法務預估
//			List<PrpLloss> lossList = this.prpLlossService.findByConditions(" compensateNo = 'R" + prpLcompensate.getClaimNo() + "00' order by serialNo asc ");
//			for (PrpLloss p : lossList) {
//				sumLoss += p.getSumLoss();
//			}
//			ReplevyRuleCondition condition = new ReplevyRuleCondition();
//			condition.setLevel("1");// 无流程数据时，默认当前1级，兼容旧数据
//			if (currSwfLog != null) {
//				condition.setLevel(String.valueOf(currSwfLog.getNodeNo()));
//			}
//			if (prpLcompensate.getCompensateNo().endsWith("00")) {// 追償協商審核
//				List<PrpLloss> tempList = compensateService.getPrpLlossForReplevy(prpLcompensate.getClaimNo());
//				double sumDefPay = 0d;
//				for (PrpLloss p : tempList) {
//					sumDefPay += p.getSumDefPay();
//				}
//				condition.setSumRealPay(sumLoss);// 協商規則，該值代表法務預估金額總和
//				condition.setSumLoss(sumDefPay);// 協商規則，該值代表賠款金額總和
//			} else if ("7".equals(prpLcompensate.getPaySituation())) {
//				// 追償給付類型為‘費用’，不需要審核權限,以一万元为限，
//				String conditions = " caseType = 'R' And compensateNo like 'R" + prpLcompensate.getClaimNo() + "%' and UnderWriteFlag = '1' Order By times Desc";
//				QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
//				List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findPrpLcompensate(queryRule);
//				double sumNoDutyFee = prpLcompensate.getSumNoDutyFee();
//				if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
//					for (PrpLcompensate p : prpLcompensateList) {
//						sumNoDutyFee += p.getSumNoDutyFee();
//					}
//				}
//				condition.setChargeAmount(sumNoDutyFee);
//				condition.setSumRealPay(0);
//				condition.setSumLoss(0);
//			} else {// 一般追償審核
//				double sumRealPay = prpLcompensate.getSumThisPaid();// 實際追償
//				// 总期数,,本次追償為分次追償
//				if (!"4".equals(prpLcompensate.getPaySituation())) {
//					String conditions = " caseType = 'R' And compensateNo like 'R" + prpLcompensate.getClaimNo() + "%' and UnderWriteFlag = '1' Order By times Desc";
//					QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
//					List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findPrpLcompensate(queryRule);
//					if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
//						for (PrpLcompensate p : prpLcompensateList) {
//							sumRealPay += p.getSumThisPaid();
//						}
//					}
//				} else {
//					condition.setTotalTimes(prpLcompensate.getTotalTimes());
//				}
//				condition.setSumRealPay(sumRealPay);
//				condition.setSumLoss(sumLoss);
//
//			}
//			for (int i = 0; i < list.size(); i++) {
//				condition.getGradeCodes().add(String.valueOf(list.get(i)));
//			}
//			try {
//				droolsRuleService.executeRules("undwrtRuleFlow", "undwrtChangeSet.xml", condition);
//				if (!condition.getResult()) {
//					throw new UserException(-1, -1, "權限效驗失敗", condition.getResultMessage());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				if (e instanceof UserException) {
//					throw e;
//				}
//				throw new UserException(-1, -1, "權限效驗失敗", e.getMessage());
//			}
//		}
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}

	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpCitemCarService getPrpCitemCarService() {
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public PrpLbuyerService getPrpLbuyerService() {
		return prpLbuyerService;
	}

	public void setPrpLbuyerService(PrpLbuyerService prpLbuyerService) {
		this.prpLbuyerService = prpLbuyerService;
	}

	public PrpLremnantService getPrpLremnantService() {
		return prpLremnantService;
	}

	public void setPrpLremnantService(PrpLremnantService prpLremnantService) {
		this.prpLremnantService = prpLremnantService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public RemnantService getRemnantService() {
		return remnantService;
	}

	public void setRemnantService(RemnantService remnantService) {
		this.remnantService = remnantService;
	}

	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

}
