package com.sinosoft.claim.audit.service.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.audit.service.facade.AuditProcessService;
import com.sinosoft.claim.replevy.service.facade.ReplevyService;
import com.sinosoft.claim.replevy.vo.ReplevyUndwrtDto;
import com.sinosoft.claim.schema.model.SwfConfig;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.SwfConfigService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.model.NodeInfo;
import com.sinosoft.one.bpm.util.JbpmAPIUtil;

public class AuditProcessServiceSpringImpl implements AuditProcessService {

	private SwfLogService swfLogService;
	private SwfConfigService swfConfigService;
	private ReplevyService replevyService;
	private SwfNotionService swfNotionService;
	private WorkFlowService workFlowService;
	
	@Override
	public List<SwfConfig> getNextNode(String flowID, Integer logNo,Map<String,Object> condition) throws Exception {
		SwfLog swfLog = this.getSwfLogService().findSwfLog(flowID, logNo);
		List<NodeInfo> nextNodeInfos = JbpmAPIUtil.getNextNodeInfos(swfLog.getProcessId(), swfLog.getActorId(), condition);
		List<String> nextActorIds = new ArrayList<String>();
		for(NodeInfo nodeInfo : nextNodeInfos){
			nextActorIds.add(nodeInfo.getActorId());
		}
		List<SwfConfig> nextNodeList = new ArrayList<SwfConfig>();
		List<SwfConfig> tempList = this.getSwfConfigService().findByCondition(" processId = '"+swfLog.getProcessId()+"' order by nodeNo asc");
		for(SwfConfig swfConfig : tempList){
			if(nextActorIds.contains(swfConfig.getId().getActorId())){
				nextNodeList.add(swfConfig);
			}
		}
		return nextNodeList;
	}
	
	@Override
	public void processWorkFlow(String auditType,String businessNo,WorkFlowDto workFlowDto) throws Exception {
		workFlowDto.setUpdate(true);
		if("Replevy".equals(auditType) && ReplevyUndwrtDto.START.equals(workFlowDto.getNextActorId())){//追償審批
			this.getReplevyService().saveUndwrtBack(businessNo, workFlowDto);
		}else{//提交上級
			this.getWorkFlowService().dealAudit(workFlowDto);
		}
		this.getSwfNotionService().save(workFlowDto.getCurrSwfLog().getSwfNotionList());
	}


	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public SwfConfigService getSwfConfigService() {
		return swfConfigService;
	}

	public void setSwfConfigService(SwfConfigService swfConfigService) {
		this.swfConfigService = swfConfigService;
	}

	public ReplevyService getReplevyService() {
		return replevyService;
	}

	public void setReplevyService(ReplevyService replevyService) {
		this.replevyService = replevyService;
	}

	public SwfNotionService getSwfNotionService() {
		return swfNotionService;
	}

	public void setSwfNotionService(SwfNotionService swfNotionService) {
		this.swfNotionService = swfNotionService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	
}
