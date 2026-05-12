package com.sinosoft.claim.endcase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.util.ReinsTranslateViewHelper;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveLogService;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveService;
import com.sinosoft.claim.schema.service.facade.PrpLcaseNoService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 结案处理接口实现类
 * @author 中科软
 *
 */
public class EndcaseServiceSpringImpl extends GenericDaoHibernate<EndcaseDto, String> implements EndcaseService {
	private PrpLclaimService prpLclaimService;
	private EndcaseService endcaseService;
	private PrpLDocArchiveService prpLDocArchiveService;
	private PrpLcaseNoService prpLcaseNoService;
	private PrpLltextService prpLltextService;
	private PrpLclaimStatusService prpLclaimStatusService;
	private PrpLcompensateService prpLcompensateService;
	private PrpLrecaseService prpLrecaseService;
	private ReinsServiceManager reinsServiceManager;
	private PrpLDocArchiveLogService prpLDocArchiveLogService;
	private PrpDuserService prpDuserService;
	private UtiCodeTransferService utiCodeTransferService;
	private PrplregistrpolicyService prpLregistrpolicyService;
	private WorkFlowService workFlowService;

	/**
	 * 保存结案
	 * @param endcaseDto：自定义结案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		endcaseService.save(endcaseDto);
		ReinsCaseStatus reinsCaseStatus = ReinsTranslateViewHelper.getReinsCaseStatus(endcaseDto);
		reinsServiceManager.getReinsService().changeCaseStatus(reinsCaseStatus);
		// 保存归档信息 begin
		PrpLclaim prpLclaim = endcaseDto.getPrpLclaim();
		PrpLDocArchive prpLDocArchive = new PrpLDocArchive();
		prpLDocArchive.setClaimNo(prpLclaim.getClaimNo());
		prpLDocArchive.setRegistno(prpLclaim.getRegistNo());
		prpLDocArchive.setPolicyNo(prpLclaim.getPolicyNo());
		prpLDocArchive.setComCode(prpLclaim.getComCode());
		prpLDocArchive.setInsuredCode(prpLclaim.getInsuredCode());
		prpLDocArchive.setInsuredName(prpLclaim.getInsuredName());
		prpLDocArchive.setEndCaseDate(prpLclaim.getEndCaseDate());
		prpLDocArchive.setSumDutyPaid(prpLclaim.getSumPaid());
		prpLDocArchive.setStatus("1");
		prpLDocArchiveService.save(prpLDocArchive);
		// 插入归档调阅操作日志信息
		PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog();
		prpLDocArchiveLog.getId().setClaimNo(prpLclaim.getClaimNo());
		prpLDocArchiveLog.getId().setSerialNo(1);
		prpLDocArchiveLog.setRegistNo(prpLclaim.getRegistNo());
		prpLDocArchiveLog.setPolicyNo(prpLclaim.getPolicyNo());
		prpLDocArchiveLog.setComcode(prpLclaim.getComCode());
		prpLDocArchiveLog.setInsuredCode(prpLclaim.getInsuredCode());
		prpLDocArchiveLog.setInsuredName(prpLclaim.getInsuredName());
		prpLDocArchiveLog.setEndCaseDate(new DateTime(prpLclaim.getEndCaseDate(), DateTime.YEAR_TO_DAY));
		prpLDocArchiveLog.setSumDutyPaid(prpLclaim.getSumPaid());
		prpLDocArchiveLog.setStatus("1");
		prpLDocArchiveLog.setOperatorCode(prpLclaim.getEndCaserCode());
		PrpDuser prpDuser = prpDuserService.findPrpDuser(prpLclaim.getEndCaserCode());
		prpLDocArchiveLog.setOperatorName(prpDuser.getUserName());
		prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLDocArchiveLogService.save(prpLDocArchiveLog);
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 保存结案
	 * @param endcaseDto：自定义结案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	@ProcessTask(processId="claim_05",userId = "endca", businessBeanOffset = 0)
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 1) })
	public void saveBpm(String businessNo, String nodeType, EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(endcaseDto, workFlowDto);
	}

	// @ProcessTask(userId = "endca_related", businessBeanOffset = 0)
	// public void saveBpm_related(String businessNo, EndcaseDto endcaseDto,
	// WorkFlowDto workFlowDto) throws SQLException, Exception {
	// this.save(endcaseDto, workFlowDto);
	// }

	/**
	 * 查询还有几条数据没有结案，如果大於一条，返回endca，如果小於一天，end结束流程
	 * @param claimNo
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public String findBpmNode(String claimNo) throws Exception {
		String nodeType = "end";
		String sql = "select count(1) from PrpLrecase where claimNo='" + claimNo + "'";
		Long count = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
		if (count > 0) {
			return nodeType;
		}
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		boolean flag = prpLregistrpolicyService.isCompelFlag(prpLclaim.getRegistNo());
		if (flag) {
			sql = "select count(1) from  prpLclaim where registNo ='" + prpLclaim.getRegistNo() + "' " + " AND CANCELDATE IS NULL  AND CLAIMDATE IS NOT NULL AND ENDCASEDATE IS  NULL AND CASENO IS  NULL";
			Long sum = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
			if (sum > 1) {
				nodeType = "endca";
			}
		}
		return nodeType;
	}

	/**
	 * 保存结案
	 * @param EndcaseDto：结案对象DTO
	 * @throws Exception
	 */
	public void save(EndcaseDto endcaseDto) throws SQLException, Exception {
		String caseNo = ""; // 赔案号
		String claimNo = ""; // 赔案号
		String textType = "";
		caseNo = endcaseDto.getPrpLclaim().getCaseNo();
		claimNo = endcaseDto.getPrpLclaim().getClaimNo();
		// System.out.println("首先删除原来的相关数据");
		// 首先删除原来的相关数据
		if (endcaseDto.getPrpLclaim().getCancelDate() != null && !endcaseDto.getPrpLclaim().getCancelDate().toString().equals("")) {
			textType = "10";
		} else {
			textType = "08";
		}
		deleteSubInfo(caseNo, claimNo, textType);
		if ("1".equals(endcaseDto.getPrpLclaim().getFlag())) {
			endcaseDto.getPrpLclaim().setFlag("");
			prpLclaimService.save(endcaseDto.getPrpLclaim());
		}
		// System.out.println("插入陪案号表");
		// 插入陪案号表
		if (endcaseDto.getPrpLcaseNoList() != null) {
			prpLcaseNoService.save(endcaseDto.getPrpLcaseNoList());
		}
		// System.out.println("插入文本信息表");
		// 插入文本信息表
		if (endcaseDto.getPrpLltextList() != null) {
			prpLltextService.save(endcaseDto.getPrpLltextList());
		}
		// System.out.println("更新立案，赔款计算数，节点状态");
		// 更新立案，赔款计算数，节点状态
		updateStatus(endcaseDto);
	}

	/**
	 * 结案删除子表信息
	 * @param fcoClaimNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(String caseNo, String claimNo, String textType) throws SQLException, Exception {
		String condition1 = " claimno = " + "'" + claimNo.trim() + "'";
		String condition2 = " caseno = " + "'" + caseNo.trim() + "'";
		// 示例未完成
		String statement = "";
		statement = " DELETE FROM prplltext Where " + condition1 + " and TextType = '" + textType + "'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prplcaseno Where " + condition2;
		HibernateUtils.executeSql(session, statement);
	}

	/**
	 * 变更结案的操作状态的方法
	 * @param endcaseDto 结案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateStatus(EndcaseDto endcaseDto) throws SQLException, Exception {

		// 示例未完成
		String statement = "";
		// 更新节点状态
		if (endcaseDto.getPrpLclaimStatus() != null) {
			String condition3 = " BusinessNo='" + endcaseDto.getPrpLclaimStatus().getId().getBusinessNo().trim() + "' " + " AND NodeType ='" + endcaseDto.getPrpLclaimStatus().getId().getNodeType() + "'";
			statement = " DELETE FROM prpLclaimStatus Where " + condition3;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			HibernateUtils.executeSql(session, statement);
			prpLclaimStatusService.save(endcaseDto.getPrpLclaimStatus());
		}
		// 更新立案表信息
		if (endcaseDto.getPrpLclaim() != null) {
			prpLclaimService.update(endcaseDto.getPrpLclaim());
		}
		// 更新赔款计算书
		if (endcaseDto.getPrpLcompensateList() != null) {
			for (int i = 0; i < endcaseDto.getPrpLcompensateList().size(); i++) {
				PrpLcompensate prpLcompensate = (PrpLcompensate) (endcaseDto.getPrpLcompensateList().get(i));
				prpLcompensateService.update(prpLcompensate);
			}
		}
	}

	/**
	 * 按条件从prplcompensate表,prplregist表,prplclaimstatus表和表prpLclaim中查询多条数据
	 * @param conditions String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return Collection Modify By sunhao 2004-08-24
	 *         Reason:增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
	 */
	public List<PrpLcaseNo> findByQueryConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		String statement = "Select DISTINCT PrpLclaim.ClaimNo," + "PrpLclaim.CaseNo, " + "PrpLclaim.PolicyNo, " + "PrpLregist.LicenseNo, " + "PrpLclaim.EndCaserCode, "
				+ "PrpLclaim.EndCaseDate,PrpLclaim.RiskCode From PrpLclaim right join (select * from PrpLClaimStatus where NodeType='endca') b on b.policyno=PrpLclaim.policyno  left join PrpLregist on PrpLregist.registNo=PrpLclaim.registNo where"
				+ conditions;
		List<PrpLcaseNo> resultList = new ArrayList<PrpLcaseNo>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, rowsPerPage);
		PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
															// 而是一个数组
			prpLcaseNo = new PrpLcaseNo();
			prpLcaseNo.setClaimNo((String) object[0]);
			prpLcaseNo.getId().setCaseNo((String) object[1]);
			prpLcaseNo.setPolicyNo((String) object[2]);
			prpLcaseNo.setLicenseNo((String) object[3]);
			prpLcaseNo.setEndCaserCode((String) object[4]);
			// prpLcaseNo.setEndCaseDate(new Date(((Timestamp)
			// object[5]).getTime()));
			prpLcaseNo.setRiskCode((String) object[6]);
			resultList.add(prpLcaseNo);
		}
		return resultList;
	}

	/**
	 * 获得结案信息
	 * @param caseNo：结案号
	 * @return 结案对象
	 * @throws Exception
	 */
	public EndcaseDto findByPrimaryKey(String caseNo, String claimNo, String certiNo, String certiType) throws SQLException, UserException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.caseNo", caseNo.trim());
		EndcaseDto endcaseDto = new EndcaseDto();
		endcaseDto.setPrpLcaseNoList(prpLcaseNoService.findPrpLcaseNo(queryRule));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo.trim());
		queryRule.addEqual("id.textType", "08");
		endcaseDto.setPrpLltextList(prpLltextService.findPrpLltext(queryRule));
		endcaseDto.setPrpLclaimStatus(prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(caseNo, "endca", 0)));
		endcaseDto.setPrpLclaim(prpLclaimService.findPrpLclaim(claimNo));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo.trim());
		endcaseDto.setPrpLcompensateList(prpLcompensateService.findPrpLcompensate(queryRule));
		return endcaseDto;
	}

	@Override
	public void reCaseSave(EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// 创建数据库管理对象
		PrpLrecase prpLrecase = endcaseDto.getPrpLrecase();
		prpLrecaseService.update(prpLrecase);
		ReinsCaseStatus reinsCaseStatus = new ReinsCaseStatus();
		reinsCaseStatus.setClaimNo(prpLrecase.getId().getClaimNo());
		reinsCaseStatus.setBusinessType(ReinsCaseStatus.BusinessType.ENDCASE);
		reinsCaseStatus.setOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		reinsCaseStatus.setOperaterCode(prpLrecase.getCloseCaseUserCode());
		reinsCaseStatus.setOperateComCode(prpLrecase.getCloseCaseComCode());
		reinsServiceManager.getReinsService().changeCaseStatus(reinsCaseStatus);
		// }
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 从开赔案的结案，带jbpm工作流信息
	 * @param businessNo
	 * @param nodeType
	 * @param endcaseDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	@ProcessTask(userId = "endca", businessBeanOffset = 0)
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 1) })
	public void saveBpmReCaseSave(String businessNo, String nodeType, EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.reCaseSave(endcaseDto, workFlowDto);
	}

	@Override
	public void delete(String caseNo) throws SQLException, Exception {

	}

	@Override
	public boolean isExist(String caseNo, String claimNo, String certiNo, String certiType) throws SQLException, Exception {
		return false;
	}

	@Override
	public Collection<?> findByConditions(String conditions) throws SQLException, Exception {
		return null;
	}

	@Override
	public List<PrpLcaseNo> findByQueryConditions(String conditions) throws SQLException, Exception {
		return this.findByQueryConditions(conditions, 0, 20);
	}

	@Override
	public EndcaseDto findByPrimaryKey(String claimNo) throws SQLException, UserException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo.trim());
		EndcaseDto endcaseDto = new EndcaseDto();
		endcaseDto.setPrpLcaseNoList(prpLcaseNoService.findPrpLcaseNo(queryRule));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo.trim());
		queryRule.addEqual("id.textType", "08");
		endcaseDto.setPrpLltextList(prpLltextService.findPrpLltext(queryRule));
		endcaseDto.setPrpLclaim(prpLclaimService.findPrpLclaim(claimNo));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo.trim());
		endcaseDto.setPrpLcompensateList(prpLcompensateService.findPrpLcompensate(queryRule));
		return endcaseDto;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
	}

	public PrpLDocArchiveService getPrpLDocArchiveService() {
		return prpLDocArchiveService;
	}

	public void setPrpLDocArchiveService(PrpLDocArchiveService prpLDocArchiveService) {
		this.prpLDocArchiveService = prpLDocArchiveService;
	}

	public PrpLcaseNoService getPrpLcaseNoService() {
		return prpLcaseNoService;
	}

	public void setPrpLcaseNoService(PrpLcaseNoService prpLcaseNoService) {
		this.prpLcaseNoService = prpLcaseNoService;
	}

	public PrpLltextService getPrpLltextService() {
		return prpLltextService;
	}

	public void setPrpLltextService(PrpLltextService prpLltextService) {
		this.prpLltextService = prpLltextService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public PrpLDocArchiveLogService getPrpLDocArchiveLogService() {
		return prpLDocArchiveLogService;
	}

	public void setPrpLDocArchiveLogService(PrpLDocArchiveLogService prpLDocArchiveLogService) {
		this.prpLDocArchiveLogService = prpLDocArchiveLogService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
