package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDuser;


public interface PrpDuserService {

	/**
	 * 用户基本信息
	 * @param Prpduser ：传入的用户名
	 */
	public void save(PrpDuser prpDuser) ;
	
	/**
	 * 保存用户基本信息
	 * @param list  :传入的用户基本信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpDuser> list) ;
	
	/**
	 * 删除用户名信息
	 * @param userCode ：传入的用户名编号
	 */
	public void delete(String userCode) ;

	/**
	 * 更新用户名信息
	 * @param PrpDuser :传入需要更新的用户名
	 */
	public void update(PrpDuser prpDuser) ;

	/**
	 * 根据用户名编号查询出用户名信息
	 * @param userCode ：传入的用户名编号
	 * @return 返回用户名
	 */
	public PrpDuser findPrpDuser(String userCode);
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的用户名页面信息
	 * @deprecated 不建议使用，请用findByPage代替
	 */
	public Page findPrpDuser(QueryRule queryRule, int pageNo, int pageSize) ;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的用户名页面信息
	 */
	List<PrpDuser> findPrpDuser(QueryRule queryRule);
	/**
	 * @param userCode 用户编码
	 * @param isChinese true 是获取中午名称，false 获取英文名称
	 * @return 返回用户的中文名称，或者英文名称
	 * @throws Exception
	 */
	public String getUserName(String userCode,boolean isChinese)throws Exception;
	/**
	 * 查询用户名称
	 * @param userCode 用户编码
	 * @return 返回用户的中文名称
	 * @throws Exception
	 */
	public String getUserName(String userCode)throws Exception;
	/**
	 * 
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @author 中科软
	 * @param statement 查询条件
	 * @param pageNo 开始页
	 * @param rowsPerPage 每页显示的条数
	 * @return 返回用户对象
	 * @throws Exception
	 */
	public Page queryUserHaveRights(String statement,int pageNo,int rowsPerPage) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的用户名页面信息
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) ;
    /**
     * 翻译用户代码
     * @param userCode 用户名称
     * @param isChinese 中午还是英文
     * @throws Exception
     * @return String 用户名称
     */
    public String translateCode(String userCode,boolean isChinese) throws Exception;

    /**
     * 根据sql语句条件查询
	 * @param conditions 查询条件
	 * @return
	 * @throws Exception 
	 */
	public List<PrpDuser> findByConditions(String conditions) throws Exception;
	/**
	 * 更具条件查询用户对象
	 * @param conditions  查询条件
	 * @param pageNo 页面起始页
	 * @param pageSize 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public List<PrpDuser> findByStatement(String statement,int pageNo,int pageSize) throws Exception;
	/**
	 * 更具条件查询用户对象
	 * @param conditions 查询条件
	 * @param pageNo 页面起始页
	 * @param pageSize 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public List<PrpDuser> findByConditions(String conditions,int pageNo,int pageSize) throws Exception;



}
