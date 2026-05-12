package com.sinosoft.claim.schema.service.facade;
/**
 * 刷卡信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCard;

public interface PrpCardService {
	
	/**
	 * 刷卡信息信息
	 * @param PrpCard ：传入的刷卡信息
	 */
	public void save(PrpCard PrpCard) throws Exception;
	
	/**
	 * 保存刷卡信息
	 * @param list  :传入的刷卡信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCard> list) throws Exception;
	
	/**
	 * 删除刷卡信息信息
	 * @param policyNo ：传入的刷卡信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新刷卡信息信息
	 * @param PrpCard :传入需要更新的刷卡信息
	 */
	public void update(PrpCard PrpCard) throws Exception;

	/**
	 * 根据刷卡信息编号查询出刷卡信息信息
	 * @param policyNo ：传入的刷卡信息编号
	 * @return 返回刷卡信息
	 */
	public PrpCard findPrpCard(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的刷卡信息页面信息
	 */
	public Page findPrpCard(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取刷卡对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的刷卡页面信息
	 */
	public List<PrpCard> findPrpCard(QueryRule queryRule) throws Exception;
}
