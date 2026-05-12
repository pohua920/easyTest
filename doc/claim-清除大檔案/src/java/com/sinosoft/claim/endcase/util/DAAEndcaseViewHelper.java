package com.sinosoft.claim.endcase.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLguarantee;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLguaranteeService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.ui.control.facade.UIClaimFittingsSaveFacade;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.payment.payment.model.PrpJpayRefRec;
import com.sinosoft.payment.payment.service.facade.PrpJpayRefRecService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: EndcaseViewHelper
 * </p>
 * <p>
 * Description:结案ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 201
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class DAAEndcaseViewHelper extends EndcaseViewHelper {
	/** 重开赔案服务 */
	private RecaseService recaseService;
	/** 重开赔案信息服务 */
	private PrpLrecaseService prpLrecaseService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 报案服务 */
	private RegistService registService;
	/** 代码服务 */
	private CodeService codeService;
	/** 结案服务 */
	private EndcaseService endcaseService;
	/** 担保信息服务 */
	private PrpLguaranteeService prpLguaranteeService;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 用户基本信息服务 */
	private PrpDuserService prpDuserService;
	/** 预赔登记信息服务 */
	private PrpLprepayService prpLprepayService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 单号取号服务 */
	private BillService billService;
	/** 实收实付信息服务 */
	private PrpJpayRefRecService prpJpayRefRecService;

	/**
	 * 默认构造方法
	 */
	public DAAEndcaseViewHelper() {
	}

	/**
	 * 保存结案时结案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return endcaseDto 结案数据传输数据结构
	 * @throws Exception
	 */
	public EndcaseDto viewToDto(HttpServletRequest httpServletRequest, boolean stepFlag) throws Exception {
		// 继承对endcase,endcaseText表的赋值
		EndcaseDto endcaseDto = super.viewToDto(httpServletRequest, stepFlag);
		String claimNo1 = (String) httpServletRequest.getParameter("prpLclaimClaimNo"); // 赔案号
		String caseNo1 = (String) httpServletRequest.getAttribute("caseNo"); // 陪案号
		// 取得立案信息
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo1.trim());
		// 根据查询出来的数据内容，给PrpLcaseNoDto赋值
		PrpLclaim prpLclaim = new PrpLclaim();
		prpLclaim = claimDto.getPrpLclaim();
		// 取得当前用户信息，写操作员信息到结案中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		if (this.isRecase(claimNo1) == false) { // 如果是重开赔案的结案，不回写立案表，这里照原样赋值，相当於没有重写
			if (stepFlag == true) {
				if (caseNo1 != null && caseNo1.length() > 1) {
					prpLclaim.setCaseNo(caseNo1.trim());
				} else {
					prpLclaim.setCaseNo(null);
				}
				// 因为结案时间是不能被改变的。。所以只在保存的时候生成一次时间，如是赔案号生成，就不需要修改
				prpLclaim.setCaseType("2"); // 设置案件类型 2为正常结案
				prpLclaim.setEndCaserCode(user.getUserCode());
				prpLclaim.setEndCaseDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				endcaseDto.setPrpLclaim(prpLclaim);
			}
			if (stepFlag == true && caseNo1 != null) {
				// 取得赔款计算书信息
				QueryRule queryRule = QueryRule.getInstance().addEqual("claimNo", claimNo1).addNotEqual("caseType", "E");
				List<PrpLcompensate> arrayCompensate = prpLcompensateService.findPrpLcompensate(queryRule);
				for (int j = 0; j < arrayCompensate.size(); j++) {
					PrpLcompensate prpLcompensate = null;
					prpLcompensate = (PrpLcompensate) arrayCompensate.get(j);
					prpLcompensate.setCaseNo(caseNo1);
				}
				endcaseDto.setPrpLcompensateList(arrayCompensate);
			}

		}// 非重开赔案
			// 重开赔案的需回写 prplrecase表
		else {
			int maxSerialNo = 0;
			ReCaseDto reCaseDto = new ReCaseDto();
			maxSerialNo = recaseService.getMaxSerialNo(claimNo1);
			reCaseDto = recaseService.findByPrimaryKey(claimNo1, maxSerialNo);
			PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
			prpLrecase.setCloseCaseDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLrecase.setCloseCaseUserCode(user.getUserCode());
			endcaseDto.setPrpLrecase(prpLrecase);
			endcaseDto.setPrpLclaim(prpLclaim);
		}
		new UIClaimFittingsSaveFacade().removeAllMap(prpLclaim.getRegistNo());
		return endcaseDto;
	}

	/**
	 * 生成陪案信息详细画面
	 * @param httpServletRequest 返回给页面的request
	 * @param caseNo 陪案号
	 * @param editType 编辑类型
	 * @param claimNo 赔案号
	 * @param certiNo 单证名称
	 * @param certiType 单证类型
	 * @throws Exception
	 */
	public void endcaseDtoView(HttpServletRequest httpServletRequest, String caseNo, String editType, String claimNo, String certiNo, String certiType) throws Exception {
		// 取得当前用户信息，写操作员信息到结案中
		EndcaseDto endcaseDto = endcaseService.findByPrimaryKey(caseNo, claimNo, certiNo, certiType);
		// 根据查询出来的数据内容，给PrpLcaseNoDto赋值
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		PrpLclaim prpLclaim = new PrpLclaim();
		prpLclaim = endcaseDto.getPrpLclaim();
		// 设置结案操作的状态为 案件修改 (正处理任务)
		if (endcaseDto.getPrpLclaimStatus() != null) {
			if (endcaseDto.getPrpLclaimStatus().getStatus().equals("7"))
				endcaseDto.getPrpLclaimStatus().setStatus("3");
			prpLcaseNo.setStatus(endcaseDto.getPrpLclaimStatus().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLcaseNo.setStatus("4");
		}
		// 给陪案文件多行列表准备数据
		PrpLltext prpLltext = new PrpLltext();
		if (endcaseDto.getPrpLltextList() != null && endcaseDto.getPrpLltextList().size() > 0) {
			prpLltext = (PrpLltext) endcaseDto.getPrpLltextList().get(0);
		}
		prpLltext.getId().setTextType("08");
		httpServletRequest.setAttribute("prpLltext", prpLltext);
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLclaim);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcaseNo);
		// 设置主结案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, endcaseDto);
		RegistDto registDto = new RegistDto();
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
	}

	/**
	 * 生成陪案信息详细画面
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public void endcaseDtoView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		// 取得报案的信息
		EndcaseDto endcaseDto = endcaseService.findByPrimaryKey(claimNo);
		// 根据查询出来的数据内容，给PrpLcaseNoDto赋值
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		PrpLclaim prpLclaim = endcaseDto.getPrpLclaim();
		// 查询报案信息
		RegistDto registDto = registService.findByPrimaryKey(prpLclaim.getRegistNo());
		// reason 强制保单关联信息写到结案中
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		prpLclaim.setClaimType(registDto.getPrpLregist().getClaimType());
		// 查询保单信息
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		prpLclaim.setPolicyNo(prpCmain.getPolicyNo());
		prpLclaim.setHandler1Code(prpCmain.getHandler1Code());
		prpLclaim.setStartDate(new DateTime(prpCmain.getStartDate(), DateTime.YEAR_TO_DAY));
		prpLclaim.setEndDate(new DateTime(prpCmain.getEndDate(), DateTime.YEAR_TO_DAY));
		prpLclaim.setComCode(prpCmain.getComCode());
		prpLclaim.setInsuredCode(registDto.getPrpLregist().getInsuredCode());
		prpLclaim.setInsuredName(registDto.getPrpLregist().getInsuredName());
		prpLclaim.setSumAmount(prpCmain.getSumAmount());
		prpLclaim.setSumPremium(prpCmain.getSumPremium());
		prpLclaim.setBusinessNature(prpCmain.getBusinessNature());
		prpLclaim.setPolicyType(prpCmain.getPolicyType());
		prpLclaim.setCurrency(prpCmain.getCurrency());
		prpLclaim.setRiskCode(prpCmain.getRiskCode());
		List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
		if (!CommonUtils.isEmpty(prpCitemCarList)) {
			// 对车型等信息的支持
			PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
			prpLclaim.setClauseType(prpCitemCar.getClauseType());
			prpLclaim.setLicenseNo(prpCitemCar.getLicenseNo());
			prpLclaim.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
			prpLclaim.setLicenseColor(prpCitemCar.getLicenseColorCode());
			prpLclaim.setBrandName(prpCitemCar.getBrandName());
			prpLclaim.setCarKindCode(prpCitemCar.getCarKindCode());
		}
		// 设置结案操作的状态为 新案件登记 (未处理任务)
		prpLcaseNo.setStatus("1");
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLclaim);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcaseNo);
		// 设置主结案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
		// 设置立案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		// 在界面上显示险种名称
		String riskCName = codeService.translateRiskCode(prpLclaim.getRiskCode(), true);
		httpServletRequest.setAttribute("riskCName", riskCName);
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, endcaseDto);
		// 查询出预赔计算书号
		PrpLprepay prpLprepay = new PrpLprepay();
		String strpreCompensateNo = "";
		List<PrpLprepay> preCompensateNoList = prpLprepayService.findByClaimNo(prpLclaim.getClaimNo());
		if (preCompensateNoList.size() > 0) {
			prpLprepay = preCompensateNoList.get(0);
		}
		strpreCompensateNo = prpLprepay.getPreCompensateNo();
		prpLcaseNo.setPreCompensateNo(strpreCompensateNo);
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
		// 查出赔款计算书号码
		String strcompensateNo = "";
		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addNotEqual("caseType", "E");
		queryRule.addSql("(casetype not in ('E') or casetype is null)");
		queryRule.addEqual("claimNo", prpLclaim.getClaimNo());
		List<PrpLcompensate> CompensateNoList = prpLcompensateService.findPrpLcompensate(queryRule);
		for (int i = 0; i < CompensateNoList.size(); i++) {
			if(CompensateNoList.get(i).getCompensateNo().startsWith("C")){
				strcompensateNo = CompensateNoList.get(i).getCompensateNo();
				break;
			}
		}
		prpLcaseNo.setCompensateNo(strcompensateNo);
		// 增加尚未收回担保单证 begin
		if (!"".equals(prpLclaim.getGuaranteeFlag()) || !"0".equals(prpLclaim.getGuaranteeFlag())) {
			PrpLguarantee prplguarantee = null;
			prplguarantee = prpLguaranteeService.findPrpLguarantee(claimNo);
			if (prplguarantee == null) {
				prpLcaseNo.setNotBackCount("0");
			} else {
				try {
					int offerCountBackInt = 0;
					if (prplguarantee.getRemark().length() > 0) {
						String[] offerCountBackList = prplguarantee.getRemark().split(",");
						for (int i = 0; i < offerCountBackList.length; i++) {
							offerCountBackInt = offerCountBackInt + Integer.parseInt(DataUtils.nullToZero(offerCountBackList[i]));
						}
					} else {
						offerCountBackInt = 0;
					}
					prpLcaseNo.setNotBackCount((prplguarantee.getOfferCount() - offerCountBackInt) + "");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					prpLcaseNo.setNotBackCount("0");
				}
			}
		}
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
	}

	/**
	 * 填写结案页面及查询结案request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @param editType 编辑类型
	 * @throws Exception
	 */
	public void claimDtoToView(HttpServletRequest httpServletRequest, String claimNo, String editType) throws Exception {
		// 取得当前用户信息，写操作员信息到结案中
		DateTime.setDateDelimiter("-");
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		// 根据查询出来的数据内容，给PrpLcaseNoDto赋值
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		PrpLclaim prpLclaim = new PrpLclaim();
		prpLclaim = claimDto.getPrpLclaim();
		// 查询报案信息
		RegistDto registDto = registService.findByPrimaryKey(prpLclaim.getRegistNo());
		// reason 强制保单关联信息写到结案中
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		prpLclaim.setClaimType(registDto.getPrpLregist().getClaimType());
		// 查询保单信息
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		//PolicyDto policyDto = endorseViewHelper.findForEndorBefore(prpLclaim.getPolicyNo(), new DateTime(prpLclaim.getDamageStartDate()).toString(), prpLclaim.getDamageStartHour());
		prpLclaim.setPolicyNo(prpCmain.getPolicyNo());
		prpLclaim.setHandler1Code(prpCmain.getHandler1Code());
		prpLclaim.setStartDate(new DateTime(prpCmain.getStartDate().toString(), DateTime.YEAR_TO_DAY));
		prpLclaim.setEndDate(new DateTime(prpCmain.getEndDate().toString(), DateTime.YEAR_TO_DAY));
		prpLclaim.setComCode(prpCmain.getComCode());
		prpLclaim.setInsuredCode(prpLclaim.getInsuredCode());
		prpLclaim.setInsuredName(prpLclaim.getInsuredName());
		prpLclaim.setSumAmount(prpCmain.getSumAmount());
		prpLclaim.setSumPremium(prpCmain.getSumPremium());
		prpLclaim.setBusinessNature(prpCmain.getBusinessNature());
		prpLclaim.setPolicyType(prpCmain.getPolicyType());
		prpLclaim.setCurrency(prpCmain.getCurrency());
		prpLclaim.setRiskCode(prpCmain.getRiskCode());
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());//处理出险小时未经格式的情况
		prpLclaim.setDamageStartHour(timeTemp.substring(0, 2));
		// 如果出险地点为空，取报案出险地点
		if (prpLclaim.getDamageAddress() == null || "".equals(prpLclaim.getDamageAddress())) {
			prpLclaim.setDamageAddress(registDto.getPrpLregist().getDamageAddress());
		}
		PrpCitemCar PrpCitemCar = new PrpCitemCar();
		List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
		if (prpCitemCarList.size() > 0) {
			// 对车型等信息的支持
			PrpCitemCar = prpCitemCarList.get(0);
			prpLclaim.setClauseType(PrpCitemCar.getClauseType());
			prpLclaim.setLicenseNo(PrpCitemCar.getLicenseNo());
			prpLclaim.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
			prpLclaim.setLicenseColor(PrpCitemCar.getLicenseColorCode());
			prpLclaim.setBrandName(PrpCitemCar.getBrandName());
			prpLclaim.setCarKindCode(PrpCitemCar.getCarKindCode());
		}
		// 设置结案操作的状态为 新案件登记 (未处理任务)
		prpLcaseNo.setStatus("1");
		// 结案报告初始带出理算报告------------------------------------
		String tempContext = "";
		EndcaseDto endcaseDto = endcaseService.findByPrimaryKey(claimNo);
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		CompensateDto compensateDto = new CompensateDto();
		int compListSize = endcaseDto.getPrpLcompensateList().size();
		if (endcaseDto.getPrpLcompensateList() != null && compListSize > 0) {
			for (int i = 0; i < compListSize; i++) {
				prpLcompensate = (PrpLcompensate) endcaseDto.getPrpLcompensateList().get(i);
				compensateDto = compensateService.findByPrimaryKey(prpLcompensate.getCompensateNo());
				if (compensateDto.getPrpLctextList() != null) {
					Iterator<PrpLctext> iterator = compensateDto.getPrpLctextList().iterator();
					while (iterator.hasNext()) {
						PrpLctext prpLctextTemp = (PrpLctext) iterator.next();
						if (prpLctextTemp.getId().getTextType().equals("1")) {
							tempContext = tempContext + prpLctextTemp.getContext();
						}
					}
				}

			}
		}
		// 设值文本的内容
		List<PrpLltext> arraylTextList = new ArrayList<PrpLltext>();
		PrpLltext prpLltext = new PrpLltext();
		prpLltext.getId().setTextType("08");
		prpLltext.setContext(tempContext);
		prpLltext.setLtextList(arraylTextList);
		httpServletRequest.setAttribute("prpLltext", prpLltext);
		// 结案时，回写立案表的总赔付金额
		double sumPaid = 0; // 总赔付金额
		// 核赔通过的赔款计算书才计算在内
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo);
		queryRule.addSql("(UnderWriteFlag = '1' OR UnderWriteFlag = '3')");
		Collection<PrpLcompensate> compensateListTemp = prpLcompensateService.findPrpLcompensate(queryRule);
		Iterator<PrpLcompensate> iTemp = compensateListTemp.iterator();
		while (iTemp.hasNext()) {
			PrpLcompensate prpLcompensateTemp = (PrpLcompensate) iTemp.next();
			String tempCompeNo = prpLcompensateTemp.getCompensateNo();
			if(!("R"+claimNo+"00").equals(tempCompeNo)){
				sumPaid = sumPaid + prpLcompensateTemp.getSumPaid();//除去追償登錄計算書的
			}
		}
		// 审核通过後，回写立案总赔付金额信息,去掉追偿部分的内容
		if (compensateDto.getPrpLcompensate() != null) {
			prpLclaim.setSumPaid(sumPaid - prpLclaim.getSumReplevy());
			prpLcaseNo.setCompensateNo(compensateDto.getPrpLcompensate().getCompensateNo());
		}
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLclaim);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcaseNo);
		// 设置立案信息内容到窗体表单
		// 如果DamageAreaName为空则通过DamageAreaCode重新得到DamageAreaName，並把它付给DTO，再传给页面
		if (("".equals(prpLclaim.getDamageAreaName())) || (prpLclaim.getDamageAreaName() == null)) {
			prpLclaim.setDamageAreaName(codeService.translateCode("DamageAreaCode", prpLclaim.getDamageAreaCode(), ConstantCodes.Language.CHINESE));
		}
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", codeService.translateRiskCode(prpLclaim.getRiskCode(), true));// 待调整险种翻译
		// 查询出预赔计算书号
		PrpLprepay prpLprepay = new PrpLprepay();
		String strpreCompensateNo = "";
		List<PrpLprepay> preCompensateNoList = prpLprepayService.findByClaimNo(prpLclaim.getClaimNo());
		if (preCompensateNoList.size() > 0) {
			prpLprepay = preCompensateNoList.get(0);
		}
		strpreCompensateNo = prpLprepay.getPreCompensateNo();
		prpLcaseNo.setPreCompensateNo(strpreCompensateNo);
		if (!"".equals(prpLclaim.getGuaranteeFlag()) || !"0".equals(prpLclaim.getGuaranteeFlag())) {
			PrpLguarantee prplguarantee = null;
			prplguarantee = prpLguaranteeService.findPrpLguarantee(claimNo);
			if (prplguarantee == null) {
				prpLcaseNo.setNotBackCount("0");
			} else {
				try {
					int offerCountBackInt = 0;
					if (prplguarantee.getRemark().length() > 0) {
						String[] offerCountBackList = prplguarantee.getRemark().split(",");
						for (int i = 0; i < offerCountBackList.length; i++) {
							offerCountBackInt = offerCountBackInt + Integer.parseInt(DataUtils.nullToZero(offerCountBackList[i]));
						}
					} else {
						offerCountBackInt = 0;
					}
					prpLcaseNo.setNotBackCount((prplguarantee.getOfferCount() - offerCountBackInt) + "");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					prpLcaseNo.setNotBackCount("0");
				}
			}
		}
		// 设置主结案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);

	}

	/**
	 * 根据赔案号和报案号查询结案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 赔案号
	 * @param claimNo 报案号
	 * @throws Exception
	 */
	public void setPrpLendcaseDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {
		// caseNO,policyNo,claimNo
		// 根据输入的保单号，结案号生成SQL where 子句
		String caseNo = StringUtils.rightTrim(workFlowQueryDto.getCaseNo());
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String comCode = StringUtils.rightTrim(workFlowQueryDto.getComCode());
		String claimDate = StringUtils.rightTrim(workFlowQueryDto.getClaimDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("claimNo", claimNo, workFlowQueryDto.getClaimNoSign()));
		conditions.append(StringConvert.convertString("caseNo", caseNo, workFlowQueryDto.getCaseNoSign()));
		conditions.append(StringConvert.convertString("policyno", policyNo, workFlowQueryDto.getPolicyNoSign()));
		conditions.append(StringConvert.convertString("licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign()));
		conditions.append(StringConvert.convertString("comCode", comCode, workFlowQueryDto.getComCodeSign()));
		conditions.append(StringConvert.convertString("insuredName", insuredName, workFlowQueryDto.getInsuredNameSign()));
		if (claimDate != null && !claimDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("claimDate", claimDate, workFlowQueryDto.getClaimDateSign()));
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("endcaseDate", operateDate, workFlowQueryDto.getOperateDateSign()));
		}
		conditions.append("and endCaserCode is not null and endCaseDate is not null");
		// 查询立案信息
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions.toString());
		List<PrpLclaim> endcaseList = prpLclaimService.findPrpLclaim(queryRule);
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		prpLcaseNo.setCaseList(endcaseList);
		prpLcaseNo.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
	}

	public Page setPrpLendcaseDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto, int pageNo, int pageSize) throws Exception {
		String caseNo = StringUtils.rightTrim(workFlowQueryDto.getCaseNo());
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String comCode = StringUtils.rightTrim(workFlowQueryDto.getComCode());
		String claimDate = StringUtils.rightTrim(workFlowQueryDto.getClaimDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("claimNo", claimNo, workFlowQueryDto.getClaimNoSign()));
		conditions.append(StringConvert.convertString("caseNo", caseNo, workFlowQueryDto.getCaseNoSign()));
		conditions.append(StringConvert.convertString("policyno", policyNo, workFlowQueryDto.getPolicyNoSign()));
		conditions.append(StringConvert.convertString("licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign()));
		conditions.append(StringConvert.convertString("comCode", comCode, workFlowQueryDto.getComCodeSign()));
		conditions.append(StringConvert.convertString("insuredName", insuredName, workFlowQueryDto.getInsuredNameSign()));
		conditions.append(StringConvert.convertString("registNo", registNo, workFlowQueryDto.getRegistNoSign()));
		if (claimDate != null && !claimDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("claimDate", claimDate, workFlowQueryDto.getClaimDateSign()));
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("endcaseDate", operateDate, workFlowQueryDto.getOperateDateSign()));
		}
		conditions.append("and endCaserCode is not null and endCaseDate is not null");
		/***业务表查询不再限制机构  delete by chenjie 20130614 start*/
//		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
//		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
//		conditions.append(uiPowerInterface.addPower(userDto, "PrpLclaim", "", "ComCode"));
		/***业务表查询不再限制机构  delete by chenjie 20130614 end*/
		Page page = prpLclaimService.findByConditions(conditions.toString(), pageNo, pageSize);
		String endCaserCode = null;
		String endCaserName = null;
		for (int i = 0; i < page.getResult().size(); i++) {
			PrpLclaim prpLclaim = (PrpLclaim) page.getResult().get(i);
			endCaserCode = prpLclaim.getEndCaserCode();
			if (endCaserCode != null && !"".equals(endCaserCode)) {
				endCaserName = prpDuserService.findPrpDuser(endCaserCode).getUserName();
				prpLclaim.setEndCaserName(endCaserName);
			}
		}
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		prpLcaseNo.setCaseList(page.getResult());
		prpLcaseNo.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
		return page;
	}

	/**
	 * 根据endcaseDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param endcaseDto 结案的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, EndcaseDto endcaseDto) throws Exception {
		// 给报案文件多行列表准备数据
		PrpLltext prpLltext = new PrpLltext();
		String tempContext = "";
		if (endcaseDto.getPrpLltextList() != null) {
			Iterator<PrpLltext> iterator = endcaseDto.getPrpLltextList().iterator();
			while (iterator.hasNext()) {
				PrpLltext prpLltextTemp = iterator.next();
				tempContext = tempContext + prpLltextTemp.getContext();
			}
		}
		prpLltext.setContext(tempContext);
		prpLltext.getId().setTextType("08");
		httpServletRequest.setAttribute("prpLltext", prpLltext);
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNo 结案的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLcaseNo prpLcaseNo) throws Exception {
		// 结案性质列表
		List<PrpDcode> endcaseNatures = codeService.getCodeType("EndcaseNature", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("endcaseNatures", endcaseNatures);
		// 赔案类别
		List<PrpDcode> caseCodes = codeService.getCodeType("CaseCode", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("caseCodes", caseCodes);
		// 出险地点分类
		List<PrpDcode> damageAddresss = codeService.getCodeType("DamageAddress", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("damageAddresss", damageAddresss);
		// 事故赔偿责任
		List<PrpDcode> indemnityDutys = codeService.getCodeType("IndemnityDuty", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("indemnityDutys", indemnityDutys);

		// 得到实赔类型列表
		List<PrpDcode> reportTypes = codeService.getCodeType("ReportType", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// 得到案件种类列表列表
		List<PrpDcode> claimTypes = codeService.getCodeType("CaseCode", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// 得到出险地址类型列表
		List<PrpDcode> damageAddressTypes = codeService.getCodeType("DamageAddress", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// 得到车辆种类列表
		List<PrpDcode> carKindCodes = codeService.getCodeType("CarKind", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// 得到车牌底色列表
		List<PrpDcode> licenseColorCode = codeService.getCodeType("LicenseColor", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// 得到赔偿责任列表
		List<PrpDcode> indemnityDuty = codeService.getCodeType("IndemnityDuty", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("indemnityDutys", indemnityDuty);
		// 得到赔案类别列表
		List<PrpDcode> escapeFlags = codeService.getCodeType("CaseCode", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("escapeFlags", escapeFlags);
		// 得到得到性别
		List<PrpDcode> driverSex = codeService.getCodeType("SexCode", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// 得到职业分类
		List<PrpDcode> driverOccupation = codeService.getCodeType("Occupation", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("driverOccupations", driverOccupation);
		// 得到文化程度
		List<PrpDcode> education = codeService.getCodeType("Education", prpLcaseNo.getRiskCode());
		httpServletRequest.setAttribute("educations", education);
	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNoDto 结案的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, PrpLclaim prpLclaim) throws Exception {
		// 业务性质
		String businessNature = prpLclaim.getBusinessNature();
		String businessNatureName = codeService.translateCodeCode("BusinessNature", businessNature, true);
		prpLclaim.setBusinessNature(businessNatureName);
		// 业务性质
		String language = prpLclaim.getLanguage();
		String languageName = codeService.translateCodeCode("Language", language, true);
		prpLclaim.setLanguage(languageName);

		// (1)条款名称的转换
		String clauseType = prpLclaim.getClauseType();
		String clauseName = codeService.translateCodeCode("ClauseType", clauseType, true);
		prpLclaim.setClauseName(clauseName);
		// (2)号牌颜色转换
		String licenseColorCodeCode = prpLclaim.getLicenseColorCode();
		String licenseColor = codeService.translateCodeCode("LicenseColor", licenseColorCodeCode, true);
		prpLclaim.setLicenseColor(licenseColor);
		// (3)车辆类型转换
		String carKindCode = prpLclaim.getCarKindCode();
		String carKind = codeService.translateCodeCode("CarKind", carKindCode, true);
		prpLclaim.setCarKind(carKind);
		// (4)赔偿责任类型转换
		String indemnityDuty = prpLclaim.getIndemnityDuty();
		String indemnityDutyName = codeService.translateCodeCode("IndemnityDuty", indemnityDuty, true);
		prpLclaim.setIndemnityDutyName(indemnityDutyName);
		// (5)赔案类别转换
		String claimType = prpLclaim.getEscapeFlag();
		if (claimType != null && claimType.trim().length() > 0) {
			claimType = new Character(claimType.charAt(0)).toString();
			String claimTypeName = codeService.translateCodeCode("CaseCode", claimType, true);
			prpLclaim.setClaimTypeName(claimTypeName);
		}
		// (6)对业务归属结构进行转换
		String comCode = prpLclaim.getComCode();
		String comName = codeService.translateComCode(comCode, true);
		prpLclaim.setComName(comName);
		// (7)理赔登记机构名称进行转换
		String makeCom = prpLclaim.getMakeCom();
		String makeComName = codeService.translateComCode(makeCom, true);
		prpLclaim.setMakeComName(makeComName);
		// (8)对归属业务员进行转换
		String handler1Code = prpLclaim.getHandler1Code();
		String handler1Name = codeService.translateUserCode(handler1Code, true);
		prpLclaim.setHandler1Name(handler1Name);
		// (9)对代理人进行转换
		String agentCode = prpLclaim.getAgentCode();
		String agentName = codeService.translateUserCode(agentCode, true);
		prpLclaim.setAgentName(agentName);
		// (10)对经办人进行转换
		String handlerCode = prpLclaim.getHandlerCode();
		String handlerName = codeService.translateUserCode(handlerCode, true);
		prpLclaim.setHandlerName(handlerName);
		// (11)对操作员名称进行转换
		String operatorCode = prpLclaim.getOperatorCode();
		String userName = codeService.translateUserCode(operatorCode, true);
		prpLclaim.setOperatorName(userName);
		// (12)对结案员名称进行转换
		String endCaserCode = prpLclaim.getEndCaserCode();
		String endCaserName = codeService.translateUserCode(endCaserCode, true);
		prpLclaim.setEndCaserName(endCaserName);
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写结案单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public EndcaseDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		EndcaseDto endcaseDto = new EndcaseDto();
		return endcaseDto;
	}

	/**
	 * 填写结案页面及查询结案request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param endcaseDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, EndcaseDto endcaseDto) throws Exception {
	}

	/**
	 * 检查是否还有未通过的赔款计算书 返回值 0：没有赔款计算书 -1:还有未通过的赔款计算书 1：所有的赔款计算书都已经通过 2:垫付结案
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public int checkCompensate(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		// 取得赔款计算书信息
		int compensateFlag = 1;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo.trim());
		queryRule.addLike("compensateNo","C%");
		ArrayList<PrpLcompensate> arraylist = (ArrayList<PrpLcompensate>) prpLcompensateService.findPrpLcompensate(queryRule);
		if (arraylist == null || arraylist.size() < 1) {
			// return 0;
			// 如果是垫付情况下，应该允许结案的。所以还需要判断是否有垫付的情况。
			queryRule.addEqual("caseType", "8");
			List<PrpLprepay> prepayList = prpLprepayService.findPrpLprepay(queryRule);
			compensateFlag = 0;
			if (prepayList != null && prepayList.size() > 0) {
				compensateFlag = 2; // 如果有垫付的情况，可以进行结案操作。
			}
		}
		if (arraylist != null) {
			for (int i = 0; i < arraylist.size(); i++) {
				PrpLcompensate prpLcompensate = null;
				prpLcompensate = (PrpLcompensate) arraylist.get(i);
				if (!(prpLcompensate.getUnderWriteFlag().equals("1") || prpLcompensate.getUnderWriteFlag().equals("3"))) {
					compensateFlag = -1;
				}
			}
		}
		return compensateFlag;
	}
	/**
	 * 交验是否已经录入支付信息，没有的话不能结案（由於特殊情况没有遵循层次调用之间的约定。）
	 * 结案前判断共保的案件是否还有未回摊的收付信息 begin
	 * @param httpServletRequest
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpJpayRefRec> checkCoinsFlag(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		String sql = "select *  from PrpJpayRefRec where claimno = '" + claimNo + "' and coinsflag in ('1') and payrefreason in ('M60', 'M5A', 'M61', 'M63', 'M64', 'M62', 'M65','M66', 'M67', 'M69', 'M68','M50') and planfee < 0";
		List<PrpJpayRefRec> ve =  prpJpayRefRecService.findBySqlQuery(sql);
		return ve;
	}

	// 交验是否已经录入支付信息，没有的话不能结案（由於特殊情况没有遵循层次调用之间的约定。）
	public int checkPlanFeeFlag(HttpServletRequest httpServletRequest, String claimNo) throws Exception {

		int intReturn = 1;
		String conpensateNo = "";
//		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
		List<PrpLcompensate> arraylist = prpLcompensateService.findByClaimNo(claimNo.trim());
		if (arraylist == null || arraylist.size() < 1) {
			return 0;
		}
		if (arraylist != null) {
			for (int i = 0; i < arraylist.size(); i++) {
				PrpLcompensate prpLcompensate = (PrpLcompensate) arraylist.get(i);
				conpensateNo = prpLcompensate.getCompensateNo();
				String conditions2 = "select *  from PrpJplanFee where  certiNo='" + conpensateNo + "'";
				List<PrpJpayRefRec> planFee = prpJpayRefRecService.finBySql(conditions2);
				if (planFee == null) {
					intReturn = 0;
					break;
				} else {
					intReturn = 1;

				}
			}
		}
		return intReturn;
	}

	/**
	 * 生成陪案号的功能
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public PrpLcaseNo compensateToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		String caseNo = ""; // 赔案号
		String policyNo = ""; // 保单号
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");

		// 生成赔案号
		caseNo = getCaseNo(claimNo, user.getComCode());

		PrpLcaseNo prpLcaseNoList = new PrpLcaseNo();
		List<PrpLcaseNo> arrayCaseNo = new ArrayList<PrpLcaseNo>();
		List<PrpLcompensate> arraylist = prpLcompensateService.findByClaimNo(claimNo.trim());
		if (arraylist != null) {
			for (int i = 0; i < arraylist.size(); i++) {
				PrpLcompensate prpLcompensate = new PrpLcompensate();
				prpLcompensate = (PrpLcompensate) arraylist.get(i);
				PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
				prpLcaseNo.getId().setCertiNo(prpLcompensate.getCompensateNo());
				prpLcaseNo.setSumPaid(prpLcompensate.getSumPaid());

				prpLcaseNo.getId().setCaseNo(caseNo);
				prpLcaseNo.getId().setCertiType("C");
				prpLcaseNo.setFlag("");
				prpLcaseNo.setClaimNo(claimNo.trim());
				policyNo = prpLcompensate.getPolicyNo();
				arrayCaseNo.add(prpLcaseNo);
			}
		}
		prpLcaseNoList.setCaseList(arrayCaseNo);
		prpLcaseNoList.setPolicyNo(policyNo);
		prpLcaseNoList.setClaimNo(claimNo);
		// 使用最後一个陪案号码
		prpLcaseNoList.getId().setCaseNo(caseNo);
		httpServletRequest.getSession().setAttribute("prpLcaseNo", prpLcaseNoList);
		return prpLcaseNoList;
	}

	/**
	 * 生成结案号
	 * @param claimNo
	 * @param comCode
	 * @return
	 * @throws Exception
	 */
	public String getCaseNo(String claimNo, String comCode) throws Exception {
		String tableName = "prplcaseno";
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("damageCode",prpLclaim.getDamageCode());
		infoMap.put("policyNo",prpLclaim.getPolicyNo());
		String caseNo = billService.getNoByPolciyYear(tableName, prpLclaim.getRiskCode(),infoMap);
		return caseNo;
	}

	/**
	 * 根据赔案号,报案号,案件状态，车牌号码，操作时间查询结案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 赔案号
	 * @param claimNo 报案号
	 * @throws 增加车牌号，案件状态，操作时间查询条件
	 */
	public void setPrpLendcaseToView(HttpServletRequest httpServletRequest, String caseNo, String claimNo, String policyNo, String licenseNo, String status, String operateDate) throws Exception {
		// caseNO,policyNo,claimNo
		// 根据输入的保单号，结案号生成SQL where 子句
		caseNo = StringUtils.rightTrim(caseNo);
		claimNo = StringUtils.rightTrim(claimNo);
		policyNo = StringUtils.rightTrim(policyNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		status = StringUtils.rightTrim(status);
		operateDate = StringUtils.rightTrim(operateDate);
		String strSign = httpServletRequest.getParameter("OperateDateSign");
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("PrpLclaim.claimNo", claimNo, httpServletRequest.getParameter("ClaimNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLclaim.caseNo", caseNo, httpServletRequest.getParameter("CaseNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLclaim.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLRegist.licenseNo", licenseNo, httpServletRequest.getParameter("LicenseNoSign"));
		conditions = conditions + StringConvert.convertDate("PrpLclaim.EndCaseDate", operateDate, strSign);
		conditions = conditions + " and PrpLclaim.endCaserCode is not null and PrpLclaim.endCaseDate is not null";
		// 拼权限
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "prplclaim", "", "ComCode");
		// 查询立案信息
		// 得到多行结案主表信息
		List<PrpLcaseNo> endcaseList = new ArrayList<PrpLcaseNo>();
		endcaseList = endcaseService.findByQueryConditions(conditions);
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		prpLcaseNo.setCaseList(endcaseList);
		prpLcaseNo.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
	}

	// 结案查询增加承保机构、立案时间；和上面那个函数一样，参数不一样，由於担心别的地方引用上面那个函数，所以增加重载函数。
	public void setPrpLendcaseToView(HttpServletRequest httpServletRequest, String caseNo, String claimNo, String policyNo, String licenseNo, String status, String operateDate, String comCode, String claimDate) throws Exception {
		caseNo = StringUtils.rightTrim(caseNo);
		claimNo = StringUtils.rightTrim(claimNo);
		policyNo = StringUtils.rightTrim(policyNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		status = StringUtils.rightTrim(status);
		operateDate = StringUtils.rightTrim(operateDate);
		String strSign = httpServletRequest.getParameter("OperateDateSign");
		comCode = StringUtils.rightTrim(comCode);
		claimDate = StringUtils.rightTrim(claimDate);
		String claimDateSign = httpServletRequest.getParameter("claimDateSign");
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("PrpLclaim.claimNo", claimNo, httpServletRequest.getParameter("ClaimNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLclaim.caseNo", caseNo, httpServletRequest.getParameter("CaseNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLclaim.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLRegist.licenseNo", licenseNo, httpServletRequest.getParameter("LicenseNoSign"));
		conditions = conditions + StringConvert.convertString("PrpLclaim.comCode", comCode, httpServletRequest.getParameter("comCodeSign"));
		conditions = conditions + StringConvert.convertDate("PrpLclaim.EndCaseDate", operateDate, strSign);
		conditions = conditions + StringConvert.convertDate("PrpLclaim.inputDate", claimDate, claimDateSign);
		conditions = conditions + " and PrpLclaim.endCaserCode is not null and PrpLclaim.endCaseDate is not null";
		// 拼权限
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "prplclaim", "", "ComCode");
		// 查询立案信息
		// 得到多行结案主表信息
		List<PrpLcaseNo> endcaseList = new ArrayList<PrpLcaseNo>();
		endcaseList = endcaseService.findByQueryConditions(conditions);
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		prpLcaseNo.setCaseList(endcaseList);
		prpLcaseNo.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcaseNo", prpLcaseNo);
	}

	/**
	 * 查询结案
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkEndcase(String claimNo) throws Exception {
		boolean blnReturn = false;
		// 查询立案信息
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		if (prpLclaim != null && prpLclaim.getClaimNo() != null) {
			if (prpLclaim.getEndCaserCode() != null && prpLclaim.getEndCaserCode().trim().length() > 0) {

				blnReturn = true;
			}
		}
		return blnReturn;
	}

	/**
	 * 根据立案号出现重开赔案
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public boolean checkRecase(String claimNo) throws Exception {
		boolean blnReturn = false;
		int maxSerialNo = 0;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo);
		Collection<PrpLrecase> list = prpLrecaseService.findPrpLrecase(queryRule);
		if (list != null && list.size() > 0) {
			maxSerialNo = recaseService.getMaxSerialNo(claimNo);
			ReCaseDto reCaseDto = recaseService.findByPrimaryKey(claimNo, maxSerialNo);
			PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
			if (prpLrecase.getCloseCaseUserCode() != null && prpLrecase.getCloseCaseUserCode().length() > 0) {
				blnReturn = true; // 重开已结为true,其他都为false
			}
		}
		return blnReturn;
	}

	// add by lym
	// 200603015----------------------------------------------------[END]

	/**
	 * 立案已经结案的不许再进行结案登记
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean isGenrateCaseNo(String claimNo) throws Exception {
		boolean blnReturn = false;
		// 查询立案信息
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		if (claimDto != null) {
			PrpLclaim prpLclaim = claimDto.getPrpLclaim();
			if (prpLclaim != null && prpLclaim.getClaimNo() != null) {
				if (prpLclaim.getCancelDate() != null) {
					throw new UserException(0, -1, "重開賠案", "案件已註銷，不允許重開賠案!");
				}
				if (DataUtils.emptyToNull(prpLclaim.getCaseNo()) != null) {
					blnReturn = true;
				}
			}
		} else {
			throw new UserException(0, -1, "重開賠案", "不存在該賠案號的相應賠案! 請輸入正確的賠案號!");
		}
		return blnReturn;
	}

	public RecaseService getRecaseService() {
		return recaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
	}

	public PrpLguaranteeService getPrpLguaranteeService() {
		return prpLguaranteeService;
	}

	public void setPrpLguaranteeService(PrpLguaranteeService prpLguaranteeService) {
		this.prpLguaranteeService = prpLguaranteeService;
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

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public PrpJpayRefRecService getPrpJpayRefRecService() {
		return prpJpayRefRecService;
	}

	public void setPrpJpayRefRecService(PrpJpayRefRecService prpJpayRefRecService) {
		this.prpJpayRefRecService = prpJpayRefRecService;
	}
}
