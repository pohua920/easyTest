package com.sinosoft.claim.schema.service.facade;
/**
 * 逾款欠款清单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLarrearage;
import com.sinosoft.claim.schema.model.PrpLarrearageId;

public interface PrpLarrearageService {
	
	/**
	 * 保存逾款欠款清单信息
	 * @param prpLarrearage ：传入的逾款欠款清单
	 */
	public void save(PrpLarrearage prpLarrearage) throws Exception;
	
	/**
	 * 逾款欠款清单信息
	 * @param list  :传入的逾款欠款清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLarrearage> list) throws Exception;
	
	/**
	 * 删除逾款欠款清单信息
	 * @param prpLarrearageId ：传入的逾款欠款清单编号
	 */
	public void delete(PrpLarrearageId prpLarrearageId) throws Exception;

	/**
	 * 更新逾款欠款清单信息
	 * @param prpLarrearage :传入需要更新的逾款欠款清单
	 */
	public void update(PrpLarrearage prpLarrearage) throws Exception;

	/**
	 * 根据逾款欠款清单编号查询出逾款欠款清单信息
	 * @param prpLarrearageId ：传入的逾款欠款清单编号
	 * @return 返回逾款欠款清单
	 */
	public PrpLarrearage findPrpLarrearage(PrpLarrearageId prpLarrearageId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的逾款欠款清单页面信息
	 */
	public Page findPrpLarrearage(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  逾款欠款清单页面信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的 逾款欠款清单页面信息 的列表
	 */
	public List<PrpLarrearage> findPrpLarrearage(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据逾款欠款清单编号查询出逾款欠款清单信息
	 * @param certiNo ：传入的逾款欠款清单编号
	 * @return 返回逾款欠款清单
	 */
	public PrpLarrearage findPrpLarrearage(String certiNo) throws Exception;
}
