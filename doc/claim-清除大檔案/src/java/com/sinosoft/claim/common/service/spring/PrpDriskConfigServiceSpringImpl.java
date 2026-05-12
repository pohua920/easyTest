package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpDriskConfigId;

public class PrpDriskConfigServiceSpringImpl extends GenericDaoHibernate<PrpDriskConfig, PrpDriskConfigId> implements PrpDriskConfigService{

	/**
	 * 查询险种配置信息
	 * @param comCode 机构
	 * @param riskCode 险别
	 *  @param configCode 险别配置
	 */
	@Override
	public PrpDriskConfig findByPrimaryKey(String comCode, String riskCode, String configCode) {
		PrpDriskConfigId prpdRiskConfigId=new PrpDriskConfigId();
		prpdRiskConfigId.setComCode(comCode);
		prpdRiskConfigId.setRiskCode(riskCode);
		prpdRiskConfigId.setConfigCode(configCode);
		return super.get(PrpDriskConfig.class, prpdRiskConfigId);
	}

	/**
	 * 保存险种配置信息
	 * @param queryRule 传入的险种配置
	 */
	@Override
	public List<PrpDriskConfig> findPrpDriskConfig(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	


	/**
	 * 保存险种配置信息
	 * @param prpdRiskConfig  传入的险种配置
	 */
	@Override
	public void save(PrpDriskConfig prpdRiskConfig) throws Exception {
		logger.info("保存险种配置信息");
		super.save(prpdRiskConfig);
	}

	/**
	 * 保存或修改
	 * @param prpdRiskConfig
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpDriskConfig prpdRiskConfig) throws Exception {
		super.getSession().merge(prpdRiskConfig);
	}

	/**
	 * 删除险种配置信息
	 * @param registNo ：传入的险种配置编号
	 */
	@Override
	public void delete(PrpDriskConfigId prpdRiskConfigId) throws Exception {
		super.deleteByPK(PrpDriskConfig.class, prpdRiskConfigId);
		logger.info("删除险种配置编号为" + prpdRiskConfigId + "的险种配置信息");
	}

	/**
	 * 保存险种配置信息
	 * @param list:保存险种配置信息
	 */
	@Override
	public void save(List<PrpDriskConfig> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: 险种配置修改
	 * @param PrpDriskConfig 险种配置对象
	 * @throws Exception
	 */
	@Override
	public void update(PrpDriskConfig prpdRiskConfig) {
		logger.info("修改险种配置信息开始");
		super.update(prpdRiskConfig);
		logger.info("修改险种配置信息结束");
	}

	/**
	 * 根据险种配置编号查询出险种配置信息
	 * @param prpdRiskConfigId ：传入的险种配置编号
	 * @return 返回险种配置
	 */
	@Override
	public PrpDriskConfig findPrpDriskConfig(PrpDriskConfigId prpdRiskConfigId) throws Exception {
		logger.info("查询险种配置编号为" + prpdRiskConfigId + "的险种配置信息");
		return super.get(PrpDriskConfig.class, prpdRiskConfigId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种配置页面信息
	 */
	@Override
	public Page findPrpDriskConfig(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取险种配置列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询主键
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种配置页面信息
	 */
	@Override
	public Page findPrpDriskConfig(PrpDriskConfigId conditions, int pageNo, int pageSize) throws Exception{
		logger.info("获取险种配置列表信息");
		String sql = "select * from PrpDriskConfig where "+conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, PrpDriskConfig.class);
		return page;
	}

	/**
	 * 根据sql语句条件查询
	 * @param conditions 查询条件
	 * @return
	 * @throws Exception 
	 */
	public List<PrpDriskConfig> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	/**
	 * 通过机构代码、险种和属性名称返回参数值
	 * @param configCode 险种配置
	 * @param riskCode 险种
	 * @return 包含的险种配置页面信息
	 */
	public String getConfigValue(String configCode, String riskCode) throws Exception {
		String   comCode     = "00";
		String configValue =  "";
		try{
			PrpDriskConfig prpDriskConfig = null;
			if(!configCode.equals("")&&!riskCode.equals("")){
				PrpDriskConfigId id = new PrpDriskConfigId();
				id.setComCode(comCode);
				id.setConfigCode(configCode);
				id.setRiskCode(riskCode);
				prpDriskConfig = super.get(id);
			}
			if(prpDriskConfig!=null){
			    configValue =prpDriskConfig.getConfigValue();
			}
		}catch(Exception e){
			throw e;
		}
		return configValue ;
	} 

}
