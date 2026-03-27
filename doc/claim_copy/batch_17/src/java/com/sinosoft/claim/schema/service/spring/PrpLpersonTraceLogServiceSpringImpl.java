package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪修改轨迹信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpersonTraceLog;
import com.sinosoft.claim.schema.model.PrpLpersonTraceLogId;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceLogService;

public class PrpLpersonTraceLogServiceSpringImpl extends
GenericDaoHibernate<PrpLpersonTraceLog, PrpLpersonTraceLogId> implements PrpLpersonTraceLogService{

	@Override
	public void save(PrpLpersonTraceLog prpLpersonTraceLog) throws Exception {
		logger.info("保存人伤跟踪修改轨迹信息");
		super.save(prpLpersonTraceLog);
		
	}

	@Override
	public void save(List<PrpLpersonTraceLog> list) throws Exception {
		logger.info("保存人伤跟踪修改轨迹信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLpersonTraceLogId prpLpersonTraceLogId) throws Exception {
		logger.info("删除人伤跟踪修改轨迹信息编号为" + prpLpersonTraceLogId + "的人伤跟踪修改轨迹信息");
		super.deleteByPK(PrpLpersonTraceLog.class, prpLpersonTraceLogId);
	}

	@Override
	public PrpLpersonTraceLog findPrpLpersonTraceLog(PrpLpersonTraceLogId prpLpersonTraceLogId) throws Exception {
		logger.info("查询人伤跟踪修改轨迹信息编号为" + prpLpersonTraceLogId + "的人伤跟踪修改轨迹信息");
		return super.get(PrpLpersonTraceLog.class, prpLpersonTraceLogId);
	}

	@Override
	public Page findPrpLpersonTraceLog(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取人伤跟踪修改轨迹信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLpersonTraceLog> findPrpLpersonTraceLog(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪修改轨迹编号查询出人伤跟踪修改轨迹信息
	 * @param certiNo ：传入的人伤跟踪修改轨迹编号
	 * @return 返回人伤跟踪修改轨迹
	 */
	public PrpLpersonTraceLog findPrpLpersonTraceLog(String certiNo) throws Exception{
		PrpLpersonTraceLog prpLpersonTraceLog = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLpersonTraceLog> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLpersonTraceLog = resultList.get(0);
		}
		return prpLpersonTraceLog;
	}

}
