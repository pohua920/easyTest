package com.sinosoft.claim.schema.service.facade;
/**
 * 刷卡子信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCardSub;
import com.sinosoft.claim.schema.model.PrpCardSubId;

public interface PrpCardSubService {
	
	/**
	 * 保存刷卡子信息信息
	 * @param PrpCardSub ：传入的刷卡子信息
	 */
	public void save(PrpCardSub PrpCardSub) throws Exception;
	
	/**
	 * 刷卡子信息信息
	 * @param list  :传入的刷卡子信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCardSub> list) throws Exception;
	
	/**
	 * 删除刷卡子信息信息
	 * @param PrpCardSubId ：传入的刷卡子信息编号
	 */
	public void delete(PrpCardSubId PrpCardSubId) throws Exception;

	/**
	 * 更新刷卡子信息信息
	 * @param PrpCardSub :传入需要更新的刷卡子信息
	 */
	public void update(PrpCardSub PrpCardSub) throws Exception;

	/**
	 * 根据刷卡子信息编号查询出刷卡子信息信息
	 * @param PrpCardSubId ：传入的刷卡子信息编号
	 * @return 返回刷卡子信息
	 */
	public PrpCardSub findPrpCardSub(PrpCardSubId PrpCardSubId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的刷卡子信息页面信息
	 */
	public Page findPrpCardSub(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取刷卡子信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的刷卡子信息列表
	 */
	public List<PrpCardSub> findPrpCardSub(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据刷卡子信息编号查询出刷卡子信息信息
	 * @param certiNo ：传入的刷卡子信息编号
	 * @return 返回刷卡子信息
	 */
	public PrpCardSub findPrpCardSub(String certiNo) throws Exception;
}
