package com.sinosoft.claim.schedule.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.Collection;

import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 *  调度逻辑分发
 * <p>Title: 车险理赔调度</p>
 * <p>Description: 车险理赔调度facade</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Sinosoft</p>
 * @author 中科软
 * @version 1.0
 */
public interface ScheduleService
{
	  /**
	   * 保存理赔调度
	   * @param scheduleDto：自定义理赔调度对象
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void save(ScheduleDto scheduleDto) throws SQLException,Exception;
	  /**
	   * 保存理赔调度带工作流
	   * @param scheduleDto：自定义理赔调度对象
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void save(ScheduleDto scheduleDto,WorkFlowDto workFlowDto) throws SQLException,Exception;
	  
	  /**
	   * 获得理赔调度
	   * @param  scheduleNo	
	   * @return 自定义理赔调度对象
	   * @throws SQLException
	   * @throws Exception
	   */
	public ScheduleDto findByPrimaryKey(int scheduleID, String registNo) throws SQLException, Exception;

	/**
	 * 根据主键查询调度信息 理赔调度任务处理查询方法 @param scheduleDto 理赔调度任务处理对象 @throws
	 * SQLException @throws Exception @return 无
	 */
	public ScheduleDto findByRegistNo(int scheduleID, String registNo) throws SQLException, Exception;

	public void saveSmcInfo(ScheduleDto scheduleDto) throws SQLException, Exception;
	  
	  /**
	   * 删除理赔调度
	   * @param  scheduleNo
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void delete(int scheduleID,String registNo) throws SQLException,Exception;

	  /**
	   * 判断理赔调度通知号是否存在
	   * @param scheduleNo
	   * @return 是/否
	   * @throws SQLException
	   * @throws Exception
	   */
	  public boolean isExist(int scheduleID,String registNo) throws SQLException,Exception;

	  /**
	   * 根据条件查询理赔调度表信息
	   * @param conditions String
	   * @throws Exception
	   * @return Collection
	   */
	  public Collection<?> findByConditions(String conditions) throws Exception;

	/**
	   * 取得调度号
	   * @param registNo
	   * @return scheduleID
	   * @throws SQLException
	   * @throws Exception
	   */
	  public int getNo(String registNo) throws SQLException,Exception;
	  
	  /**
		 * 获得案件调度Item处理信息
		 * @param conditions：查询条件
		 * @return 案件调度Item处理对象
		 * @throws Exception
		 */
		public Collection<PrpLscheduleItem> findItemByConditions(String conditions) throws SQLException, Exception;

	 /**
	   * 保存理赔调度改派带工作流
	   * @param scheduleDto：自定义理赔调度对象
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void changeSave(ScheduleDto scheduleDto,WorkFlowDto workFlowDto) throws SQLException,Exception;
	 
	  /**
	   * 调度将案件提交双代节点,申请双代处理   
	   * @param workFlowDto：工作作流对象
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void applyCommiCase(ScheduleDto scheduleDto,WorkFlowDto workFlowDto) throws SQLException,Exception;
	  //reason:增加分页查询
	  public Page findByQueryConditions(String conditions,int pageNo,int recordPerPage, String scheduleType) throws Exception;
		/**
		 * 获得调度查询信息
		 * @param  conditions：查询条件
		 * @return page
		 */
	  public Page findByQueryConditions(String conditions, int pageNo, int recordPerPage);
	/**
	 * 查找符合条件的个数(scheduleMainWF表)
	 * @param conditon
	 * @return
	 * @throws Exception
	 */
	public int findScheduleItemCountByConditon(String conditions)throws Exception;
	/**
	 * 查找符合条件的个数(scheduleMainWF表)
	 * @param conditon
	 * @return
	 * @throws Exception
	 */
	public Page findScheduleItemCountByConditon(String conditions,int PageNo,int RecordPerPage);
	/**
	 * 查找符合条件的个数(scheduleMainWF表)
	 * @param conditon
	 * @return
	 * @throws Exception
	 */
	public int findScheduleMainWFCountByConditon(String condition);
	/**
	 * 查找符合备案条件的个数
	 * @param conditions
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public Page findForRegistConditions(String conditions,int pageNo,int recordPerPage);
	/**
	 * 保存调度信息带工作流的处理过程
	 * @param JbpmDto
	 * @param scheduleDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveBpm(JbpmDto JbpmDto,ScheduleDto scheduleDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 根据条件查询查勘调度表
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public PrpLscheduleMainWF findScheduleMainByConditions(String conditions)throws Exception;
}
