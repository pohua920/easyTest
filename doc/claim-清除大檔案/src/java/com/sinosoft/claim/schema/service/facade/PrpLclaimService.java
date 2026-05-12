package com.sinosoft.claim.schema.service.facade;
/**
 * 立案信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaim;

public interface PrpLclaimService {
	
	/**
	 * 立案基本信息
	 * @param PrpLclaim ：传入的立案信息
	 */
	public void save(PrpLclaim prpLclaim) throws Exception;
	
	/**
	 * 保存立案基本信息
	 * @param list  :传入的立案基本信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaim> list) throws Exception;
	
	/**
	 * 删除立案信息信息
	 * @param policyNo ：传入的立案信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新立案信息信息
	 * @param PrpLclaim :传入需要更新的立案信息
	 */
	public void update(PrpLclaim prpLclaim) throws Exception;

	/**
	 * 根据立案信息编号查询出立案信息信息
	 * @param policyNo ：传入的立案信息编号
	 * @return 返回立案信息
	 */
	public PrpLclaim findPrpLclaim(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案信息页面信息
	 */
	public Page findPrpLclaim(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 立案信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的立案信息  的列表
	 */
	public List<PrpLclaim> findPrpLclaim(QueryRule queryRule) throws Exception;
	/**
	 * 根据报案号判断是否立案
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public boolean isClaim(String registNo)throws Exception;
	 /**
	  * 根据报案号查询立案信息
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLclaim> findByRegistNo(String registNo) throws Exception;
	/**
	 * 根据报案号查询立案信息,立案没有注销的信息
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLclaim> findByRegistNoCancel(String registNo) throws Exception;
	/**
	 * 根据sql语句查询有多少条立案信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public long getCount(String conditions)throws Exception;
	/**
	 * 判断是否已经立案，如果没立案返回null，立案就返回立案号
	 * @param registNo
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public String isClaim(String registNo,String policyNo)throws Exception;
	/**
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 * @param prpLclaim
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpLclaim prpLclaim) throws Exception;
	/**
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 * @param list
	 * @throws Exception
	 */
	public void saveOrUpdate(List<PrpLclaim> list) throws Exception;
	/***
	 * 
	 * @Description: 翻译代码
	 * @author 中科软
	 * @date Feb 24, 2013 2:17:03 AM
	 * @param businessCode
	 * @param isSearchClaimNo
	 */
	public String translateCode(String businessCode,boolean isSearchClaimNo) throws Exception;
	
	/***
	 * 
	 * @Description: 翻译代码
	 * @author 中科软
	 * @date Feb 24, 2013 2:17:03 AM
	 * @param businessCode
	 * @param isSearchClaimNo
	 */
	public String[] translateCodes(String businessCode,boolean isSearchClaimNo) throws Exception;
	
    /**
     * 预赔提交时回写立案的预赔金额
     * @param claim String
     * @throws Exception
     * @return Collection
     */
    public void updatePrepayPaid(PrpLclaim prpLclaim) throws Exception;
    /**
     * 根据sql语句查询page信息
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByConditions(String conditions,int pageNo,int pageSize)throws Exception;
	
	/***
	 * 查找可進行追償等級的案子
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findReplevyCase(String conditions,int pageNo,int pageSize)throws Exception;
	
}
