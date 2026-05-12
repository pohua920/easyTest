package com.sinosoft.claim.check.service.facade;

/**
 * 查勘处理接口
 * @author 中科软
 */
import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.reference.DBManager;

public interface CheckService {

	/**
	 * 查勘保存方法
	 * @param checkDto 查勘对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void save(CheckDto checkDto) throws SQLException, Exception;

	/**
	 * 保存查勘/带工作流
	 * @param checkDto：自定义查勘对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CheckDto checkDto, CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 保存查勘
	 * @param checkDto：自定义查勘对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(CheckDto checkDto, CertainLossDto certainLossDto) throws SQLException, Exception;

	/**
	 * 变更查勘的操作状态的方法
	 * @param checkDto 查勘对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(CheckDto checkDto) throws SQLException, Exception;

	/**
	 * 查勘查询方法
	 * @param checkNo 查勘号码
	 * @return 查勘对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public CheckDto findByPrimaryKey(String checkNo) throws SQLException, Exception;

	/**
	 * 判断查勘通知号是否存在
	 * @param checkNo
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String checkNo) throws Exception;
	/**
	 * 查勘查询方法
	 * @param conditions 查询条件
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public List<PrpLcheckItem> findNewScheduleTaskList(String conditions);

	/**
	 * 根据条件查询报案主表信息
	 * @param conditions String
	 * @throws Exception
	 * @return Collection Add By sunhao 2004-08-24 Reason:增加新的查询方法
	 */
	public List<PrpLcheck> findByQueryConditionsAcci(String conditions) throws SQLException, Exception;

	/***
	 * 根据条件查询查勘信息
	 * @param conditions
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<PrpLcheck> findByQueryConditions(String conditions) throws SQLException, Exception;

	/**
	 * 按条件从prplcheck表,prplregist表和prplclaimstatus表中查询多条数据
	 * @param conditions String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return Collection Modify By sunhao 2004-08-24
	 *         Reason:增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int pageSize) throws Exception;

	/***
	 * 查勘新增三者车增加定损分案工作流处理
	 * @param checkDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveScheduleAddCertainLoss(CheckDto checkDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 保存到理赔车辆信息表和调度任务标的表中
	 * @param CheckDto：查勘对象DTO
	 * @throws Exception
	 */
	public void saveScheduleAddCertainLoss(CheckDto checkDto);

	/***
	 * 分页查询意键险信息
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByQueryConditionsAcci(String conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 查勘删除
	 * @param checkNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void delete(DBManager dbManager, String checkNo) throws SQLException, Exception;

	/**
	 * 查勘调度保存方法
	 * @param scheduleDto 调度对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void saveSchedule(ScheduleDto scheduleDto) throws SQLException, Exception;

	/**
	 * 保存工作流数据
	 * @param registNo
	 * @param checkDto
	 * @param certainLossDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception 
	 */
	public void saveBpm(CheckDto checkDto, CertainLossDto certainLossDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
}
