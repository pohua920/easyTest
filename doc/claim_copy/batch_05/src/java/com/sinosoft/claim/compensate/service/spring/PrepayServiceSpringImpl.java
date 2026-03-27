package com.sinosoft.claim.compensate.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.compensate.vo.PrepayDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLptext;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLptextService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.prpall.pubfun.PubTools;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.undwrt.dto.custom.UndwrtSubmitDto;

/**
 * 预赔 实现
 * @Description 
 * @author 中科软
 */
public class PrepayServiceSpringImpl extends GenericDaoHibernate<PrepayDto, String> implements PrepayService {
	/** 预赔Service */
	private PrpLprepayService prpLprepayService;
	/** 案件状态Service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 理赔文字Service */
	private PrpLptextService prpLptextService;
	/** 立案Service */
	private PrpLclaimService prpLclaimService;
	/** 工作流引擎Service */
	private WorkFlowService workFlowService;
	/** 双核工作流Service */
	private WfLogService wfLogService;

	@Override
	public void approve(String prepayNo, String userCode, String underWriteFlag) throws Exception {
		this.prpLprepayService.approve(prepayNo, userCode, underWriteFlag);
	}
	/**
	 * 删除预赔
	 */
	@Override
	public void delete(String PrepayNo) throws Exception {
		String condition = " prepayNo = '" + StringUtils.rightTrim(PrepayNo) + "'";
		String statement = " DELETE FROM prpLprepayText Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLdriver Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLthirdParty Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prpLprepay Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	/**
	 * 获得预赔信息
	 */
	@Override
	public List<PrpLprepay> findByApproveConditions(String conditions) throws Exception {
		return this.prpLprepayService.findByApproveQueryConditions(conditions, 0, 0);
	}

	@Override
	public List<PrpLprepay> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.prpLprepayService.findPrpLprepay(queryRule);
	}

	@Override
	public PrepayDto findByPrimaryKey(String prepayNo) throws Exception {
		PrepayDto prepayDto = null;
		PrpLprepay prpLprepay = this.prpLprepayService.findPrpLprepay(prepayNo);
		if (prpLprepay != null) {
			prepayDto = new PrepayDto();
			prepayDto.setPrpLprepay(prpLprepay);
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.preCompensateNo", prepayNo);
			prepayDto.setPrpLclaim(this.prpLclaimService.findPrpLclaim(prpLprepay.getClaimNo()));
			prepayDto.setPrpLptextList(this.prpLptextService.findPrpLptext(queryRule));
			prepayDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(prepayNo, "speci", 5)));
		}
		return prepayDto;
	}

	@Override
	public List<PrpLprepay> findByQueryConditions(String conditions) throws Exception {
		String statement = "Select DISTINCT a.preCompensateNo,"
				+ "a.ClaimNo, "
				+ "a.PolicyNo, "
				+ "a.handlerCode, "
				+ "a.UnderWriteFlag, "
				+ "b.OperateDate, "
				+ "b.Status, "
				+ "b.RiskCode, "
				+ "c.LicenseNo From (select * from PrpLClaimStatus where NodeType='prepa') b LEFT JOIN PrpLprepay a ON a.precompensateno = b.BusinessNo LEFT JOIN (SELECT PrpLclaim.ClaimNo AS ClaimNo,PrpLclaim.RegistNo AS RegistNo,PrpLregist.LicenseNo AS LicenseNo from PrpLclaim LEFT JOIN PrpLregist on PrpLregist.RegistNo=PrpLclaim.RegistNo) c ON a.ClaimNo = c.ClaimNo where"
				+ conditions;
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		List<PrpLprepay> prpLprepayList = new ArrayList<PrpLprepay>();
		if (list != null && !list.isEmpty()) {
			PrpLprepay prpLprepay = null;
			Object[] object = null;
			for (Iterator<?> it = list.iterator(); it.hasNext(); prpLprepayList.add(prpLprepay)) {
				object = (Object[]) it.next();
				prpLprepay = new PrpLprepay();
				prpLprepay.setPreCompensateNo((String) object[0]);
				prpLprepay.setClaimNo((String) object[1]);
				prpLprepay.setPolicyNo((String) object[2]);
				prpLprepay.setHandlerCode((String) object[3]);
				prpLprepay.setUnderWriteFlag((String) object[4]);
				prpLprepay.setOperateDate(new DateTime(new Date(((Timestamp) object[6]).getTime()), DateTime.YEAR_TO_DAY));
				prpLprepay.setStatus((String) object[6]);
				prpLprepay.setRiskCode((String) object[7]);
			}
		}
		return prpLprepayList;
	}

	/**
	 * 按条件从prplprepay,prplclaimstatus表中查询多条数据
	 */
	@Override
	public boolean isExist(String prepayNo) throws Exception {
		return this.prpLprepayService.findPrpLprepay(prepayNo) != null;
	}

	/**
	 * 保存预赔带工作流
	 */
	@Override
	public void save(PrepayDto prepayDto, WorkFlowDto workFlowDto, UserDto user, String preCompensateNo, boolean isSubmitUndwrt) throws Exception {
		//因为在核赔流程中，会查询和修改prpLprepay表的信息，所有需要先提交hibernate的事务，保存到数据库中，
		//在核赔中才能查询出来和修改表信息。liudaoping 2013-03-06
		//后续可以修改了核赔的流程后，不用dbmanager查询后，在修改放到一个事物中
		this.save(prepayDto);
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
		// 核赔和预赔同一事物
		if (isSubmitUndwrt) {
			int vericLogNo = 0;
			if (workFlowDto.getSubmitSwfLogList() != null) {
				vericLogNo = workFlowDto.getSubmitSwfLogList().get(0).getId().getLogNo();
			}
			PrpLprepay prpLprepay = prepayDto.getPrpLprepay();
			UndwrtSubmitDto undwrtSubmitDto = new UndwrtSubmitDto();
			undwrtSubmitDto.setModelType("22");
			undwrtSubmitDto.setCertiType("Y");
			undwrtSubmitDto.setBusinessNo(preCompensateNo);
			undwrtSubmitDto.setRiskCode(prpLprepay.getRiskCode());
			undwrtSubmitDto.setClassCode(prpLprepay.getClassCode());
			undwrtSubmitDto.setComCode(user.getComCode());
			undwrtSubmitDto.setMakecom(prpLprepay.getMakeCom());
			undwrtSubmitDto.setUserCode(user.getUserCode());
			undwrtSubmitDto.setHandlerCode(prpLprepay.getHandlerCode());
			undwrtSubmitDto.setHandler1Code(prpLprepay.getHandler1Code());
			undwrtSubmitDto.setContractNo("");
			undwrtSubmitDto.setClaimFlag("claim");
			undwrtSubmitDto.setLFlowID(workFlowDto.getUpdateSwfLog().getId().getFlowID());
			undwrtSubmitDto.setLLogNo(vericLogNo);
			undwrtSubmitDto.setPolicyNo(prpLprepay.getPolicyNo());
			undwrtSubmitDto.setClaimNo(prpLprepay.getClaimNo());
			Map<String,String> infoMap = new HashMap<String,String>();
			infoMap.put("comCode", user.getComCode());
			wfLogService.start(undwrtSubmitDto,infoMap);
		}
	}
	/**
	 * 保存带工作流的信息,特殊赔案的处理
	 * @param prepayDto
	 * @param workFlowDto
	 * @param userCode
	 * @param preCompensateNo
	 * @param isSubmitUndwrt
	 * @throws Exception
	 */
	@ProcessTask(processId = "claim_05",userId="speci",businessBeanOffset=0)
	public void saveBpm(String businessNo,PrepayDto prepayDto, WorkFlowDto workFlowDto, UserDto user, String preCompensateNo, boolean isSubmitUndwrt) throws Exception{
		this.save(prepayDto, workFlowDto, user, preCompensateNo, isSubmitUndwrt);
	}

	@Override
	public void save(PrepayDto prepayDto) throws Exception {
		PrpLprepay prpLprepay = prepayDto.getPrpLprepay();
		double exchangeRate = PubTools.getExchangeRate(prpLprepay.getCurrency(), ConstantCodes.LOCAL_CURRENCY, DateTime.current().toString(DateTime.YEAR_TO_DAY));
		prpLprepay.setExchangeRate(exchangeRate);
		prpLprepay.setPaidCNY(prpLprepay.getSumPrePaid() * exchangeRate);
		// 添加本位币兑换率和本位币赔款begin
		String preCompensateNo = prpLprepay.getPreCompensateNo();
		// 首先删除原来的相关数据
		this.deleteSubInfo(preCompensateNo);
		this.prpLprepayService.save(prpLprepay);
		List<PrpLptext> prpLptextList = prepayDto.getPrpLptextList();
		if (prpLptextList != null && !prpLptextList.isEmpty()) {
			this.prpLptextService.save(prpLptextList);
		}
		PrpLclaim prpLclaim = prepayDto.getPrpLclaim();
		if (prpLclaim != null) {
			this.prpLclaimService.updatePrepayPaid(prpLclaim);
		}
		// 进行状态的改变
		this.updateClaimStatus(prepayDto);
	}

	/**
	 * 预赔删除子表信息
	 * @param preCompensateNo
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(String preCompensateNo) throws Exception {
		String condition = " preCompensateNo = " + "'" + preCompensateNo.trim() + "'";
		// 示例未完成
		String statement = " DELETE FROM prplptext Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		statement = " DELETE FROM prplprepay Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	/**
	 * 变更预赔的操作状态的方法
	 * @param prepayDto 预赔对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(PrepayDto prepayDto) throws Exception {
		PrpLclaimStatus prpLclaimStatus = prepayDto.getPrpLclaimStatus();
		if (prpLclaimStatus != null) {
			String statement = " DELETE FROM prpLclaimStatus Where ";
			statement += " BusinessNo='" + prpLclaimStatus.getId().getBusinessNo().trim() + "' " + " AND NodeType ='speci' and TypeFlag='5'";
			HibernateUtils.executeSql(super.getSession(), statement);
			this.prpLclaimStatusService.save(prpLclaimStatus);
		}
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLptextService getPrpLptextService() {
		return prpLptextService;
	}

	public void setPrpLptextService(PrpLptextService prpLptextService) {
		this.prpLptextService = prpLptextService;
	}
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	public WfLogService getWfLogService() {
		return wfLogService;
	}
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}
}
