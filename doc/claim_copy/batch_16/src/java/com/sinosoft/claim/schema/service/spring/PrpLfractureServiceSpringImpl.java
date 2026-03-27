package com.sinosoft.claim.schema.service.spring;

/**
 * 骨折程度service
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLfracture;
import com.sinosoft.claim.schema.model.PrpLfractureId;
import com.sinosoft.claim.schema.service.facade.PrpLfractureService;

public class PrpLfractureServiceSpringImpl extends GenericDaoHibernate<PrpLfracture, PrpLfractureId> implements PrpLfractureService {
	/**
	 * 保存骨折程度和骨折部位信息
	 * @param prpLfracture ：骨折程度和骨折部位信息
	 */
	@Override
	public void save(PrpLfracture prpLfracture) throws Exception {
		super.save(prpLfracture);

	}
	/**
	 * 保存骨折程度和骨折部位信息
	 * @param list  :骨折程度和骨折部位信息集合
	 * @throws Exception
	 */
	@Override
	public void save(List<PrpLfracture> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 删除骨折程度和骨折部位
	 * @param prpLfractureId ：骨折程度和骨折部位信息主键
	 */
	@Override
	public void delete(PrpLfractureId prpLfractureId) throws Exception {
		super.deleteByPK(PrpLfracture.class, prpLfractureId);
	}
	/**
	 * 根据主键查询骨折程度和骨折部位信息
	 * @param prpLfractureId ：骨折程度和骨折部位信息ID
	 * @return 骨折程度和骨折部位信息
	 */
	@Override
	public PrpLfracture findPrpLfracture(PrpLfractureId prpLfractureId) throws Exception {
		return super.get(PrpLfracture.class, prpLfractureId);
	}
	/**
	 * 根据查询对象获取骨折程度和骨折部位信息
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的骨折程度和骨折部位信息集合
	 */
	@Override
	public Page findPrpLfracture(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}
	/**
	 * 根据查询对象获取 骨折程度和骨折部位信息
	 * @param queryRule 查询对象
	 * @return 包含的 骨折程度和骨折部位信息 的集合
	 */
	@Override
	public List<PrpLfracture> findPrpLfracture(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 根据骨折类型查询
	 * @param fractureType 骨折程度，骨折部位
	 * @return
	 * @throws Exception
	 */
	public List<PrpLfracture> findPrpLfracture(String fractureType) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		String sql=" fractureType='"+fractureType+"' and validStatus='1' order by to_number(fracturecode)";
		queryRule.addSql(sql);
		return super.find(queryRule);
	}

}
