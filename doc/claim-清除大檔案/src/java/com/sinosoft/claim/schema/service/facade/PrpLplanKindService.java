package com.sinosoft.claim.schema.service.facade;
/**
 * 赔案险种组合收费计划接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.PrpLplanKind;
import com.sinosoft.claim.schema.model.PrpLplanKindId;

public interface PrpLplanKindService {
	
	/**
	 * 保存赔案险种组合收费计划信息
	 * @param prpLplanKind ：传入的赔案险种组合收费计划
	 */
	public void save(PrpLplanKind prpLplanKind) throws Exception;
	
	/**
	 * 赔案险种组合收费计划信息
	 * @param list  :传入的赔案险种组合收费计划信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLplanKind> list) throws Exception;
	
	/**
	 * 删除赔案险种组合收费计划信息
	 * @param prpLplanKindId ：传入的赔案险种组合收费计划编号
	 */
	public void delete(PrpLplanKindId prpLplanKindId) throws Exception;

	/**
	 * 更新赔案险种组合收费计划信息
	 * @param prpLplanKind :传入需要更新的赔案险种组合收费计划
	 */
	public void update(PrpLplanKind prpLplanKind) throws Exception;

	/**
	 * 根据赔案险种组合收费计划编号查询出赔案险种组合收费计划信息
	 * @param prpLplanKindId ：传入的赔案险种组合收费计划编号
	 * @return 返回赔案险种组合收费计划
	 */
	public PrpLplanKind findPrpLplanKind(PrpLplanKindId prpLplanKindId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔案险种组合收费计划页面信息
	 */
	public Page findPrpLplanKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**根据查询对象获取 赔案险种组合收费计划信息 的集合
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLplanKind> findPrpLplanKind(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔案险种组合收费计划编号查询出赔案险种组合收费计划信息
	 * @param certiNo ：传入的赔案险种组合收费计划编号
	 * @return 返回赔案险种组合收费计划
	 */
	public PrpLplanKind findPrpLplanKind(String certiNo) throws Exception;

	List<PrpLplanKind> findPayLossByConditions(String compensateNo, Map<String,String> codeMap, String coinsType, String coinsFlag, double coinsRate, double sumPaid, Map<String, Object> serialNoMap, String damageDate, String isComBin) throws Exception;

	String findSubRiskCode(PrpLplanKind prpLPlanKind) throws Exception;

	public Collection<?> findByConditions(String conditions)throws Exception;

	List<PrpLplanKind> findReplevyLossByConditions(String compensateNo, Map<String,String> codeMap, String coinsType, String coinsFlag, double coinsRate, double sumPaid, Map<String, Object> serialNoMap, String damageDate, String isComBin) throws Exception;
	
	public List<PrpLplanKind> findRemnantByConditions(String compensateNo, Map<String,String> codeMap, String coinsType, String coinsFlag, double coinsRate, double sumPaid, Map<String, Object> serialNoMap, String damageDate, String isComBin) throws Exception;
}
