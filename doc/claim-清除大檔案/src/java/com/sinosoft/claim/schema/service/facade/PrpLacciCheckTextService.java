package com.sinosoft.claim.schema.service.facade;
/**
 * 意健险调查信息描述接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLacciCheckText;
import com.sinosoft.claim.schema.model.PrpLacciCheckTextId;

public interface PrpLacciCheckTextService {
	
	/**
	 * 保存意健险调查信息描述信息
	 * @param prpLacciCheckText ：传入的意健险调查信息描述
	 */
	public void save(PrpLacciCheckText prpLacciCheckText) throws Exception;
	
	/**
	 * 意健险调查信息描述信息
	 * @param list  :传入的意健险调查信息描述信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLacciCheckText> list) throws Exception;
	
	/**
	 * 删除意健险调查信息描述信息
	 * @param prpLacciCheckTextId ：传入的意健险调查信息描述编号
	 */
	public void delete(PrpLacciCheckTextId prpLacciCheckTextId) throws Exception;

	/**
	 * 更新意健险调查信息描述信息
	 * @param prpLacciCheckText :传入需要更新的意健险调查信息描述
	 */
	public void update(PrpLacciCheckText prpLacciCheckText) throws Exception;

	/**
	 * 根据意健险调查信息描述编号查询出意健险调查信息描述信息
	 * @param prpLacciCheckTextId ：传入的意健险调查信息描述编号
	 * @return 返回意健险调查信息描述
	 */
	public PrpLacciCheckText findPrpLacciCheckText(PrpLacciCheckTextId prpLacciCheckTextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的意健险调查信息描述页面信息
	 */
	public Page findPrpLacciCheckText(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 意健险调查 的列表
	 * @param queryRule 查询对象
	 * @return 包含的意健险调查  的列表
	 */
	public List<PrpLacciCheckText> findPrpLacciCheckText(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据意健险调查信息描述编号查询出意健险调查信息描述信息
	 * @param certiNo ：传入的意健险调查信息描述编号
	 * @return 返回意健险调查信息描述
	 */
	public PrpLacciCheckText findPrpLacciCheckText(String certiNo) throws Exception;

	public List<PrpLacciCheckText> findByConditions(String conditions) throws Exception;
}
