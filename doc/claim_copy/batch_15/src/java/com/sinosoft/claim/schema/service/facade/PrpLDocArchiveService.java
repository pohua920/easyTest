package com.sinosoft.claim.schema.service.facade;
/**
 * 资料归档调阅主表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLDocArchive;

public interface PrpLDocArchiveService {
	
	/**
	 * PRPLDOCARCHIVE信息
	 * @param PrpLDocArchive ：传入的资料归档调阅主表信息
	 */
	public void save(PrpLDocArchive prpLDocArchive) throws Exception;
	
	/**
	 * 保存资料归档调阅主表信息
	 * @param list  :传入的资料归档调阅主表信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLDocArchive> list) throws Exception;
	
	/**
	 * 删除资料归档调阅主表信息
	 * @param policyNo ：传入的资料归档调阅主表信息
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新资料归档调阅主表信息信息
	 * @param PrpLDocArchive :传入需要更新的资料归档调阅主表信息
	 */
	public void update(PrpLDocArchive prpLDocArchive) throws Exception;

	/**
	 * 根据资料归档调阅主表信息编号查询出保单资料归档调阅主表信息
	 * @param policyNo ：传入的资料归档调阅主表信息编号
	 * @return 返回资料归档调阅主表信息
	 */
	public PrpLDocArchive findPrpLDocArchive(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的资料归档调阅主表信息页面信息
	 */
	public Page findPrpLDocArchive(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取资料归档调阅主表信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的资料归档调阅主表信息  的集合
	 */
	public List<PrpLDocArchive> findPrpLDocArchive(QueryRule queryRule) throws Exception;
	/**
	 * 更具sql语句条件，查询page对象信息
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize)throws Exception ;
}
