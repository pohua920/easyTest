package com.sinosoft.claim.schema.service.facade;

/**
 * 立案除外信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.Prplexcludeclaim;

public interface PrplexcludeclaimService {

	/**
	 * 立案除外信息
	 * @param Prplexcludeclaim ：传入的立案除外信息
	 */
	public void save(Prplexcludeclaim prplexcludeclaim) throws Exception;

	/**
	 * 保存立案除外信息
	 * @param list :传入的立案除外信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<Prplexcludeclaim> list) throws Exception;

	/**
	 * 删除立案除外信息
	 * @param policyNo ：传入的立案除外信息
	 */
	public void delete(String registNo) throws Exception;

	/**
	 * 更新立案除外信息信息
	 * @param Prplexcludeclaim :传入需要更新的立案除外信息
	 */
	public void update(Prplexcludeclaim prplexcludeclaim) throws Exception;

	/**
	 * 根据立案除外信息编号查询出保单立案除外信息
	 * @param policyNo ：传入的立案除外信息编号
	 * @return 返回立案除外信息
	 */
	public Prplexcludeclaim findPrplexcludeclaim(String registno) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案除外信息页面信息
	 */
	public Page findPrplexcludeclaim(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取立案除外信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的立案除外信息  的集合
	 */
	public List<Prplexcludeclaim> findPrplexcludeclaim(QueryRule queryRule) throws Exception;

	/**
	 * 判断是否有例外的案件
	 * @param registNo
	 * @return
	 * @throws Exception 
	 */
	public boolean isExcluded(String registNo) throws Exception;

	/**
	 * 立案除外提交
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
	public void save(String registNo, String excludeReason, UserDto userDto) throws Exception;
}
