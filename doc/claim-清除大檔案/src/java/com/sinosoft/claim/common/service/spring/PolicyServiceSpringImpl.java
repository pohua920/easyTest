package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.schema.model.PrpCfeeId;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemShipId;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainLoanId;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.model.PrpCplaneId;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.service.facade.PrpCCargoItemService;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcarDriverService;
import com.sinosoft.claim.schema.service.facade.PrpCengageService;
import com.sinosoft.claim.schema.service.facade.PrpCfeeService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCitemHouseService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCitemPropService;
import com.sinosoft.claim.schema.service.facade.PrpCitemShipService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCarGoSubService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpCmainConstructService;
import com.sinosoft.claim.schema.service.facade.PrpCmainLiabService;
import com.sinosoft.claim.schema.service.facade.PrpCmainLoanService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCmainSubService;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;
import com.sinosoft.claim.schema.service.facade.PrpCplaneService;
import com.sinosoft.claim.schema.service.facade.PrpCprofitDetailService;
import com.sinosoft.claim.schema.service.facade.PrpCprofitService;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * UI保单逻辑
 * <p>
 * Title: 车险理赔样本程序 保单action
 * </p>
 * <p>
 * Description: 车险理赔样本程序 保单action
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
public class PolicyServiceSpringImpl extends GenericDaoHibernate<PolicyDto, String> implements PolicyService {
	/** 保单保额保费Service */
	private PrpCfeeService prpCfeeService;
	/** 险别Service */
	private PrpCitemKindService prpCitemKindService;
	/** 被保险人地址Service */
	private PrpCaddressService prpCaddressService;
	/** 被保险人Service */
	private PrpCinsuredService prpCinsuredService;
	/** 被保险车辆Service */
	private PrpCitemCarService prpCitemCarService;
	/** 优惠折扣明细Service */
	private PrpCprofitDetailService prpCprofitDetailService;
	/** 优惠折扣Service */
	private PrpCprofitService prpCprofitService;
	/** 缴费机会Service */
	private PrpCplanService prpCplanService;
	/** 特别约定Service */
	private PrpCengageService prpCengageService;
	/** 驾驶人信息Service */
	private PrpCcarDriverService prpCcarDriverService;
	/** 理赔状态Service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 房屋标的Service */
	private PrpCitemHouseService prpCitemHouseService;
	/** 保单限额Service */
	private PrpClimitService prpClimitService;
	/** 保单关联报案Service */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 财产险标的Service */
	private PrpCitemPropService prpCitemPropService;
	/** 责任险保单Service */
	private PrpCmainLiabService prpCmainLiabService;
	/** 人伤跟踪Service */
	private PrpLacciPersonService prpLacciPersonService;
	/** 保单Service */
	private PrpCmainService prpCmainService;
	/** 建安工险保单Service */
	private PrpCmainConstructService prpCmainConstructService;
	/** 贷款保险保单Service */
	private PrpCmainLoanService prpCmainLoanService;
	/** 保单隶属Service */
	private PrpCmainSubService prpCmainSubService;
	/** 货运险保单Service */
	private PrpCmainCargoService prpCmainCargoService;
	/** 批改信息Service */
	private PrpPheadService prpPheadService;
	/** 货物运输信息 */
	private PrpCmainCarGoSubService prpCmainCarGoSubService;
	/** 船舶险标的信息 */
	private PrpCitemShipService prpCitemShipService;
	/** 航空信息 */
	private PrpCplaneService prpCplaneService;
	/** 货运险标的信息 */
	private PrpCCargoItemService prpCCargoItemService;
	private CodeService codeService;
	/**
	 * 保单保存方法
	 *@param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void save(PolicyDto policyDto) throws SQLException, Exception {
		if (policyDto.getPrpCmain() == null)
			throw new Exception();
		// new DBFcoPolicy(dbManager).insert(policyDto.getFcoPolicyDto());
		// 未完成，理赔部分不需要保存表信息
	}

	/**
	 * 保单删除
	 * @param fcoPolicyNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String policyNo) throws SQLException, Exception {
		String condition = " policyNo = " + "'" + StringUtils.rightTrim(policyNo) + "';";
		String statement = " DELETE FROM prpCitemKind Where " + condition + " DELETE FROM prpCitemCar Where " + condition + " DELETE FROM prpCinsured Where " + condition + " DELETE FROM prpCaddress Where " + condition + " DELETE FROM prpCfee Where "
				+ condition + " DELETE FROM prpCplan Where " + condition + " DELETE FROM prpCmain Where " + condition;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		HibernateUtils.executeSql(session, statement);
	}

	/**
	 * 保单查询方法
	 * @param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyDto findByPrimaryKey(String policyNo) throws SQLException, Exception {
		PolicyDto policyDto = new PolicyDto();
		// 取得涉案车辆
		if (!CommonUtils.isEmpty(policyNo)) {
			policyDto.setPrpCmain(prpCmainService.findPrpCmainByPrimaryKey(policyNo));
		}
		PrpCfeeId prpCfeeId = new PrpCfeeId();
		prpCfeeId.setPolicyNo(policyNo);
		prpCfeeId.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		policyDto.setPrpCfee(prpCfeeService.findPrpCfee(prpCfeeId));
		String conditions = " policyNo = '" + policyNo + "'";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		int pageNo = 0;
		int rowsPerPage = 0;
		String riskCode = policyDto.getPrpCmain().getRiskCode();
		String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		if (policyDto.getPrpCmain() != null) {
			if ((!"D".equals(strRiskType)) && "02".equals(policyDto.getPrpCmain().getPolicyType())) { // 判断非车险並且是团险
				policyDto.setPrpCitemKindList(prpCitemKindService.findByConditionsDistinct(conditions, pageNo, rowsPerPage));
			} else {
				policyDto.setPrpCitemKindList(prpCitemKindService.findPrpCitemKind(QueryRule.getInstance().addEqual("id.policyNo", policyNo).addAscOrder("id.itemKindNo")));
			}
		} else {
			policyDto.setPrpCitemKindList(prpCitemKindService.findPrpCitemKind(QueryRule.getInstance().addEqual("id.policyNo", policyNo).addAscOrder("id.itemKindNo")));
		}
		policyDto.setPrpCaddressList(prpCaddressService.findPrpCaddress(queryRule));
		if (!ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			/** modify by 中科軟 大保單取數優化  begin */
			policyDto.setPrpCinsuredList(prpCinsuredService.findPrpCinsured(queryRule));
			policyDto.setPrpCinsuredNatureList(this.find(PrpCinsuredNature.class, queryRule));
		}
		/** modify by 中科軟 大保單取數優化  begin */
		policyDto.setPrpCitemCarList(prpCitemCarService.findPrpCitemCar(queryRule));
		policyDto.setPrpCprofitDetailList(prpCprofitDetailService.findPrpCprofitDetail(queryRule));
		policyDto.setPrpCprofitList(prpCprofitService.findPrpCprofit(queryRule));
		policyDto.setPrpCplanList(prpCplanService.findPrpCplan(queryRule));
		policyDto.setPrpCCargoItemList(this.prpCCargoItemService.findPrpCCargoItem(queryRule));
		String conditions2 = conditions + " ORDER BY serialno,lineno";
		QueryRule queryRule1 = QueryRule.getInstance();
		queryRule1.addSql(conditions2);
		policyDto.setPrpCengageList(prpCengageService.findPrpCengage(queryRule1));
		policyDto.setPrpCfeeList(prpCfeeService.findPrpCfee(queryRule));
		if (!CommonUtils.isEmpty(policyNo)) {
			policyDto.setLiabStartDate(prpCmainLiabService.findByPrimaryKeyStartDate(policyNo));
		}
		// 取得驾驶员信息
		policyDto.setPrpCcarDriverList(prpCcarDriverService.findPrpCcarDriver(queryRule));
		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
		prpLclaimStatusId.setBusinessNo(policyNo);
		prpLclaimStatusId.setNodeType("polic");
		prpLclaimStatusId.setSerialNo(0);
		policyDto.setPrpLclaimStatus(prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId));
		policyDto.setPrpCitemHouseList(prpCitemHouseService.findPrpCitemHouse(queryRule));
		PrpCmainLoanId prpCmainLoanId = new PrpCmainLoanId();
		prpCmainLoanId.setPolicyNo(policyNo);
		policyDto.setPrpCmainLoanList(prpCmainLoanService.findPrpCmainLoan(prpCmainLoanId));
		if (!CommonUtils.isEmpty(policyNo)) {
			policyDto.setPrpCmainCargo(prpCmainCargoService.findPrpCmainCargo(policyNo));
		}
		policyDto.setPrpClimitList(prpClimitService.findPrpClimit(queryRule));
		String conditions1 = "";
		conditions1 = " mainpolicyno= '" + policyNo + "' or  policyno= '" + policyNo + "'";
		QueryRule queryRule2 = QueryRule.getInstance();
		queryRule2.addSql(conditions1);
		policyDto.setPrpCmainSubList(prpCmainSubService.findPrpCmainSub(queryRule2));
		policyDto.setPrpLRegistRPolicyList(prpLregistrpolicyService.findPrplregistrpolicy(queryRule));
		policyDto.setPrpCitemPropList(prpCitemPropService.findPrpCitemProp(queryRule));
		QueryRule queryRule3 = QueryRule.getInstance();
		queryRule3.addEqual("policyNo", policyNo);
		policyDto.setPrpCmainConstructList(prpCmainConstructService.findPrpCmainConstruct(queryRule3));
		policyDto.setPrpCmainLiabList(prpCmainLiabService.findPrpCmainLiab(queryRule3));
		policyDto.setPrpCmainCarGoSubList(prpCmainCarGoSubService.findPrpCmainCarGoSub("policyNo = '"+policyNo+"' order by serialno "));
		PrpCitemShipId prpCitemShipId = new PrpCitemShipId();
		prpCitemShipId.setPolicyNo(policyNo);
		prpCitemShipId.setItemNo(1);
		policyDto.setPrpCitemShip(prpCitemShipService.findPrpCitemShip(prpCitemShipId));
		PrpCplaneId prpCplaneId = new PrpCplaneId();
		prpCplaneId.setPolicyNo(policyNo);
		prpCplaneId.setSerialNo(1);
		policyDto.setPrpCplane(prpCplaneService.findPrpCplane(prpCplaneId));
		return policyDto;
	}

	/**
	 * 有效保单查询方法
	 * @param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyDto findByPrimaryKey(String policyNo, String strDamageDate) throws SQLException, Exception {
		PolicyDto policyDto = new PolicyDto();
		// 取得涉案车辆
		policyDto.setPrpCmain(prpCmainService.findPrpCmainByPrimaryKey(policyNo));
		PrpCfeeId prpCfeeId = new PrpCfeeId();
		prpCfeeId.setPolicyNo(policyNo);
		prpCfeeId.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		policyDto.setPrpCfee(prpCfeeService.findPrpCfee(prpCfeeId));
		String conditions = " policyNo = '" + policyNo + "'";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		int pageNo = 0;
		int rowsPerPage = 0;
		if (policyDto.getPrpCmain() != null) {
			String riskCode = policyDto.getPrpCmain().getRiskCode();
			String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
			if ("E".equals(strRiskType) || "Q".equals(strRiskType)) {
				pageNo = 1;
				rowsPerPage = 20;
			}
			if ((!"D".equals(strRiskType)) && "02".equals(policyDto.getPrpCmain().getPolicyType())) { // 判断非车险並且是团险
				policyDto.setPrpCitemKindList(prpCitemKindService.findByConditionsDistinct(conditions, pageNo, rowsPerPage));
			} else {
				policyDto.setPrpCitemKindList(prpCitemKindService.findPrpCitemKind(queryRule));
			}
		} else {
			policyDto.setPrpCitemKindList(prpCitemKindService.findPrpCitemKind(queryRule));
		}

		policyDto.setPrpCaddressList(prpCaddressService.findPrpCaddress(queryRule));
		policyDto.setPrpCinsuredList(prpCinsuredService.findPrpCinsured(queryRule));
		policyDto.setPrpCitemCarList(prpCitemCarService.findPrpCitemCar(queryRule));
		policyDto.setPrpCprofitDetailList(prpCprofitDetailService.findPrpCprofitDetail(queryRule));
		policyDto.setPrpCprofitList(prpCprofitService.findPrpCprofit(queryRule));
		policyDto.setPrpCplanList(prpCplanService.findPrpCplan(queryRule));
		policyDto.setPrpCengageList(prpCengageService.findPrpCengage(queryRule));
		policyDto.setPrpCfeeList(prpCfeeService.findPrpCfee(queryRule));
		policyDto.setLiabStartDate(prpCmainLiabService.findByPrimaryKeyStartDate(policyNo));
		// 取得驾驶员信息
		policyDto.setPrpCcarDriverList(prpCcarDriverService.findPrpCcarDriver(queryRule));
		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
		prpLclaimStatusId.setBusinessNo(policyNo);
		prpLclaimStatusId.setNodeType("polic");
		prpLclaimStatusId.setSerialNo(0);
		policyDto.setPrpLclaimStatus(prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId));
		policyDto.setPrpCitemHouseList(prpCitemHouseService.findPrpCitemHouse(queryRule));
		PrpCmainLoanId prpCmainLoanId = new PrpCmainLoanId();
		prpCmainLoanId.setPolicyNo(policyNo);
		policyDto.setPrpCmainLoanList(prpCmainLoanService.findPrpCmainLoan(prpCmainLoanId));
		policyDto.setPrpCmainCargo(prpCmainCargoService.findPrpCmainCargo(policyNo));
		policyDto.setPrpClimitList(prpClimitService.findPrpClimit(queryRule));
		String conditions1 = "";
		conditions1 = " mainpolicyno= '" + policyNo + "' or  policyno= '" + policyNo + "'";
		QueryRule queryRule1 = QueryRule.getInstance();
		queryRule1.addSql(conditions1);
		policyDto.setPrpCmainSubList(prpCmainSubService.findPrpCmainSub(queryRule1));
		policyDto.setPrpLRegistRPolicyList(prpLregistrpolicyService.findPrplregistrpolicy(queryRule));
		policyDto.setPrpCitemPropList(prpCitemPropService.findPrpCitemProp(queryRule));
		QueryRule queryRule3 = QueryRule.getInstance();
		queryRule3.addEqual("policyNo", policyNo);
		policyDto.setPrpCmainConstructList(prpCmainConstructService.findPrpCmainConstruct(queryRule3));
		policyDto.setPrpCmainLiabList(prpCmainLiabService.findPrpCmainLiab(queryRule3));
		return policyDto;
	}

	/**
	 * 根据保单号获得保单主信息
	 * @param policyNo 保单号码
	 * @return 返回保单对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public PrpCmain findPrpCmainDtoByPrimaryKey(String policyNo) throws SQLException, Exception {
		return prpCmainService.findPrpCmainByPrimaryKey(policyNo);
	}

	/**
	 * 根据条件查询prpLacciPerson对象的序号
	 * @param condition 查询条件
	 * @return prpLacciPerson表的序号
	 * @throws SQLException
	 * @throws Exception
	 */
	public int findBySeriaNo(String condition) throws SQLException, Exception {
		int seriaNo = 0;
		seriaNo = prpLacciPersonService.findBySeriaNo(condition);
		return seriaNo;
	}

	/**
	 * 变更立案的操作状态的方法
	 *@param claimDto 立案对象
	 *@throws SQLException
	 *@throws Exception
	 *@return 无
	 */
	public void updateClaimStatus(PolicyDto policyDto) throws SQLException, Exception {
		// 示例未完成
		String statement = "";
		if (policyDto.getPrpLclaimStatus() != null) {
			String condition3 = " BusinessNo='" + StringUtils.rightTrim(policyDto.getPrpLclaimStatus().getId().getBusinessNo()) + "' " + " AND NodeType ='polic' ";
			statement = " DELETE FROM prpLclaimStatus Where " + condition3;
			super.getSession().createSQLQuery(statement).executeUpdate();
			prpLclaimStatusService.save(policyDto.getPrpLclaimStatus());
		}
	}

	/**
	 * 判断保单通知号是否存在
	 * @param policyNo 保单号码
	 * @return 是/否
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean isExist(String policyNo) throws SQLException, Exception {
		if (prpCmainService.findPrpCmainByPrimaryKey(policyNo) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断保单是否注销或退保
	 * @param wsRegistDto 报案Dto对象
	 * @return 19 注销保单;21 全单退保;空 正常保单
	 * @throws Exception
	 */
	public String isWithdraw(String PolicyNo, String DamageStartDate, String DamageStartHour) throws SQLException, Exception {
		// 取得出险後的批改信息
		String returnFlag = "";
		String conditions = " ";
		conditions = " PolicyNo = '" + PolicyNo + "'" + " AND ValidDate <= to_date('" + DamageStartDate + "','yyyy-mm-dd') " + " AND UnderWriteFlag in ('1', '3') " + " ORDER BY ENDORSENO ASC ";
		List<PrpPhead> prpPheadList = prpPheadService.findByConditions(conditions, 0, 0);
		if (prpPheadList == null || prpPheadList.size() < 1) {
		} else {
			PrpPhead prpPhead = null;
			for (int i = 0; i < prpPheadList.size(); i++) {
				prpPhead = (PrpPhead) prpPheadList.get(i);// 取批改的数据
				if(ConstantCodes.ENDORSE_CANCEL.contains(prpPhead.getEndorType())) {
					returnFlag = prpPhead.getEndorType();
					break;
				}
			}
		}
		return returnFlag;
	}

	/**
	 * 获得未缴费的期数
	 * @param conditions 查询条件
	 * @throws Exception
	 * @return Collection
	 */
	public int[] getDelinquentfeeTime(String conditions) throws Exception {
		String statement = "SELECT serialno FROM prpcplan where payno > 0 and delinquentfee >0 and (" + conditions + ")";
//		String statement = "Select serialno from intfPrpJpayRefrec Where " + "("+conditions +") and certiType In('P','E') and accountDate is null";
		List<?> qishuList = super.getSession().createSQLQuery(statement).list();
		int[] qishuArray = new int[qishuList.size()];
		for (int i = 0; i < qishuList.size(); i++) {
			qishuArray[i] = DataUtils.getInteger(qishuList.get(i));
		}
		return qishuArray;
	}

	/**
	 * 检查缴费情况
	 * @param conditions 查询条件
	 * @throws Exception
	 * @return int表示,-1未缴，0-未缴全,1表示缴全
	 */
	public int checkPay(String conditions) throws Exception {
		//判断是否intfprpjpayrefrec.accountdate实收标志
//		int intRet = -1;
//		String statement = "Select count(*),accountDate from intfPrpJpayRefrec Where " + "("+conditions +") and certiType In('P','E')  group by accountDate";
//		List<?> list = HibernateUtils.findbySql(super.getSession(),statement);
//		int countNullDate = 0;
//		int countDate = 0;
//		Object[] obj = null;
//		for(int i=0;i<list.size();i++){
//			obj = (Object[]) list.get(i);
//			if(obj[1]==null){
//				countNullDate++;
//			}else{
//				countDate++;
//			}
//		}
//		if(countDate>0&&countNullDate>0){
//			intRet = 0;
//		}else if(countDate>0&&countNullDate==0){
//			intRet = 1;
//		}else{
//			intRet = -1;
//		}
//		return intRet;
		conditions = "realpayrefflag = '0' and (certitype = 'P' or ( certitype='E' and planFee >= 0 ) ) AND  payrefreason Not In ('R72','R73','R74','R00','A01','A02') AND (" + conditions + ")";
		String statement = "Select count(*) from prpjpayrefrec Where " + conditions;
		int intReturn = ((Number) HibernateUtils.getCountbyCountSql(super.getSession(), statement)).intValue();
		int intRet = 1;
		// -1,表示未缴全，1表示全交了
		// reason:默认为-1，根本没有交费,不能用0，因为0是初始化的值，以後会有问题的
		if (intReturn > 0) {
			intRet = -1;
		} else {
			intRet = 1;
		}
		return intRet;
	}

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 开始页数
	 * @param rowsPerPage 每页显示条数
	 * @throws Exception
	 * @return Collection
	 */
	public Page findForRegistConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
//		return prpCmainService.findForRegistConditions(conditions, pageNo, rowsPerPage);
		String statements = " select t.* from prpcopymain t , prpphead p where t.endorseno = p.endorseno(+) " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), statements , pageNo, rowsPerPage, PrpCopyMain.class);
	}

	public PrpCfeeService getPrpCfeeService() {
		return prpCfeeService;
	}

	public void setPrpCfeeService(PrpCfeeService prpCfeeService) {
		this.prpCfeeService = prpCfeeService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public PrpCitemCarService getPrpCitemCarService() {
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public PrpCprofitDetailService getPrpCprofitDetailService() {
		return prpCprofitDetailService;
	}

	public void setPrpCprofitDetailService(PrpCprofitDetailService prpCprofitDetailService) {
		this.prpCprofitDetailService = prpCprofitDetailService;
	}

	public PrpCprofitService getPrpCprofitService() {
		return prpCprofitService;
	}

	public void setPrpCprofitService(PrpCprofitService prpCprofitService) {
		this.prpCprofitService = prpCprofitService;
	}

	public PrpCplanService getPrpCplanService() {
		return prpCplanService;
	}

	public void setPrpCplanService(PrpCplanService prpCplanService) {
		this.prpCplanService = prpCplanService;
	}

	public PrpCengageService getPrpCengageService() {
		return prpCengageService;
	}

	public void setPrpCengageService(PrpCengageService prpCengageService) {
		this.prpCengageService = prpCengageService;
	}

	public PrpCcarDriverService getPrpCcarDriverService() {
		return prpCcarDriverService;
	}

	public void setPrpCcarDriverService(PrpCcarDriverService prpCcarDriverService) {
		this.prpCcarDriverService = prpCcarDriverService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpCitemHouseService getPrpCitemHouseService() {
		return prpCitemHouseService;
	}

	public void setPrpCitemHouseService(PrpCitemHouseService prpCitemHouseService) {
		this.prpCitemHouseService = prpCitemHouseService;
	}

	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public PrpCitemPropService getPrpCitemPropService() {
		return prpCitemPropService;
	}

	public void setPrpCitemPropService(PrpCitemPropService prpCitemPropService) {
		this.prpCitemPropService = prpCitemPropService;
	}

	public PrpCmainLiabService getPrpCmainLiabService() {
		return prpCmainLiabService;
	}

	public void setPrpCmainLiabService(PrpCmainLiabService prpCmainLiabService) {
		this.prpCmainLiabService = prpCmainLiabService;
	}

	public PrpLacciPersonService getPrpLacciPersonService() {
		return prpLacciPersonService;
	}

	public void setPrpLacciPersonService(PrpLacciPersonService prpLacciPersonService) {
		this.prpLacciPersonService = prpLacciPersonService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpCmainConstructService getPrpCmainConstructService() {
		return prpCmainConstructService;
	}

	public void setPrpCmainConstructService(PrpCmainConstructService prpCmainConstructService) {
		this.prpCmainConstructService = prpCmainConstructService;
	}

	public PrpCmainLoanService getPrpCmainLoanService() {
		return prpCmainLoanService;
	}

	public void setPrpCmainLoanService(PrpCmainLoanService prpCmainLoanService) {
		this.prpCmainLoanService = prpCmainLoanService;
	}

	public PrpCmainSubService getPrpCmainSubService() {
		return prpCmainSubService;
	}

	public void setPrpCmainSubService(PrpCmainSubService prpCmainSubService) {
		this.prpCmainSubService = prpCmainSubService;
	}

	public PrpCmainCargoService getPrpCmainCargoService() {
		return prpCmainCargoService;
	}

	public void setPrpCmainCargoService(PrpCmainCargoService prpCmainCargoService) {
		this.prpCmainCargoService = prpCmainCargoService;
	}

	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}

	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpCmainCarGoSubService getPrpCmainCarGoSubService() {
		return prpCmainCarGoSubService;
	}

	public void setPrpCmainCarGoSubService(PrpCmainCarGoSubService prpCmainCarGoSubService) {
		this.prpCmainCarGoSubService = prpCmainCarGoSubService;
	}

	public PrpCitemShipService getPrpCitemShipService() {
		return prpCitemShipService;
	}

	public void setPrpCitemShipService(PrpCitemShipService prpCitemShipService) {
		this.prpCitemShipService = prpCitemShipService;
	}

	public PrpCplaneService getPrpCplaneService() {
		return prpCplaneService;
	}

	public void setPrpCplaneService(PrpCplaneService prpCplaneService) {
		this.prpCplaneService = prpCplaneService;
	}

	public PrpCCargoItemService getPrpCCargoItemService() {
		return prpCCargoItemService;
	}

	public void setPrpCCargoItemService(PrpCCargoItemService prpCCargoItemService) {
		this.prpCCargoItemService = prpCCargoItemService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
}
