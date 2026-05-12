package com.sinosoft.claim.schema.service.facade;
/**
 * 任務定義接口
 * @author 理赔组
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.UtiTask;

public interface UtiTaskService {
	
	/**
	 * 任務定義信息信息
	 * @param utiTask ：传入的任務定義
	 */
	public void save(UtiTask utiTask) throws Exception;
	
	/**
	 * 保存任務定義信息
	 * @param list  :传入的任務定義信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<UtiTask> list) throws Exception;
	
	/**
	 * 删除任務定義信息
	 * @param policyNo ：传入的任務定義编号
	 */
	public void delete(String taskcode) throws Exception;

	/**
	 * 更新任務定義信息
	 * @param UtiTask :传入需要更新的任務定義
	 */
	public void update(UtiTask utiTask) throws Exception;

	/**
	 * 根据任務定義编号查询出任務定義信息
	 * @param policyNo ：传入的任務定義编号
	 * @return 返回任務定義
	 */
	public UtiTask findUtiTask(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的任務定義页面信息
	 */
	public Page findUtiTask(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<UtiTask> findUtiTask(QueryRule queryRule) throws Exception;
}
