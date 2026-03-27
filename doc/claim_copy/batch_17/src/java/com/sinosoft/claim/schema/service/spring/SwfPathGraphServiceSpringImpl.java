package com.sinosoft.claim.schema.service.spring;
/**
 * SwfPathGraph信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPathGraph;
import com.sinosoft.claim.schema.model.SwfPathGraphId;
import com.sinosoft.claim.schema.service.facade.SwfPathGraphService;

public class SwfPathGraphServiceSpringImpl extends
GenericDaoHibernate<SwfPathGraph, SwfPathGraphId> implements SwfPathGraphService{

	/**
	 * 保存单条信息
	 * @param SwfPathGraph
	 */
	public void save(SwfPathGraph swfPathGraph) throws Exception {
		logger.info("保存SwfPathGraph信息");
		super.save(swfPathGraph);
		
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfPathGraph> list
	 */
	public void save(List<SwfPathGraph> list) throws Exception {
		logger.info("保存SwfPathGraph信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfPathGraphId
	 */
	public void delete(SwfPathGraphId swfPathGraphId) throws Exception {
		logger.info("删除SwfPathGraph信息编号为" + swfPathGraphId + "的SwfPathGraph信息");
		super.deleteByPK(SwfPathGraph.class, swfPathGraphId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfPathGraphId
	 */
	public SwfPathGraph findSwfPathGraph(SwfPathGraphId swfPathGraphId) throws Exception {
		logger.info("查询SwfPathGraph信息编号为" + swfPathGraphId + "的SwfPathGraph信息");
		return super.get(SwfPathGraph.class, swfPathGraphId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息
	 * pageNo 开始的页数
	 * pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfPathGraph(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取SwfPathGraph信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfPathGraph> findSwfPathGraph(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
