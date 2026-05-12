package com.sinosoft.claim.schema.service.spring;
/**
 * SwfPathLogStore信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfPathLogStore;
import com.sinosoft.claim.schema.model.SwfPathLogStoreId;
import com.sinosoft.claim.schema.service.facade.SwfPathLogStoreService;

public class SwfPathLogStoreServiceSpringImpl extends
GenericDaoHibernate<SwfPathLogStore, SwfPathLogStoreId> implements SwfPathLogStoreService{

	/**
	 * 保存单条信息
	 * @param SwfPathLogStore
	 */
	public void save(SwfPathLogStore swfPathLogStore) throws Exception {
		logger.info("保存SwfPathLogStore信息");
		super.save(swfPathLogStore);
		
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfPathLogStore> list
	 */
	public void save(List<SwfPathLogStore> list) throws Exception {
		logger.info("保存SwfPathLogStore信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfPathLogStoreId
	 */
	public void delete(String flowId,Integer pathNo) throws Exception {
		logger.info("删除SwfPathLogStore信息编号为" + new SwfPathLogStoreId(flowId,pathNo) + "的SwfPathLogStore信息");
		super.deleteByPK(SwfPathLogStore.class, new SwfPathLogStoreId(flowId,pathNo));
	}

	/**
	 * 根据主键查询信息
	 * @param swfPathLogStoreId
	 */
	public SwfPathLogStore findSwfPathLogStore(String flowId,Integer pathNo) throws Exception {
		logger.info("查询SwfPathLogStore信息编号为" + new SwfPathLogStoreId(flowId,pathNo) + "的SwfPathLogStore信息");
		return super.get(SwfPathLogStore.class, new SwfPathLogStoreId(flowId,pathNo));
	}

	/**
	 * 根据查询条件queryRule 查询分页信息
	 * pageNo 开始的页数
	 * pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfPathLogStore(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取SwfPathLogStore信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfPathLogStore> findSwfPathLogStore(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	 /**
     * 获取pathno号
     * @param flowID
     * @return LogNo
     * @throws Exception
     */
    public int getMaxPathNo(String flowId) throws Exception {
		int pathNo = -1;
		String statement = "Select max(PathNo+1) from SwfPathLogStore Where flowID='" + flowId + "'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number)list.get(0);
			if (num!=null) {
				return num.intValue();
			}
		}
		logger.debug(statement);
		if (pathNo == -1) {
			pathNo = 1;
		}
		return pathNo;
	}
}
