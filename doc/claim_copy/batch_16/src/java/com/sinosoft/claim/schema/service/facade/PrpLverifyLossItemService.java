package com.sinosoft.claim.schema.service.facade;
/**
 * 定核损处理标的表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLverifyLossItem;
import com.sinosoft.claim.schema.model.PrpLverifyLossItemId;

public interface PrpLverifyLossItemService {
	
	/**
	 * 保存定核损处理标的信息
	 * @param prpLverifyLossItem ：传入的定核损处理标的
	 */
	public void save(PrpLverifyLossItem prpLverifyLossItem) throws Exception;
	
	/**
	 * 定核损处理标的信息
	 * @param list  :传入的定核损处理标的信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLverifyLossItem> list) throws Exception;
	
	/**
	 * 删除定核损处理标的信息
	 * @param prpLverifyLossItemId ：传入的定核损处理标的编号
	 */
	public void delete(PrpLverifyLossItemId prpLverifyLossItemId) throws Exception;

	/**
	 * 更新定核损处理标的信息
	 * @param prpLverifyLossItem :传入需要更新的定核损处理标的
	 */
	public void update(PrpLverifyLossItem prpLverifyLossItem) throws Exception;

	/**
	 * 根据定核损处理标的编号查询出定核损处理标的信息
	 * @param prpLverifyLossItemId ：传入的定核损处理标的编号
	 * @return 返回定核损处理标的
	 */
	public PrpLverifyLossItem findPrpLverifyLossItem(PrpLverifyLossItemId prpLverifyLossItemId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的定核损处理标的页面信息
	 */
	public Page findPrpLverifyLossItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取定核损处理标的信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  定核损处理标的信息的集合
	 */
	public List<PrpLverifyLossItem> findPrpLverifyLossItem(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据定核损处理标的编号查询出定核损处理标的信息
	 * @param certiNo ：传入的定核损处理标的编号
	 * @return 返回定核损处理标的
	 */
	public PrpLverifyLossItem findPrpLverifyLossItem(String certiNo) throws Exception;
}
