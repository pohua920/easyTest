package com.sinosoft.claim.regist.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.dto.domain.PrpLscheduleNewDto;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLcallCenter;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLrelatePerson;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;

/**
 * 自定义报案数据传输对象
 * <p>
 * Title: 车险理赔报案DTO
 * </p>
 * <p>
 * Description: 车险理赔报案样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class RegistDto implements Serializable {
	private static final long serialVersionUID = 5282024537241195361L;
	/** 报案主信息 */
	private PrpLregist prpLregist;
	/** 三者车辆信息 */
	private List<PrpLthirdParty> prpLthirdPartyList;
	/** 驾驶员信息 */
	private List<PrpLdriver> prpLdriverList;
	/** 损失部位信息 */
	private List<PrpLthirdCarLoss> prpLthirdCarLossList;
	/** 人员伤亡跟踪信息 */
	private List<PrpLpersonTrace> prpLpersonTraceList;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;
	/** 文本信息 */
	private List<PrpLregistText> prpLregistTextList;
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 新报案提示表的数据增加 */
	private PrpLscheduleNewDto prpLscheduleNewDto;
	/** 特别约定信息 */
	private List<PrpCengage> prpCengageList;
	/** 调度主表信息 */
	private PrpLscheduleMainWF prpLscheduleMainWF;
	/** 调度表的信息 */
	private List<PrpLscheduleItem> prpLscheduleItemList;
	/** 保险标的信息 */
	private List<PrpCitemKind> prpCitemKindList;
	/** 损失部位信息 */
	private List<PrpLthirdProp> prpLthirdPropList;
	/** 添加调查信息 */
	private AcciCheckDto acciCheckDto;
	/** 增加联系人信息 */
	private List<PrpLrelatePerson> prpLrelatePersonList;
	/** 增加出口货运人的检验人名称 */
	private String identifierName = "";
	/** 添加呈报信息 */
	/** 文本信息 */
	private List<PrpLregistText> prpLregistTextList2;
	/** 出险人员信息表 */
	private PrpLacciPerson prpLacciPerson;
	/** 理赔扩展信息表 */
	private PrpLext prpLext;
	/** 关联表 强三 */
	private Prplregistrpolicy prpLRegistRPolicy;
	/** 强三关联信息 */
	private List<Prplregistrpolicy> prpLRegistRPolicyList = new ArrayList<Prplregistrpolicy>();
	/** 理赔呼叫中心备注信息表 */
	private PrpLcallCenter prpLcallCenter;

	public void setPrpLRegistRPolicyList(List<Prplregistrpolicy> prpLRegistRPolicyList) {
		this.prpLRegistRPolicyList = prpLRegistRPolicyList;
	}

	public List<Prplregistrpolicy> getPrpLRegistRPolicyList() {
		return this.prpLRegistRPolicyList;
	}

	/**
	 * 获得关联的强制保单关联信息
	 * @return
	 */
	public Prplregistrpolicy getPrpLRegistRPolicyOfCompel() {
		for (Iterator<Prplregistrpolicy> iter = prpLRegistRPolicyList.iterator(); iter.hasNext();) {
			Prplregistrpolicy prpLRegistRPolicy = (Prplregistrpolicy) iter.next();
			if (Prplregistrpolicy.COMPEL_POLICY.equals(prpLRegistRPolicy.getPolicyType())) {
				return prpLRegistRPolicy;
			}
		}
		return null;
	}

	/**
	 * 获得报案的关联保单类型
	 * @return Regist.BUSINESS_COMPEL_POLICY:商业保单强制保单关联报案
	 *         Regist.SING_BUSINESS_POLICY :仅商业保单报案 Regist.SING_COMPEL_POLICY
	 *         :仅强制保单报案 Regist.NONE_RELATION_DATA :关联表中无关联保单数据
	 */
	public String getRegistType() {
		boolean isHaveCompelPolicy = false;
		boolean isHaveBusinessPolicy = false;
		for (Iterator<Prplregistrpolicy> iter = prpLRegistRPolicyList.iterator(); iter.hasNext();) {
			Prplregistrpolicy prpLRegistRPolicy = (Prplregistrpolicy) iter.next();
			if (Prplregistrpolicy.COMPEL_POLICY.equals(prpLRegistRPolicy.getPolicyType())) {
				isHaveCompelPolicy = true;
			}
			if (Prplregistrpolicy.BUSINESS_POLICY.equals(prpLRegistRPolicy.getPolicyType())) {
				isHaveBusinessPolicy = true;
			}
		}
		if (isHaveBusinessPolicy && isHaveCompelPolicy) {
			return BUSINESS_COMPEL_POLICY;
		}
		if (isHaveBusinessPolicy && !isHaveCompelPolicy) {
			return SING_BUSINESS_POLICY;
		}
		if (!isHaveBusinessPolicy && isHaveCompelPolicy) {
			return SING_COMPEL_POLICY;
		}
		if (!isHaveBusinessPolicy && !isHaveCompelPolicy) {
			return NONE_RELATION_DATA;
		}
		return null;
	}

	/**
	 * 设置出险人员信息表
	 * @param prpLacciPerson 出险人员信息表
	 */
	public void setPrpLacciPerson(PrpLacciPerson prpLacciPerson) {
		this.prpLacciPerson = prpLacciPerson;
	}

	/**
	 * 得到出险人员信息表
	 * @return 出险人员信息表
	 */
	public PrpLacciPerson getPrpLacciPerson() {
		return this.prpLacciPerson;
	}

	/**
	 * 得到呈报文本信息
	 * @return 报案文本信息
	 */
	public List<PrpLregistText> getPrpLregistTextList2() {
		return prpLregistTextList2;
	}

	/**
	 * 设置呈报文本信息
	 * @param prpLregistTextList 报案文本信息
	 */
	public void setPrpLregistTextList2(List<PrpLregistText> prpLregistTextList2) {
		this.prpLregistTextList2 = prpLregistTextList2;
	}

	/**
	 * 获得货运信息
	 * @return 获得货信息
	 */
	public String getIdentifierName() {
		return this.identifierName;
	}

	/**
	 * 设置货运信息
	 * @param 货运信息
	 */
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	public RegistDto() {
	}

	/**
	 * 得到报案主表信息
	 * @return 报案主表信息
	 */
	public PrpLregist getPrpLregist() {
		return prpLregist;
	}

	/**
	 * 设置报案主表信息
	 * @param prpLregist 报案主表信息
	 */
	public void setPrpLreg(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

	/**
	 * 得到报案文本信息
	 * @return 报案文本信息
	 */
	public List<PrpLregistText> getPrpLregistTextList() {
		return prpLregistTextList;
	}

	/**
	 * 设置报案文本信息
	 * @param prpLregistTextList 报案文本信息
	 */
	public void setPrpLregistTextList(List<PrpLregistText> prpLregistTextList) {
		this.prpLregistTextList = prpLregistTextList;
	}

	/**
	 * 得到三者车辆信息
	 * @return 三者车辆信息
	 */
	public List<PrpLthirdParty> getPrpLthirdPartyList() {
		return prpLthirdPartyList;
	}

	/**
	 * 设置三者车辆信息
	 * @param PrpLthirdPartyList 三者车辆表信息
	 */
	public void setPrpLthirdPartyList(List<PrpLthirdParty> prpLthirdPartyList) {
		this.prpLthirdPartyList = prpLthirdPartyList;
	}

	/**
	 * 得到驾驭员信息
	 * @return 驾驭员 信息
	 */
	public List<PrpLdriver> getPrpLdriverList() {
		return prpLdriverList;
	}

	/**
	 * 设置驾驭员信息
	 * @param PrpLdriverList 驾驭员表信息
	 */
	public void setPrpLdriverList(List<PrpLdriver> prpLdriverList) {
		this.prpLdriverList = prpLdriverList;
	}

	/**
	 * 得到立案操作状态信息
	 * @return 立案操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	/**
	 * 设置立案操作状态信息
	 * @param prpLclaimStuats 立案操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	/**
	 * 得到新报案提示表信息
	 * @return 新报案提示表信息
	 */
	public PrpLscheduleNewDto getPrpLscheduleNew() {
		return prpLscheduleNewDto;
	}

	public List<PrpLthirdCarLoss> getPrpLthirdCarLossList() {
		return prpLthirdCarLossList;
	}

	/**
	 * 设置新报案提示表信息
	 * @param prpLscheduleNew 新报案提示表信息
	 */
	public void setPrpLscheduleNew(PrpLscheduleNewDto prpLscheduleNew) {
		this.prpLscheduleNewDto = prpLscheduleNew;
	}

	public void setPrpLthirdCarLossList(List<PrpLthirdCarLoss> prpLthirdCarLossList) {
		this.prpLthirdCarLossList = prpLthirdCarLossList;
	}

	/**
	 * 得到人伤跟踪信息
	 * @return 人伤跟踪 信息
	 */
	public List<PrpLpersonTrace> getPrpLpersonTraceList() {
		return prpLpersonTraceList;
	}

	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	/**
	 * 设置人伤跟踪信息
	 * @param PrpLpersonTraceList 人伤跟踪表信息
	 */
	public void setPrpLpersonTraceList(List<PrpLpersonTrace> prpLpersonTraceList) {
		this.prpLpersonTraceList = prpLpersonTraceList;
	}

	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	// 加入保险标的信息的内容，界面上可以直接显示承保险别
	/**
	 * 得到itemkind信息
	 * @return itemkind信息
	 */
	public List<PrpCitemKind> getPrpCitemKindList() {
		return prpCitemKindList;
	}

	public List<PrpCengage> getPrpCengageList() {
		return prpCengageList;
	}

	/**
	 * 设置itemkind信息
	 * @param prpCitemKindList itemkind表信息
	 */
	public void setPrpCitemKindList(List<PrpCitemKind> prpCitemKindList) {
		this.prpCitemKindList = prpCitemKindList;
	}

	public void setPrpCengageList(List<PrpCengage> prpCengageList) {
		this.prpCengageList = prpCengageList;
	}

	/**
	 * 得到调度主表信息
	 * @return 调度主表信息
	 */
	public PrpLscheduleMainWF getPrpLscheduleMainWF() {
		return prpLscheduleMainWF;
	}

	/**
	 * 设置调度主表信息
	 * @param prpLscheduleMainWF 调度主表信息
	 */
	public void setPrpLscheduleMainWF(PrpLscheduleMainWF prpLscheduleMainWF) {
		this.prpLscheduleMainWF = prpLscheduleMainWF;
	}

	/**
	 * 得到调度标的信息
	 * @return 调度标的信息
	 */
	public List<PrpLscheduleItem> getPrpLscheduleItemList() {
		return prpLscheduleItemList;
	}

	/**
	 * 设置调度标的信息
	 * @param PrpLscheduleItem调度标的信息
	 */
	public void setPrpLscheduleItemList(List<PrpLscheduleItem> prpLscheduleItemList) {
		this.prpLscheduleItemList = prpLscheduleItemList;
	}

	/**
	 * 设置其它损失信息
	 * @param prpLthirdPropList 其它损失信息
	 */

	public void setPrpLthirdPropList(List<PrpLthirdProp> prpLthirdPropList) {
		this.prpLthirdPropList = prpLthirdPropList;
	}

	/**
	 * 得到其它损失信息
	 * @return 其它损失信息
	 */

	public List<PrpLthirdProp> getPrpLthirdPropList() {
		return prpLthirdPropList;
	}

	/**
	 * 设置联系人信息
	 * @param prpLrelatePersonList 联系人信息
	 */
	public void setPrpLrelatePersonList(List<PrpLrelatePerson> prpLrelatePersonList) {
		this.prpLrelatePersonList = prpLrelatePersonList;
	}

	/**
	 * 得到联系人信息
	 * @return 得到联系人信息
	 */
	public List<PrpLrelatePerson> getPrpLrelatePersonList() {
		return this.prpLrelatePersonList;
	}

	/**
	 * 设置调查信息
	 * @param acciCheckDto 调查信息
	 */
	public void setAcciCheckDto(AcciCheckDto acciCheckDto) {
		this.acciCheckDto = acciCheckDto;
	}

	/**
	 * 获得调查信息
	 * @return 调查信息
	 **/
	public AcciCheckDto getAcciCheckDto() {
		return this.acciCheckDto;
	}

	/**
	 * 设置理赔扩展信息
	 * @param prpLext 理赔扩展信息
	 */
	public void setPrpLext(PrpLext prpLext) {
		this.prpLext = prpLext;
	}

	/**
	 * 得到理赔扩展信息
	 * @return 得到理赔扩展信息
	 */
	public PrpLext getPrpLext() {
		return prpLext;
	}

	/**
	 * 得到强三关联表信息
	 * @return
	 */
	public Prplregistrpolicy getPrpLRegistRPolicy() {
		return prpLRegistRPolicy;
	}

	/**
	 * 设置强三关联表信息
	 * @param prpLclaim
	 */
	public void setPrpLRegistRPolicy(Prplregistrpolicy prpLRegistRPolicy) {
		this.prpLRegistRPolicy = prpLRegistRPolicy;
	}

	/**
	 * 仅商业保单报案
	 */
	public static final String SING_BUSINESS_POLICY = "10";
	/**
	 * 仅强制保单报案
	 */
	public static final String SING_COMPEL_POLICY = "01";
	/**
	 * 商业保单强制保单关联报案
	 */
	public static final String BUSINESS_COMPEL_POLICY = "11";
	/**
	 * 关联表中无关联保单数据
	 */
	public static final String NONE_RELATION_DATA = "00";

	public PrpLcallCenter getPrpLcallCenter() {
		return prpLcallCenter;
	}

	public void setPrpLcallCenter(PrpLcallCenter prpLcallCenter) {
		this.prpLcallCenter = prpLcallCenter;
	}

	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

}
