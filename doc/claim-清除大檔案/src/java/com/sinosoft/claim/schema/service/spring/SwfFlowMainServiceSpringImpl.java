package com.sinosoft.claim.schema.service.spring;

/**
 * SwfFlowMain信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.service.facade.SwfFlowMainService;

public class SwfFlowMainServiceSpringImpl extends GenericDaoHibernate<SwfFlowMain, String> implements SwfFlowMainService {

	/**
	 * 保存单条信息
	 * @param SwfFlowMain
	 */
	public void save(SwfFlowMain swfFlowMain) throws Exception {
		logger.info("保存SwfFlowMain信息");
		super.save(swfFlowMain);

	}

	/**
	 * 保存多条条信息
	 * @param List<SwfFlowMain> list
	 */
	public void save(List<SwfFlowMain> list) throws Exception {
		logger.info("保存SwfFlowMain信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfFlowMainId
	 */
	public void delete(String flowId) throws Exception {
		logger.info("删除SwfFlowMain信息编号为" + flowId + "的SwfFlowMain信息");
		super.deleteByPK(SwfFlowMain.class, flowId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfFlowMainId
	 */
	public SwfFlowMain findSwfFlowMain(String flowId) throws Exception {
		logger.info("查询SwfFlowMain信息编号为" + flowId + "的SwfFlowMain信息");
		return super.get(SwfFlowMain.class, flowId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfFlowMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfFlowMain信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfFlowMain> findSwfFlowMain(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 按条件查询多条数据
	 * @param dbManager DB管理器
	 * @param conditions 查询条件
	 * @return Collection 包含swfFlowMainDto的集合
	 * @throws Exception
	 */
	public List<SwfFlowMain> findByConditions(String conditions) throws Exception {
		List<SwfFlowMain> collection = new ArrayList<SwfFlowMain>();
		if (conditions.trim().length() == 0) {
			conditions = "1=1";
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		collection = super.find(queryRule);
		return collection;
	}

	@Override
	public int getCount(String condtions) throws Exception {
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM SwfFlowMain WHERE ").append(condtions);
		List<?> resultSet = HibernateUtils.findbySql(super.getSession(), buffer.toString());
		return Integer.valueOf(String.valueOf(resultSet.get(0)));
	}

}
