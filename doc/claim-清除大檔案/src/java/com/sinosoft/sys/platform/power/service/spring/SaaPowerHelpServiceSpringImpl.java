package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sinosoft.sys.platform.common.TreeNode;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaGradeTask;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;


@SuppressWarnings("unchecked")
public class SaaPowerHelpServiceSpringImpl extends GenericDaoHibernate implements SaaPowerHelpService {

	private static CacheService cacheManager = CacheManager.getInstance("SaaPowerHelpService");

	/**
	 * 查功能代码在岗位上是否存在
	 * 
	 * @param taskCode
	 *            功能代码
	 * @param gradeId
	 *            岗位id
	 * @return true 存在 false 不存在
	 */
	@SuppressWarnings("unchecked")
	public boolean checkTaskByGrade(String taskCode, List<Long> gradeList) {
		boolean isExist = false;
		StringBuilder builder = new StringBuilder();
		List<SaaTask> saaTaskList = new ArrayList<SaaTask>(0);
		List<SaaGradeTask> saaGradeTaskList = new ArrayList<SaaGradeTask>(0);
		String hql = "select task from SaaTask task where task.taskCode =?";
		saaTaskList = super.findByHql(hql, taskCode);
		for (int i = 0; i < gradeList.size(); i++) {
			builder.append(",'");
			builder.append(gradeList.get(i));
			builder.append("'");
		}
		if (null != saaTaskList && !saaTaskList.isEmpty()) {
			hql = "select gradeTask from SaaGradeTask gradeTask where gradeTask.saaGrade.id in (" + builder.substring(1) + ")"
					+ " and gradeTask.saaTask.id=?";
			saaGradeTaskList = super.findByHql(hql, saaTaskList.get(0).getId());
			if (null != saaGradeTaskList && !saaGradeTaskList.isEmpty()) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 在添加权限范围时查询对应岗位的业务允许机构 在添加权限范围时查询对应岗位的业务除外机构
	 * 
	 * @param permitCom
	 *            允许机构集合
	 * @param exceCom
	 *            禁止机构范围集合
	 * @return String 机构sql
	 */
	@SuppressWarnings("unchecked")
	public String getComPerRange(String permitCom) {
		StringBuilder builder = new StringBuilder();
		StringBuilder resBuilder = new StringBuilder();

		// builder.append("select prpDcompany.comCode from PrpDcompany
		// prpDcompany where 1=1");
		if (permitCom.length() > 0) {
			resBuilder.append(" in (");
			// builder.append(" and prpDcompany.comCode in (");
			builder.append(permitCom);
			builder.append(")");
		} else {
			resBuilder.append(" in (''");
			builder.append(")");
			builder.append(" and 1=2");
		}

		resBuilder.append(builder.toString());
		return resBuilder.toString();
	}

	public String getComExcRange(String exceCom) {
		StringBuilder builder = new StringBuilder();
		StringBuilder resBuilder = new StringBuilder();
		if (exceCom.length() > 2) {
			builder.append(" not in (");
			// builder.append(" not in ( select prpDcompanyb.comCode from
			// PrpDcompany prpDcompanyb ");
			// builder
			// .append(" where prpDcompanyb.comCode in (");
			builder.append(exceCom);
			builder.append(")");
		} else {
			builder.append("nohave");
		}
		resBuilder.append(builder.toString());
		return resBuilder.toString();
	}

	/**
	 * 获取业务允许机构范围
	 * 
	 * @param id
	 *            员工岗位表的主键
	 * @return 业务允许机构范围
	 */
	public Collection<String> getPermitCom(long id, String comLevelStr) {
		List<String> subComList = new ArrayList<String>(0);
		List<String> permitCompanyList = this.findByHql("select comCode from SaaPermitCompany permitCompany where "
				+ "permitCompany.saaUserGrade.id =?", id);
		if (null != permitCompanyList && !permitCompanyList.isEmpty()) {
			if (permitCompanyList.contains("9999999998")) {// 判断拥有权限机构权限将有特殊权限 条件拼接 1=1
				permitCompanyList.add("9999999998");
			} else {
				subComList = this.getSubCompanyCodeList(permitCompanyList, comLevelStr);
				permitCompanyList.addAll(subComList);
			}
		}
		return permitCompanyList;
	}

	public Collection<String> getPermitComWithOut(long id, String comLevelStr) {
		List<String> subComList = new ArrayList<String>(0);
		List<String> permitCompanyList = this.findByHql("select comCode from SaaPermitCompany permitCompany where "
				+ "permitCompany.saaUserGrade.id =?", id);
		if (null != permitCompanyList && !permitCompanyList.isEmpty()) {
			subComList = this.getSubCompanyCodeList(permitCompanyList, comLevelStr);
			permitCompanyList.addAll(subComList);
		}
		return permitCompanyList;
	}

	/**
	 * 获取允许授权机构范围
	 * 
	 * @param userCode
	 *            管理员员工代码
	 * @return 分级管理员允许授权机构范围
	 */
	public Collection<String> getAuthPermitCom(String userCode, String comLevelStr) {
		List<String> subComList = new ArrayList<String>(0);
		List<String> authCompanyList = this.findByHql("select comCode from SaaAuthCompany authCompany where authCompany.userCode =?",userCode);
		if (null != authCompanyList && !authCompanyList.isEmpty()) {
			subComList = this.getSubCompanyCodeList(authCompanyList, comLevelStr);
			authCompanyList.addAll(subComList);
		}
		return authCompanyList;
	}

	/**
	 * 获取允许授权机构对应代理机构范围
	 * 
	 * @param userCode
	 *            管理员员工代码
	 * @return 分级管理员允许授权机构对应代理机构范围
	 */
	public Collection<String> getAuthPermitAgentCom(String userCode, String comLevelStr) {
		List<String> subComList = new ArrayList<String>(0);
		List ComList = new ArrayList();
		ComList.addAll(getAuthPermitCom(userCode, comLevelStr));
		List<String> authCompanyList = this.findByHql("select comCode from SaaCompany Company where " + "Company.agentInsCom in ("
				+ removeDuplicateWithOrder(ComList) + ")");

		if (null != authCompanyList && !authCompanyList.isEmpty()) {
			subComList = this.getSubCompanyCodeList(authCompanyList, comLevelStr);
			authCompanyList.addAll(subComList);
		}
		return authCompanyList;
	}

	/**
	 * 删除ArrayList中重复元素，保持顺序
	 */
	public String removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		StringBuilder builder = new StringBuilder();
		String permitCode = "";
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element)) {
				newList.add(element);
			}
		}
		list.clear();
		list.addAll(newList);
		for (int i = 0; i < list.size(); i++) {
			builder.append(",'" + list.get(i) + "'");
		}
		if (list.isEmpty()) {
			builder.append(",''");
		}
		if (builder.toString().length() > 0) {
			permitCode = builder.toString().substring(1);
		} else {
			permitCode = "''";
		}
		return permitCode;
	}

	public List<String> getMixed(List<String> listA, List<String> listB) {
		List<String> mixList = new ArrayList<String>(0);
		if (!listA.isEmpty() && !listB.isEmpty()) {
			for (String strA : listA) {
				if (listB.contains(strA)) {
					mixList.add(strA);
				}
			}
		}
		return mixList;
	}

	/**
	 * 业务机构禁止范围
	 * 
	 * @param id
	 *            员工岗位表的主键
	 * @return
	 */
	public String getExceCom(long id, String comLevelStr) {
		String exceCom = "";
		StringBuilder builder = new StringBuilder();
		List<String> subComList = new ArrayList<String>(0);
		List<String> exceptCompanyList = this.findByHql("select comCode from SaaExceptCompany exceptCompanyt where "
				+ "exceptCompanyt.saaUserGrade.id =?", id);
		if (null != exceptCompanyList && !exceptCompanyList.isEmpty()) {

			subComList = this.getSubCompanyCodeList(exceptCompanyList, comLevelStr);
			for (int i = 0; i < subComList.size(); i++) {
				builder.append(",'" + subComList.get(i) + "'");
			}
		}
		if (builder.toString().length() > 0) {
			exceCom = builder.substring(1);
		}

		return exceCom;
	}

	/**
	 * 业务机构禁止范围
	 * 
	 * @param id
	 *            员工岗位表的主键
	 * @return
	 */
	public List<String> getExceComList(long id, String comLevelStr) {
		List<String> subComList = new ArrayList<String>(0);
		List<String> exceptCompanyList = this.findByHql("select comCode from SaaExceptCompany exceptCompanyt where "
				+ "exceptCompanyt.saaUserGrade.id =?", id);
		if (null != exceptCompanyList && !exceptCompanyList.isEmpty()) {

			subComList = this.getSubCompanyCodeList(exceptCompanyList, comLevelStr);
			if (null != subComList && !subComList.isEmpty()) {
				exceptCompanyList.addAll(subComList);
			}
		}

		return exceptCompanyList;
	}

	/**
	 * 管理员授权机构禁止范围
	 * 
	 * @param userCode
	 *            管理员员工代码
	 * @return
	 */
	public Collection<String> getAuthExceCom(String userCode, String comLevelStr) {
		List<String> subComList = new ArrayList<String>(0);
		List<String> exceptCompanyList = this.findByHql("select comCode from SaaAuthExceptCompany authExceptCompany where "
				+ "authExceptCompany.userCode =?", userCode);
		if (null != exceptCompanyList && !exceptCompanyList.isEmpty()) {
			subComList = this.getSubCompanyCodeList(exceptCompanyList, comLevelStr);
			exceptCompanyList.addAll(subComList);
		}

		return exceptCompanyList;
	}

	/**
	 * 在添加权限范围时查询业务允许产品表来获取险种
	 * 
	 * @param id
	 *            员工岗位表的主键
	 * @return String 险种集合
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getRisksByProductCode(long id) {
		List<String> productCodeList = this.findByHql("select permitProduct.productCode from SaaPermitProduct permitProduct where "
				+ "permitProduct.saaUserGrade.id =?", id);
		List<String> proCodeList = new ArrayList();
		if (null != productCodeList && !productCodeList.isEmpty()) {
			for (int i = 0; i < productCodeList.size(); i++) {
				int key = getProductType(productCodeList.get(i).trim());
				switch (key) {
				case 0:// 表示产品
					proCodeList.addAll(
						this.findByHql("select saaRisk.riskCode from SaaRisk saaRisk where (select count(*) from SaaClass saaClass where saaClass.businessLineCode=? and saaRisk.classCode = saaClass.classCode)>0 ",
										productCodeList.get(i).trim()));
					break;
				case 1:// 表示险类
					int subint = productCodeList.get(i).indexOf(".") + 1;
					String classCode = productCodeList.get(i).substring(subint);
					proCodeList.addAll(this.findByHql("select saaRisk.riskCode from SaaRisk saaRisk where " + "saaRisk.classCode =?",
							classCode));
					break;
				case 2:// 表示险种
					int codeint = productCodeList.get(i).lastIndexOf(".") + 1;
					proCodeList.add(productCodeList.get(i).substring(codeint));
					break;
				default:
					throw new IllegalArgumentException("productCode must have value");
				}
			}
		}
		return proCodeList;
	}

	/**
	 * 管理员的产品控制范围
	 * 
	 * @param userCode
	 *            管理员员工代码
	 * @return List 产品集合
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getAuthRisksByProductCode(String userCode) {
		List<String> productCodeList = this.findByHql("select authProduct.productCode from SaaAuthProduct authProduct where "
				+ "authProduct.userCode =?", userCode);
		List<String> proCodeList = new ArrayList();
		if (null != productCodeList && !productCodeList.isEmpty()) {
			for (int i = 0; i < productCodeList.size(); i++) {
				int key = getProductType(productCodeList.get(i).trim());
				switch (key) {
				case 0:// 表示产品
					proCodeList
							.addAll(this
									.findByHql(
											"select saaRisk.riskCode from SaaRisk saaRisk where (select count(*) from SaaClass saaClass where saaClass.businessLineCode=? and saaRisk.classCode = saaClass.classCode)>0 ",
											productCodeList.get(i).trim()));
					break;
				case 1:// 表示险类
					int subint = productCodeList.get(i).indexOf(".") + 1;
					String classCode = productCodeList.get(i).substring(subint);
					proCodeList.addAll(this.findByHql("select saaRisk.riskCode from SaaRisk saaRisk where " + "saaRisk.classCode =?",
							classCode));
					break;
				case 2:// 表示险种
					proCodeList.add(productCodeList.get(i).trim());
					break;
				default:
					throw new IllegalArgumentException("productCode must have value");
				}
			}
		}
		return productCodeList;
	}

	/**
	 * 产品种类判断
	 * 
	 * @productStr 产品
	 * @return 0-产品;1-险类;2-险种
	 */
	public int getProductType(String prodectStr) {

		if (prodectStr.indexOf('.') == -1) {
			return 0;
		} else if (prodectStr.indexOf('.') == prodectStr.lastIndexOf('.')) {
			return 1;
		} else {
			return 2;
		}
	}

	// *******************************************************
	/**
	 * 得到当前机构列表的所有下属机构(外部接口)
	 * 
	 * @param comCodes
	 * @return
	 */
	public List<String> getSubCompanyCodeList(List<String> comCodes, String comLevelStr) {
		List<String> comCodeList = this.getSubCompanyCode(comCodes, comLevelStr);
		return comCodeList;
	}

	/**
	 * 得到当前机构列表的所有下属机构(权限专用接口)
	 * 
	 * @param comCodes
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	private List<String> getSubCompanyCode(List<String> comCodes, String comLevelStr) {
		List<String> comCodeReturn = new ArrayList<String>(0);
		for (String str : comCodes) {
			comCodeReturn.addAll(this.getSubAllCompanyCode(str, comLevelStr));
		}
		return comCodeReturn;
	}

	/**
	 * 得到当前机构列表的所有下属机构
	 * 
	 * @param comCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSubAllCompanyCode(String comCode, String comLevelStr) {
		String key = cacheManager.generateCacheKey("subAllCompanyCode", comCode);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<String>) result;
		}
		List<String> comCodeList = new ArrayList<String>(0);
		TreeNode<String> node = this.getCompanyTree(comLevelStr).get(comCode);
		if (node != null) {
			List<TreeNode<String>> children = node.getAllChildren();
			if (null != children && !children.isEmpty()) {
				for (TreeNode<String> child : children) {
					comCodeList.add(child.getValue());
				}
			}
			comCodeList.add(node.getValue());
		}
		cacheManager.putCache(key, comCodeList);
		return comCodeList;
	}

	private Map<String, TreeNode<String>> getCompanyTree(String comLevelStr) {
		Map<String, TreeNode<String>> treeNodeMap = (Map<String, TreeNode<String>>) cacheManager.getCache("CompanyTree");
		if (treeNodeMap == null) {
			treeNodeMap = initCompanyTreeCache(comLevelStr);
		}
		return treeNodeMap;
	}

	private Map<String, TreeNode<String>> initCompanyTreeCache(String comLevelStr) {
		Map<String, TreeNode<String>> companyTree = new HashMap<String, TreeNode<String>>();
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select com.comCode, com.upperComCode from SaaCompany com where com.validStatus='1'");
		if (comLevelStr != null && !comLevelStr.trim().equals("")) {
			sbHql.append(" and com.comLevel in ").append(comLevelStr);
		}
		List<Object[]> list = super.findByHql(sbHql.toString());
		for (Object[] str : list) {
			TreeNode<String> node = new TreeNode<String>((String) str[0]);
			companyTree.put(node.getValue(), node);
		}
		for (Object[] str : list) {
			TreeNode<String> node = companyTree.get((String) str[0]);
			TreeNode<String> parent = companyTree.get((String) str[1]);
			if (parent != null && !parent.equals(node)) {
				node.setParent(parent);
				parent.addChild(node);
			}
		}
		cacheManager.putCache("CompanyTree", companyTree);
		return companyTree;
	}

	public Map builderGradeMap(String taskCode, String taskType) {
		Map gradeMap = new HashMap();
		StringBuffer builder = new StringBuffer();
		if (taskCode.length() > 0) {
			List gradeNameList = new ArrayList();
			builder.append("select gradeTask.saaGrade.id from SaaGradeTask gradeTask, SaaTask task ");
			builder.append(" where gradeTask.saaTask.id =task.id and task.taskCode = ?");
			List gradeIDList = this.findByHql(builder.toString(), taskCode);
			if (null != gradeIDList && !gradeIDList.isEmpty()) {
				builder = new StringBuffer();
				builder.append("select grade.gradeCName from SaaGrade grade where grade.id = ?");
				gradeNameList = this.findByHql(builder.toString(), gradeIDList.get(0));
			}
			if ("1".equals(taskType)) {
				if (gradeNameList.get(0).equals("一級核保崗")) {
					gradeMap.put(" ", 0);
				} else if (gradeNameList.get(0).equals("二級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
				} else if (gradeNameList.get(0).equals("三級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
				} else if (gradeNameList.get(0).equals("四級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
				} else if (gradeNameList.get(0).equals("五級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
					gradeMap.put("四級核保崗", 4);
				} else if (gradeNameList.get(0).equals("六級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
					gradeMap.put("四級核保崗", 4);
					gradeMap.put("五級核保崗", 5);
				} else if (gradeNameList.get(0).equals("七級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
					gradeMap.put("四級核保崗", 4);
					gradeMap.put("五級核保崗", 5);
					gradeMap.put("六級核保崗", 6);
				} else if (gradeNameList.get(0).equals("八級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
					gradeMap.put("四級核保崗", 4);
					gradeMap.put("五級核保崗", 5);
					gradeMap.put("六級核保崗", 6);
					gradeMap.put("七級核保崗", 7);
				} else if (gradeNameList.get(0).equals("九級核保崗")) {
					gradeMap.put("一級核保崗", 1);
					gradeMap.put("二級核保崗", 2);
					gradeMap.put("三級核保崗", 3);
					gradeMap.put("四級核保崗", 4);
					gradeMap.put("五級核保崗", 5);
					gradeMap.put("六級核保崗", 6);
					gradeMap.put("七級核保崗", 7);
					gradeMap.put("八級核保崗", 8);
				}
			} else if ("2".equals(taskType)) {
				if (gradeNameList.get(0).equals("一級核賠崗")) {
					gradeMap.put(" ", 0);
				} else if (gradeNameList.get(0).equals("二級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
				} else if (gradeNameList.get(0).equals("三級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
				} else if (gradeNameList.get(0).equals("四級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
				} else if (gradeNameList.get(0).equals("五級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
					gradeMap.put("四級核賠崗", 4);
				} else if (gradeNameList.get(0).equals("六級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
					gradeMap.put("四級核賠崗", 4);
					gradeMap.put("五級核賠崗", 5);
				} else if (gradeNameList.get(0).equals("七級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
					gradeMap.put("四級核賠崗", 4);
					gradeMap.put("五級核賠崗", 5);
					gradeMap.put("六級核賠崗", 6);
				} else if (gradeNameList.get(0).equals("八級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
					gradeMap.put("四級核賠崗", 4);
					gradeMap.put("五級核賠崗", 5);
					gradeMap.put("六級核賠崗", 6);
					gradeMap.put("七級核賠崗", 7);
				} else if (gradeNameList.get(0).equals("九級核賠崗")) {
					gradeMap.put("一級核賠崗", 1);
					gradeMap.put("二級核賠崗", 2);
					gradeMap.put("三級核賠崗", 3);
					gradeMap.put("四級核賠崗", 4);
					gradeMap.put("五級核賠崗", 5);
					gradeMap.put("六級核賠崗", 6);
					gradeMap.put("七級核賠崗", 7);
					gradeMap.put("八級核賠崗", 8);
				}
			}
		} else {
			if ("1".equals(taskType)) {
				gradeMap.put("一級核保崗", 1);
				gradeMap.put("二級核保崗", 2);
				gradeMap.put("三級核保崗", 3);
				gradeMap.put("四級核保崗", 4);
				gradeMap.put("五級核保崗", 5);
				gradeMap.put("六級核保崗", 6);
				gradeMap.put("七級核保崗", 7);
				gradeMap.put("八級核保崗", 8);
				gradeMap.put("九級核保崗", 9);
			} else if ("2".equals(taskType)) {
				gradeMap.put("一級核賠崗", 1);
				gradeMap.put("二級核賠崗", 2);
				gradeMap.put("三級核賠崗", 3);
				gradeMap.put("四級核賠崗", 4);
				gradeMap.put("五級核賠崗", 5);
				gradeMap.put("六級核賠崗", 6);
				gradeMap.put("七級核賠崗", 7);
				gradeMap.put("八級核賠崗", 8);
				gradeMap.put("九級核賠崗", 9);
			}
		}
		return gradeMap;
	}

	public String getUpperComcode(String comcode) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("comCode", comcode);
		queryRule.addEqual("validStatus", "1");
		SaaCompany saaCompany = super.findUnique(SaaCompany.class, queryRule);
		if (saaCompany != null) {
			return saaCompany.getUpperComCode();
		}
		return "";
	}

	public void clearAllServerCacheManager() {
		cacheManager.clearAllCacheManager();

	}
}
