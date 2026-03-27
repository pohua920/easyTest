package com.sinosoft.claim.schema.service.facade;

/**
 * 用户模板接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.schema.model.SwfModelUse;
import com.sinosoft.claim.schema.model.SwfModelUseId;

public interface SwfModelUseService {

	/**
	 * 保存SwfModelUse信息
	 * @param SwfModelUse ：传入的SwfModelUse
	 */
	public void save(SwfModelUse swfModelUse) throws Exception;
	
	/**
	 * SwfModelUse信息
	 * @param list  :传入的SwfModelUse信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<SwfModelUse> list) throws Exception;
	
	/**
	 * 删除SwfModelUse信息
	 * @param SwfModelUseId ：传入的SwfModelUse编号
	 */
	public void delete(SwfModelUseId swfModelUseId) throws Exception;

	/**
	 * 更新SwfModelUse信息
	 * @param SwfModelUse :传入需要更新的SwfModelUse
	 */
	public void update(SwfModelUse swfModelUse) throws Exception;

	/**
	 * 根据SwfModelUse编号查询出SwfModelUse信息
	 * @param SwfModelUseId ：传入的SwfModelUse编号
	 * @return 返回SwfModelUse
	 */
	public SwfModelUse findSwfModelUse(SwfModelUseId swfModelUseId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的SwfModelUse页面信息
	 */
	public Page findSwfModelUse(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的SwfModelUse页面信息
	 */
	public List<SwfModelUse> findSwfModelUse(QueryRule queryRule) throws Exception;
	/**
	 * @param riskCode
	 * @param comCode
	 * @return
	 * @throws Exception
	 * 获取用户的理赔模板号
	 */
	public int getModelNo(String riskCode, String comCode) throws Exception;

	public List<SwfModelUse> findByConditions(String conditions)throws Exception;
	/**
	 * 查询模版号
	 * @param modelType : 模版类型
	 * @param riskCode  ：险种代码
	 * @param comCode   ：部门代码
	 * @return：void
	 * @throws Exception
	 */
	public int getModelNo(String modelType, String riskCode, String comCode) throws SQLException, Exception;
	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public List<SwfModelUse> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
    /**
	    * 查询模板使用情况列表
	    * @param conditions String
	    * @throws SQLException
	    * @throws Exception
	    * @return Collection
	    */
	public List<SwfModelUse> findByModelUseConditions(String conditions) throws Exception ;
	public void saveOrUpdate(SwfModelUse swfModelUse)throws Exception;

}
