package com.sinosoft.claim.compensate.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.Query;
import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompelMedical;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonHospital;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCengageService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpDlimitService;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;
import com.sinosoft.claim.schema.service.facade.PrpLcarInsuranceService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeeService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLdeductCondService;
import com.sinosoft.claim.schema.service.facade.PrpLdeductibleService;
import com.sinosoft.claim.schema.service.facade.PrpLearthquakeFundService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonHospitalService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLqualityCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeTaskService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.one.bpm.util.JbpmAPIUtil;
import com.sinosoft.prpall.pubfun.PubTools;
import com.sinosoft.reins.common.model.PrpLDangerItem;
import com.sinosoft.reins.common.model.PrpLDangerTot;
import com.sinosoft.reins.common.model.PrpLDangerUnit;
import com.sinosoft.reins.common.service.facade.PrpLDangerItemService;
import com.sinosoft.reins.common.service.facade.PrpLDangerTotService;
import com.sinosoft.reins.common.service.facade.PrpLDangerUnitService;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.dto.custom.UndwrtSubmitDto;
import com.sinosoft.utility.string.ChgDate;

/**
 * 实赔数据库管理对象
 * <p>
 * Title: 车险理赔实赔数据管理
 * </p>
 * <p>
 * Description: 车险理赔实赔数据管理
 * </p>
 * @author 中科软
 */
public class CompensateServiceSpringImpl extends GenericDaoHibernate<CompensateDto, String> implements CompensateService {

	/**立案服务*/
	private PrpLclaimService prpLclaimService;
	/**理算服务*/
	private PrpLcompensateService prpLcompensateService;
	/**定损服务*/
	private PrpLverifyLossService prpLverifyLossService;
	/**特别约定服务*/
	private PrpCengageService prpCengageService;
	/**保单限额/免赔服务*/
	private PrpClimitService prpClimitService;
	/**险别限额免赔服务*/
	private PrpDlimitService prpDlimitService;
	/**节点状态服务*/
	private PrpLclaimStatusService prpLclaimStatusService;
	/**赔款费用服务*/
	private PrpLchargeService prpLchargeService;
	/**理算书服务*/
	private PrpLctextService prpLctextService;
	/**车辆财产赔付信息服务*/
	private PrpLlossService prpLlossService;
	/**人员赔付信息服务*/
	private PrpLpersonLossService prpLpersonLossService;
	/**赔款计算金额服务*/
	private PrpLcfeeService prpLcfeeService;
	/**报案信息补充服务*/
	private PrpLregistExtService prpLregistExtService;
	/**免赔信息服务*/
	private PrpLdeductibleService prpLdeductibleService;
	/**质量评审内容服务*/
	private PrpLqualityCheckService prpLqualityCheckService;
	/**立案文字服务*/
	private PrpLltextService prpLltextService;
	/**人伤跟踪服务*/
	private PrpLacciPersonService prpLacciPersonService;
	/**联共保赔付金额分摊服务*/
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/**立案服务*/
	private PrpLdeductCondService prpLdeductCondService;
	/**赔付对象服务*/
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/**理賠的危險單位劃分表服务*/
	private PrpLDangerUnitService prpLDangerUnitService;
	/**理賠危險單位金額合計資訊表服务*/
	private PrpLDangerTotService prpLDangerTotService;
	/**理賠的危險單位資訊表服务*/
	private PrpLDangerItemService prpLDangerItemService;
	/**险别配置服务*/
	private PrpDriskConfigService prpDriskConfigService;
	/**工作流数据处理*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**工作流处理服务*/
	private WorkFlowService workFlowService;
	/**理赔工作流日志服务*/
	private SwfLogService swfLogService;
	/**核赔工作流日志服务*/
	private WfLogService wfLogService;
	/**赔案保单关联服务*/
	private PrplregistrpolicyService prpLregistrpolicyService;
	/**就诊医院service*/
	private PrpLpersonHospitalService prpLpersonHospitalService;
	private CodeService codeService;
	private UtiUserGradeTaskService utiUserGradeTaskService;
	/** 備案处理接口 */
	private PrpLregistService prpLregistService;
	/** 地震基金接口 */
	private PrpLearthquakeFundService prpLearthquakeFundService;
	private CertainLossService certainLossService;
	/** 车体险讯息接口 */
	private PrpLcarInsuranceService prpLcarInsuranceService;
	/**
	 * 实赔退回
	 */
	@Override
	public void backToCerta(String claimNo, PrpLverifyLoss prpLverifyLoss,WorkFlowDto workFlowDto) throws Exception {
		String conditions = "claimNo='" + claimNo + "'";
		String statement = "";
		String registNo = this.prpLclaimService.translateCode(claimNo, false);// 报案号
		// 如果有计算书，删除计算书信息
		long count = this.prpLcompensateService.getCount(conditions);
		if (count > 0) {
			// 删除计算书信息
			if (count > 1) {
				throw new UserException(1, 3, "1000", "可以回退的理算，計算書不能超過1個");
			}
			deleteByClaimNo(claimNo);
		}
		// 如果为首次退回则需要设置回退标记
		conditions = "registNo='" + registNo + "' and CompensateFlag='1'";
		count = this.prpLverifyLossService.getCount(conditions);
		if (count == 0) {
			// 设置定损信息为首次提交不能修改
			// [1]换件
			statement = " Update PrpLcomponent set CompensateBackFlag='1' Where registNo='" + registNo + "'"; 
			HibernateUtils.executeSql(super.getSession(), statement);
			// [2]修理
			statement = " Update PrpLrepairFee set CompensateBackFlag='1' Where registNo='" + registNo + "'"; 
			HibernateUtils.executeSql(super.getSession(), statement);
			// [3]人伤
			statement = " Update PrpLperson set CompensateBackFlag='1' Where registNo='" + registNo + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
			// [4]财产
			statement = " Update PrpLprop set CompensateBackFlag='1' Where registNo='" + registNo + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
		}
		statement = " Update prplverifyLoss set CompensateFlag='1', CompensateOpinion='" + prpLverifyLoss.getCompensateOpinion() + "', CompensateBackDate='" + prpLverifyLoss.getCompensateBackDate() + "', CompensateApproverCode='"
				+ prpLverifyLoss.getCompensateApproverCode() + "'  Where registNo='" + registNo + "'";
		HibernateUtils.executeSql(super.getSession(), statement);
		// 整理计算书的轨迹信息
		statement = " Update swfLog set nodeStatus='5' Where flowId='" + prpLverifyLoss.getFlowID() + "' and nodeType='compp' and nodestatus<4";
		HibernateUtils.executeSql(super.getSession(), statement);
		this.getWorkFlowService().deal(workFlowDto);
	}
	/**
	 * 复核实赔
	 */
	@Override
	public void approve(String compensateNo, String userCode, String underWriteFlag) throws Exception {
		this.prpLcompensateService.approve(compensateNo, userCode, underWriteFlag);
	}

	@Override
	public void delete(String compensateNo) throws Exception {
		String condition = " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		String statement = " DELETE FROM prpLcompensate Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLcfee Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLctext Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLcharge Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLloss Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLpersonLoss Where " + condition;
		condition = " businessno = '" + StringUtils.rightTrim(compensateNo) + "'";
		statement = " DELETE FROM swflog Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prplclaimstatus Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
	}
	/**
	 * 查询特别约定,赔偿限额/免赔额信息
	 */
	@Override
	public CompensateDto findByAppendInformation(CompensateDto compensateDto) throws Exception {
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
        if (prpLcompensate != null) {
        	QueryRule queryRule = QueryRule.getInstance();
        	queryRule.addEqual("id.policyNo", prpLcompensate.getPolicyNo());
            compensateDto.setPrpCengageList(this.prpCengageService.findPrpCengage(queryRule));
            compensateDto.setPrpClimitList(this.prpClimitService.findPrpClimit(queryRule));
            queryRule = QueryRule.getInstance();
            queryRule.addEqual("id.riskCode", prpLcompensate.getRiskCode());
            compensateDto.setPrpDlimitList(this.prpDlimitService.findByConditions(queryRule));
        }
        return compensateDto;
	}
	
	//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 start
	/**
	 * 取出計算書備註
	 * @param compensateNo 計算書號
	 * @return 計算書備註
	 */
	public String getContextByCompensateNo(String compensateNo) {
		try{
			String querySql = " SELECT NVL(listagg(CONTEXT,'') WITHIN GROUP(ORDER BY TEXTTYPE,LINENO),'') FROM prpLctext Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
			List<?> tempResult = HibernateUtils.findbySql(getSession(), querySql);
			String context = "";
			if (!CommonUtils.isEmpty(tempResult) && tempResult.get(0) != null) {
				context = tempResult.get(0).toString();
			}
			return context;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public BigDecimal getClaimSumPaidByClaimNo(String claimNo) {
		StringBuilder str = new StringBuilder();
		str.append(" SELECT ")//
			.append(" SUM( ")//
			.append(" NVL((SELECT SUM(SUMREALPAY) FROM PRPLLOSS WHERE PRPLLOSS.COMPENSATENO = COM.COMPENSATENO),0) + ")//
			.append(" NVL((SELECT SUM(SUMREALPAY) FROM PRPLPERSONLOSS WHERE PRPLPERSONLOSS.COMPENSATENO = COM.COMPENSATENO),0) + ")//
			.append(" NVL((SELECT SUM(REALPAY*-1) FROM PRPLREMNANT WHERE PRPLREMNANT.COMPENSATENO = COM.COMPENSATENO),0) ")//
			.append(" )AS SUMREALPAY ")//
			.append(" FROM PRPLCOMPENSATE COM ")//
			.append(" WHERE UNDERWRITEFLAG IN ('1','3') AND COM.CLAIMNO = '"+claimNo+"' ");
    	List<?> list = HibernateUtils.findbySql(super.getSession(), str.toString());
    	if (list!=null && !list.isEmpty()) {
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				return (BigDecimal)it.next();
			}
		}
		return new BigDecimal(0);
	}
	@Override
	public BigDecimal getClaimSumFeeByClaimNo(String claimNo) {
		StringBuilder str = new StringBuilder();
		str.append(" SELECT ")//
			.append(" SUM( ")//
			.append(" NVL((SELECT SUM(PRPLCHARGE.CHARGEAMOUNT) FROM PRPLCHARGE WHERE PRPLCHARGE.COMPENSATENO = COM.COMPENSATENO),0) ")//
			.append(" )AS CHARGEAMOUNT ")//
			.append(" FROM PRPLCOMPENSATE COM ")//
			.append(" WHERE UNDERWRITEFLAG IN ('1','3') AND COM.CLAIMNO = '"+claimNo+"' ");
    	List<?> list = HibernateUtils.findbySql(super.getSession(), str.toString());
    	if (list!=null && !list.isEmpty()) {
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				return (BigDecimal)it.next();
			}
		}
		return new BigDecimal(0);
	}
	//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 end
	
	/**
	 * 获得实赔查询信息
	 * 按条件从prplcompensate表,prplregist表和prplclaimstatus表中查询多条数据
	 */
	@Override
	public List<PrpLcompensate> findByApproveConditions(String conditions) throws Exception {
		String statement = "Select prplcompensate.CompensateNo, prplcompensate.PolicyNo, "
						 + "prplcompensate.ClaimNo, prplcompensate.OperatorCode, " 
						 + "prplcompensate.InputDate,prplcompensate.RiskCode from prplcompensate where " + conditions;
		List<?> tempList = HibernateUtils.findbySql(super.getSession(), statement);
		List<PrpLcompensate> list = new ArrayList<PrpLcompensate>();
		if(tempList!=null && !tempList.isEmpty()){
			PrpLcompensate prpLcompensate = null;
			Object[] object = null;
			for (Iterator<?> it = tempList.iterator() ; it.hasNext(); list.add(prpLcompensate)) {
				object = (Object[])it.next();
				prpLcompensate = new PrpLcompensate();
				prpLcompensate.setCompensateNo((String)object[0]);
	            prpLcompensate.setPolicyNo((String)object[1]);
	            prpLcompensate.setClaimNo((String)object[2]);
	            prpLcompensate.setOperatorCode((String)object[3]);
	            prpLcompensate.setInputDate(new Date(((Timestamp) object[4]).getTime()));
	            prpLcompensate.setRiskCode((String)object[5]);
			}
		}
		return list;
	}
	/**
	 * 获得实赔信息
	 */
	@Override
	public List<PrpLcompensate> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.prpLcompensateService.findPrpLcompensate(queryRule);
	}

	@Override
	public Page findByConditions(String conditions, int pageNo, int pageSize) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.prpLcompensateService.findPrpLcompensate(queryRule, pageNo, pageSize);
	}
	/**
	 * 获得实赔信息
	 */
	@Override
	public CompensateDto findByPrimaryKey(String compensateNo) throws Exception {
		CompensateDto compensateDto = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("compensateNo", compensateNo);
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		if (prpLcompensate != null) {
			compensateDto = new CompensateDto();
			compensateDto.setPrpLcompensate(prpLcompensate);
			compensateDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(compensateNo, "compe", 0)));
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", compensateNo);
			compensateDto.setPrpLchargeList(this.prpLchargeService.findPrpLcharge(queryRule));
			compensateDto.setPrpLlossList(this.prpLlossService.findPrpLloss(queryRule));
			compensateDto.setPrpLearthquakeFundList(prpLearthquakeFundService.findPrpLearthquakeFund(queryRule));
			compensateDto.setPrpLcfeeList(this.prpLcfeeService.findPrpLcfee(queryRule));
			queryRule = QueryRule.getInstance();
//			mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -start
//			queryRule.addLike("id.compensateNo", "C" + prpLcompensate.getClaimNo() + "%");
			queryRule.addEqual("id.compensateNo", compensateNo);
			queryRule.addAscOrder("id.serialNo");
//			mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -end
			compensateDto.setPrpLcarInsuranceList(prpLcarInsuranceService.findPrpLcarInsurance(queryRule));
			QueryRule queryRulePerson = QueryRule.getInstance();
			queryRulePerson.addEqual("id.compensateNo", compensateNo);
			queryRulePerson.addAscOrder("personNo");
			queryRulePerson.addAscOrder("id.serialNo");
			compensateDto.setPrpLpersonLossList(this.prpLpersonLossService.findPrpLpersonLoss(queryRulePerson));
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", compensateNo);
			queryRule.addAscOrder("id.textType");
			queryRule.addAscOrder("id.lineNo");
			compensateDto.setPrpLctextList(this.prpLctextService.findPrpLctext(queryRule));
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", compensateNo);
			queryRule.addAscOrder("id.certiType");
			queryRule.addAscOrder("id.serialNo");
			List<PrpLpayObjectInfo> listtemp = this.prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
			List<PrpLpayObjectInfo> list = new ArrayList<PrpLpayObjectInfo>();
			PrpLpayObjectInfo prpLpayObjectInfo = null;
			for (int i = 0; i < listtemp.size(); i++) {
				prpLpayObjectInfo = listtemp.get(i);
				// 利用反射实现将判断对象prpLpayObjectInfo中类型为String的参数（前6个final参数除外），如果为null或者"null",则赋值为"".
				Field[] fs = PrpLpayObjectInfo.class.getDeclaredFields();
				for (int j = 6; j < fs.length; j++) {
					Class<?> clazz = fs[j].getType();
					if (clazz.equals(String.class)) {
						String methodname = "get" + fs[j].getName().substring(0, 1).toUpperCase() + fs[j].getName().substring(1);
						Method m = PrpLpayObjectInfo.class.getMethod(methodname);
						if (m!=null&&null == m.invoke(prpLpayObjectInfo)) {
							String methodname2 = "set" + fs[j].getName().substring(0, 1).toUpperCase() + fs[j].getName().substring(1);
							Method n = PrpLpayObjectInfo.class.getMethod(methodname2, fs[j].getType());
							if(n!=null){
								n.invoke(prpLpayObjectInfo, "");
							}
						}
					}
				}
				if (prpLpayObjectInfo != null) {
					list.add(prpLpayObjectInfo);
				}
			}
			
			compensateDto.setPrpLpayObjectInfoList(list);
			
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", prpLcompensate.getPolicyNo());
			compensateDto.setPrpCengageList(this.prpCengageService.findPrpCengage(queryRule));
			compensateDto.setPrpClimitList(this.prpClimitService.findPrpClimit(queryRule));

			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.riskCode", prpLcompensate.getRiskCode());
			compensateDto.setPrpDlimitList(this.prpDlimitService.findByConditions(queryRule));
			PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
			compensateDto.setPrpLclaim(prpLclaim);
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", prpLcompensate.getRegistNo());
			compensateDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
		}
		return compensateDto;
	}

	@Override
	public CompensateDto findByPrimaryKey(String compensateNo, String caseType) throws Exception {
        CompensateDto compensateDto = this.findByPrimaryKey(compensateNo);
        if (compensateDto != null) {
			if (caseType != null) {//特殊赔案处理
				if ("3".equals(caseType.trim()) || "4".equals(caseType.trim())) {
					compensateDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(compensateNo, "speci", Integer.parseInt(DataUtils.nullToZero(caseType)))));
				} else {
					compensateDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(compensateNo, "compe", 0)));
				}
			}
			QueryRule queryRule = QueryRule.getInstance();
			//增加理赔免赔信息
			queryRule.addEqual("id.compensateNo", compensateNo);
			compensateDto.setPrpLdeductibleList(this.prpLdeductibleService.findPrpLdeductible(queryRule));
			
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", compensateNo);
			queryRule.addEqual("id.qualityCheckType", "compe");
			compensateDto.setPrpLqualityCheckList(this.prpLqualityCheckService.findPrpLqualityCheck(queryRule));
			PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", prpLclaim.getRegistNo());
			compensateDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
			
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("prpLclaim.claimNo", prpLclaim.getClaimNo());
			compensateDto.setPrpLltextList(this.prpLltextService.findPrpLltext(queryRule));
			
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.certiNo", prpLclaim.getClaimNo());
			queryRule.addEqual("flag", "1");
			compensateDto.setPrpLacciPersonList(this.prpLacciPersonService.findPrpLacciPerson(queryRule));
		}
        return compensateDto;
	}
	/**
	 * 按条件从prplcompensate表,prplregist表和prplclaimstatus表中查询多条数据
	 */
	@Override
	public List<PrpLcompensate> findByQueryConditions(String conditions) throws Exception {
    	return this.prpLcompensateService.findByQueryConditions(conditions, 0, 0);
	}

	@Override
	public Page findPageByConditions(String conditions, int pageNo, int pageSize) {
    	String statement = "Select DISTINCT a.ClaimNo,"
			+ "a.PolicyNo, a.CompensateNo, a.SumPaid, a.UnderWriteFlag, b.Status, b.RiskCode From "
			+ "(select * from PrpLClaimStatus where NodeType='compe') b LEFT JOIN PrpLcompensate a "
			+ "ON a.CompensateNo = b.BusinessNo  where" + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), statement, pageNo, pageSize);
		List<?> tempList = page.getResult();
		List<PrpLcompensate> resultList = new ArrayList<PrpLcompensate>();
		if(tempList!=null && !tempList.isEmpty()){
			PrpLcompensate prpLcompensate = null;
			Object[] object = null;
			BigDecimal d = null;
			String compensateNo = null;
			for (Iterator<?> it = tempList.iterator() ; it.hasNext();) {
				object = (Object[])it.next();
				compensateNo = (String)object[2];
				if(DataUtils.emptyToNull(compensateNo)==null){
					continue;
				}
				prpLcompensate = new PrpLcompensate();
				prpLcompensate.setClaimNo((String)object[0]);
	            prpLcompensate.setPolicyNo((String)object[1]);
	            prpLcompensate.setCompensateNo(compensateNo);
	            d = (BigDecimal)object[3];
	            prpLcompensate.setSumPaid(d==null?0:d.doubleValue());
	            prpLcompensate.setUnderWriteFlag((String)object[4]);
	            prpLcompensate.setStatus((String)object[5]);
	            prpLcompensate.setRiskCode((String)object[6]); 
	            resultList.add(prpLcompensate);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}
	/**
	 * 根据赔案号得到已决赔款
	 */
	@Override
	public CompensateFeeDto findCompensateFeeByClaimNo(String claimNo) throws Exception {
		CompensateFeeDto compensateFeeDto = new CompensateFeeDto();
		String statement = " select sum(t.sumpaid)  from prplcompensate t  where  t.claimno = '"+claimNo+"' and (t.UnderWriteFlag = 1 or t.UnderWriteFlag =3)";
    	List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
    	if (list!=null && !list.isEmpty()) {
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				BigDecimal temp = (BigDecimal)it.next();
				if (temp != null ) {
					compensateFeeDto.setSumPaid(temp.doubleValue());
				}
			}
		}
		return compensateFeeDto;
	}
	/**
	 * 根据条件查询实赔标的信息
	 */
	@Override
	public List<PrpLloss> findLossByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.prpLlossService.findPrpLloss(queryRule);
	}
	/**
	 * 获得实赔人员信息
	 */
	@Override
	public List<PrpLpersonLoss> findPersonLossByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.prpLpersonLossService.findPrpLpersonLoss(queryRule);
	}
	/**
	 * 判断理算任务是否可以提交
	 * @author 中科软
	 * @param businessNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getCompFlagByConditions(String businessNo) throws Exception {
		String compFlag = "";
		String statement = " compensateNo like 'C"+businessNo+"%' order by compensateNo desc ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule = QueryRule.getInstance();
		queryRule.addSql(statement);
		List<PrpLcompensate> compensateList = this.prpLcompensateService.findPrpLcompensate(queryRule);
		int index = 0;
		if(compensateList ==null || compensateList.isEmpty()){
			compFlag = "0";//没有出计算书
		}else{
			compFlag = "2";//默认全核赔通过
			for(PrpLcompensate p : compensateList){
				if(!"1".equals(p.getUnderWriteFlag()) && !"3".equals(p.getUnderWriteFlag())){
					compFlag = "1";//存在未审核通过的计算书
					index++;
					break;
				}
			}
		}
		List<SwfLog> canceList = this.getSwfLogService().findByConditions(" businessNo='"+businessNo+"' and nodeType='cance' ");
		compFlag = compFlag+"-"+index;
		if(canceList.size()>0){
			compFlag +="-1";
		}else{
			compFlag +="-0";
		}
		return compFlag;
	}
	/**
	 * 判断实赔通知号是否存在
	 */
	@Override
	public boolean isExist(String compensateNo) throws Exception {
		return this.prpLcompensateService.findPrpLcompensate(compensateNo)!=null;
	}
	/**
	 * 判断理算是否可以注销
	 */
	@Override
	public boolean isRejectByConditions(String businessNo, String conditions) throws Exception {
		//由判断是否存在理算节点改成判断是否存在计算书节点来判断理算是否可以注销
		List<SwfLog> compensateList = this.getSwfLogService().findByConditions(" keyin='"+businessNo+"' and nodeType='compp' ");
		//特殊赔案获取需要根据KEYIN字段查询
		List<SwfLog> prpLprepayList = this.getSwfLogService().findByConditions(" keyin='"+businessNo+"' and nodeType='speci' ");
		//判断是否已经申请注销/拒赔
		List<SwfLog> canceList = this.getSwfLogService().findByConditions(" businessNo='"+businessNo+"' and nodeType='cance' ");
		if(compensateList.size()==0&&prpLprepayList.size()==0&&canceList.size()==0){
			return true;
		}
		return false;
	}

	@Override
	public void save(CompensateDto compensateDto) throws Exception {
		if (compensateDto.getPrpLcompensate() == null) {
			throw new UserException(0, 0, "實賠", "實賠信息不存在！");
		}
		// 添加本位币兑换率和本位币赔款
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String compensateNo = prpLcompensate.getCompensateNo();
		if(compensateNo.startsWith("R")){//追償計算書
			PrpLcompensate replevyPrpLcompensate = this.getPrpLcompensateService().getReplevyPrpLcompensate(prpLcompensate.getClaimNo());
			if(replevyPrpLcompensate!=null && !compensateNo.equals(replevyPrpLcompensate.getCompensateNo())){
				//本次保存的不是追償登錄那張計算書
				replevyPrpLcompensate.setUnderWriteFlag(prpLcompensate.getUnderWriteFlag());
				this.prpLcompensateService.saveOrUpdate(replevyPrpLcompensate);
			}else{
				PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
				if(!"1".equals(prpLclaim.getReplevyFlag())){
					prpLclaim.setReplevyFlag("1");
					this.prpLclaimService.save(prpLclaim);
				}
			}
		}
		this.deleteSubInfo(compensateDto);
		ChgDate thisDte = new ChgDate();
		double exchangeRate = PubTools.getExchangeRate(prpLcompensate.getCurrency(), ConstantCodes.LOCAL_CURRENCY, thisDte.getCurrentTime("yyyy-MM-dd"));
		prpLcompensate.setExchangeRate(exchangeRate);
		prpLcompensate.setPaidCNY(prpLcompensate.getSumThisPaid() * exchangeRate);
		// 首先删除原来的相关数据
		this.prpLcompensateService.saveOrUpdate(compensateDto.getPrpLcompensate());
		List<PrpLcfeecoins> prpLcfeecoinsList = compensateDto.getPrpLcfeecoinsList();
		if (prpLcfeecoinsList!= null && !prpLcfeecoinsList.isEmpty()) {
			this.prpLcfeecoinsService.save(prpLcfeecoinsList);
		}
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
 		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			this.prpLchargeService.save(prpLchargeList);
		}
 		List<PrpLpayObjectInfo> prpLpayObjectInfoList = compensateDto.getPrpLpayObjectInfoList();
 		if (prpLpayObjectInfoList != null && !prpLpayObjectInfoList.isEmpty()) {
			this.prpLpayObjectInfoService.save(prpLpayObjectInfoList);
		}
 		List<PrpLdeductCond> prpLdeductCondList = prpLcompensate.getPrpLdeductCondList();
		if (prpLdeductCondList!= null && !prpLdeductCondList.isEmpty()) {
			for (PrpLdeductCond p: prpLdeductCondList) {
				p.getId().setCompensateNo(prpLcompensate.getCompensateNo());
			}
			this.prpLdeductCondService.save(prpLdeductCondList);
		}
		/*
		 * 如果是追偿，回写立案信息 增加非车巨灾代码的保存和更新 
		 */
		if ("E".equals(prpLcompensate.getCaseType()) && compensateDto.getPrpLclaim() != null) {
			this.prpLclaimService.update(compensateDto.getPrpLclaim());
		} else if (compensateDto.getPrpLclaim() != null && compensateDto.getPrpLclaim().getCatastropheCode1() != null) {
			this.prpLclaimService.update(compensateDto.getPrpLclaim());
		}
		// reason:增加危险单位
		if (compensateDto.getPrplRiskUnitList() != null) {
//			BLPrpLdangerUnitFacade pu = new BLPrpLdangerUnitFacade();
			for (PrpLDangerUnit prpLdangerUnitDto: compensateDto.getPrplRiskUnitList()) {
				prpLDangerUnitService.save(prpLdangerUnitDto);
			}
		}
		if (compensateDto.getPrpLprpLdangerTotList() != null) {
//			BLPrpLdangerTotFacade ptf = new BLPrpLdangerTotFacade();
			for (PrpLDangerTot prpLdangerTotDto: compensateDto.getPrpLprpLdangerTotList()) {
				prpLDangerTotService.save(prpLdangerTotDto);
			}
		}
		if (compensateDto.getPrpLprpLdangerItemList() != null) {
//			BLPrpLdangerItemFacade pif = new BLPrpLdangerItemFacade();
			for (PrpLDangerItem prpLdangerItemDto: compensateDto.getPrpLprpLdangerItemList()) {
				prpLDangerItemService.save(prpLdangerItemDto);
			}
		}
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null) {
			this.prpLlossService.save(prpLlossList);
		}
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null) {
			this.prpLpersonLossService.save(prpLpersonLossList);
		}
		List<PrpLpersonHospital> prpLpersonHospitalList = compensateDto.getPrpLpersonHospitalList();
		if(prpLpersonHospitalList!=null&&!prpLpersonHospitalList.isEmpty()){
			prpLpersonHospitalService.save(prpLpersonHospitalList);
		}
		if (compensateDto.getPrpLctextList() != null) {
			this.prpLctextService.save(compensateDto.getPrpLctextList());
		}
		if (compensateDto.getPrpLqualityCheckList() != null) {
			this.prpLqualityCheckService.save(compensateDto.getPrpLqualityCheckList());
		}
		// 扩展信息
		if (compensateDto.getPrpLregistExtList() != null) {
			this.prpLregistExtService.save(compensateDto.getPrpLregistExtList());
		}
		if (compensateDto.getPrpLcfeeList() != null) {
			this.prpLcfeeService.save(compensateDto.getPrpLcfeeList());
		}
		if (compensateDto.getPrpLltextList() != null) {
			this.prpLltextService.save(compensateDto.getPrpLltextList());
		}
		if (compensateDto.getPrpLacciPersonList() != null) {
			for (PrpLacciPerson prpLacciPerson : compensateDto.getPrpLacciPersonList()) {
				this.prpLacciPersonService.updateFlag(prpLacciPerson);
			}
		}
		if(compensateDto.getPrpLpayObjectInfoList()!=null && compensateDto.getPrpLpayObjectInfoList().size()>0){
			this.prpLpayObjectInfoService.save(compensateDto.getPrpLpayObjectInfoList());
		}
		if(compensateDto.getPrpLearthquakeFundList()!=null&&compensateDto.getPrpLearthquakeFundList().size()>0){
			this.prpLearthquakeFundService.save(compensateDto.getPrpLearthquakeFundList());
		}
		if(compensateDto.getCertainLossDto()!=null&&"1".equals(compensateDto.getPrpLclaim().getSimpleFlag())){
			certainLossService.save(compensateDto.getCertainLossDto());
		}
		if(compensateDto.getPrpLcarInsuranceList()!=null){
			this.prpLcarInsuranceService.save(compensateDto.getPrpLcarInsuranceList());
		}
		// 进行状态的改变
		this.updateClaimStatus(compensateDto);

		//理算出險原因修改同步
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		String sql = null;
		if (!CommonUtils.isEmpty(prpLcompensate.getDamageCode())) {
			sql = "update PrpLclaim set damageCode = ? , damageName = ?  where claimNo = ? ";
			HibernateUtils.executeSql(super.getSession(), sql , prpLcompensate.getDamageCode() , prpLcompensate.getDamageName() , prpLclaim.getRegistNo());
			//理算修改更新立案、備案出險原因
			String classCode = this.codeService.translateClassCodeByRiskCode(prpLcompensate.getRiskCode());
			if (ConstantCodes.CLASSCODE_D_B.equals(classCode)) {
				sql = "update PrpLregist set damageCodeBZ = ? , damageNameBZ = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLcompensate.getDamageCode() , prpLcompensate.getDamageName() , prpLclaim.getRegistNo());
				sql = "update PrpLcheck set damageCodeBZ = ? , damageNameBZ = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLcompensate.getDamageCode() , prpLcompensate.getDamageName() , prpLclaim.getRegistNo());
			} else {
				sql = "update PrpLregist set damageCode = ? , damageName = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLcompensate.getDamageCode() , prpLcompensate.getDamageName() , prpLclaim.getRegistNo());
				sql = "update PrpLcheck set damageCode = ? , damageName = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLcompensate.getDamageCode() , prpLcompensate.getDamageName() , prpLclaim.getRegistNo());
			}
		}
		// 強制險計算書存儲時，對醫療費用收據資料的處理
		if("B01".equals(prpLcompensate.getRiskCode()) && compensateNo.startsWith("C") 
				 && CommonUtils.isEmpty(prpLcompensate.getMutualCompensateNo())){
			this.saveCompePrpLcompelMedical(compensateDto);
		}
	}
	/**
	 * 保存实赔带工作流
	 */
	@Override
	public void save(Boolean isSumbitUndwrt, CompensateDto compensateDto, WorkFlowDto workFlowDto) throws Exception {
		try {
			this.save(compensateDto);// 因暂时不能与工作流共享事务，所以单独处理。
			PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
			if (workFlowDto != null) {
				this.getWorkFlowService().deal(workFlowDto);
			}
			// 使业务提交、工作流提交和核赔工作流提交在同一事务内操作
			if (isSumbitUndwrt) {
				int vericLogNo = 0;
				List<SwfLog> list = workFlowDto.getSubmitSwfLogList();
				if (list != null && !list.isEmpty()) {
					for (SwfLog swfLog : list) {// SubmitSwfLogList里面不一定第一个封装的就是核赔节点，所以用下面方式取核赔节点序号
						if ("veric".equals(swfLog.getNodeType())) {
							vericLogNo = swfLog.getId().getLogNo();
							break;
						}
					}
				}
				ActionContext act = ActionContext.getContext();
				Map<String, Object> session = act.getSession();
				UserDto user = (UserDto) session.get("user");
				// 组织数据
				UndwrtSubmitDto undwrtSubmitDto = new UndwrtSubmitDto();
				undwrtSubmitDto.setModelType("22");
				undwrtSubmitDto.setCertiType("C");
				undwrtSubmitDto.setBusinessNo(prpLcompensate.getCompensateNo());
				undwrtSubmitDto.setRiskCode(prpLcompensate.getRiskCode());
				undwrtSubmitDto.setClassCode(prpLcompensate.getClassCode());
				undwrtSubmitDto.setComCode(user.getComCode());
				undwrtSubmitDto.setMakecom(prpLcompensate.getMakeCom());
				undwrtSubmitDto.setUserCode(user.getUserCode());
				undwrtSubmitDto.setHandlerCode(prpLcompensate.getHandlerCode());
				undwrtSubmitDto.setHandler1Code(prpLcompensate.getHandler1Code());
				undwrtSubmitDto.setContractNo("");
				undwrtSubmitDto.setClaimFlag("claim");
				undwrtSubmitDto.setLFlowID(workFlowDto.getUpdateSwfLog().getId().getFlowID());
				undwrtSubmitDto.setLLogNo(vericLogNo);
				undwrtSubmitDto.setPolicyNo(prpLcompensate.getPolicyNo());
				undwrtSubmitDto.setClaimNo(prpLcompensate.getClaimNo());
				// 提交核赔开始
				// new BLTaskAction().start(dbManager, undwrtSubmitDto);
				Map<String, String> infoMap = new HashMap<String, String>();
				infoMap.put("comCode", user.getComCode());
				wfLogService.start(undwrtSubmitDto, infoMap);
			}
		} catch (Exception e) {
			if (workFlowDto.isNewWorkFlow()) {//处理工作流引擎renw
				JbpmDto jbpmDto = workFlowDto.getJbpmDto();
				if(jbpmDto!=null&&jbpmDto.getBpmSuccess()){
					//jbpm事务回滚
					JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(),jbpmDto.getTaskId());
					jbpmDto.setBpmSuccess(false);
				}
			}
			throw e;
		}
	}
	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 */
	@ProcessTask(processId = "claim_05",userId="compe",businessBeanOffset=0,businessIdAttributeName="businessNo")
	@TaskParams(taskParams={@TaskParam(key="nodeType", paramValueBeanOffset=0,paramValueAttributeName="nodeType")})
	public void saveBpm(JbpmDto jbpmDto,boolean isSumbitUndwrt, CompensateDto compensateDto, WorkFlowDto workFlowDto, UserDto user) throws Exception{
		this.save(isSumbitUndwrt, compensateDto, workFlowDto);
	}
	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 */
	@ProcessTask(processId = "claim_reCase_05",userId="compe",businessBeanOffset=0,businessIdAttributeName="businessNo")
	@TaskParams(taskParams={@TaskParam(key="nodeType", paramValueBeanOffset=0,paramValueAttributeName="nodeType")})
	public void saveReCaseBpm(JbpmDto jbpmDto,boolean isSumbitUndwrt, CompensateDto compensateDto, WorkFlowDto workFlowDto, UserDto user) throws Exception{
		this.save(isSumbitUndwrt, compensateDto, workFlowDto);
	}
	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 * 退回到单证节点
	 */
	@ProcessTask(processId = "claim_05",userId="compe",businessBeanOffset=0,businessIdAttributeName="businessNo")
	@TaskParams(taskParams={@TaskParam(key="nodeType", paramValueBeanOffset=0,paramValueAttributeName="nodeType")})
	public void saveBpmCerti(JbpmDto jbpmDto,WorkFlowDto workFlowDto) throws Exception{
		this.getWorkFlowService().deal(workFlowDto);
	}
	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 * 退回到定损节点
	 */
	@ProcessTask(processId = "claim_05",userId="compe",businessBeanOffset=0,businessIdAttributeName="businessNo")
	@TaskParams(taskParams={@TaskParam(key="threeCar", paramValueBeanOffset=0,paramValueAttributeName="threeCar"),
			@TaskParam(key="wound", paramValueBeanOffset=0,paramValueAttributeName="wound"),
			@TaskParam(key="propc", paramValueBeanOffset=0,paramValueAttributeName="propc"),
			@TaskParam(key="nodeType", paramValueBeanOffset=0,paramValueAttributeName="nodeType"),
	        @TaskParam(key = "nodeListThree", paramValueBeanOffset = 0, paramValueAttributeName = "nodeListThree")})
	public void saveBpmCerta(JbpmDto jbpmDto,String claimNo, WorkFlowDto workFlowDto, PrpLverifyLoss prpLverifyLoss) throws Exception{
		this.backToCerta(claimNo, prpLverifyLoss, workFlowDto);
	}
    
    /**
	 * 实赔删除,删除一个案件的所有计算书
	 * @param fcoCompensateNoticeNo
	 * @throws Exception
	 */
	@Override
    public void deleteByClaimNo(String claimNo) throws Exception {
    	String condition  = " compensateNo in (select compensateNo from prpLcompensate Where claimNo='"+claimNo+ "')";
    	String condition1 = " businessno in (select compensateNo from prpLcompensate Where claimNo='"+claimNo+ "')";
      	String condition2 = " registno in (select compensateNo from prpLcompensate Where claimNo='"+claimNo+ "')";
    	  //示例未完成
        //1.8	删除理算报告文字信息
        String statement = "delete prplctext where "+condition +" and  textType = '1'";
        HibernateUtils.executeSql(super.getSession(), statement);
        //1.9	删除人员赔付信息表
        statement = "delete prplpersonloss where "+condition;
        HibernateUtils.executeSql(super.getSession(), statement);
		//1.10	删除赔付标的信息表
        statement = "delete prplloss where "+condition;
        HibernateUtils.executeSql(super.getSession(), statement);
		//1.11	费用信息表
        statement = "delete prplcharge where "+condition;
        HibernateUtils.executeSql(super.getSession(), statement);
		//1.12	赔款计算金额表
        statement = "delete prplcfee where "+condition;
        HibernateUtils.executeSql(super.getSession(), statement);
		//1.13	联共保相关信息
        //statement = "delete prplcfeecoins where "+condition1;
		//1.14	删除质量评审内容表（阳光目前没用到，但是，有数据）
        statement = "delete PrpLqualityCheck where "+condition2;
        HibernateUtils.executeSql(super.getSession(), statement);
        //1.16 删除处理情况的内容
        statement = " DELETE FROM prplclaimstatus Where " + condition1 ;
        HibernateUtils.executeSql(super.getSession(), statement);
		//1.15	删除计算书主表
        statement = "delete prplcompensate where claimNo='"+claimNo+ "'";
        HibernateUtils.executeSql(super.getSession(), statement);
		
    }
    
    /**
     * 实赔删除子表信息
     * @param compensateNo
     * @throws SQLException
     * @throws Exception
     */
    private void deleteSubInfo(CompensateDto compensateDto) throws Exception {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String compensateNo = compensateDto.getPrpLcompensate().getCompensateNo();
//		String condition = " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		String statement = ""; // 示例未完成
		statement = " DELETE FROM prpLctext Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		session.beginTransaction();
		statement = " DELETE FROM prpLcharge Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		// 免赔条件相关信息
		statement = " DELETE FROM prpldeductcond Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		// reason:增加危险单位
//		String conditions = " certino = '" + StringUtils.rightTrim(compensateNo) + "'";
		statement = " DELETE FROM prpldangertot Where " + " certino = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		// reason:加入理算免赔额
		statement = " DELETE FROM PrpLdeductible Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpldangeritem Where " + " certino = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpldangerunit Where " + " certino = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpLpersonLoss Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpLloss Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpLcfee Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpLendor Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prplcfee Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		HibernateUtils.executeSql(session, statement);
//		condition = " registNo = '" + compensateNo + "' and QualityCheckType='compe'";
		statement = " DELETE FROM PrpLqualityCheck Where " + " registNo = '" + compensateNo + "' and QualityCheckType='compe'";
		HibernateUtils.executeSql(session, statement);
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		if(prpLclaim!=null){
//			condition = " registNo = " + "'" + prpLclaim.getRegistNo() + "'";
			// 删除扩展信息
			statement = " DELETE FROM PrpLregistExt Where " + " registNo = " + "'" + prpLclaim.getRegistNo() + "'";
			HibernateUtils.executeSql(session, statement);
//			condition = " ClaimNo = '" + prpLclaim.getClaimNo() + "' AND TextType in ('05','08')";
			statement = " DELETE FROM PrpLlText Where " + " ClaimNo = '" + prpLclaim.getClaimNo() + "' AND TextType in ('05','08')";
			HibernateUtils.executeSql(session, statement);
		}
		// 删除联共保信息
//		condition = " BusinessNo = '" + compensateDto.getPrpLcompensate().getCompensateNo() + "'";
		statement = " DELETE FROM PrpLcFeecoins Where " + "BusinessNo = '" + compensateDto.getPrpLcompensate().getCompensateNo() + "'";
		HibernateUtils.executeSql(session, statement);
		// 删除支付对象信息
		statement = " DELETE FROM PrpLpayObjectInfo Where " + "compensateNo = '" + compensateDto.getPrpLcompensate().getCompensateNo() + "'";
		HibernateUtils.executeSql(session, statement);
//		statement = " DELETE FROM PrpLcompensate Where " + " CompensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
//		HibernateUtils.executeSql(session, statement);
		this.prpLcompensateService.delete(compensateNo);
		
		statement = " DELETE FROM prplpersonhospital Where compensateNo = '" + compensateDto.getPrpLcompensate().getCompensateNo() + "'";
		HibernateUtils.executeSql(session, statement);
		
		statement = " DELETE FROM PrpLearthquakeFund Where compensateNo = '" + compensateDto.getPrpLcompensate().getCompensateNo() + "'";
		HibernateUtils.executeSql(session, statement);
		
		statement = " DELETE FROM prpLcarInsurance Where compensateNo = '" + compensateDto.getPrpLcompensate().getCompensateNo() + "'";
		HibernateUtils.executeSql(session, statement);
    }
    
    /**
     * 变更实赔的操作状态的方法
     * @param compensateDto 立案对象
     * @throws SQLException
     * @throws Exception
     * @return 无
     */
    public void updateClaimStatus(CompensateDto compensateDto) throws Exception {
        //示例未完成
        String statement = "";
        PrpLclaimStatus prpLclaimStatus = compensateDto.getPrpLclaimStatus();
        if (prpLclaimStatus!= null) {
            String condition3 = " BusinessNo='"
                    + StringUtils.rightTrim(prpLclaimStatus.getId().getBusinessNo()) + "' "
                    + " AND NodeType ='" + prpLclaimStatus.getId().getNodeType() + "' and TypeFlag='"
                    + prpLclaimStatus.getTypeFlag().trim() + "'";
            statement = " DELETE FROM prpLclaimStatus Where " + condition3;
            HibernateUtils.executeSql(super.getSession(), statement);
            this.prpLclaimStatusService.save(prpLclaimStatus);
        }
    }
    
    /**
     * @param userCode
     * @return
     * @throws Exception
     * 查询用户是否有差额赔付权限,没有返回false
     */
    public String findExceedingPayout(UserDto userDto)throws Exception{
    	String exceedingPayout = String.valueOf(utiUserGradeTaskService.checkPower(userDto, ConstantCodes.EXCEEDING));
    	return exceedingPayout;
    }
    /**
     * 根据立案号获取本案已审核通过的计算书的险别赔付信息
     * @param claimNo 立案号 （任意险）
     * @return
     * @throws Exception 
     */
    public Map<String,Double> getPastCompePayAmount(String claimNo) throws Exception{
    	Map<String,Double> pastPay = new HashMap<String,Double>();
    	if(DataUtils.emptyToNull(claimNo)!=null){
    		//1、获取本案审核通过的计算书
    		String statement = "SELECT COMPENSATENO FROM PRPLCOMPENSATE WHERE CLAIMNO =? AND UNDERWRITEFLAG ='1' ";
    		List<?> compeList = super.getSession().createSQLQuery(statement).setString(0, claimNo).list();
    		if(compeList!=null && !compeList.isEmpty()){
    		   	String compeSql = "";
    		   	for(int i=0;i<=compeList.size()-1;i++){
    		   		compeSql+=(i>0)?" OR ":"";
    		   		compeSql+="COMPENSATENO = '" + String.valueOf(compeList.get(i))+"'";
    		   	}
    		   	//2、获取本案所有险别的赔付金额
    		   	statement = "Select kindcode,Sum(sumrealpay) sumrealpay From ("
    		   	         +"Select kindcode,Sum(sumrealpay) sumrealpay From prplloss Where ("+compeSql+") Group By kindcode"
    		   	         +" UNION ALL "
    		   	         +"Select kindcode,Sum(sumrealpay) sumrealpay From prplpersonloss Where ("+compeSql+") Group By kindcode"
    		   	         +" ) Group By kindcode";
    		   	List<?> list = super.getSession().createSQLQuery(statement).list();
    		   	if(list!=null && !list.isEmpty()){
    		   		Object[] objs = null;
    		   		for(Object temp:list){
    		   			objs = (Object[])temp;
    		   			pastPay.put(String.valueOf(objs[0]),((Number)objs[1]).doubleValue());
    		   		}
    		   	}
    		}
    	}
    	return pastPay;
    }
    /**
     * 更具立案号查询是否关联报案，关联报案。另外的案件是否也需要出计算书
     * @param claimNo
     * @return
     * @throws Exception
     */
    public String getRelatedCompe(String claimNo) throws Exception{
    	String relatedClaim = "false";
    	PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
    	boolean flag = prpLregistrpolicyService.isCompelFlag(prpLclaim.getRegistNo());
    	if(flag){
    		String sql = " select count(1) from prpLcompensate where claimNo in (select claimNo from prplclaim where registno='"+prpLclaim.getRegistNo()+"') and underWriteFlag  not in ('0')";
    		Long count = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
    		if(count==0){
    			relatedClaim = "true";
    		}
    	}
    	return relatedClaim;
    }
    
	@Override
	public List<PrpLloss> getPrpLlossForReplevy(String claimNo) throws Exception {
		String statement = "select p.riskCode,p.kindcode,SUM(ROUND(p.sumrealpay*p.exchRate,0)) sumrealpay from prplloss p,prplcompensate c where p.compensateno=c.compensateno and c.claimno = '" + claimNo + "' and c.compensateno like 'C" + claimNo
				+ "%' and (c.UnderWriteFlag = '1' or c.UnderWriteFlag = '3' or c.UnderWriteFlag = '9' ) GROUP BY p.riskCode,kindCode";
		List<?> list = super.getSession().createSQLQuery(statement).list();
		Map<String, PrpLloss> map = new LinkedHashMap<String, PrpLloss>();
		PrpLloss prpLloss = null;
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				Object[] object = (Object[]) obj;
				prpLloss = map.get(String.valueOf(object[0]) + "、" + String.valueOf(object[1]));
				if (prpLloss == null) {
					prpLloss = new PrpLloss();
				}
				prpLloss.setRiskCode(String.valueOf(object[0]));
				prpLloss.setKindCode(String.valueOf(object[1]));
				prpLloss.setSumLoss(prpLloss.getSumLoss() + ((Number) object[2]).doubleValue());
				map.put(prpLloss.getRiskCode() + "、" + prpLloss.getKindCode(), prpLloss);
			}
		}
		statement = "select p.riskCode,p.kindcode,SUM(ROUND(p.sumrealpay*p.exchRate,0)) sumrealpay from prplpersonloss p,prplcompensate c where p.compensateno=c.compensateno and c.claimno = '" + claimNo + "' and c.compensateno like 'C" + claimNo
				+ "%' and (c.UnderWriteFlag = '1' or c.UnderWriteFlag = '3' or c.UnderWriteFlag = '9') GROUP BY p.riskCode,kindCode";
		list = super.getSession().createSQLQuery(statement).list();
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				Object[] object = (Object[]) obj;
				prpLloss = map.get(String.valueOf(object[0]) + "、" + String.valueOf(object[1]));
				if (prpLloss == null) {
					prpLloss = new PrpLloss();
				}
				prpLloss.setRiskCode(String.valueOf(object[0]));
				prpLloss.setKindCode(String.valueOf(object[1]));
				prpLloss.setSumLoss(prpLloss.getSumLoss() + ((Number) object[2]).doubleValue());
				map.put(prpLloss.getRiskCode() + "、" + prpLloss.getKindCode(), prpLloss);
			}
		}
		List<PrpLloss> lossList = new ArrayList<PrpLloss>();
		lossList.addAll(map.values());
		for(PrpLloss p : lossList){
			p.setSumDefPay(p.getSumLoss());//追偿时 ，将险别的赔款金额存入核定赔偿
		}
		return lossList;
	}
	
	@Override
	public Map<String, Map<String, Double>> getClaimKindCodePay(String claimNo) throws Exception {
		Map<String, Double> claim = new HashMap<String, Double>();
		//統計賠付
		Map<String, Double> pay = new HashMap<String, Double>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select p.riskCode,p.kindcode,SUM(ROUND(p.sumrealpay*p.exchRate,0)) sumrealpay ");
		sb.append(" from prplloss p,prplcompensate c ");
		sb.append(" where p.compensateno=c.compensateno and c.claimno = '").append(claimNo).append("'");
		sb.append(" and ");
		sb.append(" c.compensateno like 'C" + claimNo + "%' ");
		sb.append(" and ");
		sb.append(" (c.UnderWriteFlag = '1' or c.UnderWriteFlag = '3' ) ");
		sb.append(" GROUP BY p.riskCode,kindCode ");
		String tempKey = null;
		Double tempValue = null;
		List<?> list = super.getSession().createSQLQuery(sb.toString()).list();
		if (!CommonUtils.isEmpty(list)) {
			for (Object obj : list) {
				Object[] object = (Object[]) obj;
				tempKey = String.valueOf(object[1]);
				tempValue = ((Number) object[2]).doubleValue();
				if (pay.containsKey(tempKey)) {
					pay.put(tempKey, pay.get(tempKey) + tempValue);
				} else {
					pay.put(tempKey, tempValue);
				}
				if (claim.containsKey(tempKey)) {
					claim.put(tempKey, claim.get(tempKey) + tempValue);
				} else {
					claim.put(tempKey, tempValue);
				}
			}
		}
		sb = new StringBuilder();
		sb.append(" select p.riskCode,p.kindcode,SUM(ROUND(p.sumrealpay*p.exchRate,0)) sumrealpay ");
		sb.append(" from prplpersonloss p,prplcompensate c ");
		sb.append(" where p.compensateno=c.compensateno and c.claimno = '").append(claimNo).append("'");
		sb.append(" and ");
		sb.append(" c.compensateno like 'C" + claimNo + "%' ");
		sb.append(" and ");
		sb.append(" (c.UnderWriteFlag = '1' or c.UnderWriteFlag = '3' ) ");
		sb.append(" GROUP BY p.riskCode,kindCode ");
		list = super.getSession().createSQLQuery(sb.toString()).list();
		if (!CommonUtils.isEmpty(list)) {
			for (Object obj : list) {
				Object[] object = (Object[]) obj;
				tempKey = String.valueOf(object[1]);
				tempValue = ((Number) object[2]).doubleValue();
				if (pay.containsKey(tempKey)) {
					pay.put(tempKey, pay.get(tempKey) + tempValue);
				} else {
					pay.put(tempKey, tempValue);
				}
				if (claim.containsKey(tempKey)) {
					claim.put(tempKey, claim.get(tempKey) + tempValue);
				} else {
					claim.put(tempKey, tempValue);
				}
			}
		}
		//統計追償
		sb = new StringBuilder();
		sb.append(" select p.riskCode,p.kindcode,SUM(ROUND(p.sumrealpay*p.exchRate,0)) sumrealpay ");
		sb.append(" from prplloss p,prplcompensate c ");
		sb.append(" where p.compensateno=c.compensateno and c.claimno = '").append(claimNo).append("'");
		sb.append(" and ");
		sb.append(" c.compensateno like 'R" + claimNo + "%' ");
		sb.append(" and ");
		sb.append(" (c.UnderWriteFlag = '1' or c.UnderWriteFlag = '3' ) ");
		sb.append(" GROUP BY p.riskCode,kindCode ");
		list = super.getSession().createSQLQuery(sb.toString()).list();
		Map<String, Double> replevy = new HashMap<String, Double>();
		if (!CommonUtils.isEmpty(list)) {
			for (Object obj : list) {
				Object[] object = (Object[]) obj;
				tempKey = String.valueOf(object[1]);
				tempValue = ((Number) object[2]).doubleValue();
				if (replevy.containsKey(tempKey)) {
					replevy.put(tempKey, replevy.get(tempKey) + tempValue);
				} else {
					replevy.put(tempKey, tempValue);
				}
				if (claim.containsKey(tempKey)) {
					claim.put(tempKey, claim.get(tempKey) + tempValue);
				} else {
					claim.put(tempKey, tempValue);
				}
			}
		}
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		map.put("C", pay);
		map.put("R", replevy);
		map.put("CLAIM", claim);
		return map;
	}
	
	
	@Override
	public List<PrpLloss> getReplevyInfoByClaim(String claimNo) throws Exception {
		Map<String,PrpLloss> map = new LinkedHashMap<String,PrpLloss>();
		//計算每個險別已賠付金額
		List<PrpLloss> lossList = this.getPrpLlossForReplevy(claimNo);//各險別的賠付
		for(PrpLloss p : lossList){
			map.put(p.getKindCode(), p);
		}
		//需要減去每個險別的已追償金額，追償金額 sumRealPay 為負值
		String statement = "select p.kindCode,SUM(ROUND(p.sumrealpay*p.exchRate,0)) sumRealPay from prplloss p,prplcompensate c where p.compensateno=c.compensateno and c.claimno = '"+claimNo+"' and c.caseType = 'R' and (c.UnderWriteFlag = '1' or c.UnderWriteFlag = '3') GROUP BY p.kindCode";
		List<?> list = super.getSession().createSQLQuery(statement).list();
		if(list!=null && !list.isEmpty()){
			PrpLloss temp = null;
			for(Object obj : list){
				Object[] object = (Object[])obj;
				temp = map.get(String.valueOf(object[0]));
				if(temp!=null){
					temp.setSumRealPay(temp.getSumRealPay()+((Number)object[1]).doubleValue());//temp.getSumRealPay()為險別已賠付的金額，sumRealPay為已追償的金額
				}
			}
		}
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		prpLlossList.addAll(map.values());
		return prpLlossList;
	}
	
	/**
	 * 获取案件已核赔通过，可以互冲的计算书 
	 */
	public List<String> getMutualCompensateNo(String claimNo) throws Exception {
		String statement = "select compensateNo from prplcompensate where claimNo = '"+claimNo+"' and (underWriteFlag = '1' or underWriteFlag = '3') and sumPaid <> 0 and compensateNo like 'C"+claimNo+"%' and mutualCompensateNo is null "
		+" and not EXISTS (select certino from PrpJPayRefRecHis where certino = compensateno and realpayrefflag = '1' and certitype in ('C','Y')) order by compensateNo asc ";
		List<?> resultList = super.getSession().createSQLQuery(statement).list();
		List<String> list = new ArrayList<String>();
		if(resultList != null && !resultList.isEmpty()){
			for(Object obj : resultList){
				list.add(String.valueOf(obj));
			}
		}
		return list;
	}
	@Override
	public List<String> getPayRiskCode(String compensateNo) {
		StringBuffer statement = new StringBuffer("");
		statement.append("select DISTINCT(kindcode) from PRPLPERSONLOSS where compensateno = '" + compensateNo + "'");
		statement.append(" union all ");
		statement.append("select DISTINCT(kindcode) from PRPLCHARGE where compensateno = '" + compensateNo + "' ");
		statement.append(" union all ");
		statement.append("select DISTINCT(kindcode) from PRPLLOSS where compensateno = '" + compensateNo + "'");
		List<?> resultList = super.getSession().createSQLQuery(statement.toString()).list();
		List<String> list = new ArrayList<String>();
		if(resultList != null && !resultList.isEmpty()){
			for(Object obj : resultList){
				if(!list.contains(String.valueOf(obj))){
					list.add(String.valueOf(obj));
				}
			}
		}
		return list;
	}
	
	/***
	 * 理算撤銷簡易賠案流程
	 */
	public void saveCancelSimpleCase(PrpLclaim prpLclaim, SwfLog currSwfLog) throws Exception {
		Session session = super.getSession();
		String flowID = currSwfLog.getId().getFlowID();
		int logNo = currSwfLog.getId().getLogNo();
		String statement = " update PrpLclaim set simpleFlag = '0' where claimNo = '" + prpLclaim.getClaimNo() + "'";
		HibernateUtils.executeSql(session, statement);
		statement = " update SwfLog set nodeStatus = '0' where nodeStatus = '6' and flowID = '" + flowID + "' and nodeType = 'sched' ";
		HibernateUtils.executeSql(session, statement);
		statement = " delete from SwfLog where flowID = '" + flowID + "' and logNo = " + logNo;
		HibernateUtils.executeSql(session, statement);
		statement = " delete from SwfPathLog where flowID = '" + flowID + "' and endNodeNo = " + logNo;
		HibernateUtils.executeSql(session, statement);
	}
	
	@Override
	public List<PrpLcompelMedical> findPrpLcompelMedical(String compensateNo, String identifyNumber) throws Exception {
		String sql = "select * from PrpLcompelMedical where compensateNo = ? and identifyNumber = ? ";
		List<PrpLcompelMedical> resutlt = HibernateUtils.executeQuery(PrpLcompelMedical.class, super.getSession(), sql, compensateNo, identifyNumber);
		return resutlt;
	}
	
	@Override
	public List<PrpLcompelMedical> findPrpLcompelMedical(String compensateNo) throws Exception {
		String sql = "select * from PrpLcompelMedical where compensateNo = ? order by personNo , identifyNumber , serialNo ";
		List<PrpLcompelMedical> resutlt = HibernateUtils.executeQuery(PrpLcompelMedical.class, super.getSession(), sql, compensateNo);
		return resutlt;
	}

	@Override
	public void savePrpLcompelMedical(String compensateNo, String personNo, List<PrpLcompelMedical> prpLcompelMedicalList) throws Exception {
		this.deletePrpLcompelMedical(compensateNo, personNo);
		if(!CommonUtils.isEmpty(prpLcompelMedicalList)){
			super.saveAll(prpLcompelMedicalList);
		}
	}

	@Override
	public void deletePrpLcompelMedical(String compensateNo, String personNo) throws Exception {
		// 刪除指定受害人的醫療給付費用收據資料
		String sql = "delete from PrpLcompelMedical where compensateNo = ? and personNo = ? ";
		HibernateUtils.executeSql(super.getSession(), sql, compensateNo, personNo);
	}
	
	/**
	 * 強制險理算存儲時，對受害人醫療費用收據的處理
	 */
	private void saveCompePrpLcompelMedical(CompensateDto compensateDto) throws Exception {
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String compensateNo = prpLcompensate.getCompensateNo();
		// 更新以賠案號作為存儲對象的
		String sql = "update PrpLcompelMedical set compensateNo = ? where compensateNo = ? ";
		HibernateUtils.executeSql(super.getSession(), sql , compensateNo , prpLcompensate.getClaimNo());
		// 獲取處理狀態
		String status = compensateDto.getPrpLclaimStatus().getStatus();
		if("4".equals(status)){// 如果是處理提交，則需要校驗收據費用與畫面收集的是否一致，若不一致則不
			List<PrpLcompelMedical> prpLcompelMedicalList = this.findPrpLcompelMedical(compensateNo);
			if(!CommonUtils.isEmpty(prpLcompelMedicalList)){
				Map<String , PrpLcompelMedical> dbMedical = new HashMap<String , PrpLcompelMedical>();
				PrpLcompelMedical prpLcompelMedical = null;
				for(PrpLcompelMedical m : prpLcompelMedicalList){
					if (dbMedical.containsKey(m.getId().getIdentifyNumber())) {// 受害人已存在
						prpLcompelMedical = dbMedical.get(m.getId().getIdentifyNumber());
					} else {
						prpLcompelMedical = new PrpLcompelMedical();
						prpLcompelMedical.getId().setIdentifyNumber(m.getId().getIdentifyNumber());
						prpLcompelMedical.setPersonName(m.getPersonName());
					}
					prpLcompelMedical.setA01((prpLcompelMedical.getA01()== null ? 0d : prpLcompelMedical.getA01()) + (m.getA01()== null ? 0d : m.getA01()));
					prpLcompelMedical.setA021((prpLcompelMedical.getA021()== null ? 0d : prpLcompelMedical.getA021()) + (m.getA021()== null ? 0d : m.getA021()));
					prpLcompelMedical.setA022((prpLcompelMedical.getA022()== null ? 0d : prpLcompelMedical.getA022()) + (m.getA022()== null ? 0d : m.getA022()));
					prpLcompelMedical.setA023((prpLcompelMedical.getA023()== null ? 0d : prpLcompelMedical.getA023()) + (m.getA023()== null ? 0d : m.getA023()));
					prpLcompelMedical.setA024((prpLcompelMedical.getA024()== null ? 0d : prpLcompelMedical.getA024()) + (m.getA024()== null ? 0d : m.getA024()));
					prpLcompelMedical.setA025((prpLcompelMedical.getA025()== null ? 0d : prpLcompelMedical.getA025()) + (m.getA025()== null ? 0d : m.getA025()));
					prpLcompelMedical.setA026((prpLcompelMedical.getA026()== null ? 0d : prpLcompelMedical.getA026()) + (m.getA026()== null ? 0d : m.getA026()));
					prpLcompelMedical.setA029((prpLcompelMedical.getA029()== null ? 0d : prpLcompelMedical.getA029()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029a()== null ? 0d : m.getA029a()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029b()== null ? 0d : m.getA029b()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029c()== null ? 0d : m.getA029c()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029z()== null ? 0d : m.getA029z()));
					prpLcompelMedical.setA03((prpLcompelMedical.getA03()== null ? 0d : prpLcompelMedical.getA03()) + (m.getA03()== null ? 0d : m.getA03()));
					prpLcompelMedical.setA04((prpLcompelMedical.getA04()== null ? 0d : prpLcompelMedical.getA04()) + (m.getA04()== null ? 0d : m.getA04()));
					prpLcompelMedical.setHealthPoints((prpLcompelMedical.getHealthPoints()== null ? 0d : prpLcompelMedical.getHealthPoints()) + (m.getHealthPoints()== null ? 0d : m.getHealthPoints()));
					prpLcompelMedical.setHealthAmount((prpLcompelMedical.getHealthAmount()== null ? 0d : prpLcompelMedical.getHealthAmount()) + (m.getHealthAmount()== null ? 0d : m.getHealthAmount()));
					dbMedical.put(m.getId().getIdentifyNumber(), prpLcompelMedical);
				}
				//對當前畫面收集的受害人醫療費用資料進行匯總處理
				Map<String , PrpLcompelMedical> currMedical = new HashMap<String , PrpLcompelMedical>();
				for(PrpLpersonLoss prpLpersonLoss : compensateDto.getPrpLpersonLossList()){
					if(!dbMedical.containsKey(prpLpersonLoss.getIdentifyNumber())){
						continue ;
					}
					if(currMedical.containsKey(prpLpersonLoss.getIdentifyNumber())){//
						prpLcompelMedical = currMedical.get(prpLpersonLoss.getIdentifyNumber());
					} else {
						prpLcompelMedical = new PrpLcompelMedical();
						prpLcompelMedical.getId().setIdentifyNumber(prpLpersonLoss.getIdentifyNumber());
						prpLcompelMedical.setPersonName(prpLpersonLoss.getPersonName());
						prpLcompelMedical.setPersonNo(prpLpersonLoss.getPersonNo());
						prpLcompelMedical.setHealthPoints((prpLpersonLoss.getHealthPoints()== null ? 0d : prpLpersonLoss.getHealthPoints()));
						prpLcompelMedical.setHealthAmount((prpLpersonLoss.getHealthAmount()== null ? 0d : prpLpersonLoss.getHealthAmount()));
					}
					if (!CommonUtils.isEmpty(prpLpersonLoss.getLiabDetailCode())) {
						switch (prpLpersonLoss.getLiabDetailCode()) {
						case "A01":
							prpLcompelMedical.setA01((prpLcompelMedical.getA01() == null ? 0d : prpLcompelMedical.getA01()) + prpLpersonLoss.getSumRealPay());break;
						case "A021":
							prpLcompelMedical.setA021((prpLcompelMedical.getA021() == null ? 0d : prpLcompelMedical.getA021()) + prpLpersonLoss.getSumRealPay());break;
						case "A022":
							prpLcompelMedical.setA022((prpLcompelMedical.getA022() == null ? 0d : prpLcompelMedical.getA022()) + prpLpersonLoss.getSumRealPay());break;
						case "A023":
							prpLcompelMedical.setA023((prpLcompelMedical.getA023() == null ? 0d : prpLcompelMedical.getA023()) + prpLpersonLoss.getSumRealPay());break;
						case "A024":
							prpLcompelMedical.setA024((prpLcompelMedical.getA024() == null ? 0d : prpLcompelMedical.getA024()) + prpLpersonLoss.getSumRealPay());break;
						case "A025":
							prpLcompelMedical.setA025((prpLcompelMedical.getA025() == null ? 0d : prpLcompelMedical.getA025()) + prpLpersonLoss.getSumRealPay());break;
						case "A026":
							prpLcompelMedical.setA026((prpLcompelMedical.getA026() == null ? 0d : prpLcompelMedical.getA026()) + prpLpersonLoss.getSumRealPay());break;
						case "A029":
							prpLcompelMedical.setA029((prpLcompelMedical.getA029() == null ? 0d : prpLcompelMedical.getA029()) + prpLpersonLoss.getSumRealPay());break;
						case "A03":
							prpLcompelMedical.setA03((prpLcompelMedical.getA03() == null ? 0d : prpLcompelMedical.getA03()) + prpLpersonLoss.getSumRealPay());break;
						case "A04":
							prpLcompelMedical.setA04((prpLcompelMedical.getA04() == null ? 0d : prpLcompelMedical.getA04()) + prpLpersonLoss.getSumRealPay());break;
						}
						if("A01".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA01((prpLcompelMedical.getA01() == null ? 0d : prpLcompelMedical.getA01()) + prpLpersonLoss.getSumRealPay());
						}else if("A021".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA021((prpLcompelMedical.getA021() == null ? 0d : prpLcompelMedical.getA021()) + prpLpersonLoss.getSumRealPay());
						}else if("A022".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA022((prpLcompelMedical.getA022() == null ? 0d : prpLcompelMedical.getA022()) + prpLpersonLoss.getSumRealPay());
						}else if("A023".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA023((prpLcompelMedical.getA023() == null ? 0d : prpLcompelMedical.getA023()) + prpLpersonLoss.getSumRealPay());
						}else if("A024".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA024((prpLcompelMedical.getA024() == null ? 0d : prpLcompelMedical.getA024()) + prpLpersonLoss.getSumRealPay());
						}else if("A025".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA025((prpLcompelMedical.getA025() == null ? 0d : prpLcompelMedical.getA025()) + prpLpersonLoss.getSumRealPay());
						}else if("A026".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA026((prpLcompelMedical.getA026() == null ? 0d : prpLcompelMedical.getA026()) + prpLpersonLoss.getSumRealPay());
						}else if("A029".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA029((prpLcompelMedical.getA029() == null ? 0d : prpLcompelMedical.getA029()) + prpLpersonLoss.getSumRealPay());
						}else if("A03".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA03((prpLcompelMedical.getA03() == null ? 0d : prpLcompelMedical.getA03()) + prpLpersonLoss.getSumRealPay());
						}else if("A04".equals(prpLpersonLoss.getLiabDetailCode())){
							prpLcompelMedical.setA04((prpLcompelMedical.getA04() == null ? 0d : prpLcompelMedical.getA04()) + prpLpersonLoss.getSumRealPay());
						}
					}
					currMedical.put(prpLpersonLoss.getIdentifyNumber(), prpLcompelMedical);
				}
				// 校驗一致性
				for (Entry<String, PrpLcompelMedical> entry : currMedical.entrySet()) {
					prpLcompelMedical = dbMedical.get(entry.getKey());// 受害人已存儲的收據資料費用彙總
					dbMedical.remove(entry.getKey());
					PrpLcompelMedical entryValue = entry.getValue();// 本次提交待存儲的受害人醫療費用彙總
					// 校驗不一致的，資料狀態為 “暫存”
					String medicalStatus = prpLcompelMedical.feeEquals(entryValue) ? "4" : "2";
					sql = "update PrpLcompelMedical set personName = ? , personNo = ? , status = ?  where compensateNo = ? and identifyNumber = ? ";
					HibernateUtils.executeSql(super.getSession(), sql, entryValue.getPersonName(), entryValue.getPersonNo(), medicalStatus, compensateNo, entryValue.getId().getIdentifyNumber());
				}
				if (!dbMedical.isEmpty()) {
					StringBuffer removeIdentifyNumbers = new StringBuffer();
					String separator = "','";
					for (String s : dbMedical.keySet()) {
						removeIdentifyNumbers.append(separator).append(s);
					}
					sql = "delete from PrpLcompelMedical where compensateNo = ? and identifyNumber in ('" + removeIdentifyNumbers + "') ";
					HibernateUtils.executeSql(super.getSession(), sql, compensateNo);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page findPrpLcompelMedical(String statements, Object[] params, int pageNo, int pageSize) {
		Session session = super.getSession();
		long count = 0L;
		Query query;
		List<?> resultList;
		if (pageNo > 0 && pageSize > 0) {
			// 統計數量
			String countSQL = "select count(0) from ( " + statements + " ) num ";
			query = session.createSQLQuery(countSQL);
			if (!CommonUtils.isEmpty(params)) {
				for (int i = 0, l = params.length; i < l; i++) {
					query.setParameter(i, params[i]);
				}
			}
			resultList = query.list();
			if (resultList.size() > 0) {
				BigDecimal object = (BigDecimal) resultList.get(0);
				count = object.longValue();
			}
			if (count == 0) {
				return new Page((pageNo - 1) * pageSize, 0L, pageSize, new ArrayList());
			}
		}
		query = session.createSQLQuery(statements);
		if (!CommonUtils.isEmpty(params)) {
			for (int i = 0, l = params.length; i < l; i++) {
				query.setParameter(i, params[i]);
			}
		}
		pageNo = (pageNo < 0) ? 0 : pageNo;
		pageSize = (pageSize < 0) ? 0 : pageSize;
		if (pageNo > 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		resultList = query.list();
		return new Page((pageNo - 1) * pageSize, count, pageSize, resultList);
	}
	@Override
	public List<PrpLcompelMedical> findLastPrpLcompelMedical(String claimNo,String identifyNumber,String compensateNo)
			throws Exception {
		String sql = "select * from PrpLcompelMedical where compensateNo like ? and identifyNumber = ? and compensateNo != ?";
		List<PrpLcompelMedical> resutlt = HibernateUtils.executeQuery(PrpLcompelMedical.class, super.getSession(), sql, "C"+claimNo+"%", identifyNumber,compensateNo);
		return resutlt;
	}
	
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	@Override
	public String verifyPrpLcompelMedical(String identifyNumber,String compensateNo,Integer serialNo,Date startDate)
			throws Exception {
		String sql = "select * from PrpLcompelMedical where identifyNumber = ? and TO_CHAR(startDate,'yyyy-MM-dd') = ?";
		List<PrpLcompelMedical> resutlt = HibernateUtils.executeQuery(PrpLcompelMedical.class, super.getSession(), sql,
				identifyNumber,FastDateFormat.getInstance("yyyy-MM-dd").format(startDate));
		List<String> strings = new ArrayList<String>();
		for(PrpLcompelMedical m:resutlt){
			if(compensateNo.equalsIgnoreCase(m.getId().getCompensateNo())&&serialNo.equals(m.getId().getSerialNo())){
				continue;
			}
			strings.add(m.getId().getCompensateNo()+":"+m.getId().getSerialNo());
		}
		return org.apache.commons.lang.StringUtils.join(strings, '、'); 
	}
	@Override
	public String verifyPrpLcompelMedical(PrpLcompelMedical compelMedical)
			throws Exception {
		return verifyPrpLcompelMedical(compelMedical.getId().getIdentifyNumber(),compelMedical.getId().getCompensateNo(),compelMedical.getId().getSerialNo(),compelMedical.getStartDate()); 
	}
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
	 */
	

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}
	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}
	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}
	public PrpCengageService getPrpCengageService() {
		return prpCengageService;
	}
	public void setPrpCengageService(PrpCengageService prpCengageService) {
		this.prpCengageService = prpCengageService;
	}
	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}
	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}
	public PrpDlimitService getPrpDlimitService() {
		return prpDlimitService;
	}
	public void setPrpDlimitService(PrpDlimitService prpDlimitService) {
		this.prpDlimitService = prpDlimitService;
	}
	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}
	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}
	public PrpLchargeService getPrpLchargeService() {
		return prpLchargeService;
	}
	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
	}
	public PrpLctextService getPrpLctextService() {
		return prpLctextService;
	}
	public void setPrpLctextService(PrpLctextService prpLctextService) {
		this.prpLctextService = prpLctextService;
	}
	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}
	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}
	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}
	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}
	public PrpLcfeeService getPrpLcfeeService() {
		return prpLcfeeService;
	}
	public void setPrpLcfeeService(PrpLcfeeService prpLcfeeService) {
		this.prpLcfeeService = prpLcfeeService;
	}
	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}
	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}
	public PrpLdeductibleService getPrpLdeductibleService() {
		return prpLdeductibleService;
	}
	public void setPrpLdeductibleService(PrpLdeductibleService prpLdeductibleService) {
		this.prpLdeductibleService = prpLdeductibleService;
	}
	public PrpLqualityCheckService getPrpLqualityCheckService() {
		return prpLqualityCheckService;
	}
	public void setPrpLqualityCheckService(PrpLqualityCheckService prpLqualityCheckService) {
		this.prpLqualityCheckService = prpLqualityCheckService;
	}
	public PrpLltextService getPrpLltextService() {
		return prpLltextService;
	}
	public void setPrpLltextService(PrpLltextService prpLltextService) {
		this.prpLltextService = prpLltextService;
	}
	public PrpLacciPersonService getPrpLacciPersonService() {
		return prpLacciPersonService;
	}
	public void setPrpLacciPersonService(PrpLacciPersonService prpLacciPersonService) {
		this.prpLacciPersonService = prpLacciPersonService;
	}
	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}
	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
	}
	public PrpLdeductCondService getPrpLdeductCondService() {
		return prpLdeductCondService;
	}
	public void setPrpLdeductCondService(PrpLdeductCondService prpLdeductCondService) {
		this.prpLdeductCondService = prpLdeductCondService;
	}
	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}
	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}
	public PrpLDangerUnitService getPrpLDangerUnitService() {
		return prpLDangerUnitService;
	}
	public void setPrpLDangerUnitService(PrpLDangerUnitService prpLDangerUnitService) {
		this.prpLDangerUnitService = prpLDangerUnitService;
	}
	public PrpLDangerTotService getPrpLDangerTotService() {
		return prpLDangerTotService;
	}
	public void setPrpLDangerTotService(PrpLDangerTotService prpLDangerTotService) {
		this.prpLDangerTotService = prpLDangerTotService;
	}
	public PrpLDangerItemService getPrpLDangerItemService() {
		return prpLDangerItemService;
	}
	public void setPrpLDangerItemService(PrpLDangerItemService prpLDangerItemService) {
		this.prpLDangerItemService = prpLDangerItemService;
	}
	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}
	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}
	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}
	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	public SwfLogService getSwfLogService() {
		return swfLogService;
	}
	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}
	public WfLogService getWfLogService() {
		return wfLogService;
	}
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}
	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}
	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}
	public PrpLpersonHospitalService getPrpLpersonHospitalService() {
		return prpLpersonHospitalService;
	}
	public void setPrpLpersonHospitalService(PrpLpersonHospitalService prpLpersonHospitalService) {
		this.prpLpersonHospitalService = prpLpersonHospitalService;
	}
	public CodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public UtiUserGradeTaskService getUtiUserGradeTaskService() {
		return utiUserGradeTaskService;
	}
	public void setUtiUserGradeTaskService(UtiUserGradeTaskService utiUserGradeTaskService) {
		this.utiUserGradeTaskService = utiUserGradeTaskService;
	}
	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}
	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}
	public PrpLearthquakeFundService getPrpLearthquakeFundService() {
		return prpLearthquakeFundService;
	}
	public void setPrpLearthquakeFundService(PrpLearthquakeFundService prpLearthquakeFundService) {
		this.prpLearthquakeFundService = prpLearthquakeFundService;
	}
	public CertainLossService getCertainLossService() {
		return certainLossService;
	}
	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}
	public PrpLcarInsuranceService getPrpLcarInsuranceService() {
		return prpLcarInsuranceService;
	}
	public void setPrpLcarInsuranceService(PrpLcarInsuranceService prpLcarInsuranceService) {
		this.prpLcarInsuranceService = prpLcarInsuranceService;
	}

}
