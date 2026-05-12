package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDcompany;

public interface PrpDcompanyService {
	/**
	 * 保存机构信息
	 * @param prpDcompany 传入的机构
	 * @throws Exception
	 */
	public void save(PrpDcompany prpDcompany) throws Exception;

	/**
	 * 保存机构信息
	 * @param list:保存机构信息
	 * @throws Exception
	 */
	public void save(List<PrpDcompany> list) throws Exception;

	/**
	 * 删除机构信息
	 * @param comCode ：传入的机构代码
	 * @throws Exception
	 */
	public void delete(String comCode) throws Exception;

	/**
	 * 更新机构信息
	 * @param prpDcompany :传入需要更新的机构
	 *  * @throws Exception
	 */
	public void update(PrpDcompany prpDcompany) throws Exception;
	
	/**
	 * 保存或修改
	 * @param prpDcompany
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpDcompany prpDcompany) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面代码
	 * @param pageSize 页面大小
	 * @return 包含的机构页面信息
	 */
	public Page findPrpDcompany(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 查询部门的结果集
	 * @param queryRule 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<PrpDcompany> findPrpDcompany(QueryRule queryRule) throws Exception;

	/**
	 * 根据sql语句条件查询
	 * @param conditions 查询条件
	 * @return 返回部门结果
	 * @throws Exception 
	 */
	public List<PrpDcompany> findByConditions(String conditions) throws Exception;

	/**
	 * 更具机构代码查询机构
	 * @param comCode 机构代码
	 * @return 返回机构对象
	 */
	public PrpDcompany query(String comCode);

	/**
	 * 查询机构
	 * @param userCode 用户代码
	 * @param taskCode 用户
	 * @param rule 查询条件
	 * @param pageNo 起始页
	 * @param pageSize 中页数
	 * @return
	 */
	public Page findCompanyByRule(String userCode, String taskCode, QueryRule rule, int pageNo, int pageSize);

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的机构页面信息
	 */
	public Page findPrpDcompany(String conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据机构编号查询出机构信息
	 * @param comCode ：传入的机构编号
	 * @return 返回机构
	 */
	public PrpDcompany findPrpDcompany(String comCode) throws Exception;

	/**
	 * 是否存在
	 * @param comCode
	 * @return
	 */
	public boolean isExist(String comCode) throws Exception;

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception;

	/**
	 * 查询下级机构的数量
	 * @param prpDComCode 机构代码
	 * @param i 下几级（3或者4级）
	 * @return 返回多少个
	 */
	public int getCompanyAmount(String prpDComCode, int i);

	/**
	 * 查询部门名称
	 * @param comCode 编码代码
	 * @param isChinese true 获取中午名称，false 获取英文名称
	 * @return 根据部门代码，获取部门名称
	 */
	public String getComName(String comCode, boolean isChinese) throws Exception;

	/**
	 * 查询部门名称
	 * @param comCode 编码代码
	 * @return 根据部门代码，获取部门中午名称
	 */
	public String getComName(String comCode) throws Exception;

	/**
	 * 获得某个用户所有分配的机构
	 * @Description: 
	 * @author 中科软
	 * @param userCode 用户代码
	 * @return
	 * @throws Exception
	 */
	public List<PrpDcompany> findUserGradeCompanyListByUserCode(String userCode) throws Exception;

	/**
	 * 查询机构
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param pageSize 每页显示的条数
	 * @return 返回page对象
	 * @throws Exception
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 翻译代码
	 * @param userCode 用户代码
	 * @param isChinese 中文，英文
	 * @throws Exception
	 * @return String 返回名称
	 */
	public String translateCode(String comCode, boolean isChinese) throws Exception;
	  /**
	   * 获得部门信息
	   * @param  comCode
	   * @return prpDcompany对象
	   * @throws Exception
	   */
	  public PrpDcompany findByPrimaryKey(String comCode);
	  /**
	     * 按comCode查询
	     * @param comCode 部门代码
	     * @param withSubCompany 是否包含下级
	     * @param conditions 附加条件
	     * @return 包含prpDcompanyDto的集合
	     * @throws Exception
	     */
	public Collection<PrpDcompany> findByComCode(String comCode,
            boolean withSubCompany, String conditions)throws Exception;

}
