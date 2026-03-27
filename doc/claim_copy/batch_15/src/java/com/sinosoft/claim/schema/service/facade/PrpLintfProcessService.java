package com.sinosoft.claim.schema.service.facade;
/**
 * 系统交互处理信息表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLintfProcess;

public interface PrpLintfProcessService {
	
	/**
	 * PRPLINTFPROCESS信息
	 * @param PrpLintfProcess ：传入的系统交互处理信息
	 */
	public void save(PrpLintfProcess prpLintfProcess) throws Exception;
	
	/**
	 * 保存系统交互处理信息
	 * @param list  :传入的系统交互处理信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLintfProcess> list) throws Exception;
	
	/**
	 * 删除系统交互处理信息
	 * @param policyNo ：传入的系统交互处理信息
	 */
	public void delete(String businessNo) throws Exception;

	/**
	 * 更新系统交互处理信息
	 * @param PrpLintfProcess :传入需要更新的系统交互处理信息
	 */
	public void update(PrpLintfProcess prpLintfProcess) throws Exception;
	

	/**
	 * 根据系统交互处理信息编号查询出保单系统交互处理信息
	 * @param policyNo ：传入的系统交互处理信息编号
	 * @return 返回系统交互处理信息
	 */
	public PrpLintfProcess findPrpLintfProcess(String businessNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的系统交互处理信息页面信息
	 */
	public Page findPrpLintfProcess(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取系统交互处理信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 系统交互处理信息 的集合
	 */
	public List<PrpLintfProcess> findPrpLintfProcess(QueryRule queryRule) throws Exception;
	/***
	 * 送收付并记录交互讯息
	 * @param prpLinrfProcess
	 * @throws Exception
	 */
	public void logForReplevy(PrpLintfProcess prpLinrfProcess) throws Exception;
}
