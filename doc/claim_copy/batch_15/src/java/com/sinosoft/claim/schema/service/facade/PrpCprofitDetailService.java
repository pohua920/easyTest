package com.sinosoft.claim.schema.service.facade;

/**
 * 优惠折扣明细接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCprofitDetail;
import com.sinosoft.claim.schema.model.PrpCprofitDetailId;

public interface PrpCprofitDetailService {

	/**
	 * 保存优惠折扣明细信息
	 * @param prpLcheck ：传入的优惠折扣明细
	 */
	public void save(PrpCprofitDetail prpCprofitDetail) throws Exception;

	/**
	 * 优惠折扣明细信息
	 * @param list :传入的优惠折扣明细信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCprofitDetail> list) throws Exception;

	/**
	 * 删除优惠折扣明细信息
	 * @param prpCprofitDetailId ：传入的优惠折扣明细编号
	 */
	public void delete(PrpCprofitDetailId prpCprofitDetailId) throws Exception;

	/**
	 * 更新优惠折扣明细信息
	 * @param prpCprofitDetail :传入需要更新的优惠折扣明细
	 */
	public void update(PrpCprofitDetail prpCprofitDetail) throws Exception;

	/**
	 * 根据优惠折扣明细编号查询出优惠折扣明细信息
	 * @param prpCprofitDetailId ：传入的优惠折扣明细编号
	 * @return 返回优惠折扣明细
	 */
	public PrpCprofitDetail findPrpCprofitDetail(PrpCprofitDetailId prpCprofitDetailId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的优惠折扣明细页面信息
	 */
	public Page findPrpCprofitDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取优惠折扣明细  的列表
	 * @param queryRule 查询对象
	 * @return 包含的优惠折扣明细  的列表
	 */
	public List<PrpCprofitDetail> findPrpCprofitDetail(QueryRule queryRule) throws Exception;
}
