package com.sinosoft.claim.schema.service.facade;

/**
 * 保单保额保费表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyFee;
import com.sinosoft.claim.schema.model.PrpCopyFeeId;

public interface PrpCopyFeeService {

	/**
	 * 保存保单保额保费表信息
	 * @param prpLcfee ：传入的保单保额保费表
	 */
	public void save(PrpCopyFee prpCopyFee) throws Exception;

	/**
	 * 保单保额保费表信息
	 * @param list :传入的保单保额保费表信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyFee> list) throws Exception;

	/**
	 * 删除保单保额保费表信息
	 * @param prpCopyFeeId ：传入的保单保额保费表编号
	 */
	public void delete(PrpCopyFeeId prpCopyFeeId) throws Exception;

	/**
	 * 更新保单保额保费表信息
	 * @param prpCopyFee :传入需要更新的保单保额保费表
	 */
	public void update(PrpCopyFee prpCopyFee) throws Exception;

	/**
	 * 根据保单保额保费表编号查询出保单保额保费表信息
	 * @param prpCopyFeeId ：传入的保单保额保费表编号
	 * @return 返回保单保额保费表
	 */
	public PrpCopyFee findPrpCopyFee(PrpCopyFeeId prpCopyFeeId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单保额保费表页面信息
	 */
	public Page findPrpCopyFee(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取保单保额保费表页面信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的保单保额保费表页面信息的列表
	 */
	public List<PrpCopyFee> findPrpCopyFee(QueryRule queryRule) throws Exception;

	/**
	 * 根据保单保额保费表编号查询出保单保额保费表信息
	 * @param certiNo ：传入的保单保额保费表编号
	 * @return 返回保单保额保费表
	 */
	public PrpCopyFee findPrpCopyFee(String certiNo) throws Exception;
}
