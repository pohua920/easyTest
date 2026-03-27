package com.sinosoft.claim.schedule.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWFId;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLcheckItemService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 报案数据库管理对象
 * <p>
 * Title: 车险理赔报案数据管理
 * </p>
 * <p>
 * Description: 车险理赔报案数据管理
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
public class ScheduleServiceSpringImpl extends GenericDaoHibernate<ScheduleDto, String> implements ScheduleService {
	private PrpLregistService prpLregistService;
	private PrpLscheduleMainWFService prpLscheduleMainWFService;
	private PrpLclaimStatusService prpLclaimStatusService;
	private PrpLscheduleItemService prpLscheduleItemService;
	private PrpLregistExtService prpLregistExtService;
	private PrpLcheckItemService PrpLcheckItemService;
	private CodeService codeService;
	private SwfLogService swfLogService;
	private WorkFlowService workFlowService;

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	/**
	 * 报案保存方法
	 * @param registDto 报案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void save(ScheduleDto scheduleDto) throws SQLException, Exception {

		// 业务操作

		// 判断是哪种类别的保存的情况
		String saveType = "";
		if(scheduleDto.getPrpLscheduleMainWF()!=null){
			saveType = scheduleDto.getPrpLscheduleMainWF().getSaveType();
		}else{
			saveType = "GETBACKEDIT";
		}

		if (saveType.equals("GETBACKEDIT")) {
			getBackUpdate(scheduleDto);
		} else {
			if (saveType.equals("cancel")) {
			} else {
				insert(scheduleDto);
			}
		}

	}

	/**
	 * 变更调度的操作状态的方法
	 * @param scheduleDto 调度对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(ScheduleDto scheduleDto) throws SQLException, Exception {
		// 示例未完成
		if (scheduleDto.getPrpLclaimStatus() != null) {
			this.prpLclaimStatusService.save(scheduleDto.getPrpLclaimStatus());
		}
	}

	/**
	 * 理赔调度任务处理删除子表信息
	 * @param fcoClaimNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(ScheduleDto scheduleDto) throws SQLException, Exception {
		// 示例未完成
		String statement = "";
		// String scheduleType = scheduleDto.getScheduleType();
		if (scheduleDto.getPrpLscheduleMainWF() != null) {
			// 删除扩展信息
			String condition = " registNo = '" + StringUtils.rightTrim(scheduleDto.getPrpLscheduleMainWF().getId().getRegistNo()) + "'";
			statement = " DELETE FROM PrpLregistExt Where " + condition;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			HibernateUtils.executeSql(session, statement);
			// 先删除scheduleItem的内容
		}
	}

	/**
	 * 状态删除
	 * @param fcoScheduleNewNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(String registNo) throws SQLException, Exception {
		String statement = "";
		// String condition = " registNo = '" + StringUtils.rightTrim(registNo)
		// + "'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		HibernateUtils.executeSql(session, statement);
	}

	/**
	 * 根据主键查询调度信息
	 * 理赔调度任务处理查询方法 @param scheduleDto 理赔调度任务处理对象 @throws SQLException @throws
	 * Exception @return 无
	 */
	public ScheduleDto findByPrimaryKey(int scheduleID, String registNo) throws SQLException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		queryRule.addEqual("id.scheduleID", scheduleID);
		
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setPrpLscheduleMainWF(this.prpLscheduleMainWFService.findPrpLscheduleMainWF(scheduleID, registNo));
		scheduleDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo, "sched", scheduleID)));
		QueryRule queryRule2 = QueryRule.getInstance();
		queryRule2.addEqual("id.registNo", registNo);
		queryRule2.addAscOrder("nextNodeNo");
		queryRule2.addAscOrder("id.itemNo");
		scheduleDto.setPrpLscheduleItemList((ArrayList<PrpLscheduleItem>) this.prpLscheduleItemService.findPrpLscheduleItem(queryRule2));
		scheduleDto.setPrpLcheckItemList((ArrayList<PrpLcheckItem>) this.PrpLcheckItemService.findPrpLcheckItem(queryRule));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		scheduleDto.setPrpLregistExtList((ArrayList<PrpLregistExt>) this.prpLregistExtService.findPrpLregistExt(queryRule));
		return scheduleDto;
	}
	/**
	 * 根据主键查询调度信息
	 * 理赔调度任务处理查询方法 @param scheduleDto 理赔调度任务处理对象 @throws SQLException @throws
	 * Exception @return 无
	 */
	public ScheduleDto findByRegistNo(int scheduleID, String registNo) throws SQLException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		queryRule.addEqual("id.scheduleID", scheduleID);
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setPrpLscheduleMainWF(this.prpLscheduleMainWFService.findPrpLscheduleMainWF(scheduleID, registNo));
		scheduleDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo, "sched", scheduleID)));
		
		QueryRule queryRule2 = QueryRule.getInstance();
		queryRule2.addEqual("id.registNo", registNo);
		queryRule2.addAscOrder("nextNodeNo");
		queryRule2.addAscOrder("id.itemNo");
		scheduleDto.setPrpLscheduleItemList((ArrayList<PrpLscheduleItem>) this.prpLscheduleItemService.findPrpLscheduleItem(queryRule2));
		scheduleDto.setPrpLcheckItemList((ArrayList<PrpLcheckItem>) this.PrpLcheckItemService.findPrpLcheckItem(queryRule));
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		scheduleDto.setPrpLregistExtList((ArrayList<PrpLregistExt>) this.prpLregistExtService.findPrpLregistExt(queryRule));
		return scheduleDto;
	}

	@Override
	public void save(ScheduleDto scheduleDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// 创建数据库管理对象
		save(scheduleDto);
		if (scheduleDto.getSmSendSMListList() != null && scheduleDto.getSmSendSMListList().size() > 0) {
			saveSmcInfo(scheduleDto);
		}
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}

	}

	@ProcessTask(processId = "claim_05",userId = "sched", businessBeanOffset = 0, businessIdAttributeName = "businessNo")
	@TaskParams(taskParams = { @TaskParam(key = "threeCar", paramValueBeanOffset = 0, paramValueAttributeName = "threeCar"), 
			@TaskParam(key = "wound", paramValueBeanOffset = 0, paramValueAttributeName = "wound"),
			@TaskParam(key = "propc", paramValueBeanOffset = 0, paramValueAttributeName = "propc"), 
			@TaskParam(key = "nodeType", paramValueBeanOffset = 0, paramValueAttributeName = "nodeType"),
			@TaskParam(key = "check", paramValueBeanOffset = 0, paramValueAttributeName = "check"),
			@TaskParam(key = "nodeListThree", paramValueBeanOffset = 0, paramValueAttributeName = "nodeListThree")})
	public void saveBpm(JbpmDto jbpmDto, ScheduleDto scheduleDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		save(scheduleDto, workFlowDto);
	}

	@Override
	public void saveSmcInfo(ScheduleDto scheduleDto) throws SQLException, Exception {

	}

	@Override
	public void delete(int scheduleID, String registNo) throws SQLException, Exception {

	}

	@Override
	public boolean isExist(int scheduleID, String registNo) throws SQLException, Exception {
		return false;
	}

	@Override
	public Collection<?> findByConditions(String conditions) throws Exception {
		return null;
	}

	@Override
	public int getNo(String registNo) throws SQLException, Exception {
		return 0;
	}

	@Override
	public void changeSave(ScheduleDto scheduleDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
	}

	@Override
	public void applyCommiCase(ScheduleDto scheduleDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
	}

	/**
	 * 调用位置：调度任务处理->调度查询
	 */
	@Override
	public Page findByQueryConditions(String conditions, int pageNo, int pageSize, String scheduleType) throws Exception {
		if (scheduleType.equals("sched")) {
			String statement = "Select ScheduleID," + " RegistNo," + " SurveyNo," + " ClaimComCode," + " RiskCode," + " PolicyNo," + " OperatorCode," + " InputDate," + " InputHour," + " ScheduleArea," + " ScheduleMoreFlag," + " ScheduleFlag,"
					+ " ScheduleObjectID," + " ScheduleObjectName," + " ScheduleType," + " CheckInputDate," + " CheckOperatorCode," + " CheckFlag," + " CheckInfo," + " Flag," + " CheckSite," + " NextHandlerCode," + " NextHandlerName,"
					+ " NextNodeNo," + " InputMinute," + " ScheduleStatus," + " CommiItemFlag From PrpLscheduleMainWF Where " + conditions;
			StringBuffer buffer = new StringBuffer(200);
			buffer.append(statement);
			List<PrpLscheduleMainWF> resultList = new ArrayList<PrpLscheduleMainWF>();
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, pageSize);
			PrpLscheduleMainWF prpLscheduleMainWF = null;
			for (int i = 0; i < tempList.size(); i++) {
				Object[] object = (Object[]) tempList.get(i);
				prpLscheduleMainWF = new PrpLscheduleMainWF();
				PrpLscheduleMainWFId prpLscheduleMainWFId = new PrpLscheduleMainWFId();
				prpLscheduleMainWFId.setRegistNo((String) object[1]);
				prpLscheduleMainWFId.setScheduleID(new Integer(String.valueOf(object[0])));
				prpLscheduleMainWF.setId(prpLscheduleMainWFId);
				prpLscheduleMainWF.setSurveyNo(new Integer(String.valueOf(object[2])));
				prpLscheduleMainWF.setClaimComCode((String) object[3]);
				prpLscheduleMainWF.setRiskCode((String) object[4]);
				prpLscheduleMainWF.setPolicyNo((String) object[5]);
				prpLscheduleMainWF.setOperatorCode((String) object[6]);
				prpLscheduleMainWF.setInputDate((Date) object[7]);
				prpLscheduleMainWF.setInputHour(new Integer(String.valueOf(object[8])));
				prpLscheduleMainWF.setScheduleArea(new Integer(String.valueOf(object[9])));
				prpLscheduleMainWF.setScheduleMoreFlag((String) object[10]);
				prpLscheduleMainWF.setScheduleFlag((String) object[11]);
				prpLscheduleMainWF.setScheduleObjectID((String) object[12]);
				prpLscheduleMainWF.setScheduleObjectName((String) object[13]);
				prpLscheduleMainWF.setScheduleType((String) object[14]);
				prpLscheduleMainWF.setOperatorName(codeService.translateCode(ConstantCodes.CodeConfig.USERCODE, (String) object[6], ConstantCodes.Language.CHINESE));
				prpLscheduleMainWF.setCheckOperatorName(codeService.translateCode(ConstantCodes.CodeConfig.USERCODE, (String) object[16], ConstantCodes.Language.CHINESE));
				if (object[15] != null) {
					prpLscheduleMainWF.setCheckInputDate(new Date(((Timestamp) object[15]).getTime()));
				} else {
					prpLscheduleMainWF.setCheckInputDate(null);
				}
				prpLscheduleMainWF.setCheckOperatorCode((String) object[16]);
				prpLscheduleMainWF.setCheckFlag((String) object[17]);
				prpLscheduleMainWF.setCheckInfo((String) object[18]);
				prpLscheduleMainWF.setFlag((String) object[19]);
				prpLscheduleMainWF.setCheckSite((String) object[20]);
				prpLscheduleMainWF.setNextHandlerCode((String) object[21]);
				prpLscheduleMainWF.setNextHandlerName((String) object[22]);
				prpLscheduleMainWF.setNextNodeNo((String) object[23]);
				prpLscheduleMainWF.setInputMinute((BigDecimal) object[24]);
				prpLscheduleMainWF.setScheduleStatus((String) object[25]);
				prpLscheduleMainWF.setCommiItemFlag((String) object[26]);
				resultList.add(prpLscheduleMainWF);
			}
			return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(session, statement), pageSize, resultList);
		} else {
			String sql = "select * from PrpLscheduleItem where "+conditions;
			Page page = HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLscheduleItem.class);
			return page;
		}
	}

	/**
	 * 理赔调度任务处理任务取回並保存的方法
	 * @param scheduleDto 理赔调度任务处理任务取回並保存的对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void getBackUpdate(ScheduleDto scheduleDto) throws SQLException, Exception {
		if (scheduleDto.getPrpLscheduleMainWF() != null) {
//			PrpLscheduleMainWFId prpLscheduleMainWFId = new PrpLscheduleMainWFId();
//			prpLscheduleMainWFId.setRegistNo(scheduleDto.getPrpLscheduleMainWF().getId().getRegistNo());
//			prpLscheduleMainWFId.setScheduleID(scheduleDto.getPrpLscheduleMainWF().getId().getScheduleID());
			prpLscheduleMainWFService.saveOrUpdate(scheduleDto.getPrpLscheduleMainWF());
//			prpLscheduleMainWFService.delete(prpLscheduleMainWFId);
//			prpLscheduleMainWFService.save(scheduleDto.getPrpLscheduleMainWF());
		}
		if (scheduleDto.getPrpLscheduleItem() != null) {
//			PrpLscheduleItemId prpLscheduleItemId = new PrpLscheduleItemId();
//			prpLscheduleItemId.setItemNo(scheduleDto.getPrpLscheduleItem().getId().getItemNo());
//			prpLscheduleItemId.setRegistNo(scheduleDto.getPrpLscheduleItem().getId().getRegistNo());
//			prpLscheduleItemId.setScheduleID(scheduleDto.getPrpLscheduleItem().getId().getScheduleID());
			prpLscheduleItemService.saveOrUpdate(scheduleDto.getPrpLscheduleItem());
//			prpLscheduleItemService.delete(prpLscheduleItemId);
//			prpLscheduleItemService.save(scheduleDto.getPrpLscheduleItem());
		}
		// 发送短信 begin
		if (scheduleDto.getSmcComCodeInfoList() != null) {
			// new
			// DBSMCComCodeInfo(dbManager).insertAll(scheduleDto.getSmcComCodeInfoDtoList());
		}
		// 发送短信 end

	}

	/**
	 * 理赔调度任务处理保存方法
	 * @param scheduleDto 理赔调度任务处理对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void insert(ScheduleDto scheduleDto) throws SQLException, Exception {
		if (scheduleDto.getPrpLscheduleMainWF() == null) {
			throw new Exception();
		}
		// 首先删除原来的相关数据
		deleteSubInfo(scheduleDto);
		// String scheduleType =
		// scheduleDto.getPrpLscheduleMainWF().getSaveType();
		// 只有定损，没有查勘的情况下，所使用的保存方式
		if (scheduleDto.getPrpLscheduleMainWF() != null) {
			// 双代案件,出险地调度首次提交调度保存时,记录此时间为双代提交时间
			if ("1".equals(scheduleDto.getPrpLscheduleMainWF().getCommiFlag())) {
				String condition = " CommiTime='" + new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString() + "'" + " Where RegistNo ='" + scheduleDto.getPrpLscheduleMainWF().getId().getRegistNo() + "'" + " and CommiTime is null ";
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				HibernateUtils.executeSql(session, condition);
			}
			// 需求：在報案環節不管是否選擇了需不需要現場處理都有－－－查堪調度 ，屏蔽以下if()
			// -報案選擇不需要現場處理時也執行UPDAT。。。。
			this.prpLscheduleMainWFService.update(scheduleDto.getPrpLscheduleMainWF());
		}
		if (scheduleDto.getPrpLscheduleItemList() != null) {
			this.prpLscheduleItemService.saveAndDelete(scheduleDto.getPrpLscheduleItemList());
		}
		// 扩展信息
		if (scheduleDto.getPrpLregistExtList() != null) {
			this.prpLregistExtService.save(scheduleDto.getPrpLregistExtList());
		}
		// 进行节点状态的改变
		updateClaimStatus(scheduleDto);
		// 进行新案件提示表的案件状态改变

	}

	/**
	 * 获得案件调度Item处理信息
	 * @param conditions：查询条件
	 * @return 案件调度Item处理对象
	 * @throws Exception
	 */
	@Override
	public Collection<PrpLscheduleItem> findItemByConditions(String conditions) throws SQLException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return prpLscheduleItemService.findPrpLscheduleItem(queryRule);
	}

	@Override
	/**
	 * "调度任务处理"->"调度改派" 的数据查询 "调度任务处理"->"查看处理情况查询" 的数据查询 "调度任务处理"->定损任务注销。
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int recordPerPage) {
		String sql = "select * from swflog where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, recordPerPage, SwfLog.class);
	}

	@Override
	public int findScheduleItemCountByConditon(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		List<PrpLscheduleItem> list = this.prpLscheduleItemService.findPrpLscheduleItem(queryRule);
		return list.size();
	}

	@Override
	public int findScheduleMainWFCountByConditon(String condition) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(condition);
		List<PrpLscheduleMainWF> list = null;
		try {
			list = this.prpLscheduleMainWFService.findPrpLscheduleMainWF(queryRule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size();
	}

	@Override
	public Page findScheduleItemCountByConditon(String conditions, int PageNo, int RecordPerPage) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = null;
		try {
			page = this.prpLscheduleItemService.findPrpLscheduleItem(queryRule, PageNo, RecordPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public Page findForRegistConditions(String conditions, int pageNo, int recordPerPage) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = null;
		try {
			page = this.prpLscheduleMainWFService.findPrpLscheduleMainWF(queryRule, pageNo, recordPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 根据条件查询查勘调度表
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public PrpLscheduleMainWF findScheduleMainByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		List<PrpLscheduleMainWF> list = prpLscheduleMainWFService.findPrpLscheduleMainWF(queryRule);
		PrpLscheduleMainWF prpLscheduleMainWF = null;
		if(list!=null&&list.size()>0){
			prpLscheduleMainWF = list.get(0);
		}
		return prpLscheduleMainWF;
	}
	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLscheduleMainWFService getPrpLscheduleMainWFService() {
		return prpLscheduleMainWFService;
	}

	public void setPrpLscheduleMainWFService(PrpLscheduleMainWFService prpLscheduleMainWFService) {
		this.prpLscheduleMainWFService = prpLscheduleMainWFService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLscheduleItemService getPrpLscheduleItemService() {
		return prpLscheduleItemService;
	}

	public void setPrpLscheduleItemService(PrpLscheduleItemService prpLscheduleItemService) {
		this.prpLscheduleItemService = prpLscheduleItemService;
	}

	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}

	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}

	public PrpLcheckItemService getPrpLcheckItemService() {
		return PrpLcheckItemService;
	}

	public void setPrpLcheckItemService(PrpLcheckItemService prpLcheckItemService) {
		PrpLcheckItemService = prpLcheckItemService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
