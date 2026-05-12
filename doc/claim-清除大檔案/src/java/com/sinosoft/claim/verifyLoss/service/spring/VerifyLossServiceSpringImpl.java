package com.sinosoft.claim.verifyLoss.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.service.facade.PrpLcarLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcomponentService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonWoundService;
import com.sinosoft.claim.schema.service.facade.PrpLpropService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLrepairFeeService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossExtService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.verifyLoss.service.facade.VerifyLossService;
import com.sinosoft.claim.verifyLoss.vo.VerifyLossDto;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * 核损业务处理接口实现类
 * @ClassName VerifyLossServiceSpringImpl
 * @Description 
 * @author 中科软
 */
@SuppressWarnings("unchecked")
public class VerifyLossServiceSpringImpl extends GenericDaoHibernate implements VerifyLossService {
	/** 定核损信息service */
	private PrpLverifyLossService prpLverifyLossService;
	/** 车辆定损service */
	private PrpLcarLossService prpLcarLossService;
	/** 修理费用清单service */
	private PrpLrepairFeeService prpLrepairFeeService;
	/** 换件项目清单service */
	private PrpLcomponentService prpLcomponentService;
	/** 人员伤亡明细信息service */
	private PrpLpersonService prpLpersonService;
	/** 财产核定损明细清单service */
	private PrpLpropService prpLpropService;
	/** 伤情信息service */
	private PrpLpersonWoundService prpLpersonWoundService;
	/** 定核损意见service */
	private PrpLverifyLossExtService prpLverifyLossExtService;
	/** 报案信息补充说明service */
	private PrpLregistExtService prpLregistExtService;
	/** 理赔节点状态service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 工作流处理service */
	private WorkFlowService workFlowService;

	@Override
	public void delete(String registNo, String nodeType) throws Exception {
		String condition = " registNo = " + "'" + registNo.trim() + "'";
		String condition1 = " businessNo = " + "'" + registNo.trim() + "' AND NodeType ='" + nodeType.trim() + "'";
		// 示例未完成
		String statement = "";
		// 修理费用清单
		statement = " DELETE FROM prpLrepairFee Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 换件项目清单
		statement = " DELETE FROM prpLcomponent Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 人员伤亡明细信息
		statement = " DELETE FROM prpLperson Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 财产核定损明细
		statement = " DELETE FROM prpLprop Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 定损车辆表
		statement = " DELETE FROM prpLcarLoss Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 状态
		statement = " DELETE FROM prplclaimstatus Where " + condition1;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 定损主表
		statement = " DELETE FROM PrpLverifyLoss Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	@Override
	public Page findByCondition(String conditions, int pageNo, int pageSize) {
		String statement = "Select DISTINCT a.RegistNo,a.PolicyNo, a.HandlerCode, a.DefLossDate, b.OperateDate, b.Status, a.lossItemCode, b.RiskCode, a.lossItemName, " 
		+ "a.UnderWriteEndDate, a.insureCarFlag,a.underWriteCode From (select * from PrpLClaimStatus) b Right JOIN PrpLverifyLoss a ON a.RegistNo = b.BusinessNo LEFT JOIN prplregist c ON b.BusinessNo = c.RegistNo,prplregistrpolicy d " 
		+"where a.RegistNo=d.RegistNo and a.lossitemcode = b.serialNo and a.nodeType=b.nodeType and " + conditions+ " order by b.OperateDate desc ";
		Page page = HibernateUtils.findPagebySql(super.getSession(), statement, pageNo, pageSize);
		List<PrpLverifyLoss> resultList = new ArrayList<PrpLverifyLoss>();
		PrpLverifyLoss prpLverifyLoss = null;
		Object[] object = null;
		List<?> tempListSub = null;
		for (Iterator<?> it = page.getResult().iterator(); it.hasNext(); resultList.add(prpLverifyLoss)) {
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			prpLverifyLoss = new PrpLverifyLoss();
			prpLverifyLoss.getId().setRegistNo((String) object[0]);
			prpLverifyLoss.setPolicyNo((String) object[1]);
			prpLverifyLoss.setHandlerCode((String) object[2]);
			prpLverifyLoss.setDefLossDate(new DateTime(((Timestamp) object[3])));
			prpLverifyLoss.setOperateDate(new DateTime(((Timestamp) object[4])));
			prpLverifyLoss.setStatus((String) object[5]);
			prpLverifyLoss.getId().setLossItemCode((String) object[6]);
			prpLverifyLoss.setRiskCode((String) object[7]);
			prpLverifyLoss.setLossItemName((String) object[8]);
			prpLverifyLoss.setUnderWriteEndDate(object[9]==null?null:new DateTime((Timestamp) object[9],DateTime.YEAR_TO_SECOND));
			prpLverifyLoss.setInsureCarFlag((String) object[10]);
			prpLverifyLoss.setUnderWriteCode((String) object[11]);
			// reason:强三查询
			prpLverifyLoss.setRelatepolicyNo(new TreeSet());
			statement = "select PolicyNo from prplregistrpolicy where RegistNo='" + (String) object[0] + "'";
			tempListSub = HibernateUtils.findbySql(super.getSession(), statement, 0, 0);
			for (Iterator<?> itSub = tempListSub.iterator(); itSub.hasNext();) {
				prpLverifyLoss.getRelatepolicyNo().add((String) itSub.next());
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	@Override
	public VerifyLossDto findByPrimaryKey(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		VerifyLossDto verifyLossDto = new VerifyLossDto();
		verifyLossDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo, "verif", 1)));
		verifyLossDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
		verifyLossDto.setPrpLpersonList(this.prpLpersonService.findPrpLperson(queryRule));
		verifyLossDto.setPrpLpropList(this.prpLpropService.findPrpLprop(queryRule));
		verifyLossDto.setPrpLpersonWoundList(this.prpLpersonWoundService.findPrpLpersonWound(queryRule));
		verifyLossDto.setPrpLverifyLossExtList(this.prpLverifyLossExtService.findPrpLverifyLossExt(queryRule));
		verifyLossDto.setPrpLrepairFeeList(this.prpLrepairFeeService.findPrpLrepairFee(queryRule));
		verifyLossDto.setPrpLcarLossList(this.prpLcarLossService.findPrpLcarLoss(queryRule));
		verifyLossDto.setPrpLcomponentList(this.prpLcomponentService.findPrpLcomponent(queryRule));
		return verifyLossDto;
	}

	@Override
	public VerifyLossDto findByPrimaryKey(String registNo, String lossItemCode, String nodeType) throws Exception {
		VerifyLossDto verifyLossDto = new VerifyLossDto();
		PrpLverifyLoss prpLverifyLoss = this.prpLverifyLossService.findPrpLverifyLoss(registNo, lossItemCode,CommonUtils.getCertainNodeType(nodeType));
		if (prpLverifyLoss != null) {
			verifyLossDto.setPrpLverifyLoss(prpLverifyLoss);
			verifyLossDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo, nodeType, Integer.parseInt(DataUtils.nullToZero(lossItemCode)))));
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", registNo);
			verifyLossDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
			QueryRule queryRulePerson = QueryRule.getInstance();
			queryRulePerson.addEqual("id.registNo", registNo);
			if("veriw".equals(nodeType)){
				queryRulePerson.addEqual("id.personNo", Integer.valueOf(lossItemCode));
			}
			queryRulePerson.addAscOrder("id.personNo");
			verifyLossDto.setPrpLpersonList(this.prpLpersonService.findPrpLperson(queryRulePerson));
			verifyLossDto.setPrpLpersonWoundList(this.prpLpersonWoundService.findPrpLpersonWound(queryRulePerson));
			verifyLossDto.setPrpLpropList(this.prpLpropService.findPrpLprop(queryRule));
			queryRule.addEqual("id.lossItemCode", lossItemCode);
			verifyLossDto.setPrpLverifyLossExtList(this.prpLverifyLossExtService.findPrpLverifyLossExt(queryRule));
			verifyLossDto.setPrpLrepairFeeList(this.prpLrepairFeeService.findPrpLrepairFee(queryRule));
			verifyLossDto.setPrpLcarLossList(this.prpLcarLossService.findPrpLcarLoss(queryRule));
			verifyLossDto.setPrpLcomponentList(this.prpLcomponentService.findPrpLcomponent(queryRule));
		}
		return verifyLossDto;
	}

	@Override
	public boolean isExist(String registNo, String lossItemCode, String nodeType) throws Exception {
		return this.findByPrimaryKey(registNo, lossItemCode, nodeType) != null;
	}

	@Override
	public void save(VerifyLossDto verifyLossDto) throws SQLException, Exception {
		if (verifyLossDto.getPrpLverifyLoss() == null) {
			throw new Exception("數據異常！");
		}
		PrpLverifyLoss prpLverifyLoss = verifyLossDto.getPrpLverifyLoss();
		// 报案号码 关键字
		String registNo = prpLverifyLoss.getId().getRegistNo();
		String lossItemCode = prpLverifyLoss.getId().getLossItemCode();
		// 首先删除原来的相关数据
		this.deleteSubInfo(registNo, lossItemCode,prpLverifyLoss.getId().getNodeType());
		this.prpLverifyLossService.saveOrUpdate(prpLverifyLoss);
		// 定损车辆表
		if(verifyLossDto.getPrpLcarLossList()!=null){
			this.prpLcarLossService.save(verifyLossDto.getPrpLcarLossList());
		}
		// 修理费用清单
		if(verifyLossDto.getPrpLrepairFeeList()!=null){
			this.prpLrepairFeeService.save(verifyLossDto.getPrpLrepairFeeList());
		}
		// 换件项目清单
		if(verifyLossDto.getPrpLcomponentList()!=null){
			this.prpLcomponentService.save(verifyLossDto.getPrpLcomponentList());
		}
		// 人员伤亡明细信息表
		if(verifyLossDto.getPrpLpersonList()!=null){
			this.prpLpersonService.save(verifyLossDto.getPrpLpersonList());
		}
		// 财产核定损明细清单表
		if(verifyLossDto.getPrpLpropList()!=null){
			this.prpLpropService.save(verifyLossDto.getPrpLpropList());
		}
		// 伤情信息表
		if(verifyLossDto.getPrpLpersonWoundList()!=null){
			this.prpLpersonWoundService.save(verifyLossDto.getPrpLpersonWoundList());
		}
		// 定核损扩展信息
		if(verifyLossDto.getPrpLverifyLossExtList()!=null){
			this.prpLverifyLossExtService.save(verifyLossDto.getPrpLverifyLossExtList());
		}
		// 扩展信息
		if(verifyLossDto.getPrpLregistExtList()!=null){
			this.prpLregistExtService.save(verifyLossDto.getPrpLregistExtList());
		}
		// 进行状态的改变
		updateClaimStatus(verifyLossDto);
	}

	@Override
	public void save(VerifyLossDto verifyLossDto, WorkFlowDto workFlowDto) throws Exception {
		this.save(verifyLossDto);
		this.getWorkFlowService().deal(workFlowDto);
	}

	/**
	 * 保存工作流信息
	 * @param verifyLossDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	@ProcessTask(userId = "verif", businessBeanOffset = 1, businessIdAttributeName = "prpLverifyLoss.id.registNo")
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 0) })
	public void saveBpm(String jbpmNodeType, VerifyLossDto verifyLossDto, WorkFlowDto workFlowDto) throws Exception {
		this.save(verifyLossDto, workFlowDto);

	}

	/**
	 * 保存工作流信息
	 * @param verifyLossDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	@ProcessTask(userId = "verif_three", businessBeanOffset = 1, businessIdAttributeName = "prpLverifyLoss.id.registNo")
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 0) })
	public void saveBpm_verify_three(String jbpmNodeType, VerifyLossDto verifyLossDto, WorkFlowDto workFlowDto) throws Exception {
		this.save(verifyLossDto, workFlowDto);
	}

	/**
	 * 定损删除子表信息
	 * @param registNo //报案号
	 * @param lossItemCode
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(String registNo, String lossItemCode,String noedType) throws SQLException, Exception {
		String condition = " registNo ='" + registNo.trim() + "'  ";
		String statement = null;
		// 删除扩展信息
		statement = " DELETE FROM PrpLregistExt Where " + condition;
		HibernateUtils.executeSql(super.getSession(), statement);
		if ("certa".equals(noedType)) {
			// 删除定核损扩展信息
			statement = " DELETE FROM PrpLverifyLossExt Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
			// 修理费用清单
			statement = " DELETE FROM prpLrepairFee Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
			// 换件项目清单
			statement = " DELETE FROM prpLcomponent Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
			// 定损车辆表
			statement = " DELETE FROM prpLcarLoss Where " + condition + " and LossItemCode='" + lossItemCode + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
		} else if ("wound".equals(noedType)) {
			// 人员伤亡明细信息
			statement = " DELETE FROM prpLperson Where " + condition+ " and personNo='" + lossItemCode + "'";;
			HibernateUtils.executeSql(super.getSession(), statement);
			// 伤情信息表
			statement = " DELETE FROM PrpLpersonWound Where " + condition + " and personNo='" + lossItemCode + "'";;
			HibernateUtils.executeSql(super.getSession(), statement);
		} else if ("propc".equals(noedType)) {
			// 财产核定损明细
			statement = " DELETE FROM prpLprop Where " + condition;
			HibernateUtils.executeSql(super.getSession(), statement);
		} 
//		else if (i == -2) {
//		}
		this.prpLverifyLossService.delete(registNo, lossItemCode,noedType);
	}

	/**
	 * 变更定损的操作状态的方法
	 * @param verifyLossDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(VerifyLossDto verifyLossDto) throws SQLException, Exception {
		PrpLclaimStatus prpLclaimStatus = verifyLossDto.getPrpLclaimStatus();
		if (prpLclaimStatus != null) {
			String condition3 = " BusinessNo='" + prpLclaimStatus.getId().getBusinessNo().trim() + "' and nodeType='" + prpLclaimStatus.getId().getNodeType().trim() + "' AND serialNo=" + prpLclaimStatus.getId().getSerialNo();
			String statement = " DELETE FROM prpLclaimStatus Where " + condition3;
			HibernateUtils.executeSql(super.getSession(), statement);
			this.prpLclaimStatusService.save(prpLclaimStatus);
		}
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}

	public PrpLcarLossService getPrpLcarLossService() {
		return prpLcarLossService;
	}

	public void setPrpLcarLossService(PrpLcarLossService prpLcarLossService) {
		this.prpLcarLossService = prpLcarLossService;
	}

	public PrpLrepairFeeService getPrpLrepairFeeService() {
		return prpLrepairFeeService;
	}

	public void setPrpLrepairFeeService(PrpLrepairFeeService prpLrepairFeeService) {
		this.prpLrepairFeeService = prpLrepairFeeService;
	}

	public PrpLcomponentService getPrpLcomponentService() {
		return prpLcomponentService;
	}

	public void setPrpLcomponentService(PrpLcomponentService prpLcomponentService) {
		this.prpLcomponentService = prpLcomponentService;
	}

	public PrpLpersonService getPrpLpersonService() {
		return prpLpersonService;
	}

	public void setPrpLpersonService(PrpLpersonService prpLpersonService) {
		this.prpLpersonService = prpLpersonService;
	}

	public PrpLpropService getPrpLpropService() {
		return prpLpropService;
	}

	public void setPrpLpropService(PrpLpropService prpLpropService) {
		this.prpLpropService = prpLpropService;
	}

	public PrpLpersonWoundService getPrpLpersonWoundService() {
		return prpLpersonWoundService;
	}

	public void setPrpLpersonWoundService(PrpLpersonWoundService prpLpersonWoundService) {
		this.prpLpersonWoundService = prpLpersonWoundService;
	}

	public PrpLverifyLossExtService getPrpLverifyLossExtService() {
		return prpLverifyLossExtService;
	}

	public void setPrpLverifyLossExtService(PrpLverifyLossExtService prpLverifyLossExtService) {
		this.prpLverifyLossExtService = prpLverifyLossExtService;
	}

	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}

	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}
}
