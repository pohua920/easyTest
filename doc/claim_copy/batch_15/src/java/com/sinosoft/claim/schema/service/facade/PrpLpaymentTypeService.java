package com.sinosoft.claim.schema.service.facade;
/**
 * 给付类别信息
 * @author zhangxingwei
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLpaymentType;
import com.sinosoft.claim.schema.model.PrpLpaymentTypeId;

public interface PrpLpaymentTypeService {
	
	/**
	 * 保存给付类别信息
	 * @param prpLpaymentType ：给付类别信息
	 */
	public void save(PrpLpaymentType prpLpaymentType) throws Exception;
	
	/**
	 * 保存给付类别信息
	 * @param list  :给付类别信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLpaymentType> list) throws Exception;
	
	/**
	 * 删除给付类别
	 * @param prpLpaymentTypeId ：给付类别信息主键
	 */
	public void delete(PrpLpaymentTypeId prpLpaymentTypeId) throws Exception;

	/**
	 * 更新给付类别信息
	 * @param prpLpaymentType :给付类别信息
	 */
	public void update(PrpLpaymentType prpLpaymentType) throws Exception;

	/**
	 * 根据主键查询给付类别信息
	 * @param prpLpaymentTypeId ：给付类别信息ID
	 * @return 给付类别信息
	 */
	public PrpLpaymentType findPrpLpaymentType(PrpLpaymentTypeId prpLpaymentTypeId) throws Exception;
	
	/**
	 * 根据查询对象获取 给付类别信息
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的给付类别信息集合
	 */
	public Page findPrpLpaymentType(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 给付类别信息
	 * @param queryRule 查询对象
	 * @return 包含的 给付类别信息 的集合
	 */
	public List<PrpLpaymentType> findPrpLpaymentType(QueryRule queryRule) throws Exception;
	/**
	 * 查询给付类别
	 * @param prpLpaymentType 查询条件
	 * @param codeType 查询那个给付类别
	 * @param pageNo 当前页数
	 * @param pageSize 总页数
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpaymentType> findPrpLpaymentType(PrpLpaymentType prpLpaymentType,String codeType,int pageNo,int pageSize)throws Exception;
	/**
	 * 验证输入的给付类别是否存在
	 * @param prpLpaymentType 给付类别
	 * @param codeType 给付类型
	 * @return
	 * @throws Exception
	 */
	public Long countPrpLpaymentType(PrpLpaymentType prpLpaymentType,String codeType)throws Exception;
	/**
	 * 查询给付类别
	 * @param prpLpaymentType 查询条件
	 * @param codeType 查询那个给付类别
	 * @param pageNo 当前页数
	 * @param pageSize 总页数
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpaymentType> getPrpLpaymentType(PrpLpaymentType prpLpaymentType,String codeType)throws Exception;
	
}
