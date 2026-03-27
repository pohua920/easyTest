package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonId;
import com.sinosoft.claim.schema.service.facade.PrpLpersonService;

/**
 * 人员伤亡明细信息接口PrpLpersonService实现类
 * @ClassName PrpLpersonServiceSpringImpl
 * @Description 
 * @author 中科软
 */
public class PrpLpersonServiceSpringImpl extends GenericDaoHibernate<PrpLperson, PrpLpersonId> implements PrpLpersonService {

	/**
	 * 按主键删除一条数据
	 */
	@Override
	public void delete(PrpLpersonId prpLpersonId) throws Exception {
		logger.info("删除信息编号为"+prpLpersonId+"的人伤明细信息");
		super.delete(prpLpersonId);
	}

	/**
	 * 按主键查找一条数据
	 */
	@Override
	public PrpLperson findPrpLperson(PrpLpersonId prpLpersonId) throws Exception {
		logger.info("获取信息编号为"+prpLpersonId+"的人伤明细信息");
		return super.get(prpLpersonId);
	}
	
	/**
	 * 分页查询满足条件的数据
	 */
	@Override
	public Page findPrpLperson(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取人伤明细信息列表");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 查询满足条件的数据集合
	 */
	@Override
	public List<PrpLperson> findPrpLperson(QueryRule queryRule) throws Exception {
		logger.info("获取人伤明细信息集合");
		return super.find(queryRule);
	}

	/**
	 * 保存插入一条数据
	 */
	@Override
	public void save(PrpLperson prpLperson) throws Exception {
		logger.info("保存人伤明细信息");
		super.save(prpLperson);
	}

	/**
	 * 保存数据集合
	 */
	@Override
	public void save(List<PrpLperson> list) throws Exception {
		logger.info("保存人伤明细信息集合");
		for(PrpLperson prpLperson : list){
			super.save(prpLperson);
		}
	}

	/**
	 * 更新一条数据
	 */
	@Override
	public void update(PrpLperson prpLperson) {
		logger.info("更新人伤明细信息集合");
		super.update(prpLperson);
	}

}
