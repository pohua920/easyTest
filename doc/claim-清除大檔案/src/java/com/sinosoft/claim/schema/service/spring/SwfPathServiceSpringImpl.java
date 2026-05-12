package com.sinosoft.claim.schema.service.spring;

/**
 * SwfPath信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfNodeId;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.SwfPathId;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.SwfConditionService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class SwfPathServiceSpringImpl extends GenericDaoHibernate<SwfPath, SwfPathId> implements SwfPathService {

	private int conditionCount = 0;
    public String batchFlag = "false";
	private SwfNodeService swfNodeService;
	private SwfConditionService swfConditionService;
	private PrpDcompanyService prpDcompanyService;

	/**
	 * 保存单条信息
	 * @param SwfPath
	 */
	public void save(SwfPath swfPath) throws Exception {
		logger.info("保存SwfPath信息");
		super.save(swfPath);

	}

	/**
	 * 保存多条条信息
	 * @param List<SwfPath> list
	 */
	public void save(List<SwfPath> list) throws Exception {
		logger.info("保存SwfPath信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfPathId
	 */
	public void delete(SwfPathId swfPathId) throws Exception {
		logger.info("删除SwfPath信息编号为" + swfPathId + "的SwfPath信息");
		super.deleteByPK(SwfPath.class, swfPathId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfPathId
	 */
	public SwfPath findSwfPath(SwfPathId swfPathId) throws Exception {
		logger.info("查询SwfPath信息编号为" + swfPathId + "的SwfPath信息");
		return super.get(SwfPath.class, swfPathId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息 pageNo 开始的页数 pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfPath(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取SwfPath信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfPath> findSwfPath(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	public List<SwfPath> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	public List<SwfPath> findByConditions(String conditions,int pageNo,int pageSize) throws Exception {
		String sql = "select * from SwfPath where "+conditions;
		List<SwfPath> list = new ArrayList<SwfPath>();
		List<?> listTemp = null;
		if(pageSize<0){
			listTemp = HibernateUtils.findbySql(super.getSession(), sql, SwfPath.class);
		}else{
			listTemp = HibernateUtils.findbySql(super.getSession(), sql,pageNo,pageSize, SwfPath.class);
		}
		for (Iterator<?> iterator = listTemp.iterator(); iterator.hasNext();) {
			SwfPath swfPath = (SwfPath) iterator.next();
			list.add(swfPath);
		}
		return list;
	}

	// 查询以某节点为起始节点的审核通过路径。徐明杰
	public SwfPath getPassPath(WfLog wfLog) throws Exception {
		// DBSWfPath dbSwfPath = new DBSWfPath(dbManager);
		// DBSWfNode dbSwfNode = new DBSWfNode(dbManager);
		SwfPath swfPath = new SwfPath(), tmpSwfPath = null;
		SwfNode swfNode = null;
		String conditions = "ModelNo=" + wfLog.getModelNo() + " AND StartNodeNo=" + wfLog.getNodeNo();
		List<SwfPath> swfPathList = this.findByConditions(conditions);
		for (int i = 0; i < swfPathList.size(); i++) {
			tmpSwfPath = swfPathList.get(i);
			SwfNodeId swfNodeId = new SwfNodeId(tmpSwfPath.getId().getModelNo(), tmpSwfPath.getEndNodeNo());
			swfNode = swfNodeService.findSwfNode(swfNodeId);
			// swfNodeDto =
			// swfNodeService.findByPrimaryKey(tmpSwfPath.getModelNo(),
			// tmpSwfPath.getEndNodeNo());
			if (swfNode != null && StringUtils.trimToEmpty(swfNode.getEndFlag()).equals("1")) {
				swfPath = tmpSwfPath;
				break;
			}
		}
		return swfPath;
	}
	
	@Override
	public List<SwfPath> getPathes(int modelNo, int nodelNo, String comCode) throws UserException {
		String strSQL = " ModelNo=" + modelNo + " AND StartNodeNo=" + nodelNo + " ORDER BY EndNodeNo";
		SwfPath wfPathDto = new SwfPath();
		SwfNode swfNodeDto = null;
		List<SwfPath> wfPathDtoList = null;
		List<SwfPath> wfPathDtoListNew = new ArrayList<SwfPath>();
		try {
			wfPathDtoList = this.findByConditions(strSQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int intCount = wfPathDtoList.size();
		if (intCount == 0) {
			throw new UserException( -98, -1007, this.getClass().getName());
		}
		Iterator<SwfPath> itwfpath = wfPathDtoList.iterator();
		while (itwfpath.hasNext()) {
			wfPathDto = (SwfPath) itwfpath.next();
			//过滤掉审核通过节点。
			SwfNodeId swfNodeId = new SwfNodeId();
			swfNodeId.setModelNo(wfPathDto.getId().getModelNo());
			swfNodeId.setNodeNo(wfPathDto.getEndNodeNo());
			try {
				swfNodeDto = this.swfNodeService.findSwfNode(swfNodeId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(swfNodeDto == null || StringUtils.trimToEmpty(swfNodeDto.getEndFlag()).equals("1")) {
				continue;
			} else {
				wfPathDtoListNew.add(wfPathDto);
				break;
			}
		}
		return wfPathDtoListNew;
	}
	
	 /**
     *取得以某节点为起始节点的所有满足条件且优先级最高的路径以及路径的个数
     *@param modelNo 模板号
     *@param startNodeNo 起始节点号
     *@param certiType  单证类型
     *@param businessNo 业务号
     *@param defaultFlag 是否缺省值--*0:否 1:是
     *@param comCode 机构代码
     *@param dbManager dbManager
     *@throws UserException
     *@throws Exception
     *@return Collection
     */
	public List<SwfPath> getPathes(int modelNo, int startNodeNo, String certiType, String businessNo, String defaultFlag, String comCode) throws UserException, Exception {
		// BLSWfConditionAction blWfConditionAction = new
		// BLSWfConditionAction();
		// SWfPathDto wfPathDto = new SWfPathDto();
		// SWfConditionDto wfConditionDto = new SWfConditionDto();
		// DBSWfPath dbWfPath = new DBSWfPath(dbManager);
		// DBSWfCondition dbWfCondition = new DBSWfCondition(dbManager);
		// DBSWfNode dbSwfNode = new DBSWfNode(dbManager);
		// SWfNodeDto swfNodeDto = null;
		// Collection conditionList = new ArrayList();
		// Collection wfPathDtoList = new ArrayList();
		// Collection wfPathDtoListNew = new ArrayList();
		// System.out.println("----blpathAction ----------1  certiType" +
		// certiType);
		// if (certiType.equals("null") || certiType.length() == 0)
		// {
		// certiType = businessNo.substring(0, 1);
		// }
		List<SwfPath> wfPathListNew = new ArrayList<SwfPath>();
		try {
			int intCount = 0;
			int j = 0;
			boolean flag = false;
			String strWherePart = "";
			String strSQL = " ModelNo=" + modelNo + " AND StartNodeNo=" + startNodeNo + " ORDER BY EndNodeNo";
			// 查找符合该业务的路径
			// System.out.println("----blpathAction ----查找路径------SQL:\n" +
			// strSQL);
			List<SwfPath> wfPathList = this.findByConditions(strSQL, 0, 0);
			intCount = wfPathList.size();
			if (intCount == 0) {
				throw new UserException(-98, -1007, this.getClass().getName());
			}
			Iterator<SwfPath> itwfpath = wfPathList.iterator();
			SwfPath swfPath = null;
			SwfNode swfNode = null;

			List<SwfCondition> conditionList = null;
			SwfCondition swfCondition = null;
			while (itwfpath.hasNext()) {
				j++;
				swfPath = (SwfPath) itwfpath.next();
				// ----->>>>>-----过滤掉审核通过节点。双核界面调整需求。徐明杰2005-8-12
				swfNode = swfNodeService.findByPrimaryKey(swfPath.getId().getModelNo(), swfPath.getEndNodeNo());
				// 车队提交节点列表需要审核通过节点 modify by luyang 2005-11-11
				// 出单员可以直接到审核通过节点
				if (this.batchFlag.equals("false") && startNodeNo != 1) {
					if (swfNode == null || StringUtils.trimToEmpty(swfNode.getEndFlag()).equals("1")) {
						continue;
					}
				}
				// -----<<<<<-----------------------------------------------------
				wfPathListNew.add(swfPath);
				this.conditionCount = 0;
				// 查找符合该业务的路径条件,並计算出路径条件的条数conditionCount
				strWherePart = this.getCondition(comCode, swfPath.getId().getModelNo(), swfPath.getId().getPathNo());
				// 如果没有路径条件则认为此路径是满足条件的
				if (this.conditionCount == 0) {
					// continue;
					// 张颖需求：如果路径上没有设置条件，则不让通过。2005-4-8
					// wfPathDtoListNew.clear();
					throw new Exception("没有設定路径条件！！！");
					// throw new UserException( -98, -1003,
					// "没有设置路径条件！请与系统管理员联系！");
				}
				if (this.conditionCount > 0) {
					flag = false;
					strSQL = strWherePart + " Order by ModelNo,PathNo,ConditionNo";
					conditionList = swfConditionService.findByConditions(strSQL);
					Iterator<SwfCondition> itcondition = conditionList.iterator();
					while (itcondition.hasNext()) {
						swfCondition = (SwfCondition) itcondition.next();
						// 简单配置条件拼写
						if (swfCondition.getConfigType().equals("0")) {
							swfCondition.setConfigText(swfCondition.getColumnName() + swfCondition.getOperator() + swfCondition.getValue());
						}
						flag = swfConditionService.execute(businessNo, swfPath.getId().getModelNo(), swfPath.getStartNodeNo(), swfCondition, null);
						if (flag) {
							break;
						}
					}
					if (!flag) {
						wfPathListNew.remove(swfPath);
					}
				}
			}
			intCount = conditionList.size();
			int maxPriority = 100;
			itwfpath = wfPathListNew.iterator();
			// 找出所有路径中最大的优先级
			while (itwfpath.hasNext()) {
				swfPath = (SwfPath) itwfpath.next();
				if (swfPath.getPriority() < maxPriority) {
					maxPriority = swfPath.getPriority();
				}
			}
			itwfpath = wfPathListNew.iterator();
			if ("1".equals(defaultFlag)) {
				while (itwfpath.hasNext()) {
					swfPath = (SwfPath) itwfpath.next();
					if (swfPath.getDefaultFlag() == "1" && swfPath.getPriority() == maxPriority) {
						wfPathListNew.add(swfPath);
						break;
					}
				}
			} else {
				while (itwfpath.hasNext()) {
					swfPath = (SwfPath) itwfpath.next();
					if (swfPath.getPriority() == maxPriority) {
						wfPathListNew.add(swfPath);
						break;
					}
				}
			}
		} catch (UserException ue) {
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return wfPathListNew;
	}
    /**
     * @递归向上获取路径条件
     * @modify by liuguoan,2005-07-01
     * @modify reason:原来查找算法根据前几位来获取，修改为根据PrpDcompany的上级机构UpperComCode获取路径条件
     */
	private String getCondition(String iComCode, int iModelNo, int iPathNo) throws Exception {
//		int level = 8;
		int intCount = 0;
//		int conditionCount = 0;
		String strWherePart = "";
		// DBSWfCondition dbWfCondition = new DBSWfCondition(dbManager);
		// DBPrpDcompany dbPrpDcompany = null;
		PrpDcompany prpDcompany = null;
		String upperComCode = "";
		String calComCode = "";
		try {
			/*
			 * while (level >= 0) { if (level >= 0 && ! ( (iComCode == null) ||
			 * (iComCode.length() == 0))) { String calComCode =
			 * iComCode.substring(0, level); for (int i = level; i < 8; i++) {
			 * calComCode += "0"; } strWherePart = " ModelNo=" + iModelNo +
			 * " AND PathNo=" + iPathNo + " AND ComCode='" + calComCode +
			 * "' AND SerialNo=1 AND ValidStatus='1'";
			 * System.out.println("s1::"+strWherePart); } else { strWherePart =
			 * " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo +
			 * " AND (ComCode IS NULL OR ComCode='') AND SerialNo=1 AND ValidStatus='1'"
			 * ; System.out.println("s2 ::"+strWherePart); } intCount =
			 * dbWfCondition.getCount(strWherePart); if (intCount > 0) { break;
			 * } level = level - 2; }
			 */

			/* 如果传入机构为空 */
			if ((iComCode == null) || (iComCode.length() == 0)) {
//				System.out.println("---------------------11---------------------");
				strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo + " AND (ComCode IS NULL OR ComCode='') AND SerialNo=1 AND ValidStatus='1'";
				intCount = swfConditionService.getCount(strWherePart);
				this.conditionCount = intCount;
				return strWherePart;
			}

			calComCode = iComCode;
			// dbPrpDcompany = new DBPrpDcompany(dbManager);
			while (1 == 1) {
				// 获取当前机构的路径条件
				strWherePart = " ModelNo=" + iModelNo + " AND PathNo=" + iPathNo + " AND ComCode='" + calComCode.trim() + "' AND SerialNo=1 AND ValidStatus='1'";
				intCount = swfConditionService.getCount(strWherePart);
				if (intCount > 0) {
//					System.out.println("---------------------12---------------------");
					this.conditionCount = intCount;
					return strWherePart;
				}
				// 获取不到当前机构的路径条件则查找上级机构代码
				// prpDcompanyDto = new PrpDcompanyDto();
				prpDcompany = prpDcompanyService.findByPrimaryKey(calComCode);
				upperComCode = prpDcompany.getPrpDcompany().getComCode();
				// 如果上级机构代码和本机构代码相等
				if (calComCode.equalsIgnoreCase(upperComCode)) {
					intCount = 0;
					break;
				}
				calComCode = upperComCode;

			}
			this.conditionCount = intCount;
		} catch (Exception e) {
			throw e;
		}
		return strWherePart;
	}
	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public int getConditionCount() {
		return conditionCount;
	}

	public void setConditionCount(int conditionCount) {
		this.conditionCount = conditionCount;
	}

	public SwfConditionService getSwfConditionService() {
		return swfConditionService;
	}

	public void setSwfConditionService(SwfConditionService swfConditionService) {
		this.swfConditionService = swfConditionService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

}
