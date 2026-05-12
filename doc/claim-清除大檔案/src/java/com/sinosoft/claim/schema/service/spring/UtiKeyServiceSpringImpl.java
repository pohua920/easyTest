package com.sinosoft.claim.schema.service.spring;

/**
 * 键值信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.UtiKey;
import com.sinosoft.claim.schema.service.facade.UtiKeyService;

public class UtiKeyServiceSpringImpl extends GenericDaoHibernate<UtiKey, String> implements UtiKeyService {

	@Override
	public void save(UtiKey utiKey) throws Exception {
		logger.info("保存键值信息信息");
		super.save(utiKey);

	}

	@Override
	public void save(List<UtiKey> list) throws Exception {
		logger.info("保存键值信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String tableName) throws Exception {
		logger.info("删除键值信息编号为" + tableName + "的键值信息");
		super.deleteByPK(UtiKey.class, tableName);
	}

	@Override
	public UtiKey findUtiKey(String tableName) throws Exception {
		logger.info("查询键值信息编号为" + tableName + "的键值信息");
		return super.get(UtiKey.class, tableName);
	}

	@Override
	public Page findUtiKey(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取键值信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<UtiKey> findUtiKey(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
