package com.sinosoft.claim.schema.service.spring;

/**
 * SwfLogStore信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.model.SwfLogStoreId;
import com.sinosoft.claim.schema.service.facade.SwfLogStoreService;

public class SwfLogStoreServiceSpringImpl extends GenericDaoHibernate<SwfLogStore, SwfLogStoreId> implements SwfLogStoreService {

	/**
	 * 保存单条信息
	 * @param SwfLogStore
	 */
	public void save(SwfLogStore swfLogStore) throws Exception {
		logger.info("保存SwfLogStore信息");
		super.save(swfLogStore);

	}

	/**
	 * 保存多条条信息
	 * @param List<SwfLogStore> list
	 */
	public void save(List<SwfLogStore> list) throws Exception {
		logger.info("保存SwfLogStore信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfLogStoreId
	 */
	public void delete(String flowID, int logNo) throws Exception {
		logger.info("删除SwfLogStore信息编号为" + new SwfLogStoreId(flowID,logNo) + "的SwfLogStore信息");
		super.deleteByPK(SwfLogStore.class, new SwfLogStoreId(flowID,logNo));
	}

	/**
	 * 根据主键查询信息
	 * @param swfLogStoreId
	 */
	public SwfLogStore findSwfLogStore(String flowID, int logNo) throws Exception {
		logger.info("查询SwfLogStore信息编号为" + new SwfLogStoreId(flowID,logNo) + "的SwfLogStore信息");
		return super.get(SwfLogStore.class,new SwfLogStoreId(flowID,logNo));
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfLogStore(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfLogStore信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfLogStore> findSwfLogStore(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 获取logno号
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxLogNo(String flowId) throws Exception {
		int LogNo = -1;
		String statement = "Select max(LogNo+1) from SwfLogStore Where flowId='" + flowId + "'";
		logger.debug(statement);
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number)list.get(0);
			if (num!=null) {
				return num.intValue();
			}
		}
		if (LogNo == -1) {
			LogNo = 1;
		}
		return LogNo;
	}

	@Override
	public Page findByPage(String condition, int pageNo, int recordPerPage) {
		String sql = "select * from SwfLogStore where " + condition;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, recordPerPage,SwfLogStore.class);
	}

	@Override
	public int getMaxNodeLogNo(String flowID, String nodeType, String businessNo) {
		int logNo = -1;
		String statement = "Select max(LogNo) from SwfLogStore Where flowID='" + flowID + "' and  nodeType='" + nodeType + "' and businessNo='" + businessNo + "'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number) list.get(0);
			if (num != null) {
				return num.intValue();
			}
		}
		if (logNo <= 0) {
			logNo = 1;
		}
		return logNo;
	}

}
