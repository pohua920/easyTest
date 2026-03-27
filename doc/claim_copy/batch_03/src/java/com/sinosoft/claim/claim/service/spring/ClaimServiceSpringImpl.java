package com.sinosoft.claim.claim.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;

import com.sinosoft.claim.claim.service.facade.ClaimService;
//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.util.ReinsTranslateViewHelper;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemCarExt;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCopyCargoItem;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.model.PrpCopymainCarGoSub;
import com.sinosoft.claim.schema.model.PrpCopymainCargo;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimFee;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdoc;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLextId;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCCargoItemService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarExtService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCopyCargoItemService;
import com.sinosoft.claim.schema.service.facade.PrpCopyMainService;
import com.sinosoft.claim.schema.service.facade.PrpCopymainCarGoSubService;
import com.sinosoft.claim.schema.service.facade.PrpCopymainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimCreditService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimFeeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLdocService;
import com.sinosoft.claim.schema.service.facade.PrpLdriverService;
import com.sinosoft.claim.schema.service.facade.PrpLextService;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLquickCaseService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdCarLossService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPropService;
import com.sinosoft.claim.schema.service.facade.PrpallPolicyService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.prpall.pubfun.PubTools;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.utility.string.ChgDate;

/**
 * 立案接口实现类
 * @author 中科软
 *
 */
public class ClaimServiceSpringImpl extends GenericDaoHibernate<ClaimDto, String> implements ClaimService {

	private PrpLclaimService prpLclaimService;
	private PrplregistrpolicyService prpLregistrpolicyService;
	private PrpLthirdPartyService prpLthirdPartyService;
	private PrpLthirdCarLossService prpLthirdCarLossService;
	private PrpLthirdPropService prpLthirdPropService;
	private PrpLdriverService PrpLdriverService;
	private PrpLltextService prpLltextService;
	private PrpLclaimLossService prpLclaimLossService;
	private PrpLclaimFeeService prpLclaimFeeService;
	private PrpLdocService prpLdocService;
	private PrpLextService prpLextService;
	private PrpLregistExtService prpLregistExtService;
	private PrpLacciPersonService prpLacciPersonService;
	private PrpLclaimStatusService prpLclaimStatusService;
	private PrpLpersonTraceService prpLpersonTraceService;
	private PrpLquickCaseService prpLquickCaseService;
	private PrpallPolicyService prpallPolicyService;
	private PrpLprepayService prpLprepayService;
	private CodeService codeService;
	private PrpLregistService prpLregistService;
	private PrpLcompensateService prpLcompensateService;
	private CompensateService compensateService;
	private ClaimService claimService;
	private PolicyService policyService;
	private UtiCodeTransferService utiCodeTransferService;
	private PrpCmainService prpCmainService;
	private ReinsServiceManager reinsServiceManager;
	private WorkFlowService workFlowService;
	private PrpCitemCarExtService prpCitemCarExtService;
	private PrpLclaimCreditService prpLclaimCreditService;
	/** 保单Service*/
	private PrpCopyMainService prpCopyMainService;
	/** 货运险标的信息 */
	private PrpCCargoItemService prpCCargoItemService;
	/** 货运险标的信息 */
	private PrpCopyCargoItemService prpCopyCargoItemService;
	/** 货运险标的信息 */
	private PrpCopymainCargoService prpCopymainCargoService;
	/** 货运险标的信息 */
	private PrpCopymainCarGoSubService prpCopymainCarGoSubService;
	
	private PrpLcheckService prpLcheckService;
	private EndorseViewHelper endorseViewHelper;
	private PrpCitemKindService prpCitemKindService;
	
	/** mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 **/
	/** 立案viewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;
	
	public void save(ClaimDto claimDto) throws SQLException, Exception {

		if (claimDto.getPrpLclaim() == null) {
			throw new Exception();
		}
		String claimNo = "";
		String registNo = "";
		claimNo = claimDto.getPrpLclaim().getClaimNo();
		registNo = claimDto.getPrpLclaim().getRegistNo();
		if (claimDto.getPrpLltextList() != null && claimDto.getPrpLltextList().size() > 0 && (claimDto.getPrpLltextList().get(0).getId().getTextType().equals("10"))) {
			// 是注销拒赔保存，应该做cancelsave()
			saveCancel(claimDto);
			return;
		}
		if (claimDto.getAutoClaim()) {
			// 自动立案。。。
			saveCancel(claimDto);
			return;
		}
		// 首先删除原来的相关数据
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		deleteSubInfo(prpLclaim , registNo);
		prpLclaimService.saveOrUpdate(prpLclaim);
		if (!CommonUtils.isEmpty(prpLclaim.getDamageCode())) {
			//立案保存更新備案出險原因
			String classCode = this.codeService.translateClassCodeByRiskCode(prpLclaim.getRiskCode());
			String sql = null;
			if (ConstantCodes.CLASSCODE_D_B.equals(classCode)) {
				sql = "update PrpLregist set damageCodeBZ = ? , damageNameBZ = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLclaim.getDamageCode() , prpLclaim.getDamageName() , registNo);
				sql = "update PrpLcheck set damageCodeBZ = ? , damageNameBZ = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLclaim.getDamageCode() , prpLclaim.getDamageName() , registNo);
			} else {
				sql = "update PrpLregist set damageCode = ? , damageName = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLclaim.getDamageCode() , prpLclaim.getDamageName() , registNo);
				sql = "update PrpLcheck set damageCode = ? , damageName = ?  where registNo = ? ";
				HibernateUtils.executeSql(super.getSession(), sql , prpLclaim.getDamageCode() , prpLclaim.getDamageName() , registNo);
			}
			
		}
		if (claimDto.getPrplregistrpolicy() != null) {
			prpLregistrpolicyService.saveOrUpdate(claimDto.getPrplregistrpolicy());
		}

		if (claimDto.getPrpLthirdPartyList() != null) {
			prpLthirdPartyService.saveOrUpdate(claimDto.getPrpLthirdPartyList());
		}
		if (claimDto.getPrpLthirdCarLossList() != null) {
			this.prpLthirdCarLossService.saveOrUpdate(claimDto.getPrpLthirdCarLossList());
		}

		if (claimDto.getPrpLthirdPropList() != null) {
			this.prpLthirdPropService.saveOrUpdate(claimDto.getPrpLthirdPropList());
		}

		if (claimDto.getPrpLdriverList() != null) {
			this.PrpLdriverService.saveOrUpdate(claimDto.getPrpLdriverList());
		}
		if (claimDto.getPrpLltextList() != null) {
			this.prpLltextService.saveOrUpdate(claimDto.getPrpLltextList());
		}
		if (claimDto.getPrpLclaimLossList() != null) {
			this.prpLclaimLossService.saveOrUpdate(claimDto.getPrpLclaimLossList());
		}
		if (claimDto.getPrpLclaimFeeList() != null) {
			this.prpLclaimFeeService.saveOrUpdate(claimDto.getPrpLclaimFeeList());
		}
		if (claimDto.getPrpLdocList() != null) {
			this.prpLdocService.saveOrUpdate(claimDto.getPrpLdocList());
		}

		if (claimDto.getPrpLext() != null) {
			this.prpLextService.saveOrUpdate(claimDto.getPrpLext());
		}
		if (claimDto.getPrpLregistExtList() != null) {
			this.prpLregistExtService.saveOrUpdate(claimDto.getPrpLregistExtList());
		}
		if (claimDto.getPrpLacciPersonList() != null) {
			prpLacciPersonService.deleteByRegistNo(registNo, "1");
			prpLacciPersonService.saveOrUpdate(claimDto.getPrpLacciPersonList());
		}

		// 原因：添加出险人员信息
		if (claimDto.getPrpLacciPerson() != null) {
			this.prpLacciPersonService.saveOrUpdate(claimDto.getPrpLacciPerson());
		}

		// 如果为保存立案信息，则修改prpCmain表的claimtimes（理赔次数）字段的值。让它加1。
		if (claimDto.getPrpLclaimStatus().getStatus().equals("4")) {
			updateClaimTimes(claimDto.getPrpLclaim().getPolicyNo(), 1);
		}
		if (claimDto.getPrpLpersonTraceList() != null) {
			this.prpLpersonTraceService.saveOrUpdate(claimDto.getPrpLpersonTraceList());
		}
		if(claimDto.getPrpLclaimCredit() != null){
			prpLclaimCreditService.save(claimDto.getPrpLclaimCredit());
		}
		// 进行状态的改变
		updateClaimStatus(claimDto);
	}

	/**
	 * 修改出险次数
	 * @param policyNo 保单号码
	 * @param length 次数
	 * @throws Exception
	 */
	public void updateClaimTimes(String policyNo, int length) throws Exception {
		String sql = null;
		if (length >= 0) {
			sql = " update prpcmain set claimtimes=claimtimes+" + length + "  Where  PolicyNo = '" + policyNo + "'";
		} else {
			sql = " update prpcmain set claimtimes=claimtimes" + length + "  Where  PolicyNo = '" + policyNo + "'";
		}
		HibernateUtils.executeSql(super.getSession(), sql);
	}

	@Override
	public void saveCancel(ClaimDto claimDto) throws SQLException, Exception {
		if (claimDto.getPrpLclaim() == null) {
			throw new Exception();
		}

		String claimNo = claimDto.getPrpLclaim().getClaimNo();
		// 1，更改prplclaim表的caseType的位置的值,进行状态的改变
		// add by lixiang start 2007-07-19
		// reasion:判断简易赔案的申请注销拒赔，是否要自动生成
		// if (claimDto.getAutoClaim() ){
		// new DBPrpLclaim(dbManager).insert (claimDto.getPrpLclaimDto());
		// }else{
		// new DBPrpLclaim(dbManager).update(claimDto.getPrpLclaimDto());
		// }
		prpLclaimService.saveOrUpdate(claimDto.getPrpLclaim());
		// 2,增加拒赔和注销赔案的原因
		if (claimDto.getPrpLltextList() != null) {

			// String statement = " DELETE FROM prpLltext Where claimNo='"
			// +claimDto.getPrpLclaimDto().getClaimNo()+"' and texttype='10'";
			//		    
			// dbManager.executeUpdate(statement);
			// new
			// DBPrpLltext(dbManager).insertAll(claimDto.getPrpLltextDtoList());
			prpLltextService.deleteByclaimNo(claimNo, "10");
			prpLltextService.saveOrUpdate(claimDto.getPrpLltextList());
		}

		// 3,更新立案操作状态为已提交
		updateClaimStatus(claimDto);

		// add by lixiang at 20060623 start for 强三----start
		if (claimDto.getPrplregistrpolicy() != null) {
			// new
			// DBPrpLRegistRPolicy(dbManager).update(claimDto.getPrpLRegistRPolicyDto());
			// 放的save去保存，这个方法没有dbManager
			// new
			// DBPrpCmain(dbManager).updateClaimTimesMinus1(claimDto.getPrpLclaimDto().getPolicyNo());
			// // add by wzy 20090427 注销和拒赔案件，出现次数要减1
			updateClaimTimes(claimDto.getPrpLclaim().getPolicyNo(), -1);
			prpLregistrpolicyService.saveOrUpdate(claimDto.getPrplregistrpolicy());
		}
		// add by lixiang at 20060623 end for 强三----end

		// add by lixiang start at 2007-7-19
		// resion:判断如果是注销拒赔的通过这样的情况，需要把简易赔案相关的数据进行更新，並且做减1的操作。
		if (claimDto.getPrpLclaim().getCaseNo() != null && claimDto.getPrpLclaim().getCaseNo().length() > 1 && claimDto.getPrpLquickCase() != null) {
			// 删除这个立案下的计算书信息
			// new
			// DBCompensate().deleteByClaimNo(dbManager,claimDto.getPrpLclaimDto().getClaimNo()
			// );
			compensateService.deleteByClaimNo(claimNo);
			// 更新简易赔案信息
			// new
			// DBPrpLquickCase(dbManager).update(claimDto.getPrpLquickCaseDto()
			// );
			prpLquickCaseService.update(claimDto.getPrpLquickCase());
			// 更保单全貌表
			// 如果立案的时候涉及的保单案件，则把哪个案件的简易赔案次数-1
			String statement = " update PrpallPolicy A set qucikcasetimes =qucikcasetimes-1" + " Where  A.policyNo='" + claimDto.getPrpLclaim().getPolicyNo() + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
		}
	}

	@Override
	public void updateClaimStatus(ClaimDto claimDto) throws SQLException, Exception {
		if (claimDto.getPrpLclaimStatus() != null) {
			this.prpLclaimStatusService.saveOrMerge(claimDto.getPrpLclaimStatus());
		}
	}

	/**
	 * 按条件从prplclaim表,prplregist表和prplclaimstatus表中查询多条数据
	 * @param conditions String
	 * @param pageNo int
	 * @param pageSize int
	 * @throws Exception
	 * @return Collection Modify By sunhao 2004-08-24
	 *         Reason:增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String statement = "Select DISTINCT prplclaim.ClaimNo," + "prplclaim.RegistNo, " + "prplclaim.OperatorCode, " + "prplclaim.CaseType, " + "b.OperateDate," + "b.Status, " + "b.RiskCode, " + "prplregist.LicenseNo, " + "prplregist.reportDate,"
				+ "prplclaim.inputDate From (select * from PrpLClaimStatus where NodeType='claim') b LEFT JOIN prplclaim ON prplclaim.ClaimNo = b.BusinessNo LEFT JOIN prplregist ON prplclaim.registNo = prplregist.registNo where" + conditions
				+ " order by prplclaim.claimno";
		StringBuffer buffer = new StringBuffer(200);
		buffer.append(statement);
		List<PrpLclaim> resultList = new ArrayList<PrpLclaim>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, pageSize);
		PrpLclaim prpLclaim = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象 而是一个数组
			prpLclaim = new PrpLclaim();
			prpLclaim.setClaimNo((String) object[0]);
			prpLclaim.setRegistNo((String) object[1]);
			prpLclaim.setOperatorCode((String) object[2]);
			prpLclaim.setOperatorName(codeService.translateCode(ConstantCodes.CodeConfig.USERCODE, (String) object[2], ConstantCodes.Language.CHINESE));
			prpLclaim.setCaseType((String) object[3]);
			prpLclaim.setOperateDate(new Date(((Timestamp) object[4]).getTime()));
			prpLclaim.setStatus((String) object[5]);
			prpLclaim.setRiskCode((String) object[6]);
			prpLclaim.setReportDate(new Date(((Timestamp) object[8]).getTime()));
			prpLclaim.setInputDate(new Date(((Timestamp) object[9]).getTime()));
			resultList.add(prpLclaim);
		}
		return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(session, statement), pageSize, resultList);
	}

	@Override
	public void delete(String claimNo) throws SQLException, Exception {

	}

	/**
	 * 根据立案号查询
	 * @param claimNo 立案号码
	 * @return 立案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClaimDto findByPrimaryKey(String claimNo) throws SQLException, Exception {
		ClaimDto claimDto = new ClaimDto();
		
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		if (prpLclaim == null) {
			return null;
		}
		String registNo = prpLclaim.getRegistNo();
		
//		QueryRule queryRule5 = QueryRule.getInstance();
//		queryRule5.addEqual("id.certiNo", claimNo);
//		queryRule5.addIsNull("flag");
		
		QueryRule queryRule2 = QueryRule.getInstance();
		queryRule2.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		claimDto.setPrpLthirdCarLossList((ArrayList<PrpLthirdCarLoss>) this.prpLthirdCarLossService.findPrpLthirdCarLoss(queryRule2));
		claimDto.setPrpLthirdPropList((ArrayList<PrpLthirdProp>) this.prpLthirdPropService.findPrpLthirdProp(queryRule2));
		
		claimDto.setPrpLclaim(this.prpLclaimService.findPrpLclaim(claimNo));
		
		QueryRule queryRule3 = QueryRule.getInstance();
		queryRule3.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		queryRule3.addAscOrder("id.serialNo");
		claimDto.setPrpLthirdPartyList((ArrayList<PrpLthirdParty>) this.prpLthirdPartyService.findPrpLthirdParty(queryRule3));
		claimDto.setPrpLdriverList((ArrayList<PrpLdriver>) this.PrpLdriverService.findPrpLdriver(queryRule3));
		
		QueryRule queryRule4 = QueryRule.getInstance();
		queryRule4.addEqual("id.claimNo", StringUtils.rightTrim(claimNo));
		queryRule4.addEqual("id.textType", "09");
		queryRule4.addAscOrder("id.lineNo");
		claimDto.setPrpLltextList((ArrayList<PrpLltext>) this.prpLltextService.findPrpLltext(queryRule4));
		
		QueryRule claimNomutiRule = QueryRule.getInstance();
		claimNomutiRule.addEqual("id.claimNo", StringUtils.rightTrim(claimNo));
		claimDto.setPrpLdocList((ArrayList<PrpLdoc>) this.prpLdocService.findPrpLdoc(claimNomutiRule));
		claimDto.setPrpLclaimFeeList((ArrayList<PrpLclaimFee>) this.prpLclaimFeeService.findPrpLclaimFee(claimNomutiRule));
		claimDto.setPrpLclaimLossList(this.prpLclaimLossService.findPrpLclaimLoss(StringUtils.rightTrim(claimNo)));
		claimDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(claimNo, "claim", 0)));
		
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("claimNo", StringUtils.rightTrim(claimNo));
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", claimNo).addEqual("flag", "1");
		claimDto.setPrpLacciPersonList((ArrayList<PrpLacciPerson>) this.prpLacciPersonService.findPrpLacciPerson(queryRule));
		List<PrpLacciPerson> resultList = this.prpLacciPersonService.findPrpLacciPerson(QueryRule.getInstance().addSql("CertiNo = '"+claimNo+"' and Flag is null"));
		if(resultList!=null && !resultList.isEmpty()){
			claimDto.setPrpLacciPerson(resultList.get(0));
		}
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("policyNo", claimDto.getPrpLclaim().getPolicyNo());
		/** 待修改 */
		// claimDto.setPrpCengageList((ArrayList)new
		// DBPrpCengage(dbManager).findByConditions(conditions,0,0));
		claimDto.setPrpLregistExtList((ArrayList<PrpLregistExt>) this.prpLregistExtService.findPrpLregistExt(queryRule2));

		claimDto.setPrpLpersonTraceList((ArrayList<PrpLpersonTrace>) this.prpLpersonTraceService.findPrpLpersonTrace(queryRule2));
		claimDto.setPrpLext(this.prpLextService.findPrpLext(new PrpLextId(claimNo, "03")));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo);
		claimDto.setPrpLprepayList((ArrayList<PrpLprepay>) this.prpLprepayService.findPrpLprepay(queryRule));
		claimDto.setPrpLclaimCredit(this.prpLclaimCreditService.findPrpLclaimCredit(claimNo,"claim",1));
		return claimDto;
	}

	/**
	 * 保存立案带工作流
	 * @param ClaimDto：立案对象DTO
	 * @throws Exception
	 */
	public void save(ClaimDto claimDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// 业务操作
		String strCaseType = claimDto.getPrpLclaim().getCaseType();
		boolean caseTypeFlag = "1".equals(strCaseType) || "0".equals(strCaseType)||"3".equals(strCaseType);
		if (caseTypeFlag) {
			// 如果是立案拒赔或者注销用savecancel的方法
			this.saveCancel(claimDto);
			updateClaimTimes(claimDto.getPrpLclaim().getPolicyNo(), -1);
		} else { // 如果是新增加或者修改那么用insert
			this.save(claimDto);
			if (claimDto.getPrpLclaimStatus().getStatus().equals("4")) {
				updateClaimTimes(claimDto.getPrpLclaim().getPolicyNo(), 1);
			}
		}
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
			// 立案提交送再保分赔
			// 保险公司调整，由於再保不处理车险信息，车险理赔不需要与再保进行交互
			// if (!"D".equals(codeName)) {
			if (workFlowDto.getUpdateSwfLog() != null) {
				//申请注销拒赔不送再保
				if ("claim".equals(workFlowDto.getUpdateSwfLog().getNodeType()) && workFlowDto.getSubmit()) {
					String submitType = workFlowDto.getSubmitSwfLogList().size()>0?workFlowDto.getSubmitSwfLogList().get(0).getNodeType():"";
					if(!"cance".equals(submitType)){
						ReinsClaimMain reinsClaimMain = ReinsTranslateViewHelper.getClaimMainCollection(claimDto, workFlowDto);
						// 获取业务类型、渠道、车型、兑换率begin
						PrpCmain prpCmain = prpCmainService.findPrpCmain(claimDto.getPrpLclaim().getPolicyNo());
						List<PrpCitemCarExt> prpCitemCarExtDto = this.getPrpCitemCarExtService().findByPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
						String businessNature = prpCmain.getBusinessNature();//业务渠道
						String channelType = prpCmain.getChannelType();//渠道类型
						String cartypeCode = "";
						if (null != prpCitemCarExtDto && prpCitemCarExtDto.size() > 0) {
							cartypeCode = prpCitemCarExtDto.get(0).getCartypeCode();//车型
						}
						ChgDate thisDte = new ChgDate();
						double exchangeRate = PubTools.getExchangeRate(prpCmain.getCurrency(), ConstantCodes.LOCAL_CURRENCY, thisDte.getCurrentTime("yyyy-MM-dd"));
						reinsClaimMain.setBusinessNature(businessNature);
						reinsClaimMain.setExchangeRate(exchangeRate);
						reinsClaimMain.setChannelType(channelType);
						reinsClaimMain.setCartypeCode(cartypeCode);
						// 获取业务类型、渠道、车型、兑换率end
						reinsServiceManager.getReinsService().repayCal(reinsClaimMain);
					}
				}
				if ("cance".equals(workFlowDto.getUpdateSwfLog().getNodeType())) {
					ReinsCaseStatus reinsCaseStatus = ReinsTranslateViewHelper.getReinsCaseStatus(claimDto);
					reinsServiceManager.getReinsService().changeCaseStatus(reinsCaseStatus);
				}
			}
		}
		//mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏
		if (!caseTypeFlag && workFlowDto.getSubmit()) {
			// 簡易賠案，且非關聯備案時，要註銷分案節點工作流任務
			PrpLclaim prpLclaim = claimDto.getPrpLclaim();
			if ("1".equals(prpLclaim.getSimpleFlag())) {
//				SwfLog currSwfLog = workFlowDto.getCurrSwfLog();
//				String flowID = currSwfLog.getId().getFlowID();
			    List<SwfLog> swfLogList = workFlowService.findByConditions("businessNo='"+prpLclaim.getRegistNo() +"' ");
			    String flowID = swfLogList.get(0).getId().getFlowID();
				PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(prpLclaim.getRegistNo());
				String registType = prpLregist.getRegistType();
				String sql = " update SwfLog t set nodeStatus = '6' where flowID = '" + flowID + "' and nodeType = 'sched' and nodeStatus = '0' ";
				if("2".equals(registType)){//關聯報案均
					sql += " and exists ( ";
					sql += " select 0 from prplclaim where registno = t.registno and policyno <>'"+prpLclaim.getPolicyNo()+"' and simpleflag = '1' ";
					sql += " ) ";
				}
				HibernateUtils.executeSql(super.getSession(), sql);
			}
		}
	}

	/**
	 * 保存立案带工作流
	 * @param ClaimDto：立案对象DTO
	 * @throws Exception
	 */
	@ProcessTask(processId = "claim_05",userId = "claim", businessBeanOffset = 0, businessIdAttributeName = "prpLclaim.registNo")
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 2) })
	public void saveBpm(ClaimDto claimDto, WorkFlowDto workFlowDto,String nodeType) throws SQLException, Exception {
		this.save(claimDto, workFlowDto);
	}

	/**
	 * 1，判断是否是关联单的立案，
	 * 2，判断这个报案号是否已经立案
	 * @param registNo 报案号码
	 * @return
	 * @throws Exception
	 */
	public String findClaimJbpmNodeType(String registNo)throws Exception{
		String nodeType = "certi";
		boolean flag = prpLregistrpolicyService.isCompelFlag(registNo);
		if (flag) {
			String sql = "select count(1) from prpLclaimStatus where businessNo in (select claimNo from prplclaim where registno='"+registNo+"') and nodetype='claim' and status='4'";
			Long sum = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
			if (sum <1) {
				nodeType = "claim";
			}
		}
		return nodeType;
	}
	/**
	 * 保存注销拒赔的信息带工作流
	 * @param ClaimDto：立案对象DTO
	 * @throws Exception
	 */
	@ProcessTask(processId = "claim_05",userId = "cance", businessBeanOffset = 0)
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 1) })
	public void saveCancelBpm(String businessNo, String nodeType, ClaimDto claimDto, WorkFlowDto workFlowDto, String swfLogFlowID, String swfLogLogNo) throws SQLException, Exception {
		if ("end".equals(nodeType)||"related_cance".equals(nodeType)) {
			this.save(claimDto, workFlowDto);
		} else if ("cance".equals(nodeType)) {
			this.getWorkFlowService().cancelBack(swfLogFlowID, Integer.parseInt(swfLogLogNo), null);
		}
	}
	/**
	 * 1，判断是否是关联单的注销拒赔，2，如果是关联单的注销拒赔，判断是否是最后一个立案的注销
	 * @param claimNo 立案号码
	 * @return 返回节点号码
	 * @throws Exception
	 */
	public String findJbpmNodeType(String claimNo)throws Exception{
		String nodeType = "end";
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		boolean flag = prpLregistrpolicyService.isCompelFlag(prpLclaim.getRegistNo());
		if (flag) {
			String sql = "select count(1) from  prpLclaim where registNo ='" + prpLclaim.getRegistNo() + "' " + " AND CANCELDATE IS NOT NULL  AND CLAIMDATE IS NOT NULL AND ENDCASEDATE IS NOT  NULL AND CASENO IS NOT  NULL";
			Long sum = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
			if (sum==0) {
				nodeType = "related_cance";
			}
		}
		return nodeType;
	}

	/**
	 * 注销拒赔的申请 注销拒赔的信息带工作流
	 * @param ClaimDto：立案对象DTO
	 * @throws Exception
	 */
	@ProcessTask(processId = "claim_05",userId = "request_cancel", businessBeanOffset = 0)
	public void saveRequestCancelBpm(String businessNo, ClaimDto claimDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(claimDto, workFlowDto);
	}

	@Override
	public void UpdCaseType(String claimNo) throws Exception {

	}

	@Override
	public void UpdSumClaim(ClaimDto claimDto, String claimNo, double sumClaim) throws Exception {

	}
	/**
	 * 添加一个方法同时获得立案信息和报案信息
	 * @param policyNo 保单号码
	 * @return
	 * @throws Exception
	 */
	public List<RegistClaimInfoDto> findByPolicyNo(String policyNo) throws Exception {
		List<RegistClaimInfoDto> registClaimDtoList = new ArrayList<RegistClaimInfoDto>();
		List<PrpLregist> prpLregistList = prpLregistService.findByConditions(" policyNo= '" + policyNo + "' order by registNo");
		List<PrpLclaim> prpLclaimList = null;
		List<PrpLcompensate> prpLcompensateList = null;
		List<PrpLthirdCarLoss> prpLthirdCarLossList = null;
		PrpLregist prpLregist = null;
		RegistClaimInfoDto registClaimInfoDto = null;
		PrpLthirdCarLoss prpLthirdCar = null;
		PrpLcompensate prpLcompensate = null;
		for (int i = 0; i < prpLregistList.size(); i++) {
			prpLregist = prpLregistList.get(i);
			registClaimInfoDto = new RegistClaimInfoDto();
			registClaimInfoDto.setSerialNo(prpLregist.getSerialNo());
			registClaimInfoDto.setRegistNo(prpLregist.getRegistNo());
			registClaimInfoDto.setDamageStartDate(prpLregist.getDamageStartDate());
			registClaimInfoDto.setLinkerName(prpLregist.getLinkerName());
			registClaimInfoDto.setOperatorCode(prpLregist.getOperatorCode());
			registClaimInfoDto.setOperatorName(prpLregist.getOperatorName());
			registClaimInfoDto.setDamageAddress(prpLregist.getDamageAddress());
			registClaimInfoDto.setBrandName(prpLregist.getBrandName());
			registClaimInfoDto.setRegistNo(prpLregist.getRegistNo());
			registClaimInfoDto.setPhoneNumber(prpLregist.getPhoneNumber());
			registClaimInfoDto.setDamageName(prpLregist.getDamageName());
			registClaimInfoDto.setDamageName(prpLregist.getDamageName());
			registClaimInfoDto.setDamageAreaName(prpLregist.getDamageAddress());
			prpLclaimList = new ArrayList<PrpLclaim>();
			prpLcompensateList = new ArrayList<PrpLcompensate>();
			prpLthirdCarLossList = new ArrayList<PrpLthirdCarLoss>();
			double sumPaidShow = 0D;
			try {
				prpLclaimList = prpLclaimService.findByRegistNo(prpLregist.getRegistNo());
				prpLthirdCarLossList = prpLthirdCarLossService.findByRegistNo(prpLregist.getRegistNo());
			} catch (Exception e) {
				prpLclaimList = null;
			}

			if (prpLthirdCarLossList.size() < 1) {
				registClaimInfoDto.setCompName("");
			} else {
				prpLthirdCar = prpLthirdCarLossList.get(0);
				registClaimInfoDto.setCompName(prpLthirdCar.getCompName());
			}
			if (prpLclaimList == null) {
				prpLclaimList = new ArrayList<PrpLclaim>();
			}
			if (prpLclaimList.size() < 1) {
				registClaimInfoDto.setClaimNo("");
				registClaimInfoDto.setSumClaim(0);
				registClaimInfoDto.setSumPaidShow(0);
				registClaimInfoDto.setStatus("未結案");
				// reasion:增加一个报案注销的判断，如果是注销的情况，就不在已出险次数计算之列了。
				if (prpLregist.getCancelDate() != null && !"".equals(prpLregist.getCancelDate().toString())) {
					registClaimInfoDto.setStatus("已註銷");
				}
			} else {
				PrpLclaim prpLclaim = prpLclaimList.get(0);
				prpLcompensateList = prpLcompensateService.findByClaimNo(prpLclaim.getClaimNo());
				for (int j = 0; j < prpLcompensateList.size(); j++) {
					prpLcompensate = prpLcompensateList.get(j);
					sumPaidShow = sumPaidShow + prpLcompensate.getSumPaid();
				}
				registClaimInfoDto.setClaimNo(prpLclaim.getClaimNo());
				registClaimInfoDto.setSumClaim(prpLclaim.getSumClaim());
				registClaimInfoDto.setSumPaidShow(sumPaidShow);
				// DateTime对象不为null，还需要对equals("")的判断
				if (prpLclaim.getEndCaseDate() != null && !prpLclaim.getEndCaseDate().toString().equals("")) {
					registClaimInfoDto.setStatus("已結案");
				} else {
					registClaimInfoDto.setStatus("未結案");
				}
			}
			registClaimDtoList.add(registClaimInfoDto);
		}
		return registClaimDtoList;
	}

	/**
	 * 修改结束日期
	 * @param claimNo 立案号码
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateEndCaseDate(String claimNo) throws SQLException, Exception {

	}

	/**
	 * 立案删除子表信息
	 * @param fcoClaimNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(PrpLclaim prpLclaim, String registNo) throws SQLException, Exception {
		// String condition1 = " claimNo = " + "'" +
		// StringUtils.rightTrim(claimNo) + "'";
		// String condition2 = " registNo = "+ "'" +
		// StringUtils.rightTrim(registNo) + "'";
		// 示例未完成
		// String statement = "";
		// statement = " DELETE FROM prpLlText Where " + condition1;
		// dbManager.executeUpdate(statement);
		String claimNo = prpLclaim.getClaimNo();
		prpLltextService.deleteByclaimNo(claimNo);
		// statement = " DELETE FROM prpLdriver Where " + condition2;
		// dbManager.executeUpdate(statement);
		PrpLdriverService.deleteByRegistNo(registNo);
		// statement =" DELETE FROM prpLthirdParty Where " + condition2;
		// dbManager.executeUpdate(statement);
		prpLthirdPartyService.deleteByRegistNo(registNo);
		// statement = " DELETE FROM PrpLthirdCarLoss Where " + condition2;
		// dbManager.executeUpdate(statement);
		prpLthirdCarLossService.deleteByRegistNo(registNo);
		// statement = " DELETE FROM PrpLthirdProp Where " + condition2;
		// dbManager.executeUpdate(statement);
		
		// statement = " DELETE FROM prpLdoc Where " + condition1;
		// dbManager.executeUpdate(statement);
		prpLdocService.deleteByClaimNo(claimNo);
		// statement = " DELETE FROM prpLclaimFee Where " + condition1;
		// dbManager.executeUpdate(statement);
		prpLclaimFeeService.deleteByClaimNo(claimNo);
		// statement = " DELETE FROM prpLclaimLoss Where " + condition1;
		// dbManager.executeUpdate(statement);
		prpLclaimLossService.deleteByClaimNo(claimNo);
		// 立案环节增加理赔联系记录，先删後插。 2005-07-18
		// statement = " DELETE FROM PrpLregistExt Where " + condition2;
		// dbManager.executeUpdate(statement);
		prpLregistExtService.deleteByRegistNo(registNo);
		// statement = " DELETE FROM prpLclaim Where " + condition1;
		// dbManager.executeUpdate(statement);
		prpLclaimService.delete(claimNo);
		// 立案环节增加人伤跟踪信息，先删後插。 2005-07-18
		// statement = " DELETE FROM prpLpersonTrace Where " + condition2;
		// dbManager.executeUpdate(statement);
		
		prpLclaimCreditService.delete(claimNo,"claim");
		String strRiskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		if("Q".equals(strRiskType)){
			HibernateUtils.executeSql(super.getSession(), "delete from PrpLclaimLoss where registNo='"+registNo+"' and riskCode ='"+prpLclaim.getRiskCode()+"'");
		}else{
			//火险立案的时候没有添加财产和人伤信息，不删除
			prpLpersonTraceService.deleteByRegistNo(registNo);
			prpLthirdPropService.deleteByRegistNo(registNo);
		}
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception {
		int count = -1;
		String sql = "SELECT count(1) FROM PrpLclaim WHERE " + conditions;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		count = (int) HibernateUtils.getCountbyCountSql(session, sql);
		return count;
	}

	/**
	 * 查询调整估损金额信息
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws Exception
	 */
	public Page findClaimInforByCondition(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null || pageNo.trim().equals("")) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = Integer.parseInt(pageNo);
		HttpSession session = request.getSession();
		UserDto user = (UserDto) (session.getAttribute("user"));
		String comCode = user.getComCode();
		comCode = comCode.substring(0, 2);
		String claimNo = request.getParameter("ClaimNo");
		String claimNoSign = request.getParameter("ClaimNoSign");
		String policyNo = request.getParameter("PolicyNo");
		String policyNoSign = request.getParameter("PolicyNoSign");
		String insuredName = com.sinosoft.claim.common.util.StringConvert.getParam(request,"InsuredName",ConstantCodes.YUI_CHARSET);
		String insuredNameSign = request.getParameter("InsuredNameSign");
		String riskCode = request.getParameter("RiskCode");
		String riskCodeSign = request.getParameter("RiskCodeSign");
		String registNo = request.getParameter("RegistNo");
		String registNoSign = request.getParameter("RegistNoSign");
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("claimNo", claimNo, claimNoSign);
		conditions = conditions + StringConvert.convertString("policyNo", policyNo, policyNoSign);
		conditions = conditions + StringConvert.convertString("insuredName", insuredName, insuredNameSign);
		conditions = conditions + StringConvert.convertString("riskCode", riskCode, riskCodeSign);
		conditions = conditions + StringConvert.convertString("registNo", registNo, registNoSign);
		
		//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)START
		if(request.getAttribute("userCode") != null){
			conditions = conditions + StringConvert.convertString("handlercode", (String)request.getAttribute("userCode"), "=");
		}
		//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)END
		
		// 结案或注销不准许修改估损信息
		conditions = conditions + " and endcasedate is null and canceldate is null";
		if (!comCode.equals(ConstantCodes.MAINCOMPANYCOMCODE)) {
			//conditions = conditions + " and comCode like '" + comCode + "%'";
			//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)START
//			conditions = conditions + " and comCode in (select ComCode from prpdCompany Start With ComCode = '" + comCode + "' Connect By Prior comCode = uppercomCode and prior comcode != comcode and validstatus = '1')";
			conditions = conditions + " and makeCom in (select ComCode from prpdCompany Start With ComCode = '" + comCode + "' Connect By Prior comCode = uppercomCode and prior comcode != comcode and validstatus = '1')";
		}
		conditions = conditions + " order by inputdate desc";
		Page page = prpLclaimService.findByConditions(conditions, intPageNo, intRecordPerPage);
		//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 START
		List<PrpLclaim> page2 = (List<PrpLclaim>)page.getResult();
		for(PrpLclaim pplc:page2){
//			pplc.setRemark("1");
			String conditionsForPrpLregist = " REGISTNO = '"+pplc.getRegistNo()+"' AND NODETYPE = 'claim'";
			List<SwfLog> swfLog = workFlowService.findByConditions(conditionsForPrpLregist);
			if(null!=swfLog && swfLog.size()>0){
				for(SwfLog sl :swfLog){
					pplc.setRemark(sl.getId().getLogNo()+"");
				}
			}
		}

		return new Page(0, page.getTotalCount(), page.getPageSize(), page2);
		//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 END
	}

	/**
	 * 查询立案估损详细信息
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws Exception
	 */
	public void findDetailByClaimNo(HttpServletRequest request, HttpServletResponse response) throws UserException, Exception {
		String claimNo = request.getParameter("claimNo");
		if (claimNo == null) {
			throw new UserException(1, 2, "估損金額調整", "查詢立案估損詳細訊息錯誤,賠案不存在!");
		}
		// 未立案不允许调整估损金额
		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
		prpLclaimStatusId.setBusinessNo(claimNo);
		prpLclaimStatusId.setNodeType("claim");
		prpLclaimStatusId.setSerialNo(0);
		PrpLclaimStatus prpLclaimStatus = prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId);
		if (prpLclaimStatus != null) {
			if (!"4".equals(prpLclaimStatus.getStatus())) {
				throw new UserException(1,2,"估損金額調整","立案未提交不允許調整估損金額！");
			}
		}
		// 存在未核賠通過的理算時，不得調整預估。
		StringBuilder sql = new StringBuilder();
		sql.append("select count(0) from prplcompensate t where claimno='").append(claimNo).append("'");
		sql.append(" and ").append("( underwriteflag = '1' or underwriteflag = '3' )");
		sql.append(" and ").append("compensateno like 'C%'");
		sql.append(" and ").append("exists (select 0 from swflog s where s.nodetype='veric' and s.businessno = t.compensateno and s.nodestatus in ('0','2'))");
		Long count = HibernateUtils.getCountbyCountSql(super.getSession(), sql.toString());
		if (count > 0) {
			throw new UserException(1, 2, "估損金額調整", "理算已提交不允許調整估損金額");
		}
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		
		//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
		daaClaimViewHelper.claimDtoToView(request, claimNo);
		// 将签单币别放进对象
		claimDto.getPrpLclaim().setCurrencyName(codeService.translateCurrencyCode(claimDto.getPrpLclaim().getCurrency(), true));
		PrpLclaimLoss prpLclaimLoss = null;
		List<PrpLclaimLoss> claimLossList = claimDto.getPrpLclaimLossList();
		String handlerName = null;
		for(int i=0;i<claimLossList.size();i++){
			prpLclaimLoss = claimLossList.get(i);
			// 查询险别和币别中文名称
			String kindCode = prpLclaimLoss.getKindCode();
			String kindName = codeService.translateKindCode(prpLclaimLoss.getRiskCode(), kindCode, true);
			prpLclaimLoss.setKindName(kindName);
			String kindCodeSub = prpLclaimLoss.getKindCodeSub();
			if (kindCodeSub != null && !"".equals(kindCodeSub)) {
				String kindNameSub = codeService.translateKindCode(prpLclaimLoss.getRiskCode(), kindCodeSub, true);
				prpLclaimLoss.setKindNameSub(kindNameSub);
			}
			String currencyCode = prpLclaimLoss.getCurrency();
			String currencyName = codeService.translateCurrencyCode(currencyCode, true);
			prpLclaimLoss.setCurrencyName(currencyName);
			if(!CommonUtils.isEmpty(prpLclaimLoss.getHandlerCode())){
				handlerName = codeService.translateUserCode(prpLclaimLoss.getHandlerCode(), true);
				prpLclaimLoss.setHandlerName(handlerName);
			}
		}
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(claimDto.getPrpLclaim().getPolicyNo(), new DateTime(claimDto.getPrpLclaim().getDamageStartDate()));
		request.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);
		claimDto.setPrpLclaimLossList(claimLossList);
		request.setAttribute("claimDto", claimDto);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLclaim.setDamageStartHour(timeTemp.substring(0, 2));
		request.setAttribute("prpLclaim", prpLclaim);
		//估损金额调整
		request.setAttribute("lossLossFeeTypeList", ConstantsCollection.lossLossFeeTypeList);
		//範圍
		if (ConstantCodes.CLASSCODE_D_B.equals(prpLclaim.getClassCode())) {//强制险单独处理。
			request.setAttribute("lossFeeCategoryList", ConstantsCollection.lossFeeCategoryListBZ);
		} else {
			request.setAttribute("lossFeeCategoryList", ConstantsCollection.lossFeeCategoryList);
		}
		request.setAttribute("accidentTypeList", ConstantsCollection.accidentTypeList);
		// 设置险种类别
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			String insuredCode = prpLclaim.getInsuredCode();
			String insuredName = prpLclaim.getInsuredName();
			List<PrpCinsured> prpCinsuredList = this.getEndorseViewHelper().findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
			PrpCinsured prpCinsured = this.getEndorseViewHelper().getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			request.setAttribute("familyno", prpCinsured.getId().getSerialNo());
		}
		request.setAttribute("RiskType", riskType);
		request.setAttribute("RISKCODE_DAZ",com.sinosoft.claim.common.ConstantCodes.RISKCODE_DAZ);
	}

	/**
	 * 保存估损金额信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveClaimLoss(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*---------------------险别估损金额PrpLclaimloss------------------------------------*/
		List<PrpLclaimLoss> claimLossList = new ArrayList<PrpLclaimLoss>();
		PrpLclaimLoss prpLclaimLoss = null;
		// 从界面得到输入数组
		String prpLclaimLossClaimNo = request.getParameter("prpLclaimClaimNo");
		String prpLclaimLossRiskCode = request.getParameter("prpLclaimRiskCode");
		String prpLclaimRegistNo = request.getParameter("prpLclaimRegistNo");
		
		String[] prpLclaimLossKindCode = request.getParameterValues("prpLclaimLossKindCode");
		String[] prpLclaimLossItemCode = request.getParameterValues("prpLclaimLossItemCode");
		String[] prpLclaimLossCurrency = request.getParameterValues("prpLclaimLossCurrency");
		String[] prpLclaimLossKindLoss = request.getParameterValues("prpLclaimLossKindLoss");
		String[] prpLclaimLossSumClaim = request.getParameterValues("prpLclaimLossSumClaim");
		String[] prpLclaimLossInputDate = request.getParameterValues("prpLclaimLossInputDate");
		String[] prpLclaimLossRemarkFlag = request.getParameterValues("prpLclaimLossRemarkFlag");
		String[] prpLclaimLossFeeCategory = request.getParameterValues("prpLclaimLossFeeCategory");
		String[] prpLregsitLossFeeType = request.getParameterValues("prpLclaimLossLossFeeType");
		String[] prpLclaimLossDangerNo = request.getParameterValues("prpLclaimLossDangerNo");
		String[] prpLclaimLossKindRest = request.getParameterValues("prpLclaimLossKindRest");
		String[] prpLclaimLossItemName = request.getParameterValues("prpLclaimLossItemName");
		String[] prpLclaimLossItemKindNo = request.getParameterValues("prpLclaimLossItemKindNo");
		if(prpLclaimLossItemKindNo == null ){
			prpLclaimLossItemKindNo = new String[prpLclaimLossKindCode.length];
		}
		// 责任免赔额
		String[] prpLclaimLossAcciDeductiblePay = request.getParameterValues("prpLclaimLossAcciDeductiblePay");
		// 责任免赔率
		String[] prpLclaimLossAcciDeductibleRate = request.getParameterValues("prpLclaimLossAcciDeductibleRate");
		// 车险不计免赔额特约(M)对应的险别
		String[] exceptDeductibleKindCode = request.getParameterValues("exceptDeductibleKindCode");
		// 不计免赔额
		String[] exceptDeductiblePay = request.getParameterValues("exceptDeductiblePay");
		// 不计免赔率
		String[] exceptDeductibleRate = request.getParameterValues("exceptDeductibleRate");
		String[] prpLclaimLossAmount = request.getParameterValues("prpLclaimLossAmount");
		String[] prpLclaimLossHandlerCode = request.getParameterValues("prpLclaimLossHandlerCode");
		String[] prpLclaimLossAccidentType = request.getParameterValues("prpLclaimLossAccidentType");
		/*增加估損訊息來源*/
		String[] prpLclaimLossDatafrom = request.getParameterValues("prpLclaimLossDatafrom");
		if(prpLclaimLossAmount==null){
			prpLclaimLossAmount =  new String[prpLclaimLossKindCode.length];
		}
		
		/*需求變更#83，歷史估损调整記錄不做删除，不需要重新组织数据插入，因为需要保留每次估損調整的時間點*/
		List<PrpLclaimLoss> prePrpLclaimLoss = this.prpLclaimLossService.findPrpLclaimLoss(prpLclaimLossClaimNo);
		Map<String, PrpLclaimLoss> preClaimLossMap = new HashMap<String, PrpLclaimLoss>();
		for(PrpLclaimLoss p : prePrpLclaimLoss){
			preClaimLossMap.put(String.valueOf(p.getId().getSerialNo()), p);
		}
		// 立案估损金额表中存入的itemkindNo
		// 应为prpcitemKind的险别序号---[1]--------------------
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLclaimLossClaimNo);
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		List<PrpCitemKind> prpCitemKindList = null;
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		Map<String, PrpCitemKind> itemKindNoMap = new HashMap<String, PrpCitemKind>();
		Map<String, PrpCitemKind> itemKindMap = new HashMap<String, PrpCitemKind>();// 險別序號映射
		Map<String, PrpCitemKind> virtualKindMap = new HashMap<String, PrpCitemKind>();
		// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -start
		PrpCitemKind tempPrpCitemKind = null;
		for (PrpCitemKind p : prpCitemKindList) {
			tempPrpCitemKind = new PrpCitemKind();
			PropertyUtils.copyProperties(tempPrpCitemKind, p);
			itemKindNoMap.put(String.valueOf(tempPrpCitemKind.getId().getItemKindNo()), tempPrpCitemKind);
			itemKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);
			if (ConstantCodes.CLASSCODE_Z.equals(riskType)||ConstantCodes.CLASSCODE_G.equals(riskType)||ConstantCodes.CLASSCODE_Q.equals(riskType)) {
				// 虛擬標的情況處理
				List<PrpCitemKind> virtualKindList = prpCitemKindService.generateVirtualKind(tempPrpCitemKind);
				if (!CommonUtils.isEmpty(virtualKindList)) {
					virtualKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
					virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
					double sumAmount = 0d;
					for (PrpCitemKind sp : virtualKindList) {
						virtualKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
						virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
						sumAmount += sp.getAmount();
						tempPrpCitemKind.setAmount(sumAmount);
					}
				}
			}
		}
		// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -end
		PrpCitemKind temPrpCitemKind = null;
		for (int m = 0; m < prpLclaimLossKindCode.length; m++) {
			String kindCode = prpLclaimLossKindCode[m];
			if (!CommonUtils.isEmpty(kindCode)) {
				String intemKindNo = CommonUtils.getValue(prpLclaimLossItemKindNo, m);
				String itemCode = CommonUtils.getValue(prpLclaimLossItemCode, m);
				if (virtualKindMap.containsKey(kindCode)) {//虛擬標的
					if(CommonUtils.isEmpty(intemKindNo)){
						if (CommonUtils.isEmpty(itemCode)) {
							temPrpCitemKind = virtualKindMap.get(kindCode);
						} else {
							temPrpCitemKind = virtualKindMap.get(kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
						}
					} else {
						if (CommonUtils.isEmpty(itemCode)) {
							temPrpCitemKind = virtualKindMap.get(intemKindNo + "_" + kindCode);
						} else {
							temPrpCitemKind = virtualKindMap.get(intemKindNo + "_" + kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
						}
					}
				} else {
					if (CommonUtils.isEmpty(intemKindNo)) {
						temPrpCitemKind = itemKindMap.get(kindCode);
					} else {
						temPrpCitemKind = itemKindNoMap.get(intemKindNo);
						if (temPrpCitemKind == null || !temPrpCitemKind.getKindCode().equals(kindCode)) {
							temPrpCitemKind = itemKindMap.get(kindCode);
						}
					}
				}
				prpLclaimLossItemKindNo[m] = temPrpCitemKind.getId().getItemKindNo() + "";
				prpLclaimLossAmount[m] = String.valueOf(temPrpCitemKind.getAmount());
			}
		}
		if (prpLclaimLossCurrency != null) {
			String tempkey = null;
			for (int index = 1; index < prpLclaimLossCurrency.length; index++) {
				tempkey = String.valueOf(index);
				if(preClaimLossMap.containsKey(tempkey)){
					/*需求變更#83，歷史估损调整記錄不做删除，不需要重新组织数据插入，因为需要保留每次估損調整的時間點*/
					claimLossList.add(preClaimLossMap.get(tempkey));
					continue;
				}
				prpLclaimLoss = new PrpLclaimLoss();
				prpLclaimLoss.getId().setClaimNo(prpLclaimLossClaimNo);
				prpLclaimLoss.setRiskCode(prpLclaimLossRiskCode);
				prpLclaimLoss.getId().setSerialNo(index);
				prpLclaimLoss.setItemKindNo(Integer.parseInt(prpLclaimLossItemKindNo[index]));
				prpLclaimLoss.setKindCode(prpLclaimLossKindCode[index]);
				prpLclaimLoss.setItemCode(prpLclaimLossItemCode[index]);
				if (prpLclaimLossItemName != null) {
					prpLclaimLoss.setItemDetailName(DataUtils.dbNullToEmpty(prpLclaimLossItemName[index]));
				}
				if ("".equals(prpLclaimLossKindRest[index])) {
					prpLclaimLossKindRest[index] = "0";
				}
				prpLclaimLoss.setKindRest(Double.parseDouble(prpLclaimLossKindRest[index]));
				prpLclaimLoss.setCurrency(prpLclaimLossCurrency[index]);
				if (prpLclaimLossKindLoss != null && prpLclaimLossKindLoss.length > 0) {
					prpLclaimLoss.setKindLoss(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossKindLoss[index])));
				} else {
					prpLclaimLoss.setKindLoss(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossSumClaim[index])));
				}
				prpLclaimLoss.setSumClaim(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossSumClaim[index])));
				prpLclaimLoss.setInputDate(new DateTime(prpLclaimLossInputDate[index]));
				prpLclaimLoss.setRemarkFlag(prpLclaimLossRemarkFlag[index]);
				prpLclaimLoss.setDangerNo(Integer.parseInt(prpLclaimLossDangerNo[index]));
				if (prpLclaimLossFeeCategory != null) {
					prpLclaimLoss.setFeeCategory(prpLclaimLossFeeCategory[index]);
				}
				if (prpLregsitLossFeeType != null) {
					prpLclaimLoss.setLossFeeType(prpLregsitLossFeeType[index]);
				}
				if (prpLclaimLossAcciDeductiblePay != null && prpLclaimLossAcciDeductiblePay.length > 0) {
					prpLclaimLoss.setAcciDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossAcciDeductiblePay[index])));
				}
				if (prpLclaimLossAcciDeductibleRate != null && prpLclaimLossAcciDeductibleRate.length > 0) {
					prpLclaimLoss.setAcciDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossAcciDeductibleRate[index])));
				}
				prpLclaimLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossAmount, index))));
				prpLclaimLoss.setHandlerCode(CommonUtils.getValue(prpLclaimLossHandlerCode, index));
				prpLclaimLoss.setRegistNo(prpLclaimRegistNo);
//				prpLclaimLoss.setAccidentType(CommonUtils.getValue(prpLclaimLossAccidentType, index));// delete by chenjie 20150601 需求變更-095 
				prpLclaimLoss.setDeductible(0D);
				prpLclaimLoss.setDatafrom(prpLclaimLossDatafrom !=null && prpLclaimLossDatafrom.length > 0 ? prpLclaimLossDatafrom[index] : "2");
				claimLossList.add(prpLclaimLoss);
			}
			/*需求變更#83 第二次調整 移除*/
//			if (exceptDeductibleKindCode != null) {
//				for (int index = 1; index < exceptDeductibleKindCode.length; index++) {
//					prpLclaimLoss = new PrpLclaimLoss();
//					prpLclaimLoss.getId().setClaimNo(prpLclaimLossClaimNo);
//					prpLclaimLoss.setRiskCode(prpLclaimLossRiskCode);
//					prpLclaimLoss.getId().setSerialNo(index - 1 + prpLclaimLossCurrency.length);
//					for (Iterator<PrpCitemKind> iterator = prpCitemKindList.iterator(); iterator.hasNext();) {
//						prpCitemKind = iterator.next();
//						if ("M".equals(prpCitemKind.getKindCode().trim())) {
//							prpLclaimLoss.setItemKindNo(prpCitemKind.getId().getItemKindNo());
//							prpLclaimLoss.setAmount(prpCitemKind.getAmount());
//							break;
//						}
//					}
//					prpLclaimLoss.setKindCode("M");
//					prpLclaimLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
//					prpLclaimLoss.setSumClaim(Double.parseDouble(DataUtils.nullToZero(exceptDeductiblePay[index])));
//					prpLclaimLoss.setInputDate(new DateTime(prpLclaimLossInputDate[index]));
//					prpLclaimLoss.setKindLoss(Double.parseDouble(DataUtils.nullToZero(exceptDeductiblePay[index])));
//					prpLclaimLoss.setAcciDeductibleRate(Double.parseDouble(DataUtils.nullToZero(exceptDeductibleRate[index])));
//					prpLclaimLoss.setKindCodeSub(exceptDeductibleKindCode[index]);
//					prpLclaimLoss.setDatafrom("2");
//					prpLclaimLoss.setLossFeeType("P");
//					prpLclaimLoss.setFeeCategory("C");
//					claimLossList.add(prpLclaimLoss);
//				}
//			}
		}
		//合并同险别同费用类型金额
//		if(claimLossList.size()>1){
//			PrpLclaimLoss prpLclaimLossOld = null;
//			PrpLclaimLoss prpLclaimLossNew = null;
//			for(int i=0;i<claimLossList.size()-1;i++){
//				prpLclaimLossOld = claimLossList.get(i);
//				String kindCode1 = prpLclaimLossOld.getKindCode();
//				String lossFeeType1 = prpLclaimLossOld.getLossFeeType();
//				for(int j=i+1;j<claimLossList.size();j++){
//					prpLclaimLossNew = claimLossList.get(j);
//					String kindCode2 = claimLossList.get(j).getKindCode();
//					String lossFeeType2 = claimLossList.get(j).getLossFeeType();
//					if(kindCode1.equals(kindCode2) && lossFeeType1.equals(lossFeeType2)){
//						Double sumClaimNew = new BigDecimal(prpLclaimLossOld.getSumClaim()).add(new BigDecimal(prpLclaimLossNew.getSumClaim())).doubleValue();
//						String remarkFlagNew = prpLclaimLossNew.getRemarkFlag();
//						prpLclaimLossOld.setSumClaim(sumClaimNew);
//						prpLclaimLossOld.setKindLoss(sumClaimNew);
//						prpLclaimLossOld.setRemarkFlag(remarkFlagNew);
//						prpLclaimLossOld.setHandlerCode(prpLclaimLossNew.getHandlerCode());
//						prpLclaimLossOld.setInputDate(prpLclaimLossNew.getInputDate());
//						claimLossList.remove(j);
//						j = j-1;
//					}
//				}
//			}
//		}
		
		// 修改估损金额信息
		// if (prpLclaimLossClaimNo.length() == 21) {
		String conditions1 = "1=1";
		conditions1 = conditions1 + " and claimNo ='" + prpLclaimLossClaimNo + "' ";
		if ("D".equals(codeService.translateRiskCodetoRiskType(prpLclaimLossRiskCode))) {
			prpLclaimLossService.updateDAAClaimLoss(prpLclaimLossClaimNo, claimLossList);
		} else {
			prpLclaimLossService.updateClaimLoss(conditions1, claimLossList);
		}
	}
	/**
	 * 修改swfLog表数据，触发介接送数
	 * @param claimNo 立案号码
	 * @throws SQLException, Exception
	 * @return 
	 */
	public void updateSwflog(String claimNo) throws Exception{
		//修改swflog的记录，触发送介接数据。
		List<SwfLog> swfLogList = workFlowService.findByConditions("businessNo='"+claimNo+"' and nodeType='claim'");
		if(swfLogList!=null&&swfLogList.size()>0){
			HibernateUtils.executeSql(super.getSession(), "update swflog set nodestatus='2' where businessNo='"+claimNo+"' and nodeType='claim'");
			HibernateUtils.executeSql(super.getSession(), "update swflog set nodestatus='"+swfLogList.get(0).getNodeStatus()+"' where businessNo='"+claimNo+"' and nodeType='claim'");
		}
	}

	/**
	 * 根据保单号取得保单信息
	 * @param policyNo 保单号
	 * @throws SQLException, Exception
	 * @return 返回一个保单信息
	 */
	public PrpCmain findByPolicyNoKey(String policyNo) throws SQLException, Exception {
		return prpCmainService.findPrpCmainByPrimaryKey(policyNo);
	}

	@Override
	public boolean isExist(String claimNo) throws SQLException, Exception {
		boolean exist = false;
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		if (prpLclaim != null) {
			exist = true;
			return exist;
		}
		return exist;
	}
	
	@Override
	public PrpLclaim generateCargoInfo(String policyNo,String endorseNo) throws SQLException, Exception {
		//保險金額、貨物名稱、船名、開行日期、航程,規則：只有貨物運輸險) , 建造年份, 進出口別代號
		PrpLclaim prpLclaim = new PrpLclaim();
		QueryRule queryRule = QueryRule.getInstance();
		if (CommonUtils.isEmpty(endorseNo)) {
			endorseNo = policyNo;
		}
		queryRule.addEqual("id.endorseNo", endorseNo);
		List<PrpCopyCargoItem> prpCopyCargoItemList = prpCopyCargoItemService.findPrpCopyCargoItem(queryRule);
		PrpCopyMain prpCopyMain = this.prpCopyMainService.findPrpCopyMain(endorseNo);
		PrpCopymainCargo prpCopymainCargo = prpCopymainCargoService.findPrpCopymainCargo(endorseNo);
		List<PrpCopymainCarGoSub> resultList = prpCopymainCarGoSubService.findPrpCopymainCarGoSub("endorseNo='"+endorseNo+"' order by serialno");
		PrpCopymainCarGoSub prpCopymainCarGoSub = null;
		if (!CommonUtils.isEmpty(resultList)) {
			prpCopymainCarGoSub = resultList.get(resultList.size()-1);
			// 船名
			prpLclaim.setShipCName(DataUtils.dbNullToEmpty(prpCopymainCarGoSub.getSiteName()));
			prpLclaim.setEndSitePort(prpCopymainCarGoSub.getPortName());// 中轉地/目的地
		}
		//建造年份
		if (prpCopymainCargo != null) {
			prpLclaim.setClaimAgent(prpCopymainCargo.getCheckAgentCode());
			prpLclaim.setStartSitePort(prpCopymainCargo.getStartSiteName());//起運地
			// 根據進出口別代號欄位判斷, 如果是出口, 由承保帶出中轉地/目的地的編號;如果是進口,由承保帶出起運地編號
			if ("1".equals(prpCopymainCargo.getPreserveInfo())) {// 進口
				prpLclaim.setAreaCode(prpCopymainCargo.getStartSiteCode());
			} else if ("2".equals(prpCopymainCargo.getPreserveInfo())) {// 出口
				prpLclaim.setAreaCode(prpCopymainCarGoSub != null ? prpCopymainCarGoSub.getPortCode() : "");
			}
			//進出口別代號
			prpLclaim.setImportType(DataUtils.dbNullToEmpty(prpCopymainCargo.getPreserveInfo()));
		}
		PrpCopyCargoItem prpCopyCargoItem = null;
		if (!CommonUtils.isEmpty(prpCopyCargoItemList)) {
			prpCopyCargoItem = prpCopyCargoItemList.get(0);
			// 设置货物编号、名称
			prpLclaim.setCargoNo(prpCopyCargoItem.getCargoBigTypeCode());
			prpLclaim.setCargoName(prpCopyCargoItem.getCargoName());
			// 開行日期
			prpLclaim.setSailStartDate(CommonUtils.getYearToDayStr(prpCopyMain.getStartDate()));
			//保險金額
			//double sumAmount = Double.valueOf(prpCopyCargoItem.getAmount())*Double.parseDouble(prpCopyCargoItem.getExchangeRate());
			if(CommonUtils.isEmpty(prpCopyCargoItem.getAmount())||"0".equals(prpCopyCargoItem.getAmount())){
				prpLclaim.setSumAmount(prpCopyMain.getSumAmount());
			}else{
				prpLclaim.setSumAmount(Double.valueOf(prpCopyCargoItem.getAmount()));
			}
		}
		return prpLclaim;
	}
	
	// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start
	public Page findBySpecialEditConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String statement = "Select DISTINCT prplclaim.ClaimNo," + "prplclaim.RegistNo, " + "prplclaim.OperatorCode, " + "b.RiskCode, " + "b.Status, "+ "prplregist.LicenseNo, " + "prplclaim.InsuredName From (select * from PrpLClaimStatus where NodeType='claim') b LEFT JOIN prplclaim ON prplclaim.ClaimNo = b.BusinessNo LEFT JOIN prplregist ON prplclaim.registNo = prplregist.registNo" + conditions
				+ " order by prplclaim.claimno";
		StringBuffer buffer = new StringBuffer(200);
		buffer.append(statement);
		List<PrpLclaim> resultList = new ArrayList<PrpLclaim>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, pageSize);
		PrpLclaim prpLclaim = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象 而是一个数组
			prpLclaim = new PrpLclaim();
			prpLclaim.setClaimNo((String) object[0]);
			prpLclaim.setRegistNo((String) object[1]);
			prpLclaim.setOperatorCode((String) object[2]);
			prpLclaim.setRiskCode((String) object[3]);
			prpLclaim.setStatus((String) object[4]);
			prpLclaim.setLicenseNo((String) object[5]);
			prpLclaim.setInsuredName((String) object[6]);
			
			String conditionsForPrpLregist = " REGISTNO = '"+prpLclaim.getRegistNo()+"' AND NODETYPE = 'claim'";
			List<SwfLog> swfLog = workFlowService.findByConditions(conditionsForPrpLregist);
			if(null!=swfLog && swfLog.size()>0){
				for(SwfLog sl :swfLog){
					prpLclaim.setRemark(sl.getId().getLogNo()+"");
				}
			}
			resultList.add(prpLclaim);
		}
		return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(session, statement), pageSize, resultList);
	}
	
	public void updateSpecialEditCase(ClaimDto claimDto) throws SQLException, Exception{
		if (claimDto != null){
			PrpLclaim prpLclaim = claimDto.getPrpLclaim();
			String policyNo = prpLclaim.getPolicyNo();
			String registNo = prpLclaim.getRegistNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整 START
			String damageHour = prpLclaim.getDamageStartHour();
			String damageAddress = prpLclaim.getDamageAddress();
			//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整 END
			String damageAreaCode = prpLclaim.getDamageAreaCode();
			String damageAreaName = prpLclaim.getDamageAreaName();
			if (damageDate != null && damageHour != null && damageAreaCode != null && damageAreaName != null){
				String sql1 = null;
				sql1 = "update PrpLclaim set DAMAGESTARTDATE = ? , DAMAGESTARTHOUR = ? , DAMAGEENDDATE = ? , DAMAGEENDHOUR = ? , DAMAGEAREACODE = ? , DAMAGEAREANAME = ? where REGISTNO = ? AND POLICYNO = ? ";
				HibernateUtils.executeSql(super.getSession(), sql1 , damageDate , damageHour , damageDate , damageHour , damageAreaCode , damageAreaName, registNo , policyNo);
				String sql2 = null;
				//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整 START
				sql2 = "update PrpLregist set DAMAGESTARTDATE = ? , DAMAGESTARTHOUR = ? , DAMAGEENDDATE = ? , DAMAGEENDHOUR = ? ,DAMAGEADDRESS = ? where REGISTNO = ? AND POLICYNO = ? ";
				HibernateUtils.executeSql(super.getSession(), sql2 , damageDate , damageHour , damageDate , damageHour , damageAddress, registNo , policyNo);
				//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整 END
			}
			//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START
			String damageCode = prpLclaim.getDamageCode();
			String damageName = prpLclaim.getDamageName();
			if(prpLclaim.getRiskCode().equals("F01") || prpLclaim.getRiskCode().equals("F02")){
				if (damageCode != null && damageName != null ){
					String sql3 = null;
					sql3 = "update PrpLclaim set DAMAGECODE = ? , DAMAGENAME = ? where REGISTNO = ? AND POLICYNO = ? ";
					HibernateUtils.executeSql(super.getSession(), sql3 , damageCode , damageName , registNo , policyNo);
					String sql4 = null;
					
					sql4 = "update PrpLregist set DAMAGECODE = ? , DAMAGENAME = ? where REGISTNO = ? AND POLICYNO = ? ";
					HibernateUtils.executeSql(super.getSession(), sql4 , damageCode , damageName , registNo , policyNo);
					
				}
			}
			//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END
		}
	}
	// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrplregistrpolicyService getPrplregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrplregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpLthirdCarLossService getPrpLthirdCarLossService() {
		return prpLthirdCarLossService;
	}

	public void setPrpLthirdCarLossService(PrpLthirdCarLossService prpLthirdCarLossService) {
		this.prpLthirdCarLossService = prpLthirdCarLossService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLthirdPropService getPrpLthirdPropService() {
		return prpLthirdPropService;
	}

	public void setPrpLthirdPropService(PrpLthirdPropService prpLthirdPropService) {
		this.prpLthirdPropService = prpLthirdPropService;
	}

	public PrpLdriverService getPrpLdriverService() {
		return PrpLdriverService;
	}

	public void setPrpLdriverService(PrpLdriverService prpLdriverService) {
		PrpLdriverService = prpLdriverService;
	}

	public PrpLltextService getPrpLltextService() {
		return prpLltextService;
	}

	public void setPrpLltextService(PrpLltextService prpLltextService) {
		this.prpLltextService = prpLltextService;
	}

	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

	public PrpLclaimFeeService getPrpLclaimFeeService() {
		return prpLclaimFeeService;
	}

	public void setPrpLclaimFeeService(PrpLclaimFeeService prpLclaimFeeService) {
		this.prpLclaimFeeService = prpLclaimFeeService;
	}

	public PrpLdocService getPrpLdocService() {
		return prpLdocService;
	}

	public void setPrpLdocService(PrpLdocService prpLdocService) {
		this.prpLdocService = prpLdocService;
	}

	public PrpLextService getPrpLextService() {
		return prpLextService;
	}

	public void setPrpLextService(PrpLextService prpLextService) {
		this.prpLextService = prpLextService;
	}

	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}

	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}

	public PrpLacciPersonService getPrpLacciPersonService() {
		return prpLacciPersonService;
	}

	public void setPrpLacciPersonService(PrpLacciPersonService prpLacciPersonService) {
		this.prpLacciPersonService = prpLacciPersonService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLpersonTraceService getPrpLpersonTraceService() {
		return prpLpersonTraceService;
	}

	public void setPrpLpersonTraceService(PrpLpersonTraceService prpLpersonTraceService) {
		this.prpLpersonTraceService = prpLpersonTraceService;
	}

	public PrpLquickCaseService getPrpLquickCaseService() {
		return prpLquickCaseService;
	}

	public void setPrpLquickCaseService(PrpLquickCaseService prpLquickCaseService) {
		this.prpLquickCaseService = prpLquickCaseService;
	}

	public PrpallPolicyService getPrpallPolicyService() {
		return prpallPolicyService;
	}

	public void setPrpallPolicyService(PrpallPolicyService prpallPolicyService) {
		this.prpallPolicyService = prpallPolicyService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}
	
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpCitemCarExtService getPrpCitemCarExtService() {
		return prpCitemCarExtService;
	}

	public void setPrpCitemCarExtService(PrpCitemCarExtService prpCitemCarExtService) {
		this.prpCitemCarExtService = prpCitemCarExtService;
	}

	public PrpLclaimCreditService getPrpLclaimCreditService() {
		return prpLclaimCreditService;
	}

	public void setPrpLclaimCreditService(PrpLclaimCreditService prpLclaimCreditService) {
		this.prpLclaimCreditService = prpLclaimCreditService;
	}

	public PrpCCargoItemService getPrpCCargoItemService() {
		return prpCCargoItemService;
	}

	public void setPrpCCargoItemService(PrpCCargoItemService prpCCargoItemService) {
		this.prpCCargoItemService = prpCCargoItemService;
	}

	public PrpCopyCargoItemService getPrpCopyCargoItemService() {
		return prpCopyCargoItemService;
	}

	public void setPrpCopyCargoItemService(PrpCopyCargoItemService prpCopyCargoItemService) {
		this.prpCopyCargoItemService = prpCopyCargoItemService;
	}

	public PrpCopyMainService getPrpCopyMainService() {
		return prpCopyMainService;
	}

	public void setPrpCopyMainService(PrpCopyMainService prpCopyMainService) {
		this.prpCopyMainService = prpCopyMainService;
	}

	public PrpCopymainCargoService getPrpCopymainCargoService() {
		return prpCopymainCargoService;
	}

	public void setPrpCopymainCargoService(PrpCopymainCargoService prpCopymainCargoService) {
		this.prpCopymainCargoService = prpCopymainCargoService;
	}

	public PrpCopymainCarGoSubService getPrpCopymainCarGoSubService() {
		return prpCopymainCarGoSubService;
	}

	public void setPrpCopymainCarGoSubService(PrpCopymainCarGoSubService prpCopymainCarGoSubService) {
		this.prpCopymainCarGoSubService = prpCopymainCarGoSubService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}
	
	//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 START
	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}
	//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 END
}
