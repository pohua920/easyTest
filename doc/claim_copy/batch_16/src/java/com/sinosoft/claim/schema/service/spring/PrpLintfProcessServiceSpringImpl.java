package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLINTFPROCESS信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLintfProcess;
import com.sinosoft.claim.schema.service.facade.PrpLintfProcessService;

public class PrpLintfProcessServiceSpringImpl extends
GenericDaoHibernate<PrpLintfProcess, String> implements PrpLintfProcessService{

	@Override
	public void save(PrpLintfProcess prpLintfProcess) throws Exception {
		logger.info("保存PRPLINTFPROCESS信息");
		super.save(prpLintfProcess);
		
	}

	@Override
	public void save(List<PrpLintfProcess> list) throws Exception {
		logger.info("保存PRPLINTFPROCESS信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String businessNo) throws Exception {
		logger.info("删除PRPLINTFPROCESS信息编号为" + businessNo + "的PRPLINTFPROCESS信息");
		super.deleteByPK(PrpLintfProcess.class, businessNo);
	}

	@Override
	public PrpLintfProcess findPrpLintfProcess(String businessNo) throws Exception {
		logger.info("查询PRPLINTFPROCESS信息编号为" + businessNo + "的PRPLINTFPROCESS信息");
		return super.get(PrpLintfProcess.class,businessNo);
	}
    
	@Override
	public Page findPrpLintfProcess(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLINTFPROCESS信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLintfProcess> findPrpLintfProcess(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	public void logForReplevy(PrpLintfProcess prpLintfProcess) throws Exception{
		super.save(prpLintfProcess);
	}
}
