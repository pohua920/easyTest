package com.sinosoft.claim.schema.service.facade;
/**
 * 定核损意见表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.model.PrpLverifyLossExtId;

public interface PrpLverifyLossExtService {
	
	/**
	 * 保存定核损意见信息信息
	 * @param prpLverifyLossExt ：传入的定核损意见信息
	 */
	public void save(PrpLverifyLossExt prpLverifyLossExt) throws Exception;
	
	/**
	 * 定核损意见信息信息
	 * @param list  :传入的定核损意见信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLverifyLossExt> list) throws Exception;
	
	/**
	 * 删除定核损意见信息信息
	 * @param prpLverifyLossExtId ：传入的定核损意见信息编号
	 */
	public void delete(PrpLverifyLossExtId prpLverifyLossExtId) throws Exception;

	/**
	 * 更新定核损意见信息信息
	 * @param prpLverifyLossExt :传入需要更新的定核损意见信息
	 */
	public void update(PrpLverifyLossExt prpLverifyLossExt) throws Exception;

	/**
	 * 根据定核损意见信息编号查询出定核损意见信息信息
	 * @param prpLverifyLossExtId ：传入的定核损意见信息编号
	 * @return 返回定核损意见信息
	 */
	public PrpLverifyLossExt findPrpLverifyLossExt(PrpLverifyLossExtId prpLverifyLossExtId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的定核损意见信息页面信息
	 */
	public Page findPrpLverifyLossExt(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取定核损意见信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的定核损意见信息  的集合
	 */
	public List<PrpLverifyLossExt> findPrpLverifyLossExt(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据定核损意见信息编号查询出定核损意见信息信息
	 * @param certiNo ：传入的定核损意见信息编号
	 * @return 返回定核损意见信息
	 */
	public PrpLverifyLossExt findPrpLverifyLossExt(String certiNo) throws Exception;
}
