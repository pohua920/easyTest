package com.sinosoft.claim.schema.service.spring;

/**
 * 备注摘要接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLextId;
import com.sinosoft.claim.schema.service.facade.PrpLextService;

public class PrpLextServiceSpringImpl extends
		GenericDaoHibernate<PrpLext, PrpLextId> implements
		PrpLextService {
	
	/**
	 * 保存备注摘要信息
	 * @param prpLext ：传入的备注摘要
	 */
	@Override
	public void save(PrpLext prpLext) throws Exception {
		logger.info("保存备注摘要信息");
		super.save(prpLext);
	}
	
	/**
	 * 保存备注摘要信息
	 * @param list:保存备注摘要信息
	 */
	@Override
	public void save(List<PrpLext> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存备注摘要信息
	 * @param prpLext ：传入的备注摘要
	 */
	public void saveOrUpdate(PrpLext prpLext) throws Exception {
		logger.info("保存备注摘要信息");
		super.getSession().saveOrUpdate(prpLext);
	}
	/**
	 * 保存备注摘要信息
	 * @param list:保存备注摘要信息
	 */
	public void saveOrUpdate(List<PrpLext> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 删除备注摘要信息
	 * @param prpLextId ：传入的备注摘要编号
	 */
	@Override
	public void delete(PrpLextId prpLextId) throws Exception{
		super.deleteByPK(prpLextId);
		logger.info("删除备注摘要编号为" + prpLextId + "的备注摘要信息");
	}
	
	/**
	 * @description: 备注摘要修改
	 * @param PrpLext prpLext
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLext prpLext){
		logger.info("修改备注摘要信息开始");
		super.update(prpLext);
		logger.info("修改备注摘要信息结束");
	}
	
	/**
	 * 根据备注摘要编号查询出备注摘要信息
	 * @param prpLextId ：传入的备注摘要编号
	 * @return 返回备注摘要
	 */
	@Override
	public PrpLext findPrpLext(PrpLextId prpLextId) throws Exception{
		logger.info("查询备注摘要编号为" + prpLextId + "的备注摘要信息");
		return super.get(PrpLext.class,prpLextId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的备注摘要页面信息
	 */
	@Override
	public Page findPrpLext(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取备注摘要列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLext> findPrpLext(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}