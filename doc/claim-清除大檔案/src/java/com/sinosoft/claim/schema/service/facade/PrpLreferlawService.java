package com.sinosoft.claim.schema.service.facade;
/**
 * 诉讼信息表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLreferlaw;
import com.sinosoft.claim.schema.model.PrpLreferlawId;

public interface PrpLreferlawService {
	
	/**
	 * 保存诉讼信息
	 * @param prpLreferlaw ：传入的诉讼信息
	 */
	public void save(PrpLreferlaw prpLreferlaw) throws Exception;
	
	/**
	 * 诉讼信息
	 * @param list  :传入的诉讼信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLreferlaw> list) throws Exception;
	
	/**
	 * 删除诉讼信息
	 * @param prpLreferlawId ：传入的诉讼编号
	 */
	public void delete(PrpLreferlawId prpLreferlawId) throws Exception;

	/**
	 * 更新诉讼信息
	 * @param prpLreferlaw :传入需要更新的诉讼
	 */
	public void update(PrpLreferlaw prpLreferlaw) throws Exception;

	/**
	 * 根据诉讼编号查询出诉讼信息
	 * @param prpLreferlawId ：传入的诉讼编号
	 * @return 返回诉讼
	 */
	public PrpLreferlaw findPrpLreferlaw(PrpLreferlawId prpLreferlawId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的诉讼页面信息
	 */
	public Page findPrpLreferlaw(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取诉讼信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 诉讼信息 的集合
	 */
	public List<PrpLreferlaw> findPrpLreferlaw(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据诉讼编号查询出诉讼信息
	 * @param certiNo ：传入的诉讼编号
	 * @return 返回诉讼
	 */
	public PrpLreferlaw findPrpLreferlaw(String certiNo) throws Exception;
}
