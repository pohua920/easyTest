package com.sinosoft.claim.common.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyCopyService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpPengageService;
import com.sinosoft.claim.common.service.facade.PrpPfeeService;
import com.sinosoft.claim.common.service.facade.PrpPitemKindService;
import com.sinosoft.claim.common.service.facade.PrpPprofitService;
import com.sinosoft.claim.common.vo.EndorseDto;
import com.sinosoft.claim.common.vo.PolicyCopyDto;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCengageId;
import com.sinosoft.claim.schema.model.PrpCfee;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredId;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCinsuredNatureId;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemCarId;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemKindId;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCopyInsured;
import com.sinosoft.claim.schema.model.PrpCopyInsuredId;
import com.sinosoft.claim.schema.model.PrpCopyInsuredNature;
import com.sinosoft.claim.schema.model.PrpCopyInsuredNatureId;
import com.sinosoft.claim.schema.model.PrpCopyItemCar;
import com.sinosoft.claim.schema.model.PrpCopyItemCarId;
import com.sinosoft.claim.schema.model.PrpCopyItemKind;
import com.sinosoft.claim.schema.model.PrpCopyItemKindId;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.model.PrpCprofit;
import com.sinosoft.claim.schema.model.PrpCprofitId;
import com.sinosoft.claim.schema.model.PrpPengage;
import com.sinosoft.claim.schema.model.PrpPengageId;
import com.sinosoft.claim.schema.model.PrpPfee;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.PrpPitemCar;
import com.sinosoft.claim.schema.model.PrpPitemCarId;
import com.sinosoft.claim.schema.model.PrpPitemKind;
import com.sinosoft.claim.schema.model.PrpPitemKindId;
import com.sinosoft.claim.schema.model.PrpPmain;
import com.sinosoft.claim.schema.model.PrpPprofit;
import com.sinosoft.claim.schema.model.PrpPprofitId;
import com.sinosoft.claim.schema.service.facade.PrpCengageService;
import com.sinosoft.claim.schema.service.facade.PrpCfeeService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredNatureService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCopyInsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCprofitService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: ClaimStatusViewHelper
 * </p>
 * <p>
 * Description:理赔节点状态ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class EndorseViewHelper {
	/** 保单数据传输对象 */
	private PolicyDto policyDto = null;
	/** 批单数据传输对象服务 */
	private EndorseService endorseService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 保单副本数据传输对象服务 */
	private PolicyCopyService policyCopyService;
	/** 保单副本被保险人对象服务 */
	private PrpCopyInsuredService prpCopyInsuredService;
	
	private CommonService commonService;
	private PrpCmainService prpCmainService;
	private PrpCitemCarService prpCitemCarService;
	private PrpCengageService prpCengageService;
	private PrpCitemKindService prpCitemKindService;
	private PrpCfeeService prpCfeeService;
	private PrpCprofitService prpCprofitService;
	private PrpCinsuredService prpCinsuredService;
	private PrpCinsuredNatureService prpCinsuredNatureService;
	private CodeService codeService;
	private PrpPprofitService prpPprofitService;
	private PrpPitemKindService prpPitemKindService;
	private PrpPfeeService prpPfeeService;
	private PrpPengageService prpPengageService;
	/**
	 * 默认构造方法
	 */
	public EndorseViewHelper() {
	}

	/**
	 * 获得批改生效前最新保单
	 * @param policyNo 保单号
	 * @return 自定义保单对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public PolicyDto findForEndorBefore(String policyNo) throws SQLException, Exception {
		String curHour = String.valueOf(DateTime.current().getHour());
		DateTime curDate = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY);
		return findForEndorBefore(policyNo, curDate.toString(), curHour);
	}

	/**
	 * 获得批改生效前最新保单
	 * @param policyNo 保单号
	 * @return 自定义保单对象
	 * @throws SQLException
	 * @throws Exception 对保单的还原必须精确到小时
	 */
	public PolicyDto findForEndorBefore(String strPolicyNo, String strDamageDate) throws SQLException, Exception {
		String curHour = String.valueOf(DateTime.current().getHour());
		return findForEndorBefore(strPolicyNo, strDamageDate, curHour);
	}

	/**
	 * STUB-ONLY：针对出险保单的回倒
	 * @param strPolicyNo 保单号码
	 * @param strDamageDate 出险日期
	 * @param strDamageHour 出险小时
	 * @return 最近1次批改之前的保单的信息
	 * @throws UserException
	 */
	public PolicyDto findForEndorBefore(String strPolicyNo, String strDamageDate, String strDamageHour) throws Exception {
		PolicyDto policyDtoRe = null;
		// 取得当前保单的信息orig
		PolicyDto origPolicyDto = this.policyService.findByPrimaryKey(strPolicyNo);
		// 将字符串转换成整型
		int theDamageHour = 0;
		if (strDamageHour != null && strDamageHour.length() > 1) {
			theDamageHour = Integer.parseInt(strDamageHour.substring(0, 2));
		}
		if (strDamageDate != null && strDamageDate.length() > 9) {
			strDamageDate = strDamageDate.substring(0, 10);
		}
		PrpPhead prpPheadDto = new PrpPhead();
		// 取得批改信息表信息，
		//取的是生效日期之後的批單
		String iWherePart = "PolicyNo = '" + strPolicyNo + "'" + " AND (ValidDate >to_date('" + strDamageDate + "','yyyy-MM-dd') OR (ValidDate=to_date('" + strDamageDate + "','yyyy-MM-dd') AND ValidHour>" + theDamageHour + "))"
				+ " AND UnderWriteFlag in ('1', '3') " + " ORDER BY InputDate DESC,EndorseTimes DESC ";

		List<PrpPhead> listTemp = this.endorseService.findByPrpPheadConditions(iWherePart);
		// 没有找到符合条件的批单则返回当前保单
		if (listTemp == null || listTemp.size() < 1) {
			policyDtoRe = origPolicyDto;
		} else {
			// 找到後逐级回滚批单信息
			for (int i = 0; i < listTemp.size(); i++) {
				prpPheadDto = listTemp.get(i);
				backWard(origPolicyDto , prpPheadDto.getEndorseNo());
			}
			String riskCode = origPolicyDto.getPrpCmain().getRiskCode();
			String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
			if (!ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
				/** modify by 中科軟 大保單取數優化 begin */
				//保單有批改，則取出險日期之前的最後一張批單，
				String strEndorseNo = policyCopyService.getBackWardEndorseNo(strPolicyNo, strDamageDate, theDamageHour + "");
				if (CommonUtils.isEmpty(strEndorseNo)) {
					strEndorseNo = strPolicyNo;
				}
				PolicyCopyDto policyCopyDto = this.policyCopyService.findByPrimaryKey(strEndorseNo);
				origPolicyDto.setPrpCinsuredList(this.backWardPrpCinsured(origPolicyDto.getPrpCinsuredList(), policyCopyDto));
				origPolicyDto.setPrpCinsuredNatureList(this.backWardPrpCinsuredNature(origPolicyDto.getPrpCinsuredNatureList(), policyCopyDto));
			}
			/** modify by 中科軟 大保單取數優化 end */
			policyDtoRe = origPolicyDto;
		}
		return policyDtoRe;
	}

	/**
	 * STUB-ONLY：
	 * @param strEndorseNo 回倒批单
	 * @return 无
	 * @throws UserException
	 * @throws Exception
	 */
	public void backWard(PolicyDto origPolicyDto, String strEndorseNo) throws Exception {
		// 获取批改变化信息
		EndorseDto endorseDto = this.endorseService.findByPrimaryKey(strEndorseNo);
		origPolicyDto.setPrpCmain(this.backWardPrpCmain(origPolicyDto.getPrpCmain(), endorseDto));
		origPolicyDto.setPrpCitemCarList(this.backWardPrpCitemCar(origPolicyDto.getPrpCitemCarList(), endorseDto));
		origPolicyDto.setPrpCprofitList(this.backWardPrpCprofit(origPolicyDto.getPrpCprofitList(), endorseDto));
		origPolicyDto.setPrpCengageList(this.backWardPrpCengage(origPolicyDto.getPrpCengageList(), endorseDto));
		origPolicyDto.setPrpCitemKindList(this.backWardPrpCitemKind(origPolicyDto.getPrpCitemKindList(), endorseDto));
		origPolicyDto.setPrpCfeeList(this.backWardPrpCfee(origPolicyDto.getPrpCfeeList(), endorseDto));
	}
	
	/**
	 * 新回倒批单（根据copy表回滚，由于承保只存储部分关键数据故目前新老回滚方式共用）
	 * @param strEndorseNo 回倒批单
	 * @return 无
	 * @throws UserException
	 * @throws Exception
	 */
	private void backWardNew(PolicyDto origPolicyDto , String strEndorseNo) throws Exception {
		// 获取批改变化信息origPolicyDto
		PolicyCopyDto policyCopyDto = this.policyCopyService.findByPrimaryKey(strEndorseNo);
		origPolicyDto.setPrpCmain(this.backWardPrpCmain(origPolicyDto.getPrpCmain(), policyCopyDto));
		origPolicyDto.setPrpCinsuredList(this.backWardPrpCinsured(origPolicyDto.getPrpCinsuredList(),policyCopyDto));
		origPolicyDto.setPrpCitemCarList(this.backWardPrpCitemCar(origPolicyDto.getPrpCitemCarList(), policyCopyDto));
		//policyDto.setPrpCprofitList(this.backWardPrpCprofit(policyDto.getPrpCprofitList(), policyCopyDto));
		//policyDto.setPrpCengageList(this.backWardPrpCengage(policyDto.getPrpCengageList(), policyCopyDto));
		origPolicyDto.setPrpCitemKindList(this.backWardPrpCitemKind(origPolicyDto.getPrpCitemKindList(), policyCopyDto));
		//policyDto.setPrpCfeeList(this.backWardPrpCfee(policyDto.getPrpCfeeList(), policyCopyDto));
	}

	/**
	 * 回倒保单主表信息
	 * @param prpCmainDto 保单主表对象
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public PrpCmain backWardPrpCmain(PrpCmain prpCmainDto, EndorseDto endorseDto) throws Exception {
		try {
			PrpPmain prpPmainDto = new PrpPmain();
			if (endorseDto.getPrpPmain() != null) {
				prpPmainDto = endorseDto.getPrpPmain();
				PropertyUtils.copyProperties(prpCmainDto, prpPmainDto);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prpCmainDto;
	}
	
	/**
	 * 回倒保单主表信息
	 * @param prpCmainDto 保单主表对象
	 * @param policyCopyDto 保单副本对象
	 * @return
	 * @throws Exception
	 */
	public PrpCmain backWardPrpCmain(PrpCmain prpCmainDto, PolicyCopyDto policyCopyDto) throws Exception {
		try {
			if (policyCopyDto.getPrpCopyMain() != null) {
				PrpCopyMain prpCopyMainDto = policyCopyDto.getPrpCopyMain();
				PropertyUtils.copyProperties(prpCmainDto, prpCopyMainDto);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prpCmainDto;
	}

	/**
	 * 回倒车辆信息信息
	 * @param prpCitemcarDtoList 车辆信息对象
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemCar> backWardPrpCitemCar(List<PrpCitemCar> prpCitemcarDtoList, EndorseDto endorseDto) throws Exception {
		try {
			PrpPitemCar prpPitemCar = new PrpPitemCar();
			PrpCitemCar prpCitemCar = new PrpCitemCar();
			if (prpCitemcarDtoList != null && prpCitemcarDtoList.size() > 0) {
				prpCitemCar = (PrpCitemCar) prpCitemcarDtoList.get(0);
			}
			if (endorseDto.getPrpPitemcarList() != null && endorseDto.getPrpPitemcarList().size() > 0) {
				prpPitemCar = (PrpPitemCar) (endorseDto.getPrpPitemcarList().get(0));

				PrpCitemCarId prpCitemCarId = new PrpCitemCarId();
				PrpPitemCarId prpPitemCarId = prpPitemCar.getId();
				prpCitemCarId.setItemNo(prpPitemCar.getId().getItemNo());
				prpCitemCarId.setPolicyNo(prpPitemCar.getPolicyNo());
				prpPitemCar.setId(null);
				PropertyUtils.copyProperties(prpCitemCar, prpPitemCar);
				prpCitemCar.setId(prpCitemCarId);
				prpPitemCar.setId(prpPitemCarId);
				prpCitemcarDtoList.remove(0);
				prpCitemcarDtoList.add(0, prpCitemCar);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prpCitemcarDtoList;
	}
	
	/**
	 * 回倒车辆信息信息
	 * @param prpCitemcarDtoList 车辆信息对象
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemCar> backWardPrpCitemCar(List<PrpCitemCar> prpCitemcarDtoList, PolicyCopyDto policyCopyDto) throws Exception {
		try {
			PrpCopyItemCar prpCopyItemCar = new PrpCopyItemCar();
			PrpCitemCar prpCitemCar = new PrpCitemCar();
			if (prpCitemcarDtoList != null && prpCitemcarDtoList.size() > 0) {
				prpCitemCar = (PrpCitemCar) prpCitemcarDtoList.get(0);
			}
			if (!CommonUtils.isEmpty(policyCopyDto.getPrpCopyItemCarList())) {
				prpCopyItemCar = policyCopyDto.getPrpCopyItemCarList().get(0);

				PrpCitemCarId prpCitemCarId = new PrpCitemCarId();
				PrpCopyItemCarId prpCopyItemCarId = prpCopyItemCar.getId();
				prpCitemCarId.setItemNo(prpCopyItemCar.getId().getItemNo());
				prpCitemCarId.setPolicyNo(prpCopyItemCar.getPolicyNo());
				prpCopyItemCar.setId(null);
				PropertyUtils.copyProperties(prpCitemCar, prpCopyItemCar);
				prpCitemCar.setId(prpCitemCarId);
				prpCopyItemCar.setId(prpCopyItemCarId);
				prpCitemcarDtoList.remove(0);
				prpCitemcarDtoList.add(0, prpCitemCar);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prpCitemcarDtoList;
	}
	
	/**
	 * 根据批单回滚保单承保被保险人信息
	 * @param prpCinsuredList 被保险人列表
	 * @param policyCopyDto 批单号码
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> backWardPrpCinsured(List<PrpCinsured> prpCinsuredList, PolicyCopyDto policyCopyDto) throws Exception {
		PrpCinsured prpCinsured = null;
		PrpCinsuredId prpCinsuredId = null;
		PrpCopyInsuredId prpCopyInsuredId = null;
		List<PrpCopyInsured> prpCopyInsuredList = policyCopyDto.getPrpCopyInsuredList();
		prpCinsuredList = new ArrayList<PrpCinsured>();
		if (!CommonUtils.isEmpty(prpCopyInsuredList)) {
			// 先将P表prpPitemKind数据转换为C表prpCitemKind数据
			for (PrpCopyInsured prpCopyInsured : prpCopyInsuredList) {
				prpCopyInsuredId = prpCopyInsured.getId();
				prpCinsuredId = new PrpCinsuredId(prpCopyInsured.getPolicyNo(), prpCopyInsuredId.getSerialNo());
				prpCinsured = new PrpCinsured();
				prpCopyInsured.setId(null);
				PropertyUtils.copyProperties(prpCinsured, prpCopyInsured);
				prpCinsured.setId(prpCinsuredId);
				prpCopyInsured.setId(prpCopyInsuredId);
				prpCinsuredList.add(prpCinsured);
			}
		}
		return prpCinsuredList;
	}
	/**
	 * 根据批单回滚保单承保被保险人信息
	 * @param prpCinsuredList 被保险人列表
	 * @param policyCopyDto 批单号码
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> backWardPrpCinsuredNature(List<PrpCinsuredNature> prpCinsuredNatureList, PolicyCopyDto policyCopyDto) throws Exception {
		PrpCinsuredNature prpCinsuredNature = null;
		PrpCinsuredNatureId prpCinsuredNatureId = null;
		PrpCopyInsuredNatureId prpCopyInsuredNatureId = null;
		List<PrpCopyInsuredNature> prpCopyInsuredNatureList = policyCopyDto.getPrpCopyInsuredNatureList();
		prpCinsuredNatureList = new ArrayList<PrpCinsuredNature>();
		if (!CommonUtils.isEmpty(prpCopyInsuredNatureList)) {
			// 先将P表prpPitemKind数据转换为C表prpCitemKind数据
			for (PrpCopyInsuredNature tempPrpCopyInsuredNature : prpCopyInsuredNatureList) {
				prpCopyInsuredNatureId = tempPrpCopyInsuredNature.getId();
				prpCinsuredNatureId = new PrpCinsuredNatureId(tempPrpCopyInsuredNature.getPolicyNo(), prpCopyInsuredNatureId.getSerialNo());
				prpCinsuredNature = new PrpCinsuredNature();
				tempPrpCopyInsuredNature.setId(null);
				PropertyUtils.copyProperties(prpCinsuredNature, tempPrpCopyInsuredNature);
				prpCinsuredNature.setId(prpCinsuredNatureId);
				tempPrpCopyInsuredNature.setId(prpCopyInsuredNatureId);
				prpCinsuredNatureList.add(prpCinsuredNature);
			}
		}
		return prpCinsuredNatureList;
	}
	/**
	 * 回倒优惠信息
	 * @param prpCprofitDtoList  优惠信息对象
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public List<PrpCprofit> backWardPrpCprofit(List<PrpCprofit> prpCprofitDtoList, EndorseDto endorseDto) throws Exception {
		PrpPprofit prpPprofitDto = new PrpPprofit();
		PrpCprofit prpCprofitDto = new PrpCprofit();
		PrpPprofitId prpPprofitId = null;
		int strcurr = 0;
		if (endorseDto.getPrpPprofitList() != null) {
			for (int i = 0; i < endorseDto.getPrpPprofitList().size(); i++) {
				prpPprofitDto = (PrpPprofit) endorseDto.getPrpPprofitList().get(i);
				if (!"".equals(prpPprofitDto.getFlag().trim())) {
					if (prpPprofitDto.getFlag().substring(0, 1).equals("U")) {
						prpCprofitDto = new PrpCprofit();
						strcurr = this.searchPrpCprofit(prpPprofitDto.getId().getProfitType(), prpPprofitDto.getId().getItemKindNo().intValue(), prpCprofitDtoList);
						if (strcurr >= 0) {
							PrpCprofitId prpCprofitId = new PrpCprofitId();
							prpPprofitId = prpPprofitDto.getId();
							prpCprofitId.setItemKindNo(prpPprofitDto.getId().getItemKindNo());
							prpCprofitId.setPolicyNo(prpPprofitDto.getPolicyNo());
							prpCprofitId.setProfitType(prpPprofitDto.getId().getProfitType());
							prpPprofitDto.setId(null);
							PropertyUtils.copyProperties(prpCprofitDto, prpPprofitDto);
							prpCprofitDto.setId(prpCprofitId);
							prpPprofitDto.setId(prpPprofitId);
							prpCprofitDtoList.set(strcurr, prpCprofitDto);
						}
					}
					if (prpPprofitDto.getFlag().substring(0, 1).equals("I")) {
						prpCprofitDto = new PrpCprofit();
						strcurr = this.searchPrpCprofit(prpPprofitDto.getId().getProfitType(), prpPprofitDto.getId().getItemKindNo().intValue(), prpCprofitDtoList);

						if (strcurr >= 0) {
							prpCprofitDtoList.remove(strcurr);
						}
					}
				}
			}
		}

		return prpCprofitDtoList;
	}

	/**
	 * 根据险别序号及折扣类型寻找下标
	 * @param iProfitType 折扣类型
	 * @param iItemKindNo 险别序号
	 * @param listTemp 折扣信息
	 * @return 下标
	 * @throws Exception
	 */
	public int searchPrpCprofit(String iProfitType, int iItemKindNo, List<PrpCprofit> listTemp) throws Exception {
		int icurr = 0;
		int iFindFlag = 0;
		if (listTemp != null) {
			for (int i = 0; i < listTemp.size(); i++) {
				PrpCprofit prpCprofitDto = (PrpCprofit) listTemp.get(i);
				if ((prpCprofitDto.getId().getItemKindNo() == iItemKindNo) && prpCprofitDto.getId().getProfitType().trim().equals(iProfitType)) {
					icurr = i;
					iFindFlag = 1;
				}
			}
		}
		if (iFindFlag == 0) {
			icurr = -1;
		}
		return icurr;
	}


	/**
	 * 回倒特别约定信息
	 * @param prpCengageList 特别约定表
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public List<PrpCengage> backWardPrpCengage(List<PrpCengage> prpCengageList, EndorseDto endorseDto) throws Exception {
		PrpPengage prpPengageDto = new PrpPengage();
		PrpCengage prpCengageDto = new PrpCengage();
		PrpPengageId prpPengageId = null;
		int strcurr = 0;
		int iFindFlag = 0;
		if (endorseDto.getPrpPengageList() != null) {
			for (int i = 0; i < endorseDto.getPrpPengageList().size(); i++) {
				prpPengageDto = (PrpPengage) endorseDto.getPrpPengageList().get(i);
				prpPengageId = prpPengageDto.getId();
				if (prpPengageDto.getFlag().substring(0, 1).equals("U")) {
					prpCengageDto = new PrpCengage();
					strcurr = this.searchPrpCengage(prpPengageDto.getId().getSerialNo().intValue(), prpPengageDto.getId().getLineNo().intValue(), prpCengageList);
					if (strcurr >= 0) {
						PrpCengageId prpCengageId = new PrpCengageId();
						prpCengageId.setLineNo(prpPengageDto.getId().getLineNo());
						prpCengageId.setPolicyNo(prpPengageDto.getPolicyNo());
						prpCengageId.setSerialNo(prpPengageDto.getId().getSerialNo());
						prpPengageDto.setId(null);
						PropertyUtils.copyProperties(prpCengageDto, prpPengageDto);
						prpCengageDto.setId(prpCengageId);
						prpCengageList.set(strcurr, prpCengageDto);
					}
				}
				if (prpPengageDto.getFlag().substring(0, 1).equals("I")) {
					prpCengageDto = new PrpCengage();
					strcurr = this.searchPrpCengage(prpPengageDto.getId().getSerialNo().intValue(), prpPengageDto.getId().getLineNo().intValue(), prpCengageList);
					if (strcurr >= 0) {
						prpCengageList.remove(strcurr);
					}
				}
				if (prpPengageDto.getFlag().substring(0, 1).equals("D")) {
					prpCengageDto = new PrpCengage();
					// 根据序号查找插入点
					for (int j = 0; j < prpCengageList.size(); j++) {
						prpCengageDto = (PrpCengage) prpCengageList.get(j);
						if (prpPengageDto.getId().getSerialNo() < prpCengageDto.getId().getSerialNo()) {
							strcurr = j;
							iFindFlag = 1;
							break;
						}
					}
					if (iFindFlag == 0)
					// 没找到插入点为最後
					{
						strcurr = prpCengageList.size();
					}
					// 将p记录转为c记录
					PrpCengageId prpCengageId = new PrpCengageId();
					prpCengageId.setLineNo(prpPengageDto.getId().getLineNo());
					prpCengageId.setPolicyNo(prpPengageDto.getPolicyNo());
					prpCengageId.setSerialNo(prpPengageDto.getId().getSerialNo());
					prpPengageDto.setId(null);
					PropertyUtils.copyProperties(prpCengageDto, prpPengageDto);
					prpCengageDto.setId(prpCengageId);
					// 在指定位置插入
					prpCengageList.add(strcurr, prpCengageDto);
				}
				if (prpPengageDto.getFlag().substring(0, 1).equals("B")) {
					prpCengageDto = new PrpCengage();
					strcurr = this.searchPrpCengage(prpPengageDto.getId().getSerialNo().intValue(), prpPengageDto.getId().getLineNo().intValue(), prpCengageList);

					if (strcurr >= 0) {
						PrpCengageId prpCengageId = new PrpCengageId();
						prpCengageId.setLineNo(prpPengageDto.getId().getLineNo());
						prpCengageId.setPolicyNo(prpPengageDto.getPolicyNo());
						prpCengageId.setSerialNo(prpPengageDto.getId().getSerialNo());
						prpPengageDto.setId(null);
						PropertyUtils.copyProperties(prpCengageDto, prpPengageDto);
						prpCengageDto.setId(prpCengageId);
						prpCengageList.set(strcurr, prpCengageDto);
					}
				}
				prpPengageDto.setId(prpPengageId);
			}

		}
		return prpCengageList;
	}

	/**
	 * 根据地址序号寻找下标
	 * @param iSerialNo 序号
	 * @param iLineNo 行号
	 * @param listTemp 特别约定
	 * @return 下标
	 * @throws Exception
	 */
	public int searchPrpCengage(int iSerialNo, int iLineNo, List<PrpCengage> listTemp) throws Exception {
		int icurr = 0;
		int iFindFlag = 0;
		if (listTemp != null) {
			for (int i = 0; i < listTemp.size(); i++) {
				PrpCengage prpCengageDto = (PrpCengage) listTemp.get(i);
				if ((prpCengageDto.getId().getSerialNo() == iSerialNo) && ((prpCengageDto.getId().getLineNo() == iLineNo))) {
					icurr = i;
					iFindFlag = 1;
				}
			}
		}
		if (iFindFlag == 0) {
			icurr = -1;
		}
		return icurr;
	}
	
	/**
	 * 根据批单回滚保单承保险别信息
	 * @param prpCitemKindList 险别对象
	 * @param policyCopyDto 批单号码
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> backWardPrpCitemKind(List<PrpCitemKind> prpCitemKindList, PolicyCopyDto policyCopyDto) throws Exception {
		PrpCitemKind prpCitemKind = null;
		PrpCitemKindId prpCitemKindId = null;
		PrpCopyItemKindId prpCopyItemKindId = null;
		List<PrpCopyItemKind> prpCopyItemKindList = policyCopyDto.getPrpCopyItemKindList();
		prpCitemKindList = new ArrayList<PrpCitemKind>();
		if (!CommonUtils.isEmpty(prpCopyItemKindList)) {
			// 先将P表prpPitemKind数据转换为C表prpCitemKind数据
			for (PrpCopyItemKind prpCopyItemKind : prpCopyItemKindList) {
				prpCopyItemKindId = prpCopyItemKind.getId();
				prpCitemKindId = new PrpCitemKindId(prpCopyItemKind.getPolicyNo(), prpCopyItemKindId.getItemKindNo());
				prpCitemKind = new PrpCitemKind();
				prpCopyItemKind.setId(null);
				PropertyUtils.copyProperties(prpCitemKind, prpCopyItemKind);
				prpCitemKind.setId(prpCitemKindId);
				prpCopyItemKind.setId(prpCopyItemKindId);
				prpCitemKindList.add(prpCitemKind);
			}
		}
		return prpCitemKindList;
	}
	/**
	 * 根据批单回滚保单承保险别信息
	 * @param prpCitemKindList 险别对象
	 * @param policyCopyDto 批单号码
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> backWardPrpCitemKind(List<PrpCitemKind> prpCitemKindList,String familyNo,String endorseNo) throws Exception {
		PrpCitemKind prpCitemKind = null;
		PrpCitemKindId prpCitemKindId = null;
		PrpCopyItemKindId prpCopyItemKindId = null;
		List<PrpCopyItemKind> prpCopyItemKindList = policyCopyService.findPrpCopyItemKind(familyNo,endorseNo);
		prpCitemKindList = new ArrayList<PrpCitemKind>();
		if (!CommonUtils.isEmpty(prpCopyItemKindList)) {
			// 先将P表prpPitemKind数据转换为C表prpCitemKind数据
			for (PrpCopyItemKind prpCopyItemKind : prpCopyItemKindList) {
				prpCopyItemKindId = prpCopyItemKind.getId();
				prpCitemKindId = new PrpCitemKindId(prpCopyItemKind.getPolicyNo(), prpCopyItemKindId.getItemKindNo());
				prpCitemKind = new PrpCitemKind();
				prpCopyItemKind.setId(null);
				PropertyUtils.copyProperties(prpCitemKind, prpCopyItemKind);
				prpCitemKind.setId(prpCitemKindId);
				prpCopyItemKind.setId(prpCopyItemKindId);
				prpCitemKindList.add(prpCitemKind);
			}
		}
		return prpCitemKindList;
	}
	/**
	 * 根据批单回滚保单承保险别信息
	 * @param prpCitemKindList 险别对象
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> backWardPrpCitemKind(List<PrpCitemKind> prpCitemKindList, EndorseDto endorseDto) throws Exception {
		PrpCitemKind prpCitemKindDto = null;
		PrpCitemKindId prpCitemKindId = null;
		PrpPitemKindId prpPitemKindId = null;
		Map<String,PrpCitemKind> prpPitemKindMap = new HashMap<String,PrpCitemKind>();
		if(endorseDto.getPrpPitemKindList()!=null){
			//先将P表prpPitemKind数据转换为C表prpCitemKind数据
			for(PrpPitemKind prpPitemKind : endorseDto.getPrpPitemKindList()){
				prpCitemKindId = new PrpCitemKindId(prpPitemKind.getPolicyNo(),prpPitemKind.getId().getItemKindNo());
				prpPitemKindId = prpPitemKind.getId();
				prpCitemKindDto = new PrpCitemKind();
				prpPitemKind.setId(null);//清空ID，避免赋值属性报错
				PropertyUtils.copyProperties(prpCitemKindDto, prpPitemKind);
				prpCitemKindDto.setId(prpCitemKindId);
				prpPitemKind.setId(prpPitemKindId);//还原ID
				prpPitemKindMap.put(prpCitemKindId.getPolicyNo()+","+prpCitemKindId.getItemKindNo(), prpCitemKindDto);
			}
		}
		List<PrpCitemKind> tempList = new ArrayList<PrpCitemKind>();
		String tempKey = "";
		String endorseFlag = "";//批改状态
		PrpCitemKind tempKind = null;
		Iterator<PrpCitemKind> it = prpCitemKindList.iterator();
		while (it.hasNext()) {
			PrpCitemKind prpCitemKind = it.next();
			tempKey = prpCitemKind.getId().getPolicyNo()+","+prpCitemKind.getId().getItemKindNo();
			if(prpPitemKindMap.containsKey(tempKey)){//有过批改的险别
				tempKind = prpPitemKindMap.get(tempKey);
				endorseFlag = tempKind.getFlag().substring(0, 1);
				if("I".equals(endorseFlag)){//本次批增的则换原之前状态（本次批增前无此险别）
					continue;
				}
				//批减、批改、批退的情况，用P表还原
				tempList.add(tempKind);
			}else{//无批改的则用C表数据直接存储
				prpCitemKindDto = new PrpCitemKind();
				PropertyUtils.copyProperties(prpCitemKindDto, prpCitemKind);
				tempList.add(prpCitemKindDto);
			}
		}
		return tempList;
	}

	/**
	 * 根据险别序号寻找下标
	 * @param iItemKindNo 险别序号
	 * @param listTemp 险别对象
	 * @return 下标
	 * @throws Exception
	 */
	public int searchPrpCitemKind(int iItemKindNo, List<PrpCitemKind> listTemp) throws Exception {
		int icurr = 0;
		int iFindFlag = 0;
		if (listTemp != null) {
			for (int i = 0; i < listTemp.size(); i++) {
				PrpCitemKind prpCitemKindDto = (PrpCitemKind) listTemp.get(i);
				if (prpCitemKindDto.getId().getItemKindNo().intValue() == iItemKindNo) {
					icurr = i;
					iFindFlag = 1;
				}
			}
		}
		if (iFindFlag == 0) {
			icurr = -1;
		}
		return icurr;
	}

	/**
	 * 回倒金额信息
	 * @param prpCfeeList 保单金额对象
	 * @param endorseDto 批单对象
	 * @return
	 * @throws Exception
	 */
	public List<PrpCfee> backWardPrpCfee(List<PrpCfee> prpCfeeList, EndorseDto endorseDto) throws Exception {
		PrpPfee prpPfeeDto = new PrpPfee();
		PrpCfee prpCfeeDto = new PrpCfee();
		double dblChgAmount = 0;
		double dblAmount = 0;
		double dblChgPremium = 0;
		double dblPremium = 0;
		if (endorseDto.getPrpPfeeList() != null) {
			for (int i = 0; i < endorseDto.getPrpPfeeList().size(); i++) {
				prpPfeeDto = (PrpPfee) endorseDto.getPrpPfeeList().get(i);
				for (int j = 0; j < prpCfeeList.size(); j++) {
					prpCfeeDto = (PrpCfee) prpCfeeList.get(j);
					if (prpCfeeDto.getId().getCurrency().equals(prpPfeeDto.getId().getCurrency())) {
						dblChgAmount = prpPfeeDto.getChgAmount().doubleValue();
						dblAmount = prpCfeeDto.getAmount().doubleValue();
						dblAmount = dblAmount - dblChgAmount;
						dblChgPremium = prpPfeeDto.getChgPremium().doubleValue();
						dblPremium = prpCfeeDto.getPremium().doubleValue();
						dblPremium = dblPremium - dblChgPremium;
						prpCfeeDto.setAmount(dblAmount);
						prpCfeeDto.setPremium(dblPremium);
						prpCfeeList.set(j, prpCfeeDto);
					}
				}
			}
		}
		return prpCfeeList;
	}

	/**
	 * 获得转换後的保单标的险别 param blPrpCitemKind：保单标的险别 param iClauseType：条款类别 return
	 * 获得转换後的保单标的险别 throws UserException,Exception
	 * @param prpCitemKindList 保单标的险别
	 * @param iClauseType 条款类别
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> TransKind(List<PrpCitemKind> prpCitemKindList, String iClauseType) throws Exception {
		String strKindCode = "";

		if (prpCitemKindList != null) {
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				PrpCitemKind prpCitemKind = prpCitemKindList.get(i);
				strKindCode = prpCitemKind.getKindCode();
				if (strKindCode.equals(ConstantCodes.KINDCODE_D_A)) {
					if (iClauseType.equals("F22"))
						strKindCode = "BF22";
					else
						strKindCode = strKindCode + iClauseType;
				} else if (strKindCode.equals(ConstantCodes.KINDCODE_D_B)) {
					if (iClauseType.equals("F31") || iClauseType.equals("F32") || iClauseType.equals("F33") || iClauseType.equals("F34") || iClauseType.equals("F35") || iClauseType.equals("F36")) {
						strKindCode = "BF22";
					} else {
						strKindCode = strKindCode + iClauseType;
					}
				} else {
				}
				prpCitemKind.setKindCode(strKindCode);
			}
		}
		return prpCitemKindList;
	}
	/***
	 * 出險時間標準化
	 * @param strDamageDate 出險日期 yyyy-mm-dd
	 * @param strDamageHour 出險小時 0-23
	 * @return
	 */
	private String[] getFormatDamageTime(String strDamageDate, String strDamageHour) {
		int theDamageHour = 0;
		if (strDamageHour != null && strDamageHour.length() > 1) {
			theDamageHour = Integer.parseInt(strDamageHour.substring(0, 2));
		}
		if (strDamageDate != null && strDamageDate.length() > 9) {
			strDamageDate = strDamageDate.substring(0, 10);
		}
		return new String[] { strDamageDate, String.valueOf(theDamageHour) };
	}

	/***
	 * 設置險時的承保訊息SQL
	 * @param tableName 查詢核心資料表
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 */
	private String getBackSql(String tableName, String policyNo, String damageDate, String damageHour) {
		String[] str = getFormatDamageTime(damageDate, damageHour);
		damageDate = str[0];
		damageHour = str[1];
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.* ");
		sql.append(" FROM ").append(tableName).append(" T,");
		sql.append(" PRPPHEAD P ");
		sql.append(" WHERE T.ENDORSENO = P.ENDORSENO ");
		sql.append(" AND P.POLICYNO = '").append(policyNo).append("'");
		sql.append(" AND ( P.VALIDDATE > TO_DATE('").append(damageDate).append("', 'YYYY-MM-DD') ");
		sql.append(" OR (");
		sql.append(" P.VALIDDATE = TO_DATE('").append(damageDate).append("', 'YYYY-MM-DD') ");
		sql.append(" AND P.VALIDHOUR > ").append(damageHour);
		sql.append(" ) ) ");
		sql.append(" AND ( P.UNDERWRITEFLAG = '1' OR P.UNDERWRITEFLAG = '3' ) ");
		sql.append(" ORDER BY P.INPUTDATE ASC, P.ENDORSETIMES ASC ");
		return sql.toString();
	}
	
	/**
	 * 從copy取出險時保單主訊息
	 * @param policyNo
	 * @param damageDate
	 * @param damageHour
	 * @return
	 * @throws Exception
	 */
	public PrpCmain findPrpCmain(String policyNo, String damageDate, String damageHour) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCmainFromCopy(endorseNo,policyNo);
	}
	/***
	 * 從copy取出險時保單主訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @return
	 * @throws Exception
	 */
	public PrpCmain findPrpCmainFromCopy(String endorseNo , String policyNo ) throws Exception {
		String statements = " SELECT * FROM PRPCOPYMAIN WHERE ENDORSENO = '"+ endorseNo +"' ";
		List<PrpCopyMain> list = this.commonService.findByStatements(statements, PrpCopyMain.class);
		if (!CommonUtils.isEmpty(list)) {
			PrpCmain prpCmain = new PrpCmain();
			PropertyUtils.copyProperties(prpCmain, list.get(0));
			return prpCmain;
		}
		return this.prpCmainService.findByPrimaryKey(policyNo);
	}
	/**
	 * 取出險時車輛訊息
	 * @param policyNo
	 * @param damageDate
	 * @param damageHour
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemCar> findPrpCitemCar(String policyNo, String damageDate, String damageHour) throws Exception {
		String statements = this.getBackSql("PrpPitemCar", policyNo, damageDate, damageHour);
		List<PrpPitemCar> list = this.commonService.findByStatements(statements, PrpPitemCar.class);
		if (!CommonUtils.isEmpty(list)) {
			List<PrpCitemCar> prpCitemCarList = new ArrayList<PrpCitemCar>();
			PrpCitemCar prpCitemCar = null;
			PrpCitemCarId prpCitemCarId = null;
			PrpPitemCarId prpPitemCarId = null;
			String endorseNo = null;
			for (PrpPitemCar prpPitemCar : list) {
				prpPitemCarId = prpPitemCar.getId();
				if(endorseNo != null && !endorseNo.equals(prpPitemCarId.getEndorseNo())){
					break; 
				}
				endorseNo = prpPitemCarId.getEndorseNo();
				prpCitemCarId = new PrpCitemCarId(prpPitemCar.getPolicyNo(), prpPitemCarId.getItemNo());
				prpCitemCar = new PrpCitemCar();
				prpPitemCar.setId(null);
				PropertyUtils.copyProperties(prpCitemCar, prpPitemCar);
				prpCitemCar.setId(prpCitemCarId);
				prpPitemCar.setId(prpPitemCarId);
				prpCitemCarList.add(prpCitemCar);
			}
			return prpCitemCarList;
		}
		statements = " POLICYNO = '" + policyNo + "' ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		return this.prpCitemCarService.findPrpCitemCar(queryRule);
	}
	
	/***
	 * 獲取出險時間之后的保單批改訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 * @throws Exception
	 */
	public List<PrpPhead> findPrpPhead(String policyNo, String damageDate, String damageHour) throws Exception {
		String[] str = getFormatDamageTime(damageDate, damageHour);
		damageDate = str[0];
		damageHour = str[1];
		String iWherePart = "PolicyNo = '" + policyNo + "'" 
				+ " AND (ValidDate >to_date('" + damageDate + "','yyyy-MM-dd') "
				 +" OR (ValidDate=to_date('" + damageDate + "','yyyy-MM-dd') AND ValidHour>" + damageHour + "))"
				+ " AND UnderWriteFlag in ('1', '3') " + " ORDER BY InputDate DESC,EndorseTimes DESC ";
		return this.endorseService.findByPrpPheadConditions(iWherePart);
	}
	/**
	 * 取出險時折扣訊息
	 * @param prpPheadList 出險時間之后的批改訊息
	 * @param policyNo 保單信息
	 * @return
	 * @throws Exception
	 */
	public List<PrpCprofit> findPrpCprofit(List<PrpPhead> prpPheadList, String policyNo ) throws Exception {
		// 没有找到符合条件的批单则返回当前保单
		String statements = " POLICYNO = '" + policyNo + "' ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		PolicyDto policyDto = new PolicyDto();
		policyDto.setPrpCprofitList(this.prpCprofitService.findPrpCprofit(queryRule));
		if (!CommonUtils.isEmpty(prpPheadList)) {
			EndorseDto endorseDto = new EndorseDto();
			PrpPhead prpPhead = null;
			for (int i = 0; i < prpPheadList.size(); i++) {
				prpPhead = prpPheadList.get(i);
				String conditions = " endorseNo = '" + prpPhead.getEndorseNo() + "'";
				endorseDto.setPrpPprofitList(this.prpPprofitService.findByConditions(conditions, 0, 0));
				policyDto.setPrpCprofitList(this.backWardPrpCprofit(policyDto.getPrpCprofitList(), endorseDto));
			}
		}
		return policyDto.getPrpCprofitList();
	}
	/**
	 * 取出險時折扣訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 * @throws Exception
	 */
	public List<PrpCprofit> findPrpCprofit(String policyNo, String damageDate, String damageHour) throws Exception {
		List<PrpPhead> prpPheadList = this.findPrpPhead(policyNo, damageDate, damageHour);
		return this.findPrpCprofit(prpPheadList, policyNo);
	}
	/**
	 * 取出險時特別約定訊息
	 * @param prpPheadList 出險時間之后的批改訊息
	 * @param policyNo 保單號碼
	 * @return
	 * @throws Exception
	 */
	public List<PrpCengage> findPrpCengage(List<PrpPhead> prpPheadList, String policyNo ) throws Exception {
		String statements = " POLICYNO = '" + policyNo + "' ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		PolicyDto policyDto = new PolicyDto();
		policyDto.setPrpCengageList(this.prpCengageService.findPrpCengage(queryRule));
		if (!CommonUtils.isEmpty(prpPheadList)) {
			EndorseDto endorseDto = new EndorseDto();
			PrpPhead prpPhead = null;
			for (int i = 0; i < prpPheadList.size(); i++) {
				prpPhead = prpPheadList.get(i);
				String conditions = " endorseNo = '" + prpPhead.getEndorseNo() + "'";
				endorseDto.setPrpPengageList(this.prpPengageService.findByConditions(conditions, 0, 0));
				policyDto.setPrpCengageList(this.backWardPrpCengage(policyDto.getPrpCengageList(), endorseDto));
			}
		}
		return policyDto.getPrpCengageList();
	}
	/**
	 * 取出險時特別約定訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 * @throws Exception
	 */
	public List<PrpCengage> findPrpCengage(String policyNo, String damageDate, String damageHour) throws Exception {
		List<PrpPhead> prpPheadList = this.findPrpPhead(policyNo, damageDate, damageHour);
		return this.findPrpCengage(prpPheadList, policyNo);
	}
	/**
	 * 取出險時承保險別訊息
	 * @param prpPheadList 出險時間之后的批改訊息
	 * @param policyNo 保單號碼
	 * @param riskCode 險種號碼
	 * @param policyType 保單類型
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> findPrpCitemKind(List<PrpPhead> prpPheadList, String policyNo , String riskCode , String policyType) throws Exception {
		String statements = " POLICYNO = '" + policyNo + "' ";
		PolicyDto policyDto = new PolicyDto();
		if(policyType == null){
			PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(policyNo);
			if(prpCmain !=null ){
				policyType = prpCmain.getPolicyType();
				riskCode = prpCmain.getRiskCode();
			}
		}
		String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		if ((!ConstantCodes.CLASSCODE_D.equals(strRiskType)) && "02".equals(policyType)) { // 判断非车险並且是团险
			policyDto.setPrpCitemKindList(prpCitemKindService.findByConditionsDistinct(statements, 0, 0));
		} else {
			policyDto.setPrpCitemKindList(prpCitemKindService.findPrpCitemKind(QueryRule.getInstance().addEqual("id.policyNo", policyNo).addAscOrder("id.itemKindNo")));
		}
		if (!CommonUtils.isEmpty(prpPheadList)) {
			EndorseDto endorseDto = new EndorseDto();
			PrpPhead prpPhead = null;
			for (int i = 0; i < prpPheadList.size(); i++) {
				prpPhead = prpPheadList.get(i);
				String conditions = " endorseNo = '" + prpPhead.getEndorseNo() + "'";
				endorseDto.setPrpPitemKindList(this.prpPitemKindService.findByConditions(conditions, 0, 0));
				policyDto.setPrpCitemKindList(this.backWardPrpCitemKind(policyDto.getPrpCitemKindList(), endorseDto));
			}
		}
		return policyDto.getPrpCitemKindList();
	}
	/**
	 * 取出險時承保險別訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param riskCode 險種號碼
	 * @param policyType 保單類型
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> findPrpCitemKind(String policyNo, String damageDate, String damageHour, String riskCode , String policyType) throws Exception {
		List<PrpPhead> prpPheadList = this.findPrpPhead(policyNo, damageDate, damageHour);
		return this.findPrpCitemKind(prpPheadList, policyNo ,riskCode ,policyType);
	}
	/**
	 * 取出傷害險被保險人出險時承保險別訊息
	 * @param prpPheadList 出險時間之后的批改訊息
	 * @param policyNo 保單號碼
	 * @param riskCode 險種號碼
	 * @param familyNo 被保險人序號
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> findPrpCitemKind(List<PrpPhead> prpPheadList, String policyNo , int familyNo) throws Exception {
		PolicyDto policyDto = new PolicyDto();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		if (familyNo > 0) {
			queryRule.addEqual("familyNo", familyNo);
		}
		queryRule.addAscOrder("id.itemKindNo");
		policyDto.setPrpCitemKindList(this.prpCitemKindService.findPrpCitemKind(queryRule));
		if (!CommonUtils.isEmpty(prpPheadList)) {
			EndorseDto endorseDto = new EndorseDto();
			PrpPhead prpPhead = null;
			for (int i = 0; i < prpPheadList.size(); i++) {
				prpPhead = prpPheadList.get(i);
				String conditions = " endorseNo = '" + prpPhead.getEndorseNo() + "' and familyNo = " + familyNo +" order by itemKindNo ";
				endorseDto.setPrpPitemKindList(this.prpPitemKindService.findByConditions(conditions, 0, 0));
				policyDto.setPrpCitemKindList(this.backWardPrpCitemKind(policyDto.getPrpCitemKindList(), endorseDto));
			}
		}
		return policyDto.getPrpCitemKindList();
	}
	/**
	 * 取出傷害險被保險人出險時承保險別訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param familyNo 被保險人序號
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> findPrpCitemKind(String policyNo, String damageDate, String damageHour, int familyNo) throws Exception {
		List<PrpPhead> prpPheadList = this.findPrpPhead(policyNo, damageDate, damageHour);
		return this.findPrpCitemKind(prpPheadList, policyNo ,familyNo);
	}
	
	/***
	 * 取被保險人
	 * @param prpCinsuredList
	 * @param insuredCode
	 * @param insuredName
	 * @return
	 */
	public PrpCinsured getPrpCinsured(List<PrpCinsured> prpCinsuredList, String insuredCode, String insuredName) {
		PrpCinsured c = null;// 第一個被保險人
		PrpCinsured c0 = null;
		PrpCinsured c1 = null;
		for (PrpCinsured prpCinsured : prpCinsuredList) {
			if ("1".equals(prpCinsured.getInsuredFlag())) {
				// 如果找到代碼或身份證好一樣的，則直接返回
				if (!CommonUtils.isEmpty(insuredCode) 
						&& (insuredCode.equals(prpCinsured.getInsuredCode()) || insuredCode.equals(prpCinsured.getIdentifyNumber()))
						&& !CommonUtils.isEmpty(insuredName)
						&& insuredName.equals(prpCinsured.getInsuredName())) {
					return prpCinsured;
				}
				if (c == null) {
					c = prpCinsured;
				}
				// 找不到則查找被保險人代码一致的
				if (!CommonUtils.isEmpty(insuredCode) && c1 == null 
						&& (insuredCode.equals(prpCinsured.getInsuredCode()) || insuredCode.equals(prpCinsured.getIdentifyNumber()))) {
					c1 = prpCinsured;
				}
				// 找不到則查找被保險人名稱一致的
				if (!CommonUtils.isEmpty(insuredName) && c0 == null && insuredName.equals(prpCinsured.getInsuredName())) {
					c0 = prpCinsured;
				}
			}
		}
		return c1 == null ? (c0 == null ? c : c0 ) : c1;
	}
	/***
	 * 取自然人訊息
	 * @param prpCinsuredNatureList
	 * @param serialNo
	 * @return
	 */
	public PrpCinsuredNature getPrpCinsuredNature(List<PrpCinsuredNature> prpCinsuredNatureList, int serialNo) {
		for (PrpCinsuredNature prpCinsuredNature : prpCinsuredNatureList) {
			if(prpCinsuredNature.getId().getSerialNo() == serialNo){
				return prpCinsuredNature;
			}
		}
		return null;
	}
	/***
	 * 取自然人訊息序號
	 * @param prpCinsuredList
	 * @param insuredCode
	 * @param insuredName
	 * @return
	 */
	public int[] getPrpCinsuredSerialNos(List<PrpCinsured> prpCinsuredList) {
		int length = prpCinsuredList.size();
		int[] array = new int[length];
		for (int index = 0; index < length; index++) {
			array[index] = prpCinsuredList.get(index).getId().getSerialNo();
		}
		return array;
	}	
	/**
	 * 取出險時保單支付訊息
	 * @param prpPheadList 出險時間之后的批改訊息
	 * @param policyNo 保單號碼
	 * @return
	 * @throws Exception
	 */
	public List<PrpCfee> findPrpCfee(List<PrpPhead> prpPheadList, String policyNo ) throws Exception {
		String statements = " POLICYNO = '" + policyNo + "' ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		PolicyDto policyDto = new PolicyDto();
		policyDto.setPrpCfeeList(this.prpCfeeService.findPrpCfee(queryRule));
		if (!CommonUtils.isEmpty(prpPheadList)) {
			EndorseDto endorseDto = new EndorseDto();
			PrpPhead prpPhead = null;
			for (int i = 0; i < prpPheadList.size(); i++) {
				prpPhead = prpPheadList.get(i);
				String conditions = " endorseNo = '" + prpPhead.getEndorseNo() + "'";
				endorseDto.setPrpPfeeList(this.prpPfeeService.findByConditions(conditions, 0, 0));
				policyDto.setPrpCfeeList(this.backWardPrpCfee(policyDto.getPrpCfeeList(), endorseDto));
			}
		}
		return policyDto.getPrpCfeeList();
	}
	/**
	 * 取出險時保單支付訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 * @throws Exception
	 */
	public List<PrpCfee> findPrpCfee(String policyNo, String damageDate, String damageHour) throws Exception {
		List<PrpPhead> prpPheadList = this.findPrpPhead(policyNo, damageDate, damageHour);
		return this.findPrpCfee(prpPheadList, policyNo);
	}
	/**
	 * 從copy表取出險時的批單號碼
	 * @param policyNo
	 * @param damageDate
	 * @param damageHour
	 * @return 
	 * @throws Exception 
	 */
	public String getEndorseNo(String policyNo, String damageDate, String damageHour) throws Exception{
		String[] str = getFormatDamageTime(damageDate, damageHour);
		damageDate = str[0];
		damageHour = str[1];
		String strEndorseNo = policyCopyService.getBackWardEndorseNo(policyNo, damageDate, damageHour);
		return CommonUtils.isEmpty(strEndorseNo) ? policyNo : strEndorseNo ;
	}
	
	/**
	 * 被保險人查詢條件組織
	 * @param insuredCode
	 * @param insuredName
	 * @return
	 */
	private String getInsuredSql(String insuredCode, String insuredName){
		if (!CommonUtils.isEmpty(insuredCode) && !CommonUtils.isEmpty(insuredName)) {
			return " ( INSUREDFLAG != '1' OR ( ( INSUREDCODE = '" + insuredCode + "' OR IDENTIFYNUMBER = '" + insuredCode + "') AND INSUREDNAME = '" + insuredName + "' ) ) ";
		} else if (!CommonUtils.isEmpty(insuredCode)) {
			return " ( INSUREDFLAG != '1' OR INSUREDCODE = '" + insuredCode + "' OR IDENTIFYNUMBER = '" + insuredCode + "' ) ";
		} else if (!CommonUtils.isEmpty(insuredName)) {
			return " ( INSUREDFLAG != '1' OR INSUREDNAME = '" + insuredName + "' ) ";
		} else {
			return "";
		}
	}
	
	/**
	 * 被保險人查詢條件組織(FOR ConstantCodes.CLASSCODE_E)
	 * mantis：CLM0210，處理人員：DP0713，需求單編號：新核心-TA被保人姓名難字無法進行備案登記處理
	 * @param insuredCode
	 * @param insuredName
	 * @return
	 */
	private String getInsuredSqlForCall_E(String insuredCode, String insuredName){
		if (!CommonUtils.isEmpty(insuredCode) && !CommonUtils.isEmpty(insuredName)) {
			return " ( INSUREDFLAG = '1' OR ( ( INSUREDCODE = '" + insuredCode + "' OR IDENTIFYNUMBER = '" + insuredCode + "') AND INSUREDNAME = '" + insuredName + "' ) ) ";
		} else if (!CommonUtils.isEmpty(insuredCode)) {
			return " ( INSUREDFLAG = '1' OR INSUREDCODE = '" + insuredCode + "' OR IDENTIFYNUMBER = '" + insuredCode + "' ) ";
		} else if (!CommonUtils.isEmpty(insuredName)) {
			return " ( INSUREDFLAG = '1' OR INSUREDNAME = '" + insuredName + "' ) ";
		} else {
			return "";
		}
	}
	
	/*
	 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 --- start
	  原因： 在查詢時因使用名字，當姓名中有不在big-5編碼內的字時就會造成找不到資料而出現nullpointException錯誤
	  所以新增此method by identify query
	*/
	/**
	 * 被保險人查詢條件組織
	 * @param insuredCode
	 * @param insuredName
	 * @return
	 */
	private String getInsuredSqlById(String insuredCode, String identifyNumber){
		if (!CommonUtils.isEmpty(insuredCode) && !CommonUtils.isEmpty(identifyNumber)) {
			return " ( INSUREDFLAG != '1' OR ( ( INSUREDCODE = '" + insuredCode + "' OR IDENTIFYNUMBER = '" + insuredCode + "') AND IDENTIFYNUMBER = '" + identifyNumber + "' ) ) ";
		} else if (!CommonUtils.isEmpty(insuredCode)) {
			return " ( INSUREDFLAG != '1' OR INSUREDCODE = '" + insuredCode + "' OR IDENTIFYNUMBER = '" + insuredCode + "' ) ";
		} else if (!CommonUtils.isEmpty(identifyNumber)) {
			return " ( INSUREDFLAG != '1' OR IDENTIFYNUMBER = '" + identifyNumber + "' ) ";
		} else {
			return "";
		}
	}
	
	/*
	 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 --- end
	*/
	
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param policyNo 保單號碼
	 * @param damageDate
	 * @param damageHour
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsuredFromCopy(String policyNo, String damageDate, String damageHour) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredFromCopy(endorseNo, policyNo);
	}
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsuredFromCopy(String endorseNo , String policyNo ) throws Exception {
		String statements = " SELECT * FROM PRPCOPYINSURED WHERE ENDORSENO = '"+ endorseNo +"' ";
		List<PrpCopyInsured> list = this.commonService.findByStatements(statements, PrpCopyInsured.class);
		List<PrpCinsured> prpCinsuredList = new ArrayList<PrpCinsured>();
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsured prpCinsured = null;
			PrpCinsuredId prpCinsuredId = null;
			PrpCopyInsuredId prpCopyInsuredId = null;
			for (PrpCopyInsured prpCopyInsured : list) { 
				prpCopyInsuredId = prpCopyInsured.getId();
				prpCinsuredId = new PrpCinsuredId(prpCopyInsured.getPolicyNo(), prpCopyInsuredId.getSerialNo());
				prpCinsured = new PrpCinsured();
				prpCopyInsured.setId(null);
				PropertyUtils.copyProperties(prpCinsured, prpCopyInsured);
				prpCinsured.setId(prpCinsuredId);
				prpCopyInsured.setId(prpCopyInsuredId);
				prpCinsuredList.add(prpCinsured);
			}
			return prpCinsuredList;
		}
		statements = " POLICYNO = '" + policyNo + "' ";
		return this.prpCinsuredService.findPrpCinsured(statements);
	}

	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param insuredCode 被保險人代碼或者身份證字號
	 * @param insuredName 被保險人名稱
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsuredFromCopy(String policyNo, String damageDate, String damageHour, String insuredCode, String insuredName) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredFromCopy(endorseNo, policyNo, insuredCode, insuredName);
	}

	
	/*
	 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 --- start
	  原因： 在查詢時因使用名字，當姓名中有不在big-5編碼內的字時就會造成找不到資料而出現nullpointException錯誤
	  所以新增此method by identify query
	*/
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param insuredCode 被保險人代碼或者身份證字號
	 * @param insuredName 被保險人名稱
	 * @param identifyNumber 被保險人身份證字號
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsuredFromCopy(String policyNo, String damageDate, String damageHour, String insuredCode, String insuredName, String identifyNumber) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredByIdFromCopy(endorseNo, policyNo, insuredCode, insuredName, identifyNumber);
	}
	/*
	 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 --- end
	*/
	
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @param insuredCode 被保險人代碼或者身份證字號
	 * @param insuredName 被保險人名稱
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsuredFromCopy(String endorseNo, String policyNo, String insuredCode, String insuredName) throws Exception {
		String statements = "  SELECT * FROM PRPCOPYINSURED WHERE ENDORSENO = '" + endorseNo + "' ";
		//mantis：CLM0210，處理人員：DP0713，需求單編號：新核心-TA被保人姓名難字無法進行備案登記處理
		String other = this.getInsuredSqlForCall_E(insuredCode, insuredName);
		if (!CommonUtils.isEmpty(other)) {
			statements += " AND " + other;
		}
		List<PrpCopyInsured> list = this.commonService.findByStatements(statements, PrpCopyInsured.class);
		List<PrpCinsured> prpCinsuredList = new ArrayList<PrpCinsured>();
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsured prpCinsured = null;
			PrpCinsuredId prpCinsuredId = null;
			PrpCopyInsuredId prpCopyInsuredId = null;
			for (PrpCopyInsured prpCopyInsured : list) {
				prpCopyInsuredId = prpCopyInsured.getId();
				prpCinsuredId = new PrpCinsuredId(prpCopyInsured.getPolicyNo(), prpCopyInsuredId.getSerialNo());
				prpCinsured = new PrpCinsured();
				prpCopyInsured.setId(null);
				PropertyUtils.copyProperties(prpCinsured, prpCopyInsured);
				prpCinsured.setId(prpCinsuredId);
				prpCopyInsured.setId(prpCopyInsuredId);
				prpCinsuredList.add(prpCinsured);
			}
			return prpCinsuredList;
		}
		statements = " POLICYNO = '" + policyNo + "' ";
		if (!CommonUtils.isEmpty(other)) {
			statements += " AND " + other;
		}
		return this.prpCinsuredService.findPrpCinsured(statements);
	}

	
	/*
	 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 --- start
	  原因： 在查詢時因使用名字，當姓名中有不在big-5編碼內的字時就會造成找不到資料而出現nullpointException錯誤
	  所以新增此method by identify query
	*/
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @param insuredCode 被保險人代碼或者身份證字號
	 * @param insuredName 被保險人名稱
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsuredByIdFromCopy(String endorseNo, String policyNo, String insuredCode, String insuredName, String identifyNumber) throws Exception {
		
		String statements = "  SELECT * FROM PRPCOPYINSURED WHERE ENDORSENO = '" + endorseNo + "' ";
		String other = this.getInsuredSqlById(insuredCode, identifyNumber);
		if (!CommonUtils.isEmpty(other)) {
			statements += " AND " + other;
		}
		List<PrpCopyInsured> list = this.commonService.findByStatements(statements, PrpCopyInsured.class);
		List<PrpCinsured> prpCinsuredList = new ArrayList<PrpCinsured>();
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsured prpCinsured = null;
			PrpCinsuredId prpCinsuredId = null;
			PrpCopyInsuredId prpCopyInsuredId = null;
			for (PrpCopyInsured prpCopyInsured : list) {
				prpCopyInsuredId = prpCopyInsured.getId();
				prpCinsuredId = new PrpCinsuredId(prpCopyInsured.getPolicyNo(), prpCopyInsuredId.getSerialNo());
				prpCinsured = new PrpCinsured();
				prpCopyInsured.setId(null);
				PropertyUtils.copyProperties(prpCinsured, prpCopyInsured);
				prpCinsured.setId(prpCinsuredId);
				prpCopyInsured.setId(prpCopyInsuredId);
				prpCinsuredList.add(prpCinsured);
			}
			return prpCinsuredList;
		}
		statements = " POLICYNO = '" + policyNo + "' ";
		if (!CommonUtils.isEmpty(other)) {
			statements += " AND " + other;
		}
		return this.prpCinsuredService.findPrpCinsured(statements);
	}

	/*
	 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 --- end
	*/
	
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNatureFromCopy(String policyNo, String damageDate, String damageHour) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredNatureFromCopy(endorseNo, policyNo);
	}

	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNatureFromCopy(String endorseNo, String policyNo) throws Exception {
		String statements = " SELECT * FROM PRPCOPYINSUREDNATURE WHERE ENDORSENO = '" + endorseNo + "' ";
		List<PrpCopyInsuredNature> list = this.commonService.findByStatements(statements, PrpCopyInsuredNature.class);
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsuredNature prpCinsuredNature = null;
			PrpCinsuredNatureId prpCinsuredNatureId = null;
			PrpCopyInsuredNatureId prpCopyInsuredNatureId = null;
			List<PrpCinsuredNature> prpCinsuredNatureList = new ArrayList<PrpCinsuredNature>();
			for (PrpCopyInsuredNature tempPrpCopyInsuredNature : list) {
				prpCopyInsuredNatureId = tempPrpCopyInsuredNature.getId();
				prpCinsuredNatureId = new PrpCinsuredNatureId(tempPrpCopyInsuredNature.getPolicyNo(), prpCopyInsuredNatureId.getSerialNo());
				prpCinsuredNature = new PrpCinsuredNature();
				tempPrpCopyInsuredNature.setId(null);
				PropertyUtils.copyProperties(prpCinsuredNature, tempPrpCopyInsuredNature);
				prpCinsuredNature.setId(prpCinsuredNatureId);
				tempPrpCopyInsuredNature.setId(prpCopyInsuredNatureId);
				prpCinsuredNatureList.add(prpCinsuredNature);
			}
			return prpCinsuredNatureList;

		}
		statements = " POLICYNO = '" + policyNo + "' ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		return this.prpCinsuredNatureService.findPrpCinsuredNature(queryRule);
	}

	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param insuredCode 被保險人代碼或者身份證字號
	 * @param insuredName 被保險人名稱
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNatureFromCopy(String policyNo, String damageDate, String damageHour, String insuredCode, String insuredName) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredNatureFromCopy(endorseNo, policyNo, insuredCode, insuredName);
	}
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param serialnos 自然人序號
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNatureFromCopy(String policyNo, String damageDate, String damageHour, int[] serialnos) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredNatureFromCopy(endorseNo, policyNo, serialnos);
	}
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @param serialnos 自然人序號
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNatureFromCopy(String endorseNo, String policyNo, int[] serialnos) throws Exception {
		String statements = " SELECT * FROM PRPCOPYINSUREDNATURE WHERE ENDORSENO = '" + endorseNo + "' ";
		String other = "";
		if(serialnos.length > 0){
			for(int i : serialnos){
				other += ", " + i;
			}
			other = " AND SERIALNO IN ( " + other.substring(1) + " ) order by SERIALNO ";
		}
		statements += other;
		List<PrpCopyInsuredNature> list = this.commonService.findByStatements(statements, PrpCopyInsuredNature.class);
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsuredNature prpCinsuredNature = null;
			PrpCinsuredNatureId prpCinsuredNatureId = null;
			PrpCopyInsuredNatureId prpCopyInsuredNatureId = null;
			List<PrpCinsuredNature> prpCinsuredNatureList = new ArrayList<PrpCinsuredNature>();
			for (PrpCopyInsuredNature tempPrpCopyInsuredNature : list) {
				prpCopyInsuredNatureId = tempPrpCopyInsuredNature.getId();
				prpCinsuredNatureId = new PrpCinsuredNatureId(tempPrpCopyInsuredNature.getPolicyNo(), prpCopyInsuredNatureId.getSerialNo());
				prpCinsuredNature = new PrpCinsuredNature();
				tempPrpCopyInsuredNature.setId(null);
				PropertyUtils.copyProperties(prpCinsuredNature, tempPrpCopyInsuredNature);
				prpCinsuredNature.setId(prpCinsuredNatureId);
				tempPrpCopyInsuredNature.setId(prpCopyInsuredNatureId);
				prpCinsuredNatureList.add(prpCinsuredNature);
			}
			return prpCinsuredNatureList;

		}
		statements = " POLICYNO = '" + policyNo + "' ";
		statements += other;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		return this.prpCinsuredNatureService.findPrpCinsuredNature(queryRule);
	}
	/***
	 * 從copy資料表取出險時被保險人訊息
	 * @param endorseNo 批單號碼
	 * @param policyNo 保單號碼
	 * @param insuredCode 被保險人代碼或者身份證字號
	 * @param insuredName 被保險人名稱
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNatureFromCopy(String endorseNo, String policyNo, String insuredCode, String insuredName) throws Exception {
		String statements = " SELECT * FROM PRPCOPYINSUREDNATURE WHERE ENDORSENO = '" + endorseNo + "' ";
		String other = this.getInsuredSql(insuredCode, insuredName);
		if (!CommonUtils.isEmpty(other)) {
			statements += " AND " + other;
		}
		List<PrpCopyInsuredNature> list = this.commonService.findByStatements(statements, PrpCopyInsuredNature.class);
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsuredNature prpCinsuredNature = null;
			PrpCinsuredNatureId prpCinsuredNatureId = null;
			PrpCopyInsuredNatureId prpCopyInsuredNatureId = null;
			List<PrpCinsuredNature> prpCinsuredNatureList = new ArrayList<PrpCinsuredNature>();
			for (PrpCopyInsuredNature tempPrpCopyInsuredNature : list) {
				prpCopyInsuredNatureId = tempPrpCopyInsuredNature.getId();
				prpCinsuredNatureId = new PrpCinsuredNatureId(tempPrpCopyInsuredNature.getPolicyNo(), prpCopyInsuredNatureId.getSerialNo());
				prpCinsuredNature = new PrpCinsuredNature();
				tempPrpCopyInsuredNature.setId(null);
				PropertyUtils.copyProperties(prpCinsuredNature, tempPrpCopyInsuredNature);
				prpCinsuredNature.setId(prpCinsuredNatureId);
				tempPrpCopyInsuredNature.setId(prpCopyInsuredNatureId);
				prpCinsuredNatureList.add(prpCinsuredNature);
			}
			return prpCinsuredNatureList;

		}
		statements = " POLICYNO = '" + policyNo + "' ";
		if (!CommonUtils.isEmpty(other)) {
			statements += " AND " + other;
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statements);
		return this.prpCinsuredNatureService.findPrpCinsuredNature(queryRule);
	}
	
	
	/***
	 * 傷害險雙擊域
	 * 從copy資料表取出險時被保險人
	 * @param policyNo 保單號碼
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @param conditions 查詢條件
	 * @param pageNo 起始頁
	 * @param rowsPerPage 每頁記錄數
	 * @return
	 * @throws Exception
	 */
	public Page findPrpCinsuredFromCopy(String policyNo, String damageDate, String damageHour, String conditions , int pageNo , int rowsPerPage) throws Exception {
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		return this.findPrpCinsuredFromCopy(endorseNo, conditions , pageNo, rowsPerPage);
	}
	/***
	 * 傷害險雙擊域
	 * 從copy資料表取出險時被保險人
	 * @param conditions 查詢條件
	 * @param pageNo 起始頁
	 * @param rowsPerPage 每頁記錄數
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	public Page findPrpCinsuredFromCopy(String endorseNo, String conditions , int pageNo, int rowsPerPage) throws Exception {
		String statements = " endorseNo = '" + endorseNo + "' AND " + conditions;
		Page page = this.prpCopyInsuredService.findByPage(statements, pageNo, rowsPerPage);
		List<PrpCopyInsured> list = page.getResult();
		List<PrpCinsured> prpCinsuredList = new ArrayList<PrpCinsured>();
		if (!CommonUtils.isEmpty(list)) {
			PrpCinsured prpCinsured = null;
			PrpCinsuredId prpCinsuredId = null;
			PrpCopyInsuredId prpCopyInsuredId = null;
			for (PrpCopyInsured prpCopyInsured : list) {
				prpCopyInsuredId = prpCopyInsured.getId();
				prpCinsuredId = new PrpCinsuredId(prpCopyInsured.getPolicyNo(), prpCopyInsuredId.getSerialNo());
				prpCinsured = new PrpCinsured();
				prpCopyInsured.setId(null);
				PropertyUtils.copyProperties(prpCinsured, prpCopyInsured);
				prpCinsured.setId(prpCinsuredId);
				prpCopyInsured.setId(prpCopyInsuredId);
				prpCinsuredList.add(prpCinsured);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), rowsPerPage, prpCinsuredList) ;
	}

	/***
	 * 判斷車險強制任意關聯是否標的和被保險人資料一致
	 * @param policyNo 任意險保單
	 * @param mainPolicyNo 強制險保單
	 * @param damageDate 出險日期
	 * @param damageHour 出險小時
	 * @return
	 * @throws Exception 
	 */
	public boolean checkRelate(String policyNo, String mainPolicyNo, String damageDate, String damageHour) throws Exception {
		//取任意險保單出險時，前的批單資料
		String endorseNo = this.getEndorseNo(policyNo, damageDate, damageHour);
		PrpCmain prpCmain = this.findPrpCmainFromCopy(endorseNo, policyNo);
		List<PrpCitemCar> prpCitemCarList = this.findPrpCitemCar(policyNo, damageDate, damageHour);
		endorseNo = this.getEndorseNo(mainPolicyNo, damageDate, damageHour);
		PrpCmain mainPrpCmain = this.findPrpCmainFromCopy(endorseNo, mainPolicyNo);
		List<PrpCitemCar> maimPrpCitemCarList = this.findPrpCitemCar(mainPolicyNo, damageDate, damageHour);
		if(!CommonUtils.isEmpty(prpCmain.getInsuredCode()) && !CommonUtils.isEmpty(mainPrpCmain.getInsuredCode())){
			if(prpCmain.getInsuredCode().equals(mainPrpCmain.getInsuredCode())){//被保險人代碼一致
				if(!CommonUtils.isEmpty(prpCitemCarList) && !CommonUtils.isEmpty(maimPrpCitemCarList)){
					PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
					PrpCitemCar maimPrpCitemCar = maimPrpCitemCarList.get(0);
					//判斷標的車車牌一致
					return !CommonUtils.isEmpty(prpCitemCar.getLicenseNo()) && 
							!CommonUtils.isEmpty(maimPrpCitemCar.getLicenseNo()) && 
							prpCitemCar.getLicenseNo().equals(maimPrpCitemCar.getLicenseNo());
				}
			}
		}
		return false;
	}
	
	/**
	 * 取得PrpCitemKind
	 * mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> findPrpCitemKind(String policyNo, String kindCode) throws Exception {
		PolicyDto policyDto = new PolicyDto();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		queryRule.addEqual("kindCode", kindCode);
		policyDto.setPrpCitemKindList(this.prpCitemKindService.findPrpCitemKind(queryRule));
		return policyDto.getPrpCitemKindList();
	}
	
	public void setPolicyDto(PolicyDto policyDto) {
		this.policyDto = policyDto;
	}

	public PolicyDto getPolicyDto() {
		return policyDto;
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PolicyCopyService getPolicyCopyService() {
		return policyCopyService;
	}

	public void setPolicyCopyService(PolicyCopyService policyCopyService) {
		this.policyCopyService = policyCopyService;
	}

	public PrpCopyInsuredService getPrpCopyInsuredService() {
		return prpCopyInsuredService;
	}

	public void setPrpCopyInsuredService(PrpCopyInsuredService prpCopyInsuredService) {
		this.prpCopyInsuredService = prpCopyInsuredService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public PrpCinsuredNatureService getPrpCinsuredNatureService() {
		return prpCinsuredNatureService;
	}

	public void setPrpCinsuredNatureService(PrpCinsuredNatureService prpCinsuredNatureService) {
		this.prpCinsuredNatureService = prpCinsuredNatureService;
	}

	public PrpCitemCarService getPrpCitemCarService() {
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public PrpCengageService getPrpCengageService() {
		return prpCengageService;
	}

	public void setPrpCengageService(PrpCengageService prpCengageService) {
		this.prpCengageService = prpCengageService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCfeeService getPrpCfeeService() {
		return prpCfeeService;
	}

	public void setPrpCfeeService(PrpCfeeService prpCfeeService) {
		this.prpCfeeService = prpCfeeService;
	}

	public PrpCprofitService getPrpCprofitService() {
		return prpCprofitService;
	}

	public void setPrpCprofitService(PrpCprofitService prpCprofitService) {
		this.prpCprofitService = prpCprofitService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpPprofitService getPrpPprofitService() {
		return prpPprofitService;
	}

	public void setPrpPprofitService(PrpPprofitService prpPprofitService) {
		this.prpPprofitService = prpPprofitService;
	}

	public PrpPitemKindService getPrpPitemKindService() {
		return prpPitemKindService;
	}

	public void setPrpPitemKindService(PrpPitemKindService prpPitemKindService) {
		this.prpPitemKindService = prpPitemKindService;
	}

	public PrpPfeeService getPrpPfeeService() {
		return prpPfeeService;
	}

	public void setPrpPfeeService(PrpPfeeService prpPfeeService) {
		this.prpPfeeService = prpPfeeService;
	}

	public PrpPengageService getPrpPengageService() {
		return prpPengageService;
	}

	public void setPrpPengageService(PrpPengageService prpPengageService) {
		this.prpPengageService = prpPengageService;
	}

}
