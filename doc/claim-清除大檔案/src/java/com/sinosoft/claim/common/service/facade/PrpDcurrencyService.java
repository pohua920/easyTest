package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDcurrency;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

/**
 * 币别代码表的数据访问接口
 * @author 中科软
 */
public interface PrpDcurrencyService {
	/**
	 * 翻译代码
	 * @param currencyCode 币别代码
	 * @param isChinese 是否中午，英文
	 * @return 币别名称
	 */
	public String translateCode(String currencyCode, boolean isChinese);

	/**
	 * 分页币别代码表的数据
	 * @author 中科软
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 没有显示页数
	 * @return 分页数据
	 */
	public Page findByPage(String conditions, int pageNo, int rowsPerPage);
	
	/**
	 * 分页币别代码表的数据
	 * @param queryRule  查询条件
	 * @return 所有数据
	 * @throws Exception
	 */
	public List<PrpDcurrency> findPrpDcurrency(QueryRule queryRule) throws Exception;
	/**
	 * 查询支付币别
	 * @return
	 */
	public List<PrpDcurrency> findPayCurrency() throws Exception;
}
