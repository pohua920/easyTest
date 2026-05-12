package com.sinosoft.claim.workflow.service.facade;


import com.sinosoft.claim.workflow.vo.JbpmDto;


public interface JbpmBusinessService {
	
	/**启动工作流，处理第一个节点
	 * @param JbpmDto
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Object startProcess(JbpmDto jbpmDto,Object...objs)throws Exception;
	
	/**处理当前节点的信息
	 * @param JbpmDto
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Object processTask(JbpmDto jbpmDto,Object...objs)throws Exception;
	
}
