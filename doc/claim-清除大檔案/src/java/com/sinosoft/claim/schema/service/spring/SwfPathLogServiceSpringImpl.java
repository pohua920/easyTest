package com.sinosoft.claim.schema.service.spring;

/**
 * SwfPathLog信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogId;
import com.sinosoft.claim.schema.service.facade.SwfPathLogService;

public class SwfPathLogServiceSpringImpl extends GenericDaoHibernate<SwfPathLog, SwfPathLogId> implements SwfPathLogService {

	/**
	 * 保存单条信息
	 * @param SwfPathLog
	 */
	public void save(SwfPathLog swfPathLog) throws Exception {
		logger.info("保存SwfPathLog信息");
		super.save(swfPathLog);

	}

	/**
	 * 保存多条条信息
	 * @param List<SwfPathLog> list
	 */
	public void save(List<SwfPathLog> list) throws Exception {
		logger.info("保存SwfPathLog信息");
		for (int i = 0; i < list.size(); i++) {
			this.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfPathLogId
	 */
	public void delete(SwfPathLogId swfPathLogId) throws Exception {
		logger.info("删除SwfPathLog信息编号为" + swfPathLogId + "的SwfPathLog信息");
		super.deleteByPK(SwfPathLog.class, swfPathLogId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfPathLogId
	 */
	public SwfPathLog findSwfPathLog(SwfPathLogId swfPathLogId) throws Exception {
		logger.info("查询SwfPathLog信息编号为" + swfPathLogId + "的SwfPathLog信息");
		return super.get(SwfPathLog.class, swfPathLogId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfPathLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfPathLog信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfPathLog> findSwfPathLog(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 获取pathno号
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxPathNo(String flowId) throws Exception {
		String statement = "Select max(PathNo+1) from SwfPathLog Where flowID='" + flowId + "'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number) list.get(0);
			if (num != null) {
				return num.intValue();
			}
		}
		return 1;
	}

	@Override
	public int getCount(String condtions) {
		String statement = "Select count(1) from SwfPathLog Where " + condtions;
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number) list.get(0);
			if (num != null) {
				return num.intValue();
			}
		}
		return 0;
	}

	@Override
	public void deleteByConditions(String conditions) throws Exception {
		String statement = "delete from SwfPathLog Where " + conditions;
		HibernateUtils.executeSql(super.getSession(), statement);
	}

}
