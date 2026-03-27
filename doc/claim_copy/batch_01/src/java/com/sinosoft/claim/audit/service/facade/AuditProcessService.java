package com.sinosoft.claim.audit.service.facade;

import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.SwfConfig;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

public interface AuditProcessService {

	/***
	 * 獲取當前流程節點滿足條件的後續JBPM節點
	 * @param flowID
	 * @param logNo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<SwfConfig> getNextNode(String flowID,Integer logNo,Map<String,Object> condition) throws Exception;
	
	/**
	 * 審批處理
	 * @param auditType 處理類型
	 * @param businessNo 業務號碼
	 * @param workFlowDto
	 */
	public void processWorkFlow(String auditType,String businessNo,WorkFlowDto workFlowDto) throws Exception;
}
