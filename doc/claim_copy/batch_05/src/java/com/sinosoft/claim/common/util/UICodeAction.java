package com.sinosoft.claim.common.util;

import ins.framework.common.ServiceFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.claim.bl.facade.BLCodeFacade;
import com.sinosoft.claim.bl.facade.BLPrpDriskFacade;
import com.sinosoft.claim.bl.facade.BLUtiCodeTransferFacade;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.dto.domain.PrpDidentifierDto;
import com.sinosoft.claim.dto.domain.PrpDriskDto;
import com.sinosoft.claim.dto.domain.UtiCodeTransferDto;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.platform.bl.facade.BLPrpDcodeFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcompanyFacade;
import com.sinosoft.platform.dto.domain.PrpDcompanyDto;
import com.sinosoft.platform.dto.domain.PrpDriskConfigDto;

/**
 * 代码查询逻辑
 * <p>
 * Title: 车险理赔
 * </p>
 * <p>
 * Description: 车险理赔样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class UICodeAction {
	private static UICodeAction uiCodeAction = new UICodeAction();

	public static UICodeAction getInstance() {
		return uiCodeAction;
	}

	/** 部门机构*/
	public static String COMCODE = "ComCode"; // 部门机构
	/** 币别*/
	public static String CURRENCYCODE = "CurrencyCode";
	public static String KINDCODE = "KindCode"; // 险别
	public static String CLAUSECODE = "ClauseCode"; // 条款
	public static String REINSCODE = "ReinsCode"; // ？？
	public static String DAMAGECODE = "DamageCode"; // 出险原因
	public static String DAMAGEAREACODE = "DamageAreaCode"; // 出险区域
	public static String DAMAGETYPECODE = "DamageTypeCode";// 事故类型
	public static String HANDLERCODE = "HandlerCode"; // 经办人
	public static String DOCCODE = "DocCode"; // 单证代码
	public static String HANDERCODE = "HanderCode"; // 接案人 经办人
	public static String HANDLEUNIT = "HandleUnit"; // 事故处理部门
	public static String INSURECOMCODE = "InsureComCode"; // 承保公司
	public static String POLICYKINDCODE = "PolicyKindCode"; // 保单投保险别来自prpcitemkind
	public static String POLICYITEMCODE = "PolicyItemCode"; // 保单责任来自prpcitemkind
	public static String CHARGECODE = "ChargeCode"; // 费用代码
	public static String CHARGECODE1 = "ChargeCode"; // 费用代码
	public static String DAMAGEADDRESS = "DamageAddress"; // 出险地点
	public static String SCHEDULEOBJECT = "ScheduleObject"; // 出险地点
	public static String COMPCODE = "CompCode"; // 损失部件
	public static String BUSINESSSOURCE = "BusinessSource"; // 所在行业
	public static String DAMAGEDISTRICT = "DamageDistrict"; // 所在地区
	public static String POLICYKINDCODEFORPERSON = "PolicyKindCodeForPerson"; // 保单投保险别
	public static String POLICYKINDCODEFORPROP = "PolicyKindCodeForProp"; // 保单投保险别
	public static String CustomerUnit = "CustomerUnit"; // 人员定损费用
	public static String INJURYGRADE = "InjueryGrade"; // 医疗等级
	public static String HandleUnitCar = "HandleUnitCar"; // 车险的事故处理部门
	public static String CASECODE = "CaseCode"; // 案件性质
	public static String CATASTROPHECODE = "CatastropheCode"; // 巨灾代码信息
	public static String LIABCODE = "PrpDliab"; // 责任类别代码
	public static String PAYOBJECT = "PayObject"; // 支付对象

	/**立案service*/
	private PrpLclaimService prpLclaimService;
	/**险种配置service*/
	private UtiCodeTransferService utiCodeTransferService;

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	/**
	 * 查询代码
	 * @param Collection：查询代码
	 * @throws Exception
	 */
	public Collection<?> findByConditions(String codetype, String conditions, int pageNo, int rowsPerPage) throws Exception {

		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.findByConditions(codetype, conditions, pageNo, rowsPerPage);
	}

	/**
	 * 查询代码
	 * @param Collection：查询代码
	 * @throws Exception
	 */
	public Collection<?> findByConditions(String conditions) throws Exception {
		return new BLCodeFacade().findByConditions(conditions);
	}

	/**
	 * 查询免赔条件
	 * @param Collection：查询代码
	 * @throws Exception
	 */
	public Collection<?> getDeductCondition(String riskCode) throws Exception {
		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.getDeductCondition(riskCode);
	}

	/**
	 * 根据机构代码查询机构级别
	 * @param comCode：机构代码
	 * @return 机构级别
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getComLevel(String comCode) throws Exception {
		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.getComLevel(comCode);
	}

	/**
	 * 根据用户代码，查询用户的显示价格权限
	 * @param userCode：用户代码
	 * @return 用户价格权限
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getUserShowPriceFlag(String userCode) throws Exception {
		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.getUserShowPriceFlag(userCode);
	}

	/**
	 * 根据业务类型及险种查询业务代码
	 * @param codetype：业务类型
	 * @param riskcode：险种代码
	 * @return PrpDcodeDto 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	public Collection<?> getCodeType(String codetype, String riskcode) throws Exception {
		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.getCodeType(codetype, riskcode);
	}

	/**
	 * 根据车辆种类代码和险类查询
	 * @param codetype：业务类型
	 * @param classcode：险类代码
	 * @return PrpDcodeDto 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	public Collection<?> getCodeTypeCarKind(String codetype, String classCode) throws Exception {
		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.getCodeTypeCarKind(codetype, classCode);
	}

	/**
	 * 返回货币名称，代码列表
	 * @return Collection 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	public Collection<?> getCurrencyList() throws Exception {
		BLCodeFacade blCodeFacade = new BLCodeFacade();
		return blCodeFacade.getCurrencyList();
	}

	/**
	 * 根据部门代码得到部门名称
	 * @param comCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateComCode(String comCode, boolean isChinese) throws Exception {
		String comName = "";
		if (comCode == null) {
			comName = "";
		} else {
			if (!comCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				comName = blCodeFacade.translateComCode(comCode, isChinese);
			}
		}
		return comName;
	}

	/**
	 * 根据币别得到币别名称
	 * @param currencyCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCurrencyCode(String currencyCode, boolean isChinese) throws Exception {
		String curryncyName = "";

		if (currencyCode == null) {
			curryncyName = "";
		} else {
			if (!currencyCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				curryncyName = blCodeFacade.translateCurrencyCode(currencyCode, isChinese);
			}
		}
		return curryncyName;
	}

	/**
	 * 根据险种，险别代码得到险别名称
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateKindCode(String riskCode, String kindCode, boolean isChinese) throws Exception {
		String codeName = "";

		if (riskCode == null || kindCode == null) {
			codeName = "";
		} else {
			if (!riskCode.equals("") && !kindCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				codeName = blCodeFacade.translateKindCode(riskCode, kindCode, isChinese);
			}
		}
		return codeName;
	}

	/**
	 * 根据代码类型，代码查询代码名称
	 * @param codeType String
	 * @param codeCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCodeCode(String codeType, String codeCode, boolean isChinese) throws Exception {
		String codeName = "";

		if (codeType == null || codeCode == null) {
			codeName = "";
		} else {
			if (!codeType.equals("") && !codeCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				codeName = blCodeFacade.translateCodeCode(codeType, codeCode, isChinese);
			}
		}
		return codeName;
	}

	// 查询PrpdLimit表，赔偿限额专用转换
	public String translateLimit(String riskCode, String limitCode, boolean isChinese) throws Exception {
		String limitName = "";

		if (riskCode == null || limitCode == null) {
			limitName = "";
		} else {
			if (!riskCode.equals("") && !limitCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				limitName = blCodeFacade.translateLimit(riskCode, limitCode, isChinese);
			}
		}
		return limitName;
	}

	/**
	 * 根据用户代码查询代码名称
	 * @param userCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateUserCode(String userCode, boolean isChinese) throws Exception {
		String codeName = "";

		if (userCode == null) {
			codeName = "";
		} else {
			if (!userCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				codeName = blCodeFacade.translateUserCode(userCode, isChinese);
			}
		}
		return codeName;
	}

	/**
	 * 根据报案号码查询对应的赔案号码
	 * @param currencyCode String
	 * @param isSearchClaimNo boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateBusinessCode(String businessCode, boolean isSearchClaimNo) throws Exception {
		String businessNo = "";

		if (businessCode == null) {
			businessNo = "";
		} else {
			if (!businessCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				businessNo = blCodeFacade.translateBusinessCode(businessCode, isSearchClaimNo);
			}
		}
		return businessNo;
	}

	/**
	 * 根据报案号码查询对应的赔案号码
	 * @param currencyCode String
	 * @param isSearchClaimNo boolean
	 * @throws Exception
	 * @return String
	 */
	public String[] translateBusinessCodes(String businessCode, boolean isSearchClaimNo) throws Exception {
		String[] businessNo = null;
		if (businessCode == null) {
			businessNo = new String[0];
		} else {
			if (!businessCode.equals("")) {
				this.getPrpLclaimService().translateCodes(businessCode, isSearchClaimNo);
			}
		}
		return businessNo;
	}

	/**
	 * 查询本报案的相关车牌号码的列表
	 * @param registNo 报案号码
	 * @return Collection 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	public Collection<?> getLicenseNoList(String registNo) throws Exception {
		BLCodeFacade bLCodeFacade = new BLCodeFacade();
		return bLCodeFacade.getLicenseNoList(registNo);
	}

	/**
	 * 根据险种代码得到险种名称
	 * @param riskCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCode(String riskCode, boolean isChinese) throws Exception {
		String codeName = "";

		if (riskCode == null) {
			codeName = "";
		} else {
			if (!riskCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				codeName = blCodeFacade.translateRiskCode(riskCode, isChinese);
			}
		}
		return codeName;
	}

	/**
	 * 根据险种代码得到险类的代码
	 * @param riskCode String
	 * @throws Exception
	 * @return String classCode
	 */
	public String translateClassCodeByRiskCode(String riskCode) throws Exception {
		String classCode = "";

		if (riskCode != null && !riskCode.equals("")) {
			BLPrpDriskFacade blPrpDriskFacade = new BLPrpDriskFacade();
			PrpDriskDto prpDriskDto = blPrpDriskFacade.findByPrimaryKey(riskCode);
			if (prpDriskDto != null)
				classCode = prpDriskDto.getClassCode();
		}

		return classCode;
	}

	/**
	 * 根据险种，险别代码得到计入总保额标志
	 * @param riskCode String
	 * @param kindCode String
	 * @throws Exception
	 * @return String
	 */
	public String translateCalculateFlag(String riskCode, String kindCode) throws Exception {
		String codeName = "";

		if (riskCode == null || kindCode == null) {
			codeName = "";
		} else {
			if (!riskCode.equals("") && !kindCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				codeName = blCodeFacade.translateCalculateFlag(riskCode, kindCode);
			}
		}
		return codeName;
	}

	// modify by wangli add start 20050416
	/**
	 * 根据代理人代码得到代理人姓名
	 * @param dbManager DBManager
	 * @param agentCode 代理人代码
	 * @throws SQLException
	 * @throws Exception
	 * @return String
	 */
	public String translateAgentName(String agentCode) throws Exception {
		String agentName = "";

		if (agentCode == null) {
			agentName = "";
		} else {
			if (!agentCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				agentName = blCodeFacade.translateAgentName(agentCode);
			}
		}

		return agentName;
	}

	/**
	 * 根据客户代码得到客户姓名
	 * @param agentCode 客户代码
	 */

	public String translateCustomerCName(String customerCode) throws Exception {
		String customerCName = "";

		if (customerCode == null) {
			customerCName = "";
		} else {
			if (!customerCode.equals("")) {
				BLCodeFacade blCodeFacade = new BLCodeFacade();
				customerCName = blCodeFacade.translateAgentName(customerCode);
			}
		}

		return customerCName;
	}

	// modify by wangli add end 20050416

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateProductCode(String conFigCode) throws Exception {
		String codeName = "";

		try {
			if (conFigCode == null) {
				codeName = "";
			} else {
				UtiCodeTransferDto UtiCodeTransferDto = new BLUtiCodeTransferFacade().findByPrimaryKey(conFigCode);
				codeName = UtiCodeTransferDto.getOuterCode();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCodetoRiskType(String riskCode) throws Exception {
		String codeName = "";

		try {
			if (riskCode == null) {
				codeName = "";
			} else {
				ArrayList<UtiCodeTransfer> utiCodeTransferList = new ArrayList<UtiCodeTransfer>();
				utiCodeTransferList = (ArrayList<UtiCodeTransfer>) utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					codeName = UtiCodeTransfer.getRiskType();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String getRiskCodebyRiskType(String riskType) throws Exception {
		String condition = "";
		try {
			if (riskType != null) {
				ArrayList<UtiCodeTransferDto> utiCodeTransferDtoList = new ArrayList<UtiCodeTransferDto>();
				Collection<?> listTemp = new BLUtiCodeTransferFacade().findByConditions(" risktype='" + riskType + "'");
				for (Iterator<?> iterator = listTemp.iterator(); iterator.hasNext();) {
					UtiCodeTransferDto utiCodeTransferDto = (UtiCodeTransferDto) iterator.next();
					utiCodeTransferDtoList.add(utiCodeTransferDto);
				}
				if (utiCodeTransferDtoList != null && utiCodeTransferDtoList.size() != 0) {
					UtiCodeTransferDto UtiCodeTransferDto = (UtiCodeTransferDto) utiCodeTransferDtoList.get(0);
					condition = condition + "'" + UtiCodeTransferDto.getOuterCode() + "'";
					for (int i = 1; i < utiCodeTransferDtoList.size(); i++) {
						condition = condition + ",'" + ((UtiCodeTransferDto) utiCodeTransferDtoList.get(i)).getOuterCode() + "'";
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return condition;
	}

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCodetoInnerCode(String riskCode) throws Exception {
		String codeName = "";

		try {
			if (riskCode == null) {
				codeName = "";
			} else {
				ArrayList<UtiCodeTransfer> utiCodeTransferList = new ArrayList<UtiCodeTransfer>();
				utiCodeTransferList = (ArrayList<UtiCodeTransfer>) utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					codeName = UtiCodeTransfer.getInnerCode();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCodetoConfigCode(String riskCode) throws Exception {
		String codeName = "";

		try {
			if (riskCode == null) {
				codeName = "";
			} else {
				ArrayList<UtiCodeTransfer> utiCodeTransferList = new ArrayList<UtiCodeTransfer>();
				utiCodeTransferList = (ArrayList<UtiCodeTransfer>) utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					codeName = UtiCodeTransfer.getConfigCode();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 根据国外检验代理人代码转换成国外检验代理人姓名
	 * @param checkAgentCodeSQL 查询语句
	 * @exception throws Exception
	 * @return prpDidentifierDtoList
	 */
	public Collection<?> translateCheckAgentCodeToName(String checkAgentCodeSQL) throws Exception {
		Collection<?> prpDidentifierDtoList = new ArrayList<PrpDidentifierDto>();
		try {
			prpDidentifierDtoList = new BLUtiCodeTransferFacade().translateCheckAgentCodeToName(checkAgentCodeSQL);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prpDidentifierDtoList;
	}

	/**
	 * 查询接口:查询某险种,某部门(可选,null为险种配置属性,非null为业务配置属 性),配置代码为configCode的配置项
	 * @param comCode String 部门代码,如果为null,则说明为险种配置属性,如果 为 代码
	 *            值,则说明是业务配置属性.业务配置代码查询时,对部门代码采取上溯 处理,找最近一级部门.
	 * @param riskCode String 险种代码,这个是必要给的
	 * @param configCode String 配置项代码.
	 * @throws Exception 查询异常
	 * @return PrpDriskConfigDto 可以在该 dto 中,取得相关信息,如果没有找到任何信息, 则返回一个null;
	 *         dto.getConfigValue() 为取值 , dto.getConfigMessageType() 为信息类型.
	 */
	public PrpDriskConfigDto queryRiskConfig(String comCode, String riskCode, String configCode) throws Exception {
		return new BLCodeFacade().queryRiskConfig(comCode, riskCode, configCode);
	}

	/**
	 * 获得PrpDcode的集合
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public Collection<?> findPrpDcodeByConditions(String conditions) throws Exception {
		if (conditions == null || conditions.length() == 0) {
			conditions = "1=1";
		}
		return new BLPrpDcodeFacade().findByConditions(conditions);
	}

	/**
	 * 根据主键获得PrpDcompanyDto
	 * @param comcode
	 * @return
	 * @throws Exception
	 */
	public PrpDcompanyDto findPrpDcompanyByPrimaryKey(String comcode) throws Exception {
		if (comcode == null || comcode.length() == 0) {
			return null;
		} else {
			return new BLPrpDcompanyFacade().findByPrimaryKey(comcode);
		}

	}

	/**
	 * 通过一次查询获得某个用户的UtiUserGrade所有结果集
	 */
	public Collection<?> findUtiUserGradeListByUserCode(String userCode) throws Exception {
		return BLCodeFacade.getInstance().findUtiUserGradeListByUserCode(userCode);
	}

	/**
	 * 获得某个用户所有分配的机构
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public Collection<?> findUserGradeCompanyListByUserCode(String userCode) throws Exception {
		String sqlwhere = "exists (Select comcode From utiusergrade Where usercode='" + userCode + "' and comcode=prpdCompany.comcode)";
		// 经分析以上两句SQL的执行计划是一样的，都使用了索引完成。
		return new BLPrpDcompanyFacade().findByConditions(sqlwhere);
	}

	/*
	 * 根据客户代码查询客户类别 @param customerCode：客户代码 @return 客户类别 @throws SQLException
	 * @throws Exception
	 */
	public String getCustomerType(String customerCode) throws Exception {
		return "";
	}

	/**
	 * 报案号生成规则调整 规则：机构设置除总公司外，其他取省分机构
	 * @param comCode
	 * @return
	 */
	public String getRegistComCode(String comCode) {
		String registComCode = "";
		if ("00".equals(comCode.trim())) {
			registComCode = comCode.trim();
		} else {
			String sqlwhere = "COMCODE IN (Select ComCode from prpdCompany Start With ComCode = '" + comCode.trim() + "' Connect By Prior uppercomCode = comCode and prior ComCode != ComCode and validstatus = '1') " + "AND COMLEVEL ='2'";
			try {
				Collection<PrpDcompanyDto> prpDcompanyDtoList = new BLPrpDcompanyFacade().findByConditions(sqlwhere);
				Iterator<PrpDcompanyDto> iterator = prpDcompanyDtoList.iterator();
				while (iterator.hasNext()) {
					PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) iterator.next();
					if ("2".equals(prpDcompanyDto.getComLevel())) {
						registComCode = prpDcompanyDto.getComCode();
						break;
					}
				}
				// 因为机构为大项目部等机构时，其comLevel为"5",直接取机构"0000000000"
				if ("".equals(registComCode) || registComCode == null) {
					registComCode = ConstantCodes.MAINCOMPANYCOMCODE;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return registComCode;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			return (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
}
