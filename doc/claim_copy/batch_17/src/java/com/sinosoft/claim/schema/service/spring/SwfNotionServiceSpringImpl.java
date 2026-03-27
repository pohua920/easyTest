package com.sinosoft.claim.schema.service.spring;

/**
 * SwfNotion信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.model.SwfNotionId;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;

public class SwfNotionServiceSpringImpl extends GenericDaoHibernate<SwfNotion, SwfNotionId> implements SwfNotionService {

	/**
	 * 保存单条信息
	 * @param SwfNotion
	 */
	public void save(SwfNotion swfNotion) throws Exception {
		logger.info("保存SwfNotion信息");
		super.save(swfNotion);

	}

	/**
	 * 保存多条条信息
	 * @param List<SwfNotion> list
	 */
	public void save(List<SwfNotion> list) throws Exception {
		logger.info("保存SwfNotion信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfNotionId
	 */
	public void delete(SwfNotionId swfNotionId) throws Exception {
		logger.info("删除SwfNotion信息编号为" + swfNotionId + "的SwfNotion信息");
		super.deleteByPK(SwfNotion.class, swfNotionId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfNotionId
	 */
	public SwfNotion findSwfNotion(SwfNotionId swfNotionId) throws Exception {
		logger.info("查询SwfNotion信息编号为" + swfNotionId + "的SwfNotion信息");
		return super.get(SwfNotion.class, swfNotionId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfNotion(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfNotion信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfNotion> findSwfNotion(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public SwfNotion findSwfNotion(String flowID, Integer logNo, Integer lineNo) throws Exception {
		return super.get(new SwfNotionId(flowID, logNo, lineNo));
	}

	/**
	 * 当前flowID相同,LogNo相同,lineNo 的最大的的maxNo
	 * @param flowID
	 * @return LogNo
	 * @throws Exception
	 */
	public int getMaxLineNo(String flowID, int logNo) throws Exception {
		int LineNo = -1;
		String statement = "Select max(LineNo+1) from swfNotion Where flowID='" + flowID + "'" + " and logNo=" + logNo;
		logger.debug(statement);
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			Number num = (Number)list.get(0);
			if (num!=null) {
				return num.intValue();
			}
		}
		logger.info("DBSwfLog.getMaxLineNo() success!");
		if (LineNo <= 0) {
			LineNo = 1;
		}
		return LineNo;
	}
}
