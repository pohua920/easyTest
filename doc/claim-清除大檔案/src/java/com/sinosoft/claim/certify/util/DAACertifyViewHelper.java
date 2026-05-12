package com.sinosoft.claim.certify.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import com.sinosoft.claim.certify.service.facade.CertifyService;
import com.sinosoft.claim.certify.vo.CertifyDto;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcertifyCollectId;
import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLcertifyDirectId;
import com.sinosoft.claim.schema.model.PrpLcertifyImg;
import com.sinosoft.claim.schema.model.PrpLcertifyImgId;
import com.sinosoft.claim.schema.model.PrpLcertifyPayee;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLqualityCheck;
import com.sinosoft.claim.schema.model.PrpLqualityCheckId;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistExtId;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyCollectService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyDirectService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyPayeeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.claimCertify.CertifyTreeXml;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.log.Logger;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * <p>
 * Title: CertifyViewHelper
 * </p>
 * <p>
 * Description:单证ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2004
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class DAACertifyViewHelper extends CertifyViewHelper {
	/** Log日志对象 */
	private static final Logger logger = Logger.getLogger(DAACertifyViewHelper.class);
	/** 单证服务 */
	private CertifyService certifyService;
	/** 报案服务 */
	private RegistService registService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 索赔单证指引信息服务 */
	private PrpLcertifyDirectService prpLcertifyDirectService;
	/** 领款人信息服务 */
	private PrpLcertifyPayeeService prpLcertifyPayeeService;
	/** 查勘服务 */
	private CheckService checkService;
	/** 单证收集信息服务 */
	private PrpLcertifyCollectService prpLcertifyCollectService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 代码服务 */
	private CodeService codeService;
	/** 险种险类代码对照信息服务 */
	private UtiCodeTransferService utiCodeTransferService;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	private EndorseViewHelper endorseViewHelper;
	/**
	 * 保存单证时单证页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return certifyDto 单证数据传输数据结构
	 * @throws Exception 加入非车险的数据处理
	 */
	public CertifyDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对certify,certifyText表的赋值
		CertifyDto certifyDto = super.viewToDto(httpServletRequest);
		if (certifyDto.getNodeType().equals("CertifDirect")) {
			/*---------------------索赔指引 PrpLcertifyDirect------------------------------------*/
			List<PrpLcertifyDirect> prpLcertifyDirectList = new ArrayList<PrpLcertifyDirect>();
			CertifyAttribute certifyAttribute = new CertifyAttribute();
			// 从界面得到输入数组
			certifyAttribute.setRegistNo((String) httpServletRequest.getParameter("prpLcertifyCollectBusinessNo"));
			// 取得报案的其他信息
			RegistDto registDto = registService.findByPrimaryKey(certifyAttribute.getRegistNo());
			certifyAttribute.setPolicyNo(registDto.getPrpLregist().getPolicyNo());
			certifyAttribute.setRiskCode(registDto.getPrpLregist().getRiskCode());

			// 判断保单是商业，强三还是关联
			String compelPolicyFlag = "0";
			String relatePolicyFlag = "0";
			if (registDto.getPrpLRegistRPolicyList().size() > 1) {
				relatePolicyFlag = "1";
			} else if ("3".equals(((Prplregistrpolicy) registDto.getPrpLRegistRPolicyList().get(0)).getPolicyType())) {
				compelPolicyFlag = "1";
			}
			httpServletRequest.setAttribute("compelPolicyFlag", compelPolicyFlag);
			httpServletRequest.setAttribute("relatePolicyFlag", relatePolicyFlag);
			int serialNo = 0;
			String[] checkArray = httpServletRequest.getParameterValues("prpLcertifyDirectCode");
			String[] lossItemCode = httpServletRequest.getParameterValues("prpLcertifyDirectLossItemCode");
			String[] compelFlag = httpServletRequest.getParameterValues("compleChoiceFlag");
			String[] certifyDirectFlag = httpServletRequest.getParameterValues("certifyDirectFlag");
			// 处理车辆
			for (int i = 0; i < checkArray.length; i++) {
				String cFlag = "0";
				String bFlag = "0";
				// 如果是被选择的
				if (!checkArray[i].equals("0")) {
					serialNo++;
					PrpLcertifyDirect prpLcertifyDirect = new PrpLcertifyDirect();
					PrpLcertifyDirectId prpLcertifyDirectId = new PrpLcertifyDirectId();
					prpLcertifyDirectId.setRegistNo(certifyAttribute.getRegistNo());
					prpLcertifyDirectId.setSerialNo(new BigDecimal(serialNo));
					prpLcertifyDirectId.setLossItemCode(lossItemCode[i]);
					prpLcertifyDirect.setId(prpLcertifyDirectId);
					prpLcertifyDirect.setRiskCode(certifyAttribute.getRiskCode());
					prpLcertifyDirect.setPolicyNo(certifyAttribute.getPolicyNo());
					prpLcertifyDirect.setTypeCode(checkArray[i]);
					prpLcertifyDirect.setTypeName(this.codeService.translateCodeCode("ImageType", checkArray[i], true));
					prpLcertifyDirect.setColumnValue("columevaue");
					if ("D".equals(this.codeService.translateRiskCodetoRiskType(certifyAttribute.getRiskCode()))) {
						if ("1".equals(compelFlag[i])) {
							cFlag = "1";
						}
						if ("1".equals(certifyDirectFlag[i])) {
							bFlag = "1";
						}
						prpLcertifyDirect.setCompelFlag(cFlag);
						prpLcertifyDirect.setBusinessFlag(bFlag);
					}
					prpLcertifyDirectList.add(prpLcertifyDirect);
				}
			}
			certifyAttribute.setSerialNo(serialNo);
			/**
			 * 获取自定义类型
			 */
			List<PrpLcertifyDirect> customCertifyDirect = getCustomCertifyDirect(httpServletRequest, certifyAttribute);
			prpLcertifyDirectList.addAll(customCertifyDirect);

			// 立案集合中加入三者车辆
			certifyDto.setPrpLcertifyDirectList(prpLcertifyDirectList);
		}

		List<PrpLqualityCheck> prpLqualityCheckList = new ArrayList<PrpLqualityCheck>();
		PrpLqualityCheck prpLqualityCheck = null;
		PrpLqualityCheckId prpLqualityCheckId = null;
		String strCount = httpServletRequest.getParameter("txtRecordNum");
		logger.debug("-----1---" + strCount);
		int intCount = Integer.parseInt(DataUtils.nullToZero(strCount));
		int j = 0;
		String strQuestionCode = "";
		String strQuestionName = "";
		String strQuestionRemark = "";
		String strVisitBackQueRes = "";
		for (int i = 0; i < intCount; i++) {
			j = i + 1;
			strQuestionCode = "txtQuestionCode" + j;
			strQuestionName = "txtQuestionName" + j;
			strQuestionRemark = "txtQuestionRemark" + j;
			strVisitBackQueRes = "VisitBackQue" + j;
			prpLqualityCheck = new PrpLqualityCheck();
			prpLqualityCheckId = new PrpLqualityCheckId();
			prpLqualityCheckId.setRegistNo(certifyDto.getPrpLcertifyCollect().getId().getBusinessNo());
			prpLqualityCheckId.setQualityCheckType(httpServletRequest.getParameter("qualityCheckType"));
			prpLqualityCheckId.setSerialNo(i + 1);
			prpLqualityCheck.setId(prpLqualityCheckId);
			prpLqualityCheck.setTypeName(httpServletRequest.getParameter(strQuestionName));
			prpLqualityCheck.setTypeCode(httpServletRequest.getParameter(strQuestionCode));
			prpLqualityCheck.setCheckResult(httpServletRequest.getParameter(strVisitBackQueRes));
			prpLqualityCheck.setCheckRemark(httpServletRequest.getParameter(strQuestionRemark));
			prpLqualityCheck.setFlag("");
			prpLqualityCheckList.add(prpLqualityCheck);
			logger.debug("----j----" + j + "|" + strVisitBackQueRes + "|" + strQuestionRemark);
		}
		// 加到ArrayList中
		certifyDto.setPrpLqualityCheckList(prpLqualityCheckList);
		// 整理回访问询信息结束
		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 从界面得到输入数组
		String prpLregistExtRegistNo = (String) httpServletRequest.getParameter("prpLregistExtRegistNo");
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");

		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo != null) {
			PrpLregistExtId prpLregistExtId = null;
			// logger.debug("人员伤亡跟踪部分开始 ");
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExtId = new PrpLregistExtId();
				prpLregistExtId.setRegistNo(prpLregistExtRegistNo);
				prpLregistExtId.setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setId(prpLregistExtId);
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.setInputDate(new DateTime(prpLregistExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			certifyDto.setPrpLregistExtList(prpLregistExtList);
		}

		logger.debug("------nodeType- 索赔清单的标志------" + certifyDto.getNodeType());

		// reason:巨灾代码
		PrpLclaim prpLclaim = new PrpLclaim();
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(certifyDto.getPrpLcertifyCollect().getId().getBusinessNo());
		if (prpLclaimList != null && prpLclaimList.size() > 0) {
			for (int i = 0; i < prpLclaimList.size(); i++) {
				prpLclaim = (PrpLclaim) prpLclaimList.get(i);
			}
		}
		String strCatastropheCode1 = httpServletRequest.getParameter("prpCatastropheCode1");
		String strCatastropheName1 = httpServletRequest.getParameter("prpCatastropheName1");
		String strCatastropheCode2 = httpServletRequest.getParameter("prpCatastropheCode2");
		String strCatastropheName2 = httpServletRequest.getParameter("prpCatastropheName2");
		// 对是否涉及担保进行更新 
		String strFGuaranteeFlag = httpServletRequest.getParameter("guaranteeFlag");
		if (strFGuaranteeFlag != null && !"".equals(strFGuaranteeFlag) && !"null".equals(strFGuaranteeFlag)) {
			prpLclaim.setGuaranteeFlag(strFGuaranteeFlag);
		}
		// 对接收客户索赔申请时间 进行更新 
		String strStartApplyPayDate = httpServletRequest.getParameter("startApplyPayDate");
		if (DataUtils.dbNullToEmpty(strStartApplyPayDate).length() != 0) {
			prpLclaim.setStartApplyPayDate(new DateTime(strStartApplyPayDate, DateTime.YEAR_TO_DAY));
		}
		// 对接收客户索赔申请时间 进行更新 
		prpLclaim.setCatastropheCode1(strCatastropheCode1);
		prpLclaim.setCatastropheName1(strCatastropheName1);
		prpLclaim.setCatastropheCode2(strCatastropheCode2);
		prpLclaim.setCatastropheName2(strCatastropheName2);
		certifyDto.setPrpLclaim(prpLclaim);

		return certifyDto;
	}

	/**
	 * 填写单证页面及查询单证request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param certifyDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, CertifyDto certifyDto) throws Exception {
	}

	/**
	 * 进入单证收集画面前取得必要的初始信息.
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 业务号码
	 * @throws Excep tion
	 */
	public void certifyDtoToView(HttpServletRequest httpServletRequest, String registNo, String uploadNodeFlag) throws Exception {
		// 工作流的信息
		String swfLogFlowID = (String) httpServletRequest.getParameter("swfLogFlowID");
		String swfLogLogNo = (String) httpServletRequest.getParameter("swfLogLogNo");
		CertifyDto certifyDto = certifyService.findCertifyDto(registNo);

		/*
		 * 需要在继续修改..查勘对象没有生成
		 */
		CheckDto checkDto = checkService.findByPrimaryKey(registNo); // add

		RegistDto registDto = registService.findByPrimaryKey(registNo);
		// 判断保单是商业，强三还是关联
		String compelPolicyFlag = "0";
		String relatePolicyFlag = "0";
		if (registDto.getPrpLRegistRPolicyList() != null && registDto.getPrpLRegistRPolicyList().size() > 0) {
			if (registDto.getPrpLRegistRPolicyList().size() > 1) {
				relatePolicyFlag = "1";
			} else if ("3".equals(((Prplregistrpolicy) registDto.getPrpLRegistRPolicyList().get(0)).getPolicyType())) {
				compelPolicyFlag = "1";
			}
		}
		httpServletRequest.setAttribute("compelPolicyFlag", compelPolicyFlag);
		httpServletRequest.setAttribute("relatePolicyFlag", relatePolicyFlag);
		if ("1".equals(relatePolicyFlag)) {
			httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		}
		httpServletRequest.setAttribute("prpLregist", registDto.getPrpLregist());
		PrpLcertifyCollect prpLcertifyCollect = null;
		// 如果已经存在单证主表(PrpLcertifyCollect)信息
		if (certifyDto != null && certifyDto.getPrpLcertifyCollect() != null) {
			logger.debug("----已经存在单证主表信息---------");
			prpLcertifyCollect = certifyDto.getPrpLcertifyCollect();
			// 设置状态，原来有的取原来的，没有的设置为1
			if (certifyDto.getPrpLclaimStatus() != null) {
				prpLcertifyCollect.setStatus(certifyDto.getPrpLclaimStatus().getStatus());
			} else {
				prpLcertifyCollect.setStatus("1");
			}
		} else {
			logger.debug("----不存在单证主表信息---------" + registDto.getPrpLregist().getPolicyNo());
			prpLcertifyCollect = new PrpLcertifyCollect();
			PrpLcertifyCollectId prpLcertifyCollectId = new PrpLcertifyCollectId();
			prpLcertifyCollectId.setBusinessNo(registNo);
			prpLcertifyCollect.setId(prpLcertifyCollectId);
			prpLcertifyCollect.setPolicyNo(registDto.getPrpLregist().getPolicyNo());
			// 设置三者车收集标志
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < registDto.getPrpLthirdCarLossList().size() + 3; i++) {
				stringBuffer.append("0");
			}
			logger.debug("----不存在单证主表信息--stringBuffer-------" + stringBuffer.toString());
			prpLcertifyCollect.setCltThirdCarFlag(stringBuffer.toString());// 十辆车够用
			prpLcertifyCollectId.setLossItemCode("1");
			prpLcertifyCollect.setLossItemName("標的名稱");
			prpLcertifyCollect.setPicCount(new BigDecimal(0));
			prpLcertifyCollect.setUploadYear(String.valueOf(new DateTime(DateTime.current().toString()).getYear()));
			prpLcertifyCollect.setRiskCode(registDto.getPrpLregist().getRiskCode());
			prpLcertifyCollect.setStartDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLcertifyCollect.setStartHour(String.valueOf(DateTime.current().getHour()));
			prpLcertifyCollect.setEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLcertifyCollect.setEndHour(String.valueOf(DateTime.current().getHour()));
			if (httpServletRequest.getSession().getAttribute("user") == null) {
				prpLcertifyCollect.setOperatorCode(registDto.getPrpLregist().getHandlerCode());
			} else {
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				prpLcertifyCollect.setOperatorCode(user.getUserCode());
			}
			prpLcertifyCollect.setContent("");
			prpLcertifyCollect.setFlag("");
			prpLcertifyCollect.setCaseFlag("0000"); // 事故类型
			prpLcertifyCollect.setStatus("1");
		}
		String status = httpServletRequest.getParameter("status");// 从工作流上去状态
		if (status != null && !status.equals("")) {
			prpLcertifyCollect.setStatus(status);
		}
		// 工作流的东西
		if (!prpLcertifyCollect.getStatus().equals("4")) {
			String msg = "";
			// 校验立案是否注销拒赔
			PrpLclaim prpLclaim = null;
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("registNo", registNo);
			List<PrpLclaim> claimList = prpLclaimService.findPrpLclaim(queryRule);
			boolean modify = true;
			for (int i = 0; i < claimList.size(); i++) {
				prpLclaim = claimList.get(i);
				if (!("0".equals(prpLclaim.getCaseType()) || "1".equals(prpLclaim.getCaseType())||"3".equals(prpLclaim.getCaseType()))) {
					msg = "";
					modify = false;
				}
				if (modify) {
					if ("0".equals(prpLclaim.getCaseType())) {
						msg = "該案已經註銷";
					}else if ("1".equals(prpLclaim.getCaseType())) {
						msg = "該案已經拒賠";
					}else if("3".equals(prpLclaim.getCaseType())){
						msg = "該案已經自付註銷";
					}
				}
			}

			if (msg.equals("")) {
				msg = this.getWorkFlowViewHelper().checkNodeSubmit(swfLogFlowID, swfLogLogNo);
			}
			prpLcertifyCollect.setNoSubmitMsg(msg);
		}
		// 把单证主表设置到 prpLcertifyCollectDto
		httpServletRequest.setAttribute("prpLcertifyCollect", prpLcertifyCollect);
		// 处理单证及影像表(PrpLcertifyImg)
		PrpLcertifyImg prpLcertifyImg = new PrpLcertifyImg();
		prpLcertifyImg.setCertifyImgList(certifyDto.getPrpLcertifyImgList());
		if (certifyDto.getPrpLcertifyImgList() != null) {
			logger.debug("---处理单证及影像表------" + certifyDto.getPrpLcertifyImgList().size() + "|" + certifyDto.getPrpLcertifyDirectList().size());
		}
		prpLcertifyImg.setUploadNodeFlag(uploadNodeFlag);
		PrpLcertifyImgId prpLcertifyImgId = new PrpLcertifyImgId();
		prpLcertifyImgId.setBusinessNo(registNo);
		prpLcertifyImg.setId(prpLcertifyImgId);
		prpLcertifyImg.setPolicyNo(registDto.getPrpLregist().getPolicyNo());
		prpLcertifyImg.setSignInDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcertifyImg.setThirdPartyCode("9999999999");
		if (httpServletRequest.getSession().getAttribute("user") == null) {
			prpLcertifyImg.setCollectorName(registDto.getPrpLregist().getHandlerCode());
		} else {
			prpLcertifyImg.setCollectorName(((UserDto) httpServletRequest.getSession().getAttribute("user")).getUserCode());
		}
		prpLcertifyImg.setFlag("1");
		httpServletRequest.setAttribute("prpLcertifyImg", prpLcertifyImg);
		httpServletRequest.getSession().setAttribute("prpLcertifyImg", prpLcertifyImg);
		httpServletRequest.setAttribute("thirdPartyList", registDto.getPrpLthirdPartyList());
		httpServletRequest.setAttribute("prpLqualityCheckList", certifyDto.getPrpLqualityCheckList());
		PrpLcertifyDirect prpLcertifyDirect = new PrpLcertifyDirect();
		prpLcertifyDirect.setCertifyDirectList(certifyDto.getPrpLcertifyDirectList());
		httpServletRequest.setAttribute("prpLcertifyDirect", prpLcertifyDirect);
		httpServletRequest.setAttribute("prpLpersonTraceList", checkDto.getPrpLpersonTraceList());
		List<PrpDcode> imageTypeList = this.codeService.getCodeType("ImageType", registDto.getPrpLregist().getRiskCode());
		Map<String, Object> infoMap = generateCertifyInfo(imageTypeList);
		httpServletRequest.setAttribute("imageTypeList", imageTypeList);
		httpServletRequest.setAttribute("imageTypeMap", infoMap);
		Collection<PrpDcode> certiQuality = this.codeService.getCodeType("CertiQuality", prpLcertifyDirect.getRiskCode());
		httpServletRequest.setAttribute("qualityCheckList", certiQuality);

		// 根据需要标志，都上传了。。就认为是齐全的，如果有需要标志的，但是没上传，就是认为不齐全的。

		// 设置三者车收集标志
		StringBuffer stringBuffer = new StringBuffer();
		String cltInsureCarFlag = "1";
		String cltPersonFlag = "1"; // 人伤收集标志
		String cltPropFlag = "1"; // 物损收集标志
		String cltCarLossFlag = "1"; // 盗抢收集标志
		String cltAllLossFlag = "1"; // 全损收集标志 no support
		String compelFlag = "1"; // 强制保险收集标志
		Collection<PrpDcode> requireList = new ArrayList<PrpDcode>();
		Collection<PrpDcode> uploadList = new ArrayList<PrpDcode>();

		String imageType = "07%";

		// 查询在需要标志不为空的情况 默认都为不齐全。
		// 查询在需要标志中为需要，並且都上传的，则认为是齐全的。
		// 查询在需要标志中为需要，有没上传的，认为不齐全。
		//state 2013-07-08 收集标志位的修改，查询影像系统的数据;
		String conditons = "codetype='ImageType' and codecode like '" + imageType + "'" + " and exists (select 0 from prpLcertifyDirect " + " where registno='" + registNo + "'" + " and typecode=codecode)";
		requireList = this.codeService.findPrpDcodeByConditions(conditons);
		// 人伤资料不齐全
		if (requireList != null && requireList.size()> 0) {
			conditons = conditons + " and not exists (select 0 from SFM_FILEINDEX " + " where bussNo='" + registNo + "'" + " and (typepath4=codecode or typepath5=codecode))";
			uploadList = this.codeService.findPrpDcodeByConditions(conditons);
			if (uploadList != null && uploadList.size() > 0)
				cltPersonFlag = "0";
		}
		// 盗抢不齐全
		imageType = "08%";
		conditons = "codetype='ImageType' and codecode like '" + imageType + "'" + " and exists (select 0 from prpLcertifyDirect " + " where registno='" + registNo + "'" + " and typecode=codecode)";
		requireList = this.codeService.findPrpDcodeByConditions(conditons);
		if (requireList != null && requireList.size() > 0) {
			conditons = conditons + " and not exists (select 0 from SFM_FILEINDEX " + " where bussNo='" + registNo + "'" + " and (typepath4=codecode or typepath5=codecode))";
			uploadList = this.codeService.findPrpDcodeByConditions(conditons);
			if (uploadList != null && uploadList.size() > 0)
				cltCarLossFlag = "0";
		}
		// 物损资料不齐全
		imageType = "06%";
		conditons = "codetype='ImageType' and codecode like '" + imageType + "'" + " and exists (select 0 from prpLcertifyDirect " + " where registno='" + registNo + "'" + " and typecode=codecode)";
		requireList = this.codeService.findPrpDcodeByConditions(conditons);

		if (requireList != null && requireList.size() > 0) {
			conditons = conditons + " and not exists (select 0 from SFM_FILEINDEX " + " where bussNo='" + registNo + "'" + " and (typepath4=codecode or typepath5=codecode))";
			uploadList = this.codeService.findPrpDcodeByConditions(conditons);
			if (uploadList != null && uploadList.size() > 0)
				cltPropFlag = "0";
		}
		imageType = "05%";
		// 主车收集标志
		String lossItemCode = "";
		String carFlag = "1";
		if (registDto.getPrpLthirdPartyList() != null) {
			for (int i = 0; i < registDto.getPrpLthirdPartyList().size(); i++) {
				lossItemCode = ((PrpLthirdParty) registDto.getPrpLthirdPartyList().get(i)).getId().getSerialNo() + "";
				carFlag = "1";
				if("1".equals(lossItemCode)){
					imageType = "05%";
				}else{
					imageType = "12%";
				}
				conditons = "codetype='ImageType' and codeCode like '" + imageType + "'" + " and exists (select 0 from prpLcertifyDirect " + " where registno='" + registNo + "' and lossitemcode='" + lossItemCode + "'" + " and typecode=codecode )";
				requireList = this.codeService.findPrpDcodeByConditions(conditons);

				if (requireList == null || requireList.size() == 0) {
					logger.debug("lossitemcode" + lossItemCode + ":requals 0");
					carFlag = "0";
				} else {
					//and lossitemcode='" + lossItemCode + "'" + "影像系统不区分标的车和三者车
					conditons = conditons + " and not exists (select 0 from SFM_FILEINDEX  where bussNo='" + registNo + "' and typepath5=codecode and typepath4 = '"+lossItemCode+"')";
					uploadList = this.codeService.findPrpDcodeByConditions(conditons);
					if (uploadList != null && uploadList.size() > 0) {
						carFlag = "0";
						logger.debug("lossitemcode" + lossItemCode + ":upload >0" + conditons);

					}
				}
				// flag in car
				if (lossItemCode.equals("1") && carFlag.equals("0")){
					cltInsureCarFlag = "0";
				}
				stringBuffer.append(carFlag);
			}
		}
		List<PrpLcertifyDirect> compelNeedList = prpLcertifyDirectService.findPrpLcertifyDirect(registNo);
		if (compelNeedList != null && compelNeedList.size() > 0) {
			compelFlag = "0";
		}

		logger.debug("----不存在单证主表信息--stringBuffer-------" + stringBuffer.toString());
		prpLcertifyCollect.setCltInsureCarFlag(cltInsureCarFlag); // 主车收集标志
		prpLcertifyCollect.setCltThirdCarFlag(stringBuffer.toString());// 十辆车够用
																		// 三者车收集标志
		prpLcertifyCollect.setCompelFlag(compelFlag); // 强制保险收集标志
		prpLcertifyCollect.setCltPersonFlag(cltPersonFlag); // 人伤收集标志
		prpLcertifyCollect.setCltPropFlag(cltPropFlag); // 物损收集标志
		prpLcertifyCollect.setCltCarLossFlag(cltCarLossFlag); // 盗抢收集标志
		prpLcertifyCollect.setCltAllLossFlag(cltAllLossFlag); // 全损收集标志

		if (cltPersonFlag.equals("1") && cltPropFlag.equals("1") && cltCarLossFlag.equals("1") && cltAllLossFlag.equals("1") && stringBuffer.indexOf("0") < 0) {
			prpLcertifyCollect.setCollectFlag("1"); // 全部收集标志
		} else {
			prpLcertifyCollect.setCollectFlag("0"); // 全部收集标志
		}

		// (1)得到实赔类型列表
		logger.debug("---处理单证及影像表---imageTypeList---" + imageTypeList.size());
		// 增加领款人信息 意健险
		String utiCodeTransferConditions = " 1=1 and outercode = '" + registDto.getPrpLregist().getRiskCode() + "'";

		Collection<UtiCodeTransfer> uticodetransfer = this.utiCodeTransferService.findByConditions(utiCodeTransferConditions);
		UtiCodeTransfer utiCodeTransferDto = null;
		Iterator<UtiCodeTransfer> it = uticodetransfer.iterator();
		if (it.hasNext()) {
			utiCodeTransferDto = (UtiCodeTransfer) it.next();
		}
		if ("E".equals(utiCodeTransferDto.getRiskType())) {
			List<PrpLcertifyPayee> prpLcertifyPayeeList = prpLcertifyPayeeService.findPrpLcertifyPayee(registNo);
			certifyDto.setPrpLcertifyPayeeList(prpLcertifyPayeeList);
		}
		// 结案後不能上传图片 和删除 DAA
		String isCase = "No";
		String editType = httpServletRequest.getParameter("editType");
		if ("SHOW".equals(editType)) {
			boolean flag = prpLclaimService.isClaim(registNo);
			if (flag) {
				isCase = "Yes";
			}
		}
		httpServletRequest.setAttribute("isCase", isCase);
		httpServletRequest.setAttribute("registDto", registDto);
		httpServletRequest.setAttribute("certifyDto", certifyDto);
		httpServletRequest.setAttribute("registType", RegistDto.BUSINESS_COMPEL_POLICY);

		// 给报案信息补充说明多行列表准备数据
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		PrpLregistExtId prpLregistExtId = new PrpLregistExtId();
		prpLregistExtId.setRegistNo(registDto.getPrpLregist().getRegistNo());
		prpLregistExt.setId(prpLregistExtId);
		prpLregistExt.setRiskCode(registDto.getPrpLregist().getRiskCode());
		arrayListRegistExt = certifyDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);
		// 从立案取巨灾代码在页面展示，允许修改
		PrpLclaim prpLclaim = new PrpLclaim();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", registNo);
		List<PrpLclaim> prpLclaimList = prpLclaimService.findPrpLclaim(queryRule);
		if (prpLclaimList != null && prpLclaimList.size() > 0) {
			for (int i = 0; i < prpLclaimList.size(); i++) {
				prpLclaim = prpLclaimList.get(i);
			}
		}
		PolicyDto policyDto = policyService.findByPrimaryKey(registDto.getPrpLregist().getPolicyNo());
		httpServletRequest.setAttribute("policyDto", policyDto);
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("riskType", ConstantCodes.carClassMap.get(registDto.getPrpLregist().getRiskCode()));
		CertifyTreeXml certifyTreeXml = new CertifyTreeXml();
		String typeTreeXML = "";
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		if ("D".equals(ConstantCodes.carClassMap.get(registDto.getPrpLregist().getRiskCode()))) {
			typeTreeXML = certifyTreeXml.getCertifyTree(prpLcertifyCollect, certifyDto.getPrpLcertifyDirectList(), registDto.getPrpLthirdPartyList(), checkDto.getPrpLpersonTraceList(), "");
		} else {
			typeTreeXML = certifyTreeXml.getCertifyTree(prpLcertifyCollect, certifyDto.getPrpLcertifyDirectList());
		}
		String paramString = "";
		String paramString_show = "";
		if (httpServletRequest.getSession().getAttribute("user") == null) {
			paramString = certifyTreeXml.getParamString(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getOperatorCode(), prpLcertifyCollect.getId().getBusinessNo(), "");
			paramString_show = certifyTreeXml.getParamString(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getOperatorCode(), prpLcertifyCollect.getId().getBusinessNo(), "_show");
		} else {
			paramString = certifyTreeXml.getParamString(user.getComCode(), user.getUserCode(), prpLcertifyCollect.getId().getBusinessNo(), "");
			paramString_show = certifyTreeXml.getParamString(user.getComCode(), user.getUserCode(), prpLcertifyCollect.getId().getBusinessNo(), "_show");
		}

		String remoteUrl = AppConfig.get("sysconst.NewCertify_URL");
		String remoteUrl_show = AppConfig.get("sysconst.NewCertify_URL_show");
		httpServletRequest.setAttribute("typeTreeXML", typeTreeXML);

		httpServletRequest.setAttribute("remoteUrl", remoteUrl);
		httpServletRequest.setAttribute("remoteUrl_show", remoteUrl_show);
		httpServletRequest.setAttribute("paramString", paramString);
		httpServletRequest.setAttribute("paramString_show", paramString_show);
		httpServletRequest.setAttribute("certifyTypeList", ConstantsCollection.certifyTypeList);

	}

	/**
	 * 处理页面上显示的数据
	 * @param request
	 * @param forward
	 * @throws Exception
	 */
	public void showView(HttpServletRequest request, String forward) throws Exception {
		if (forward.contains("DAA")) {
			showView05(request);
		} else if ("CertifyDirectPrint".equals(forward) || "OtherCertifyDirectPrint".equals(forward)) {
			certifyDirectPrintShow(request);
		}
	}

	/**
	 * 整理单证清单中页面显示的数据
	 * @param request
	 */
	public void certifyDirectPrintShow(HttpServletRequest request) {
		try {
			RegistDto registDto = (RegistDto) request.getAttribute("registDto");
			PrpLregist prpLregist = registDto.getPrpLregist();
			String registNo = prpLregist.getRegistNo();
			String policyNo = prpLregist.getPolicyNo();
			/** modify by 中科軟 保單取值優化  begin */
//			PolicyDto policyDto = policyService.findByPrimaryKey(policyNo);
//			PrpCmain prpCmain = policyDto.getPrpCmain();
			String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
			String damageHour = prpLregist.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			/** modify by 中科軟 保單取值優化  end */
			String strCode = StringConvert.encode(prpLregist.getComCode());
			String strName = this.codeService.translateComCode(strCode, true);
			int certifyDtoCount = 0;
			CertifyDto certifyDto = (CertifyDto) request.getAttribute("certifyDto");
			if (certifyDto.getPrpLcertifyDirectList() != null) {
				certifyDtoCount = certifyDto.getPrpLcertifyDirectList().size();
			}
			request.setAttribute("certifyDtoCount", certifyDtoCount);
			request.setAttribute("prpCmainDto", prpCmain);
			request.setAttribute("strName", strName);
			request.setAttribute("registNo", registNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面上移动过来的java代码,车险部分
	 * @param request 
	 */
	public void showView05(HttpServletRequest request) throws Exception {
		List<?> imageTypeList = (ArrayList<?>) request.getAttribute("imageTypeList");
		int imageTypeListSize = 0;
		if (imageTypeList != null) {
			imageTypeListSize = imageTypeList.size();
		}
		request.setAttribute("imageTypeListSize", imageTypeListSize);
		PrpLcertifyCollect prpLcertifyCollect = (PrpLcertifyCollect) request.getAttribute("prpLcertifyCollect");
		String relatePolicyFlag = (String) request.getAttribute("relatePolicyFlag");
		String compelPolicyFlag = (String) request.getAttribute("compelPolicyFlag");
		// 单证类别进行循环
		String compelType = "hidden";
		String businessType = "hidden";
		if ("1".equals(relatePolicyFlag)) {
			businessType = "checkbox";
			compelType = "checkbox";
		} else if ("1".equals(compelPolicyFlag)) {
			compelType = "checkbox";
		} else {
			businessType = "checkbox";
		}
		request.setAttribute("businessType", businessType);
		request.setAttribute("compelType", compelType);
		// 三者车的标志
		String cltThirdCarFlag = prpLcertifyCollect.getCltThirdCarFlag();
		int cltThirdCarFlagSize = 0;
		if (cltThirdCarFlag != null && cltThirdCarFlag.length() > 0) {
			cltThirdCarFlagSize = cltThirdCarFlag.length(); // 1111
		}
		request.setAttribute("cltThirdCarFlagSize", cltThirdCarFlagSize);
		request.setAttribute("cltThirdCarFlag", cltThirdCarFlag);
		String strRiskCode = this.codeService.translateRiskCodetoConfigCode(prpLcertifyCollect.getRiskCode());
		request.setAttribute("strRiskCode", strRiskCode);
	}

	/**
	 * 根据赔案号,报案号,案件状态，车牌号码，操作时间查询单证信息
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 赔案号
	 * @throws Exception Reason:增加车牌号，案件状态，操作时间查询条件
	 */
	public void setPrpLcertifyDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {
		// 根据输入的保单号，单证号生成SQL where 子句
		String businessNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		logger.debug(":::1::::::::" + licenseNo);
		logger.debug(":::2:::::::" + httpServletRequest.getParameter("LicenseNoSign"));
		conditions = conditions + StringConvert.convertString("a.businessNo", businessNo, workFlowQueryDto.getRegistNoSign());
		conditions = conditions + StringConvert.convertString("c.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ")";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");

		conditions = conditions + uiPowerInterface.addPower(userDto, "c", "", "ComCode");
		// 查询立案信息

		// 得到多行单证主表信息
		logger.debug("start to search,please waiting ...");
		logger.debug("end search,please waiting for result...");
		PrpLcertifyCollect prpLcertifyCollect = new PrpLcertifyCollect();
		List<PrpLcertifyCollect> certifyList = prpLcertifyCollectService.findByQueryConditions(conditions);
		prpLcertifyCollect.setCertifyCollectList(certifyList);
		logger.debug("finish add list");
		logger.debug("editType=" + httpServletRequest.getParameter("editType"));
		prpLcertifyCollect.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcertifyCollect", prpLcertifyCollect);
	}
	/**
	 * 收集單證數據
	 * @param httpServletRequest
	 * @param workFlowQueryDto
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public void setPrpLcertifyDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto, int pageNo, int recordPerPage) throws Exception {
		// 根据输入的保单号，单证号生成SQL where 子句
		String businessNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		logger.debug(":::1::::::::" + licenseNo);
		logger.debug(":::2:::::::" + httpServletRequest.getParameter("LicenseNoSign"));
		conditions = conditions + StringConvert.convertString("a.businessNo", businessNo, workFlowQueryDto.getRegistNoSign());
		conditions = conditions + StringConvert.convertString("c.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + "AND b.status in (" + status + ")";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		/***业务表查询不再限制机构  delete by chenjie 20130614 start*/
//		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
//		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
//		conditions = conditions + uiPowerInterface.addPower(userDto, "c", "", "ComCode");
		/***业务表查询不再限制机构  delete by chenjie 20130614 end*/

		String condition = httpServletRequest.getParameter("condition");
		// reason 查询标志
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if ("true".equals(searchFlag)) {

		} else {
			if (condition != null && condition.trim().length() > 0) {
				conditions = condition;
			}
		}
		logger.debug("start to search,please waiting ...");
		logger.debug("end search,please waiting for result...");
		String editType = httpServletRequest.getParameter("editType");
		Page page = prpLcertifyCollectService.findByQueryConditions(conditions, pageNo, recordPerPage);
		httpServletRequest.setAttribute("page", page);
		List<PrpLcertifyCollect> certifyList = new ArrayList<PrpLcertifyCollect>();
		Iterator<?> it = page.getResult().iterator();
		while(it.hasNext()){
			PrpLcertifyCollect prpLcertifyCollect = (PrpLcertifyCollect) it.next();
			certifyList.add(prpLcertifyCollect);
		}
		PrpLcertifyCollect prpLcertifyCollect = new PrpLcertifyCollect();
		prpLcertifyCollect.setCertifyCollectList(certifyList);
		prpLcertifyCollect.setEditType(editType);

		httpServletRequest.setAttribute("prpLcertifyCollect", prpLcertifyCollect);
		logger.debug("finish add list");
		logger.debug("editType=" + httpServletRequest.getParameter("editType"));

	}
	/**
	 * 查询单证类型数据
	 * @param httpServletRequest
	 * @param certifyAttribute
	 * @return
	 * @throws Exception
	 */
	private List<PrpLcertifyDirect> getCustomCertifyDirect(HttpServletRequest httpServletRequest, CertifyAttribute certifyAttribute) throws Exception {
		List<PrpLcertifyDirect> customCertifyDirectList = new ArrayList<PrpLcertifyDirect>();
		String typeCode;
		String[] prpLcertifyDirectCustomTypeName = httpServletRequest.getParameterValues("prpLcertifyDirectCustomTypeName");
		String[] prpLcertifyDirectCustomTypeSerialNo = httpServletRequest.getParameterValues("prpLcertifyDirectCustomTypeSerialNo");
		String[] prpLcertifyDirectCustomTypeCode = httpServletRequest.getParameterValues("prpLcertifyDirectCustomTypeCode");
		// 声明长度为1的int型数组,目的是为了将int作为对象传入方法中(该方法可以对此参数进行修改)
		int[] startIndex = new int[] { 1 };
		if (prpLcertifyDirectCustomTypeSerialNo != null) {
			for (int i = 1; i < prpLcertifyDirectCustomTypeSerialNo.length; i++) {
				if (prpLcertifyDirectCustomTypeName[i].trim().length() > 0) {
					certifyAttribute.serialNo++;
					PrpLcertifyDirect prpLcertifyDirect = new PrpLcertifyDirect();
					PrpLcertifyDirectId prpLcertifyDirectId = new PrpLcertifyDirectId();
					prpLcertifyDirectId.setSerialNo(new BigDecimal(certifyAttribute.getSerialNo()));
					prpLcertifyDirectId.setRegistNo(certifyAttribute.getRegistNo());
					prpLcertifyDirectId.setLossItemCode("0");
					prpLcertifyDirect.setId(prpLcertifyDirectId);
					prpLcertifyDirect.setRiskCode(certifyAttribute.getRiskCode());
					prpLcertifyDirect.setPolicyNo(certifyAttribute.getPolicyNo());
					if (prpLcertifyDirectCustomTypeCode[i].trim().length() == 0) {
						typeCode = getCustomCertifyTypeCode(prpLcertifyDirectCustomTypeCode, startIndex);
					} else {
						typeCode = prpLcertifyDirectCustomTypeCode[i];
					}
					prpLcertifyDirect.setTypeCode(typeCode);
					prpLcertifyDirect.setTypeName(prpLcertifyDirectCustomTypeName[i]);
					prpLcertifyDirect.setColumnValue("columevaue");
					prpLcertifyDirect.setFlag("");
					customCertifyDirectList.add(prpLcertifyDirect);
				}
			}
		}
		return customCertifyDirectList;
	}
	/**
	 * 整理單證類型
	 * @param havedTypeCodeArray
	 * @param startIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> generateCertifyInfo(List<PrpDcode> imageTypeList) {
		Map<String, Object> infoMap = new HashMap<String, Object>();//储存整理后的索赔清单信息
		List<PrpDcode> tempCodeList = null;
		String[] cerifyTypes = null;
		PrpDcode prpDcode = null;
		for (int i = 0; i < imageTypeList.size(); i++) {
			prpDcode =  imageTypeList.get(i);
			if (CommonUtils.isEmpty(prpDcode.getFlag())) {
				prpDcode.setFlag("0");
			}
			cerifyTypes = prpDcode.getFlag().split(",");//如果一个类型对应多个序号的，以逗号隔开
			for(String str:cerifyTypes) {
				if(infoMap.containsKey(str)) {
					tempCodeList = (List<PrpDcode>)infoMap.get(str);
					tempCodeList.add(prpDcode);
				} else {
					tempCodeList = new ArrayList<PrpDcode>();
					tempCodeList.add(prpDcode);
					infoMap.put(str, tempCodeList);
				}
			}
		}
		TreeMap<String, Object> treemap = new TreeMap(infoMap);  
		return treemap;
	}
	/**
	 * 查詢單證類型
	 * @param havedTypeCodeArray
	 * @param startIndex
	 * @return
	 */
	private String getCustomCertifyTypeCode(String[] havedTypeCodeArray, int[] startIndex) {
		String typeCode;
		DecimalFormat df = new DecimalFormat("00");
		int index = startIndex == null ? 1 : startIndex[0];
		typeCode = null;
		for (int i = index; i < 100; i++) {
			typeCode = "99" + df.format(i);
			// 判断该typeCode在已保存的列表中是否存在
			if (!ArrayUtils.contains(havedTypeCodeArray, typeCode)) {
				startIndex[0] = i + 1;
				return typeCode;
			}
		}
		throw new RuntimeException("自定義的單證類型太多了,超出了系統的範圍");
	}
	/**
	 * 單證屬性
	 * @author 中科軟
	 *
	 */
	private static class CertifyAttribute {
		String policyNo;

		String registNo;

		String riskCode;

		int serialNo;

		public String getPolicyNo() {
			return policyNo;
		}

		public void setPolicyNo(String policyNo) {
			this.policyNo = policyNo;
		}

		public String getRegistNo() {
			return registNo;
		}

		public void setRegistNo(String registNo) {
			this.registNo = registNo;
		}

		public String getRiskCode() {
			return riskCode;
		}

		public void setRiskCode(String riskCode) {
			this.riskCode = riskCode;
		}

		public int getSerialNo() {
			return serialNo;
		}

		public void setSerialNo(int serialNo) {
			this.serialNo = serialNo;
		}
	}

	public CertifyService getCertifyService() {
		return certifyService;
	}

	public void setCertifyService(CertifyService certifyService) {
		this.certifyService = certifyService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcertifyDirectService getPrpLcertifyDirectService() {
		return prpLcertifyDirectService;
	}

	public void setPrpLcertifyDirectService(PrpLcertifyDirectService prpLcertifyDirectService) {
		this.prpLcertifyDirectService = prpLcertifyDirectService;
	}

	public PrpLcertifyPayeeService getPrpLcertifyPayeeService() {
		return prpLcertifyPayeeService;
	}

	public void setPrpLcertifyPayeeService(PrpLcertifyPayeeService prpLcertifyPayeeService) {
		this.prpLcertifyPayeeService = prpLcertifyPayeeService;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public PrpLcertifyCollectService getPrpLcertifyCollectService() {
		return prpLcertifyCollectService;
	}

	public void setPrpLcertifyCollectService(PrpLcertifyCollectService prpLcertifyCollectService) {
		this.prpLcertifyCollectService = prpLcertifyCollectService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}
	
	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}
	
	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}
}
