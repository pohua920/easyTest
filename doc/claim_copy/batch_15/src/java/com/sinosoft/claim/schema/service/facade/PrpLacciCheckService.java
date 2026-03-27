package com.sinosoft.claim.schema.service.facade;

/**
 * 意健险调查信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLacciCheck;

public interface PrpLacciCheckService {

	/**
	 * 意健险调查信息
	 * @param PrpLacciCheck ：传入的意健险调查信息
	 */
	public void save(PrpLacciCheck prpLacciCheck) throws Exception;

	/**
	 * 保存意健险调查信息
	 * @param list :传入的意健险调查信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLacciCheck> list) throws Exception;

	/**
	 * 删除意健险调查信息
	 * @param policyNo ：传入的意健险调查信息编号
	 */
	public void delete(String checkNo) throws Exception;

	/**
	 * 更新意健险调查信息
	 * @param PrpLacciCheck :传入需要更新的意健险调查信息
	 */
	public void update(PrpLacciCheck prpLacciCheck) throws Exception;

	/**
	 * 根据意健险调查信息编号查询出意健险调查信息
	 * @param policyNo ：传入的意健险调查信息编号
	 * @return 返回意健险调查信息
	 */
	public PrpLacciCheck findPrpLacciCheck(String checkNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的意健险调查信息页面信息
	 */
	public Page findPrpLacciCheck(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 意健险调查信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的意健险调查信息  的列表
	 */
	public List<PrpLacciCheck> findPrpLacciCheck(QueryRule queryRule) throws Exception;

	/**
	 * 获得等於某个报案号的所有调查费用总和
	 * @author 中科软
	 * @date Feb 26, 2013 7:16:52 PM
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public double getAcciCheckFeeByRegistNo(String registNo) throws Exception;

	public int findByRegistNoMaxTimes(String registNo) throws Exception;

	public List<PrpLacciCheck> findByConditions(String conditions) throws Exception;
	 /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Page 查询的一页的结果
     * @throws Exception
     */
    public Page findByConditions(String conditions,int pageNo,int pageSize) throws Exception;
}
