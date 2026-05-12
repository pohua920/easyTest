package com.sinosoft.claim.schema.service.spring;

/**
 * SwfCondition信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfConditionId;
import com.sinosoft.claim.schema.service.facade.SwfConditionService;
import com.sinosoft.claim.undwrt.service.facade.ConfigUndwrtService;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class SwfConditionServiceSpringImpl extends GenericDaoHibernate<SwfCondition, SwfConditionId> implements SwfConditionService {
	private ConfigUndwrtService configUndwrtService;
	/**
	 * 保存单条信息
	 * @param SwfCondition
	 */
	public void save(SwfCondition swfCondition) throws Exception {
		logger.info("保存SwfCondition信息");
		super.save(swfCondition);

	}

	/**
	 * 保存多条条信息
	 * @param List<SwfCondition> list
	 */
	public void save(List<SwfCondition> list) throws Exception {
		logger.info("保存SwfCondition信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfConditionId
	 */
	public void delete(SwfConditionId swfConditionId) throws Exception {
		logger.info("删除SwfCondition信息编号为" + swfConditionId + "的SwfCondition信息");
		super.deleteByPK(SwfCondition.class, swfConditionId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfConditionId
	 */
	public SwfCondition findSwfCondition(SwfConditionId swfConditionId) throws Exception {
		logger.info("查询SwfCondition信息编号为" + swfConditionId + "的SwfCondition信息");
		return super.get(SwfCondition.class, swfConditionId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfCondition(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfCondition信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfCondition> findSwfCondition(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	
	public List<SwfCondition> findByConditions(String conditions) throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	@Override
	public boolean executeResult(String sql) {
		List<?> result = super.getSession().createSQLQuery(sql).list();
		if(result!=null && !result.isEmpty()){
			Number num = (Number)result.get(0);
			if(num!=null && num.intValue()>0){
				return true;
			}
		}
		return false;
	}
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 查询多少条数
	 */
	public int getCount(String conditions) throws Exception{
		String sql = "select count(1) from SwfCondition where "+conditions;
		return ((Number)HibernateUtils.getCountbyCountSql(super.getSession(), sql)).intValue();
	}
	/**
	 * 执行工作流系统发出的sql语句(针对简单描述和SQL描述)
	 * @param businessNo 业务号码
	 * @param wfConditionDto WfConditionDto
	 * @param dbManager DBManager
	 * @throws UserException
	 * @throws Exception
	 * @return boolean
	 */
	public boolean execute(String businessNo, int modelno, int nodeNo, SwfCondition swfCondition, String userCode) throws UserException, Exception {
		boolean blnResult = false;
		String strWhere = "";
		String strConfig = "";
		if (swfCondition.getConfigType().equals("0") || swfCondition.getConfigType().equals("1")) {
			strWhere = swfCondition.getBusinessKey().trim() + "='" + businessNo.trim() + "' AND " + swfCondition.getConfigText().trim();
			strConfig = "SELECT COUNT(*) FROM " + swfCondition.getTableName().trim() + " WHERE " + strWhere.trim();
			blnResult = configUndwrtService.executeSql(businessNo, strConfig);
		}
		if (swfCondition.getConfigType().equals("2")) {
			strConfig = swfCondition.getConfigText().trim();
			// linjf0703 add
			if (userCode == null) {
				blnResult = false;
			} else {
				blnResult = configUndwrtService.executeFunc(businessNo, modelno, nodeNo, strConfig, userCode);
			}
		}
		return blnResult;
	}

	public ConfigUndwrtService getConfigUndwrtService() {
		return configUndwrtService;
	}

	public void setConfigUndwrtService(ConfigUndwrtService configUndwrtService) {
		this.configUndwrtService = configUndwrtService;
	}

}
