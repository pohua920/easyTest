package cn.com.sinosoft.saa.service.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.saa.util.TreeNode;

/**
 * 权限控制辅助方法接口类
 * @author wuyanlong
 *
 */

public interface SaaPowerHelpService{
	/**
	 * 检查功能代码在岗位上是否存在
	 * @param taskCode
	 * @param gradeId
	 * @return true 存在 false 不存在
	 */
	public boolean checkTaskByGrade(String taskCode,List<Long> gradeList);
	
	/**
	 * 在添加权限范围时查询对应岗位的业务允许机构
	 * 在添加权限范围时查询对应岗位的业务除外机构
	 * @param permitCom 允许机构集合
	 * @param exceCom 禁止机构范围集合
	 * @return String 机构sql
	 */
	public String getComPerRange(String permitCom);
	public String getComExcRange(String exceCom);
	/**
	 * 获取业务允许机构范围
	 * @param id  员工岗位表的主键
	 * @return 业务允许机构范围
	 */
	public Collection<String> getPermitCom(long id);
	/**
	 * 获取允许授权机构范围
	 * @param userCode  管理员员工代码
	 * @return 分级管理员允许授权机构范围
	 */
	public Collection<String> getAuthPermitCom(String userCode);
	/**
	 * 获取允许授权机构对应代理机构范围
	 * @param userCode  管理员员工代码
	 * @return 分级管理员允许授权机构对应代理机构范围
	 */
	public Collection<String> getAuthPermitAgentCom(String userCode);
	/**
	 * 删除ArrayList中重复元素，保持顺序
	 */
	public String removeDuplicateWithOrder(List list);
	/**
	 * 业务机构禁止范围
	 * @param id 员工岗位表的主键
	 * @return
	 */
	public String getExceCom(long id);
	/**
	 * 管理员授权机构禁止范围
	 * @param userCode 管理员员工代码
	 * @return
	 */
	public Collection<String> getAuthExceCom(String userCode);
	
	/**
	 * 在添加权限范围时查询业务允许产品表来获取险种
	 * @param id 员工岗位表的主键
	 * @return String 险种集合
	 */
	public Collection<String> getRisksByProductCode(long id);
	/**
	 * 管理员的产品控制范围
	 * 
	 * @param userCode
	 *            管理员员工代码
	 * @return List 产品集合
	 */
	public List<String> getExceComList(long id);
	@SuppressWarnings("unchecked")
	public Collection<String> getAuthRisksByProductCode(String userCode);
	//***************************************
	public List<String> getSubCompanyCodeList(List<String> comCodes);
	
	public Map builderGradeMap(String taskCode, String taskType);
	
	/**
	 * 通过当前的COMCODE得到上一级的COMCODE
	 * 
	 */
	public String getUpperComcode(String comcode);
	/**
	 * 清除所有服务器端缓存
	 */
	public void clearAllServerCacheManager();
}
