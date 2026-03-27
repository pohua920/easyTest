package com.sinosoft.claim.schema.service.spring;
/**
 * 伤情信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpersonWound;
import com.sinosoft.claim.schema.model.PrpLpersonWoundId;
import com.sinosoft.claim.schema.service.facade.PrpLpersonWoundService;

public class PrpLpersonWoundServiceSpringImpl extends GenericDaoHibernate<PrpLpersonWound, PrpLpersonWoundId> implements PrpLpersonWoundService {

	/**
	 * 按主键删除一条数据
	 */
	@Override
	public void delete(PrpLpersonWoundId prpLpersonWoundId) throws Exception {
		super.deleteByPK(PrpLpersonWound.class, prpLpersonWoundId);
	}
	
	/**
	 * 按主键查找一条数据
	 */
	@Override
	public PrpLpersonWound findPrpLpersonWound(PrpLpersonWoundId prpLpersonWoundId) throws Exception {
		return super.get(PrpLpersonWound.class, prpLpersonWoundId);
	}

	/**
	 * 按条件分页查询
	 */
	@Override
	public Page findPrpLpersonWound(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 查询满足条件的数据集合
	 */
	@Override
	public List<PrpLpersonWound> findPrpLpersonWound(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 保存数据
	 */
	@Override
	public void save(PrpLpersonWound prpLpersonWound) throws Exception {
		super.save(prpLpersonWound);
	}

	/**
	 * 保存数据集合
	 */
	@Override
	public void save(List<PrpLpersonWound> list) throws Exception {
		for(PrpLpersonWound prpLpersonWound : list){
			super.save(prpLpersonWound);
		}	
	}

	/**
	 * 更新数据
	 */
	@Override
	public void update(PrpLpersonWound prpLpersonWound) {
		super.update(prpLpersonWound);
	}

}
