package com.sinosoft.claim.schema.service.facade;

/**
 * 機構員工崗位差異功能權限接口
 * @author 理赔组
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.UtiUserGradeTask;
import com.sinosoft.claim.schema.model.UtiUserGradeTaskId;

public interface UtiUserGradeTaskService {

	/**
	 * 保存機構員工崗位差異功能權限信息
	 * @param UtiUserGradeTask ：传入的機構員工崗位差異功能權限
	 */
	public void save(UtiUserGradeTask utiUserGradeTask) throws Exception;

	/**
	 * 機構員工崗位差異功能權限信息
	 * @param list :传入的機構員工崗位差異功能權限信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<UtiUserGradeTask> list) throws Exception;

	/**
	 * 删除機構員工崗位差異功能權限信息
	 * @param UtiUserGradeTaskId ：传入的機構員工崗位差異功能權限编号
	 */
	public void delete(UtiUserGradeTaskId utiUserGradeTaskId) throws Exception;

	/**
	 * 更新機構員工崗位差異功能權限信息
	 * @param UtiUserGradeTask :传入需要更新的機構員工崗位差異功能權限
	 */
	public void update(UtiUserGradeTask utiUserGradeTask) throws Exception;

	/**
	 * 根据機構員工崗位差異功能權限编号查询出機構員工崗位差異功能權限信息
	 * @param UtiUserGradeTaskId ：传入的機構員工崗位差異功能權限编号
	 * @return 返回機構員工崗位差異功能權限
	 */
	public UtiUserGradeTask findUtiUserGradeTask(UtiUserGradeTaskId utiUserGradeTaskId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的機構員工崗位差異功能權限页面信息
	 */
	public Page findUtiUserGradeTask(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	public List<UtiUserGradeTask> findUtiUserGradeTask(QueryRule queryRule) throws Exception;
	/**
	 * 检查这个用户是否配置了这个权限,true有这个权限，false没有这个权限
	 * @param userDto
	 * @param taskCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkPower(UserDto userDto,String taskCode)throws Exception;
}
