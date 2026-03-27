package com.sinosoft.claim.endcase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.util.ReinsTranslateViewHelper;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.PrpLrecaseId;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.StartProcess;

/**
 * 重开赔案处理实现类
 * @author 中科软
 */
public class RecaseServiceSpringImpl extends GenericDaoHibernate<ReCaseDto, String> implements RecaseService {
	private PrpLclaimService prpLclaimService;
	private PrpLrecaseService prpLrecaseService;
	private ReinsServiceManager reinsServiceManager;
	private UtiCodeTransferService utiCodeTransferService;
	private WorkFlowService workFlowService;

	/**
	 * 重开赔案保存
	 * @param recaseDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(ReCaseDto recaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		save(recaseDto);
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
		// 送再保
		String riskCode = workFlowDto.getSubmitSwfLogList().get(0).getRiskCode();
		// String codeName = "";
		List<UtiCodeTransfer> utiCodeTransferDtoList = this.getUtiCodeTransferService().findByConditions(" outercode='" + riskCode + "'");
		if (utiCodeTransferDtoList != null && utiCodeTransferDtoList.size() != 0) {
			// codeName = utiCodeTransferDtoList.get(0).getRiskType();
		}
		// 国寿财公司调整，由于再保不处理车险信息，车险理赔不需要与再保进行交互
		ReinsCaseStatus reinsCaseStatus = ReinsTranslateViewHelper.getReinsCaseStatus(recaseDto);
		// 待调整杨芳
		// 以调整
		reinsServiceManager.getReinsService().changeCaseStatus(reinsCaseStatus);

	}

	/**
	 * 重开赔案保存,保存jbpm工作流信息
	 * @param recaseDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	@StartProcess(processId = "claim_reCase_05", businessBeanOffset = 0)
	@ProcessTask(userId = "recase", businessBeanOffset = 0)
	public void saveBpm(String businessNo, ReCaseDto recaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(recaseDto, workFlowDto);
	}

	/**
	 * 保存
	 */
	public void save(ReCaseDto reCaseDto) throws SQLException, Exception {
		insert(reCaseDto);
	}

	/**
	 * 增加重开赔案信息
	 * @param reCaseDto
	 * @throws Exception
	 */
	public void insert(ReCaseDto reCaseDto) throws SQLException, Exception {
		// System.out.println("--[重开赔案]--DBRecase---[insert]-[claimNo]："+
		// reCaseDto.getPrpLrecaseDto().getClaimNo());
		// 先删後插
		PrpLrecase prpLrecase = new PrpLrecase();
		prpLrecase = reCaseDto.getPrpLrecase();

		deleteSubInfo(reCaseDto);
		prpLrecaseService.save(prpLrecase);
		// updateClaimStatus(dbManager,ReCaseDto);
	}

	/**
	 * 删除重开赔案子表信息
	 * @param ReCaseDto
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(ReCaseDto ReCaseDto) throws SQLException, Exception {
		// System.out.println("--[重开赔案]--DBRecase----[deleteSubInfo]");
		String condition = "";
		String statement = "";
		if (ReCaseDto.getPrpLrecase() != null) {
			String claimNo = ReCaseDto.getPrpLrecase().getId().getClaimNo().trim();
			int serialNo = ReCaseDto.getPrpLrecase().getId().getSerialNo();
			condition = " claimNo = " + "'" + claimNo + "' and serialNo ='" + serialNo + "'";
			statement = " DELETE FROM PrpLRecase Where " + condition;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			HibernateUtils.executeSql(session, statement);
		}
	}

	/**
	 * 查询
	 */
	public ReCaseDto findByPrimaryKey(String claimNo, int serialNo) throws SQLException, Exception {
		// System.out.println("--[重开赔案]--DBRecase----[findByPrimaryKey]");
		ReCaseDto ReCaseDto = new ReCaseDto();
		PrpLrecaseId prpLrecaseId = new PrpLrecaseId();
		prpLrecaseId.setClaimNo(claimNo);
		prpLrecaseId.setSerialNo(serialNo);
		ReCaseDto.setPrpLclaim(prpLclaimService.findPrpLclaim(claimNo));
		ReCaseDto.setPrpLrecase(prpLrecaseService.findPrpLrecase(prpLrecaseId));
		return ReCaseDto;
	}

	/**
	 * 判断是否重开赔案
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public boolean isRecase(String claimNo) throws Exception {
		boolean blnReturn = false; // 为false为无重开 或 重开已结案
		int maxSerialNo = 0;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo);
		Collection<PrpLrecase> list = prpLrecaseService.findPrpLrecase(queryRule);
		if (list != null && list.size() > 0) {
			maxSerialNo = this.getMaxSerialNo(claimNo);
			ReCaseDto reCaseDto = this.findByPrimaryKey(claimNo, maxSerialNo);
			PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
			if (CommonUtils.isEmpty(prpLrecase.getCloseCaseUserCode()) && prpLrecase.getCloseCaseDate() == null) {
				blnReturn = true; // 有重开，且未结案
			}
		}
		return blnReturn;
	}

	public int getMaxSerialNo(String claimNo) throws Exception {
		int serialNo = prpLrecaseService.getCount(claimNo);
		return serialNo;
	}

	/**
	 * 根据条件查询重开赔案信息集合
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public Collection<?> findByConditions(String conditions) throws SQLException, Exception {
		String sql = "select * from PrpLrecase where " + conditions;
		return HibernateUtils.findbySql(super.getSession(), sql, PrpLrecase.class);
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
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

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
