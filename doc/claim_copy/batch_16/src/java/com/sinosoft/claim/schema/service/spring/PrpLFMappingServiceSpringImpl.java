package com.sinosoft.claim.schema.service.spring;
/**
 * 理赔费用与收付原因对照信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.PrpLFMapping;
import com.sinosoft.claim.schema.model.PrpLFMappingId;
import com.sinosoft.claim.schema.service.facade.PrpLFMappingService;

public class PrpLFMappingServiceSpringImpl extends
GenericDaoHibernate<PrpLFMapping, PrpLFMappingId> implements PrpLFMappingService{

	@Override
	public void save(PrpLFMapping prpLFMapping) throws Exception {
		logger.info("保存理赔费用与收付原因对照信息");
		super.save(prpLFMapping);
		
	}

	@Override
	public void save(List<PrpLFMapping> list) throws Exception {
		logger.info("保存理赔费用与收付原因对照信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLFMappingId prpLFMappingId) throws Exception {
		logger.info("删除理赔费用与收付原因对照信息编号为" + prpLFMappingId + "的理赔费用与收付原因对照信息");
		super.deleteByPK(PrpLFMapping.class, prpLFMappingId);
	}

	@Override
	public PrpLFMapping findPrpLFMapping(PrpLFMappingId prpLFMappingId) throws Exception {
		logger.info("查询理赔费用与收付原因对照信息编号为" + prpLFMappingId + "的理赔费用与收付原因对照信息");
		return super.get(PrpLFMapping.class, prpLFMappingId);
	}

	@Override
	public Page findPrpLFMapping(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取理赔费用与收付原因对照信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLFMapping> findPrpLFMapping(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据理赔费用与收付原因对照编号查询出理赔费用与收付原因对照信息
	 * @param certiNo ：传入的理赔费用与收付原因对照编号
	 * @return 返回理赔费用与收付原因对照
	 */
	public PrpLFMapping findPrpLFMapping(String certiNo) throws Exception{
		PrpLFMapping prpLFMapping = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLFMapping> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLFMapping = resultList.get(0);
		}
		return prpLFMapping;
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.claim.schema.service.facade.PrpLFMappingService#findByConditions(java.lang.String)
	 * 根据条件查询所有的
	 */
	public List<PrpLFMapping> findByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		if(conditions==null||"".equals(conditions)){
			conditions = "1=1";
		}
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	/** (non-Javadoc)
	 * @see com.sinosoft.claim.schema.service.facade.PrpLFMappingService#findByConditions(java.lang.String)
	 * 根据条件查询所有的
	 */
	public Map<String, String> findMapByConditions(String conditions)throws Exception{
		Map<String, String> codeMap = new HashMap<String, String>();
		List<PrpLFMapping> prpLFMappingList = this.findByConditions(conditions);
		if (null != prpLFMappingList) {
			PrpLFMapping prpLFMapping = null;
			for (int i = 0; i < prpLFMappingList.size(); i++) {
				prpLFMapping = prpLFMappingList.get(i);
				codeMap.put(prpLFMapping.getId().getChargeCode() + prpLFMapping.getId().getPayRefReason().substring(0, 1), prpLFMapping.getId().getPayRefReason());
			}
		}
		return codeMap;
	}

}
