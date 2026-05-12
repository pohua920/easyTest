package com.sinosoft.claim.schema.service.facade;
/**
 * 人伤跟踪修改轨迹接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLpersonTraceLog;
import com.sinosoft.claim.schema.model.PrpLpersonTraceLogId;

public interface PrpLpersonTraceLogService {
	
	/**
	 * 保存人伤跟踪修改轨迹信息
	 * @param prpLpersonTraceLog ：传入的人伤跟踪修改轨迹
	 */
	public void save(PrpLpersonTraceLog prpLpersonTraceLog) throws Exception;
	
	/**
	 * 人伤跟踪修改轨迹信息
	 * @param list  :传入的人伤跟踪修改轨迹信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLpersonTraceLog> list) throws Exception;
	
	/**
	 * 删除人伤跟踪修改轨迹信息
	 * @param prpLpersonTraceLogId ：传入的人伤跟踪修改轨迹编号
	 */
	public void delete(PrpLpersonTraceLogId prpLpersonTraceLogId) throws Exception;

	/**
	 * 更新人伤跟踪修改轨迹信息
	 * @param prpLpersonTraceLog :传入需要更新的人伤跟踪修改轨迹
	 */
	public void update(PrpLpersonTraceLog prpLpersonTraceLog) throws Exception;

	/**
	 * 根据人伤跟踪修改轨迹编号查询出人伤跟踪修改轨迹信息
	 * @param prpLpersonTraceLogId ：传入的人伤跟踪修改轨迹编号
	 * @return 返回人伤跟踪修改轨迹
	 */
	public PrpLpersonTraceLog findPrpLpersonTraceLog(PrpLpersonTraceLogId prpLpersonTraceLogId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人伤跟踪修改轨迹页面信息
	 */
	public Page findPrpLpersonTraceLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  人伤跟踪修改轨迹页面信息的集合
	 * @param queryRule 查询对象
	 * @return 包含的  人伤跟踪修改轨迹页面信息的集合
	 */
	public List<PrpLpersonTraceLog> findPrpLpersonTraceLog(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据人伤跟踪修改轨迹编号查询出人伤跟踪修改轨迹信息
	 * @param certiNo ：传入的人伤跟踪修改轨迹编号
	 * @return 返回人伤跟踪修改轨迹
	 */
	public PrpLpersonTraceLog findPrpLpersonTraceLog(String certiNo) throws Exception;
}
