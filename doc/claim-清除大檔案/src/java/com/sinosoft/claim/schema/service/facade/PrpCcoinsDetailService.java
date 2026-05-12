package com.sinosoft.claim.schema.service.facade;
/**
 * 保单的共保人子信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcoinsDetail;
import com.sinosoft.claim.schema.model.PrpCcoinsDetailId;

public interface PrpCcoinsDetailService {
	
	/**
	 * 保存保单的共保人子信息信息
	 * @param PrpCcoinsDetail ：传入的保单的共保人子信息
	 */
	public void save(PrpCcoinsDetail PrpCcoinsDetail) throws Exception;
	
	/**
	 * 保单的共保人子信息信息
	 * @param list  :传入的保单的共保人子信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCcoinsDetail> list) throws Exception;
	
	/**
	 * 删除保单的共保人子信息信息
	 * @param PrpCcoinsDetailId ：传入的保单的共保人子信息编号
	 */
	public void delete(PrpCcoinsDetailId PrpCcoinsDetailId) throws Exception;

	/**
	 * 更新保单的共保人子信息信息
	 * @param PrpCcoinsDetail :传入需要更新的保单的共保人子信息
	 */
	public void update(PrpCcoinsDetail PrpCcoinsDetail) throws Exception;

	/**
	 * 根据保单的共保人子信息编号查询出保单的共保人子信息信息
	 * @param PrpCcoinsDetailId ：传入的保单的共保人子信息编号
	 * @return 返回保单的共保人子信息
	 */
	public PrpCcoinsDetail findPrpCcoinsDetail(PrpCcoinsDetailId PrpCcoinsDetailId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单的共保人子信息页面信息
	 */
	public Page findPrpCcoinsDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取保单的共保人子信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的保单的共保人子信息的列表
	 */
	public List<PrpCcoinsDetail> findPrpCcoinsDetail(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据保单的共保人子信息编号查询出保单的共保人子信息信息
	 * @param certiNo ：传入的保单的共保人子信息编号
	 * @return 返回保单的共保人子信息
	 */
	public PrpCcoinsDetail findPrpCcoinsDetail(String certiNo) throws Exception;
}
