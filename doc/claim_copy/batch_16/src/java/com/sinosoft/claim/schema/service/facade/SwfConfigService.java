package com.sinosoft.claim.schema.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfConfig;

public interface SwfConfigService {
	
	/***
	 * 获取流程节点映射配置讯息
	 * @param processId 流程实例
	 * @param actorId 当前流程节点
	 * @return
	 */
	public SwfConfig getSwfConfig(String processId,String actorId);
	
	/**
	 * 查询满足条件的流程节点映射配置讯息
	 * @param conditions
	 * @return
	 */
	public List<SwfConfig> findByCondition(String conditions);

}
