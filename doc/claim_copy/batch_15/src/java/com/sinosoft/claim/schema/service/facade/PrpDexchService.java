package com.sinosoft.claim.schema.service.facade;

import java.util.Date;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDexch;

/***
 * 每日汇率相关查询
 * @author 中科软
 */
public interface PrpDexchService {
	/***
	 * 获取汇率
	 * @param exchDate 日期
	 * @param baseCurrency 本位币
	 * @param exchCurrency 转换币
	 * @return
	 */
	public PrpDexch findPrpDexch(Date exchDate, String baseCurrency, String exchCurrency);

	/***
	 * 获取指定本位币 本日的汇率
	 * @param exchDate 日期 （ 为空则默认当日 ）
	 * @param baseCurrency 本位币
	 * @return
	 */
	public List<PrpDexch> findBasePrpDexch(Date exchDate, String baseCurrency);

	/***
	 * 获取指定目标币别 本日的汇率
	 * @param exchDate 日期 （ 为空则默认当日 ）
	 * @param baseCurrency 目标币别
	 * @return
	 */
	public List<PrpDexch> findExchPrpDexch(Date exchDate, String exchCurrency);
}
