package com.sinosoft.claim.schema.service.spring;
/**
 * SwfNode信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfNodeId;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;

public class SwfNodeServiceSpringImpl extends
GenericDaoHibernate<SwfNode, SwfNodeId> implements SwfNodeService{

	/**
	 * 保存单条信息
	 * @param SwfNode
	 */
	public void save(SwfNode swfNode) throws Exception {
		logger.info("保存SwfNode信息");
		super.save(swfNode);
		
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfNode> list
	 */
	public void save(List<SwfNode> list) throws Exception {
		logger.info("保存SwfNode信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfNodeId
	 */
	public void delete(SwfNodeId swfNodeId) throws Exception {
		logger.info("删除SwfNode信息编号为" + swfNodeId + "的SwfNode信息");
		super.deleteByPK(SwfNode.class, swfNodeId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfNodeId
	 */
	public SwfNode findSwfNode(SwfNodeId swfNodeId) throws Exception {
		logger.info("查询SwfNode信息编号为" + swfNodeId + "的SwfNode信息");
		return super.get(SwfNode.class, swfNodeId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息
	 * pageNo 开始的页数
	 * pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfNode(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取SwfNode信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfNode> findSwfNode(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

	public boolean checkEndflag(int iModelNo, int iNodeNo) throws SQLException, Exception {
		String strWherePart = "";
		int intCount = 0;
		boolean blnReturn = true;
		strWherePart = "ModelNo=" + iModelNo + " AND NodeNo<>" + iNodeNo + " AND EndFlag='1'";
		intCount = this.getCount(strWherePart);
		if (intCount > 0) {
			blnReturn = false;
		}
		return blnReturn;
	}
	
	@Override
	public List<SwfNode> findByConditions(String conditions) throws Exception{
		return super.find(QueryRule.getInstance().addSql(conditions));
	}
	
	public int getCount(String conditions) throws SQLException, Exception {
		int count = -1;
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM SWfNode WHERE ");
		buffer.append(conditions);
		if (logger.isDebugEnabled()) {
			logger.debug(buffer.toString());
		}
		count = ((Number) HibernateUtils.getCountbyCountSql(super.getSession(), buffer.toString())).intValue();
		return count;
	}
	/**
	 * @param modelNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 * 更具主键查询节点
	 */
	public SwfNode findByPrimaryKey(Integer modelNo,Integer nodeNo)throws Exception{
		SwfNodeId swfNodeId = new SwfNodeId();
		swfNodeId.setModelNo(modelNo);
		swfNodeId.setNodeNo(nodeNo);
		return this.findSwfNode(swfNodeId);
	}
	
	/**查询是否是工作流结束节点,1，表示工作流结束，最后一个节点
	 * @param modelNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 */
	public String findEndFlag(Integer modelNo,Integer nodeNo)throws Exception{
		String endFlag = "0";
		SwfNode swfNode = findByPrimaryKey(modelNo,nodeNo);
		//1表示工作流结束节点
		if(swfNode!=null){
			endFlag = swfNode.getEndFlag();
		}
		return endFlag;
	}

}
