package com.sinosoft.claim.check.service.spring;

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

import org.hibernate.Session;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schedule.service.facade.ScheduleCertainLossService;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLextId;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckChargeService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckTextService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckExtService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckItemService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckLossService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLdriverService;
import com.sinosoft.claim.schema.service.facade.PrpLextService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdCarLossService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPropService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.reference.DBManager;

public class CheckServiceSpringImpl extends GenericDaoHibernate<CheckDto, String> implements CheckService {
	/** 查勘Service */
	private PrpLcheckService prpLcheckService;
	/** 三者车Service */
	private PrpLthirdPartyService prpLthirdPartyService;
	/** 驾驶员Service */
	private PrpLdriverService prpLdriverService;
	/** 报案文字Service */
	private PrpLregistTextService prpLregistTextService;
	/** 查勘扩展Service */
	private PrpLcheckExtService prpLcheckExtService;
	/** 查勘Service */
	private PrpLcheckLossService prpLcheckLossService;
	/** 人伤跟踪Service */
	private PrpLpersonTraceService prpLpersonTraceService;
	/** 调度标的Service */
	private PrpLscheduleItemService prpLscheduleItemService;
	/** 三者车辆损失部位Service */
	private PrpLthirdCarLossService prpLthirdCarLossService;
	/** 三者财产损失部位表Service */
	private PrpLthirdPropService prpLthirdPropService;
	/** 理赔扩展系统表Service */
	private PrpLextService prpLextService;
	/** 报案信息补充说明Service */
	private PrpLregistExtService prpLregistExtService;
	/** 报案Service */
	private PrpLregistService prpLregistService;
	/** 意健险调查Service */
	private PrpLacciCheckService prpLacciCheckService;
	/** 调查文本信息表Service */
	private PrpLacciCheckTextService prpLacciCheckTextService;
	/** 意健险调查费用表Service */
	private PrpLacciCheckChargeService prpLacciCheckChargeService;
	/** 理赔节点状态表Service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 定损Service */
	private CertainLossService certainLossService;
	/** 工作流Service */
	private WorkFlowService workFlowService;
	private ScheduleCertainLossService scheduleCertainLossService;
	private PrpLscheduleMainWFService prpLscheduleMainWFService;
	private PrpLcheckItemService prpLcheckItemService;
	private PrpLclaimLossService prpLclaimLossService;

	/**
	 * 查勘保存方法
	 * @param checkDto 查勘对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	@Override
	public void save(CheckDto checkDto) throws SQLException, Exception {
		if (checkDto.getPrpLcheck() == null) {
			throw new Exception();
		}
		String checkNo = checkDto.getPrpLcheck().getId().getRegistNo();
		// 首先删除原来的相关数据
		deleteSubInfo(checkNo, checkDto);
		if (checkDto.getAcciCheckDto() == null) {
			prpLcheckService.save(checkDto.getPrpLcheck());
			if (checkDto.getPrpLthirdPartyList() != null) {
				prpLthirdPartyService.save(checkDto.getPrpLthirdPartyList());
			}
			if (checkDto.getPrpLdriverList() != null) {
				prpLdriverService.save(checkDto.getPrpLdriverList());
			}
			if (checkDto.getPrpLregistTextList() != null) {
				prpLregistTextService.save(checkDto.getPrpLregistTextList());
			}
			if (checkDto.getPrpLcheckExtList() != null) {
				prpLcheckExtService.save(checkDto.getPrpLcheckExtList());
			}
			if (checkDto.getPrpLcheckLossList() != null) {
				prpLcheckLossService.save(checkDto.getPrpLcheckLossList());
			}
			if (checkDto.getPrpLpersonTraceList() != null) {
				prpLpersonTraceService.save(checkDto.getPrpLpersonTraceList());
			}
			if("4".equals(checkDto.getPrpLclaimStatus().getStatus())){
				if (checkDto.getPrpLscheduleItemList() != null) {
					prpLscheduleItemService.saveAndDelete(checkDto.getPrpLscheduleItemList());
				}
			}
			if (checkDto.getPrpLthirdCarLossList() != null) {
				prpLthirdCarLossService.save(checkDto.getPrpLthirdCarLossList());
			}
			if (checkDto.getPrpLthirdPropList() != null) {
				prpLthirdPropService.save(checkDto.getPrpLthirdPropList());
			}
			if (checkDto.getPrpLext() != null) {
				prpLextService.save(checkDto.getPrpLext());
			}
			if (checkDto.getPrpLregistExtList() != null) {
				prpLregistExtService.save(checkDto.getPrpLregistExtList());
			}
			if (checkDto.getPrpLregist() != null) {
				prpLregistService.update(checkDto.getPrpLregist());
			}
			if("Q".equals(ConstantCodes.carClassMap.get(checkDto.getPrpLcheck().getRiskCode()))&&checkDto.getPrpLclaimLossList()!=null){
				prpLclaimLossService.save(checkDto.getPrpLclaimLossList());
			}
		} else {
			// 原因：插入调查信息
			if (checkDto.getAcciCheckDto() != null && checkDto.getAcciCheckDto().getPrpLacciCheck() != null) {
				prpLacciCheckService.save(checkDto.getAcciCheckDto().getPrpLacciCheck());
			}
			if (checkDto.getAcciCheckDto() != null && checkDto.getAcciCheckDto().getPrpLacciCheckTextList() != null) {
				prpLacciCheckTextService.save(checkDto.getAcciCheckDto().getPrpLacciCheckTextList());
			}
			// 意健险要保存调查费用信息
			if (checkDto.getAcciCheckDto() != null && checkDto.getAcciCheckDto().getPrpLacciCheckChargeList() != null) {
				prpLacciCheckChargeService.save(checkDto.getAcciCheckDto().getPrpLacciCheckChargeList());
			}
			if (checkDto.getPrpLregist() != null) {
				prpLregistService.update(checkDto.getPrpLregist());
			}
		}
		// 进行状态的改变
		updateClaimStatus(checkDto);
	}

	/**
	 * 变更查勘的操作状态的方法
	 * @param checkDto 查勘对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	@Override
	public void updateClaimStatus(CheckDto checkDto) throws SQLException, Exception {
		// 查询SQL
		if (checkDto.getPrpLclaimStatus() != null) {
			prpLclaimStatusService.save(checkDto.getPrpLclaimStatus());
		}
	}

	/**
	 * 查勘删除
	 * @param checkNo
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void delete(DBManager dbManager, String checkNo) throws SQLException, Exception {
		Session session = getSession();
		String condition = " registNo = '" + StringUtils.rightTrim(checkNo) + "'";
		String condition3 = " BusinessNo='" + checkNo.trim() + "' " + " AND NodeType ='check' ";
		String condition2 = "  businessno ='" + checkNo.trim() + "' " + " AND NodeType ='check' and (nodeStatus='7' or nodeStatus<'4')";

		// 查询SQL
		String statement = ""; // 查询SQL
		statement = " DELETE FROM PrpLregistText Where " + condition + " and TextType = '3'";
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM PrpLcheckExt Where " + condition;
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM PrpLcheckLoss Where " + condition;
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM prpLclaimStatus Where " + condition3;
		HibernateUtils.executeSql(session, statement);
		statement = " DELETE FROM PrpLcheck Where " + condition;
		HibernateUtils.executeSql(session, statement);

		// 如果有工作流的话
		statement = " update swflog set nodestatus='0' Where " + condition2;

		HibernateUtils.executeSql(session, statement);
	}

	/**
	 * 查勘查询方法
	 * @param checkDto 查勘对象
	 * @throws SQLException
	 * @throws Exception
	 * @param checkNo
	 * @return 无
	 */
	@Override
	public CheckDto findByPrimaryKey(String checkNo) throws SQLException, Exception {
		QueryRule mutiKeyRule = QueryRule.getInstance();// 关联主键查询
		QueryRule queryRule1 = QueryRule.getInstance();// 关联主键查询
		mutiKeyRule.addEqual("id.registNo", checkNo);
		queryRule1.addEqual("id.registNo", checkNo);
		queryRule1.addEqual("id.textType", "3");
		CheckDto checkDto = new CheckDto();
		checkDto.setPrpLcheck(prpLcheckService.findPrpLcheck(new PrpLcheckId(checkNo, 1)));
		checkDto.setPrpLthirdPartyList(prpLthirdPartyService.findPrpLthirdParty(mutiKeyRule));
		checkDto.setPrpLthirdCarLossList(prpLthirdCarLossService.findPrpLthirdCarLoss(mutiKeyRule));
		checkDto.setPrpLthirdPropList(prpLthirdPropService.findPrpLthirdProp(mutiKeyRule));
		checkDto.setPrpLdriverList(prpLdriverService.findPrpLdriver(mutiKeyRule));
		checkDto.setPrpLregistTextList(prpLregistTextService.findPrpLregistText(queryRule1));
		checkDto.setPrpLcheckExtList(prpLcheckExtService.findPrpLcheckExt(mutiKeyRule));
		checkDto.setPrpLcheckLossList(prpLcheckLossService.findPrpLcheckLoss(mutiKeyRule));
		checkDto.setPrpLpersonTraceList(prpLpersonTraceService.findPrpLpersonTrace(mutiKeyRule));
		checkDto.setPrpLclaimStatus(prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(checkNo, "check", 0)));
		checkDto.setPrpLregistExtList(prpLregistExtService.findPrpLregistExt(mutiKeyRule));
		QueryRule queryRule = QueryRule.getInstance().addEqual("registNo", checkNo).addAscOrder("id.serialNo");
		checkDto.setPrpLclaimLossList(prpLclaimLossService.findPrpLclaimLoss(queryRule));
		/*
		 * 表prplext字段certiType说明： 1、certiType为 "01"时，车险报案、查勘环节都为"01".
		 * 2、certiType为 "02"时，非车险查勘环节都为"02",报案环节没有用到. 3、certiType为
		 * "03"时，所有险种立案环节为"03".
		 */
		checkDto.setPrpLext(prpLextService.findPrpLext(new PrpLextId(checkNo, "01")));// add

		if (checkDto.getPrpLext() == null) {
			checkDto.setPrpLext(prpLextService.findPrpLext(new PrpLextId(checkNo, "02")));
		}
		// 原因：添加调查信息

		AcciCheckDto acciCheckDto = new AcciCheckDto();
		int time = prpLacciCheckService.findByRegistNoMaxTimes(checkNo);
		String conditions = " RegistNo = '" + checkNo + "' and Times = " + time;
		List<PrpLacciCheck> acciCheckList = prpLacciCheckService.findByConditions(conditions);
		if (acciCheckList != null && acciCheckList.size() > 0) {
			acciCheckDto.setPrpLacciCheck((PrpLacciCheck) acciCheckList.get(0));
			acciCheckDto.setPrpLacciCheckTextList(prpLacciCheckTextService.findByConditions("CheckNo = '" + acciCheckDto.getPrpLacciCheck().getCheckNo() + "'"));
			acciCheckDto.setPrpLregist(prpLregistService.findPrpLregist(checkNo));
			String condition = " checkNo = '" + checkNo + "'";
			acciCheckDto.setPrpLacciCheckChargeList(prpLacciCheckChargeService.findByConditions(condition));
		}
		checkDto.setAcciCheckDto(acciCheckDto);

		return checkDto;
	}

	/**
	 * 查勘删除子表信息
	 * @param checkNo
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteSubInfo(String checkNo, CheckDto checkDto) throws SQLException, Exception {
		String condition = " registNo = '" + StringUtils.rightTrim(checkNo) + "'";
		Session session = getSession();
		String statement = ""; // 查询SQL

		if (checkDto.getAcciCheckDto() == null) {
			// 查勘则删除以下子表(非意健险中一般都是查勘)
			statement = " DELETE FROM PrpLregistExt Where " + condition;
			HibernateUtils.executeSql(session, statement);
			statement = " DELETE FROM PrpLregistText Where " + condition + " and TextType = '3'";
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM PrpLcheckExt Where " + condition;
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM prpLpersonTrace Where " + condition;
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM PrpLcheckLoss Where " + condition;
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM prpLdriver Where " + condition;
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM prpLthirdParty Where " + condition;
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM PrpLcheck Where " + condition;
			HibernateUtils.executeSql(session, statement);

			// 货运险扩展信息
			String condition1 = " certino = '" + StringUtils.rightTrim(checkNo) + "'";
			statement = " DELETE FROM prplext Where " + condition1;
			HibernateUtils.executeSql(session, statement);

			// 如果传过来的有scheduleITem的数据的话
//			if("4".equals(checkDto.getPrpLclaimStatus().getStatus())){
//				if (checkDto.getPrpLscheduleItemList() != null) {
//					prpLscheduleItemService.deleteByRegistNo(StringUtils.rightTrim(checkNo));
//				}
//			}
			statement = " DELETE FROM prplThirdcarloss Where " + condition;
			HibernateUtils.executeSql(session, statement);

			statement = " DELETE FROM prplThirdProp Where " + condition;
			HibernateUtils.executeSql(session, statement);
			//火险增加定损信息
			if("Q".equals(ConstantCodes.carClassMap.get(checkDto.getPrpLcheck().getRiskCode()))){
				statement = " DELETE FROM prpLclaimLoss Where " + condition;
				HibernateUtils.executeSql(session, statement);
			}
		} else {
			// 调查则删除以下子表(意健险中一般称为调查)
			statement = " DELETE FROM PrpLacciCheckText Where CheckNo = '" + checkDto.getAcciCheckDto().getPrpLacciCheck().getCheckNo() + "'";
			HibernateUtils.executeSql(session, statement);
			statement = " DELETE FROM PrpLacciCheckCharge Where CheckNo = '" + checkDto.getAcciCheckDto().getPrpLacciCheck().getCheckNo() + "'";
			HibernateUtils.executeSql(session, statement);
			statement = " DELETE FROM PrpLacciCheck Where CheckNo = '" + checkDto.getAcciCheckDto().getPrpLacciCheck().getCheckNo() + "'";
			HibernateUtils.executeSql(session, statement);
		}
	}

	/**
	 * 查勘查询方法
	 * @param conditions 查询条件
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	@Override
	public List<PrpLcheckItem> findNewScheduleTaskList(String conditions) {
		List<PrpLcheckItem> checkItemList = new ArrayList<PrpLcheckItem>();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		checkItemList = prpLcheckItemService.findPrpLcheckItem(queryRule);
		return checkItemList;
	}

	/**
	 * 查勘调度保存方法
	 * @param scheduleDto 调度对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	@Override
	public void saveSchedule(ScheduleDto scheduleDto) throws SQLException, Exception {
		if (scheduleDto.getPrpLscheduleMainWF() == null) {
			throw new Exception();
		}
		String registNo = scheduleDto.getPrpLscheduleMainWF().getId().getRegistNo();
		int scheduleID = scheduleDto.getPrpLscheduleMainWF().getId().getScheduleID();
		deleteScheduleInfo(registNo, scheduleID);
		if (scheduleDto.getPrpLscheduleMainWF() != null) {
			prpLscheduleMainWFService.save(scheduleDto.getPrpLscheduleMainWF());

			if (scheduleDto.getPrpLscheduleItemList() != null) {
				prpLscheduleItemService.saveAndDelete(scheduleDto.getPrpLscheduleItemList());
			}
			// 还需要保存需要查勘的信息到相应的查勘信息表中
			if (scheduleDto.getPrpLcheckItemList() != null) {
				prpLcheckItemService.save(scheduleDto.getPrpLcheckItemList());
			}
		}
	}

	/**
	 * 保存查勘/带工作流
	 * @param checkDto：自定义查勘对象
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void save(CheckDto checkDto, CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// 创建数据库管理对象
		this.save(checkDto);
		if (certainLossDto.getPrpLverifyLoss() != null && (certainLossDto.getPrpLverifyLoss().getId().getRegistNo() != null && !certainLossDto.getPrpLverifyLoss().getId().getRegistNo().trim().equals(""))) {
			certainLossService.save(certainLossDto);
		}
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 保存查勘
	 * @param checkDto：自定义查勘对象
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void save(CheckDto checkDto, CertainLossDto certainLossDto) throws SQLException, Exception {
		this.save(checkDto);
		certainLossService.save(certainLossDto);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.sinosoft.claim.check.service.facade.CheckService#saveBpm(java.lang
	 * .String, com.sinosoft.claim.check.vo.CheckDto,
	 * com.sinosoft.claim.certainLoss.vo.CertainLossDto,
	 * com.sinosoft.claim.dto.custom.WorkFlowDto) 保存工作流数据
	 */
	@ProcessTask(processId = "claim_05", userId = "check", businessBeanOffset = 0, businessIdAttributeName = "prpLcheck.id.registNo")
	public void saveBpm(CheckDto checkDto, CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.save(checkDto, certainLossDto, workFlowDto);
	}

	/**
	 * 查勘调度删除子表信息
	 * @param scheduleID,registNo
	 * @throws SQLException
	 * @throws Exception
	 */
	private void deleteScheduleInfo(String registNo, int scheduleID) throws SQLException, Exception {
		String condition = " registNo = '" + StringUtils.rightTrim(registNo) + "' and scheduleID=" + scheduleID;
		String statement = ""; // 查询SQL
		statement = " DELETE FROM PrpLcheckItem Where " + condition;
		HibernateUtils.executeSql(getSession(), statement);

		statement = " DELETE FROM PrpLscheduleItem Where " + condition;
		HibernateUtils.executeSql(getSession(), statement);

		statement = " DELETE FROM PrpLscheduleMainWFWF Where " + condition;
		HibernateUtils.executeSql(getSession(), statement);

	}

	/***
	 * 判断备案是否存在查勘讯息
	 * @param checkNo 
	 * @return 
	 */
	@Override
	public boolean isExist(String checkNo) throws Exception {
		boolean flag = true;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", checkNo);
		List<PrpLcheck> resultList = prpLcheckService.findPrpLcheck(queryRule);
		if (resultList.size() < 1) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 按条件从prplcheck表,prplregist表和prplclaimstatus表中查询多条数据
	 * @param conditions String
	 * @param pageNo int
	 * @param pageSize int
	 * @throws Exception
	 * @return Collection 增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Page findByQueryConditions(String conditions, int pageNo, int pageSize) throws Exception {
		// reason:强三查询
		String statement = "Select DISTINCT a.RegistNo,"
				+ "a.PolicyNo, "
				+ "a.Checker1, "
				+ "a.Checker2, "
				+ "a.CheckDate, "
				+ "b.OperateDate, "
				+ "b.RiskCode, "
				+ "b.Status,c.LicenseNo  From (select * from PrpLClaimStatus where NodeType='check') b Right JOIN PrpLcheck a ON a.RegistNo = b.BusinessNo left join prplregist c on c.registno=b.BusinessNo,prplregistrpolicy d where (d.registno=a.registno) and b.nodetype='check' and "
				+ conditions;

		List<PrpLcheck> resultList = new ArrayList<PrpLcheck>();
		List<String> policyNoList = new ArrayList<String>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, pageSize);
		PrpLcheck prpLcheck = new PrpLcheck();
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpLcheck = new PrpLcheck();
			prpLcheck.getId().setRegistNo((String) object[0]);
			prpLcheck.setPolicyNo((String) object[1]);
			prpLcheck.setChecker1((String) object[2]);
			prpLcheck.setChecker2((String) object[3]);
			prpLcheck.setCheckDate(new Date(((Timestamp) object[4]).getTime()));
			prpLcheck.setOperateDate(new Date(((Timestamp) object[5]).getTime()));
			prpLcheck.setRiskCode((String) object[6]);
			prpLcheck.setStatus((String) object[7]);
			// 强三查询
			prpLcheck.setRelatepolicyNo(new ArrayList<String>());
			statement = "select PolicyNo from prplregistrpolicy where RegistNo='" + prpLcheck.getId().getRegistNo() + "'";
			policyNoList = (List<String>) HibernateUtils.findbySql(session, statement);
			for (String policyNo : policyNoList) {
				prpLcheck.getRelatepolicyNo().add(policyNo);
			}
			resultList.add(prpLcheck);
		}
		return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(session, statement), pageSize, resultList);
	}

	// 原因：用於查询意键险信息
	/**
	 * 按条件从prplcheck表,prplregist表和prplclaimstatus表中查询多条数据
	 * @param conditions String
	 * @param pageNo int
	 * @param pageSize int
	 * @throws Exception
	 * @return Collection 增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
	 */
	@Override
	public Page findByQueryConditionsAcci(String conditions, int pageNo, int pageSize) throws Exception {
		conditions = conditions.replaceAll("d.policyNo", "c.policyNo");
		String statement = "Select DISTINCT a.RegistNo," + "a.PolicyNo, " + "a.CheckerCode, " + "a.CheckObject, " + "a.CheckDate, " + "b.OperateDate, " + "b.RiskCode, "
				+ "b.Status,a.CheckNo From (select * from PrpLClaimStatus where NodeType='check') b Right JOIN PrpLacciCheck a ON a.RegistNo = b.BusinessNo left join prplregist c on c.registno=b.BusinessNo where" + conditions;
		List<PrpLcheck> resultList = new ArrayList<PrpLcheck>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, pageSize);
		// List<?> tempList = HibernateUtils.findbySql(session, statement,
		// pageNo, pageSize,PrpLcheck.class);
		PrpLcheck prpLcheck = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpLcheck = new PrpLcheck();
			prpLcheck.getId().setRegistNo((String) object[0]);
			prpLcheck.setPolicyNo((String) object[1]);
			prpLcheck.setChecker1((String) object[2]);
			prpLcheck.setChecker2((String) object[3]);
			prpLcheck.setCheckDate(new Date(((Timestamp) object[4]).getTime()));
			prpLcheck.setOperateDate(new Date(((Timestamp) object[5]).getTime()));
			prpLcheck.setRiskCode((String) object[6]);
			prpLcheck.setStatus((String) object[7]);
			prpLcheck.setCheckNo((String) object[8]);
			resultList.add(prpLcheck);
		}
		return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(session, statement), pageSize, resultList);
	}

	/**
	 * 根据条件查询报案主表信息
	 * @param conditions String
	 * @throws Exception
	 * @return Collection Add By sunhao 2004-08-24 Reason:增加新的查询方法
	 */
	@SuppressWarnings("unchecked")
	public List<PrpLcheck> findByQueryConditionsAcci(String conditions) throws Exception {
		Page page = this.findByQueryConditionsAcci(conditions, 0, 0);
		return page.getResult();
	}

	/**
	 * @param conditions
	 * @return count
	 * @throws Exception
	 */
	public int getCount1(String conditions) throws Exception {
		int count = -1;
		String statement = "select count (DISTINCT a.RegistNo) from (select * from PrpLClaimStatus where NodeType='check') b Right JOIN PrpLcheck a ON a.RegistNo = b.BusinessNo left join prplregist c on c.registno=b.BusinessNo,prplregistrpolicy d where (d.registno=a.registno) and b.nodetype='check' and "
				+ conditions;
		logger.debug(statement);
		count = (int) HibernateUtils.getCountbyCountSql(this.getSession(), statement);
		logger.info("DBPrpLcheck.getCount1() success!");
		return count;
	}

	public int getCount2(String conditions) throws Exception {
		conditions = conditions.replaceAll("d.policyNo", "c.policyNo");
		int count = -1;
		String statement = "select count (DISTINCT a.RegistNo) from (select * from PrpLClaimStatus where NodeType='check') b Right JOIN PrpLacciCheck a ON a.RegistNo = b.BusinessNo left join prplregist c on c.registno=b.BusinessNo where"
				+ conditions;
		logger.debug(statement);
		count = (int) HibernateUtils.getCountbyCountSql(this.getSession(), statement);
		logger.info("DBPrpLcheck.getCount2() success!");
		return count;
	}
	
	/***
	 * 根据条件查询查勘信息
	 * @param conditions
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public List<PrpLcheck> findByQueryConditions(String conditions) throws SQLException, Exception {
		String statement = "Select DISTINCT a.RegistNo,"
				+ "a.PolicyNo, "
				+ "a.Checker1, "
				+ "a.Checker2, "
				+ "a.CheckDate, "
				+ "b.OperateDate, "
				+ "b.RiskCode, "
				+ "b.Status,c.LicenseNo  From (select * from PrpLClaimStatus where NodeType='check') b Right JOIN PrpLcheck a ON a.RegistNo = b.BusinessNo left join prplregist c on c.registno=b.BusinessNo,prplregistrpolicy d where (d.registno=a.registno) and b.nodetype='check' and "
				+ conditions;
		List<PrpLcheck> resultList = new ArrayList<PrpLcheck>();
		List<?> tempList = HibernateUtils.findbySql(super.getSession(), statement);
		if (tempList != null && !tempList.isEmpty()) {
			PrpLcheck prpLcheck = null;
			for (Iterator<?> it = tempList.iterator(); it.hasNext(); resultList.add(prpLcheck)) {
				Object[] object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
				prpLcheck = new PrpLcheck();
				prpLcheck.getId().setRegistNo((String) object[0]);
				prpLcheck.setPolicyNo((String) object[1]);
				prpLcheck.setChecker1((String) object[2]);
				prpLcheck.setChecker2((String) object[3]);
				prpLcheck.setCheckDate(new Date(((Timestamp) object[4]).getTime()));
				prpLcheck.setOperateDate(new Date(((Timestamp) object[5]).getTime()));
				prpLcheck.setRiskCode((String) object[6]);
				prpLcheck.setStatus((String) object[7]);
				prpLcheck.setRelatepolicyNo(new TreeSet<String>());
				statement = "select PolicyNo from prplregistrpolicy where RegistNo='" + (String) object[0] + "'";
				List<?> tempListSub = HibernateUtils.findbySql(super.getSession(), statement);
				for (Iterator<?> itSub = tempListSub.iterator(); itSub.hasNext();) {
					prpLcheck.getRelatepolicyNo().add((String) itSub.next());
				}
				resultList.add(prpLcheck);
			}
		}
		return resultList;
	}
	/***
	 * 查勘新增三者车增加定损分案工作流处理
	 * @param checkDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void saveScheduleAddCertainLoss(CheckDto checkDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		this.scheduleCertainLossService.save(checkDto);
		if (workFlowDto != null) {
			this.workFlowService.deal(workFlowDto);
		}
	}
	/**
	 * 保存到理赔车辆信息表和调度任务标的表中
	 * @param CheckDto：查勘对象DTO
	 * @throws Exception
	 */
	@Override
	public void saveScheduleAddCertainLoss(CheckDto checkDto) {
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpLdriverService getPrpLdriverService() {
		return prpLdriverService;
	}

	public void setPrpLdriverService(PrpLdriverService prpLdriverService) {
		this.prpLdriverService = prpLdriverService;
	}

	public PrpLregistTextService getPrpLregistTextService() {
		return prpLregistTextService;
	}

	public void setPrpLregistTextService(PrpLregistTextService prpLregistTextService) {
		this.prpLregistTextService = prpLregistTextService;
	}

	public PrpLcheckExtService getPrpLcheckExtService() {
		return prpLcheckExtService;
	}

	public void setPrpLcheckExtService(PrpLcheckExtService prpLcheckExtService) {
		this.prpLcheckExtService = prpLcheckExtService;
	}

	public PrpLcheckLossService getPrpLcheckLossService() {
		return prpLcheckLossService;
	}

	public void setPrpLcheckLossService(PrpLcheckLossService prpLcheckLossService) {
		this.prpLcheckLossService = prpLcheckLossService;
	}

	public PrpLpersonTraceService getPrpLpersonTraceService() {
		return prpLpersonTraceService;
	}

	public void setPrpLpersonTraceService(PrpLpersonTraceService prpLpersonTraceService) {
		this.prpLpersonTraceService = prpLpersonTraceService;
	}

	public PrpLscheduleItemService getPrpLscheduleItemService() {
		return prpLscheduleItemService;
	}

	public void setPrpLscheduleItemService(PrpLscheduleItemService prpLscheduleItemService) {
		this.prpLscheduleItemService = prpLscheduleItemService;
	}

	public PrpLthirdCarLossService getPrpLthirdCarLossService() {
		return prpLthirdCarLossService;
	}

	public void setPrpLthirdCarLossService(PrpLthirdCarLossService prpLthirdCarLossService) {
		this.prpLthirdCarLossService = prpLthirdCarLossService;
	}

	public PrpLthirdPropService getPrpLthirdPropService() {
		return prpLthirdPropService;
	}

	public void setPrpLthirdPropService(PrpLthirdPropService prpLthirdPropService) {
		this.prpLthirdPropService = prpLthirdPropService;
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

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}

	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}

	public PrpLacciCheckTextService getPrpLacciCheckTextService() {
		return prpLacciCheckTextService;
	}

	public void setPrpLacciCheckTextService(PrpLacciCheckTextService prpLacciCheckTextService) {
		this.prpLacciCheckTextService = prpLacciCheckTextService;
	}

	public PrpLacciCheckChargeService getPrpLacciCheckChargeService() {
		return prpLacciCheckChargeService;
	}

	public void setPrpLacciCheckChargeService(PrpLacciCheckChargeService prpLacciCheckChargeService) {
		this.prpLacciCheckChargeService = prpLacciCheckChargeService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public CertainLossService getCertainLossService() {
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public ScheduleCertainLossService getScheduleCertainLossService() {
		return scheduleCertainLossService;
	}

	public void setScheduleCertainLossService(ScheduleCertainLossService scheduleCertainLossService) {
		this.scheduleCertainLossService = scheduleCertainLossService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpLscheduleMainWFService getPrpLscheduleMainWFService() {
		return prpLscheduleMainWFService;
	}

	public void setPrpLscheduleMainWFService(PrpLscheduleMainWFService prpLscheduleMainWFService) {
		this.prpLscheduleMainWFService = prpLscheduleMainWFService;
	}

	public PrpLcheckItemService getPrpLcheckItemService() {
		return prpLcheckItemService;
	}

	public void setPrpLcheckItemService(PrpLcheckItemService prpLcheckItemService) {
		this.prpLcheckItemService = prpLcheckItemService;
	}

	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

}
