package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyCopyService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.vo.PolicyCopyDto;
import com.sinosoft.claim.schema.model.PrpCopyFeeId;
import com.sinosoft.claim.schema.model.PrpCopyItemKind;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.service.facade.PrpCopyCarDriverService;
import com.sinosoft.claim.schema.service.facade.PrpCopyFeeService;
import com.sinosoft.claim.schema.service.facade.PrpCopyInsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCopyItemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCopyItemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCopyMainService;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.claim.schema.model.PrpCopyInsuredNature;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

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
public class PolicyCopyServiceSpringImpl extends GenericDaoHibernate<PolicyCopyDto, String> implements PolicyCopyService {
	/** 保单保额保费Service*/
	private PrpCopyFeeService prpCopyFeeService;
	/** 险别Service*/
	private PrpCopyItemKindService prpCopyItemKindService;
//	/** 被保险人地址Service*/
//	private PrpCopyAddressService prpCopyAddressService;
	/** 被保险人Service*/
	private PrpCopyInsuredService prpCopyInsuredService;
	/** 被保险车辆Service*/
	private PrpCopyItemCarService prpCopyItemCarService;
//	/** 优惠折扣明细Service*/
//	private PrpCopyProfitDetailService prpCopyProfitDetailService;
//	/** 优惠折扣Service*/
//	private PrpCopyProfitService prpCopyProfitService;
//	/** 缴费机会Service*/
//	private PrpCopyPlanService prpCopyPlanService;
//	/** 特别约定Service*/
//	private PrpCopyEngageService PrpCopyEngageService;
	/** 驾驶人信息Service*/
	private PrpCopyCarDriverService prpCopyCarDriverService;
	/** 理赔状态Service*/
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 房屋标的Service*/
//	private PrpCopyItemHouseService prpCopyItemHouseService;//暂无对应表
//	/** 保单限额Service*/
//	private PrpCopyLimitService prpCopyLimitService;//暂无对应表
	/** 保单关联报案Service*/
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 财产险标的Service*/
//	private PrpCopyItemPropService prpCopyItemPropService;
	/** 责任险保单Service*/
//	private PrpCopymainLiabService prpCopymainLiabService;
	/** 人伤跟踪Service*/
	private PrpLacciPersonService prpLacciPersonService;
	/** 保单Service*/
	private PrpCopyMainService prpCopyMainService;
	/** 建安工险保单Service*/
//	private PrpCopyMainConstructService prpCopyMainConstructService;
//	/** 贷款保险保单Service*/
//	private PrpCopyMainLoanService prpCopyMainLoanService;
//	/** 保单隶属Service*/
//	private PrpCopyMainSubService prpCopyMainSubService;
//	/** 货运险保单Service*/
//	private PrpCopyMainCargoService prpCopyMainCargoService;
	/** 批改信息Service*/
	private PrpPheadService prpPheadService;
	/** 批单数据传输对象服务 */
	private EndorseService endorseService;

	/**
	 * 保单保存方法
	 *@param policyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 *@return 无
	 */
	public void save(PolicyCopyDto policyCopyDto) throws SQLException, Exception {
		if (policyCopyDto.getPrpCopyMain() == null)
			throw new Exception();
		// new DBFcoPolicy(dbManager).insert(policyCopyDto.getFcoPolicyCopyDto());
		// 未完成，理赔部分不需要保存表信息
	}

	/**
	 * 保单删除
	 * @param fcoPolicyNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String endorseNo) throws SQLException, Exception {
		String condition = " endorseNo = " + "'" + StringUtils.rightTrim(endorseNo) + "';";
		String statement = " DELETE FROM prpCopyItemKind Where " + condition + " DELETE FROM prpCopyItemCar Where " + condition + " DELETE FROM prpCopyInsured Where " + condition + " DELETE FROM prpCopyAddress Where " + condition + " DELETE FROM prpCopyFee Where "
				+ condition + " DELETE FROM prpCopyPlan Where " + condition + " DELETE FROM prpCopyMain Where " + condition;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		HibernateUtils.executeSql(session, statement);
	}

	/**
	 * 保单查询方法
	 * @param policyCopyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public PolicyCopyDto findByPrimaryKey(String endorseNo) throws SQLException, Exception {
		PolicyCopyDto policyCopyDto = new PolicyCopyDto();
		// 取得涉案车辆
		if (!CommonUtils.isEmpty(endorseNo)) {
			policyCopyDto.setPrpCopyMain(prpCopyMainService.findPrpCopyMainByPrimaryKey(endorseNo));
		}
		// 取得涉案车辆
		policyCopyDto.setPrpPhead(this.prpPheadService.findByPrimaryKey(endorseNo));
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo);
		policyCopyDto.setPrpCopyItemKindList(this.prpCopyItemKindService.findPrpCopyItemKind(queryRule));
		policyCopyDto.setPrpCopyItemCarList(this.prpCopyItemCarService.findPrpCopyItemCar(queryRule));
		policyCopyDto.setPrpCopyFeeList(this.prpCopyFeeService.findPrpCopyFee(queryRule));
		// policyCopyDto.setPrpCaddressList(prpCaddressService.findPrpCaddress(queryRule));
		policyCopyDto.setPrpCopyInsuredList(prpCopyInsuredService.findPrpCopyInsured(queryRule));
		policyCopyDto.setPrpCopyItemCarList(prpCopyItemCarService.findPrpCopyItemCar(queryRule));
		//增加PrpCopyInsuredNature
		policyCopyDto.setPrpCopyInsuredNatureList(this.find(PrpCopyInsuredNature.class, queryRule));
		// policyCopyDto.setprpCopyprofitDetailList(prpCopyprofitDetailService.findprpCopyprofitDetail(queryRule));
		// policyCopyDto.setprpCopyprofitList(prpCopyprofitService.findprpCopyprofit(queryRule));
		// policyCopyDto.setprpCopyplanList(prpCopyplanService.findprpCopyplan(queryRule));
		if (endorseNo == null) {
			throw new UserException(-98, -1000, this.getClass().getName() + ".findByPrimaryKey(" + endorseNo + ")");
		}
		return policyCopyDto;
	}
	/**
	 * 保单查询方法
	 * @param policyCopyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public List<PrpCopyItemKind> findPrpCopyItemKind(String familyNo,String endorseNo) throws SQLException, Exception {
		// 取得涉案车辆
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo);
		if(familyNo!=null&&familyNo.length()!=0){
			queryRule.addEqual("familyNo", DataUtils.getInteger(familyNo));
		}
		List<PrpCopyItemKind> list = this.prpCopyItemKindService.findPrpCopyItemKind(queryRule);
		return list;
	}
	/**
	 * 有效保单查询方法
	 * @param policyCopyDto 保单对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	@SuppressWarnings("unused")
	public PolicyCopyDto findByPrimaryKey(String endorseNo, String strDamageDate) throws SQLException, Exception {
		PolicyCopyDto policyCopyDto = new PolicyCopyDto();
		// 取得涉案车辆
		policyCopyDto.setPrpCopyMain(prpCopyMainService.findPrpCopyMainByPrimaryKey(endorseNo));
		PrpCopyFeeId prpCopyFeeId = new PrpCopyFeeId();
		prpCopyFeeId.setEndorseNo(endorseNo);
		prpCopyFeeId.setCurrency(ConstantCodes.LOCAL_CURRENCY);
		policyCopyDto.setPrpCopyFee(prpCopyFeeService.findPrpCopyFee(prpCopyFeeId));
		String conditions = " endorseNo = '" + endorseNo + "'";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo);
		int pageNo = 0;
		int rowsPerPage = 0;
		if (policyCopyDto.getPrpCopyMain() != null) {
			String riskCode = policyCopyDto.getPrpCopyMain().getRiskCode();
			UICodeAction uiCodeAction = new UICodeAction();
			String strRiskType = uiCodeAction.translateRiskCodetoRiskType(riskCode);
			if ("E".equals(strRiskType) || "Q".equals(strRiskType)) {
				pageNo = 1;
				rowsPerPage = 20;
			}
//			if ((!"D".equals(strRiskType)) && "02".equals(policyCopyDto.getPrpCopyMain().getPolicyType())) { // 判断非车险並且是团险
//				policyCopyDto.setPrpCopyItemKindList(prpCopyItemKindService.findByConditionsDistinct(conditions, pageNo, rowsPerPage));
//			} else {
//				policyCopyDto.setPrpCopyItemKindList(prpCopyItemKindService.findPrpCopyItemKind(queryRule));
//			}
		} else {
//			policyCopyDto.setPrpCopyItemKindList(prpCopyItemKindService.findPrpCopyItemKind(queryRule));
		}

//		policyCopyDto.setPrpCopyAddressList(prpCopyAddressService.findPrpCopyAddress(queryRule));
//		policyCopyDto.setPrpCopyInsuredList(prpCopyInsuredService.findPrpCopyInsured(queryRule));
//		policyCopyDto.setPrpCopyItemCarList(prpCopyItemCarService.findPrpCopyItemCar(queryRule));
//		policyCopyDto.setPrpCopyProfitDetailList(prpCopyProfitDetailService.findPrpCopyProfitDetail(queryRule));
//		policyCopyDto.setPrpCopyProfitList(prpCopyProfitService.findPrpCopyProfit(queryRule));
//		policyCopyDto.setPrpCopyPlanList(prpCopyPlanService.findPrpCopyPlan(queryRule));
//		policyCopyDto.setPrpCopyEngageList(PrpCopyEngageService.findPrpCopyEngage(queryRule));
		policyCopyDto.setPrpCopyFeeList(prpCopyFeeService.findPrpCopyFee(queryRule));
//		policyCopyDto.setLiabStartDate(prpCopyMainLiabService.findByPrimaryKeyStartDate(endorseNo));
		// 取得驾驶员信息
		policyCopyDto.setPrpCopyCarDriverList(prpCopyCarDriverService.findPrpCopyCarDriver(queryRule));
		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
		prpLclaimStatusId.setBusinessNo(endorseNo);
		prpLclaimStatusId.setNodeType("polic");
		prpLclaimStatusId.setSerialNo(0);
		policyCopyDto.setPrpLclaimStatus(prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId));
//		policyCopyDto.setPrpCopyItemHouseList(prpCopyItemHouseService.findPrpCopyItemHouse(queryRule)); 
//		PrpCopyMainLoanId prpCopyMainLoanId = new PrpCopyMainLoanId();
//		prpCopyMainLoanId.setPolicyNo(endorseNo);
//		policyCopyDto.setPrpCopyMainLoanList(prpCopyMainLoanService.findPrpCopyMainLoan(prpCopyMainLoanId));
//		policyCopyDto.setPrpCopyMainCargo(prpCopyMainCargoService.findPrpCopyMainCargo(endorseNo));
//		policyCopyDto.setPrpCopyLimitList(prpCopyLimitService.findPrpCopyLimit(queryRule)); 
		String conditions1 = "";
		conditions1 = " mainpolicyno= '" + endorseNo + "' or  policyno= '" + endorseNo + "'";
		QueryRule queryRule1 = QueryRule.getInstance();
		queryRule1.addSql(conditions1);
//		policyCopyDto.setPrpCopyMainSubList(prpCopyMainSubService.findPrpCopyMainSub(queryRule1));
		policyCopyDto.setPrpLRegistRPolicyList(prpLregistrpolicyService.findPrplregistrpolicy(queryRule));
//		policyCopyDto.setPrpCopyItemPropList(prpCopyItemPropService.findPrpCopyItemProp(queryRule)); 
		QueryRule queryRule3 = QueryRule.getInstance();
		queryRule3.addEqual("endorseNo", endorseNo);
//		policyCopyDto.setPrpCopyMainConstructList(prpCopyMainConstructService.findPrpCopyMainConstruct(queryRule3));
//		policyCopyDto.setPrpCopyMainLiabList(prpCopyMainLiabService.findPrpCopyMainLiab(queryRule3)); 
		return policyCopyDto;
	}

	/**
	 * 根据保单号获得保单主信息
	 * @param endorseNo 保单号码
	 * @return 返回保单对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public PrpCopyMain findPrpCopyMainDtoByPrimaryKey(String endorseNo) throws SQLException, Exception {
		return prpCopyMainService.findPrpCopyMainByPrimaryKey(endorseNo);
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
	public void updateClaimStatus(PolicyCopyDto policyCopyDto) throws SQLException, Exception {
		// 示例未完成
		String statement = "";
		if (policyCopyDto.getPrpLclaimStatus() != null) {
			String condition3 = " BusinessNo='" + StringUtils.rightTrim(policyCopyDto.getPrpLclaimStatus().getId().getBusinessNo()) + "' " + " AND NodeType ='polic' ";
			statement = " DELETE FROM prpLclaimStatus Where " + condition3;
			super.getSession().createSQLQuery(statement).executeUpdate();
			prpLclaimStatusService.save(policyCopyDto.getPrpLclaimStatus());
		}
	}

	/**
	 * 判断保单通知号是否存在
	 * @param endorseNo 保单号码
	 * @return 是/否
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean isExist(String endorseNo) throws SQLException, Exception {
		if (prpCopyMainService.findPrpCopyMainByPrimaryKey(endorseNo) == null) {
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
				if (prpPhead.getEndorType().indexOf(ConstantCodes.EndorseType_19) > -1) {
					returnFlag = ConstantCodes.EndorseType_19;
					break;
				} else if (prpPhead.getEndorType().indexOf(ConstantCodes.EndorseType_21) > -1) {
					returnFlag = ConstantCodes.EndorseType_21;
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
		String statement = "SELECT serialno FROM prpCopyPlan where payno > 0 and delinquentfee >0 and (" + conditions + ")";
		List<?> qishuList = super.getSession().createQuery(statement).list();
		int[] qishuArray = new int[qishuList.size()];
		for (int i = 0; i < qishuList.size(); i++) {
			qishuArray[i] = ((Integer) qishuList.get(i)).intValue();
		}
		return qishuArray;
	}

	/**
	 * 检查缴费情况
	 * @param conditions 查询条件
	 * @throws Exception
	 * @return Collection
	 */
//	public int checkPay(String conditions) throws Exception {
//		conditions = " realpayrefflag = '0' and certitype In('P','E') AND  payrefreason Not In ('R72','R73','R74','R00','A01','A02') AND (" + conditions + ")";
//		String statement = "Select count(*) from prpjpayrefrec Where " + conditions;
//		int intReturn = ((Number) HibernateUtils.getCountbyCountSql(super.getSession(), statement)).intValue();
//		int intRet = 1;
//		// -1,表示未缴全，1表示全交了
//		// reason:默认为-1，根本没有交费,不能用0，因为0是初始化的值，以後会有问题的
//		if (intReturn > 0)
//			intRet = -2;
//		else
//			intRet = 1;
//		return intRet;
//	}

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 开始页数
	 * @param rowsPerPage 每页显示条数
	 * @throws Exception
	 * @return Collection
	 */
	public List<PrpCopyMain> findForRegistConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		return prpCopyMainService.findForRegistConditions(conditions, pageNo, rowsPerPage);
	}
	
	/**
	 * 获取出险时保单信息对应copy表中批单号
	 * @param strPolicyNo 保单号码
	 * @param strDamageDate 出险日期
	 * @param strDamageHour 出险小时
	 * @return 出险时保单信息对应copy表中批单号
	 * @throws UserException
	 */
	public String getBackWardEndorseNo(String strPolicyNo, String strDamageDate, String strDamageHour) throws Exception {
		String strEndorseNo = strPolicyNo;
		String iWherePart = "PolicyNo = '" + strPolicyNo + "'" + " AND (ValidDate <to_date('" + strDamageDate + "','yyyy-MM-dd') OR " + //
				"(ValidDate=to_date('" + strDamageDate + "','yyyy-MM-dd') AND ValidHour<=" + strDamageHour + "))" + //
				" AND UnderWriteFlag in ('1', '3') " + " ORDER BY InputDate DESC,EndorseTimes DESC ";
		List<PrpPhead> listTemp = this.endorseService.findByPrpPheadConditions(iWherePart);
		if (!CommonUtils.isEmpty(listTemp)) {
			PrpPhead prpPhead = listTemp.get(0);
			strEndorseNo = prpPhead.getEndorseNo();
		}
		return strEndorseNo;
	}

	public PrpCopyFeeService getPrpCopyFeeService() {
		return prpCopyFeeService;
	}

	public void setPrpCopyFeeService(PrpCopyFeeService prpCopyFeeService) {
		this.prpCopyFeeService = prpCopyFeeService;
	}

//	public PrpCopyItemKindService getPrpCopyItemKindService() {
//		return prpCopyItemKindService;
//	}
//
//	public void setPrpCopyItemKindService(PrpCopyItemKindService prpCopyItemKindService) {
//		this.prpCopyItemKindService = prpCopyItemKindService;
//	}
//
//	public PrpCopyAddressService getPrpCopyAddressService() {
//		return prpCopyAddressService;
//	}
//
//	public void setPrpCopyAddressService(PrpCopyAddressService prpCopyAddressService) {
//		this.prpCopyAddressService = prpCopyAddressService;
//	}
//
//	public PrpCopyInsuredService getPrpCopyInsuredService() {
//		return prpCopyInsuredService;
//	}
//
//	public void setPrpCopyInsuredService(PrpCopyInsuredService prpCopyInsuredService) {
//		this.prpCopyInsuredService = prpCopyInsuredService;
//	}
//
//	public PrpCopyItemCarService getPrpCopyItemCarService() {
//		return prpCopyItemCarService;
//	}
//
//	public void setPrpCopyItemCarService(PrpCopyItemCarService prpCopyItemCarService) {
//		this.prpCopyItemCarService = prpCopyItemCarService;
//	}
//
//	public PrpCopyProfitDetailService getPrpCopyProfitDetailService() {
//		return prpCopyProfitDetailService;
//	}
//
//	public void setPrpCopyProfitDetailService(PrpCopyProfitDetailService prpCopyProfitDetailService) {
//		this.prpCopyProfitDetailService = prpCopyProfitDetailService;
//	}
//
//	public PrpCopyProfitService getPrpCopyProfitService() {
//		return prpCopyProfitService;
//	}
//
//	public void setPrpCopyProfitService(PrpCopyProfitService prpCopyProfitService) {
//		this.prpCopyProfitService = prpCopyProfitService;
//	}
//
//	public PrpCopyPlanService getPrpCopyPlanService() {
//		return prpCopyPlanService;
//	}
//
//	public void setPrpCopyPlanService(PrpCopyPlanService prpCopyPlanService) {
//		this.prpCopyPlanService = prpCopyPlanService;
//	}
//
//	public PrpCopyEngageService getPrpCopyEngageService() {
//		return PrpCopyEngageService;
//	}
//
//	public void setPrpCopyEngageService(PrpCopyEngageService PrpCopyEngageService) {
//		this.PrpCopyEngageService = PrpCopyEngageService;
//	}

	public PrpCopyCarDriverService getPrpCopyCarDriverService() {
		return prpCopyCarDriverService;
	}

	public void setPrpCopyCarDriverService(PrpCopyCarDriverService prpCopyCarDriverService) {
		this.prpCopyCarDriverService = prpCopyCarDriverService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}


	public PrpLacciPersonService getPrpLacciPersonService() {
		return prpLacciPersonService;
	}

	public void setPrpLacciPersonService(PrpLacciPersonService prpLacciPersonService) {
		this.prpLacciPersonService = prpLacciPersonService;
	}

	public PrpCopyMainService getPrpCopyMainService() {
		return prpCopyMainService;
	}

	public void setPrpCopyMainService(PrpCopyMainService prpCopyMainService) {
		this.prpCopyMainService = prpCopyMainService;
	}

//	public PrpCopyMainConstructService getPrpCopyMainConstructService() {
//		return prpCopyMainConstructService;
//	}
//
//	public void setPrpCopyMainConstructService(PrpCopyMainConstructService prpCopyMainConstructService) {
//		this.prpCopyMainConstructService = prpCopyMainConstructService;
//	}
//
//	public PrpCopyMainLoanService getPrpCopyMainLoanService() {
//		return prpCopyMainLoanService;
//	}
//
//	public void setPrpCopyMainLoanService(PrpCopyMainLoanService prpCopyMainLoanService) {
//		this.prpCopyMainLoanService = prpCopyMainLoanService;
//	}
//
//	public PrpCopyMainSubService getPrpCopyMainSubService() {
//		return prpCopyMainSubService;
//	}
//
//	public void setPrpCopyMainSubService(PrpCopyMainSubService prpCopyMainSubService) {
//		this.prpCopyMainSubService = prpCopyMainSubService;
//	}
//
//	public PrpCopyMainCargoService getPrpCopyMainCargoService() {
//		return prpCopyMainCargoService;
//	}
//
//	public void setPrpCopyMainCargoService(PrpCopyMainCargoService prpCopyMainCargoService) {
//		this.prpCopyMainCargoService = prpCopyMainCargoService;
//	}

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

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public PrpCopyItemKindService getPrpCopyItemKindService() {
		return prpCopyItemKindService;
	}

	public void setPrpCopyItemKindService(PrpCopyItemKindService prpCopyItemKindService) {
		this.prpCopyItemKindService = prpCopyItemKindService;
	}

	public PrpCopyInsuredService getPrpCopyInsuredService() {
		return prpCopyInsuredService;
	}

	public void setPrpCopyInsuredService(PrpCopyInsuredService prpCopyInsuredService) {
		this.prpCopyInsuredService = prpCopyInsuredService;
	}

	public PrpCopyItemCarService getPrpCopyItemCarService() {
		return prpCopyItemCarService;
	}

	public void setPrpCopyItemCarService(PrpCopyItemCarService prpCopyItemCarService) {
		this.prpCopyItemCarService = prpCopyItemCarService;
	}

}
