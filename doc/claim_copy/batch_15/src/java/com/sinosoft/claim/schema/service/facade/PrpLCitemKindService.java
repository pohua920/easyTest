package com.sinosoft.claim.schema.service.facade;
/**
 * 代赔保单投保险别接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLCitemKind;
import com.sinosoft.claim.schema.model.PrpLCitemKindId;

public interface PrpLCitemKindService {
	
	/**
	 * 保存代赔保单投保险别信息
	 * @param prpLCitemKind ：传入的代赔保单投保险别
	 */
	public void save(PrpLCitemKind prpLCitemKind) throws Exception;
	
	/**
	 * 代赔保单投保险别信息
	 * @param list  :传入的代赔保单投保险别信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLCitemKind> list) throws Exception;
	
	/**
	 * 删除代赔保单投保险别信息
	 * @param prpLCitemKindId ：传入的代赔保单投保险别编号
	 */
	public void delete(PrpLCitemKindId prpLCitemKindId) throws Exception;

	/**
	 * 更新代赔保单投保险别信息
	 * @param prpLCitemKind :传入需要更新的代赔保单投保险别
	 */
	public void update(PrpLCitemKind prpLCitemKind) throws Exception;

	/**
	 * 根据代赔保单投保险别编号查询出代赔保单投保险别信息
	 * @param prpLCitemKindId ：传入的代赔保单投保险别编号
	 * @return 返回代赔保单投保险别
	 */
	public PrpLCitemKind findPrpLCitemKind(PrpLCitemKindId prpLCitemKindId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代赔保单投保险别页面信息
	 */
	public Page findPrpLCitemKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 代赔保单投保险别页面信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的代赔保单投保险别页面信息  的列表
	 */
	public List<PrpLCitemKind> findPrpLCitemKind(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据代赔保单投保险别编号查询出代赔保单投保险别信息
	 * @param certiNo ：传入的代赔保单投保险别编号
	 * @return 返回代赔保单投保险别
	 */
	public PrpLCitemKind findPrpLCitemKind(String certiNo) throws Exception;
	
	/**
	 * mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public String findDeductibleTypeByConditions(String conditions)throws Exception;

	/**
	 * mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核
	 * 檢查 理算書號、牌照號碼、出險日期、出險小時 是否存在
	 */
	public String checkLicenceNoAndDamageStartDate(String licenseNo, String damageDate,
			String damageHour, String compensateNo) throws Exception;
}
