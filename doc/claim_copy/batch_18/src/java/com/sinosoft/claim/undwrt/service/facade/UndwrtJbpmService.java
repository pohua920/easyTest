package com.sinosoft.claim.undwrt.service.facade;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author 中科软
 *
 */
public interface UndwrtJbpmService {
	
	
	
	/**
	 * 保存核赔节点，带jbpm工作流信息
	 * @param businessNo
	 * @param nodeType
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public int submitTaskBpm(String businessNo,String nodeType,String processId,HttpServletRequest req) throws Exception;
	
	/**
	 * 保存特殊赔案节点，带jbpm工作流信息
	 * @param nodeType
	 * @param businessNo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public int submitTaskSpeciBpm(String nodeType,String businessNo,String processId,HttpServletRequest req) throws Exception;
	
	/**
	 * 查询jbpm的下一节点
	 * @param businessNo
	 * @return
	 * @throws Exception
	 */
	public String getJbpmNextNode(String businessNo) throws Exception;
	
	/**
	 * 任务提交
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public int submitTask(HttpServletRequest req) throws Exception;

	/**
	 * 批次任务提交
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public int submitHeapTask(HttpServletRequest req) throws Exception;
}
